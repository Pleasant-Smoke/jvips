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

package com.pss.jvips.plugin.service.naming.method;

import com.google.common.base.Preconditions;
import com.pss.jvips.plugin.service.VersionedService;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public class ParameterNameTokenizerImpl implements ParameterNameTokenizer {

    /**
     * We may have to do other replacements so use {@link Arrays#stream(Object[])} instead of
     * {@link Arrays#asList(Object[])}
     *
     * @see MethodNameTokenizerIml#tokenizeCurrent(String)
     *
     * @param name name of the parameter
     * @return name split by `_`
     */
    @Override
    public List<String> tokenize(String name){
        Preconditions.checkArgument(StringUtils.isNotBlank(name), "value passed in is null or blank");
        return Arrays.stream(name.replaceFirst("(vips|image)_", "").split("_")) // Use stream in case of situations described above
                .toList();
    }
}
