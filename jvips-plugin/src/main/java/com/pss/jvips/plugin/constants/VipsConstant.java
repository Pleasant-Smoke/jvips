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

package com.pss.jvips.plugin.constants;

import com.pss.jvips.plugin.naming.JavaCaseFormat;
import com.pss.jvips.plugin.service.deprecation.SymbolDeprecation;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;
import org.immutables.value.Value;
import org.jetbrains.annotations.Nullable;

@Value.Immutable
public interface VipsConstant {

    JavaCaseFormat name();

    String path();

    TypeName type();

    String value();

    ClassName className();

    @Nullable
    String getDocumentation();

    @Nullable
    SymbolDeprecation getDeprecation();

    @EnsuresNonNullIf(expression = "deprecation()", result = true)
    default boolean hasDeprecations(){
        return getDeprecation() != null;
    }
}
