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

import com.pss.jvips.plugin.antlr.DocReaderLexer;
import com.pss.jvips.plugin.antlr.DocReaderParser;
import com.pss.jvips.plugin.antlr.DocReaderParserBaseVisitor;
import com.pss.jvips.plugin.constants.VipsConstant;
import com.pss.jvips.plugin.naming.JavaNaming;
import com.pss.jvips.plugin.naming.TypeRegistration;
import com.pss.jvips.plugin.service.executables.ExecutableDTO;
import com.pss.jvips.plugin.util.History;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

public class TextVisitor extends DocReaderParserBaseVisitor<CodeBlock.Builder> {

    private static final Logger log = LoggerFactory.getLogger(TextVisitor.class);
    protected final ParserContext context;
    protected final CodeBlock.Builder codeBlock = CodeBlock.builder();

    public TextVisitor(ParserContext context) {
        this.context = context;
    }

    @Override
    protected CodeBlock.Builder defaultResult() {
        return codeBlock;
    }


    @Override
    public CodeBlock.Builder visitPrimitiveType(DocReaderParser.PrimitiveTypeContext ctx) {

        codeBlock.add(ctx.Identifier().getText().toLowerCase());
        return super.visitPrimitiveType(ctx);
    }



    @Override
    public CodeBlock.Builder visitText(DocReaderParser.TextContext ctx) {

        var tokens = context.getTokens();
        if(ctx.start.getTokenIndex() > 0 ){
            Token token = tokens.get(ctx.start.getTokenIndex() - 1);
            if(token.getType() == DocReaderLexer.Newline){ // prepend if there is space after new line
                List<Token> leftPadding = tokens.getHiddenTokensToLeft(ctx.start.getTokenIndex(), DocReaderLexer.WHITESPACE);
                if(leftPadding != null){
                    String padding = " ".repeat(leftPadding.size());
                    codeBlock.add(padding);
                }
            }
        }

        ctx.children.forEach(p-> {
            if(p instanceof TerminalNode tn){
                codeBlock.add(tn.getText());
                padRight(tn);
            }
        });
        return super.visitText(ctx);
    }

    void getValue(String text){
        ExecutableDTO executable = context.getExecutable();
        executable.getOptionalArgument(text)
                                .ifPresentOrElse(arg-> {
                                    if(executable.hasSingularOptionalParameter()){
                                        codeBlock.add("{@code $L}", arg.getFormattedName().getJavaName());
                                    } else {
                                        codeBlock.add("{@link $T#$L}", executable.getDtoClassName(), arg.getFormattedName().getJavaName());
                                    }
                                }, () -> {
                                    String param = executable.getRequiredArgument(text).map(x-> x.getFormattedName().getJavaName()).orElse(text);
                                    codeBlock.add("{@code $L}", param);
                                });

    }

    @Override
    public CodeBlock.Builder visitFunctionType(DocReaderParser.FunctionTypeContext ctx) {
        context.getNamingService().findMethod(ctx.Identifier.getText()).ifPresentOrElse((x)-> {
            codeBlock.add("{@link $T#$L}", context.getClassName(), x.target().getJavaName());
        }, ()-> {
            log.warn("{} method Identifier was not found", ctx.Identifier().getText());
        });
        padRight(ctx);
        return super.visitFunctionType(ctx);
    }

    @Override
    public CodeBlock.Builder visitParameter(DocReaderParser.ParameterContext ctx) {
        getValue(ctx.Identifier().getText());


        padRight(ctx);
        return super.visitParameter(ctx);
    }

    @Override
    public CodeBlock.Builder visitUserType(DocReaderParser.UserTypeContext ctx) {
        String text = ctx.Identifier.getText();
        context.getConstantsService().lookupConstant(text)
                        .ifPresentOrElse((c)-> {
                            codeBlock.add("{@link $T#$L}", c.className(), c.name().getJavaName());
                        }, ()->{
                            codeBlock.add("$L", text);
                        });

        padRight(ctx);
        return super.visitUserType(ctx);
    }

    public void padRight(int index) {
        List<Token> hiddenTokensToRight = context.getTokens().getHiddenTokensToRight(index, DocReaderLexer.WHITESPACE);
        if (CollectionUtils.isNotEmpty(hiddenTokensToRight)) {
            codeBlock.add("$B".repeat(hiddenTokensToRight.size()));
        }
    }

    @Override
    public CodeBlock.Builder visitNewlineBlock(DocReaderParser.NewlineBlockContext ctx) {

       codeBlock.add("$B");
        return super.visitNewlineBlock(ctx);
    }

    public void padRight(ParserRuleContext ctx){
        padRight(ctx.stop.getTokenIndex());
    }

    public void padRight(TerminalNode ctx){
        padRight(ctx.getSymbol().getTokenIndex());
    }

    public void padRight(Token token){
        padRight(token.getTokenIndex());
    }
}
