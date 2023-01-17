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
public class _VipsOperationClass {

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
        Constants$root.C_POINTER$LAYOUT.withName("usage"),
        Constants$root.C_POINTER$LAYOUT.withName("get_flags"),
        Constants$root.C_INT$LAYOUT.withName("flags"),
        MemoryLayout.paddingLayout(32),
        Constants$root.C_POINTER$LAYOUT.withName("invalidate")
    ).withName("_VipsOperationClass");
    public static MemoryLayout $LAYOUT() {
        return _VipsOperationClass.$struct$LAYOUT;
    }
    public static MemorySegment parent_class$slice(MemorySegment seg) {
        return seg.asSlice(0, 328);
    }
    static final FunctionDescriptor usage$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle usage$MH = RuntimeHelper.downcallHandle(
        _VipsOperationClass.usage$FUNC
    );
    public interface usage {

        void apply(java.lang.foreign.MemoryAddress _x0, java.lang.foreign.MemoryAddress _x1);
        static MemorySegment allocate(usage fi, MemorySession session) {
            return RuntimeHelper.upcallStub(usage.class, fi, _VipsOperationClass.usage$FUNC, session);
        }
        static usage ofAddress(MemoryAddress addr, MemorySession session) {
            MemorySegment symbol = MemorySegment.ofAddress(addr, 0, session);
            return (java.lang.foreign.MemoryAddress __x0, java.lang.foreign.MemoryAddress __x1) -> {
                try {
                    _VipsOperationClass.usage$MH.invokeExact((Addressable)symbol, (java.lang.foreign.Addressable)__x0, (java.lang.foreign.Addressable)__x1);
                } catch (Throwable ex$) {
                    throw new AssertionError("should not reach here", ex$);
                }
            };
        }
    }

    static final VarHandle usage$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("usage"));
    public static VarHandle usage$VH() {
        return _VipsOperationClass.usage$VH;
    }
    public static MemoryAddress usage$get(MemorySegment seg) {
        return (java.lang.foreign.MemoryAddress)_VipsOperationClass.usage$VH.get(seg);
    }
    public static void usage$set( MemorySegment seg, MemoryAddress x) {
        _VipsOperationClass.usage$VH.set(seg, x);
    }
    public static MemoryAddress usage$get(MemorySegment seg, long index) {
        return (java.lang.foreign.MemoryAddress)_VipsOperationClass.usage$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void usage$set(MemorySegment seg, long index, MemoryAddress x) {
        _VipsOperationClass.usage$VH.set(seg.asSlice(index*sizeof()), x);
    }
    public static usage usage (MemorySegment segment, MemorySession session) {
        return usage.ofAddress(usage$get(segment), session);
    }
    static final FunctionDescriptor get_flags$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle get_flags$MH = RuntimeHelper.downcallHandle(
        _VipsOperationClass.get_flags$FUNC
    );
    public interface get_flags {

        int apply(java.lang.foreign.MemoryAddress _x0);
        static MemorySegment allocate(get_flags fi, MemorySession session) {
            return RuntimeHelper.upcallStub(get_flags.class, fi, _VipsOperationClass.get_flags$FUNC, session);
        }
        static get_flags ofAddress(MemoryAddress addr, MemorySession session) {
            MemorySegment symbol = MemorySegment.ofAddress(addr, 0, session);
            return (java.lang.foreign.MemoryAddress __x0) -> {
                try {
                    return (int)_VipsOperationClass.get_flags$MH.invokeExact((Addressable)symbol, (java.lang.foreign.Addressable)__x0);
                } catch (Throwable ex$) {
                    throw new AssertionError("should not reach here", ex$);
                }
            };
        }
    }

    static final VarHandle get_flags$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("get_flags"));
    public static VarHandle get_flags$VH() {
        return _VipsOperationClass.get_flags$VH;
    }
    public static MemoryAddress get_flags$get(MemorySegment seg) {
        return (java.lang.foreign.MemoryAddress)_VipsOperationClass.get_flags$VH.get(seg);
    }
    public static void get_flags$set( MemorySegment seg, MemoryAddress x) {
        _VipsOperationClass.get_flags$VH.set(seg, x);
    }
    public static MemoryAddress get_flags$get(MemorySegment seg, long index) {
        return (java.lang.foreign.MemoryAddress)_VipsOperationClass.get_flags$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void get_flags$set(MemorySegment seg, long index, MemoryAddress x) {
        _VipsOperationClass.get_flags$VH.set(seg.asSlice(index*sizeof()), x);
    }
    public static get_flags get_flags (MemorySegment segment, MemorySession session) {
        return get_flags.ofAddress(get_flags$get(segment), session);
    }
    static final VarHandle flags$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("flags"));
    public static VarHandle flags$VH() {
        return _VipsOperationClass.flags$VH;
    }
    public static int flags$get(MemorySegment seg) {
        return (int)_VipsOperationClass.flags$VH.get(seg);
    }
    public static void flags$set( MemorySegment seg, int x) {
        _VipsOperationClass.flags$VH.set(seg, x);
    }
    public static int flags$get(MemorySegment seg, long index) {
        return (int)_VipsOperationClass.flags$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void flags$set(MemorySegment seg, long index, int x) {
        _VipsOperationClass.flags$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final FunctionDescriptor invalidate$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle invalidate$MH = RuntimeHelper.downcallHandle(
        _VipsOperationClass.invalidate$FUNC
    );
    public interface invalidate {

        void apply(java.lang.foreign.MemoryAddress _x0);
        static MemorySegment allocate(invalidate fi, MemorySession session) {
            return RuntimeHelper.upcallStub(invalidate.class, fi, _VipsOperationClass.invalidate$FUNC, session);
        }
        static invalidate ofAddress(MemoryAddress addr, MemorySession session) {
            MemorySegment symbol = MemorySegment.ofAddress(addr, 0, session);
            return (java.lang.foreign.MemoryAddress __x0) -> {
                try {
                    _VipsOperationClass.invalidate$MH.invokeExact((Addressable)symbol, (java.lang.foreign.Addressable)__x0);
                } catch (Throwable ex$) {
                    throw new AssertionError("should not reach here", ex$);
                }
            };
        }
    }

    static final VarHandle invalidate$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("invalidate"));
    public static VarHandle invalidate$VH() {
        return _VipsOperationClass.invalidate$VH;
    }
    public static MemoryAddress invalidate$get(MemorySegment seg) {
        return (java.lang.foreign.MemoryAddress)_VipsOperationClass.invalidate$VH.get(seg);
    }
    public static void invalidate$set( MemorySegment seg, MemoryAddress x) {
        _VipsOperationClass.invalidate$VH.set(seg, x);
    }
    public static MemoryAddress invalidate$get(MemorySegment seg, long index) {
        return (java.lang.foreign.MemoryAddress)_VipsOperationClass.invalidate$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void invalidate$set(MemorySegment seg, long index, MemoryAddress x) {
        _VipsOperationClass.invalidate$VH.set(seg.asSlice(index*sizeof()), x);
    }
    public static invalidate invalidate (MemorySegment segment, MemorySession session) {
        return invalidate.ofAddress(invalidate$get(segment), session);
    }
    public static long sizeof() { return $LAYOUT().byteSize(); }
    public static MemorySegment allocate(SegmentAllocator allocator) { return allocator.allocate($LAYOUT()); }
    public static MemorySegment allocateArray(int len, SegmentAllocator allocator) {
        return allocator.allocate(MemoryLayout.sequenceLayout(len, $LAYOUT()));
    }
    public static MemorySegment ofAddress(MemoryAddress addr, MemorySession session) { return RuntimeHelper.asArray(addr, $LAYOUT(), 1, session); }
}

