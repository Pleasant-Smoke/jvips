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
import com.pss.jvips.plugin.args.ModifiableOptionalParam;
import com.pss.jvips.plugin.args.OptionalParam;
import com.pss.jvips.plugin.context.ParserScopedPluginContext;
import com.squareup.javapoet.CodeBlock;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Parses optional params to create a parameter class
 */
public class OptionalParamVisitorOld extends DocReaderParserBaseVisitor<OptionalParam> {

    private static final Logger log = LoggerFactory.getLogger(OptionalParamVisitorOld.class);

    private boolean inBlock;

    private final ModifiableOptionalParam builder = ModifiableOptionalParam.create();

    private final BufferedTokenStream tokens;
    private final ParserScopedPluginContext context;

    public OptionalParamVisitorOld(ParserScopedPluginContext context) {
        this.context = context;
        this.tokens = context.tokenStream();
    }


    @Override
    public OptionalParam visitPrimitiveType(DocReaderParser.PrimitiveTypeContext ctx) {
        if(!inBlock) {
            if (builder.getType() != null) {
                log.warn("Type is not null!");
            }
            builder.setType(ctx.Identifier().getText());
        }
        return super.visitPrimitiveType(ctx);
    }

    @Override
    public OptionalParam visitOptionalArgument(DocReaderParser.OptionalArgumentContext ctx) {
        return super.visitOptionalArgument(ctx);
    }

    @Override
    public OptionalParam visitUserType(DocReaderParser.UserTypeContext ctx) {

        if(!inBlock) {
            if (builder.getType() != null) {
                log.warn("Type is not null!");
            }
            builder.setType(ctx.Identifier().getText());
        }
        return super.visitUserType(ctx);
    }

    @Override
    protected OptionalParam defaultResult() {
        return builder;
    }

    @Override
    public OptionalParam visitParameter(DocReaderParser.ParameterContext ctx) {
        if(!inBlock) {

           builder.setName(ctx.Identifier().getText());
        }
        return super.visitParameter(ctx);
    }

    @Override
    public OptionalParam visitBlocks(DocReaderParser.BlocksContext ctx) {
        inBlock = true;
        var v = new TextBlockVisitor(context);
        CodeBlock.Builder accept = ctx.accept(v);
        builder.setDoc(accept.build());
        return builder.toImmutable();
    }


}
