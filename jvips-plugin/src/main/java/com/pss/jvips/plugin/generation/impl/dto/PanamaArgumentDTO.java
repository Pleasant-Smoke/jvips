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

package com.pss.jvips.plugin.generation.impl.dto;

import com.pss.jvips.plugin.service.executables.arguments.EarlyStageArgumentDTO;
import com.pss.jvips.plugin.antlr.early.old.EarlyStageOptionalParameter;
import com.pss.jvips.plugin.antlr.csource.VisitedCodeBlock;
import com.pss.jvips.plugin.model.xml.types.Direction;
import com.pss.jvips.plugin.naming.JavaTypeMapping;
import com.pss.jvips.plugin.naming.Packages;
import com.pss.jvips.plugin.util.MethodOptionalParametersDocumentation;
import com.pss.jvips.plugin.context.GlobalPluginContext;
import com.pss.jvips.plugin.context.OperationContext;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.nio.file.Path;
import java.util.*;

import static com.pss.jvips.plugin.naming.JavaTypeMapping.*;
import static com.pss.jvips.plugin.util.Constants.*;


/**
 * Out Params use `long $bitset` field to set/get
 */
public class PanamaArgumentDTO extends AbstractArgumentDTO {



//    final Map<UUID, ClassName> methodToDtoName;
//    protected boolean addStaticImport;
//    final List<MethodOptionalParametersDocumentation> visitors;
//    final ArgumentDTOInfo info;

    public PanamaArgumentDTO(Path task, GlobalPluginContext context) {
        super(task, context, OperationContext.PANAMA);
    }

    public static final Map<TypeName, String> pointers = Map.of(TypeName.INT, "C_INT", TypeName.DOUBLE, "C_DOUBLE", TypeName.LONG, "C_LONG");


    @Override
    protected Modifier[] modifiers() {
        return new Modifier[0];
    }

    @Override
    protected void buildDto(TypeSpec.Builder typeSpec, Args arguments) {
        MethodOptionalParametersDocumentation visitor = arguments.getEarlyStageResults().get(0);
        visitor.getExecutable().filename()
                .ifPresent(line-> {
                    typeSpec.addJavadoc("From $L", line);
                });

        // One method has duplicated names
        Set<String> addedNames = new HashSet<>();

        ArrayTypeName objArray = ArrayTypeName.of(Object.class);

        MethodSpec.Builder serializeMethod = MethodSpec.methodBuilder("serialize")
                .addParameter(MemorySession_class, serialize$ctx)
                .addParameter(TypeName.INT, in$index)
                .addParameter(objArray, in$params)
                .addParameter(TypeName.INT, out$index)
                .addParameter(objArray, out$params)
                .addModifiers(Modifier.PUBLIC);

        MethodSpec.Builder deserializeMethod = MethodSpec.methodBuilder("deserialize")
                .addParameter(MemorySession_class, deserialize$ctx)
                .addParameter(TypeName.INT, out$index)
                .addParameter(objArray, out$params)
                .addModifiers(Modifier.PUBLIC);



        Optional<VisitedCodeBlock> bl =  Optional.ofNullable(visitor.getSourceCodeVisited());



        int i = 1;

        for (EarlyStageOptionalParameter parameter : visitor.getOptionalParameters().values()) {
            if(addedNames.add(parameter.javaName())) {
                //@todo: Don't forget out params should not have a setter, or at least a public one?
                TypeName t = parameter.getType().get().box();

                TypeName original = t;

                Optional<EarlyStageArgumentDTO> first = bl
                        .flatMap(y-> y.arguments().values().stream().filter(x -> x != null && x.getName().equals(parameter.nativeName()))
                                .findFirst());
                boolean isOut = first.map(x-> x.getDirection() == Direction.OUT).orElse(Boolean.FALSE);

                if(t.equals(TypeName.OBJECT) && bl.isPresent()){
                    t =  first.map(x-> x.getType()).orElse(TypeName.OBJECT).box();
                    if(t.equals(JVipsImage_class)){
                        t = JVipsImage_MemorySession_class;
                    }
                } else if(VipsInterpolate_class.equals(t)){
                    t = String_class;
                }



                String name = parameter.javaName();


                String typeConstant = getTypeConstant(t.isBoxedPrimitive() ? t.unbox() : t);
                if(isOut){

                    String outPtr = name + "$ptr";

                    serializeMethod
                            .beginControlFlow("if((($L >> $L) & 1) == 1)", $bitset, i)
                                .addStatement("$T $L = $L.allocate(C_POINTER)", MemorySegment_class, outPtr, serialize$ctx)
                                .addStatement("$L[++$L] = $L.allocateUtf8String($S)", in$params, in$index, serialize$ctx,  parameter.nativeName())
                                .addStatement("$L[++$L] = $L", in$params, in$index, outPtr)
                                .addStatement("$L[++$L] = $L", out$params, out$index, outPtr)
                            .endControlFlow();



                    deserializeMethod.beginControlFlow("if((($L >> $L) & 1) == 1)", $bitset, i)
                            .addStatement("$T $L = ($T) $L[++$L]", MemorySegment_class, outPtr, MemorySegment_class, out$params, out$index);
                    if(JavaTypeMapping.isRegularEnum(t) || JavaTypeMapping.isNegativeEnum(t)){
                        deserializeMethod.addStatement("this.$L = $T.getType($L.get(C_INT, 0))", name, t, outPtr);
                    } else if(t.isBoxedPrimitive()) {
                        TypeName unboxed = t.unbox();
                        String s = pointers.get(unboxed);
                        deserializeMethod.addStatement("this.$L = $L.get($L, 0)", name, outPtr, s);
                    }
                    deserializeMethod.endControlFlow();
                    i++;
                } else {

                    serializeMethod.beginControlFlow("if($L != null)", name)
                            .addStatement("$L[++$L] = $L.allocateUtf8String($S)", in$params, in$index, serialize$ctx,  parameter.nativeName());
                    if(JavaTypeMapping.isRegularEnum(original)){
                        serializeMethod.addStatement("$L[++$L] = $L.ordinal()", in$params, in$index, name);
                    } else if(JavaTypeMapping.isNegativeEnum(original)){
                        serializeMethod.addStatement("$L[++$L] = $L.getValue()", in$params, in$index, name);
                    } else {
                        serializeMethod.addStatement("$L[++$L] = $L", in$params, in$index, name);
                    }
                    serializeMethod.endControlFlow();
                }

            }
        }
        serializeMethod.addStatement("$L[++$L] = $T.$L", in$params, in$index, MemoryAddress_class, "NULL");

        typeSpec.addMethod(serializeMethod.build()).addMethod(deserializeMethod.build());
    }

    @Override
    protected String getPackage() {
        return Packages.Panama.Generated.arguments;
    }

    @Override
    protected TypeSpec.Builder addSuperClass(TypeSpec.Builder typeSpec, Args args) {
        if(args.hasImage){
            return typeSpec
                    .superclass(ParameterizedTypeName.get(args.parentClassName, MemorySegment_class, MemorySession_class, MemorySession_class));
        }
        return typeSpec
                .superclass(ParameterizedTypeName.get(args.parentClassName, MemorySession_class, MemorySession_class));
    }

    @Override
    protected void customize(JavaFile.Builder customize) {
        customize.addStaticImport(LibVips_class, "*");
    }
}
