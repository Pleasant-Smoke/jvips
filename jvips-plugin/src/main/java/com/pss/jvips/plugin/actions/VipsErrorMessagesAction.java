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

package com.pss.jvips.plugin.actions;

import com.pss.jvips.plugin.ErrorMessages;
import com.pss.jvips.plugin.naming.JavaTypeMapping;
import com.pss.jvips.plugin.naming.Packages;
import com.squareup.javapoet.*;
import org.gradle.api.Action;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Deprecated
public class VipsErrorMessagesAction implements Action<Task> {

    private static final Logger log = LoggerFactory.getLogger(VipsErrorMessagesAction.class);
    private NamedDomainObjectContainer<ErrorMessages> container;
    public VipsErrorMessagesAction(NamedDomainObjectContainer<ErrorMessages> container) {
        this.container = container;
    }


    @Override
    public void execute(Task task) {

        TypeSpec.Builder typeSpec = TypeSpec.enumBuilder(JavaTypeMapping.ERROR_ENUM_CLASS).addModifiers(Modifier.PUBLIC);

        List<String> lines = new ArrayList<>();
        List<ErrorMessages> messages = new ArrayList<>();
        lines.add("extern \"C\" {");
        lines.add("");

        container.all(new Action<ErrorMessages>() {
            @Override
            public void execute(ErrorMessages errorMessages) {
                messages.add(errorMessages);
            }
        });
        Collections.sort(messages);
        messages.forEach(errorMessages -> {
            TypeSpec enumInitializer = TypeSpec
                    .anonymousClassBuilder("$LL, $S", errorMessages.getValue(), errorMessages.getMessage())
                    .build();
            typeSpec.addEnumConstant(errorMessages.getName(), enumInitializer);

            lines.add(String.format("    #define %s %sL", errorMessages.getName(), errorMessages.getValue()));
        });
        FieldSpec mapping = FieldSpec.builder(
                        ParameterizedTypeName.get(JavaTypeMapping.Map_class, TypeName.LONG.box(), JavaTypeMapping.ERROR_ENUM_CLASS), "mapping", Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                .initializer("$T.stream(JVipsErrors.values()).collect($T.toMap(JVipsErrors::getValue, x-> x))", Arrays.class, Collectors.class).build();
        typeSpec.addField(mapping);
        typeSpec.addField(long.class, "value", Modifier.PRIVATE, Modifier.FINAL);
        typeSpec.addField(String.class, "message", Modifier.PRIVATE, Modifier.FINAL);

        var constr = MethodSpec.constructorBuilder();

        constr.addParameter(Long.class, "value");
        constr.addParameter(String.class, "message");

        constr.addCode(
                CodeBlock.builder()
                        .addStatement("this.value = value")
                        .addStatement("this.message = message")
                        .build()
        );

        typeSpec.addMethod(constr.build());

        typeSpec.addMethod(
                MethodSpec
                        .methodBuilder("getValue")
                        .returns(long.class)
                        .addStatement("return this.value")
                        .build());

        typeSpec.addMethod(
                MethodSpec
                        .methodBuilder("getMessage")
                        .returns(String.class)
                        .addStatement("return this.message")
                        .build());

        typeSpec.addMethod(
                MethodSpec.methodBuilder("getErrorType")
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .addParameter(long.class, "error")
                        .returns(JavaTypeMapping.ERROR_ENUM_CLASS)
                        .addStatement("return mapping.get(error)")
                        .build());

        JavaFile builder = JavaFile.builder(Packages.commonBase, typeSpec.build()).build();

        lines.add("");
        lines.add("}");
        try {
            builder.writeTo(task.getProject().getBuildDir().toPath().resolve("generated/src/main/java"));
            Files.write(task.getProject().getProjectDir().toPath().resolve("src/main/cpp/JVipsErrorCodes.h"), lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
