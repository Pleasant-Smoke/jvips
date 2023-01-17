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
class constants$64 {

    static final FunctionDescriptor vips_object_set_argument_from_string$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_object_set_argument_from_string$MH = RuntimeHelper.downcallHandle(
        "vips_object_set_argument_from_string",
        constants$64.vips_object_set_argument_from_string$FUNC
    );
    static final FunctionDescriptor vips_object_argument_needsstring$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_object_argument_needsstring$MH = RuntimeHelper.downcallHandle(
        "vips_object_argument_needsstring",
        constants$64.vips_object_argument_needsstring$FUNC
    );
    static final FunctionDescriptor vips_object_get_argument_to_string$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_object_get_argument_to_string$MH = RuntimeHelper.downcallHandle(
        "vips_object_get_argument_to_string",
        constants$64.vips_object_get_argument_to_string$FUNC
    );
    static final FunctionDescriptor vips_object_set_required$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_object_set_required$MH = RuntimeHelper.downcallHandle(
        "vips_object_set_required",
        constants$64.vips_object_set_required$FUNC
    );
    static final FunctionDescriptor VipsObjectSetArguments$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle VipsObjectSetArguments$MH = RuntimeHelper.downcallHandle(
        constants$64.VipsObjectSetArguments$FUNC
    );
}

