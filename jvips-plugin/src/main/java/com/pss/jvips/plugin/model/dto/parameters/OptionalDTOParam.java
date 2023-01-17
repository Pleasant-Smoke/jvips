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

package com.pss.jvips.plugin.model.dto.parameters;

import com.pss.jvips.plugin.context.GlobalPluginContext;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;

import java.util.Iterator;

public class OptionalDTOParam extends WritableParameter {

    protected TypeName typeName;

    public OptionalDTOParam(TypeName typeName, GlobalPluginContext context) {
        super(context);
        this.typeName = typeName;
    }

    @Override
    public boolean addArrayInitializer(int in, int out, CodeBlock.Builder builder, Iterator<WritableParameter> params) {
        if(params.hasNext()){
            throw new RuntimeException("Optional Param must be last");
        }
        return addArrayInitializerInternal(add(in, "getInSize"), add(out, "getOutSize"), builder);
    }

    @Override
    public boolean addArrayInitializerPanama(int in, int out, CodeBlock.Builder builder, Iterator<WritableParameter> params) {
        if(params.hasNext()){
            throw new RuntimeException("Optional Param must be last");
        }
        return false;
    }

    CodeBlock add(int num, String method){
        return CodeBlock.builder().add("$L + (args != null ? args.$L() * 3 : 0)", num, method).build();
    }



    @Override
    public void writeJniOutArray(CodeBlock.Builder builder) {
        builder.beginControlFlow("if(args != null)")
                .addStatement("args.serialize(null, $L, $L, $L, $L)", in$index, in$params, out$index, out$params)
                .endControlFlow();
    }

    @Override
    public void addPostInit(CodeBlock.Builder builder) {
        builder.beginControlFlow("if(args != null)")
                .addStatement("args.deserialize(null, $L, $L)", out$index,  out$params)
                .endControlFlow();
    }

    @Override
    public void addParameters(MethodSpec.Builder builder) {
        builder.addParameter(ParameterSpec.builder(typeName, "args").build());
    }
}
