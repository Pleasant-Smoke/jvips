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
class constants$62 {

    static final FunctionDescriptor vips_object_preclose$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_object_preclose$MH = RuntimeHelper.downcallHandle(
        "vips_object_preclose",
        constants$62.vips_object_preclose$FUNC
    );
    static final FunctionDescriptor vips_object_build$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_object_build$MH = RuntimeHelper.downcallHandle(
        "vips_object_build",
        constants$62.vips_object_build$FUNC
    );
    static final FunctionDescriptor vips_object_summary_class$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_object_summary_class$MH = RuntimeHelper.downcallHandle(
        "vips_object_summary_class",
        constants$62.vips_object_summary_class$FUNC
    );
    static final FunctionDescriptor vips_object_summary$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_object_summary$MH = RuntimeHelper.downcallHandle(
        "vips_object_summary",
        constants$62.vips_object_summary$FUNC
    );
    static final FunctionDescriptor vips_object_dump$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_object_dump$MH = RuntimeHelper.downcallHandle(
        "vips_object_dump",
        constants$62.vips_object_dump$FUNC
    );
    static final FunctionDescriptor vips_object_print_summary_class$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_object_print_summary_class$MH = RuntimeHelper.downcallHandle(
        "vips_object_print_summary_class",
        constants$62.vips_object_print_summary_class$FUNC
    );
}

