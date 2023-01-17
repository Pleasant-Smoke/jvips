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

package com.pss.jvips.plugin.args;

import com.pss.jvips.plugin.overrides.RemapOptionalArg;
import com.pss.jvips.plugin.overrides.VipsOverrides;
import com.pss.jvips.plugin.util.Utils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

@Deprecated
public class OptionalArgUtil {

    private static final Logger log = LoggerFactory.getLogger(OptionalArgUtil.class);

    public static String getOptionalArgumentName(List<String> functions){
        for (RemapOptionalArg optionalArg : VipsOverrides.getOverrides().getRemappedArgs()){
            if(CollectionUtils.containsAny(optionalArg.getIdentifiers(), functions)){
                return optionalArg.getTo();
            }
        }
        return remapNames(functions.stream().map(s-> rename(s)).collect(Collectors.toList()));
    }

    /**
     * Strip ending number, and add `_` between (any letter)save|load
     * @param value method name
     * @return renamed method
     */
    public static String rename(String value){
        return value.replaceAll("\\d$", "")
                .replaceAll("(\\w)(save|load)", "$1_$2");
    }

    /**
     * Split names into tokens, find the tokens common to all methods
     * @param list method names
     * @return Pascal Cased class name
     */
    public static String remapNames(List<String> list){
        if(list.size() == 1){
            String[] tokens = list.get(0).split("_");
            StringBuilder sb = new StringBuilder();
            for(var token : tokens){

                sb.append(StringUtils.capitalize(token));

            }
            sb.append("Params");
            return sb.toString() ;
        }

        Set<String> allTokens = new HashSet<>();

        Set<List<String>> parts = new HashSet<>();

        for (var method : list){
            String[] tokens = method.split("_");
            List<String> strings = new ArrayList<>();

            Collections.addAll(strings, tokens);

            allTokens.addAll(strings);

            parts.add(strings);
        }

        // Go through each list of tokens and retain only the ones that are in each
        parts.forEach(allTokens::retainAll);

        if(allTokens.isEmpty()){
            return "";
        }

        if(allTokens.size() == 1){
            log.warn("Only 1 part left {}", list);
        }

        List<String> commonTokens = Utils.getFirst(parts);
        StringBuilder sb = new StringBuilder();
        for(var commonToken : commonTokens){
            if(allTokens.contains(commonToken)){
                sb.append(StringUtils.capitalize(commonToken));
            }
        }
        sb.append("Params");
        return sb.toString();
    }
}
