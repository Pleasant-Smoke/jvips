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

package com.pss.jvips.plugin.actions.visitor;

import com.pss.jvips.plugin.actions.GenerateVipsSourcesAction;
import com.pss.jvips.plugin.model.dto.parameters.*;
import com.pss.jvips.plugin.model.xml.Namespace;
import com.pss.jvips.plugin.model.xml.executable.AbstractExecutable;
import com.pss.jvips.plugin.model.xml.executable.Parameter;
import com.pss.jvips.plugin.model.xml.toplevel.*;
import com.pss.jvips.plugin.naming.JavaNaming;
import com.pss.jvips.plugin.naming.JavaTypeMapping;
import com.pss.jvips.plugin.naming.TypeRegistration;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.pss.jvips.plugin.naming.JavaTypeMapping.*;

public class MemberRegistrationVisitor extends BaseTopLevelVisitor<GenerateVipsSourcesAction.ExecutableRegistration> {

    private static final Logger log = LoggerFactory.getLogger(MemberRegistrationVisitor.class);

    public MemberRegistrationVisitor(Namespace namespace, GenerateVipsSourcesAction.ExecutableRegistration context) {
        super(namespace, context);
    }

    @Override
    protected void handleAlias(Alias alias) {
        super.handleAlias(alias);
    }

    @Override
    protected void handleBitField(BitField bitField) {
        super.handleBitField(bitField);
    }

    @Override
    protected void handleCallback(Callback cb) {
        super.handleCallback(cb);
    }

    @Override
    protected void handleClass(NativeClass clazz) {
        if("Image".equals(clazz.getName())){
            clazz.getExecutables().forEach(executableType -> {
                handleExecutable(executableType, JavaTypeMapping.VipsOperations_class);
            });
//            clazz.getConstructors().forEach(constructor -> {
//                handleExecutable(constructor, JavaTypeMapping.VipsOperations_class);
//            });
        } else {
//            clazz.getExecutables().forEach(executableType -> {
//                handleExecutable(executableType, clazz.getClassName());
//            });
//            clazz.getConstructors().forEach(constructor -> {
//                handleExecutable(constructor, clazz.getFactoryClassName());
//            });
        }

        super.handleClass(clazz);
    }

    @Override
    protected void handleConstant(Constant constant) {
        super.handleConstant(constant);
    }

    @Override
    protected void handleFunctionMacro(FunctionMacro macro) {
        super.handleFunctionMacro(macro);
    }

    @Override
    protected void handleRecord(NativeRecord record) {
        if(!TypeRegistrationVisitor.EXCLUDED_RECORDS.contains(record.getName())) {
            record.getExecutables().forEach(executableType -> {
              //  handleExecutable(executableType, record.getClassName());
            });

//            record.getFields().forEach(field -> {
//                if (StringUtils.isNotBlank(field.getName())) {
//                    field.setJavaName(JavaNaming.parameterName(field.getName()));
//                }
//                TypeRegistration.registerField(field.getJavaName(), record.getClassName());
//            });
            super.handleRecord(record);
        }
    }

    @Override
    protected void handleEnumeration(Enumeration enumeration) {
        enumeration.getMembers().forEach(member -> {
            member.setJavaName(JavaNaming.registerConstant(member.getIdentifier()));
            TypeRegistration.registerEnum(member.getJavaName(), enumeration.getClassName());
        });
        super.handleEnumeration(enumeration);
    }

    @Override
    protected void handleFunction(Function function) {
        handleExecutable(function, JavaTypeMapping.VipsOperations_class);
        super.handleFunction(function);
    }

    protected void handleExecutable(AbstractExecutable executable, ClassName cn){
        if(!context.getContext().functionNames().contains(executable.getName())){
            return;
        }
        if(executable.getJavaName() == null && StringUtils.isNotBlank(executable.getName())){
            executable.setJavaName(JavaNaming.methodName(executable.identifier()));
        }
        TypeRegistration.registerExecutable(executable.getJavaName(), cn);
        Boolean inImageH = Optional.ofNullable(executable.getDocumentation())
                .map(x -> x.getFilename().endsWith("include/vips/image.h") || x.getFilename().endsWith("libvips/iofuncs/image.c")).orElse(false);
        executable.setExclude(inImageH);

        {
            List<ParameterWithLength> arrayParameters = new ArrayList<>();

            List<WritableParameter> writableParams = new ArrayList<>();

            int i =  -1;
            Parameter previous = null;
            for (Parameter parameter : executable.getParameters()) {
                if(parameter.isExcluded()){
                    executable.setExclude(true);
                }
                if(!parameter.isVarArg()) {
                    i++;
                    parameter.setJavaName(JavaNaming.parameterName(parameter.getName()));
                    parameter.setIndex(i);
                    TypeName typeName = parameter.constructType();

                    if (parameter.isArrayType()) {
                        if (typeName.equals(ByteBuffer_class) || typeName.equals(DoubleBuffer_class)) {
                            ByteBufferParam byteBufferParam = new ByteBufferParam(new BasicWritableParam( parameter, context.getContext() ), context.getContext());
                            arrayParameters.add(byteBufferParam);
                        } else if (typeName.equals(VipsImage_Array_class)) {
                            ImageArrayParam byteBufferParam = new ImageArrayParam(new BasicWritableParam(parameter,
                                    context.getContext().operationContext().getImageArray(), context.getContext()), context.getContext());
                            arrayParameters.add(byteBufferParam);
                        } else {
                            writableParams.add(new BasicWritableParam(parameter, context.getContext()));
                            // throw new RuntimeException("Unsupported array type: " + typeName);
                        }
                    } else if (Parameter.POSSIBLE_LENGTH_OR_SIZE.contains(parameter.getName())) {
                        if (arrayParameters.isEmpty()) {
                            writableParams.add((new BasicWritableParam(parameter, context.getContext())));
                            //throw new RuntimeException("Array length type is empty ");
                        } else {
                            ComposedParam composedParam = new ComposedParam(new BasicWritableParam(parameter, context.getContext() ), context.getContext());
                            composedParam.getParams().addAll(arrayParameters);
                            arrayParameters.clear();
                            writableParams.add(composedParam);
                        }
                    } else if (typeName.equals(JVipsImage_class)) {
                        writableParams.add(new ImageParam(new BasicWritableParam(parameter, context.getContext().operationContext().getImage(),context.getContext() ), context.getContext()));
                    } else {
                        writableParams.add(new BasicWritableParam(parameter, context.getContext()));
                    }
                }
            }
            executable.setWritableParams(writableParams);
            context.addExecutable(executable);
            executable.filename().ifPresent(fn-> context.putFileMapping(fn, executable));
        }



    }
}
