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

import com.pss.jvips.plugin.antlr.ParserFactoryOld;
import com.pss.jvips.plugin.antlr.csource.VisitedCodeBlock;
import com.pss.jvips.plugin.generation.CodeGeneratorOld;
import com.pss.jvips.plugin.generation.impl.RecordGenerator;
import com.pss.jvips.plugin.model.dto.parameters.BasicWritableParam;
import com.pss.jvips.plugin.model.dto.parameters.OptionalDTOParam;
import com.pss.jvips.plugin.model.dto.parameters.OptionalParam;
import com.pss.jvips.plugin.model.dto.parameters.WritableParameter;
import com.pss.jvips.plugin.model.xml.executable.AbstractExecutable;
import com.pss.jvips.plugin.model.xml.executable.Parameter;
import com.pss.jvips.plugin.model.xml.types.Direction;
import com.pss.jvips.plugin.naming.JavaTypeMapping;
import com.pss.jvips.plugin.out.OutParam;
import com.pss.jvips.plugin.util.MethodOptionalParametersDocumentation;
import com.pss.jvips.plugin.util.Utils;
import com.pss.jvips.plugin.context.GlobalPluginContext;
import com.pss.jvips.plugin.context.OperationContext;
import com.pss.jvips.plugin.context.ScopedPluginContext;
import com.squareup.javapoet.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.Modifier;
import java.nio.file.Path;
import java.util.*;

import static com.pss.jvips.plugin.naming.JavaTypeMapping.*;

public abstract class OperationGenerator<A extends OperationGenerator.OperationGeneratorContext> extends CodeGeneratorOld<A, TypeSpec> {

    private static final Logger log = LoggerFactory.getLogger(OperationGenerator.class);

    protected final RecordGenerator recordGenerator;

    public OperationGenerator(Path task, GlobalPluginContext context, OperationContext operationContext) {
        super(task, context, operationContext);
        this.recordGenerator = new RecordGenerator(task, context);
    }

    protected abstract ParameterizedTypeName parameterizedContextClass();

    protected abstract ParameterizedTypeName parameterizedOperationClass();

    protected abstract ParameterizedTypeName parameterizedImageClass();

    protected abstract ClassName className();

    protected abstract Modifier[] modifiers();

    @Override
    protected TypeSpec.Builder getTypeSpec(A arguments) {
        return TypeSpec.classBuilder(className())
                .addModifiers(modifiers());
    }

    protected boolean canAdd(AbstractExecutable executable){
        return Objects.equals(executable.getIntrospectable(), Boolean.TRUE)
                && !executable.isExclude()
                && StringUtils.isNotEmpty(executable.getName());
    }

    @Override
    protected final Result<TypeSpec> runInternal(TypeSpec.Builder typeSpec, A arguments) {
        addConstructor(typeSpec);
        for(var executable : arguments.getExecutables()){
            if (canAdd(executable)) {
                addExecutables(arguments, typeSpec, executable);
            }
        }
        return new Result<>(typeSpec.build(), className());
    }

    protected void addExecutables(A arguments, TypeSpec.Builder typeSpec, AbstractExecutable executable) {
        MethodSpec.Builder method = MethodSpec.methodBuilder(executable.getJavaName().getJavaName())
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(generateNativeAnnotationSpec(executable))
                .addException(JavaTypeMapping.VipsException_class);
        executable.documentation()
                .ifPresent((String documentation) -> method.addJavadoc("$L", ParserFactoryOld.getMethodDoc(documentation, new ScopedPluginContext(context, executable, VipsOperations_class))));
        List<WritableParameter> writableParams = executable.getWritableParams();
        writableParams.forEach(x-> x.addParameters(method));
        Set<OutParam> outParams = new LinkedHashSet<>();
        writableParams.forEach(x-> x.registerOutParams(outParams));
        TypeName returnType =  resolveReturnType(executable, writableParams);
        method.returns(returnType);
        customize(method);
        if(context.isSingularOptionalParam(executable)){
            buildWithSingularOptionalParam(typeSpec, method, executable, writableParams, returnType);
        } else if(context.isDTOOptionalParam(executable)){
            buildWithDTOOptionalParam(typeSpec, method, executable, writableParams, returnType);
        } else {
            buildWithOutOptional(typeSpec, method, executable, writableParams, returnType);
        }
    }

    protected void addConstructor(TypeSpec.Builder typeSpec){

    }

    protected MethodSpec.Builder createAbstractConstructor(){
        return createConstructor(JavaTypeMapping.VipsOperationContext_class, DataType_TypeVariable, JavaTypeMapping.UnderlyingSession_TypeVariable, CodeBlock.of("this.context = context"));
    }

    protected MethodSpec.Builder createSuperConstructor(TypeName type, TypeName type2){
        return createConstructor(JavaTypeMapping.VipsOperationContext_class, type, type2, CodeBlock.of("super(context)"));
    }

    protected MethodSpec.Builder createConstructor(ClassName type, TypeName param, TypeName param2, CodeBlock body){
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ParameterSpec.builder(ParameterizedTypeName.get(type, param, param2), "context").build())
                .addStatement(body);
    }

    protected TypeName resolveReturnType(AbstractExecutable executable, List<WritableParameter> params){
        Set<OutParam> outParams = new LinkedHashSet<>();
        params.forEach(param-> param.registerOutParams(outParams));
        TypeName nativeReturnType = executable.getReturnValue().constructType();
        if(outParams.size() == 0 && (TypeName.VOID.equals(nativeReturnType) || TypeName.INT.equals(nativeReturnType))){
            return TypeName.VOID;
        } else if(outParams.size() == 0){
            log.info("Executable {} returns type: {}", executable.getName(), nativeReturnType);
            return nativeReturnType;
        } else if(outParams.size() == 1){
            TypeName type = Utils.getFirst(outParams).type();
            if(JavaTypeMapping.JVipsImage_class.equals(type)){
                return parameterizedImageClass();
            }
            return type;
        } else {
            log.info("Executable returns the following: {}, generating a new class", outParams);
            return recordGenerator.run(new RecordGenerator.RecordArgs(executable, outParams));
        }
    }

    protected MethodSpec.Builder newFromFile(){
        return MethodSpec.methodBuilder("newImageFromFile").addParameter(ParameterSpec.builder(String_class, "fileName").build()).addModifiers(Modifier.PUBLIC)
                .addException(VipsException_class)
                .returns(parameterizedImageClass());
    }

    protected void customize(MethodSpec.Builder builder){

    }

    protected abstract void buildWithOutOptional(TypeSpec.Builder typeSpec, MethodSpec.Builder methodSpec, AbstractExecutable executable, List<WritableParameter> params, TypeName returnType);

    protected final void buildWithSingularOptionalParam(TypeSpec.Builder typeSpec, MethodSpec.Builder methodSpec, AbstractExecutable executable, List<WritableParameter> params, TypeName returnType){
        MethodOptionalParametersDocumentation visitor = context.getSingularOptionalParam(executable);
        Optional<VisitedCodeBlock> bl =  Optional.ofNullable(visitor.getSourceCodeVisited());
        String firstParam1 =  Utils.getFirst(visitor.getOptionalParameters().keySet());
        var firstParam =  Utils.getFirst(visitor.getOptionalParameters().values());
        TypeName boxedType = Utils.toPerformantReferenceType(bl.map(x-> x.arguments().get(firstParam1)).map(x-> x.getType()).orElseGet(()->firstParam.getType().get()));

        params.add(new OptionalParam(new BasicWritableParam(boxedType, Direction.IN, firstParam.getDocumentation().toString(), firstParam.getName(), context), context));
        // Add optional Param to original method
        ParameterSpec parameterSpec = ParameterSpec.builder(boxedType, firstParam.javaName())
                .addAnnotation(
                        AnnotationSpec.builder(JavaTypeMapping.OptionalParameter_class)
                                .addMember("value", "$S", firstParam.nativeName())
                                .build())
                .build();
        buildWithSingularOptionalParam(typeSpec, methodSpec, executable, params, returnType, parameterSpec);
    }

    protected abstract void buildWithSingularOptionalParam(TypeSpec.Builder typeSpec, MethodSpec.Builder methodSpec,
                                                           AbstractExecutable executable, List<WritableParameter> params, TypeName returnType, ParameterSpec parameterSpec);

    /**
     * Do not add parameter to method spec {@link GenerateAbstractOperations#buildWithDTOOptionalParam(TypeSpec.Builder, MethodSpec.Builder, AbstractExecutable, List, TypeName, ParameterSpec)}
     *
     *
     * @param typeSpec
     * @param methodSpec
     * @param executable
     * @param params
     * @param returnType
     */
    protected final void buildWithDTOOptionalParam(TypeSpec.Builder typeSpec, MethodSpec.Builder methodSpec, AbstractExecutable executable, List<WritableParameter> params, TypeName returnType) {
        ClassName dto = context.getDtoOptionalParam(executable);
        ParameterizedTypeName parameterizedDTO = ParameterizedTypeName.get(dto, context.operationContext().getDtoTypes());

        params.add(new OptionalDTOParam(parameterizedDTO, context));

        ParameterSpec parameterSpec = ParameterSpec.builder(parameterizedDTO , "args")
                .addAnnotation(
                        AnnotationSpec.builder(JavaTypeMapping.OptionalParameter_class)
                                .build())
                .build();

        // Do not add to method spec
        buildWithDTOOptionalParam(typeSpec, methodSpec, executable, params, returnType, parameterSpec);
    }

    protected abstract void buildWithDTOOptionalParam(TypeSpec.Builder typeSpec, MethodSpec.Builder methodSpec, AbstractExecutable executable, List<WritableParameter> params, TypeName returnType, ParameterSpec parameterSpec);



    protected AnnotationSpec generateNativeAnnotationSpec(AbstractExecutable executable){
        AnnotationSpec.Builder annotationSpec = AnnotationSpec.builder(JavaTypeMapping.NativeExecutable_class)
                .addMember("name", "$S", executable.identifier())
                .addMember("returns", "$S", executable.nativeReturnType());

        for (Parameter parameter : executable.getParameters()) {
            annotationSpec.addMember("parameters", "@$T(value = $S, type = $S)", NativeParameter_class, parameter.getName(), parameter.getNativeType());
        }
        return annotationSpec.build();
    }



    public static class OperationGeneratorContext {
        private final List<AbstractExecutable> executables;

        public OperationGeneratorContext(List<AbstractExecutable> executables) {
            this.executables = executables;
        }

        public List<AbstractExecutable> getExecutables() {
            return executables;
        }
    }
}
