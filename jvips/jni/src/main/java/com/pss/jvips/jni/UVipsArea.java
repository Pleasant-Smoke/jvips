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

package com.pss.jvips.jni;

import com.pss.jvips.common.api.GType;
import com.pss.jvips.jni.util.U;

import java.nio.ByteBuffer;

public class UVipsArea extends JniVipsArea<Long> {

    private final long _address;


    UVipsArea(Long address, long secondaryOffset, ByteBuffer bb) {
        super(address, secondaryOffset, bb);
        this._address = address;
    }

    @Override
    public ByteBuffer getData() {
        return bb;
    }

    @Override
    public long length() {
        return U.UNSAFE.getLong(_address + secondaryOffset + offsetLength);
    }

    @Override
    public long n() {
        return U.UNSAFE.getLong(_address + secondaryOffset + offsetN);
    }

    @Override
    public GType type() {
        return GType.values()[( U.UNSAFE.getInt(_address + secondaryOffset + offsetType))];
    }

    @Override
    public long sizeOfType() {
        return U.UNSAFE.getLong(_address + secondaryOffset + offsetSizeOfType);
    }

    @Override
    public Long getAddress() {
        return address;
    }
}
