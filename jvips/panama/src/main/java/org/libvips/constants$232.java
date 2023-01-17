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
class constants$232 {

    static final FunctionDescriptor vips_identity$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_identity$MH = RuntimeHelper.downcallHandleVariadic(
        "vips_identity",
        constants$232.vips_identity$FUNC
    );
    static final FunctionDescriptor vips_buildlut$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_buildlut$MH = RuntimeHelper.downcallHandleVariadic(
        "vips_buildlut",
        constants$232.vips_buildlut$FUNC
    );
    static final FunctionDescriptor vips_invertlut$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_invertlut$MH = RuntimeHelper.downcallHandleVariadic(
        "vips_invertlut",
        constants$232.vips_invertlut$FUNC
    );
    static final FunctionDescriptor vips_tonelut$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_tonelut$MH = RuntimeHelper.downcallHandleVariadic(
        "vips_tonelut",
        constants$232.vips_tonelut$FUNC
    );
    static final FunctionDescriptor vips_mask_ideal$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_INT$LAYOUT,
        Constants$root.C_INT$LAYOUT,
        Constants$root.C_DOUBLE$LAYOUT
    );
    static final MethodHandle vips_mask_ideal$MH = RuntimeHelper.downcallHandleVariadic(
        "vips_mask_ideal",
        constants$232.vips_mask_ideal$FUNC
    );
    static final FunctionDescriptor vips_mask_ideal_ring$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_INT$LAYOUT,
        Constants$root.C_INT$LAYOUT,
        Constants$root.C_DOUBLE$LAYOUT,
        Constants$root.C_DOUBLE$LAYOUT
    );
    static final MethodHandle vips_mask_ideal_ring$MH = RuntimeHelper.downcallHandleVariadic(
        "vips_mask_ideal_ring",
        constants$232.vips_mask_ideal_ring$FUNC
    );
}

