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

import com.pss.jvips.plugin.util.Tuple;
import com.pss.jvips.plugin.util.Utils;
import com.squareup.javapoet.ClassName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.pss.jvips.plugin.naming.TypeRegistration.Types.*;

/**
 * Maps members to their enclosing class, ie: unqualified method names or enum names found in the documentation
 * this is so we can then reference/import these elements with out throwing errors
 *
 * For instance if the Doc contained a sentence like:
 * ```
 * Resize using VipsEnum#VIPS_LANCZOS
 * ```
 *
 * We can use the case format tool to get the correct fully qualified class name and correct java name
 */
public class TypeRegistration {

    private static final Logger log = LoggerFactory.getLogger(TypeRegistration.class);

    enum Types {EXECUTABLE, FIELD, ENUM, BIT_FIELD, NEGATIVE_ENUM, CONSTANT}

    private static final Map<Types, Map<JavaCaseFormat, Set<ClassName>>> typeRegistration = Map.of(
            EXECUTABLE, new HashMap<>(),
            FIELD, new HashMap<>(),
            ENUM, new HashMap<>(),
            BIT_FIELD, new HashMap<>(),
            CONSTANT, new HashMap<>()
    );



    private static final Map<String, String> unprefixedNames = new HashMap<>();


    public static ClassName getRegisteredExecutableClass(JavaCaseFormat name, ClassName calledFrom){
        return getRegisteredClassName(EXECUTABLE, name, calledFrom);
    }


    public static ClassName getRegisteredFieldClass(JavaCaseFormat name, ClassName calledFrom){
        return getRegisteredClassName(FIELD, name, calledFrom);
    }


    public static ClassName getRegisteredEnumClass(JavaCaseFormat name, ClassName calledFrom){
        return getRegisteredClassName(ENUM, name, calledFrom);
    }


    public static ClassName getRegisteredNegativeEnumClass(JavaCaseFormat name, ClassName calledFrom){
        return getRegisteredClassName(NEGATIVE_ENUM, name, calledFrom);
    }



    public static void registerExecutable(JavaCaseFormat name, ClassName calledFrom){
         register(EXECUTABLE, name, calledFrom);
    }


    public static void registerField(JavaCaseFormat name, ClassName calledFrom){
        register(FIELD, name, calledFrom);
    }


    public static void registerEnum(JavaCaseFormat name, ClassName calledFrom){
         register(ENUM, name, calledFrom);
    }


    public static void registerConstant(JavaCaseFormat name, ClassName calledFrom){
         register(CONSTANT, name, calledFrom);
    }

    public ClassName getConstant(JavaCaseFormat name, ClassName calledFrom){
        return getRegisteredClassName(CONSTANT, name, calledFrom);
    }


    public static void registerNegativeEnum(JavaCaseFormat name, ClassName calledFrom){
         register(NEGATIVE_ENUM, name, calledFrom);
    }


    public static void registerBitField(JavaCaseFormat name, ClassName calledFrom){
         register(BIT_FIELD, name, calledFrom);
    }


    public static void registerUnprefixedName(String unprefixed, String prefixed){
        if(prefixed.toLowerCase().startsWith("vips") && prefixed.endsWith(unprefixed)){
            unprefixedNames.put(unprefixed, prefixed);
        } else {
            log.warn("Unprefixed name {} doesn't match prefixed name {}", unprefixed, prefixed);
        }
    }


    public static Tuple<ClassName, JavaCaseFormat> resolve(JavaCaseFormat format){
        for (Map.Entry<Types, Map<JavaCaseFormat, Set<ClassName>>> entries : typeRegistration.entrySet()) {
            Set<ClassName> names;
            if((names = entries.getValue().get(format)) != null){
                return new Tuple<>(Utils.getFirst(names), format);
            }

        }
        return null;
    }


    private static void register(Types registration, JavaCaseFormat member, ClassName className) {
        Map<JavaCaseFormat, Set<ClassName>> names = typeRegistration.get(registration);
        if(names.containsKey(member) && !names.get(member).contains(className)){
            log.warn("{} {}#{} already mapped to: {}", registration, className.simpleName(), member, names.get(member));
        }
        names.computeIfAbsent(member, (v)-> new HashSet<>()).add(className);
    }



    private static ClassName getRegisteredClassName(Types registration, JavaCaseFormat javaName, ClassName calledFrom){
        Map<JavaCaseFormat, Set<ClassName>> names = typeRegistration.get(registration);
        Set<ClassName> classNames = names.get(javaName);
        if(classNames != null){
            if(classNames.contains(calledFrom)){
                return calledFrom;
            } else if(classNames.isEmpty()){
                return calledFrom;
            }
            return Utils.getFirst(classNames);
        }
        return calledFrom;
    }

}
