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
import com.pss.jvips.plugin.args.OptionalParam;
import com.pss.jvips.plugin.naming.JavaNaming;
import com.pss.jvips.plugin.naming.TypeRegistration;
import com.pss.jvips.plugin.context.ParserScopedPluginContext;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import org.antlr.v4.runtime.BufferedTokenStream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BlockVisitorOld extends DocReaderParserBaseVisitor<CodeBlock.Builder> {

    private final BufferedTokenStream tokens;
    private List<OptionalParam> optionalParams = new ArrayList<>();

    private final ParserScopedPluginContext context;

    private final CodeBlock.Builder builder = CodeBlock.builder();

    public BlockVisitorOld(ParserScopedPluginContext context) {
        this.context = context;
        this.tokens = context.tokenStream();
    }


    @Override
    public CodeBlock.Builder visitBlocks(DocReaderParser.BlocksContext ctx) {

        return super.visitBlocks(ctx);
    }

    @Override
    public CodeBlock.Builder visitMainBlocks(DocReaderParser.MainBlocksContext ctx) {
        TextBlockVisitor visitor = new TextBlockVisitor(context, optionalParams);
        CodeBlock.Builder accept = ctx.accept(visitor);
        builder.add("$L", accept.build());
        if(ctx.Paragraph() != null){
            builder.add("\n\n");
        }
        return super.visitMainBlocks(ctx);
    }


    @Override
    public CodeBlock.Builder visitSeeAlsoDoclet(DocReaderParser.SeeAlsoDocletContext ctx) {
        ctx.accept(new DocReaderParserBaseVisitor<>(){
            @Override
            public Object visitFunctionType(DocReaderParser.FunctionTypeContext ctx) {
                var name = JavaNaming.methodName(ctx.Identifier().getText());
                ClassName className = TypeRegistration.getRegisteredExecutableClass(name, context.currentClass());
                builder.add("@see $T#$L\n", className, name.getJavaName());
                return super.visitFunctionType(ctx);
            }
        });
        return super.visitSeeAlsoDoclet(ctx);
    }

    @Override
    public CodeBlock.Builder visitOptionalArguments(DocReaderParser.OptionalArgumentsContext ctx) {
        optionalParams = ctx.optionalArgument()
                .stream().map(c-> {
                    var v = new OptionalParamVisitorOld(context);
                   return c.accept(v);

                }).collect(Collectors.toList());
        return super.visitOptionalArguments(ctx);
    }

    @Override
    protected CodeBlock.Builder defaultResult() {
        return builder;
    }

    @Override
    public CodeBlock.Builder visitCodeBlock(DocReaderParser.CodeBlockContext ctx) {
        TextBlockVisitor visitor = new TextBlockVisitor(context, optionalParams);
        CodeBlock.Builder accept = ctx.blocks().accept(visitor);
        builder.add(".$L", accept.build());
        if(ctx.Paragraph() != null){

            builder.add("\n");
        }
        return super.visitCodeBlock(ctx);
    }

    @Override
    public CodeBlock.Builder visitInnerCode(DocReaderParser.InnerCodeContext ctx) {
        builder.add("[source,c]\n----$L----\n\n", ctx.getText());
        return super.visitInnerCode(ctx);
    }

    @Override
    public CodeBlock.Builder visitText(DocReaderParser.TextContext ctx) {
        return super.visitText(ctx);
    }

    public CodeBlock.Builder getBuilder() {
        return builder;
    }
}
