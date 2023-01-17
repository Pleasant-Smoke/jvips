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

package com.pss.jvips.plugin.antlr.csource;

import com.pss.jvips.plugin.antlr.CSourceParser;
import com.pss.jvips.plugin.antlr.CSourceParserBaseVisitor;
import com.pss.jvips.plugin.service.executables.arguments.EarlyStageArgumentDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

import static com.pss.jvips.plugin.antlr.ParserFactoryOld.FOREIGN;

public class VisitCSourceFile extends CSourceParserBaseVisitor<List<VisitedCodeBlock>> {

    private static final Logger log = LoggerFactory.getLogger(VisitCSourceFile.class);

    /**
     * The Following methods have overloads that do not follow the same template as other operations, the single
     * constructor {@link Overload} example "draw_flood" becomes Overload(file: "draw_flood", nickname: "draw_flood",
     * newNickname: "draw_flood1")
     */
    private static final List<Overload> OVERLOADS = List.of(
            new Overload("draw_flood"),
            new Overload("draw_circle"),
            new Overload("draw_rect"),
            new Overload("svgload", "svgload_buffer", "svgload_string"),
            new Overload("linear.c", "linear", "linear1"));

    private final List<VisitedCodeBlock> blocks = new ArrayList<>();
    private final String fileName;


    public VisitCSourceFile(String fileName) {
        this.fileName = fileName;
    }

    public record Overload(String file, String nickname, String newNickname){
        public Overload(String name){
            this(name, name, name + "1");
        }
    }

    //svgload_string needs a special case, it just calls svgload_buffer
    @Override
    public List<VisitedCodeBlock> visitCodeBlocks(CSourceParser.CodeBlocksContext ctx) {
        super.visitCodeBlocks(ctx);

        if(!blocks.isEmpty()){
            List<VisitedCodeBlock> base = blocks.stream()
                    .filter(x ->
                            x.nickname().endsWith("_base")
                            || x.nickname().equals("icc") // Base method in icc file is just `icc`
                            || (fileName.endsWith("tilecache.c") && x.nickname().endsWith("blockcache"))) // `blockcache` is the base here
                    .toList();
            if(base.size() > 1){
                log.warn("Base was greater than 1, names: {}", base.stream().map(VisitedCodeBlock::nickname).collect(Collectors.toList()));
            } else if(base.size() == 1) {
                VisitedCodeBlock baseBLock = base.get(0);
                blocks.remove(baseBLock);
                blocks.forEach(x-> x.arguments().putAll(baseBLock.arguments()));

            }
            if(fileName.contains("save")){
                Map<String, EarlyStageArgumentDTO> save = FOREIGN.get("save").arguments();
                blocks.forEach(x-> x.arguments().putAll(save));
            } else if(fileName.contains("load")){
                Map<String, EarlyStageArgumentDTO> save = FOREIGN.get("load").arguments();
                blocks.forEach(x-> x.arguments().putAll(save));
            }

            OVERLOADS.forEach(overload -> {
                if(fileName.contains(overload.file())){
                    blocks.stream()
                            .filter(x-> x.nickname().equals(overload.nickname()))
                            .findFirst()
                            .ifPresent(x-> blocks.add(new VisitedCodeBlock(overload.newNickname(), x.description(), x.arguments())));
                }
            });
            return blocks;
        }
        return Collections.emptyList();
    }





    @Override
    public List<VisitedCodeBlock> visitCodeBlock(CSourceParser.CodeBlockContext ctx) {

        var nickname = Optional.ofNullable(ctx.vobjectNickname().stringLiteral()).map(x->x.sb.toString()).orElse("");
        var description = Optional.ofNullable(ctx.vobjectDescription()).map(x-> x.stringLiteral()).map(x->x.sb.toString()).orElse("");
        if(CollectionUtils.isNotEmpty(ctx.args())){
            var arguments = ctx.args().stream().map(x-> (EarlyStageArgumentDTO) x.arguments.build()).collect(Collectors.toMap(x-> x.getFormattedName().getJavaName(), x-> x)); // This needs to be mutable
            blocks.add(new VisitedCodeBlock(nickname, description, arguments));
        } else {
            blocks.add(new VisitedCodeBlock(nickname, description, new HashMap<>())); // This needs to be mutable
        }
        return super.visitCodeBlock(ctx);
    }
}
