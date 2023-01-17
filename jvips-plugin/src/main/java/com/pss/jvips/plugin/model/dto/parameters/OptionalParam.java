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

import com.pss.jvips.plugin.model.xml.types.Direction;
import com.pss.jvips.plugin.context.GlobalPluginContext;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;

import java.util.Iterator;

public class OptionalParam extends WritableParameter {

    protected BasicWritableParam param;

    public OptionalParam(BasicWritableParam param, GlobalPluginContext context) {
        super(context);
        this.param = param;
    }

    @Override
    public boolean addArrayInitializer(int in, int out, CodeBlock.Builder builder, Iterator<WritableParameter> params) {
        if(params.hasNext()){
            throw new RuntimeException("Optional Param must be last");
        }
        if(param.direction == Direction.IN){
            return addArrayInitializerInternal(condition(in), number(out), builder);
        } else {
            return addArrayInitializerInternal(number(in), condition(out), builder);
        }

    }

    public CodeBlock number(int num){
        return CodeBlock.builder().add("$L", num).build();
    }

    public CodeBlock condition(int num){
        return CodeBlock.builder().add("$L + ($L != null ? 3 : 0)", num, param.javaName).build();
    }

    @Override
    public void writeJniInArray(CodeBlock.Builder builder) {
        if(param.direction == Direction.IN) {
            builder.beginControlFlow("if($L != null)", param.javaName);
            param.writeJniInArray(builder);
            builder.endControlFlow();
        }

    }

    @Override
    public void addParameters(MethodSpec.Builder builder) {
        param.addParameters(builder);
    }

    @Override
    public void addPostInit(CodeBlock.Builder builder) {
        if(param.direction == Direction.OUT){

        }
    }

    public BasicWritableParam getParam() {
        return param;
    }
}
