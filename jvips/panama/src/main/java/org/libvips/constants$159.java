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
class constants$159 {

    static final FunctionDescriptor vips_csvsave_target$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_csvsave_target$MH = RuntimeHelper.downcallHandleVariadic(
        "vips_csvsave_target",
        constants$159.vips_csvsave_target$FUNC
    );
    static final FunctionDescriptor vips_matrixload$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_matrixload$MH = RuntimeHelper.downcallHandleVariadic(
        "vips_matrixload",
        constants$159.vips_matrixload$FUNC
    );
    static final FunctionDescriptor vips_matrixload_source$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_matrixload_source$MH = RuntimeHelper.downcallHandleVariadic(
        "vips_matrixload_source",
        constants$159.vips_matrixload_source$FUNC
    );
    static final FunctionDescriptor vips_matrixsave$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_matrixsave$MH = RuntimeHelper.downcallHandleVariadic(
        "vips_matrixsave",
        constants$159.vips_matrixsave$FUNC
    );
    static final FunctionDescriptor vips_matrixsave_target$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_matrixsave_target$MH = RuntimeHelper.downcallHandleVariadic(
        "vips_matrixsave_target",
        constants$159.vips_matrixsave_target$FUNC
    );
    static final FunctionDescriptor vips_matrixprint$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_matrixprint$MH = RuntimeHelper.downcallHandleVariadic(
        "vips_matrixprint",
        constants$159.vips_matrixprint$FUNC
    );
}

