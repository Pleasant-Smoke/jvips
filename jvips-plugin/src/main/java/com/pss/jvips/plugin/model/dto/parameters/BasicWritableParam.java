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

import com.pss.jvips.plugin.model.xml.executable.Parameter;
import com.pss.jvips.plugin.model.xml.types.Direction;
import com.pss.jvips.plugin.naming.JavaCaseFormat;
import com.pss.jvips.plugin.naming.JavaTypeMapping;
import com.pss.jvips.plugin.out.OutParam;
import com.pss.jvips.plugin.util.Utils;
import com.pss.jvips.plugin.context.GlobalPluginContext;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;

import java.util.Iterator;
import java.util.Set;

import static com.pss.jvips.plugin.naming.JavaTypeMapping.getTypeConstant;

public class BasicWritableParam extends WritableParameter {

    protected String javaName;

    protected String nativeName;

    protected TypeName typeName;

    protected Direction direction;

    protected String documentation;

    protected JavaCaseFormat naming;

    public BasicWritableParam(TypeName typeName, Direction direction, String documentation, JavaCaseFormat naming, GlobalPluginContext context) {
        super(context);
        this.javaName = naming.getJavaName();
        this.nativeName = naming.getNativeName();
        this.typeName = typeName;
        this.direction = direction;
        this.documentation = documentation;
        this.naming = naming;
    }

    public BasicWritableParam(Parameter parameter, TypeName override, GlobalPluginContext context) {
        super(context);
        this.javaName = parameter.getJavaName().getJavaName();
        this.nativeName = parameter.getJavaName().getNativeName();
        this.typeName = override;
        this.direction = parameter.getDirection();
        this.documentation = parameter.documentation().orElse(null);
        this.naming = parameter.getJavaName();
    }

    public BasicWritableParam(Parameter parameter, GlobalPluginContext context) {
        super(context);
        this.javaName = parameter.getJavaName().getJavaName();
        this.nativeName = parameter.getJavaName().getNativeName();
        this.typeName = parameter.constructType();
        this.direction = parameter.getDirection();
        this.documentation = parameter.documentation().orElse(null);
        this.naming = parameter.getJavaName();
    }

    @Override
    public boolean addArrayInitializer(int in, int out, CodeBlock.Builder builder, Iterator<WritableParameter> params) {
        if(Utils.isInParameter(direction, javaName)){
            in += 3;
        } else {
            out +=3;
        }
        if(params.hasNext()){
           return params.next().addArrayInitializer(in, out, builder, params);
        } else {
            builder.addStatement("Object[] $L = new Object[$L]", in$params,  in);
            builder.addStatement("Object[] $L = new Object[$L]", out$params, out);
            return out > 0;
        }
    }


    @Override
    public void registerOutParams(Set<OutParam> outParams) {
        if(Utils.isOutParameter(direction, javaName)){

            outParams.add(new OutParam(javaName, typeName));
        }
    }

    @Override
    public void addParameters(MethodSpec.Builder builder) {
        if(Utils.isInParameter(direction, javaName)){
            ParameterSpec.Builder ps = ParameterSpec.builder(typeName, javaName);
            if(documentation != null){
                ps.addJavadoc(documentation + "\n");
            }
            builder.addParameter(ps.build());
        } else {
//            ParameterizedTypeName parameterized = ParameterizedTypeName.get(Reference_class, typeName.box());
//            builder.addParameter(parameterized, javaName);
        }
    }

    @Override
    public void writeJniInArray(CodeBlock.Builder builder) {

        if(Utils.isInParameter(direction, javaName)){
            String typeConstant = getTypeConstant(typeName.isBoxedPrimitive() ? typeName.unbox() : typeName);
            if(JavaTypeMapping.isRegularEnum(typeName)){
                writeInternal(builder, in$params, in$index, nativeName, typeConstant, javaName + ".ordinal()");
            } else if (JavaTypeMapping.isNegativeEnum(typeName)) {
                writeInternal(builder, in$params, in$index, nativeName, typeConstant, javaName + ".getValue()");
            } else {
                writeInternal(builder, in$params, in$index, nativeName, typeConstant, javaName);
            }

        }
    }

    @Override
    public void writeJniOutArray(CodeBlock.Builder builder) {
        if(Utils.isOutParameter(direction, javaName)){
            String typeConstant = getTypeConstant(typeName.isBoxedPrimitive() ? typeName.unbox() : typeName);
            writeInternal(builder, out$params, out$index, nativeName, typeConstant, "null");
        }
    }

    public String getJavaName() {
        return javaName;
    }

    public String getNativeName() {
        return nativeName;
    }

    public TypeName getTypeName() {
        return typeName;
    }

    public Direction getDirection() {
        return direction;
    }

    public String getDocumentation() {
        return documentation;
    }

    public JavaCaseFormat getNaming() {
        return naming;
    }
}
