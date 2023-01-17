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
class constants$224 {

    static final FunctionDescriptor vips_col_Lab2XYZ$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_FLOAT$LAYOUT,
        Constants$root.C_FLOAT$LAYOUT,
        Constants$root.C_FLOAT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_col_Lab2XYZ$MH = RuntimeHelper.downcallHandle(
        "vips_col_Lab2XYZ",
        constants$224.vips_col_Lab2XYZ$FUNC
    );
    static final FunctionDescriptor vips_col_XYZ2Lab$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_FLOAT$LAYOUT,
        Constants$root.C_FLOAT$LAYOUT,
        Constants$root.C_FLOAT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_col_XYZ2Lab$MH = RuntimeHelper.downcallHandle(
        "vips_col_XYZ2Lab",
        constants$224.vips_col_XYZ2Lab$FUNC
    );
    static final FunctionDescriptor vips_col_ab2h$FUNC = FunctionDescriptor.of(Constants$root.C_DOUBLE$LAYOUT,
        Constants$root.C_DOUBLE$LAYOUT,
        Constants$root.C_DOUBLE$LAYOUT
    );
    static final MethodHandle vips_col_ab2h$MH = RuntimeHelper.downcallHandle(
        "vips_col_ab2h",
        constants$224.vips_col_ab2h$FUNC
    );
    static final FunctionDescriptor vips_col_ab2Ch$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_FLOAT$LAYOUT,
        Constants$root.C_FLOAT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_col_ab2Ch$MH = RuntimeHelper.downcallHandle(
        "vips_col_ab2Ch",
        constants$224.vips_col_ab2Ch$FUNC
    );
    static final FunctionDescriptor vips_col_Ch2ab$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_FLOAT$LAYOUT,
        Constants$root.C_FLOAT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_col_Ch2ab$MH = RuntimeHelper.downcallHandle(
        "vips_col_Ch2ab",
        constants$224.vips_col_Ch2ab$FUNC
    );
    static final FunctionDescriptor vips_col_L2Lcmc$FUNC = FunctionDescriptor.of(Constants$root.C_FLOAT$LAYOUT,
        Constants$root.C_FLOAT$LAYOUT
    );
    static final MethodHandle vips_col_L2Lcmc$MH = RuntimeHelper.downcallHandle(
        "vips_col_L2Lcmc",
        constants$224.vips_col_L2Lcmc$FUNC
    );
}

