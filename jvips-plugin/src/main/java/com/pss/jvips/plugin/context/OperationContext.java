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

package com.pss.jvips.plugin.context;

import com.pss.jvips.plugin.config.CommonConfig;
import com.pss.jvips.plugin.config.JniConfig;
import com.pss.jvips.plugin.config.PanamaConfig;
import com.pss.jvips.plugin.naming.GeneratedPackages;
import com.pss.jvips.plugin.naming.Packages;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import se.jbee.inject.bind.Bundle;

import static com.pss.jvips.plugin.naming.JavaTypeMapping.*;

public enum OperationContext {
    COMMON(DataType_TypeVariable, UnderlyingSession_TypeVariable, "", new Packages.Common.Generated(), CommonConfig.class),
    JNI(Long_class, VOID, "Jni", new Packages.Jni.Generated(), JniConfig.class),
    PANAMA(MemorySegment_class, MemorySession_class, "Panama", new Packages.Panama.Generated(), PanamaConfig.class);

    private final ParameterizedTypeName image;
    private final ParameterizedTypeName context;
    private final ParameterizedTypeName operations;
    private final ArrayTypeName imageArray;
    private final TypeName[]  dtoTypes;
    private final TypeName  imageType;
    private final String prefix;
    private final GeneratedPackages packages;
    private final Class<? extends Bundle> config;

    OperationContext(TypeName type, TypeName serialize, String prefix, GeneratedPackages packages, Class<? extends Bundle> config){
        this.image = ParameterizedTypeName.get(JVipsImage_class, type);
        this.imageType = type;
        this.packages = packages;
        this.config = config;
        this.imageArray = ArrayTypeName.of(this.image);
        this.context = ParameterizedTypeName.get(VipsOperationContext_class, type, serialize);
        this.operations = ParameterizedTypeName.get(VipsOperations_class, type, serialize);
        this.dtoTypes = new TypeName[]{type, serialize};
        this.prefix = prefix;
    }

    public ParameterizedTypeName getImage() {
        return image;
    }

    public ParameterizedTypeName getContext() {
        return context;
    }

    public ParameterizedTypeName getOperations() {
        return operations;
    }

    public ArrayTypeName getImageArray() {
        return imageArray;
    }

    public TypeName[] getDtoTypes() {
        return dtoTypes;
    }

    public String getPrefix() {
        return prefix;
    }

    public GeneratedPackages getPackages() {
        return packages;
    }

    public TypeName getImageType() {
        return imageType;
    }

    public Class<? extends Bundle> getConfig() {
        return config;
    }
}
