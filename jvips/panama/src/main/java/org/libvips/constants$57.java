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
class constants$57 {

    static final FunctionDescriptor vips__find_rightmost_brackets$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips__find_rightmost_brackets$MH = RuntimeHelper.downcallHandle(
        "vips__find_rightmost_brackets",
        constants$57.vips__find_rightmost_brackets$FUNC
    );
    static final FunctionDescriptor vips__filename_split8$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips__filename_split8$MH = RuntimeHelper.downcallHandle(
        "vips__filename_split8",
        constants$57.vips__filename_split8$FUNC
    );
    static final FunctionDescriptor vips_ispoweroftwo$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_INT$LAYOUT
    );
    static final MethodHandle vips_ispoweroftwo$MH = RuntimeHelper.downcallHandle(
        "vips_ispoweroftwo",
        constants$57.vips_ispoweroftwo$FUNC
    );
    static final FunctionDescriptor vips_amiMSBfirst$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT);
    static final MethodHandle vips_amiMSBfirst$MH = RuntimeHelper.downcallHandle(
        "vips_amiMSBfirst",
        constants$57.vips_amiMSBfirst$FUNC
    );
    static final FunctionDescriptor vips__temp_name$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips__temp_name$MH = RuntimeHelper.downcallHandle(
        "vips__temp_name",
        constants$57.vips__temp_name$FUNC
    );
    static final FunctionDescriptor vips__change_suffix$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_INT$LAYOUT
    );
    static final MethodHandle vips__change_suffix$MH = RuntimeHelper.downcallHandle(
        "vips__change_suffix",
        constants$57.vips__change_suffix$FUNC
    );
}

