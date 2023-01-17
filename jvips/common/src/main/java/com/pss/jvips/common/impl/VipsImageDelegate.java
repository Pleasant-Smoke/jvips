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

import java.util.concurrent.locks.StampedLock;


public class VipsImageDelegate<T> extends VipsDelegate<T, JVipsImage<T>> implements JVipsImage<T> {


    public VipsImageDelegate(long id, SharedReference resource, Thread thread) {
        super(id, resource, thread);
    }


    public JVipsImage<T> getSharedImage() {
        return resource.getReferent();
    }

    public SharedReference<T, JVipsImage<T>> resource(){
        return resource;
    }


    @Override
    public long getId() {
        ensureOperation();
        StampedLock lock = resource.getLock();
        long id = lock.tryReadLock();
        try {
            if(id != 0) {
                return getSharedImage().getId();
            } else {
                throw new RuntimeException("Failed to get read lock for image");
            }
        } finally {
            lock.unlockRead(id);
        }
    }


    @Override
    public int getXSize() {
        ensureOperation();
        StampedLock lock = resource.getLock();
        long id = lock.tryReadLock();
        try {
            if(id != 0) {
                return getSharedImage().getXSize();
            } else {
                throw new RuntimeException("Failed to get read lock for image");
            }
        } finally {
            lock.unlockRead(id);
        }
    }

    @Override
    public int getYSize() {
        ensureOperation();
        StampedLock lock = resource.getLock();
        long id = lock.tryReadLock();
        try {
            if(id != 0) {
                return getSharedImage().getYSize();
            } else {
                throw new RuntimeException("Failed to get read lock for image");
            }
        } finally {
            lock.unlockRead(id);
        }

    }

    @Override
    public int getBands() {
        ensureOperation();
        StampedLock lock = resource.getLock();
        long id = lock.tryReadLock();
        try {
            if(id != 0) {
                return getSharedImage().getBands();
            } else {
                throw new RuntimeException("Failed to get read lock for image");
            }
        } finally {
            lock.unlockRead(id);
        }

    }

    @Override
    public int getBandFmt() {
        ensureOperation();
        StampedLock lock = resource.getLock();
        long id = lock.tryReadLock();
        try {
            if(id != 0) {
                return getSharedImage().getBandFmt();
            } else {
                throw new RuntimeException("Failed to get read lock for image");
            }
        } finally {
            lock.unlockRead(id);
        }

    }

    @Override
    public int getCoding() {
        ensureOperation();
        StampedLock lock = resource.getLock();
        long id = lock.tryReadLock();
        try {
            if(id != 0) {
                return getSharedImage().getCoding();
            } else {
                throw new RuntimeException("Failed to get read lock for image");
            }
        } finally {
            lock.unlockRead(id);
        }

    }

    @Override
    public int getType() {
        ensureOperation();
        StampedLock lock = resource.getLock();
        long id = lock.tryReadLock();
        try {
            if(id != 0) {
                return getSharedImage().getType();
            } else {
                throw new RuntimeException("Failed to get read lock for image");
            }
        } finally {
            lock.unlockRead(id);
        }

    }

    @Override
    public double getXRes() {
        ensureOperation();
        StampedLock lock = resource.getLock();
        long id = lock.tryReadLock();
        try {
            if(id != 0) {
                return getSharedImage().getXRes();
            } else {
                throw new RuntimeException("Failed to get read lock for image");
            }
        } finally {
            lock.unlockRead(id);
        }

    }

    @Override
    public double getYRes() {
        ensureOperation();
        StampedLock lock = resource.getLock();
        long id = lock.tryReadLock();
        try {
            if(id != 0) {
                return getSharedImage().getYRes();
            } else {
                throw new RuntimeException("Failed to get read lock for image");
            }
        } finally {
            lock.unlockRead(id);
        }

    }

    @Override
    public int getXOffset() {
        ensureOperation();
        StampedLock lock = resource.getLock();
        long id = lock.tryReadLock();
        try {
            if(id != 0) {
                return getSharedImage().getXOffset();
            } else {
                throw new RuntimeException("Failed to get read lock for image");
            }
        } finally {
            lock.unlockRead(id);
        }

    }

    @Override
    public int getYOffset() {
        ensureOperation();
        StampedLock lock = resource.getLock();
        long id = lock.tryReadLock();
        try {
            if(id != 0) {
                return getSharedImage().getYOffset();
            } else {
                throw new RuntimeException("Failed to get read lock for image");
            }
        } finally {
            lock.unlockRead(id);
        }

    }

    @Override
    public int getLength() {
        ensureOperation();
        StampedLock lock = resource.getLock();
        long id = lock.tryReadLock();
        try {
            if(id != 0) {
                return getSharedImage().getLength();
            } else {
                throw new RuntimeException("Failed to get read lock for image");
            }
        } finally {
            lock.unlockRead(id);
        }

    }

    @Override
    public short getCompression() {
        ensureOperation();
        StampedLock lock = resource.getLock();
        long id = lock.tryReadLock();
        try {
            if(id != 0) {
                return getSharedImage().getCompression();
            } else {
                throw new RuntimeException("Failed to get read lock for image");
            }
        } finally {
            lock.unlockRead(id);
        }

    }

    @Override
    public short getLevel() {
        ensureOperation();
        StampedLock lock = resource.getLock();
        long id = lock.tryReadLock();
        try {
            if(id != 0) {
                return getSharedImage().getLevel();
            } else {
                throw new RuntimeException("Failed to get read lock for image");
            }
        } finally {
            lock.unlockRead(id);
        }

    }


}
