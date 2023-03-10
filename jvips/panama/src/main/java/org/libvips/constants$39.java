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
class constants$39 {

    static final FunctionDescriptor g_clear_weak_pointer$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle g_clear_weak_pointer$MH = RuntimeHelper.downcallHandle(
        "g_clear_weak_pointer",
        constants$39.g_clear_weak_pointer$FUNC
    );
    static final FunctionDescriptor g_set_weak_pointer$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle g_set_weak_pointer$MH = RuntimeHelper.downcallHandle(
        "g_set_weak_pointer",
        constants$39.g_set_weak_pointer$FUNC
    );
    static final FunctionDescriptor g_weak_ref_init$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle g_weak_ref_init$MH = RuntimeHelper.downcallHandle(
        "g_weak_ref_init",
        constants$39.g_weak_ref_init$FUNC
    );
    static final FunctionDescriptor g_weak_ref_clear$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle g_weak_ref_clear$MH = RuntimeHelper.downcallHandle(
        "g_weak_ref_clear",
        constants$39.g_weak_ref_clear$FUNC
    );
    static final FunctionDescriptor g_weak_ref_get$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle g_weak_ref_get$MH = RuntimeHelper.downcallHandle(
        "g_weak_ref_get",
        constants$39.g_weak_ref_get$FUNC
    );
    static final FunctionDescriptor g_weak_ref_set$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle g_weak_ref_set$MH = RuntimeHelper.downcallHandle(
        "g_weak_ref_set",
        constants$39.g_weak_ref_set$FUNC
    );
}


