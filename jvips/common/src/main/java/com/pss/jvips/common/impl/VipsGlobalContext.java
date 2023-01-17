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

import com.pss.jvips.common.context.VipsOperationContext;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public abstract class VipsGlobalContext<DataType, Session> {

    private static final long longMax = Long.MAX_VALUE - 10;
    private static final AtomicLong idGenerator = new AtomicLong(0);

    private final InheritableThreadLocal<VipsOperationContext<DataType, Session>> scopedInstance = new InheritableThreadLocal<>();

    private final ReferenceQueue<VipsImageDelegate<DataType>> imageQueue = new ReferenceQueue<>();

    private final Map<UUID, WeakReference<VipsOperationContext<DataType, Session>>> imageContexts = new ConcurrentHashMap<>();

}
