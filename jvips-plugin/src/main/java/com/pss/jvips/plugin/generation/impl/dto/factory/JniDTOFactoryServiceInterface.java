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
import java.util.Map;

import static com.pss.jvips.plugin.naming.JavaTypeMapping.*;

public  class JniDTOFactoryServiceInterface extends CodeGeneratorOld<Map<TypeName, ClassName>, Map<String, MethodSpec>> {

    private static final Logger log = LoggerFactory.getLogger(JniDTOFactoryServiceInterface.class);

    protected  Map<String, DTOTuple> functionNamesToDTO;
    protected  GlobalPluginContext operationContext;

    public JniDTOFactoryServiceInterface(Path task, GlobalPluginContext context) {
        super(task, context, OperationContext.JNI);
    }






    @Override
    protected Result<Map<String, MethodSpec>> runInternal(TypeSpec.Builder typeSpec, Map<TypeName, ClassName> arguments) {

        Map<String, MethodSpec> mapping = new HashMap<>();
        for (Map.Entry<String, DTOTuple> entry : functionNamesToDTO.entrySet()) {
            String methodName = StringUtils.capitalize(entry.getKey());
            ParameterizedTypeName returnType = ParameterizedTypeName.get(entry.getValue().parent(), Long_class, VOID);
            ClassName clazz = entry.getValue().child();


            MethodSpec builder = MethodSpec.methodBuilder("for" + methodName)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(returnType)
                    .addStatement("return new $T()", clazz)
                    .build();


            typeSpec.addMethod(builder);
              mapping.put("for" + methodName, builder);
        }
        typeSpec.addAnnotation(AnnotationSpec.builder(AutoService_class).addMember("value","$T.class", OptionalParameterFactory_class).build());

        return new Result<>(mapping, OptionalJniParameterFactory_class);
    }


    @Override
    protected TypeSpec.Builder getTypeSpec(Map<TypeName, ClassName> arguments) {
        return TypeSpec.classBuilder(OptionalJniParameterFactory_class).addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(OptionalParameterFactory_class,Long_class, VOID));
    }
}