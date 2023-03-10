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
class constants$58 {

    static final FunctionDescriptor vips_realpath$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_realpath$MH = RuntimeHelper.downcallHandle(
        "vips_realpath",
        constants$58.vips_realpath$FUNC
    );
    static final FunctionDescriptor vips__random$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_INT$LAYOUT
    );
    static final MethodHandle vips__random$MH = RuntimeHelper.downcallHandle(
        "vips__random",
        constants$58.vips__random$FUNC
    );
    static final FunctionDescriptor vips__random_add$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_INT$LAYOUT,
        Constants$root.C_INT$LAYOUT
    );
    static final MethodHandle vips__random_add$MH = RuntimeHelper.downcallHandle(
        "vips__random_add",
        constants$58.vips__random_add$FUNC
    );
    static final FunctionDescriptor vips__icc_dir$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT);
    static final MethodHandle vips__icc_dir$MH = RuntimeHelper.downcallHandle(
        "vips__icc_dir",
        constants$58.vips__icc_dir$FUNC
    );
    static final FunctionDescriptor vips__windows_prefix$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT);
    static final MethodHandle vips__windows_prefix$MH = RuntimeHelper.downcallHandle(
        "vips__windows_prefix",
        constants$58.vips__windows_prefix$FUNC
    );
    static final FunctionDescriptor vips__get_iso8601$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT);
    static final MethodHandle vips__get_iso8601$MH = RuntimeHelper.downcallHandle(
        "vips__get_iso8601",
        constants$58.vips__get_iso8601$FUNC
    );
}


