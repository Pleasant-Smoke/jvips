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
public class _VipsDbuf {

    static final  GroupLayout $struct$LAYOUT = MemoryLayout.structLayout(
        Constants$root.C_POINTER$LAYOUT.withName("data"),
        Constants$root.C_LONG_LONG$LAYOUT.withName("allocated_size"),
        Constants$root.C_LONG_LONG$LAYOUT.withName("data_size"),
        Constants$root.C_LONG_LONG$LAYOUT.withName("write_point")
    ).withName("_VipsDbuf");
    public static MemoryLayout $LAYOUT() {
        return _VipsDbuf.$struct$LAYOUT;
    }
    static final VarHandle data$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("data"));
    public static VarHandle data$VH() {
        return _VipsDbuf.data$VH;
    }
    public static MemoryAddress data$get(MemorySegment seg) {
        return (java.lang.foreign.MemoryAddress)_VipsDbuf.data$VH.get(seg);
    }
    public static void data$set( MemorySegment seg, MemoryAddress x) {
        _VipsDbuf.data$VH.set(seg, x);
    }
    public static MemoryAddress data$get(MemorySegment seg, long index) {
        return (java.lang.foreign.MemoryAddress)_VipsDbuf.data$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void data$set(MemorySegment seg, long index, MemoryAddress x) {
        _VipsDbuf.data$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle allocated_size$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("allocated_size"));
    public static VarHandle allocated_size$VH() {
        return _VipsDbuf.allocated_size$VH;
    }
    public static long allocated_size$get(MemorySegment seg) {
        return (long)_VipsDbuf.allocated_size$VH.get(seg);
    }
    public static void allocated_size$set( MemorySegment seg, long x) {
        _VipsDbuf.allocated_size$VH.set(seg, x);
    }
    public static long allocated_size$get(MemorySegment seg, long index) {
        return (long)_VipsDbuf.allocated_size$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void allocated_size$set(MemorySegment seg, long index, long x) {
        _VipsDbuf.allocated_size$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle data_size$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("data_size"));
    public static VarHandle data_size$VH() {
        return _VipsDbuf.data_size$VH;
    }
    public static long data_size$get(MemorySegment seg) {
        return (long)_VipsDbuf.data_size$VH.get(seg);
    }
    public static void data_size$set( MemorySegment seg, long x) {
        _VipsDbuf.data_size$VH.set(seg, x);
    }
    public static long data_size$get(MemorySegment seg, long index) {
        return (long)_VipsDbuf.data_size$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void data_size$set(MemorySegment seg, long index, long x) {
        _VipsDbuf.data_size$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle write_point$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("write_point"));
    public static VarHandle write_point$VH() {
        return _VipsDbuf.write_point$VH;
    }
    public static long write_point$get(MemorySegment seg) {
        return (long)_VipsDbuf.write_point$VH.get(seg);
    }
    public static void write_point$set( MemorySegment seg, long x) {
        _VipsDbuf.write_point$VH.set(seg, x);
    }
    public static long write_point$get(MemorySegment seg, long index) {
        return (long)_VipsDbuf.write_point$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void write_point$set(MemorySegment seg, long index, long x) {
        _VipsDbuf.write_point$VH.set(seg.asSlice(index*sizeof()), x);
    }
    public static long sizeof() { return $LAYOUT().byteSize(); }
    public static MemorySegment allocate(SegmentAllocator allocator) { return allocator.allocate($LAYOUT()); }
    public static MemorySegment allocateArray(int len, SegmentAllocator allocator) {
        return allocator.allocate(MemoryLayout.sequenceLayout(len, $LAYOUT()));
    }
    public static MemorySegment ofAddress(MemoryAddress addr, MemorySession session) { return RuntimeHelper.asArray(addr, $LAYOUT(), 1, session); }
}


