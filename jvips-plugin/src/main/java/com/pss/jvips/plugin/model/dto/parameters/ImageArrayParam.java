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
import java.util.List;

public class ImageArrayParam extends ParameterWithLength {

    BasicWritableParam component;

    public ImageArrayParam(BasicWritableParam component, GlobalPluginContext context) {
        super(context);
        this.component = component;
    }

    @Override
    public boolean addArrayInitializer(int in, int out, CodeBlock.Builder builder, Iterator<WritableParameter> params) {
        throw new RuntimeException("Should not be an intializer");
    }

    @Override
    public void addParameters(MethodSpec.Builder builder) {
        builder.addParameter(component.typeName, component.javaName);
    }

    @Override
    public void addPreInit(MethodSpec.Builder builder) {
        builder.addStatement("long[] $L = new long[$L.length]", getAddressName(), component.javaName);
        builder.beginControlFlow("for(int i = 0; i < $L.length; i++)", component.javaName)
                .addStatement("$L[i] = $L[i].getAddress()", getAddressName(), component.javaName)
                .endControlFlow();
    }

    @Override
    public void writeJniInArray(CodeBlock.Builder builder) {
        writeInternal(builder, in$params, in$index, component.nativeName, "IMAGE", getAddressName());
    }

    @Override
    public boolean requiresLock(Iterator<WritableParameter> params) {
        if(this.component.direction == Direction.IN){
            return true;
        }
        return super.requiresLock(params);
    }

    @Override
    public void addImageArrayLocks(List<CodeBlock> blocks) {
        blocks.add(CodeBlock.of("$L", component.javaName));
        super.addImageArrayLocks(blocks);
    }

    private String getAddressName(){
        return component.javaName + "$address";
    }

    @Override
    public String javaName() {
        return component.javaName;
    }

    @Override
    public String getLengthProperty() {
        return "length";
    }
}
