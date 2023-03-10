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
class constants$138 {

    static final FunctionDescriptor vips_image_get_filename$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_image_get_filename$MH = RuntimeHelper.downcallHandle(
        "vips_image_get_filename",
        constants$138.vips_image_get_filename$FUNC
    );
    static final FunctionDescriptor vips_image_get_mode$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_image_get_mode$MH = RuntimeHelper.downcallHandle(
        "vips_image_get_mode",
        constants$138.vips_image_get_mode$FUNC
    );
    static final FunctionDescriptor vips_image_get_scale$FUNC = FunctionDescriptor.of(Constants$root.C_DOUBLE$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_image_get_scale$MH = RuntimeHelper.downcallHandle(
        "vips_image_get_scale",
        constants$138.vips_image_get_scale$FUNC
    );
    static final FunctionDescriptor vips_image_get_offset$FUNC = FunctionDescriptor.of(Constants$root.C_DOUBLE$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_image_get_offset$MH = RuntimeHelper.downcallHandle(
        "vips_image_get_offset",
        constants$138.vips_image_get_offset$FUNC
    );
    static final FunctionDescriptor vips_image_get_page_height$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_image_get_page_height$MH = RuntimeHelper.downcallHandle(
        "vips_image_get_page_height",
        constants$138.vips_image_get_page_height$FUNC
    );
    static final FunctionDescriptor vips_image_get_n_pages$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_image_get_n_pages$MH = RuntimeHelper.downcallHandle(
        "vips_image_get_n_pages",
        constants$138.vips_image_get_n_pages$FUNC
    );
}


