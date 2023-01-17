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

package com.pss.jvips.common.data;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.nio.channels.Channel;

/**
 * Warning: This will probably be removed, this is shaky AF
 */
public class JVipsTarget {

    private static final VarHandle VAR_HANDLE;


    static {
        VarHandle vh = null;
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            vh = MethodHandles.privateLookupIn(FileDescriptor.class, lookup)
                    .findVarHandle(FileDescriptor.class, "handle", long.class);

        } catch (NoSuchFieldException | IllegalAccessException e) {

        }
        VAR_HANDLE = vh;
    }

    private final FileDescriptor descriptor;

    public JVipsTarget(FileDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    public static JVipsTarget from(FileInputStream fis){
        try {
            return new JVipsTarget(fis.getFD());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JVipsTarget from(Channel channel){
        try {
            Field field = channel.getClass().getDeclaredField("fd");
            field.setAccessible(true);
            FileDescriptor fd = (FileDescriptor) field.get(channel);
            return new JVipsTarget(fd);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public long getId(){
        return (long) VAR_HANDLE.get(descriptor);
    }

    public FileDescriptor getDescriptor() {
        return descriptor;
    }
}
