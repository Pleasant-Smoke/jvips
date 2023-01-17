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

package com.pss.jvips.plugin.service.naming.constants;

import com.google.common.base.Preconditions;
import com.pss.jvips.plugin.naming.ImmutableJavaCaseFormat;
import com.pss.jvips.plugin.naming.JavaCaseFormat;
import com.pss.jvips.plugin.service.VersionedService;
import com.squareup.javapoet.ClassName;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ConstantsRegistrationImpl implements ConstantsRegistration {

    protected Map<String, JavaCaseFormat> names = new HashMap<>();




    @Override
    public JavaCaseFormat registerConstant(final String name){
        Preconditions.checkArgument(name.startsWith("VIPS_"));

        return names.computeIfAbsent(name, (k)->{
            return ImmutableJavaCaseFormat.builder()
                    .nativeName(name)
                    .javaName(name.replace("VIPS_", ""))
                    .build();
        });
    }

    @Override
    public Optional<JavaCaseFormat> get(String name){
        Preconditions.checkArgument(name.startsWith("VIPS_"));
        return Optional.ofNullable(names.get(name));
    }
}
