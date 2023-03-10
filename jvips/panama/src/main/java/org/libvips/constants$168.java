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
class constants$168 {

    static final FunctionDescriptor vips_jp2ksave_target$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_jp2ksave_target$MH = RuntimeHelper.downcallHandleVariadic(
        "vips_jp2ksave_target",
        constants$168.vips_jp2ksave_target$FUNC
    );
    static final FunctionDescriptor vips_jxlload_source$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_jxlload_source$MH = RuntimeHelper.downcallHandleVariadic(
        "vips_jxlload_source",
        constants$168.vips_jxlload_source$FUNC
    );
    static final FunctionDescriptor vips_jxlload_buffer$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_LONG_LONG$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_jxlload_buffer$MH = RuntimeHelper.downcallHandleVariadic(
        "vips_jxlload_buffer",
        constants$168.vips_jxlload_buffer$FUNC
    );
    static final FunctionDescriptor vips_jxlload$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_jxlload$MH = RuntimeHelper.downcallHandleVariadic(
        "vips_jxlload",
        constants$168.vips_jxlload$FUNC
    );
    static final FunctionDescriptor vips_jxlsave$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_jxlsave$MH = RuntimeHelper.downcallHandleVariadic(
        "vips_jxlsave",
        constants$168.vips_jxlsave$FUNC
    );
    static final FunctionDescriptor vips_jxlsave_buffer$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_jxlsave_buffer$MH = RuntimeHelper.downcallHandleVariadic(
        "vips_jxlsave_buffer",
        constants$168.vips_jxlsave_buffer$FUNC
    );
}


