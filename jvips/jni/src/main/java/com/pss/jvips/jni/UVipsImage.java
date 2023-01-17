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


import com.pss.jvips.common.context.VipsOperationContext;
import com.pss.jvips.jni.util.U;

/**
 * VipsImage
 *
 * A Java VipsImage is **NOT** thread safe, do not call without it being within the same try(..) with resources statement
 * a thread local is used to clean up the native references, to use an image outside a {@link VipsOperationContext} scope it
 *
 * This class will be wrapped in a delegate if it gets unrefed the field will be set to null
 */
@SuppressWarnings("unused")
public class UVipsImage extends JniVipsImage<Long> {

    private final long _address;

    UVipsImage(long address) {
        super(address);
        this._address = address;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public Long getAddress() {
        return _address;
    }

    @Override
    public int getXSize(){
        return U.UNSAFE.getInt(_address + offsetXsize);
    }


    @Override
    public int getYSize(){
        return U.UNSAFE.getInt(_address + offsetYsize);
    }


    @Override
    public int getBands(){
        return U.UNSAFE.getInt(_address + offsetBands);
    }


    @Override
    public int getBandFmt(){
        return U.UNSAFE.getInt(_address + offsetBandFmt);
    }


    @Override
    public int getCoding(){
        return U.UNSAFE.getInt(_address + offsetCoding);
    }


    @Override
    public int getType(){
        return U.UNSAFE.getInt(_address + offsetType);
    }


    @Override
    public double getXRes(){
        return U.UNSAFE.getInt(_address + offsetXres);
    }


    @Override
    public double getYRes(){
        return U.UNSAFE.getInt(_address + offsetYres);
    }


    @Override
    public int getXOffset(){
        return U.UNSAFE.getInt(_address + offsetXoffset);
    }


    @Override
    public int getYOffset(){
        return U.UNSAFE.getInt(_address + offsetYoffset);
    }


    @Override
    public int getLength(){
        return U.UNSAFE.getInt(_address + offsetLength);
    }



    @Override
    public short getCompression(){
        return U.UNSAFE.getShort(_address + offsetCompression);
    }



    @Override
    public short getLevel(){
        return U.UNSAFE.getShort(_address + offsetLevel);
    }

}
