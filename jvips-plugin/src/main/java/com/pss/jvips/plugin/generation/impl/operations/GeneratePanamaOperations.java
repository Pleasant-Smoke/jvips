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

import com.pss.jvips.plugin.generation.impl.dto.PanamaArgumentDTO;
import com.pss.jvips.plugin.model.dto.parameters.*;
import com.pss.jvips.plugin.model.xml.executable.AbstractExecutable;
import com.pss.jvips.plugin.model.xml.types.Direction;
import com.pss.jvips.plugin.out.OutParam;
import com.pss.jvips.plugin.util.Utils;
import com.pss.jvips.plugin.context.GlobalPluginContext;
import com.pss.jvips.plugin.context.OperationContext;
import com.squareup.javapoet.*;
import org.gradle.api.Task;

import javax.lang.model.element.Modifier;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.pss.jvips.plugin.naming.JavaTypeMapping.*;
import static com.pss.jvips.plugin.util.Constants.*;

public class GeneratePanamaOperations extends OperationGenerator<OperationGenerator.OperationGeneratorContext> {

    private static final String $session = "$session";
    private static final String $i = "$i";

    public GeneratePanamaOperations(Path task, GlobalPluginContext context) {
        super(task, context, OperationContext.PANAMA);
    }

    @Override
    protected ParameterizedTypeName parameterizedContextClass() {
        return VipsOperationContext_MemorySession_class;
    }

    @Override
    protected ParameterizedTypeName parameterizedOperationClass() {
        return VipsOperations_MemorySegment_class;
    }

    @Override
    protected ParameterizedTypeName parameterizedImageClass() {
        return JVipsImage_MemorySession_class;
    }

    @Override
    protected ClassName className() {
        return PanamaVipsOperations_class;
    }

    @Override
    protected Modifier[] modifiers() {
        return new Modifier[]{Modifier.PUBLIC};
    }


    @Override
    protected TypeSpec.Builder getTypeSpec(OperationGeneratorContext arguments) {
        return super.getTypeSpec(arguments).superclass(parameterizedOperationClass());
    }

    @Override
    protected void addConstructor(TypeSpec.Builder typeSpec) {
        typeSpec.addMethod(createSuperConstructor(MemorySegment_class, MemorySession_class).addParameter(ParameterSpec.builder(MemorySession_class, $session).build())
                        .addStatement("this.$L = $L", $session, $session)
                .build());
        typeSpec.addField(MemorySession_class, $session, Modifier.PRIVATE, Modifier.FINAL);
    }




    protected void buildInternal(TypeSpec.Builder typeSpec, MethodSpec.Builder method,
                                 AbstractExecutable executable, List<WritableParameter> writableParams,
                                 TypeName type, CodeBlock call, boolean hasOptional) {



        CodeBlock.Builder builder = CodeBlock.builder();
        writableParams.forEach(x-> x.addPreInit(method));
        var iterator = writableParams.iterator();
//        if(iterator.hasNext()){
//            iterator.next().addArrayInitializer(0,0, builder, iterator);
//        }
        List<CodeBlock> locks = new ArrayList<>();
        Set<OutParam> outParams = new LinkedHashSet<>();
        writableParams.forEach(x-> x.registerOutParams(outParams));
        writableParams.forEach(x-> x.addImageLocks(locks));
        writableParams.forEach(x-> x.addImageArrayLocks(locks));
        CodeBlock lockParameters = CodeBlock.join(locks, ", ");

        boolean requiresLock = false;
        boolean drawMethod = Utils.requiresWriteLock(executable);

        if(!writableParams.isEmpty()){
            iterator = writableParams.iterator();
            requiresLock = iterator.next().requiresLock(iterator);
        }


        if(!writableParams.isEmpty()) {


//            builder.addStatement("int $L = -1", in$index);
//            builder.addStatement("int $L = -1", out$index);


            builder.add("$L", call);
            CodeBlock.Builder outLoop = CodeBlock.builder();

//            builder.addStatement("$L = 2", out$index);
//           writableParams.forEach(x-> x.addPostInit(builder));


            if(drawMethod) {
//                impl.returns(TypeName.VOID);
            } else if(type.isPrimitive()){
                builder.beginControlFlow("if($S.equals($L[0]) && $L[2] != null)", "out", out$params, out$params)
                        .addStatement("return ($T) $L[2]", type, out$params)
                        .endControlFlow()
                        .addStatement("throw new VipsException($S)", "Return value was null");
            } else if(parameterizedImageClass().equals(type)){
                builder.addStatement("$T outAddress = out.get(C_POINTER, 0)", MemoryAddress_class)
                        .addStatement("$T outSegment = $T.ofAddress(outAddress, $L)", MemorySegment_class, VipsImage_class, $session)
                        .addStatement("return context.createImage(0L, outSegment, Thread.currentThread())");

            } else if(outParams.size() > 1){
                //impl.beginControlFlow("for(int i = 0; i < $L.length; i +=3)", out$params);
                String $name = "$name";
                List<CodeBlock> blocks = new ArrayList<>();
                int i = 0;
                for (OutParam outParam : outParams) {
                    if(JVipsImage_class.equals(type)){
                        blocks.add(
                                CodeBlock.of("context.createImage($L, (Long) $L[$L], Thread.currentThread())", outParam.name() + "$id", out$params, i)
                        );
                    } else {
                        TypeName type1 = outParam.type();
                        if(type1.isBoxedPrimitive()){
                            type1 = type1.unbox();
                        }
                        String s = PanamaArgumentDTO.pointers.get(type1);
                        blocks.add(
                                CodeBlock.of("$L.get($L, 0)", outParam.name(), s)
                        );
                    }
                    i+=3;
                }
                CodeBlock joined = CodeBlock.join(blocks, ", ");
                builder.addStatement("return new $T($L)", type, joined);
            }
        }

        if(requiresLock && drawMethod) {
            method.addCode("$T.writeLock($L, ()-> { \n$>$L $<\n});", Locking_class, lockParameters, builder.build());
        } else if(requiresLock && outParams.isEmpty()){
            method.addCode("$T.lock($L, ()-> { \n$>$L $<\n});", Locking_class, lockParameters, builder.build());
        } else if(requiresLock){
            method.addCode("return $T.lock($L, ()-> { \n$>$L $<\n});", Locking_class, lockParameters, builder.build());
        } else {
            method.addCode("$L", builder.build());
        }

        typeSpec.addMethod(method.build());
    }


    @Override
    protected void customize(JavaFile.Builder customize) {
        customize.addStaticImport(LibVips_class, "*");
        customize.addStaticImport(MemoryAddress_class, "*");
    }

    @Override
    protected void buildWithOutOptional(TypeSpec.Builder typeSpec, MethodSpec.Builder methodSpec, AbstractExecutable executable, List<WritableParameter> params, TypeName returnType) {
        CodeBlock.Builder code = CodeBlock.builder();
        List<CodeBlock> methodCallParameters = new ArrayList<>();
        extracted(params, code, methodCallParameters);
        code.addStatement("$L($L, NULL)",  executable.nativeName(), CodeBlock.join(methodCallParameters, ", "));
        buildInternal(typeSpec, methodSpec, executable, params, returnType, code.build(), false);
    }

    @Override
    protected void buildWithSingularOptionalParam(TypeSpec.Builder typeSpec, MethodSpec.Builder methodSpec, AbstractExecutable executable, List<WritableParameter> params, TypeName returnType, ParameterSpec parameterSpec) {
        methodSpec.addParameter(parameterSpec);
        CodeBlock.Builder code = CodeBlock.builder();
        List<CodeBlock> methodCallParameters = new ArrayList<>();
        extracted(params, code, methodCallParameters);
        OptionalParam writableParameter = (OptionalParam) params.get(params.size() - 1);
        code.beginControlFlow("if($L == null)", writableParameter.getParam().getJavaName())
                .addStatement("$L($L, NULL)",  executable.nativeName(), CodeBlock.join(methodCallParameters, ", "))
                .nextControlFlow("else")
                .addStatement("$L($L, $S, $L, NULL)",  executable.nativeName(), CodeBlock.join(methodCallParameters, ", "), writableParameter.getParam().getNativeName(), writableParameter.getParam().getJavaName())
                .endControlFlow();
        buildInternal(typeSpec, methodSpec, executable, params, returnType, code.build(), false);
    }

    @Override
    protected void buildWithDTOOptionalParam(TypeSpec.Builder typeSpec, MethodSpec.Builder methodSpec, AbstractExecutable executable, List<WritableParameter> params, TypeName returnType, ParameterSpec parameterSpec) {
        methodSpec.addParameter(parameterSpec);
        CodeBlock.Builder code = CodeBlock.builder();
        List<CodeBlock> methodCallParameters = new ArrayList<>();

        extracted(params, code, methodCallParameters);
        code
                .addStatement("int $L", $i)
                .beginControlFlow("if(args != null && ($L = args.getInSize() + args.getOutSize()) > 0)", $i)
                .addStatement("$T $L = new Object[($L * 2) + 1]", Object_Array, in$params, $i)
                .addStatement("$T $L = new Object[args.getOutSize()]", Object_Array, out$params)
                .addStatement("int $L = -1", in$index)
                .addStatement("int $L = -1", out$index)
                .addStatement("$L($L, $L)",  executable.nativeName(), CodeBlock.join(methodCallParameters, ", "), in$params)
                .addStatement("args.deserialize($L, $L, $L)", $session, out$index,  out$params)
                .nextControlFlow("else")
                .addStatement("$L($L, NULL)",  executable.nativeName(), CodeBlock.join(methodCallParameters, ", "))
                .endControlFlow();


        buildInternal(typeSpec, methodSpec, executable, params, returnType, code.build(), false);
    }

    private static void extracted(List<? extends WritableParameter> params, CodeBlock.Builder code, List<CodeBlock> methodCallParameters) {
        for (WritableParameter param : params) {
            if(param instanceof BasicWritableParam bwp){
                if(bwp.getDirection() == Direction.IN && String_class.equals(bwp.getTypeName())){
                    String name = bwp.getJavaName() + "$cstring";
                    code.addStatement("$T $L = $L.allocateUtf8String($L)", MemorySegment_class, name, $session, bwp.getJavaName());
                    methodCallParameters.add(CodeBlock.of("$L", name));
                } else if(bwp.getDirection() == Direction.IN){
                    if(isNegativeEnum(bwp.getTypeName())){
                        methodCallParameters.add(CodeBlock.of("$L.getValue()", bwp.getJavaName()));
                    } else if(isRegularEnum(bwp.getTypeName())){
                        methodCallParameters.add(CodeBlock.of("$L.ordinal()", bwp.getJavaName()));
                    } else {
                        methodCallParameters.add(CodeBlock.of("$L", bwp.getJavaName()));
                    }
                    methodCallParameters.add(CodeBlock.of("$L", bwp.getJavaName()));
                } else {
                    code.addStatement("$T $L = $L.allocate(C_POINTER)", MemorySegment_class, bwp.getJavaName(), $session);
                    methodCallParameters.add(CodeBlock.of("$L", bwp.getJavaName()));
                }
            } else if(param instanceof ImageParam ip){
                if(ip.getParam().getDirection() == Direction.IN){
                    methodCallParameters.add(CodeBlock.of("$L.getAddress()", ip.getParam().getJavaName()));
                } else {
                    code.addStatement("$T $L = $L.allocate(C_POINTER)", MemorySegment_class, ip.getParam().getJavaName(), $session);
                    methodCallParameters.add(CodeBlock.of("$L", ip.getParam().getJavaName()));
                }
            } else if(param instanceof ComposedParam cp){
                extracted(cp.getParams(), code, methodCallParameters);
                ParameterWithLength parameterWithLength = cp.getParams().get(0);
                methodCallParameters.add(CodeBlock.of("$L.$L", parameterWithLength.javaName(), parameterWithLength.getLengthProperty()));
            } else if(param instanceof ByteBufferParam bbp){
                methodCallParameters.add(CodeBlock.of("$T.ofBuffer($L)", MemorySegment_class, bbp.javaName()));
            }
        }
    }


}
