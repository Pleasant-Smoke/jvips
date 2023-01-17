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

package com.pss.jvips.common.util;

import com.pss.jvips.common.context.VipsException;
import org.intellij.lang.annotations.PrintFormat;

public class Conditions {

    public static void require(boolean truthy,
                               @PrintFormat String message, Object... format) throws VipsException {
        if(!truthy){
            throw new VipsException(String.format(message, format));
        }
    }

    public static void require(boolean truthy, String message) throws VipsException {
        if(!truthy){
            throw new VipsException(message);
        }
    }

    public static void requireUnchecked(boolean truthy,
                               @PrintFormat String message, Object... format) {
        if(!truthy){
            throw new RuntimeException(String.format(message, format));
        }
    }

    public static void requireUnchecked(boolean truthy, String message) {
        if(!truthy){
            throw new RuntimeException(message);
        }
    }
}
