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

package com.pss.jvips.plugin.generation.impl.operations;

import com.pss.jvips.plugin.model.dto.parameters.WritableParameter;
import com.pss.jvips.plugin.model.xml.executable.AbstractExecutable;
import com.pss.jvips.plugin.util.Utils;
import com.pss.jvips.plugin.context.GlobalPluginContext;
import com.pss.jvips.plugin.context.OperationContext;
import com.squareup.javapoet.*;
import org.gradle.api.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.Modifier;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static com.pss.jvips.plugin.naming.JavaTypeMapping.*;

public class GenerateAbstractOperations extends OperationGenerator<OperationGenerator.OperationGeneratorContext> {

    private static final Logger log = LoggerFactory.getLogger(GenerateAbstractOperations.class);

    public GenerateAbstractOperations(Path task, GlobalPluginContext context) {
        super(task, context, OperationContext.COMMON);
    }

    @Override
    protected ParameterizedTypeName parameterizedContextClass() {
        return VipsOperationContext_T_class;
    }


    @Override
    protected ParameterizedTypeName parameterizedOperationClass() {
        return VipsOperations_T_class;
    }

    @Override
    protected ParameterizedTypeName parameterizedImageClass() {
        return JVipsImage_T_class;
    }

    @Override
    protected ClassName className() {
        return VipsOperations_class;
    }

    @Override
    protected Modifier[] modifiers() {
        return new Modifier[]{Modifier.PUBLIC, Modifier.ABSTRACT};
    }

    @Override
    protected TypeSpec.Builder getTypeSpec(OperationGeneratorContext arguments) {
        return super.getTypeSpec(arguments).addTypeVariables(List.of(DataType_TypeVariable, UnderlyingSession_TypeVariable));
    }

    @Override
    protected void buildWithOutOptional(TypeSpec.Builder typeSpec, MethodSpec.Builder methodSpec, AbstractExecutable executable, List<WritableParameter> params, TypeName returnType) {
        typeSpec.addMethod(methodSpec.build());
    }

    @Override
    protected void addConstructor(TypeSpec.Builder typeSpec) {
        typeSpec.addField(FieldSpec.builder(VipsOperationContextAccess_class, "access", Modifier.FINAL, Modifier.STATIC, Modifier.PROTECTED)
                .initializer("$T.getContextAccess()", VipsSharedSecret_class).build());
        typeSpec.addField(parameterizedContextClass(), "context", Modifier.PROTECTED, Modifier.FINAL);
        typeSpec.addMethod(createAbstractConstructor().build());
        typeSpec.addMethod(newFromFile().addModifiers(Modifier.ABSTRACT).build());
    }


    @Override
    protected void customize(MethodSpec.Builder builder) {
        builder.addModifiers(Modifier.ABSTRACT);
    }

    @Override
    protected void buildWithSingularOptionalParam(TypeSpec.Builder typeSpec, MethodSpec.Builder methodSpec, AbstractExecutable executable, List<WritableParameter> params, TypeName returnType, ParameterSpec parameterSpec) {
        addOverload(typeSpec, methodSpec, parameterSpec);
    }

    @Override
    protected void buildWithDTOOptionalParam(TypeSpec.Builder typeSpec, MethodSpec.Builder methodSpec, AbstractExecutable executable, List<WritableParameter> params, TypeName returnType, ParameterSpec parameterSpec) {
        addOverload(typeSpec,  methodSpec, parameterSpec);
    }


    private void addOverload(TypeSpec.Builder typeSpec, MethodSpec.Builder originalMethodBuilder, ParameterSpec additional) {
        MethodSpec.Builder overloadedBuilder = originalMethodBuilder.build().toBuilder();
        originalMethodBuilder.addParameter(additional);
        MethodSpec originalMethod = originalMethodBuilder.build();
        //executable.setBaseMethod(originalMethod);
        typeSpec.addMethod(originalMethod);
        Utils.clearJavaDoc(overloadedBuilder);
        overloadedBuilder.modifiers.remove(Modifier.ABSTRACT);
        overloadedBuilder.modifiers.add(Modifier.FINAL);
        String params = overloadedBuilder.parameters.stream().map(x -> x.name).collect(Collectors.joining(", "));

        if(!TypeName.VOID.equals(originalMethod.returnType)){
            overloadedBuilder.addCode("return ");
        }
        overloadedBuilder.addCode("$L($L, null);", originalMethod.name, params);

        CodeBlock paramSignature = CodeBlock.join(originalMethod.parameters.stream().map(p -> CodeBlock.of("$T", p.type instanceof ParameterizedTypeName tp ? tp.rawType : p.type)).toList(), ", ");
        overloadedBuilder.annotations.clear();
        overloadedBuilder.addJavadoc("@see $T#$L($L)", VipsOperations_class, originalMethod.name, paramSignature);
        typeSpec.addMethod(overloadedBuilder.build());
    }





}
