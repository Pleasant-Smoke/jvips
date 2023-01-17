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
class constants$83 {

    static final FunctionDescriptor vips_g_input_stream_new_from_source$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_g_input_stream_new_from_source$MH = RuntimeHelper.downcallHandle(
        "vips_g_input_stream_new_from_source",
        constants$83.vips_g_input_stream_new_from_source$FUNC
    );
    static final FunctionDescriptor vips_source_g_input_stream_new$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_source_g_input_stream_new$MH = RuntimeHelper.downcallHandle(
        "vips_source_g_input_stream_new",
        constants$83.vips_source_g_input_stream_new$FUNC
    );
    static final FunctionDescriptor vips_target_get_type$FUNC = FunctionDescriptor.of(Constants$root.C_LONG_LONG$LAYOUT);
    static final MethodHandle vips_target_get_type$MH = RuntimeHelper.downcallHandle(
        "vips_target_get_type",
        constants$83.vips_target_get_type$FUNC
    );
    static final FunctionDescriptor vips_target_new_to_descriptor$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_INT$LAYOUT
    );
    static final MethodHandle vips_target_new_to_descriptor$MH = RuntimeHelper.downcallHandle(
        "vips_target_new_to_descriptor",
        constants$83.vips_target_new_to_descriptor$FUNC
    );
    static final FunctionDescriptor vips_target_new_to_file$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_target_new_to_file$MH = RuntimeHelper.downcallHandle(
        "vips_target_new_to_file",
        constants$83.vips_target_new_to_file$FUNC
    );
    static final FunctionDescriptor vips_target_new_to_memory$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT);
    static final MethodHandle vips_target_new_to_memory$MH = RuntimeHelper.downcallHandle(
        "vips_target_new_to_memory",
        constants$83.vips_target_new_to_memory$FUNC
    );
}

