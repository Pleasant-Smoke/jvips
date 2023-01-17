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

import java.util.Iterator;

public class ByteBufferParam extends ParameterWithLength {

    BasicWritableParam param;

    public ByteBufferParam(BasicWritableParam param, GlobalPluginContext context) {
        super(context);
        this.param = param;
    }

    @Override
    public boolean addArrayInitializer(int in, int out, CodeBlock.Builder builder, Iterator<WritableParameter> params) {
        return false;
    }


    @Override
    public void addParameters(MethodSpec.Builder builder) {
        builder.addParameter(param.typeName, param.javaName);
    }

    @Override
    public void addPreInit(MethodSpec.Builder builder) {
        super.addPreInit(builder);
    }

    @Override
    public void writeJniInArray(CodeBlock.Builder builder) {
        writeInternal(builder, in$params, in$index, param.nativeName, "BYTE_BUFFER", param.javaName);
    }

    @Override
    public String javaName() {
        return param.javaName;
    }

    @Override
    public String getLengthProperty() {
        return "limit()";
    }
}
