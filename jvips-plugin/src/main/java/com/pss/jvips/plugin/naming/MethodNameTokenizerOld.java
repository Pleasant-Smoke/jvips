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

package com.pss.jvips.plugin.naming;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MethodNameTokenizerOld {

    @SuppressWarnings("DuplicatedCode")
    private static final Map<String, String> replacements = Map.of(
            "const", "constant",
            "hist", "histogram",
            "eor", "xor",
            "bool", "boolean"

    );


    public VersionedTokenization tokenize(String value){
        return new VersionedTokenization(tokenizeCurrent(value));
    }



    @SuppressWarnings("DuplicatedCode")
    public static List<String> tokenizeCurrent(String value){
        Preconditions.checkArgument(StringUtils.isNotBlank(value), "value passed in is null or blank");
        return Arrays.stream(value.replaceAll("\\d$", "")
                .replaceAll("(\\w)(save|load)", "$1_$2")
                .replaceAll("^(band)(\\w)", "$1_$2")
                .replaceAll("^(reduce|shrink)(h)", "$1_horizontal")
                .replaceAll("^(reduce|shrink)(v)", "$1_vertical")
                .replaceAll("^(gauss)(\\w)", "gaussian_$2")
                .replaceAll("^(lshift)", "left_shift")
                .replaceAll("^(rshift)", "right_shift")
                .replaceAll("^(and|or|eor)image", "$1_image")
                .replaceAll("^(\\w+)(equal|eq)", "$1_equal")
                .replaceAll("^(\\w+)_is(\\w+)", "$1_is_$2")
                .replaceAll("^(\\w+)cache", "$1_cache")
                .replaceAll("^label(\\w+)", "label_$1")
                .replaceAll("(?<!sh)if([a-z])", "if_$1")
                .replaceAll("then([a-z])", "then_$1")
                .replaceAll("^get(\\w)", "get_$1")
                .split("_"))
                .map(x-> replacements.getOrDefault(x, x)).toList();
    }


    public record VersionedTokenization(List<String> current, List<String> compatible){
        public VersionedTokenization(List<String> same){
            this(same, same);
        }
    }
}
