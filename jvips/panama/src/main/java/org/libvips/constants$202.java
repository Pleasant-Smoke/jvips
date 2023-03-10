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
class constants$202 {

    static final FunctionDescriptor vips_cast_short$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_cast_short$MH = RuntimeHelper.downcallHandleVariadic(
        "vips_cast_short",
        constants$202.vips_cast_short$FUNC
    );
    static final FunctionDescriptor vips_cast_uint$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_cast_uint$MH = RuntimeHelper.downcallHandleVariadic(
        "vips_cast_uint",
        constants$202.vips_cast_uint$FUNC
    );
    static final FunctionDescriptor vips_cast_int$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_cast_int$MH = RuntimeHelper.downcallHandleVariadic(
        "vips_cast_int",
        constants$202.vips_cast_int$FUNC
    );
    static final FunctionDescriptor vips_cast_float$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_cast_float$MH = RuntimeHelper.downcallHandleVariadic(
        "vips_cast_float",
        constants$202.vips_cast_float$FUNC
    );
    static final FunctionDescriptor vips_cast_double$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_cast_double$MH = RuntimeHelper.downcallHandleVariadic(
        "vips_cast_double",
        constants$202.vips_cast_double$FUNC
    );
    static final FunctionDescriptor vips_cast_complex$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_cast_complex$MH = RuntimeHelper.downcallHandleVariadic(
        "vips_cast_complex",
        constants$202.vips_cast_complex$FUNC
    );
}


