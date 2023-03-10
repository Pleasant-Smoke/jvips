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

// Generated by jextract

package org.libvips;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
public class _VipsFormatClass {

    static final  GroupLayout $struct$LAYOUT = MemoryLayout.structLayout(
        MemoryLayout.structLayout(
            MemoryLayout.structLayout(
                MemoryLayout.structLayout(
                    Constants$root.C_LONG_LONG$LAYOUT.withName("g_type")
                ).withName("g_type_class"),
                Constants$root.C_POINTER$LAYOUT.withName("construct_properties"),
                Constants$root.C_POINTER$LAYOUT.withName("constructor"),
                Constants$root.C_POINTER$LAYOUT.withName("set_property"),
                Constants$root.C_POINTER$LAYOUT.withName("get_property"),
                Constants$root.C_POINTER$LAYOUT.withName("dispose"),
                Constants$root.C_POINTER$LAYOUT.withName("finalize"),
                Constants$root.C_POINTER$LAYOUT.withName("dispatch_properties_changed"),
                Constants$root.C_POINTER$LAYOUT.withName("notify"),
                Constants$root.C_POINTER$LAYOUT.withName("constructed"),
                Constants$root.C_LONG_LONG$LAYOUT.withName("flags"),
                Constants$root.C_LONG_LONG$LAYOUT.withName("n_construct_properties"),
                Constants$root.C_POINTER$LAYOUT.withName("pspecs"),
                Constants$root.C_LONG_LONG$LAYOUT.withName("n_pspecs"),
                MemoryLayout.sequenceLayout(3, Constants$root.C_POINTER$LAYOUT).withName("pdummy")
            ).withName("parent_class"),
            Constants$root.C_POINTER$LAYOUT.withName("build"),
            Constants$root.C_POINTER$LAYOUT.withName("postbuild"),
            Constants$root.C_POINTER$LAYOUT.withName("summary_class"),
            Constants$root.C_POINTER$LAYOUT.withName("summary"),
            Constants$root.C_POINTER$LAYOUT.withName("dump"),
            Constants$root.C_POINTER$LAYOUT.withName("sanity"),
            Constants$root.C_POINTER$LAYOUT.withName("rewind"),
            Constants$root.C_POINTER$LAYOUT.withName("preclose"),
            Constants$root.C_POINTER$LAYOUT.withName("close"),
            Constants$root.C_POINTER$LAYOUT.withName("postclose"),
            Constants$root.C_POINTER$LAYOUT.withName("new_from_string"),
            Constants$root.C_POINTER$LAYOUT.withName("to_string"),
            Constants$root.C_INT$LAYOUT.withName("output_needs_arg"),
            MemoryLayout.paddingLayout(32),
            Constants$root.C_POINTER$LAYOUT.withName("output_to_arg"),
            Constants$root.C_POINTER$LAYOUT.withName("nickname"),
            Constants$root.C_POINTER$LAYOUT.withName("description"),
            Constants$root.C_POINTER$LAYOUT.withName("argument_table"),
            Constants$root.C_POINTER$LAYOUT.withName("argument_table_traverse"),
            Constants$root.C_LONG_LONG$LAYOUT.withName("argument_table_traverse_gtype"),
            Constants$root.C_INT$LAYOUT.withName("deprecated"),
            MemoryLayout.paddingLayout(32),
            Constants$root.C_POINTER$LAYOUT.withName("_vips_reserved1"),
            Constants$root.C_POINTER$LAYOUT.withName("_vips_reserved2"),
            Constants$root.C_POINTER$LAYOUT.withName("_vips_reserved3"),
            Constants$root.C_POINTER$LAYOUT.withName("_vips_reserved4")
        ).withName("parent_class"),
        Constants$root.C_POINTER$LAYOUT.withName("is_a"),
        Constants$root.C_POINTER$LAYOUT.withName("header"),
        Constants$root.C_POINTER$LAYOUT.withName("load"),
        Constants$root.C_POINTER$LAYOUT.withName("save"),
        Constants$root.C_POINTER$LAYOUT.withName("get_flags"),
        Constants$root.C_INT$LAYOUT.withName("priority"),
        MemoryLayout.paddingLayout(32),
        Constants$root.C_POINTER$LAYOUT.withName("suffs")
    ).withName("_VipsFormatClass");
    public static MemoryLayout $LAYOUT() {
        return _VipsFormatClass.$struct$LAYOUT;
    }
    public static MemorySegment parent_class$slice(MemorySegment seg) {
        return seg.asSlice(0, 328);
    }
    static final FunctionDescriptor is_a$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle is_a$MH = RuntimeHelper.downcallHandle(
        _VipsFormatClass.is_a$FUNC
    );
    public interface is_a {

        int apply(java.lang.foreign.MemoryAddress _x0);
        static MemorySegment allocate(is_a fi, MemorySession session) {
            return RuntimeHelper.upcallStub(is_a.class, fi, _VipsFormatClass.is_a$FUNC, session);
        }
        static is_a ofAddress(MemoryAddress addr, MemorySession session) {
            MemorySegment symbol = MemorySegment.ofAddress(addr, 0, session);
            return (java.lang.foreign.MemoryAddress __x0) -> {
                try {
                    return (int)_VipsFormatClass.is_a$MH.invokeExact((Addressable)symbol, (java.lang.foreign.Addressable)__x0);
                } catch (Throwable ex$) {
                    throw new AssertionError("should not reach here", ex$);
                }
            };
        }
    }

    static final VarHandle is_a$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("is_a"));
    public static VarHandle is_a$VH() {
        return _VipsFormatClass.is_a$VH;
    }
    public static MemoryAddress is_a$get(MemorySegment seg) {
        return (java.lang.foreign.MemoryAddress)_VipsFormatClass.is_a$VH.get(seg);
    }
    public static void is_a$set( MemorySegment seg, MemoryAddress x) {
        _VipsFormatClass.is_a$VH.set(seg, x);
    }
    public static MemoryAddress is_a$get(MemorySegment seg, long index) {
        return (java.lang.foreign.MemoryAddress)_VipsFormatClass.is_a$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void is_a$set(MemorySegment seg, long index, MemoryAddress x) {
        _VipsFormatClass.is_a$VH.set(seg.asSlice(index*sizeof()), x);
    }
    public static is_a is_a (MemorySegment segment, MemorySession session) {
        return is_a.ofAddress(is_a$get(segment), session);
    }
    static final FunctionDescriptor header$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle header$MH = RuntimeHelper.downcallHandle(
        _VipsFormatClass.header$FUNC
    );
    public interface header {

        int apply(java.lang.foreign.MemoryAddress _x0, java.lang.foreign.MemoryAddress _x1);
        static MemorySegment allocate(header fi, MemorySession session) {
            return RuntimeHelper.upcallStub(header.class, fi, _VipsFormatClass.header$FUNC, session);
        }
        static header ofAddress(MemoryAddress addr, MemorySession session) {
            MemorySegment symbol = MemorySegment.ofAddress(addr, 0, session);
            return (java.lang.foreign.MemoryAddress __x0, java.lang.foreign.MemoryAddress __x1) -> {
                try {
                    return (int)_VipsFormatClass.header$MH.invokeExact((Addressable)symbol, (java.lang.foreign.Addressable)__x0, (java.lang.foreign.Addressable)__x1);
                } catch (Throwable ex$) {
                    throw new AssertionError("should not reach here", ex$);
                }
            };
        }
    }

    static final VarHandle header$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("header"));
    public static VarHandle header$VH() {
        return _VipsFormatClass.header$VH;
    }
    public static MemoryAddress header$get(MemorySegment seg) {
        return (java.lang.foreign.MemoryAddress)_VipsFormatClass.header$VH.get(seg);
    }
    public static void header$set( MemorySegment seg, MemoryAddress x) {
        _VipsFormatClass.header$VH.set(seg, x);
    }
    public static MemoryAddress header$get(MemorySegment seg, long index) {
        return (java.lang.foreign.MemoryAddress)_VipsFormatClass.header$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void header$set(MemorySegment seg, long index, MemoryAddress x) {
        _VipsFormatClass.header$VH.set(seg.asSlice(index*sizeof()), x);
    }
    public static header header (MemorySegment segment, MemorySession session) {
        return header.ofAddress(header$get(segment), session);
    }
    static final FunctionDescriptor load$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle load$MH = RuntimeHelper.downcallHandle(
        _VipsFormatClass.load$FUNC
    );
    public interface load {

        int apply(java.lang.foreign.MemoryAddress _x0, java.lang.foreign.MemoryAddress _x1);
        static MemorySegment allocate(load fi, MemorySession session) {
            return RuntimeHelper.upcallStub(load.class, fi, _VipsFormatClass.load$FUNC, session);
        }
        static load ofAddress(MemoryAddress addr, MemorySession session) {
            MemorySegment symbol = MemorySegment.ofAddress(addr, 0, session);
            return (java.lang.foreign.MemoryAddress __x0, java.lang.foreign.MemoryAddress __x1) -> {
                try {
                    return (int)_VipsFormatClass.load$MH.invokeExact((Addressable)symbol, (java.lang.foreign.Addressable)__x0, (java.lang.foreign.Addressable)__x1);
                } catch (Throwable ex$) {
                    throw new AssertionError("should not reach here", ex$);
                }
            };
        }
    }

    static final VarHandle load$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("load"));
    public static VarHandle load$VH() {
        return _VipsFormatClass.load$VH;
    }
    public static MemoryAddress load$get(MemorySegment seg) {
        return (java.lang.foreign.MemoryAddress)_VipsFormatClass.load$VH.get(seg);
    }
    public static void load$set( MemorySegment seg, MemoryAddress x) {
        _VipsFormatClass.load$VH.set(seg, x);
    }
    public static MemoryAddress load$get(MemorySegment seg, long index) {
        return (java.lang.foreign.MemoryAddress)_VipsFormatClass.load$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void load$set(MemorySegment seg, long index, MemoryAddress x) {
        _VipsFormatClass.load$VH.set(seg.asSlice(index*sizeof()), x);
    }
    public static load load (MemorySegment segment, MemorySession session) {
        return load.ofAddress(load$get(segment), session);
    }
    static final FunctionDescriptor save$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle save$MH = RuntimeHelper.downcallHandle(
        _VipsFormatClass.save$FUNC
    );
    public interface save {

        int apply(java.lang.foreign.MemoryAddress _x0, java.lang.foreign.MemoryAddress _x1);
        static MemorySegment allocate(save fi, MemorySession session) {
            return RuntimeHelper.upcallStub(save.class, fi, _VipsFormatClass.save$FUNC, session);
        }
        static save ofAddress(MemoryAddress addr, MemorySession session) {
            MemorySegment symbol = MemorySegment.ofAddress(addr, 0, session);
            return (java.lang.foreign.MemoryAddress __x0, java.lang.foreign.MemoryAddress __x1) -> {
                try {
                    return (int)_VipsFormatClass.save$MH.invokeExact((Addressable)symbol, (java.lang.foreign.Addressable)__x0, (java.lang.foreign.Addressable)__x1);
                } catch (Throwable ex$) {
                    throw new AssertionError("should not reach here", ex$);
                }
            };
        }
    }

    static final VarHandle save$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("save"));
    public static VarHandle save$VH() {
        return _VipsFormatClass.save$VH;
    }
    public static MemoryAddress save$get(MemorySegment seg) {
        return (java.lang.foreign.MemoryAddress)_VipsFormatClass.save$VH.get(seg);
    }
    public static void save$set( MemorySegment seg, MemoryAddress x) {
        _VipsFormatClass.save$VH.set(seg, x);
    }
    public static MemoryAddress save$get(MemorySegment seg, long index) {
        return (java.lang.foreign.MemoryAddress)_VipsFormatClass.save$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void save$set(MemorySegment seg, long index, MemoryAddress x) {
        _VipsFormatClass.save$VH.set(seg.asSlice(index*sizeof()), x);
    }
    public static save save (MemorySegment segment, MemorySession session) {
        return save.ofAddress(save$get(segment), session);
    }
    static final FunctionDescriptor get_flags$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle get_flags$MH = RuntimeHelper.downcallHandle(
        _VipsFormatClass.get_flags$FUNC
    );
    public interface get_flags {

        int apply(java.lang.foreign.MemoryAddress _x0);
        static MemorySegment allocate(get_flags fi, MemorySession session) {
            return RuntimeHelper.upcallStub(get_flags.class, fi, _VipsFormatClass.get_flags$FUNC, session);
        }
        static get_flags ofAddress(MemoryAddress addr, MemorySession session) {
            MemorySegment symbol = MemorySegment.ofAddress(addr, 0, session);
            return (java.lang.foreign.MemoryAddress __x0) -> {
                try {
                    return (int)_VipsFormatClass.get_flags$MH.invokeExact((Addressable)symbol, (java.lang.foreign.Addressable)__x0);
                } catch (Throwable ex$) {
                    throw new AssertionError("should not reach here", ex$);
                }
            };
        }
    }

    static final VarHandle get_flags$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("get_flags"));
    public static VarHandle get_flags$VH() {
        return _VipsFormatClass.get_flags$VH;
    }
    public static MemoryAddress get_flags$get(MemorySegment seg) {
        return (java.lang.foreign.MemoryAddress)_VipsFormatClass.get_flags$VH.get(seg);
    }
    public static void get_flags$set( MemorySegment seg, MemoryAddress x) {
        _VipsFormatClass.get_flags$VH.set(seg, x);
    }
    public static MemoryAddress get_flags$get(MemorySegment seg, long index) {
        return (java.lang.foreign.MemoryAddress)_VipsFormatClass.get_flags$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void get_flags$set(MemorySegment seg, long index, MemoryAddress x) {
        _VipsFormatClass.get_flags$VH.set(seg.asSlice(index*sizeof()), x);
    }
    public static get_flags get_flags (MemorySegment segment, MemorySession session) {
        return get_flags.ofAddress(get_flags$get(segment), session);
    }
    static final VarHandle priority$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("priority"));
    public static VarHandle priority$VH() {
        return _VipsFormatClass.priority$VH;
    }
    public static int priority$get(MemorySegment seg) {
        return (int)_VipsFormatClass.priority$VH.get(seg);
    }
    public static void priority$set( MemorySegment seg, int x) {
        _VipsFormatClass.priority$VH.set(seg, x);
    }
    public static int priority$get(MemorySegment seg, long index) {
        return (int)_VipsFormatClass.priority$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void priority$set(MemorySegment seg, long index, int x) {
        _VipsFormatClass.priority$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle suffs$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("suffs"));
    public static VarHandle suffs$VH() {
        return _VipsFormatClass.suffs$VH;
    }
    public static MemoryAddress suffs$get(MemorySegment seg) {
        return (java.lang.foreign.MemoryAddress)_VipsFormatClass.suffs$VH.get(seg);
    }
    public static void suffs$set( MemorySegment seg, MemoryAddress x) {
        _VipsFormatClass.suffs$VH.set(seg, x);
    }
    public static MemoryAddress suffs$get(MemorySegment seg, long index) {
        return (java.lang.foreign.MemoryAddress)_VipsFormatClass.suffs$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void suffs$set(MemorySegment seg, long index, MemoryAddress x) {
        _VipsFormatClass.suffs$VH.set(seg.asSlice(index*sizeof()), x);
    }
    public static long sizeof() { return $LAYOUT().byteSize(); }
    public static MemorySegment allocate(SegmentAllocator allocator) { return allocator.allocate($LAYOUT()); }
    public static MemorySegment allocateArray(int len, SegmentAllocator allocator) {
        return allocator.allocate(MemoryLayout.sequenceLayout(len, $LAYOUT()));
    }
    public static MemorySegment ofAddress(MemoryAddress addr, MemorySession session) { return RuntimeHelper.asArray(addr, $LAYOUT(), 1, session); }
}


