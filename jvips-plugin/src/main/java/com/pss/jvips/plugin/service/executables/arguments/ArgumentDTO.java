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

package com.pss.jvips.plugin.service.executables.arguments;

import com.pss.jvips.plugin.antlr.csource.MacroType;
import com.pss.jvips.plugin.antlr.csource.ValueHolder;
import com.pss.jvips.plugin.model.xml.types.Direction;
import com.pss.jvips.plugin.naming.JavaCaseFormat;
import com.squareup.javapoet.TypeName;
import org.immutables.value.Value;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;


public interface ArgumentDTO {

    MacroType getMacroType();

    TypeName getType();

    JavaCaseFormat getFormattedName();

    @Nullable
    String getDocumentation();

    String getName();

    String getLabel();

    String getDescription();

    @Nullable
    ValueHolder getDefaultValue();

    @Nullable
    ValueHolder getMinValue();

    @Nullable
    ValueHolder getMaxValue();

    default Optional<ValueHolder> defaultValue(){
        return Optional.ofNullable(getDefaultValue());
    }

    default Optional<ValueHolder> minValue(){
        return Optional.ofNullable(getMinValue());
    }

    default Optional<ValueHolder> maxValue(){
        return Optional.ofNullable(getMaxValue());
    }


    boolean isImage();

    boolean isDeprecated();

    boolean isRequired();

    Direction getDirection();

    default int getArgumentCount(){
        return 1;
    }

}
