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
public class _VipsProgress {

    static final  GroupLayout $struct$LAYOUT = MemoryLayout.structLayout(
        Constants$root.C_POINTER$LAYOUT.withName("im"),
        Constants$root.C_INT$LAYOUT.withName("run"),
        Constants$root.C_INT$LAYOUT.withName("eta"),
        Constants$root.C_LONG_LONG$LAYOUT.withName("tpels"),
        Constants$root.C_LONG_LONG$LAYOUT.withName("npels"),
        Constants$root.C_INT$LAYOUT.withName("percent"),
        MemoryLayout.paddingLayout(32),
        Constants$root.C_POINTER$LAYOUT.withName("start")
    ).withName("_VipsProgress");
    public static MemoryLayout $LAYOUT() {
        return _VipsProgress.$struct$LAYOUT;
    }
    static final VarHandle im$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("im"));
    public static VarHandle im$VH() {
        return _VipsProgress.im$VH;
    }
    public static MemoryAddress im$get(MemorySegment seg) {
        return (java.lang.foreign.MemoryAddress)_VipsProgress.im$VH.get(seg);
    }
    public static void im$set( MemorySegment seg, MemoryAddress x) {
        _VipsProgress.im$VH.set(seg, x);
    }
    public static MemoryAddress im$get(MemorySegment seg, long index) {
        return (java.lang.foreign.MemoryAddress)_VipsProgress.im$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void im$set(MemorySegment seg, long index, MemoryAddress x) {
        _VipsProgress.im$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle run$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("run"));
    public static VarHandle run$VH() {
        return _VipsProgress.run$VH;
    }
    public static int run$get(MemorySegment seg) {
        return (int)_VipsProgress.run$VH.get(seg);
    }
    public static void run$set( MemorySegment seg, int x) {
        _VipsProgress.run$VH.set(seg, x);
    }
    public static int run$get(MemorySegment seg, long index) {
        return (int)_VipsProgress.run$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void run$set(MemorySegment seg, long index, int x) {
        _VipsProgress.run$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle eta$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("eta"));
    public static VarHandle eta$VH() {
        return _VipsProgress.eta$VH;
    }
    public static int eta$get(MemorySegment seg) {
        return (int)_VipsProgress.eta$VH.get(seg);
    }
    public static void eta$set( MemorySegment seg, int x) {
        _VipsProgress.eta$VH.set(seg, x);
    }
    public static int eta$get(MemorySegment seg, long index) {
        return (int)_VipsProgress.eta$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void eta$set(MemorySegment seg, long index, int x) {
        _VipsProgress.eta$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle tpels$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("tpels"));
    public static VarHandle tpels$VH() {
        return _VipsProgress.tpels$VH;
    }
    public static long tpels$get(MemorySegment seg) {
        return (long)_VipsProgress.tpels$VH.get(seg);
    }
    public static void tpels$set( MemorySegment seg, long x) {
        _VipsProgress.tpels$VH.set(seg, x);
    }
    public static long tpels$get(MemorySegment seg, long index) {
        return (long)_VipsProgress.tpels$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void tpels$set(MemorySegment seg, long index, long x) {
        _VipsProgress.tpels$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle npels$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("npels"));
    public static VarHandle npels$VH() {
        return _VipsProgress.npels$VH;
    }
    public static long npels$get(MemorySegment seg) {
        return (long)_VipsProgress.npels$VH.get(seg);
    }
    public static void npels$set( MemorySegment seg, long x) {
        _VipsProgress.npels$VH.set(seg, x);
    }
    public static long npels$get(MemorySegment seg, long index) {
        return (long)_VipsProgress.npels$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void npels$set(MemorySegment seg, long index, long x) {
        _VipsProgress.npels$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle percent$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("percent"));
    public static VarHandle percent$VH() {
        return _VipsProgress.percent$VH;
    }
    public static int percent$get(MemorySegment seg) {
        return (int)_VipsProgress.percent$VH.get(seg);
    }
    public static void percent$set( MemorySegment seg, int x) {
        _VipsProgress.percent$VH.set(seg, x);
    }
    public static int percent$get(MemorySegment seg, long index) {
        return (int)_VipsProgress.percent$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void percent$set(MemorySegment seg, long index, int x) {
        _VipsProgress.percent$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle start$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("start"));
    public static VarHandle start$VH() {
        return _VipsProgress.start$VH;
    }
    public static MemoryAddress start$get(MemorySegment seg) {
        return (java.lang.foreign.MemoryAddress)_VipsProgress.start$VH.get(seg);
    }
    public static void start$set( MemorySegment seg, MemoryAddress x) {
        _VipsProgress.start$VH.set(seg, x);
    }
    public static MemoryAddress start$get(MemorySegment seg, long index) {
        return (java.lang.foreign.MemoryAddress)_VipsProgress.start$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void start$set(MemorySegment seg, long index, MemoryAddress x) {
        _VipsProgress.start$VH.set(seg.asSlice(index*sizeof()), x);
    }
    public static long sizeof() { return $LAYOUT().byteSize(); }
    public static MemorySegment allocate(SegmentAllocator allocator) { return allocator.allocate($LAYOUT()); }
    public static MemorySegment allocateArray(int len, SegmentAllocator allocator) {
        return allocator.allocate(MemoryLayout.sequenceLayout(len, $LAYOUT()));
    }
    public static MemorySegment ofAddress(MemoryAddress addr, MemorySession session) { return RuntimeHelper.asArray(addr, $LAYOUT(), 1, session); }
}


