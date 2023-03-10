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
class constants$23 {

    static final FunctionDescriptor g_signal_connect_closure$FUNC = FunctionDescriptor.of(Constants$root.C_LONG_LONG$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_INT$LAYOUT
    );
    static final MethodHandle g_signal_connect_closure$MH = RuntimeHelper.downcallHandle(
        "g_signal_connect_closure",
        constants$23.g_signal_connect_closure$FUNC
    );
    static final FunctionDescriptor g_signal_connect_data$FUNC = FunctionDescriptor.of(Constants$root.C_LONG_LONG$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_INT$LAYOUT
    );
    static final MethodHandle g_signal_connect_data$MH = RuntimeHelper.downcallHandle(
        "g_signal_connect_data",
        constants$23.g_signal_connect_data$FUNC
    );
    static final FunctionDescriptor g_signal_handler_block$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_LONG_LONG$LAYOUT
    );
    static final MethodHandle g_signal_handler_block$MH = RuntimeHelper.downcallHandle(
        "g_signal_handler_block",
        constants$23.g_signal_handler_block$FUNC
    );
    static final FunctionDescriptor g_signal_handler_unblock$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_LONG_LONG$LAYOUT
    );
    static final MethodHandle g_signal_handler_unblock$MH = RuntimeHelper.downcallHandle(
        "g_signal_handler_unblock",
        constants$23.g_signal_handler_unblock$FUNC
    );
    static final FunctionDescriptor g_signal_handler_disconnect$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_LONG_LONG$LAYOUT
    );
    static final MethodHandle g_signal_handler_disconnect$MH = RuntimeHelper.downcallHandle(
        "g_signal_handler_disconnect",
        constants$23.g_signal_handler_disconnect$FUNC
    );
    static final FunctionDescriptor g_signal_handler_is_connected$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_LONG_LONG$LAYOUT
    );
    static final MethodHandle g_signal_handler_is_connected$MH = RuntimeHelper.downcallHandle(
        "g_signal_handler_is_connected",
        constants$23.g_signal_handler_is_connected$FUNC
    );
}


