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

import java.util.ServiceLoader;

/**
 * Responsible for creating a global context, startup threads and init Vips, this should be thread safe and done once
 *
 *
 * @param <Arguments> Startup Parameters
 * @param <DataType> The data type, could be {@link java.lang.foreign.MemorySegment} or {@link Long} or
 *                  {@link java.nio.ByteBuffer}
 * @param <UnderlyingSession> To allocate strings and things panama (unless we are opening a global session) needs a
 *                           shared session {@link java.lang.foreign.MemorySession} or {@link Void} for everything else
 */
public interface VipsGlobalContextFactory<Arguments extends VipsConfig, DataType, UnderlyingSession> {

    VipsOperationContextFactory<DataType, UnderlyingSession> create(Arguments args);

    void shutdown();


    class Singleton {
        private static final VipsGlobalContextFactory instance = ServiceLoader.load(VipsGlobalContextFactory.class).findFirst().orElseThrow();

        private static volatile VipsOperationContextFactory contextFactory;

        // We want this to be lazy, so no innerclass Instance pattern or enum
        public static <Arguments extends VipsConfig, DataType, UnderlyingSession> VipsOperationContextFactory<DataType, UnderlyingSession> create(Arguments arguments) {
            if(contextFactory == null){
                synchronized (Singleton.class){
                    if(contextFactory == null) {
                        contextFactory = instance.create(arguments);
                    }
                }
            }
            return (VipsOperationContextFactory< DataType, UnderlyingSession>) contextFactory;
        }
    }

}
