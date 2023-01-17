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
import com.pss.jvips.plugin.antlr.csource.ValueHolder;
import com.pss.jvips.plugin.antlr.csource.VisitedCodeBlock;
import com.pss.jvips.plugin.antlr.early.old.EarlyStageOptionalParameter;
import com.pss.jvips.plugin.model.xml.types.Direction;
import com.pss.jvips.plugin.naming.Packages;
import com.pss.jvips.plugin.util.MethodOptionalParametersDocumentation;
import com.pss.jvips.plugin.context.GlobalPluginContext;
import com.pss.jvips.plugin.context.OperationContext;
import com.squareup.javapoet.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.Modifier;
import java.nio.file.Path;
import java.util.*;

import static com.pss.jvips.plugin.naming.JavaTypeMapping.*;
import static com.pss.jvips.plugin.util.Constants.$bitset;

/**
 * Out Params use `long $bitset` field to set/get
 */
public class BaseArgumentDTO extends AbstractArgumentDTO {

    private static final Logger log = LoggerFactory.getLogger(BaseArgumentDTO.class);



    public BaseArgumentDTO(Path task, GlobalPluginContext context) {
        super(task, context, OperationContext.COMMON);
    }


    @Override
    protected Modifier[] modifiers() {
        return new Modifier[]{Modifier.PUBLIC, Modifier.ABSTRACT};
    }

    private void add(CodeBlock.Builder builder, String docletTag, ValueHolder holder){

        if(holder != null && holder.value() != null){
            addNewLineIfNeeded(builder);
            builder.add("@$L", docletTag);
            if(holder.isEnum()) {
                builder.add(" {@link $T#$L}", holder.className(), holder.name().getJavaName());
            } else {
                if(holder.value() instanceof String){
                    builder.add(" $S", holder.value());
                } else {
                    builder.add(" $L", holder.value());
                }

                if(holder.isReference()){
                    if(holder.negated()){
                        builder.add(" -");
                    } else {
                        builder.add(" ");
                    }
                    builder.add("{@link $T#$L}", holder.className(), holder.name().getJavaName());
                    if(holder.minus1()){
                        builder.add(" - 1");
                    }
                }
            }
        }
    }

    private static void addNewLineIfNeeded(CodeBlock.Builder builder) {
        if(!builder.isEmpty()){
            builder.add("\n");
        }
    }


    @Override
    protected void buildDto(TypeSpec.Builder typeSpec, Args arguments) {

        ParameterizedTypeName type =
                ParameterizedTypeName.get(arguments.className, DataType_TypeVariable, UnderlyingSession_TypeVariable);

        MethodOptionalParametersDocumentation visitor = arguments.getEarlyStageResults().get(0);

        visitor.getExecutable().filename().ifPresent(line-> typeSpec.addJavadoc("From $L", line));

        // One method has duplicated names
        Set<String> addedNames = new HashSet<>();


        Optional<VisitedCodeBlock> bl =  Optional.ofNullable(visitor.getSourceCodeVisited());



        if(arguments.hasOut) {
            typeSpec.addField(FieldSpec.builder(TypeName.LONG, $bitset, Modifier.PROTECTED).build());
        }

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

                if(bl.isEmpty()){
                    log.warn("Bl is empty");
                }

                if(JVipsImage_class.equals(t)) {
                    t = JVipsImage_T_class;
                } else if(t.equals(TypeName.OBJECT) && bl.isPresent()){
                    t =  first.map(x-> x.getType()).orElse(TypeName.OBJECT).box();
                    if(JVipsImage_class.equals(t)){
                        t = JVipsImage_T_class;
                    }
                } else if(VipsInterpolate_class.equals(t)){
                    t = String_class;
                }

                FieldSpec.Builder fieldBuilder = FieldSpec.builder(t, parameter.javaName()).addModifiers(Modifier.PROTECTED);
                if(VipsInterpolate_class.equals(original)){
                    AnnotationSpec anno = AnnotationSpec.builder(MagicConstant_class)
                            .addMember("valuesFromClass", "$T.class", VipsInterpolate_class)
                            .build();
                    fieldBuilder.addAnnotation(anno);
                }



                CodeBlock.Builder fieldDoclets = CodeBlock.builder();


                first.ifPresent(f-> {
                   f.defaultValue().ifPresent(x-> add(fieldDoclets, "default", x));
                   f.minValue().ifPresent(x-> add(fieldDoclets, "min", x));
                   f.maxValue().ifPresent(x-> add(fieldDoclets, "max", x));
                });
                CodeBlock doc = parameter.getDocumentation();
                if(doc.isEmpty() && !fieldDoclets.isEmpty()){
                    fieldBuilder.addJavadoc(fieldDoclets.build());
                } else if(!doc.isEmpty()){
                    if(!fieldDoclets.isEmpty()){
                        doc = doc.toBuilder().add("\n\n").add(fieldDoclets.build()).build();
                    }
                    fieldBuilder.addJavadoc(doc);
                }


                AnnotationSpec.Builder anno = AnnotationSpec.builder(OptionalParameter_class)
                        .addMember("value", "$S", parameter.nativeName());
                first.ifPresent(f-> {
                    if(f.getDirection() == Direction.OUT){
                        anno.addMember("direction", "$L",  "OUT");
                    }
                });
                fieldBuilder.addAnnotation(anno.build());
                typeSpec.addField(fieldBuilder.build());

                String name = parameter.javaName();
                String pascalCased = StringUtils.capitalize(parameter.javaName());
                typeSpec.addMethod(MethodSpec.methodBuilder("get" + pascalCased)
                        .returns(t).addModifiers(Modifier.PUBLIC).addStatement("return $L", name).build());
                if(isOut){
                    typeSpec.addMethod(MethodSpec.methodBuilder("include" + pascalCased)
                            .addParameter(TypeName.BOOLEAN, name)
                            .returns(type).addModifiers(Modifier.PUBLIC)
                            .beginControlFlow("if($L)", name)
                            .addStatement("incrementOut()")
                            .addStatement("$L |= 1 << $L", $bitset, i)
                            .nextControlFlow("else")
                            .addStatement("decrementOut()")
                            .addStatement("$L &= ~(1 << $L)", $bitset, i)
                            .endControlFlow()
                            .addStatement("return this").build());
                    i++;
                } else {
                    typeSpec.addMethod(MethodSpec.methodBuilder("set" + pascalCased).addParameter(t, name)
                            .returns(type).addModifiers(Modifier.PUBLIC)
                            .addCode(CodeBlock.builder()
                                    .beginControlFlow("if($L == null)", name)
                                    .addStatement("decrementIn()")
                                    .nextControlFlow("else")
                                    .addStatement("incrementIn()")
                                    .endControlFlow().build()
                            )
                            .addStatement("this.$L = $L", name, name)
                            .addStatement("return this").build());
                }

            }
        }
    }

    @Override
    protected String getPackage() {
        return Packages.Common.Generated.arguments;
    }

    @Override
    protected TypeSpec.Builder addSuperClass(TypeSpec.Builder typeSpec, Args args) {
        return typeSpec.addTypeVariables(List.of(DataType_TypeVariable, UnderlyingSession_TypeVariable))
                .superclass(VipsSerializable_Parameterized);
    }

    @Override
    protected void customize(JavaFile.Builder customize) {

        customize.addStaticImport(OptionalParameter$Direction_class, "OUT");
    }
}
