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

package com.pss.jvips.plugin.generation.impl;

import com.pss.jvips.plugin.generation.CodeGeneratorOld;
import com.pss.jvips.plugin.util.SourceLoader;
import com.pss.jvips.plugin.context.GlobalPluginContext;
import com.pss.jvips.plugin.context.OperationContext;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.pss.jvips.plugin.naming.JavaTypeMapping.String_class;
import static com.pss.jvips.plugin.naming.JavaTypeMapping.VipsInterpolate_class;

public class InterpolateGenerator extends CodeGeneratorOld {

    private static final Pattern names = Pattern.compile("vips_interpolate_(\\w+)_get_type");


    public InterpolateGenerator(Path task, GlobalPluginContext context) {
        super(task, context, OperationContext.COMMON);
    }



    @Override
    protected Result runInternal(final TypeSpec.Builder typeSpec, Object arguments) {
        Path interpolatePath = SourceLoader.getSrcDir().resolve("libvips/resample/interpolate.c");
        try {
            Set<String> types = new HashSet<>();
            String fileContents = Files.readString(interpolatePath);
            Matcher matcher = names.matcher(fileContents);
            while (matcher.find()){
                types.add(matcher.group(1));
            }

            for(String type : types){
                FieldSpec field = FieldSpec.builder(String_class, type.toUpperCase(), Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                        .initializer("$S", type).build();
                typeSpec.addField(field);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new Result(null, VipsInterpolate_class);
    }

    @Override
    protected TypeSpec.Builder getTypeSpec(Object arguments) {
        return TypeSpec.classBuilder(VipsInterpolate_class).addModifiers(Modifier.PUBLIC);
    }
}
