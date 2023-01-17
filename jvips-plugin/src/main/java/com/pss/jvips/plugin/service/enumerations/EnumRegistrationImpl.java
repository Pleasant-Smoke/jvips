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

package com.pss.jvips.plugin.service.enumerations;

import com.pss.jvips.plugin.model.xml.Member;
import com.pss.jvips.plugin.model.xml.Namespace;
import com.pss.jvips.plugin.model.xml.toplevel.Enumeration;
import com.pss.jvips.plugin.naming.ImmutableJavaCaseFormat;
import com.pss.jvips.plugin.naming.JavaCaseFormat;
import com.pss.jvips.plugin.naming.Packages;
import com.pss.jvips.plugin.service.VersionedService;
import com.squareup.javapoet.ClassName;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EnumRegistrationImpl implements EnumRegistration {

    Map<String, EnumerationDTO> mapping = new HashMap<>();

    @Override
    public void registerEnumerations(Namespace namespace){

        for (Enumeration enumeration : namespace.getEnums()) {
            String type = enumeration.getCtype();
            String name = enumeration.getName();
            if(type.startsWith("Vips")){
                ClassName className = ClassName.get(Packages.Common.Generated.enumerations, type);
                var builder = ImmutableEnumerationDTO.builder()
                        .className(className)
                        .name(ImmutableJavaCaseFormat.builder().nativeName(type).javaName(type).build())
                        .documentation(enumeration.documentation().orElse(null));
                for (Member member : enumeration.getMembers()) {
                    String identifier = member.getIdentifier();
                    String javaName = identifier.replaceFirst("^VIPS_", "");
                    var casing = ImmutableJavaCaseFormat.builder().nativeName(identifier).javaName(javaName).build();
                    int value = Integer.parseInt(member.getValue());
                    if(value == -1){
                        builder.isNegative(true);
                    }
                    EnumerationMemberDTO dto = ImmutableEnumerationMemberDTO.builder()
                            .className(className)
                            .name(casing)
                            .value(value)
                            .documentation(member.documentation().orElse(null))
                            .build();
                    builder.addValues(dto);
                }
                ImmutableEnumerationDTO build = builder.build();
                mapping.put(name, build);
                mapping.put(type, build);
            } else {
                throw new RuntimeException("Enum registration must start with Vips got " + type);
            }

        }
    }


    @Override
    public Optional<EnumerationDTO> get(String name){
        return Optional.ofNullable(mapping.get(name));
    }

}
