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
class constants$99 {

    static final FunctionDescriptor im_rotate_imask45$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle im_rotate_imask45$MH = RuntimeHelper.downcallHandle(
        "im_rotate_imask45",
        constants$99.im_rotate_imask45$FUNC
    );
    static final FunctionDescriptor im_rotate_dmask90$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle im_rotate_dmask90$MH = RuntimeHelper.downcallHandle(
        "im_rotate_dmask90",
        constants$99.im_rotate_dmask90$FUNC
    );
    static final FunctionDescriptor im_rotate_dmask45$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle im_rotate_dmask45$MH = RuntimeHelper.downcallHandle(
        "im_rotate_dmask45",
        constants$99.im_rotate_dmask45$FUNC
    );
    static final FunctionDescriptor im_mattrn$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle im_mattrn$MH = RuntimeHelper.downcallHandle(
        "im_mattrn",
        constants$99.im_mattrn$FUNC
    );
    static final FunctionDescriptor im_matcat$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle im_matcat$MH = RuntimeHelper.downcallHandle(
        "im_matcat",
        constants$99.im_matcat$FUNC
    );
    static final FunctionDescriptor im_matmul$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle im_matmul$MH = RuntimeHelper.downcallHandle(
        "im_matmul",
        constants$99.im_matmul$FUNC
    );
}


