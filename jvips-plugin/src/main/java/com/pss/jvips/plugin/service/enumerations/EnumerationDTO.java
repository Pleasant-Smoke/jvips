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

import com.pss.jvips.plugin.naming.FormattedName;
import com.pss.jvips.plugin.naming.JavaCaseFormat;
import com.pss.jvips.plugin.service.DocumentableDTO;
import com.squareup.javapoet.ClassName;
import org.immutables.value.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;

@Value.Immutable
public interface EnumerationDTO extends DocumentableDTO, FormattedName {

    JavaCaseFormat getName();

    ClassName getClassName();

    boolean isNegative();

    @Value.NaturalOrder
    SortedSet<EnumerationMemberDTO> getValues();

    @Value.Lazy
    default Map<String, EnumerationMemberDTO> valueMap(){
        Map<String, EnumerationMemberDTO> map = new HashMap<>();
        for (EnumerationMemberDTO value : getValues()) {
            map.put(value.getName().getNativeName(), value);
            map.put(value.getName().getJavaName(), value);
        }
        return map;
    }
}
