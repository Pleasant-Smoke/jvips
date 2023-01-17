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

import com.google.common.collect.Multimap;
import com.pss.jvips.plugin.constants.ImmutableVipsConstant;
import com.pss.jvips.plugin.constants.VipsConstant;
import com.pss.jvips.plugin.generation.CodeGenerator;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.nio.file.Path;
import java.util.List;

import static com.pss.jvips.plugin.naming.JavaTypeMapping.String_class;

public class ConstantGenerator extends CodeGenerator<ConstantGenerator.Constant> {

    public ConstantGenerator(Path path) {
        super(path);
    }

    @Override
    protected void runInternal(TypeSpec.Builder typeSpec, Constant arguments, ClassName className) {
        for (VipsConstant constant : arguments.constants) {
            if(String_class.equals(constant.type())){
                if(!constant.name().getJavaName().equals("CONFIG")){ // This is build output
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

            if(constant.hasDeprecations()){
                var deps = constant.getDeprecation();
                if(deps.isRelocated()){
                    arguments.deprecations.put(deps.relocated(), constant);
                } else if(deps.isRenamed()){

                    boolean remapLong = TypeName.INT.equals(constant.type());

                    FieldSpec.Builder builder = FieldSpec
                            .builder(remapLong ? TypeName.LONG : constant.type(), constant.getDeprecation().rename().getJavaName(), Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                            .initializer("$L", constant.name().getJavaName())
                            .addAnnotation(Deprecated.class);
                    typeSpec.addField(builder.build());
                }
            }
        }
    }

    @Override
    protected TypeSpec.Builder getTypeSpec(Constant arguments, ClassName className) {
        return TypeSpec.classBuilder(className).addModifiers(Modifier.PUBLIC);
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


    public static class Constant {

        protected final Multimap<ClassName, VipsConstant> deprecations;
        protected final List<VipsConstant> constants;

        public Constant(Multimap<ClassName, VipsConstant> deprecations, List<VipsConstant> constants) {
            this.deprecations = deprecations;
            this.constants = constants;
        }
    }
}
