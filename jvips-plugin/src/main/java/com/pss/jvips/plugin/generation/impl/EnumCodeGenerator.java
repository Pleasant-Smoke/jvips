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
import com.pss.jvips.plugin.model.xml.Member;
import com.pss.jvips.plugin.model.xml.toplevel.Enumeration;
import com.pss.jvips.plugin.naming.JavaCaseFormat;
import com.pss.jvips.plugin.naming.JavaNaming;
import com.pss.jvips.plugin.naming.JavaTypeMapping;
import com.pss.jvips.plugin.context.GlobalPluginContext;
import com.pss.jvips.plugin.context.OperationContext;
import com.pss.jvips.plugin.naming.TypeRegistration;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

import static com.pss.jvips.plugin.naming.JavaTypeMapping.NegativeEnum_class;

public class EnumCodeGenerator extends CodeGeneratorOld<Enumeration, Object> {

    public EnumCodeGenerator(Path task, GlobalPluginContext context) {
        super(task, context, OperationContext.COMMON);
    }

    @Override
    protected CodeGeneratorOld.Result<Object> runInternal(final TypeSpec.Builder typeSpec, Enumeration enumeration) {

        enumeration.documentation().ifPresent(typeSpec::addJavadoc);

        // Sort
        List<Member> members = enumeration.getMembers()
                .stream()
                .sorted(Comparator.comparingInt(a -> Integer.parseInt(a.getValue()))).toList();

        boolean isNegative = (!members.isEmpty() && "-1".equals(members.get(0).getValue()));
        if(isNegative){
            typeSpec.addSuperinterface(NegativeEnum_class);
            JavaTypeMapping.registerNegativeEnum(enumeration.getClassName());
        } else {
            JavaTypeMapping.registerEnum(enumeration.getClassName());
        }

        for (Member member : members) {
            // The addEnumCOnstant adds an anonymous class anyways
            TypeSpec.Builder builder;
            if(isNegative){
                builder = TypeSpec.anonymousClassBuilder("$L", member.getValue());
            } else {
                builder = TypeSpec.anonymousClassBuilder("");
            }

            member.documentation().ifPresent(doc-> builder.addJavadoc(doc));


            JavaCaseFormat javaCaseFormat = JavaNaming.registerConstant(member.getIdentifier());
            TypeRegistration.registerEnum(javaCaseFormat, enumeration.getClassName());

            typeSpec.addEnumConstant(javaCaseFormat.getJavaName(), builder.build());

        }



        FieldSpec types = FieldSpec.builder(ArrayTypeName.of(enumeration.getClassName()), "TYPES")
                .initializer("$T.values()", enumeration.getClassName())
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                .build();
        typeSpec.addField(types);

        if(isNegative){
            typeSpec.addMethod(MethodSpec.constructorBuilder().addParameter(TypeName.INT, "value").addStatement("this.value = value").build());
            typeSpec.addField(TypeName.INT, "value", Modifier.PRIVATE, Modifier.FINAL);
            MethodSpec valueMethod = MethodSpec.methodBuilder("getValue")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(TypeName.INT)
                    .addStatement("return value")
                    .build();

            typeSpec.addMethod(valueMethod);
        }

        MethodSpec getType = MethodSpec.methodBuilder("getType")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(enumeration.getClassName())
                .addParameter(TypeName.INT, "v")
                .addJavadoc("Fail Fast should throw exception if out of bounds etc")
                .addStatement("return TYPES[$L]", isNegative ? "++v": "v").build();

        typeSpec.addMethod(getType);
        return new Result<Object>(null, enumeration.getClassName());
    }

    @Override
    protected TypeSpec.Builder getTypeSpec(Enumeration arguments) {
        return TypeSpec.enumBuilder(arguments.getClassName())
                .addModifiers(Modifier.PUBLIC);
    }
}
