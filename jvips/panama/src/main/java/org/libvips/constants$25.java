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
class constants$25 {

    static final FunctionDescriptor g_signal_override_class_handler$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_LONG_LONG$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle g_signal_override_class_handler$MH = RuntimeHelper.downcallHandle(
        "g_signal_override_class_handler",
        constants$25.g_signal_override_class_handler$FUNC
    );
    static final FunctionDescriptor g_signal_chain_from_overridden$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle g_signal_chain_from_overridden$MH = RuntimeHelper.downcallHandle(
        "g_signal_chain_from_overridden",
        constants$25.g_signal_chain_from_overridden$FUNC
    );
    static final FunctionDescriptor g_signal_chain_from_overridden_handler$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle g_signal_chain_from_overridden_handler$MH = RuntimeHelper.downcallHandleVariadic(
        "g_signal_chain_from_overridden_handler",
        constants$25.g_signal_chain_from_overridden_handler$FUNC
    );
    static final FunctionDescriptor g_signal_accumulator_true_handled$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle g_signal_accumulator_true_handled$MH = RuntimeHelper.downcallHandle(
        "g_signal_accumulator_true_handled",
        constants$25.g_signal_accumulator_true_handled$FUNC
    );
    static final FunctionDescriptor g_signal_accumulator_first_wins$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle g_signal_accumulator_first_wins$MH = RuntimeHelper.downcallHandle(
        "g_signal_accumulator_first_wins",
        constants$25.g_signal_accumulator_first_wins$FUNC
    );
    static final FunctionDescriptor g_signal_handlers_destroy$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle g_signal_handlers_destroy$MH = RuntimeHelper.downcallHandle(
        "g_signal_handlers_destroy",
        constants$25.g_signal_handlers_destroy$FUNC
    );
}


