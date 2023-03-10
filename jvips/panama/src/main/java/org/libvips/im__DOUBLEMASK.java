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
public class im__DOUBLEMASK {

    static final  GroupLayout $struct$LAYOUT = MemoryLayout.structLayout(
        Constants$root.C_INT$LAYOUT.withName("xsize"),
        Constants$root.C_INT$LAYOUT.withName("ysize"),
        Constants$root.C_DOUBLE$LAYOUT.withName("scale"),
        Constants$root.C_DOUBLE$LAYOUT.withName("offset"),
        Constants$root.C_POINTER$LAYOUT.withName("coeff"),
        Constants$root.C_POINTER$LAYOUT.withName("filename")
    ).withName("im__DOUBLEMASK");
    public static MemoryLayout $LAYOUT() {
        return im__DOUBLEMASK.$struct$LAYOUT;
    }
    static final VarHandle xsize$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("xsize"));
    public static VarHandle xsize$VH() {
        return im__DOUBLEMASK.xsize$VH;
    }
    public static int xsize$get(MemorySegment seg) {
        return (int)im__DOUBLEMASK.xsize$VH.get(seg);
    }
    public static void xsize$set( MemorySegment seg, int x) {
        im__DOUBLEMASK.xsize$VH.set(seg, x);
    }
    public static int xsize$get(MemorySegment seg, long index) {
        return (int)im__DOUBLEMASK.xsize$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void xsize$set(MemorySegment seg, long index, int x) {
        im__DOUBLEMASK.xsize$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle ysize$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("ysize"));
    public static VarHandle ysize$VH() {
        return im__DOUBLEMASK.ysize$VH;
    }
    public static int ysize$get(MemorySegment seg) {
        return (int)im__DOUBLEMASK.ysize$VH.get(seg);
    }
    public static void ysize$set( MemorySegment seg, int x) {
        im__DOUBLEMASK.ysize$VH.set(seg, x);
    }
    public static int ysize$get(MemorySegment seg, long index) {
        return (int)im__DOUBLEMASK.ysize$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void ysize$set(MemorySegment seg, long index, int x) {
        im__DOUBLEMASK.ysize$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle scale$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("scale"));
    public static VarHandle scale$VH() {
        return im__DOUBLEMASK.scale$VH;
    }
    public static double scale$get(MemorySegment seg) {
        return (double)im__DOUBLEMASK.scale$VH.get(seg);
    }
    public static void scale$set( MemorySegment seg, double x) {
        im__DOUBLEMASK.scale$VH.set(seg, x);
    }
    public static double scale$get(MemorySegment seg, long index) {
        return (double)im__DOUBLEMASK.scale$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void scale$set(MemorySegment seg, long index, double x) {
        im__DOUBLEMASK.scale$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle offset$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("offset"));
    public static VarHandle offset$VH() {
        return im__DOUBLEMASK.offset$VH;
    }
    public static double offset$get(MemorySegment seg) {
        return (double)im__DOUBLEMASK.offset$VH.get(seg);
    }
    public static void offset$set( MemorySegment seg, double x) {
        im__DOUBLEMASK.offset$VH.set(seg, x);
    }
    public static double offset$get(MemorySegment seg, long index) {
        return (double)im__DOUBLEMASK.offset$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void offset$set(MemorySegment seg, long index, double x) {
        im__DOUBLEMASK.offset$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle coeff$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("coeff"));
    public static VarHandle coeff$VH() {
        return im__DOUBLEMASK.coeff$VH;
    }
    public static MemoryAddress coeff$get(MemorySegment seg) {
        return (java.lang.foreign.MemoryAddress)im__DOUBLEMASK.coeff$VH.get(seg);
    }
    public static void coeff$set( MemorySegment seg, MemoryAddress x) {
        im__DOUBLEMASK.coeff$VH.set(seg, x);
    }
    public static MemoryAddress coeff$get(MemorySegment seg, long index) {
        return (java.lang.foreign.MemoryAddress)im__DOUBLEMASK.coeff$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void coeff$set(MemorySegment seg, long index, MemoryAddress x) {
        im__DOUBLEMASK.coeff$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle filename$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("filename"));
    public static VarHandle filename$VH() {
        return im__DOUBLEMASK.filename$VH;
    }
    public static MemoryAddress filename$get(MemorySegment seg) {
        return (java.lang.foreign.MemoryAddress)im__DOUBLEMASK.filename$VH.get(seg);
    }
    public static void filename$set( MemorySegment seg, MemoryAddress x) {
        im__DOUBLEMASK.filename$VH.set(seg, x);
    }
    public static MemoryAddress filename$get(MemorySegment seg, long index) {
        return (java.lang.foreign.MemoryAddress)im__DOUBLEMASK.filename$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void filename$set(MemorySegment seg, long index, MemoryAddress x) {
        im__DOUBLEMASK.filename$VH.set(seg.asSlice(index*sizeof()), x);
    }
    public static long sizeof() { return $LAYOUT().byteSize(); }
    public static MemorySegment allocate(SegmentAllocator allocator) { return allocator.allocate($LAYOUT()); }
    public static MemorySegment allocateArray(int len, SegmentAllocator allocator) {
        return allocator.allocate(MemoryLayout.sequenceLayout(len, $LAYOUT()));
    }
    public static MemorySegment ofAddress(MemoryAddress addr, MemorySession session) { return RuntimeHelper.asArray(addr, $LAYOUT(), 1, session); }
}


