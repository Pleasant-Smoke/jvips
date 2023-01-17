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
class constants$80 {

    static final FunctionDescriptor vips_source_new_from_target$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_source_new_from_target$MH = RuntimeHelper.downcallHandle(
        "vips_source_new_from_target",
        constants$80.vips_source_new_from_target$FUNC
    );
    static final FunctionDescriptor vips_source_new_from_memory$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_LONG_LONG$LAYOUT
    );
    static final MethodHandle vips_source_new_from_memory$MH = RuntimeHelper.downcallHandle(
        "vips_source_new_from_memory",
        constants$80.vips_source_new_from_memory$FUNC
    );
    static final FunctionDescriptor vips_source_new_from_options$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_source_new_from_options$MH = RuntimeHelper.downcallHandle(
        "vips_source_new_from_options",
        constants$80.vips_source_new_from_options$FUNC
    );
    static final FunctionDescriptor vips_source_minimise$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_source_minimise$MH = RuntimeHelper.downcallHandle(
        "vips_source_minimise",
        constants$80.vips_source_minimise$FUNC
    );
    static final FunctionDescriptor vips_source_unminimise$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_source_unminimise$MH = RuntimeHelper.downcallHandle(
        "vips_source_unminimise",
        constants$80.vips_source_unminimise$FUNC
    );
    static final FunctionDescriptor vips_source_decode$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_source_decode$MH = RuntimeHelper.downcallHandle(
        "vips_source_decode",
        constants$80.vips_source_decode$FUNC
    );
}

