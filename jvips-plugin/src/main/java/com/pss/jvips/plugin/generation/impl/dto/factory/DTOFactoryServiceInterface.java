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

package com.pss.jvips.plugin.generation.impl.dto.factory;

import com.pss.jvips.plugin.generation.CodeGeneratorOld;
import com.pss.jvips.plugin.context.GlobalPluginContext;
import com.pss.jvips.plugin.context.OperationContext;
import com.squareup.javapoet.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.Modifier;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import static com.pss.jvips.plugin.naming.JavaTypeMapping.*;

public  class DTOFactoryServiceInterface extends CodeGeneratorOld<Map<String, DTOTuple>, Map<String, MethodSpec>> {

    private static final Logger log = LoggerFactory.getLogger(DTOFactoryServiceInterface.class);



    public DTOFactoryServiceInterface(Path output, GlobalPluginContext context) {
        super(output, context, OperationContext.COMMON);
    }


    @Override
    protected Result<Map<String, MethodSpec>> runInternal(TypeSpec.Builder typeSpec, Map<String, DTOTuple> functionNamesToDTO) {
        typeSpec = TypeSpec.interfaceBuilder(OptionalParameterFactory_class).addModifiers(Modifier.PUBLIC).addTypeVariables(List.of(DataType_TypeVariable, UnderlyingSession_TypeVariable));
        FieldSpec field = FieldSpec.builder(OptionalParameterFactory_class, "INSTANCE")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
                .initializer("$T.load($T.class).findFirst().orElseThrow(()-> new $T($S))",
                        ServiceLoader.class, OptionalParameterFactory_class, RuntimeException.class, "Unable to locate OptionalParameter Factory Service")
                .build();
        typeSpec.addField(field);
        Map<String, MethodSpec> mapping = new HashMap<>();
        for (Map.Entry<String, DTOTuple> entry : functionNamesToDTO.entrySet()) {
            String methodName = entry.getKey();
            ClassName clazz = entry.getValue().child();
            String name = "for" + StringUtils.capitalize(methodName);
            MethodSpec build = MethodSpec.methodBuilder(name).returns(ParameterizedTypeName.get(clazz, DataType_TypeVariable, UnderlyingSession_TypeVariable)).addModifiers(Modifier.DEFAULT, Modifier.PUBLIC)
                    .addStatement("return INSTANCE.$L()", name)
                    .build();
            typeSpec.addMethod(build);
            mapping.put(name, build);
        }

        return new Result<>(mapping, OptionalParameterFactory_class);
    }

    @Override
    protected TypeSpec.Builder getTypeSpec(Map<String, DTOTuple> arguments) {
        return super.getTypeSpec(arguments);
    }
}