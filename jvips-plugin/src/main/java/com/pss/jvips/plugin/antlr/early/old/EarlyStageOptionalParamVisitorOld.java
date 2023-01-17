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

package com.pss.jvips.plugin.antlr.early.old;


import com.pss.jvips.plugin.antlr.DocReaderParser;
import com.pss.jvips.plugin.antlr.DocReaderParserBaseVisitor;
import com.pss.jvips.plugin.model.xml.executable.AbstractExecutable;
import com.pss.jvips.plugin.naming.JavaTypeMapping;
import com.pss.jvips.plugin.util.MethodOptionalParametersDocumentation;
import com.pss.jvips.plugin.context.ParserScopedPluginContext;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * First stage of doc processing to register optional arg classnames
 */
public class EarlyStageOptionalParamVisitorOld extends DocReaderParserBaseVisitor<MethodOptionalParametersDocumentation> {

    private static final Logger log = LoggerFactory.getLogger(EarlyStageOptionalParamVisitorOld.class);

    private final AtomicInteger counter = new AtomicInteger(0);



    private final AbstractExecutable executable;

    private final Set<EarlyStageOptionalParameter> parameters = new LinkedHashSet<>();



    private final MethodOptionalParametersDocumentation result;
    private final ParserScopedPluginContext context;


    public EarlyStageOptionalParamVisitorOld(ParserScopedPluginContext context) {
        this.context = context;
        this.executable = context.currentExecutable();
        this.result = new MethodOptionalParametersDocumentation(executable);
    }



    @Override
    public MethodOptionalParametersDocumentation visitOptionalArguments(DocReaderParser.OptionalArgumentsContext ctx) {
        return super.visitOptionalArguments(ctx);
    }


    @Override
    protected MethodOptionalParametersDocumentation defaultResult() {
        return result;
    }

    @Override
    public MethodOptionalParametersDocumentation visitOptionalArgument(DocReaderParser.OptionalArgumentContext ctx) {
        TextBlockVisitor objectBlockVisitor = new TextBlockVisitor(context, new ArrayList<>());
        CodeBlock.Builder accept = ctx.blocks().accept(objectBlockVisitor);
        var optionalParameter = new EarlyStageOptionalParameter();
        optionalParameter.setExecutable(this.executable);
        optionalParameter.setJavaDoc(accept.build());
        optionalParameter.setOrder(counter.getAndIncrement());
        optionalParameter.setName(ctx.parameter().javaName);
        switch (ctx.hasType){
            case FUNCTION -> {
                log.warn("Optional parameter with Function type");
                optionalParameter.setType(()-> TypeName.VOID);
            }
            case PRIMITIVE -> {
                optionalParameter.setType(()-> ctx.types.primitiveType().typeName);
            }
            case USER_TYPE -> {
                optionalParameter.setType(()-> JavaTypeMapping.getType(ctx.types.userType().javaName.getJavaName()));
            }
            case STRING -> {
                optionalParameter.setType(()-> ctx.types.stringType().typeName);
            }
            case NONE -> {
                optionalParameter.setType(()-> TypeName.OBJECT);
            }
        }

        //builder.type(()-> null);
        result.getOptionalParameters().put(optionalParameter.javaName(), optionalParameter);
        //result.getEarlyStageParameters().add(optionalParameter);
        result.getParameterNames().add(optionalParameter.javaName());
        return super.visitOptionalArgument(ctx);
    }


}
