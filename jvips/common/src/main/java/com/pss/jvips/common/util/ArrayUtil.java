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

public class ArrayUtil {

    public static Object[] preSize(int arguments){
        return new Object[arguments * 3];
    }


    public static Object[] resize(Object[] params, int resize){
        Object[] newArray = new Object[((resize - 1) * 3) + params.length];
        System.arraycopy(params, 0, newArray, 0, params.length);
        return newArray;
    }

    public static int indexOfParam(Object[] params, String param){
        for (int i = 0; i < params.length; i += 3) {
            if(param.equals(params[i])){
                return i;
            }
        }
        return -1;
    }

}
