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
class constants$70 {

    static final FunctionDescriptor vips_area_copy$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_area_copy$MH = RuntimeHelper.downcallHandle(
        "vips_area_copy",
        constants$70.vips_area_copy$FUNC
    );
    static final FunctionDescriptor vips_area_free_cb$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_area_free_cb$MH = RuntimeHelper.downcallHandle(
        "vips_area_free_cb",
        constants$70.vips_area_free_cb$FUNC
    );
    static final FunctionDescriptor vips_area_unref$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_area_unref$MH = RuntimeHelper.downcallHandle(
        "vips_area_unref",
        constants$70.vips_area_unref$FUNC
    );
    static final FunctionDescriptor vips_area_new$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_area_new$MH = RuntimeHelper.downcallHandle(
        "vips_area_new",
        constants$70.vips_area_new$FUNC
    );
    static final FunctionDescriptor vips_area_new_array$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_LONG_LONG$LAYOUT,
        Constants$root.C_LONG_LONG$LAYOUT,
        Constants$root.C_INT$LAYOUT
    );
    static final MethodHandle vips_area_new_array$MH = RuntimeHelper.downcallHandle(
        "vips_area_new_array",
        constants$70.vips_area_new_array$FUNC
    );
    static final FunctionDescriptor vips_area_new_array_object$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_INT$LAYOUT
    );
    static final MethodHandle vips_area_new_array_object$MH = RuntimeHelper.downcallHandle(
        "vips_area_new_array_object",
        constants$70.vips_area_new_array_object$FUNC
    );
}


