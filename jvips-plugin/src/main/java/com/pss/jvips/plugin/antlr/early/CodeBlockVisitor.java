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

import java.util.ArrayList;
import java.util.List;

public class CodeBlockVisitor extends DocReaderParserBaseVisitor<CodeBlock> {

    List<CodeBlock> blocks = new ArrayList<>();

    protected final ParserContext context;

    public CodeBlockVisitor(ParserContext context) {
        this.context = context;
    }

    @Override
    public CodeBlock visitCodeBlock(DocReaderParser.CodeBlockContext ctx) {
        TextVisitor visitor = new TextVisitor(context);
        CodeBlock.Builder accept = ctx.blocks().accept(visitor);
        CodeBlock.Builder builder = CodeBlock.builder().add(".$L", accept.build());
//        if(ctx.Paragraph() != null){
//            builder.add("\n");
//        }
        blocks.add(builder.build());
        super.visitCodeBlock(ctx);
        return CodeBlock.join(blocks, "\n");
    }

    @Override
    public CodeBlock visitInnerCode(DocReaderParser.InnerCodeContext ctx) {
        blocks.add(CodeBlock.of("[source,c]\n----$L----\n", ctx.getText()));
        return super.visitInnerCode(ctx);
    }
}
