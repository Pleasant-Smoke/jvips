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
import com.pss.jvips.plugin.out.OutParam;
import com.pss.jvips.plugin.context.GlobalPluginContext;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ImageParam extends WritableParameter {

    protected BasicWritableParam param;

    public ImageParam(BasicWritableParam param, GlobalPluginContext context) {
        super(context);
        this.param = param;
    }

    @Override
    public boolean addArrayInitializer(int in, int out, CodeBlock.Builder builder, Iterator<WritableParameter> params) {
        return param.addArrayInitializer(in, out, builder, params);
    }

    @Override
    public void writeJniInArray(CodeBlock.Builder builder) {
        if(param.direction == Direction.IN && !"out".equals(param.javaName)){
            writeInternal(builder, in$params, in$index, param.nativeName, "IMAGE", param.javaName + ".getAddress()");
        }

    }

    @Override
    public void addImageLocks(List<CodeBlock> blocks) {
        if(!"out".equals(param.javaName) && param.direction != Direction.OUT) {
            blocks.add(CodeBlock.of("$L", param.javaName));
        }
        super.addImageLocks(blocks);
    }

    @Override
    public boolean requiresLock(Iterator<WritableParameter> params) {
        if(this.param.direction == Direction.IN && !"out".equals(param.javaName)){
            return true;
        }
        return super.requiresLock(params);
    }

    @Override
    public void writeJniOutArray(CodeBlock.Builder builder) {
        if(param.direction == Direction.OUT || "out".equals(param.javaName)){
            String name$id = param.javaName + "$id";
            builder.addStatement("long $L = access.getNewId(context)", name$id);
            writeInternal(builder, out$params, out$index, param.nativeName, "IMAGE", name$id);
        }
    }

    @Override
    public void addParameters(MethodSpec.Builder builder) {
        if(!"out".equals(param.javaName)){
            param.addParameters(builder);
        }
    }

    @Override
    public void registerOutParams(Set<OutParam> outParams) {
        param.registerOutParams(outParams);
    }

    @Override
    public String javaName() {
        return param.javaName;
    }

    public BasicWritableParam getParam() {
        return param;
    }
}
