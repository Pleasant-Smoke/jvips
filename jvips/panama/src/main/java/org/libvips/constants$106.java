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
class constants$106 {

    static final FunctionDescriptor vips_image_set_progress$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_INT$LAYOUT
    );
    static final MethodHandle vips_image_set_progress$MH = RuntimeHelper.downcallHandle(
        "vips_image_set_progress",
        constants$106.vips_image_set_progress$FUNC
    );
    static final FunctionDescriptor vips_image_iskilled$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_image_iskilled$MH = RuntimeHelper.downcallHandle(
        "vips_image_iskilled",
        constants$106.vips_image_iskilled$FUNC
    );
    static final FunctionDescriptor vips_image_set_kill$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_INT$LAYOUT
    );
    static final MethodHandle vips_image_set_kill$MH = RuntimeHelper.downcallHandle(
        "vips_image_set_kill",
        constants$106.vips_image_set_kill$FUNC
    );
    static final FunctionDescriptor vips_filename_get_filename$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_filename_get_filename$MH = RuntimeHelper.downcallHandle(
        "vips_filename_get_filename",
        constants$106.vips_filename_get_filename$FUNC
    );
    static final FunctionDescriptor vips_filename_get_options$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_filename_get_options$MH = RuntimeHelper.downcallHandle(
        "vips_filename_get_options",
        constants$106.vips_filename_get_options$FUNC
    );
    static final FunctionDescriptor vips_image_new$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT);
    static final MethodHandle vips_image_new$MH = RuntimeHelper.downcallHandle(
        "vips_image_new",
        constants$106.vips_image_new$FUNC
    );
}


