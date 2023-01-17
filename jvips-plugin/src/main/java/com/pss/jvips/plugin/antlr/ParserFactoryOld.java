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

package com.pss.jvips.plugin.antlr;

import com.pss.jvips.plugin.antlr.csource.CSourceListener;
import com.pss.jvips.plugin.antlr.csource.VisitCSourceFile;
import com.pss.jvips.plugin.antlr.csource.VisitedCodeBlock;
import com.pss.jvips.plugin.antlr.early.old.BlockVisitorOld;
import com.pss.jvips.plugin.antlr.early.old.EarlyStageOptionalParamVisitorOld;
import com.pss.jvips.plugin.overrides.VipsOverrides;
import com.pss.jvips.plugin.util.MethodOptionalParametersDocumentation;
import com.pss.jvips.plugin.util.SourceLoader;
import com.pss.jvips.plugin.context.GlobalPluginContext;
import com.pss.jvips.plugin.context.ParserScopedPluginContext;
import com.pss.jvips.plugin.context.ScopedPluginContext;
import com.squareup.javapoet.CodeBlock;
import org.antlr.v4.runtime.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class ParserFactoryOld {

    private static final Logger log = LoggerFactory.getLogger(ParserFactoryOld.class);

    private static final Map<String, Map<String, VisitedCodeBlock>> visited = new HashMap<>();

    public static Map<String, VisitedCodeBlock> FOREIGN;
    public static Map<String, VisitedCodeBlock> MAGICK7LOAD;





    public static CodeBlock getMethodDoc(String value, ScopedPluginContext context){
        CharStream charStream = CharStreams.fromString(value);
        DocReaderLexer lexer = new DocReaderLexer(charStream);
        lexer.addErrorListener(new BaseErrorListener(){
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                super.syntaxError(recognizer, offendingSymbol, line, charPositionInLine, msg, e);
            }
        });
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        var parserContext = new ParserScopedPluginContext(context, tokenStream);
        BlockVisitorOld visitor = new BlockVisitorOld(parserContext);
        var p = new DocReaderParser(tokenStream);
        return p.documentation().accept(visitor).build();
    }



   public static Optional<MethodOptionalParametersDocumentation> optionalParams(String value, ScopedPluginContext context){
        if(value.startsWith("Optional arguments")) {
            CharStream charStream = CharStreams.fromString(value);
            DocReaderLexer lexer = new DocReaderLexer(charStream);
            lexer.addErrorListener(new BaseErrorListener(){
                @Override
                public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                    super.syntaxError(recognizer, offendingSymbol, line, charPositionInLine, msg, e);
                }
            });
            CommonTokenStream tokenStream = new CommonTokenStream(lexer);
            DocReaderParser parser = new DocReaderParser(tokenStream);
            parser.addErrorListener(new BaseErrorListener(){
                @Override
                public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                    super.syntaxError(recognizer, offendingSymbol, line, charPositionInLine, msg, e);
                }
            });
            var parserContext = new ParserScopedPluginContext(context, tokenStream);
            EarlyStageOptionalParamVisitorOld visitor = new EarlyStageOptionalParamVisitorOld(parserContext);

            return Optional.of(parser.documentation().accept(visitor));

        }

        return Optional.empty();
   }



    public static Map<String, VisitedCodeBlock> loadCSource(String value, GlobalPluginContext context){
        if(FOREIGN == null){
            FOREIGN = getCodeBlocksInternal("libvips/foreign/foreign.c", context);
        }

        if(SourceLoader.getSrcDir() != null){
            return visited.computeIfAbsent(value, (val)->{
                return getCodeBlocksInternal(value, context);
            });


        }

        return Collections.emptyMap();

    }

    @NotNull
    private static Map<String, VisitedCodeBlock> getCodeBlocksInternal(String value, GlobalPluginContext context) {
        log.warn("Entering: {}", value);
        Path resolve = SourceLoader.getSrcDir().resolve(value);
        try {
            CharStream charStream  = CharStreams.fromPath(resolve);

            CSourceLexer lexer = new CSourceLexer(charStream);
            lexer.addErrorListener(new BaseErrorListener(){
                @Override
                public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                    super.syntaxError(recognizer, offendingSymbol, line, charPositionInLine, msg, e);
                }
            });
            CommonTokenStream tokenStream = new CommonTokenStream(lexer);

            CSourceParser parser = new CSourceParser(tokenStream);
            parser.addParseListener(new CSourceListener(context));
            parser.addErrorListener(new BaseErrorListener(){
                @Override
                public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                    System.out.println("Error at: " + offendingSymbol);
                }
            });
            List<VisitedCodeBlock> visit = new VisitCSourceFile(value).visit(parser.codeBlocks());

            log.warn("Visit found: {}", visit.size());
            return visit.stream().collect(Collectors.toMap((VisitedCodeBlock k) -> k.nickname(), (VisitedCodeBlock k) -> k));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyMap();
    }


    public static VisitedCodeBlock loadCSource(String value, String nickName, GlobalPluginContext context){

        return loadCSource(VipsOverrides.getOverrides().remap(nickName, value), context).get(nickName);
    }

}
