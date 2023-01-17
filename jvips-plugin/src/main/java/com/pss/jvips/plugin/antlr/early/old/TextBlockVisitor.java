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

import com.pss.jvips.plugin.antlr.DocReaderLexer;
import com.pss.jvips.plugin.antlr.DocReaderParser;
import com.pss.jvips.plugin.antlr.DocReaderParserBaseVisitor;
import com.pss.jvips.plugin.args.OptionalParam;
import com.pss.jvips.plugin.model.xml.executable.AbstractExecutable;
import com.pss.jvips.plugin.naming.JavaNaming;
import com.pss.jvips.plugin.naming.TypeRegistration;
import com.pss.jvips.plugin.util.MethodOptionalParametersDocumentation;
import com.pss.jvips.plugin.context.ParserScopedPluginContext;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;

public class TextBlockVisitor extends DocReaderParserBaseVisitor<CodeBlock.Builder> {

    private final CodeBlock.Builder codeBlock = CodeBlock.builder();

    private final BufferedTokenStream tokens;

    private final ClassName currentClass;

    private final List<OptionalParam> optionalParams;

    private final Map<UUID, MethodOptionalParametersDocumentation> singularOptionalParams;
    private final Map<UUID, ClassName> optionalParameters;

    private final AbstractExecutable executable;
    private final ParserScopedPluginContext context;

    public TextBlockVisitor(ParserScopedPluginContext context){
        this(context, Collections.emptyList());
    }

    public TextBlockVisitor(ParserScopedPluginContext context, List<OptionalParam> optionalParams){
        this.context = context;
        this.tokens = context.tokenStream();
        this.currentClass = context.currentClass();
        this.singularOptionalParams = context.singularOptionalParams();
        this.optionalParameters = context.dtoOptionalParameters();
        this.executable = context.currentExecutable();
        this.optionalParams = optionalParams;
    }


    @Override
    public CodeBlock.Builder visitPrimitiveType(DocReaderParser.PrimitiveTypeContext ctx) {

        codeBlock.add(ctx.Identifier().getText().toLowerCase());
        return super.visitPrimitiveType(ctx);
    }

    @Override
    public CodeBlock.Builder visitText(DocReaderParser.TextContext ctx) {
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

    @Override
    public CodeBlock.Builder visitParameter(DocReaderParser.ParameterContext ctx) {
        getValue(ctx.Identifier().getText());


        padRight(ctx);
        return super.visitParameter(ctx);
    }


    @Override
    protected CodeBlock.Builder defaultResult() {
        return codeBlock;
    }

    void getValue(String text){

         optionalParams.stream()
                    .filter(t-> Objects.equals(t.getName(), text) && context.isDTOOptionalParam(executable))
                    .findFirst()
                        .ifPresentOrElse(va-> {
                            codeBlock.add("{@link $T#$L}", context.getDtoOptionalParam(executable), text);
                        }, ()->{
                            codeBlock.add("{@code $L}", text);
                        });

    }

    @Override
    public CodeBlock.Builder visitFunctionType(DocReaderParser.FunctionTypeContext ctx) {
        ClassName className = TypeRegistration.getRegisteredExecutableClass(ctx.javaName, context.currentClass());
        codeBlock.add("{@link $T#$L}", className, ctx.javaName.getJavaName());
        padRight(ctx);
        return super.visitFunctionType(ctx);
    }

    @Override
    public CodeBlock.Builder visitUserType(DocReaderParser.UserTypeContext ctx) {
        String text = ctx.Identifier.getText();
        JavaNaming.getConstant(text)
                .map(x-> TypeRegistration.resolve(x))
                .ifPresentOrElse((cf)-> {
                    codeBlock.add("{@link $T#$L}", cf.first(), cf.second().getJavaName());
                }, ()->{
                    codeBlock.add("$L", text);
                });
        padRight(ctx);
        return super.visitUserType(ctx);
    }

    @Override
    public CodeBlock.Builder visitNewlineBlock(DocReaderParser.NewlineBlockContext ctx) {

        codeBlock.add("\n");
        return super.visitNewlineBlock(ctx);
    }

    public void padRight(int index) {
        List<Token> hiddenTokensToRight = tokens.getHiddenTokensToRight(index, DocReaderLexer.WHITESPACE);
        if (CollectionUtils.isNotEmpty(hiddenTokensToRight)) {
            codeBlock.add(" ".repeat(hiddenTokensToRight.size()));
        }
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
