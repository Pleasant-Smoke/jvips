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

package com.pss.jvips.common.cleaner;

import com.pss.jvips.common.context.VipsOperationContext;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class BaseVipsCleaner<DataType> implements VipsCleaner<DataType> {

    private static final AtomicBoolean cleanerRunning = new AtomicBoolean(false);

    private static volatile boolean runCleaning = true;

    @Override
    public void run() {
        if (cleanerRunning.compareAndSet(false, true)) {
            for (;;) {
                if (runCleaning) {
                    try {
                        VipsOperationContext.ImageRef<?> remove = (VipsOperationContext.ImageRef<DataType>) VipsOperationContext.imageQueue.remove(100);
                        unref((DataType) remove.address, false);
                    } catch (Throwable t) {
                        // Do nothing
                    }
                } else {
                    return;
                }
            }
        }
    }

    public void shutdown(){
        runCleaning = false;
    }
}
