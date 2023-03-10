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
class constants$84 {

    static final FunctionDescriptor vips_target_new_temp$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_target_new_temp$MH = RuntimeHelper.downcallHandle(
        "vips_target_new_temp",
        constants$84.vips_target_new_temp$FUNC
    );
    static final FunctionDescriptor vips_target_write$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_LONG_LONG$LAYOUT
    );
    static final MethodHandle vips_target_write$MH = RuntimeHelper.downcallHandle(
        "vips_target_write",
        constants$84.vips_target_write$FUNC
    );
    static final FunctionDescriptor vips_target_read$FUNC = FunctionDescriptor.of(Constants$root.C_LONG_LONG$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_LONG_LONG$LAYOUT
    );
    static final MethodHandle vips_target_read$MH = RuntimeHelper.downcallHandle(
        "vips_target_read",
        constants$84.vips_target_read$FUNC
    );
    static final FunctionDescriptor vips_target_seek$FUNC = FunctionDescriptor.of(Constants$root.C_LONG_LONG$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_LONG_LONG$LAYOUT,
        Constants$root.C_INT$LAYOUT
    );
    static final MethodHandle vips_target_seek$MH = RuntimeHelper.downcallHandle(
        "vips_target_seek",
        constants$84.vips_target_seek$FUNC
    );
    static final FunctionDescriptor vips_target_end$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_target_end$MH = RuntimeHelper.downcallHandle(
        "vips_target_end",
        constants$84.vips_target_end$FUNC
    );
    static final FunctionDescriptor vips_target_finish$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_target_finish$MH = RuntimeHelper.downcallHandle(
        "vips_target_finish",
        constants$84.vips_target_finish$FUNC
    );
}


