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
import com.pss.jvips.plugin.antlr.csource.MacroType;
import com.pss.jvips.plugin.model.xml.types.Direction;
import org.immutables.value.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value.Immutable
public interface IntermediateExecutableDTO extends BaseExecutableDTO {



    List<ArgumentDTO> getAllArguments();

    @Value.Lazy
    default List<ArgumentDTO> getNonDeprecatedArguments(){
        return getAllArguments().stream().filter(x-> !x.isDeprecated()).collect(Collectors.toList());
    }

    @Value.Lazy
    default List<ArgumentDTO> getRequiredInput(){
        return getNonDeprecatedArguments().stream().filter(x-> x.isRequired() && x.getDirection() == Direction.IN).collect(Collectors.toList());
    }

    @Value.Lazy
    default List<ArgumentDTO> getOptionalInput(){
        return getNonDeprecatedArguments().stream().filter(x-> !x.isRequired() && x.getDirection() == Direction.IN).collect(Collectors.toList());
    }

    @Value.Lazy
    default List<ArgumentDTO> getRequiredOutput(){
        return getNonDeprecatedArguments().stream().filter(x-> x.isRequired() && x.getDirection() == Direction.OUT).collect(Collectors.toList());
    }

    @Value.Lazy
    default List<ArgumentDTO> getOptionalOutput(){
        return getNonDeprecatedArguments().stream().filter(x-> !x.isRequired() && x.getDirection() == Direction.OUT).collect(Collectors.toList());
    }

    @Value.Lazy
    default List<ArgumentDTO> getImageInput(){
        return getRequiredInput().stream().filter(s-> s.isImage() && s.getMacroType() == MacroType.IMAGE).collect(Collectors.toList());
    }

    @Value.Lazy
    default List<ArgumentDTO> getImageArrayInput(){
        return getRequiredInput().stream().filter(s-> s.isImage() && s.getMacroType() == MacroType.BOXED).collect(Collectors.toList());
    }


}
