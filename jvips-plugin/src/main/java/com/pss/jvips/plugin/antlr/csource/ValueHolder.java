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

package com.pss.jvips.plugin.antlr.csource;

import com.pss.jvips.plugin.naming.JavaCaseFormat;
import com.squareup.javapoet.ClassName;

/**
 * In case the default value refers to a constant
 * @param className
 * @param name
 * @param value
 */
public record ValueHolder(ClassName className, JavaCaseFormat name, Object value, boolean negated, boolean minus1) {

    public ValueHolder(ClassName className, JavaCaseFormat name){
        this(className, name, null, false, false);
    }

    public ValueHolder(ClassName className, JavaCaseFormat name, Object value){
        this(className, name, value, false, false);
    }
    public ValueHolder(Object value){
        this(null, null, value, false, false);
    }


    public boolean isPlainValue(){
        return className == null && name == null && value != null;
    }


    public boolean isEnum(){
        return className != null && name != null && value == null;
    }

    public boolean isReference(){
        return className != null && name != null && value != null;
    }
}
