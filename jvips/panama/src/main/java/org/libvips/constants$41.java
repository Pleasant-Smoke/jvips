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
class constants$41 {

    static final FunctionDescriptor VipsSListFold2Fn$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle VipsSListFold2Fn$MH = RuntimeHelper.downcallHandle(
        constants$41.VipsSListFold2Fn$FUNC
    );
    static final FunctionDescriptor vips_path_filename7$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_path_filename7$MH = RuntimeHelper.downcallHandle(
        "vips_path_filename7",
        constants$41.vips_path_filename7$FUNC
    );
    static final FunctionDescriptor vips_path_mode7$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_path_mode7$MH = RuntimeHelper.downcallHandle(
        "vips_path_mode7",
        constants$41.vips_path_mode7$FUNC
    );
    static final FunctionDescriptor vips_buf_rewind$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_buf_rewind$MH = RuntimeHelper.downcallHandle(
        "vips_buf_rewind",
        constants$41.vips_buf_rewind$FUNC
    );
    static final FunctionDescriptor vips_buf_destroy$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_buf_destroy$MH = RuntimeHelper.downcallHandle(
        "vips_buf_destroy",
        constants$41.vips_buf_destroy$FUNC
    );
}

