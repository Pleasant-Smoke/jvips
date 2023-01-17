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

import com.pss.jvips.common.image.JVipsImage;
import com.pss.jvips.common.context.VipsException;
import com.pss.jvips.common.context.VipsOperationContext;
import org.libvips.*;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class PanamaVipsOperationContext extends VipsOperationContext<MemorySegment, MemorySession> {

    static MethodHandle REMOVE;

    static {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle remove;
        try {
            remove = lookup.findStatic(VipsOperationContext.class, "remove", MethodType.methodType(void.class, MemoryAddress.class));

        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        REMOVE = remove;
    }



    PanamaVipsOperationContext(MemorySession session) throws VipsException {
        super(session);
    }

    @Override
    protected JVipsImage<MemorySegment> createImage(long id, MemorySegment context) {
        // leaving here seems to be missing LibVips.g_signal_connect()

        MemorySegment memorySegment = Linker.nativeLinker()
                .upcallStub(REMOVE, FunctionDescriptor.ofVoid(LibVips.C_LONG), underlying);

        LibVips.g_signal_connect_data(context, underlying.allocateUtf8String("preclose"), memorySegment,
                underlying.allocate(LibVips.C_LONG, id),
                LibVips.NULL(),0);
        return new PanamaVipsImage(context, id);
    }

    static void remove(MemoryAddress userData){
        long id = userData.get(LibVips.C_LONG, 0);
        remove(id);
    }

    @Override
    protected void increaseRefCount(MemorySegment address) {
        LibVips.g_object_ref(address);
    }

    @Override
    protected JVipsImage<MemorySegment> createImage(Long id, MemorySegment context) {
        return null;
    }

    @Override
    public void decreaseRefCount(MemorySegment address) {

    }

    public static PanamaVipsOperationContext create(){
        MemorySession memorySession = MemorySession.openShared();
        try {
            return new PanamaVipsOperationContext(memorySession);
        } catch (VipsException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unref(MemorySegment address, boolean zero) {
        if(zero){
            long type = LibVips.vips_image_get_type();
            if(LibVips.g_type_check_instance_is_a(address, type) != 0){
                MemorySegment memorySegment = VipsImage.parent_instance$slice(address);
                MemorySegment gobject = VipsObject.parent_instance$slice(memorySegment);
                int refcount = GObject.ref_count$get(gobject);
                do {
                    LibVips.g_object_unref(address);
                    refcount--;
                } while (refcount > 0);
            }
        } else {
            LibVips.g_object_unref(address);
        }
    }


    @Override
    public void close() throws VipsException {
        super.close();
        underlying.close();
    }

    @Override
    public MemorySegment getAddress() {
        return null;
    }
}
