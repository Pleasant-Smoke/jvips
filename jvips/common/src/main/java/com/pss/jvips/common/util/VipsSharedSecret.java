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

/**
 * Taking a page from the JDK team
 *
 * @see jdk.internal.access.SharedSecrets
 */
public class VipsSharedSecret {

    private static VipsOperationContextAccess contextAccess;

    public static VipsOperationContextAccess getContextAccess(){
        VipsOperationContextAccess access = contextAccess;
        if(access == null){
            try {
                Class.forName("com.pss.jvips.common.context.VipsOperationContext$Access", true, VipsSharedSecret.class.getClassLoader());
                access = contextAccess;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return access;
    }

    public static void setContextAccess(VipsOperationContextAccess access){
        contextAccess = access;
    }
}
