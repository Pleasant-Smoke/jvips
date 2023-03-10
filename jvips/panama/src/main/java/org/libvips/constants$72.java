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
class constants$72 {

    static final FunctionDescriptor vips_blob_new$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_LONG_LONG$LAYOUT
    );
    static final MethodHandle vips_blob_new$MH = RuntimeHelper.downcallHandle(
        "vips_blob_new",
        constants$72.vips_blob_new$FUNC
    );
    static final FunctionDescriptor vips_blob_copy$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_LONG_LONG$LAYOUT
    );
    static final MethodHandle vips_blob_copy$MH = RuntimeHelper.downcallHandle(
        "vips_blob_copy",
        constants$72.vips_blob_copy$FUNC
    );
    static final FunctionDescriptor vips_blob_get$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle vips_blob_get$MH = RuntimeHelper.downcallHandle(
        "vips_blob_get",
        constants$72.vips_blob_get$FUNC
    );
    static final FunctionDescriptor vips_blob_set$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_LONG_LONG$LAYOUT
    );
    static final MethodHandle vips_blob_set$MH = RuntimeHelper.downcallHandle(
        "vips_blob_set",
        constants$72.vips_blob_set$FUNC
    );
    static final FunctionDescriptor vips_blob_get_type$FUNC = FunctionDescriptor.of(Constants$root.C_LONG_LONG$LAYOUT);
    static final MethodHandle vips_blob_get_type$MH = RuntimeHelper.downcallHandle(
        "vips_blob_get_type",
        constants$72.vips_blob_get_type$FUNC
    );
    static final FunctionDescriptor vips_array_double_new$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_INT$LAYOUT
    );
    static final MethodHandle vips_array_double_new$MH = RuntimeHelper.downcallHandle(
        "vips_array_double_new",
        constants$72.vips_array_double_new$FUNC
    );
}


