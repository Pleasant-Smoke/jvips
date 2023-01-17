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

package com.pss.jvips.plugin.model.base;

import com.pss.jvips.plugin.model.xml.type.NativeArrayType;
import com.pss.jvips.plugin.naming.JavaTypeMapping;
import com.pss.jvips.plugin.model.xml.type.AbstractNativeType;
import com.pss.jvips.plugin.model.xml.type.NativeType;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.util.Optional;

public interface Typeable {



    Logger log = LoggerFactory.getLogger(Typeable.class);

    AbstractNativeType getType();


    default boolean isArrayType(){
        return getType() instanceof NativeArrayType;
    }

    default TypeName constructType(){
        if(getType() instanceof NativeArrayType at){

            String name = Optional
                    .ofNullable(at.getComponentType()).map(NativeType::getName)
                    .or(()->
                            Optional.ofNullable(at.getComponentType())
                                    .map(AbstractNativeType::getType))
                    .orElse("");
            TypeName type = JavaTypeMapping.getType(name);
            if(type.isPrimitive()){
                if(TypeName.DOUBLE.equals(type)){
                    return ClassName.get(DoubleBuffer.class);
                }
                return ClassName.get(ByteBuffer.class);
            }
            return ArrayTypeName.of(type);
        } else if(getType() instanceof NativeType c) {
            String name = Optional.ofNullable(c.getName()).or(()-> Optional.of(c.getType())).orElse("");
            return JavaTypeMapping.getType(name);
        } else {
            log.warn("Call to constructType() getting type was null, return java.lang.Object");
            return TypeName.OBJECT;
        }
    }

    default String getNativeType(){
        if(getType() instanceof NativeArrayType at){
            String name = Optional.ofNullable(at.getComponentType()).map(NativeType::getName).orElse("");
            return name + "[]";
        } else if(getType() instanceof NativeType c) {
            return Optional.ofNullable(c.getName()).orElse("");
        } else {
            return "";
        }
    }
}
