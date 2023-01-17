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
import com.pss.jvips.plugin.model.xml.executable.AbstractExecutable;
import com.pss.jvips.plugin.naming.JavaCaseFormat;
import com.pss.jvips.plugin.naming.JavaNaming;
import com.pss.jvips.plugin.naming.JavaTypeMapping;
import com.pss.jvips.plugin.naming.Packages;
import com.pss.jvips.plugin.out.OutParam;
import com.pss.jvips.plugin.util.Utils;
import com.pss.jvips.plugin.context.GlobalPluginContext;
import com.pss.jvips.plugin.context.OperationContext;
import com.squareup.javapoet.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.pss.jvips.plugin.naming.JavaTypeMapping.getClassName;

public class RecordGenerator extends CodeGeneratorOld<RecordGenerator.RecordArgs, TypeName> {

    private static final Map<TypeName, Boolean> requiresTypeParam = new HashMap<>();

    public RecordGenerator(Path path, GlobalPluginContext context) {
        super(path, context, OperationContext.COMMON);
    }


    @Override
    protected Result<TypeName> runInternal(TypeSpec.Builder typeSpec, RecordGenerator.RecordArgs arguments) {
        boolean containsImage = arguments.outParams.stream().anyMatch(x -> Utils.isImageType(x.type()));
        arguments.requiresParameter = containsImage;
        requiresTypeParam.put(arguments.className, containsImage);
        MethodSpec sp = MethodSpec.constructorBuilder()
                .addParameters(arguments.outParams.stream().map(x-> ParameterSpec.builder(x.type(), x.name()).build()).toList())
                .build();
        typeSpec.addMethod(sp);
        if(containsImage){
            return new Result<>(ParameterizedTypeName.get(arguments.className, context.operationContext().getImageType()), arguments.className);
        } else {
            return new Result<>(arguments.className, arguments.className);
        }

    }

    @Override
    protected void customizeWrite(Path path, RecordGenerator.RecordArgs args) {
        try {
            Pattern pattern = Pattern.compile("class .+\\n  (\\w+)(.+)\\n\\s+\\}");
            String contents = Files.readString(path);
            Matcher matcher = pattern.matcher(contents);

            if (matcher.find()) {
                String replaced;
                if(args.requiresParameter) {
                    replaced = matcher.replaceAll("public record $1<DataType>$2");
                } else {
                    replaced = matcher.replaceAll("public record $1$2");
                }
                Files.writeString(path, replaced);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected TypeSpec.Builder getTypeSpec(RecordGenerator.RecordArgs arguments) {
        JavaCaseFormat newName = JavaNaming.resultName(arguments.executableType.javaName());
        ClassName className = JavaTypeMapping.getClassName(newName, Packages.Common.Generated.result);
        arguments.className = className;
        return TypeSpec.classBuilder(className);
    }



    public static boolean requiresTypeParam(TypeName typeSpec){
        return requiresTypeParam.getOrDefault(typeSpec, Boolean.FALSE);
    }


    public static void registerRequiresTypeParam(TypeName typeSpec){
        requiresTypeParam.put(typeSpec, Boolean.TRUE);
    }


    public static class RecordArgs {
        protected final AbstractExecutable executableType;
        protected final Set<OutParam> outParams;
        protected ClassName className;
        protected boolean requiresParameter;

        public RecordArgs(AbstractExecutable executableType, Set<OutParam> outParams) {
            this.executableType = executableType;
            this.outParams = outParams;
        }
    }
}
