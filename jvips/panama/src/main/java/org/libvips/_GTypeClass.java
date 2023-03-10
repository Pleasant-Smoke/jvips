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
public class _GTypeClass {

    static final  GroupLayout $struct$LAYOUT = MemoryLayout.structLayout(
        Constants$root.C_LONG_LONG$LAYOUT.withName("g_type")
    ).withName("_GTypeClass");
    public static MemoryLayout $LAYOUT() {
        return _GTypeClass.$struct$LAYOUT;
    }
    static final VarHandle g_type$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("g_type"));
    public static VarHandle g_type$VH() {
        return _GTypeClass.g_type$VH;
    }
    public static long g_type$get(MemorySegment seg) {
        return (long)_GTypeClass.g_type$VH.get(seg);
    }
    public static void g_type$set( MemorySegment seg, long x) {
        _GTypeClass.g_type$VH.set(seg, x);
    }
    public static long g_type$get(MemorySegment seg, long index) {
        return (long)_GTypeClass.g_type$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void g_type$set(MemorySegment seg, long index, long x) {
        _GTypeClass.g_type$VH.set(seg.asSlice(index*sizeof()), x);
    }
    public static long sizeof() { return $LAYOUT().byteSize(); }
    public static MemorySegment allocate(SegmentAllocator allocator) { return allocator.allocate($LAYOUT()); }
    public static MemorySegment allocateArray(int len, SegmentAllocator allocator) {
        return allocator.allocate(MemoryLayout.sequenceLayout(len, $LAYOUT()));
    }
    public static MemorySegment ofAddress(MemoryAddress addr, MemorySession session) { return RuntimeHelper.asArray(addr, $LAYOUT(), 1, session); }
}


