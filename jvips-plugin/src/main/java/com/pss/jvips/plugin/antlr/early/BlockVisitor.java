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

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class BlockVisitor extends DocReaderParserBaseVisitor<BlockResult> {

    private static final Logger log = LoggerFactory.getLogger(BlockVisitor.class);

    private final List<CodeBlock> blocks = new ArrayList<>();

    private final ParserContext context;

    private final Set<OptionalParamDoc> optionalParams = new LinkedHashSet<>();

    public BlockVisitor(ParserContext context) {
        this.context = context;
    }

    @Override
    public BlockResult visitBlocks(DocReaderParser.BlocksContext ctx) {
        super.visitBlocks(ctx);
        return new BlockResult(CodeBlock.join(blocks, "\n\n"), optionalParams);
    }

    @Override
    public BlockResult visitMainBlocks(DocReaderParser.MainBlocksContext ctx) {
        TextVisitor visitor = new TextVisitor(context);
        CodeBlock.Builder accept = ctx.accept(visitor);
        blocks.add(accept.build());
        return super.visitMainBlocks(ctx);
    }


    @Override
    public BlockResult visitSeeAlsoDoclet(DocReaderParser.SeeAlsoDocletContext ctx) {
        CodeBlock accept = ctx.accept(new DocReaderParserBaseVisitor<CodeBlock>() {

            List<CodeBlock> blocks = new ArrayList<>();

            @Override
            public CodeBlock visitSeeAlsoDoclet(DocReaderParser.SeeAlsoDocletContext ctx) {
                super.visitSeeAlsoDoclet(ctx);
                return CodeBlock.join(blocks, "\n");
            }

            @Override
            public CodeBlock visitFunctionType(DocReaderParser.FunctionTypeContext ctx) {
                context.getNamingService().findMethod(ctx.Identifier.getText()).ifPresentOrElse((x) -> {
                    blocks.add(CodeBlock.of("@see $T#$L", context.getClassName(), x.target().getJavaName()));
                }, () -> {
                    blocks.add(CodeBlock.of("@see $T#$L", context.getClassName(), ctx.Identifier.getText()));
                    log.warn("{} method Identifier was not found", ctx.Identifier().getText());
                });
                return super.visitFunctionType(ctx);
            }
        });
        blocks.add(accept);
        return super.visitSeeAlsoDoclet(ctx);
    }

    @Override
    public BlockResult visitOptionalArguments(DocReaderParser.OptionalArgumentsContext ctx) {
        optionalParams.addAll(ctx.accept(new OptionalParamVisitor(context)));
        return super.visitOptionalArguments(ctx);
    }

    @Override
    protected BlockResult defaultResult() {
        return new BlockResult(CodeBlock.builder().build(), optionalParams);
    }

    @Override
    public BlockResult visitCodeBlock(DocReaderParser.CodeBlockContext ctx) {
        CodeBlockVisitor visitor = new CodeBlockVisitor(context);
        CodeBlock accept = ctx.accept(visitor);
        blocks.add(accept);
        return super.visitCodeBlock(ctx);
    }

    @Override
    public BlockResult visitText(DocReaderParser.TextContext ctx) {
        return super.visitText(ctx);
    }




}
