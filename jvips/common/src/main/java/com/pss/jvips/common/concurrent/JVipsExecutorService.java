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

package com.pss.jvips.common.concurrent;

import com.pss.jvips.common.context.VipsOperationContext;
import com.pss.jvips.common.impl.SharedReference;

import java.util.concurrent.*;

public class JVipsExecutorService extends ThreadPoolExecutor {


     public JVipsExecutorService(int minSize, int maxSize) {
        super(minSize, maxSize, 1L,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(), JVipsThreadFactory.getFactory(), new AbortPolicy());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        VipsOperationContext scopedInstance = VipsOperationContext.getScopedInstance();
        JVipsThread thread = (JVipsThread) Thread.currentThread();
         thread.getImages().forEach((x,y)-> {
             SharedReference<?, ?> sharedImage = y.get();
             if(sharedImage != null) {
                 scopedInstance.decreaseRefCount(sharedImage.getAddress());
             }
         });
        thread.getImages().clear();
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
    }

    public static JVipsExecutorService ofDefault(){
        int i = Runtime.getRuntime().availableProcessors();
        return new JVipsExecutorService(1 | i - 1, i * 2);
    }
}
