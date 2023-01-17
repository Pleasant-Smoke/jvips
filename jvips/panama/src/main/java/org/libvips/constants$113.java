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
class constants$113 {

    static final FunctionDescriptor vips_image_write_line$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_image_write_line$MH = RuntimeHelper.downcallHandle(
        "vips_image_write_line",
        constants$113.vips_image_write_line$FUNC
    );
    static final FunctionDescriptor vips_band_format_isint$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_INT$LAYOUT
    );
    static final MethodHandle vips_band_format_isint$MH = RuntimeHelper.downcallHandle(
        "vips_band_format_isint",
        constants$113.vips_band_format_isint$FUNC
    );
    static final FunctionDescriptor vips_band_format_isuint$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_INT$LAYOUT
    );
    static final MethodHandle vips_band_format_isuint$MH = RuntimeHelper.downcallHandle(
        "vips_band_format_isuint",
        constants$113.vips_band_format_isuint$FUNC
    );
    static final FunctionDescriptor vips_band_format_is8bit$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_INT$LAYOUT
    );
    static final MethodHandle vips_band_format_is8bit$MH = RuntimeHelper.downcallHandle(
        "vips_band_format_is8bit",
        constants$113.vips_band_format_is8bit$FUNC
    );
    static final FunctionDescriptor vips_band_format_isfloat$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_INT$LAYOUT
    );
    static final MethodHandle vips_band_format_isfloat$MH = RuntimeHelper.downcallHandle(
        "vips_band_format_isfloat",
        constants$113.vips_band_format_isfloat$FUNC
    );
    static final FunctionDescriptor vips_band_format_iscomplex$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_INT$LAYOUT
    );
    static final MethodHandle vips_band_format_iscomplex$MH = RuntimeHelper.downcallHandle(
        "vips_band_format_iscomplex",
        constants$113.vips_band_format_iscomplex$FUNC
    );
}

