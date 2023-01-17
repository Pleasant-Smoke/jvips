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

package com.pss.jvips.plugin.service.executables;

import com.pss.jvips.plugin.service.executables.arguments.ArgumentDTO;
import com.pss.jvips.plugin.service.executables.arguments.EarlyStageArgumentDTO;
import com.pss.jvips.plugin.service.executables.arguments.OptionalArgumentDTO;
import com.squareup.javapoet.ClassName;
import org.immutables.value.Value;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

@Value.Immutable
public interface ExecutableDTO extends BaseExecutableDTO {

    List<ArgumentDTO> getAllRequiredExecutableArguments();

    List<OptionalArgumentDTO> getAllOptionalExecutableArguments();

    List<String> getImageParameterNames();

    List<String> getImageArrayParameterNames();

    boolean hasOptionalParameters();

    boolean hasSingularOptionalParameter();

    boolean hasDtoOptionalParameter();

    @Nullable
    ClassName getDtoClassName();

    default Optional<OptionalArgumentDTO> getOptionalArgument(String name){
        return getAllOptionalExecutableArguments().stream().filter(x-> x.getFormattedName().getNativeName().equals(name)).findFirst();
    }

    default Optional<ArgumentDTO> getRequiredArgument(String name){
        return getAllRequiredExecutableArguments().stream().filter(x-> x.getFormattedName().getNativeName().equals(name)).findFirst();
    }
}
