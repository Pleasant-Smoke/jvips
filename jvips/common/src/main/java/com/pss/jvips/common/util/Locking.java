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

package com.pss.jvips.common.util;

import com.pss.jvips.common.impl.VipsImageDelegate;
import com.pss.jvips.common.context.VipsException;
import com.pss.jvips.common.image.JVipsImage;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

public class Locking {

    public interface ThrowingRunnable  {
        void run() throws Exception;
    }

    public static <T> T lock(JVipsImage image, JVipsImage[] array, Callable<T> callable) throws VipsException {
        return lock(image, ()-> lock(array, callable));
    }

    public static <T> T lock(JVipsImage image, Callable<T> callable) throws VipsException {
        if(image instanceof VipsImageDelegate delegate){

            StampedLock lock = delegate.getLock();
            long stamp = 0;
            try {
                stamp = lock.tryReadLock(10, TimeUnit.MILLISECONDS);
                delegate.ensureOperation();
                if (stamp != 0) {
                    return callable.call();
                }
            } catch (Exception e){
                throw new VipsException(e);
            } finally {
                if(stamp != 0){
                    lock.unlockRead(stamp);
                }
            }
        } else {
            throw new VipsException("Iamge not instance of VipsImageDelegate");
        }
        throw new VipsException("Unable to acquire read lock");
    }

    public static void lock(JVipsImage image, ThrowingRunnable callable) throws VipsException {
        if(image instanceof VipsImageDelegate delegate){

            StampedLock lock = delegate.getLock();
            long stamp = 0;
            try {
                stamp = lock.tryReadLock(10, TimeUnit.MILLISECONDS);
                delegate.ensureOperation();
                if (stamp != 0) {
                    callable.run();
                    return;
                }
            } catch (Exception e){
                throw new VipsException(e);
            } finally {
                if(stamp != 0){
                    lock.unlockRead(stamp);
                }
            }
        } else {
            throw new VipsException("Iamge not instance of VipsImageDelegate");
        }
        throw new VipsException("Unable to acquire read lock");
    }


    public static void writeLock(JVipsImage image, ThrowingRunnable callable) throws VipsException {
        if(image instanceof VipsImageDelegate delegate){

            StampedLock lock = delegate.getLock();
            long stamp = 0;
            try {
                stamp = lock.tryWriteLock(10, TimeUnit.MILLISECONDS);
                delegate.ensureOperation();
                if (stamp != 0) {
                    callable.run();
                }
            } catch (Exception e){
                throw new VipsException(e);
            } finally {
                if(stamp != 0){
                    lock.unlockWrite(stamp);
                }
            }
        } else {
            throw new VipsException("Iamge not instance of VipsImageDelegate");
        }
        throw new VipsException("Unable to acquire read lock");
    }

    public static <T> T lock(JVipsImage image1, JVipsImage image2, Callable<T> callable) throws VipsException {
        if(image1 instanceof VipsImageDelegate delegate1 && image2 instanceof VipsImageDelegate delegate2){
            StampedLock lock1 = delegate1.getLock();
            StampedLock lock2 = delegate2.getLock();


            long stamp1 = 0;
            long stamp2 = 0;
            try {
                stamp1 = lock1.tryReadLock(10, TimeUnit.MILLISECONDS);
                stamp2 = lock2.tryReadLock(10, TimeUnit.MILLISECONDS);
                delegate1.ensureOperation();
                delegate2.ensureOperation();
                if (stamp1 != 0 && stamp2 != 0) {
                    return callable.call();
                }
            } catch (Exception e){
                throw new VipsException(e);
            } finally {
                if(stamp1 != 0){
                    lock1.unlockRead(stamp1);
                }
                if(stamp2 != 0){
                    lock2.unlockRead(stamp2);
                }
            }
        } else {
            throw new VipsException("Iamge not instance of VipsImageDelegate");
        }
        throw new VipsException("Unable to acquire read lock");
    }
    public static void lock(JVipsImage image1, JVipsImage image2, ThrowingRunnable callable) throws VipsException {
        if(image1 instanceof VipsImageDelegate delegate1 && image2 instanceof VipsImageDelegate delegate2){
            StampedLock lock1 = delegate1.getLock();
            StampedLock lock2 = delegate2.getLock();


            long stamp1 = 0;
            long stamp2 = 0;
            try {
                stamp1 = lock1.tryReadLock(10, TimeUnit.MILLISECONDS);
                stamp2 = lock2.tryReadLock(10, TimeUnit.MILLISECONDS);
                delegate1.ensureOperation();
                delegate2.ensureOperation();
                if (stamp1 != 0 && stamp2 != 0) {
                    callable.run();
                }
            } catch (Exception e){
                throw new VipsException(e);
            } finally {
                if(stamp1 != 0){
                    lock1.unlockRead(stamp1);
                }
                if(stamp2 != 0){
                    lock2.unlockRead(stamp2);
                }
            }
        } else {
            throw new VipsException("Iamge not instance of VipsImageDelegate");
        }
        throw new VipsException("Unable to acquire read lock");
    }

    public static void writeLock(JVipsImage image1, JVipsImage image2, ThrowingRunnable callable) throws VipsException {
        if(image1 instanceof VipsImageDelegate delegate1 && image2 instanceof VipsImageDelegate delegate2){
            StampedLock lock1 = delegate1.getLock();
            StampedLock lock2 = delegate2.getLock();


            long stamp1 = 0;
            long stamp2 = 0;
            try {
                stamp1 = lock1.tryWriteLock(10, TimeUnit.MILLISECONDS);
                stamp2 = lock2.tryWriteLock(10, TimeUnit.MILLISECONDS);
                delegate1.ensureOperation();
                delegate2.ensureOperation();
                if (stamp1 != 0 && stamp2 != 0) {
                    callable.run();
                }
            } catch (Exception e){
                throw new VipsException(e);
            } finally {
                if(stamp1 != 0){
                    lock1.unlockWrite(stamp1);
                }
                if(stamp2 != 0){
                    lock2.unlockWrite(stamp2);
                }
            }
        } else {
            throw new VipsException("Image not instance of VipsImageDelegate");
        }
        throw new VipsException("Unable to acquire read lock");
    }

    public static <T> T lock(JVipsImage image1, JVipsImage image2, JVipsImage image3, Callable<T> callable) throws VipsException {
        if(image1 instanceof VipsImageDelegate delegate1
                && image2 instanceof VipsImageDelegate delegate2
                && image3 instanceof VipsImageDelegate delegate3){
            StampedLock lock1 = delegate1.getLock();
            StampedLock lock2 = delegate2.getLock();
            StampedLock lock3 = delegate3.getLock();


            long stamp1 = 0;
            long stamp2 = 0;
            long stamp3 = 0;

            try {
                stamp1 = lock1.tryReadLock(10, TimeUnit.MILLISECONDS);
                stamp2 = lock2.tryReadLock(10, TimeUnit.MILLISECONDS);
                stamp3 = lock3.tryReadLock(10, TimeUnit.MILLISECONDS);
                delegate1.ensureOperation();
                delegate2.ensureOperation();
                if (stamp1 != 0 && stamp2 != 0 && stamp3 != 0) {
                    return callable.call();
                }
            } catch (Exception e){
                throw new VipsException(e);
            } finally {
                if(stamp1 != 0){
                    lock1.unlockRead(stamp1);
                }
                if(stamp2 != 0){
                    lock2.unlockRead(stamp2);
                }
                if(stamp3 != 0){
                    lock3.unlockRead(stamp3);
                }
            }
        } else {
            throw new VipsException("Iamge not instance of VipsImageDelegate");
        }
        throw new VipsException("Unable to acquire read lock");
    }


    public static <T> T lock(JVipsImage[] images, Callable<T> callable) throws VipsException {
        VipsImageDelegate[] delegates = new VipsImageDelegate[images.length];
        long[] stamps = new long[images.length];
        Arrays.fill(stamps, 0);
        try {
            for (int i = 0; i < images.length; i++) {
                JVipsImage img = images[i];
                if (img instanceof VipsImageDelegate delegate) {
                    delegate.ensureOperation();
                    delegates[i] = delegate;
                    long stamp = delegate.getLock().tryReadLock(10, TimeUnit.MILLISECONDS);
                    if (stamp != 0) {
                        stamps[i] = stamp;
                    } else {
                        throw new VipsException("Unable to acquire read lock");
                    }
                    delegate.ensureOperation();
                } else {
                    throw new VipsException("Image was not a delegate");
                }
            }
            return callable.call();

        } catch (Exception e){
            throw new VipsException(e);
        } finally {
            for (int i = 0; i < delegates.length; i++) {
                VipsImageDelegate delegate = delegates[i];
                long stamp = stamps[i];
                if(stamp != 0){
                    delegate.getLock().unlockRead(stamp);
                }
            }
        }

    }

}
