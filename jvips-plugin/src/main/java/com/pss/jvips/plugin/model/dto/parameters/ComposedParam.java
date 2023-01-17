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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ComposedParam extends WritableParameter {

    List<ParameterWithLength> params = new ArrayList<>();

    BasicWritableParam length;

    public ComposedParam(BasicWritableParam length, GlobalPluginContext context) {
        super(context);
        this.length = length;
    }

    @Override
    public boolean addArrayInitializer(int in, int out, CodeBlock.Builder builder, Iterator<WritableParameter> params) {
        int i = this.params.size() * 3;
        i += 3;
        if(params.hasNext()){
            return params.next().addArrayInitializer(in + i, out, builder, params);
        }
        return addArrayInitializerInternal(CodeBlock.of("$L", (i + in)), CodeBlock.of("$L", out), builder);
    }


    @Override
    public boolean requiresLock(Iterator<WritableParameter> params) {
        if(!this.params.isEmpty()) {
            Iterator<WritableParameter> iterator =  this.params.stream().map(x-> (WritableParameter) x).iterator();
            if(iterator.next().requiresLock(iterator)){
                return true;
            };
        }
        return super.requiresLock(params);
    }

    @Override
    public void addParameters(MethodSpec.Builder builder) {
        params.forEach(parameterWithLength -> parameterWithLength.addParameters(builder));
    }

    @Override
    public void addPreInit(MethodSpec.Builder builder) {
        params.forEach(x-> x.addPreInit(builder));

        if(params.size() > 1){
            List<CodeBlock> blocks = new ArrayList<>();
            Iterator<ParameterWithLength> iterator = params.iterator();
            ParameterWithLength first = iterator.next();
            while (iterator.hasNext()){
                ParameterWithLength next = iterator.next();
                CodeBlock condition = CodeBlock.builder().add("$L.$L != $L.$L",
                        first.javaName(), first.getLengthProperty(), next.javaName(), next.getLengthProperty())
                        .build();
                blocks.add(condition);
            }
            CodeBlock joined = CodeBlock.join(blocks, " || ");
            builder.beginControlFlow("if($L)", joined)
                    .addStatement("throw new VipsException($S)", "Size/Length of Array/Buffers aren't the same")
                    .endControlFlow();
        }
    }

    @Override
    public void addImageLocks(List<CodeBlock> blocks) {
        params.forEach(x-> x.addImageLocks(blocks));
    }

    @Override
    public void addImageArrayLocks(List<CodeBlock> blocks) {
        params.forEach(x-> x.addImageArrayLocks(blocks));
    }

    public List<ParameterWithLength> getParams() {
        return params;
    }

    public void setParams(List<ParameterWithLength> params) {
        this.params = params;
    }

    @Override
    public void writeJniInArray(CodeBlock.Builder builder) {
        params.forEach(x-> x.writeJniInArray(builder));
        ParameterWithLength first = params.get(0);
        writeInternal(builder, in$params, in$index, length.nativeName, "LONG", first.javaName() + "." + first.getLengthProperty());
    }
}
