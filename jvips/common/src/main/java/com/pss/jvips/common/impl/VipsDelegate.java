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

package com.pss.jvips.common.impl;

import com.pss.jvips.common.image.JVipsImage;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;


public class VipsDelegate<T, R extends Addressable<T>> {

    protected final long id;

    protected final SharedReference<T, R> resource;
    protected final Thread thread;

    public VipsDelegate(long id, SharedReference resource, Thread thread) {
        this.id = id;
        this.resource = resource;
        this.thread = thread;
    }


    public R getResource() {
        return resource.getReferent();
    }

    public SharedReference<T, R> sharedReference(){
        return resource;
    }



    public T getAddress() {
        ensureOperation();
        return getResource().getAddress();
    }

    protected <U> U read(Callable<U> callable) {
        StampedLock lock = resource.getLock();
        long readStamp = 0;
        try {
            readStamp = lock.tryReadLock(10, TimeUnit.MILLISECONDS);
            if(readStamp != 0){
                ensureOperation();
                return callable.call();
            } else {
                throw new RuntimeException("Failed to get read lock for resource");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if(readStamp != 0){
                lock.unlockRead(readStamp);
            }
        }
    }


    public StampedLock getLock() {
        return resource.getLock();
    }

    public void ensureOperation(){
        if(this.resource.getReferent() == null || this.resource.getThreadCount() == 0){
            throw new RuntimeException("Image was GC'ed");
        }
        if(this.thread != Thread.currentThread()){
            throw new RuntimeException("Thread does not hold image");
        }
    }
}
