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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * VipsImage
 *
 * A Java VipsImage is **NOT** thread safe, do not call without it being within the same try(..) with resources statement
 * a thread local is used to clean up the native references, to use an image outside a {@link VipsOperationContext} scope it
 *
 * This class will be wrapped in a delegate if it gets unrefed the field will be set to null
 */
@SuppressWarnings("unused")
public class BBVipsImage extends JniVipsImage<ByteBuffer> {


    BBVipsImage(ByteBuffer address) {
        super(address.order(ByteOrder.nativeOrder()).asReadOnlyBuffer());
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public ByteBuffer getAddress() {
        return address;
    }

    @Override
    public int getXSize(){
        return address.getInt(offsetXsize);
    }


    @Override
    public int getYSize(){
        return address.getInt(offsetYsize);
    }


    @Override
    public int getBands(){
        return address.getInt(offsetBands);
    }


    @Override
    public int getBandFmt(){
        return address.getInt(offsetBandFmt);
    }


    @Override
    public int getCoding(){
        return address.getInt(offsetCoding);
    }


    @Override
    public int getType(){
        return address.getInt(offsetType);
    }


    @Override
    public double getXRes(){
        return address.getInt(offsetXres);
    }


    @Override
    public double getYRes(){
        return address.getInt(offsetYres);
    }


    @Override
    public int getXOffset(){
        return address.getInt(offsetXoffset);
    }


    @Override
    public int getYOffset(){
        return address.getInt(offsetYoffset);
    }


    @Override
    public int getLength(){
        return address.getInt(offsetLength);
    }



    @Override
    public short getCompression(){
        return address.getShort(offsetCompression);
    }



    @Override
    public short getLevel(){
        return address.get(offsetLevel);
    }

}
