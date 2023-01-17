/*
 * jvips, a Java implementation that interfaces to libvips
 *
 * Copyright (C) 2023 Jonathan Strauss
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * https://www.gnu.org/licenses/old-licenses/lgpl-2.1-standalone.html
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package com.pss.jvips.common.context;

import com.pss.jvips.common.cleaner.VipsCleaner;
import com.pss.jvips.common.concurrent.JVipsThread;
import com.pss.jvips.common.image.JVipsArea;
import com.pss.jvips.common.image.JVipsBlob;
import com.pss.jvips.common.image.JVipsImage;
import com.pss.jvips.common.impl.SharedReference;
import com.pss.jvips.common.impl.VipsAreaDelegate;
import com.pss.jvips.common.impl.VipsBlobImpl;
import com.pss.jvips.common.impl.VipsImageDelegate;
import com.pss.jvips.common.util.VipsOperationContextAccess;
import com.pss.jvips.common.util.VipsSharedSecret;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.StampedLock;

public abstract class VipsOperationContext<DataType, UnderlyingSession> implements AutoCloseable {



    private static final InheritableThreadLocal<VipsOperationContext<?, ?>> scopedInstance = new InheritableThreadLocal<>();

    public static final ReferenceQueue<VipsImageDelegate<?>> imageQueue = new ReferenceQueue<>();

    private static final long longMax = Long.MAX_VALUE - 10;
    private static final AtomicLong idGenerator = new AtomicLong(0);


    private static final Map<UUID, WeakReference<VipsOperationContext>> imageContexts = new ConcurrentHashMap<>();
    private final Map<Long, WeakReference<SharedReference<DataType, ?>>> images = new ConcurrentHashMap<>();

    protected Thread thread = Thread.currentThread();


    private final UUID id = UUID.randomUUID();

    protected final UnderlyingSession underlying;

    protected VipsOperationContext(UnderlyingSession underlying) throws VipsException {
        if(scopedInstance.get() != null){
            throw new VipsException("Trying to create another context");
        }
        scopedInstance.set(this);
        this.underlying = underlying;
        imageContexts.put(id, new WeakReference<>(this));
    }


    UnderlyingSession getUnderlying(){
        return underlying;
    }

    long getNewId(){
        idGenerator.compareAndSet(longMax, 0);
        return idGenerator.incrementAndGet();
    }

    public static VipsOperationContext getScopedInstance(){
        return scopedInstance.get();
    }

    @Override
    public void close() throws VipsException {
        try {
            for (WeakReference<SharedReference<DataType, ?>> value : images.values()) {
                SharedReference<DataType, ?> delegate = value.get();
                if (delegate != null) {
                    StampedLock lock = delegate.getLock();
                    long stamp = 0;
                    try {
                        stamp = lock.tryWriteLock();
                        DataType address = (DataType) delegate.getAddress();
                        delegate.clear();
                        unref(address, true);
                    } finally {
                        if (stamp != 0) {
                            lock.unlockWrite(stamp);
                        }
                    }
                }
            }
        } finally {
            scopedInstance.remove();
            imageContexts.remove(id);
        }

    }

    protected abstract void unref(DataType address, boolean zero);


    JVipsImage<DataType> createImage(long id, DataType address, Thread thread){
        SharedReference<DataType, JVipsImage<DataType>> sharedImage = new SharedReference<>(id, createImage(id, address), address);
        VipsImageDelegate<DataType> vipsImageDelegate = new VipsImageDelegate<>(id, sharedImage, thread);
        new ImageRef(vipsImageDelegate, imageQueue);
        images.put(id, new WeakReference<>(sharedImage));
        return vipsImageDelegate;
    }

    JVipsArea<DataType> createArea(long id, DataType address, Thread thread){
        SharedReference<DataType, ?> sharedImage = new SharedReference<>(id, createArea(id, address), address);
        return new VipsAreaDelegate<>(id, sharedImage, thread);
    }

    protected abstract JVipsArea<DataType> createArea(long id, DataType address);

    JVipsBlob<DataType> createBlob(long id, DataType address, Thread thread){
        JVipsArea<DataType> area = createArea(id, address, thread);
        JVipsBlob<DataType> vipsImageDelegate = new VipsBlobImpl<>(area);
        return vipsImageDelegate;
    }



    @SuppressWarnings("unchecked")
    public JVipsImage<DataType> transfer(Thread thread, JVipsImage<DataType> image){
        if(!(thread instanceof JVipsThread) || !Thread.currentThread().equals(thread)){
            throw new RuntimeException("VipsImages can only be transferred to a JVipsThread or the Original thread that constructed the context");
        }
        if(image instanceof VipsImageDelegate imageDelegate){
            StampedLock lock = imageDelegate.getLock();
            long stamp = 0;
            try {
                stamp = lock.tryWriteLock(10, TimeUnit.MILLISECONDS);
                if(stamp != 0) {
                    imageDelegate.sharedReference().increment();
                    long id = getNewId();
                    increaseRefCount((DataType) imageDelegate.getAddress());
                    VipsImageDelegate<DataType> delegate = new VipsImageDelegate<DataType>(id, imageDelegate.sharedReference(), thread);
                    new ImageRef(delegate, imageQueue);
                    return delegate;
                }
                throw new RuntimeException("Failed to acquire a write lock");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                if(stamp != 0){
                    lock.unlockWrite(stamp);
                }
            }
        }
        throw new RuntimeException("VipsImage was not a delegate");
    }

    protected abstract void increaseRefCount(DataType address);

    public void decreaseRefCount(Object address) {

    }

    /**
     * == Important
     *
     * Something may have gone wrong, shutdown the JVM, prevent corruption, most likely the refrence counting callback
     * failed to get a write lock on the SharedResource meaning some thread went rouge and may try to read from arbitrary
     * memory
     */
    void kaboom(){
        Runtime.getRuntime().halt(1);
    }


    protected abstract JVipsImage<DataType> createImage(Long id, DataType context);


    public static class ImageRef<T> extends PhantomReference<VipsImageDelegate<T>> {

        public final T address;

        public ImageRef(VipsImageDelegate<T> referent, ReferenceQueue<? super VipsImageDelegate> q) {
            super(referent, q);
            this.address = referent.getAddress();
        }
    }


    public static class Access {
        static {
            VipsSharedSecret.setContextAccess(new VipsOperationContextAccess() {
                @Override
                public long getNewId(VipsOperationContext context) {
                    return context.getNewId();
                }

                @Override
                public <U> U getUnderlying(VipsOperationContext context) {
                    return (U) context.getUnderlying();
                }

                @Override
                public <T> JVipsImage<T> createImage(VipsOperationContext context, long id, T address, Thread thread) {
                    return context.createImage(id, address, thread);
                }

                @Override
                public <DataType> JVipsBlob<DataType> createBlob(VipsOperationContext context, long id, DataType address, Thread thread) {
                    return context.createBlob(id, address, thread);
                }
            });
        }
    }

}
