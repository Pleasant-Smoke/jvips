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

import com.pss.jvips.plugin.constants.ImmutableVipsConstant;
import com.pss.jvips.plugin.constants.VipsConstant;
import com.pss.jvips.plugin.context.GlobalPluginContext;
import com.pss.jvips.plugin.context.OperationContext;
import com.pss.jvips.plugin.generation.CodeGeneratorOld;
import com.pss.jvips.plugin.util.Utils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.nio.file.Path;
import java.util.List;

import static com.pss.jvips.plugin.naming.JavaTypeMapping.String_class;

public class ConstantGeneratorOld extends CodeGeneratorOld<ConstantGeneratorOld.Constant, Void> {

    public ConstantGeneratorOld(Path task, GlobalPluginContext context) {
        super(task, context, OperationContext.COMMON);
    }

    @Override
    protected Result<Void> runInternal(TypeSpec.Builder typeSpec, ConstantGeneratorOld.Constant arguments) {
        ClassName cn = Utils.getFirst(arguments.constants).className();
        for (VipsConstant constant : arguments.constants) {
            if(String_class.equals(constant.type())){
                if(!constant.name().getJavaName().equals("CONFIG")){
                    typeSpec.addField(field(constant, "$S"));
                }

            } else {
                // Overflow on an int
                if(TypeName.INT.equals(constant.type())){
                    VipsConstant newConstant = ImmutableVipsConstant.builder()
                            .from(constant)
                            .type(TypeName.LONG).build();

                    typeSpec.addField(field(newConstant, "$LL"));
                } else {
                    typeSpec.addField(field(constant, "$L"));
                }

            }
        }
        return new Result<>(null, cn);
    }

    FieldSpec field(VipsConstant constant, String init){

        FieldSpec.Builder builder = FieldSpec
                .builder(constant.type(), constant.name().getJavaName(), Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer(init, constant.value());
        if(constant.getDocumentation() != null){
            builder.addJavadoc(constant.getDocumentation());
        }
        return builder.build();
    }


    @Override
    protected TypeSpec.Builder getTypeSpec(ConstantGeneratorOld.Constant arguments) {
        ClassName className = Utils.getFirst(arguments.constants).className();
        return TypeSpec.classBuilder(className).addModifiers(Modifier.PUBLIC);
    }

    public static class Constant {

        String path;
        List<VipsConstant> constants;
        public Constant(String path, List<VipsConstant> constants) {
            this.path = path;
            this.constants = constants;
        }
    }
}
