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

import com.pss.jvips.plugin.naming.FormattedName;
import com.pss.jvips.plugin.service.executables.arguments.EarlyStageArgumentDTO;
import com.pss.jvips.plugin.antlr.csource.MacroType;
import com.pss.jvips.plugin.antlr.csource.ValueHolder;
import com.pss.jvips.plugin.model.xml.types.Direction;
import com.pss.jvips.plugin.naming.JavaCaseFormat;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import org.immutables.value.Value;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@Value.Immutable
public interface OptionalArgumentDTO extends ArgumentDTO {

    @Nullable
    ClassName getClassName();

    ArgumentDTO getArgumentDTO();


    default MacroType getMacroType() {
        return getArgumentDTO().getMacroType();
    }

    default TypeName getType() {
        return getArgumentDTO().getType();
    }

    default JavaCaseFormat getFormattedName() {
        return getArgumentDTO().getFormattedName();
    }

    @Nullable
    default String getDocumentation() {
        return getArgumentDTO().getDocumentation();
    }

    default String getName() {
        return getArgumentDTO().getName();
    }

    default String getLabel() {
        return getArgumentDTO().getLabel();
    }

    default String getDescription() {
        return getArgumentDTO().getDescription();
    }

    default @Nullable ValueHolder getDefaultValue() {
        return getArgumentDTO().getDefaultValue();
    }

    default @Nullable ValueHolder getMinValue() {
        return getArgumentDTO().getMinValue();
    }

    default @Nullable ValueHolder getMaxValue() {
        return getArgumentDTO().getMaxValue();
    }

    default Optional<ValueHolder> defaultValue() {
        return getArgumentDTO().defaultValue();
    }

    default Optional<ValueHolder> minValue() {
        return getArgumentDTO().minValue();
    }

    default Optional<ValueHolder> maxValue() {
        return getArgumentDTO().maxValue();
    }

    default boolean isImage() {
        return getArgumentDTO().isImage();
    }

    default boolean isDeprecated() {
        return getArgumentDTO().isDeprecated();
    }

    default boolean isRequired() {
        return getArgumentDTO().isRequired();
    }

    default Direction getDirection() {
        return getArgumentDTO().getDirection();
    }

    default int getArgumentCount() {
        return getArgumentDTO().getArgumentCount();
    }


}
