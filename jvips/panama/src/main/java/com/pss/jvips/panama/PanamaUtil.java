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

package com.pss.jvips.panama;

import com.pss.jvips.common.util.VipsOperationContextAccess;
import com.pss.jvips.common.image.JVipsImage;
import com.pss.jvips.common.context.VipsOperationContext;
import com.pss.jvips.common.util.VipsSharedSecret;
import org.libvips.LibVips;
import org.libvips.VipsImage;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;

public class PanamaUtil {

    private static final VipsOperationContextAccess secret = VipsSharedSecret.getContextAccess();

    public static String getMessage(){
        MemoryAddress memoryAddress = LibVips.vips_error_buffer_copy();
        return memoryAddress.getUtf8String(0);
    }

    public static MemoryAddress doubleBuffer(MemorySession session, double[] db){
        MemorySegment array = session.allocateArray(LibVips.C_DOUBLE, db);
        MemoryAddress memoryAddress = LibVips.vips_array_double_new(array, db.length);
        return memoryAddress;
    }

    public static JVipsImage<MemorySegment> getImage(VipsOperationContext<MemorySegment, MemorySession> session,
                                                     MemoryAddress addressable){
        MemoryAddress address = addressable.get(LibVips.C_POINTER, 0);
        MemorySegment segment = VipsImage.ofAddress(address, secret.getUnderlying(session));
        long id = secret.getNewId(session);
        return secret.createImage(session, id, segment, Thread.currentThread());
    }
}
