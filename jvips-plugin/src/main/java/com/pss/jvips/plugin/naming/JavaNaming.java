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
import com.pss.jvips.plugin.util.Utils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.SourceVersion;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaNaming {

    private static final Logger log = LoggerFactory.getLogger(JavaNaming.class);

    private static final Properties remapping = new Properties();

    static {
        try {
            remapping.load(Utils.getResourceAsStream("javaize.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final Map<String, JavaCaseFormat> remappedParameters = new HashMap<>();
    private static final Map<String, JavaCaseFormat> remappedMethods = new HashMap<>();

    private static final Map<String, JavaCaseFormat> remappedVarArgClass = new HashMap<>();
    private static final Map<String, JavaCaseFormat> remappedEnum = new HashMap<>();
    private static final Map<String, JavaCaseFormat> remappedClass = new HashMap<>();
    private static final Map<String, JavaCaseFormat> remappedField = new HashMap<>();
    private static final Map<String, JavaCaseFormat> remappedReturnValue = new HashMap<>();

    private static final Map<String, JavaCaseFormat> remappedConstant = new HashMap<>();

    private static final Pattern pattern = Pattern.compile("([a-z0-9])_([a-z0-9])");

    private static final Pattern fixCasing = Pattern.compile("^(band)([a-z])");
    private static final Pattern fixSaveCasing = Pattern.compile("^([a-z0-9]+)(save|load)");
    private static final Pattern methodOverloading = Pattern.compile("([a-zA-Z]+)[0-9]$");



    private final MethodNameTokenizerOld methodNameTokenizer;

    public JavaNaming(MethodNameTokenizerOld methodNameTokenizer) {
        this.methodNameTokenizer = methodNameTokenizer;
    }


    /**
     * Turns this: `VIPS_TYPE_PRECISION` to this `VipsPrecision`, original registration was with name=".." which doesnt
     * have the Vips prefix
     * @param name
     * @return
     */
    public static JavaCaseFormat getJavaClassNameMacroName(final String name){
        String newName = camelCase(name.replace("VIPS_TYPE_", "").toLowerCase(), true);
        return getJavaClassName(newName);
    }



    public static JavaCaseFormat getJavaClassName(final String name){
        return remappedClass.computeIfAbsent(name, (final var k)-> {
            String n;
            if(!k.startsWith("Vips")){
                n = "Vips" + k;
            } else {
                log.warn("Class name {} starts with Vips, make sure data is being pulled that doesn't start with vips for consistency", k);
                n = k;
            }
            return ImmutableJavaCaseFormat.builder().javaName(n).nativeName(k).build();
        });
    }

    /**
     * So far only `boolean` has been used as a parameter and it is for a `BooleanOperation`
     * @param name
     * @return the name
     */
    public static JavaCaseFormat parameterName(final String name){
        return remappedParameters.computeIfAbsent(name, (final var key)-> {
            String replace = key.replaceAll("(vips|image)_", "");
            if(!key.equals(replace)){
                log.warn("Parameter name has 'vips_' or 'image_' in it");
            }
            String camelCase = camelCase(replace, false);
            if(SourceVersion.isKeyword(camelCase)){
                log.warn("Parameter is a reserved keyword, {}", camelCase);
                camelCase = camelCase + "Operation";
            }
            return ImmutableJavaCaseFormat.builder()
                    .nativeName(key)
                    .javaName(camelCase)
                    .build();
        });
    }

    public static JavaCaseFormat resultName(final String name){
        return remappedReturnValue.computeIfAbsent(name, (k)-> {
            String n = StringUtils.capitalize(k) + "Result";
            return ImmutableJavaCaseFormat.builder().nativeName(name).javaName(n).build();
        });
    }

    public static JavaCaseFormat fieldName(final String name){
        return remappedField.computeIfAbsent(name, (final var key)-> {
            String result = key.replaceAll("(vips|image)_", "");
            result = camelCase(result, false);

            Matcher matcher = fixCasing.matcher(result);
            if(matcher.find()){
                result = matcher.replaceFirst((r)-> r.group(1) + r.group(2).toUpperCase());
            }

            if(SourceVersion.isKeyword(result)){
                result = "vips" + StringUtils.capitalize(result);
            }
            if(result.length() == 1){ // For `Q` param
                result = result.toUpperCase();
            }
            return ImmutableJavaCaseFormat.builder()
                    .nativeName(key)
                    .javaName(result)
                    .build();
        });
    }

    public static JavaCaseFormat registerConstant(final String name){
        //Preconditions.checkArgument(name.startsWith("VIPS_"));
        return remappedConstant.computeIfAbsent(name, (k)->{
            return ImmutableJavaCaseFormat.builder().nativeName(name).javaName(name.replace("VIPS_", "")).build();
        });
    }

    public JavaCaseFormat registerConstantNew(final String name){
        Preconditions.checkArgument(name.startsWith("VIPS_"));
        return remappedConstant.computeIfAbsent(name, (k)->{
            return ImmutableJavaCaseFormat.builder().nativeName(name).javaName(name.replace("VIPS_", "")).build();
        });
    }



    public static boolean isConstant(final String name){
        return remappedConstant.containsKey(name);
    }

    public static Optional<JavaCaseFormat> getConstant(final String name){
        return Optional.ofNullable(remappedConstant.get(name));
    }

    public static JavaCaseFormat generateOptionalArgClassName(final String name){
        return remappedVarArgClass.computeIfAbsent(name, (final var key)-> {
            String result = key.replaceAll("(image)_", "");
            // False because of the remapper
            result = camelCase(result, false);

            Matcher matcher = methodOverloading.matcher(result);
            if(matcher.find()){
                result = matcher.group(1);
            }

            result = (String) remapping.getOrDefault(result, result);

            result = StringUtils.capitalize(result);
            if(!result.startsWith("Vips")){
                result = "Vips" + result;
            }
            result = result + "Params";

            return ImmutableJavaCaseFormat.builder()
                    .nativeName(key)
                    .javaName(result)
                    .build();
        });
    }


    public static JavaCaseFormat methodName(final String name){
        return remappedMethods.computeIfAbsent(name, (final var key)-> {

            String result = key.replaceAll("(vips|image)_", "");
            result = camelCase(result, false);

            Matcher matcher = fixCasing.matcher(result);
            if(matcher.find()){
                result = matcher.replaceFirst((r)-> r.group(1) + r.group(2).toUpperCase());
            }

            matcher = fixSaveCasing.matcher(result);
            if(matcher.find()){
                result = matcher.replaceFirst((r)-> r.group(1) + StringUtils.capitalize(r.group(2)));
            }

            matcher = methodOverloading.matcher(result);
            if(matcher.find()){
                result = matcher.group(1);
            }

            result = (String) remapping.getOrDefault(result, result);
            if(SourceVersion.isKeyword(result)){
                result = "vips" + StringUtils.capitalize(result);
            }
            return ImmutableJavaCaseFormat.builder()
                    .nativeName(key)
                    .javaName(result)
                    .build();
        });
    }

    public static String camelCase(String name, boolean capitalizeFirst){
        String[] s = name.split("_");
        StringBuilder sb = capitalizeFirst ? new StringBuilder() : new StringBuilder(s[0]);
        for (int i = capitalizeFirst ? 0 : 1; i < s.length; i++) {
            sb.append(StringUtils.capitalize(s[i]));
        }
        return sb.toString();
    }



}
