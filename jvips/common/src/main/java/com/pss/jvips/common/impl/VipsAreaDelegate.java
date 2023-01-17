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

import com.pss.jvips.common.api.GType;
import com.pss.jvips.common.image.JVipsArea;

import java.nio.ByteBuffer;

public class VipsAreaDelegate<T> extends VipsDelegate<T, JVipsArea<T>> implements JVipsArea<T> {


    public VipsAreaDelegate(long id, SharedReference resource, Thread thread) {
        super(id, resource, thread);
    }

    @Override
    public ByteBuffer getData() {
        return read(()-> resource.getReferent().getData());
    }

    @Override
    public long length() {
        return read(()-> resource.getReferent().length());
    }

    @Override
    public long n() {
        return read(()-> resource.getReferent().n());
    }

    @Override
    public GType type() {
        return read(()-> resource.getReferent().type());
    }

    @Override
    public long sizeOfType() {
        return read(()-> resource.getReferent().sizeOfType());
    }
}
