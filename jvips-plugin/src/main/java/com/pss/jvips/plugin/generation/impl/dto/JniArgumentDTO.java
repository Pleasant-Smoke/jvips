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
import com.pss.jvips.plugin.antlr.csource.VisitedCodeBlock;
import com.pss.jvips.plugin.antlr.early.old.EarlyStageOptionalParameter;
import com.pss.jvips.plugin.model.xml.types.Direction;
import com.pss.jvips.plugin.naming.JavaTypeMapping;
import com.pss.jvips.plugin.naming.Packages;
import com.pss.jvips.plugin.util.Constants;
import com.pss.jvips.plugin.util.MethodOptionalParametersDocumentation;
import com.pss.jvips.plugin.context.GlobalPluginContext;
import com.pss.jvips.plugin.context.OperationContext;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.nio.file.Path;
import java.util.*;

import static com.pss.jvips.plugin.naming.JavaTypeMapping.*;
import static com.pss.jvips.plugin.util.Constants.$bitset;

/**
 * Out Params use `long $bitset` field to set/get
 */
public class JniArgumentDTO extends AbstractArgumentDTO {



    public JniArgumentDTO(Path task, GlobalPluginContext context) {
        super(task, context, OperationContext.JNI);
    }


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
                .addParameter(context.operationContext().getContext(), "serializeContext")
                .addParameter(TypeName.INT, Constants.in$index)
                .addParameter(objArray, Constants.in$params)
                .addParameter(TypeName.INT, Constants.out$index)
                .addParameter(objArray, Constants.out$params)
                .addModifiers(Modifier.PUBLIC);

        MethodSpec.Builder deserializeMethod = MethodSpec.methodBuilder("deserialize")
                .addParameter(context.operationContext().getContext(), "deserializeContext")
                .addParameter(TypeName.INT, Constants.out$index)
                .addParameter(objArray, Constants.out$params)
                .addModifiers(Modifier.PUBLIC);



        Optional<VisitedCodeBlock> bl =  Optional.ofNullable(visitor.getSourceCodeVisited());



        int i = 1;
        List<EarlyStageOptionalParameter> out = new ArrayList<>();
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
                    t =  first.map(x-> x.getType()).orElseThrow().box();
                    if(t.equals(JVipsImage_class)){
                        t = JVipsImage_Long_class;
                    }
                } else if(VipsInterpolate_class.equals(t)){
                    t = String_class;
                }



                String name = parameter.javaName();

                String typeConstant;
                if(t instanceof ParameterizedTypeName ptn){
                    typeConstant = getTypeConstant(ptn.rawType);
                } else {
                     typeConstant = getTypeConstant(t.isBoxedPrimitive() ? t.unbox() : t);
                }

                if(isOut){

                    serializeMethod.beginControlFlow("if((($L >> $L) & 1) == 1)", $bitset, i)
                            .addStatement("$L[++$L] = $S", Constants.out$params, Constants.out$index, parameter.nativeName())
                            .addStatement("$L[++$L] = $T.$L", Constants.out$params, Constants.out$index, VipsNative_class, typeConstant)
                            .addStatement("$L[++$L] = null", Constants.out$params, Constants.out$index)
                            .endControlFlow();

                    out.add(parameter);

                    deserializeMethod.beginControlFlow("if((($L >> $L) & 1) == 1)", $bitset, i)
                            .addStatement("$L += 3", Constants.out$index);
                    if(JavaTypeMapping.isRegularEnum(t) || JavaTypeMapping.isNegativeEnum(t)){
                        deserializeMethod.addStatement("this.$L = $T.getType(($T) $L[$L])", name, t, Integer.class, Constants.out$params, Constants.out$index);
                    } else {
                        deserializeMethod.addStatement("this.$L = ($T) $L[$L]", name, t, Constants.out$params, Constants.out$index);
                    }
                    deserializeMethod.endControlFlow();
                    i++;
                } else {

                    serializeMethod.beginControlFlow("if($L != null)", name)
                            .addStatement("$L[++$L] = $S", Constants.in$params, Constants.in$index, parameter.nativeName())
                            .addStatement("$L[++$L] = $T.$L", Constants.in$params, Constants.in$index,  VipsNative_class, typeConstant);
                    if(JavaTypeMapping.isRegularEnum(original)){
                        serializeMethod.addStatement("$L[++$L] = $L.ordinal()", Constants.in$params, Constants.in$index, name);
                    } else if(JavaTypeMapping.isNegativeEnum(original)){
                        serializeMethod.addStatement("$L[++$L] = $L.getValue()", Constants.in$params, Constants.in$index, name);
                    } else {
                        serializeMethod.addStatement("$L[++$L] = $L", Constants.in$params, Constants.in$index, name);
                    }

                    serializeMethod.endControlFlow();
                }

            }
        }


        typeSpec.addMethod(serializeMethod.build()).addMethod(deserializeMethod.build());

    }

    @Override
    protected String getPackage() {
        return Packages.Jni.Generated.arguments;
    }

    @Override
    protected TypeSpec.Builder addSuperClass(TypeSpec.Builder typeSpec, Args args) {
        return typeSpec.addModifiers(Modifier.PUBLIC)
                .superclass(ParameterizedTypeName.get(args.parentClassName, context.operationContext().getDtoTypes()));
    }

    @Override
    protected void customize(JavaFile.Builder customize) {
        customize.addStaticImport(OptionalParameter$Direction_class, "OUT");
    }
}
