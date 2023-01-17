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

package com.pss.jvips.plugin.antlr.early;


import com.pss.jvips.plugin.antlr.DocReaderParser;
import com.pss.jvips.plugin.antlr.DocReaderParserBaseVisitor;
import com.squareup.javapoet.CodeBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * First stage of doc processing to register optional arg classnames
 */
public class OptionalParamVisitor extends DocReaderParserBaseVisitor<Set<OptionalParamDoc>> {

    private static final Logger log = LoggerFactory.getLogger(OptionalParamVisitor.class);


    private final Set<OptionalParamDoc> parameters = new LinkedHashSet<>();

    private final ParserContext context;


    public OptionalParamVisitor(ParserContext context) {
        this.context = context;

    }



    @Override
    public Set<OptionalParamDoc> visitOptionalArguments(DocReaderParser.OptionalArgumentsContext ctx) {
        super.visitOptionalArguments(ctx);
        return parameters;
    }




    @Override
    public Set<OptionalParamDoc> visitOptionalArgument(DocReaderParser.OptionalArgumentContext ctx) {
        String identifier = ctx.p.Identifier.getText();
        CodeBlock.Builder accept = ctx.blocks().accept(new TextVisitor(context));
        OptionalParamDoc param = ImmutableOptionalParamDoc.builder().name(identifier).documentation(accept.build()).build();
        parameters.add(param);
        return super.visitOptionalArgument(ctx);
    }


}
