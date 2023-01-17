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

import com.pss.jvips.plugin.constants.ImmutableVipsConstant;
import com.pss.jvips.plugin.constants.VipsConstant;
import com.pss.jvips.plugin.model.xml.Namespace;
import com.pss.jvips.plugin.model.xml.executable.Method;
import com.pss.jvips.plugin.model.xml.executable.VirtualMethod;
import com.pss.jvips.plugin.model.xml.toplevel.*;
import com.pss.jvips.plugin.naming.*;
import com.pss.jvips.plugin.context.GlobalPluginContext;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class TypeRegistrationVisitor extends BaseTopLevelVisitor<GlobalPluginContext> {

    public TypeRegistrationVisitor(Namespace namespace, GlobalPluginContext context) {
        super(namespace, context);
    }

    public static final List<String> EXCLUDED_RECORDS = List.of("ArrayDouble", "ArrayInt");

    @Override
    protected void handleAlias(Alias alias) {
        super.handleAlias(alias);
    }

    @Override
    protected void handleBitField(BitField bitField) {
        JavaCaseFormat javaName = JavaNaming.getJavaClassName(bitField.getName());
        ClassName className = JavaTypeMapping.getClassName(javaName, Packages.Common.Generated.bits);
        bitField.setJavaName(javaName);
        bitField.setClassName(className);
        JavaTypeMapping.putBitField(className);
        super.handleBitField(bitField);
    }

    @Override
    protected void handleCallback(Callback cb) {
        JavaCaseFormat javaName = JavaNaming.getJavaClassName(cb.getName());
        ClassName className = JavaTypeMapping.getClassName(javaName, Packages.Common.Generated.callbacks);
        cb.setJavaName(javaName);
        cb.setClassName(className);
        super.handleCallback(cb);
    }

    @Override
    protected void handleClass(NativeClass clazz) {
        JavaCaseFormat javaName = JavaNaming.getJavaClassName(clazz.getName());
        ClassName className = JavaTypeMapping.getClassName(javaName, Packages.Common.Generated.base);
        clazz.setJavaName(javaName);
        clazz.setClassName(className);
        handleClassLike(clazz, className);
        super.handleClass(clazz);
    }

    @Override
    protected void handleConstant(Constant constant) {
        JavaCaseFormat name = JavaNaming.registerConstant(constant.getIdentifier());
        String fileName = constant.getSourcePosition().getFilename();
        TypeName type = JavaTypeMapping.getType(constant.getType().getName());
        String capitalize = StringUtils.capitalize(FilenameUtils.getBaseName(fileName));
        ClassName className = JavaTypeMapping.getClassName(capitalize + "Constants", Packages.Common.Generated.constants);
        TypeRegistration.registerConstant(name, className);
        VipsConstant vips =  ImmutableVipsConstant.builder()
                .name(name)
                .path(fileName)
                .type(type)
                .value(constant.getValue())
                .className(className)
                .documentation(constant.documentation().orElse(null))
                .build();
        context.constants().put(constant.getIdentifier(), constant.getValue());
        context._constants().computeIfAbsent(constant.getSourcePosition().getFilename(),(k)-> new ArrayList<>()).add(vips);
        super.handleConstant(constant);
    }

    @Override
    protected void handleFunctionMacro(FunctionMacro macro) {
        super.handleFunctionMacro(macro);
    }

    @Override
    protected void handleRecord(NativeRecord record) {
        if(!EXCLUDED_RECORDS.contains(record.getName())) {
            JavaCaseFormat javaName = JavaNaming.getJavaClassName(record.getName());
            ClassName className = JavaTypeMapping.getClassName(javaName, Packages.Common.Generated.dto);
            record.setJavaName(javaName);
            record.setClassName(className);
            handleClassLike(record, className);

            super.handleRecord(record);
        }
    }

    @Override
    protected void handleEnumeration(Enumeration enumeration) {
        JavaCaseFormat javaName = JavaNaming.getJavaClassName(enumeration.getName());
        ClassName className = JavaTypeMapping.getClassName(javaName, Packages.Common.Generated.enumerations);
        enumeration.setJavaName(javaName);
        enumeration.setClassName(className);
        super.handleEnumeration(enumeration);
    }

    /**
     * Function is a top level type but it isn't registered in the top level
     * @param function
     */
    @Override
    protected void handleFunction(Function function) {
        super.handleFunction(function);
    }

    private void handleClassLike(ClassLike cl, ClassName className){
       // ClassName factoryName = JavaTypeMapping.getClassName(className.simpleName() + "Factory", Packages.Common.Generated.factory );
        //cl.setFactoryClassName(factoryName);
        cl.getExecutables().forEach(exec->{
            if(exec instanceof VirtualMethod v){
                cl.getVirtualMethods().add(v);
            } else if(exec instanceof Function f){
                cl.getFunctions().add(f);
            } else if(exec instanceof Method m){
                cl.getMethods().add(m);
            }
        });
    }
}
