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
class constants$12 {

    static final FunctionDescriptor g_type_interface_add_prerequisite$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_LONG_LONG$LAYOUT,
        Constants$root.C_LONG_LONG$LAYOUT
    );
    static final MethodHandle g_type_interface_add_prerequisite$MH = RuntimeHelper.downcallHandle(
        "g_type_interface_add_prerequisite",
        constants$12.g_type_interface_add_prerequisite$FUNC
    );
    static final FunctionDescriptor g_type_interface_prerequisites$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_LONG_LONG$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle g_type_interface_prerequisites$MH = RuntimeHelper.downcallHandle(
        "g_type_interface_prerequisites",
        constants$12.g_type_interface_prerequisites$FUNC
    );
    static final FunctionDescriptor g_type_interface_instantiatable_prerequisite$FUNC = FunctionDescriptor.of(Constants$root.C_LONG_LONG$LAYOUT,
        Constants$root.C_LONG_LONG$LAYOUT
    );
    static final MethodHandle g_type_interface_instantiatable_prerequisite$MH = RuntimeHelper.downcallHandle(
        "g_type_interface_instantiatable_prerequisite",
        constants$12.g_type_interface_instantiatable_prerequisite$FUNC
    );
    static final FunctionDescriptor g_type_class_add_private$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_LONG_LONG$LAYOUT
    );
    static final MethodHandle g_type_class_add_private$MH = RuntimeHelper.downcallHandle(
        "g_type_class_add_private",
        constants$12.g_type_class_add_private$FUNC
    );
    static final FunctionDescriptor g_type_add_instance_private$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_LONG_LONG$LAYOUT,
        Constants$root.C_LONG_LONG$LAYOUT
    );
    static final MethodHandle g_type_add_instance_private$MH = RuntimeHelper.downcallHandle(
        "g_type_add_instance_private",
        constants$12.g_type_add_instance_private$FUNC
    );
    static final FunctionDescriptor g_type_instance_get_private$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_LONG_LONG$LAYOUT
    );
    static final MethodHandle g_type_instance_get_private$MH = RuntimeHelper.downcallHandle(
        "g_type_instance_get_private",
        constants$12.g_type_instance_get_private$FUNC
    );
}


