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

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.locks.StampedLock;

import static java.util.concurrent.atomic.AtomicIntegerFieldUpdater.newUpdater;

public class SharedReference<T, R extends Addressable<T>> {

    private static final AtomicIntegerFieldUpdater<SharedReference> updater = newUpdater(SharedReference.class, "threadCount");

    private final long id;

    private volatile int threadCount = 1;

    private volatile R referent;

    private final StampedLock lock = new StampedLock();

    private final T address;

    public SharedReference(long id, R referent, T address) {
        this.id = id;
        this.referent = referent;
        this.address = address;
    }

    public R getReferent() {
        return referent;
    }

    public void clear(){
        this.referent = null;
    }

    public void increment(){
        updater.incrementAndGet(this);
    }

    public void decrement(){
        updater.decrementAndGet(this);
    }

    public int getThreadCount(){
        return updater.get(this);
    }

    public long getId() {
        return id;
    }

    public StampedLock getLock() {
        return lock;
    }

    public T getAddress() {
        return address;
    }
}
