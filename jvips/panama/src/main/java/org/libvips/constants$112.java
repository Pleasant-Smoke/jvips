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
class constants$112 {

    static final FunctionDescriptor vips_image_copy_memory$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_image_copy_memory$MH = RuntimeHelper.downcallHandle(
        "vips_image_copy_memory",
        constants$112.vips_image_copy_memory$FUNC
    );
    static final FunctionDescriptor vips_image_wio_input$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_image_wio_input$MH = RuntimeHelper.downcallHandle(
        "vips_image_wio_input",
        constants$112.vips_image_wio_input$FUNC
    );
    static final FunctionDescriptor vips_image_pio_input$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_image_pio_input$MH = RuntimeHelper.downcallHandle(
        "vips_image_pio_input",
        constants$112.vips_image_pio_input$FUNC
    );
    static final FunctionDescriptor vips_image_pio_output$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_image_pio_output$MH = RuntimeHelper.downcallHandle(
        "vips_image_pio_output",
        constants$112.vips_image_pio_output$FUNC
    );
    static final FunctionDescriptor vips_image_inplace$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_image_inplace$MH = RuntimeHelper.downcallHandle(
        "vips_image_inplace",
        constants$112.vips_image_inplace$FUNC
    );
    static final FunctionDescriptor vips_image_write_prepare$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_image_write_prepare$MH = RuntimeHelper.downcallHandle(
        "vips_image_write_prepare",
        constants$112.vips_image_write_prepare$FUNC
    );
}


