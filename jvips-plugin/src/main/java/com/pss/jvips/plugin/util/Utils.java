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

package com.pss.jvips.plugin.util;

import com.google.common.base.Preconditions;
import com.pss.jvips.plugin.context.Version;
import com.pss.jvips.plugin.model.xml.executable.AbstractExecutable;
import com.pss.jvips.plugin.model.xml.types.Direction;
import com.pss.jvips.plugin.service.VersionedService;
import com.squareup.javapoet.*;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.lang.model.SourceVersion;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.pss.jvips.plugin.naming.JavaTypeMapping.JVipsImage_class;

public class Utils {

    private static final Pattern VIPS_PREFIX = Pattern.compile("^vips_?(.*)", Pattern.CASE_INSENSITIVE);

    private static final Map<TypeName, String> unwrap = new HashMap<>();

    private static final Field JAVA_DOC_FIELD;
    private static final Field RETURN_TYPE_FIELD;

    static {
        try {
            Field javadoc = MethodSpec.Builder.class.getDeclaredField("javadoc");
            Field returnType = MethodSpec.Builder.class.getDeclaredField("returnType");
            javadoc.setAccessible(true);
            returnType.setAccessible(true);
            JAVA_DOC_FIELD = javadoc;
            RETURN_TYPE_FIELD = returnType;
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }



    public static URL getResource(String name){
        return Utils.class.getClassLoader().getResource(name);
    }



    public static InputStream getVersionedResourceAsStream(Version version, String name){
        return Utils.class.getClassLoader().getResourceAsStream(String.format("%s/%s", version, name));
    }

    public static InputStream getResourceAsStream(String name){
        return Utils.class.getClassLoader().getResourceAsStream(name);
    }



    public static TypeName getReturnType(MethodSpec.Builder builder){
        try {
            return (TypeName) RETURN_TYPE_FIELD.get(builder);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void clearJavaDoc(MethodSpec.Builder builder){
        try {
            CodeBlock.Builder block = (CodeBlock.Builder) JAVA_DOC_FIELD.get(builder);
            block.clear();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static TypeName unbox(TypeName type){
        if(type.isBoxedPrimitive()){
            return type.unbox();
        } else if(type instanceof ArrayTypeName atm && atm.componentType.isBoxedPrimitive()){
            return ArrayTypeName.of(atm.componentType.unbox());
        }
        return type;
    }

    /**
     *
     * @param type
     * @return the most "performant" reference type
     */
    public static TypeName toPerformantReferenceType(@NonNull TypeName type){
        Objects.requireNonNull(type);
        Preconditions.checkArgument(TypeName.OBJECT.equals(type), "Object type passed in, this is an error");
        if(type.isPrimitive()){
            return type.box();
        } else if(type instanceof ArrayTypeName atm && atm.componentType.isBoxedPrimitive()){
            return ArrayTypeName.of(atm.componentType.unbox());
        }
        return type;
    }

    public static <T> T getFirst(Collection<T> set){
        return set.iterator().next();
    }

    public static <T> Optional<T> get(List<T> list, int index){
        return list.size() > index ? Optional.ofNullable(list.get(index)) : Optional.empty();
    }

    public static boolean isInParameter(Direction direction, String name){
        return (direction == Direction.IN && !"out".equals(name)) || "in".equals(name);
    }
    public static boolean isOutParameter(Direction direction, String name){
        return direction == Direction.OUT || "out".equals(name);
    }

    public static boolean requiresWriteLock(AbstractExecutable executable){
        return executable.getName().toLowerCase().contains("draw");
    }

    public static boolean isType(TypeName t1, ClassName t2){
        if(t1.equals(t2)){
            return true;
        }
        if(t1 instanceof ParameterizedTypeName ptn){
            return ptn.rawType.equals(t2);
        } else if(t1 instanceof ArrayTypeName atn){
            return isType(atn.componentType, t2);
        }
        return false;
    }

    public static boolean isImageType(TypeName typeName){
        if(typeName instanceof ParameterizedTypeName ptn) {
            return ptn.rawType.equals(JVipsImage_class) || ptn.typeArguments.stream().anyMatch(Utils::isImageType);
        } else if (typeName instanceof ArrayTypeName atn) {
            return atn.componentType.equals(JVipsImage_class);
        } else if (typeName instanceof ClassName cn) {
            return cn.equals(JVipsImage_class);
        }
        return false;
    }


    public static String caseTokens(List<String> tokens, boolean pascal){
        Objects.requireNonNull(tokens);
        Preconditions.checkArgument(tokens.size() > 0, "Token arguments were empty");
        StringBuilder sb = new StringBuilder();
        Iterator<String> iterator = tokens.iterator();
        String next = iterator.next();
        sb.append(pascal ? StringUtils.capitalize(next) : next);
        while (iterator.hasNext()){
            next = iterator.next();
            sb.append(StringUtils.capitalize(next));
        }
        return sb.toString();
    }



    public static String prefixNameIfJavaIdentifier(String prefix, String name){
        Preconditions.checkArgument(StringUtils.isNotBlank(prefix), "Prefix passed in is null or blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(name), "Name passed in is null or blank");
        if(SourceVersion.isKeyword(name)){
            return prefix + StringUtils.capitalize(name);
        }
        return name;
    }

    public static String postfixNameIfJavaIdentifier(String postfix, String name){
        Preconditions.checkArgument(StringUtils.isNotBlank(postfix), "Prefix passed in is null or blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(name), "Name passed in is null or blank");
        if(SourceVersion.isKeyword(name)){
            return  name + postfix;
        }
        return name;
    }


    public static void requireNotNullOrEmpty(String value){
        Objects.requireNonNull(value);
        Preconditions.checkArgument(StringUtils.isNotBlank(value));
    }

    public static String normalizeName(String name){
        Preconditions.checkArgument(StringUtils.isNotBlank(name), "Name passed in is null or blank");
        Matcher matcher = VIPS_PREFIX.matcher(name);
        if(matcher.matches()){
            return matcher.group(1);
        }
        return name;
    }

    public static void banVipsPrefix(String name){
        Preconditions.checkArgument(StringUtils.isNotBlank(name), "Passed in null or blank method name");
        String lowercase = name.toLowerCase();
        Preconditions.checkArgument(!lowercase.startsWith("vips_"), "Passed in method that starts with vips_");
        Preconditions.checkArgument(!lowercase.toLowerCase().startsWith("vips"), "Passed in method that starts with vips");
    }

    public static void isValidVersionRange(Version input, VersionedService service){
        Preconditions.checkArgument(isValidVersionRange(input, service.min(), service.max()), """
        Invalid version input(%s, %s) for range, min(%s, %s) - max(%s, %s)
        """, input.name(), input.ordinal(), service.min().name(), service.min().ordinal(), service.max().name(), service.max().ordinal());
    }

    public static boolean isValidVersionRange(Version input, Version min, Version max){
        int inputInt = input.ordinal();
        int minInt = min.ordinal();
        int maxInt = max.ordinal();
        return inputInt == maxInt || (minInt <= inputInt && maxInt >= inputInt);
    }
}
