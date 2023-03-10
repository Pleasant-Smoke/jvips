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
class constants$108 {

    static final FunctionDescriptor vips_image_new_from_memory_copy$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_LONG_LONG$LAYOUT,
        Constants$root.C_INT$LAYOUT,
        Constants$root.C_INT$LAYOUT,
        Constants$root.C_INT$LAYOUT,
        Constants$root.C_INT$LAYOUT
    );
    static final MethodHandle vips_image_new_from_memory_copy$MH = RuntimeHelper.downcallHandle(
        "vips_image_new_from_memory_copy",
        constants$108.vips_image_new_from_memory_copy$FUNC
    );
    static final FunctionDescriptor vips_image_new_from_buffer$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_LONG_LONG$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_image_new_from_buffer$MH = RuntimeHelper.downcallHandleVariadic(
        "vips_image_new_from_buffer",
        constants$108.vips_image_new_from_buffer$FUNC
    );
    static final FunctionDescriptor vips_image_new_from_source$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_image_new_from_source$MH = RuntimeHelper.downcallHandleVariadic(
        "vips_image_new_from_source",
        constants$108.vips_image_new_from_source$FUNC
    );
    static final FunctionDescriptor vips_image_new_matrix$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_INT$LAYOUT,
        Constants$root.C_INT$LAYOUT
    );
    static final MethodHandle vips_image_new_matrix$MH = RuntimeHelper.downcallHandle(
        "vips_image_new_matrix",
        constants$108.vips_image_new_matrix$FUNC
    );
    static final FunctionDescriptor vips_image_new_matrixv$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_INT$LAYOUT,
        Constants$root.C_INT$LAYOUT
    );
    static final MethodHandle vips_image_new_matrixv$MH = RuntimeHelper.downcallHandleVariadic(
        "vips_image_new_matrixv",
        constants$108.vips_image_new_matrixv$FUNC
    );
    static final FunctionDescriptor vips_image_new_matrix_from_array$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_INT$LAYOUT,
        Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_INT$LAYOUT
    );
    static final MethodHandle vips_image_new_matrix_from_array$MH = RuntimeHelper.downcallHandle(
        "vips_image_new_matrix_from_array",
        constants$108.vips_image_new_matrix_from_array$FUNC
    );
}


