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
class constants$20 {

    static final FunctionDescriptor g_signal_set_va_marshaller$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_INT$LAYOUT,
        Constants$root.C_LONG_LONG$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle g_signal_set_va_marshaller$MH = RuntimeHelper.downcallHandle(
        "g_signal_set_va_marshaller",
        constants$20.g_signal_set_va_marshaller$FUNC
    );
    static final FunctionDescriptor g_signal_emitv$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_INT$LAYOUT,
        Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle g_signal_emitv$MH = RuntimeHelper.downcallHandle(
        "g_signal_emitv",
        constants$20.g_signal_emitv$FUNC
    );
    static final FunctionDescriptor g_signal_emit_valist$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_INT$LAYOUT,
        Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle g_signal_emit_valist$MH = RuntimeHelper.downcallHandle(
        "g_signal_emit_valist",
        constants$20.g_signal_emit_valist$FUNC
    );
    static final FunctionDescriptor g_signal_emit$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_INT$LAYOUT,
        Constants$root.C_INT$LAYOUT
    );
    static final MethodHandle g_signal_emit$MH = RuntimeHelper.downcallHandleVariadic(
        "g_signal_emit",
        constants$20.g_signal_emit$FUNC
    );
    static final FunctionDescriptor g_signal_emit_by_name$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle g_signal_emit_by_name$MH = RuntimeHelper.downcallHandleVariadic(
        "g_signal_emit_by_name",
        constants$20.g_signal_emit_by_name$FUNC
    );
    static final FunctionDescriptor g_signal_lookup$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_LONG_LONG$LAYOUT
    );
    static final MethodHandle g_signal_lookup$MH = RuntimeHelper.downcallHandle(
        "g_signal_lookup",
        constants$20.g_signal_lookup$FUNC
    );
}


