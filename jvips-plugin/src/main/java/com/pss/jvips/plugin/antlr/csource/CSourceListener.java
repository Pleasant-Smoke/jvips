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

import com.google.common.base.Preconditions;
import com.pss.jvips.plugin.antlr.*;
import com.pss.jvips.plugin.naming.*;
import com.pss.jvips.plugin.model.xml.types.Direction;
import com.pss.jvips.plugin.context.GlobalPluginContext;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

import static com.pss.jvips.plugin.antlr.CSourceLexer.*;

public class CSourceListener extends CSourceParserBaseListener {

    private static final List<Integer> REQUIRED = List.of(VIPS_ARGUMENT_REQUIRED, VIPS_ARGUMENT_REQUIRED_INPUT, VIPS_ARGUMENT_REQUIRED_OUTPUT);
    private static final List<Integer> INPUT = List.of(VIPS_ARGUMENT_INPUT, VIPS_ARGUMENT_REQUIRED_INPUT, VIPS_ARGUMENT_OPTIONAL_INPUT);
    private static final List<Integer> OUTPUT = List.of(VIPS_ARGUMENT_OUTPUT, VIPS_ARGUMENT_REQUIRED_OUTPUT, VIPS_ARGUMENT_OPTIONAL_OUTPUT);

    private static final Logger log = LoggerFactory.getLogger(CSourceListener.class);

    private final GlobalPluginContext context;

    public CSourceListener(GlobalPluginContext context) {
        this.context = context;
    }


    @Override
    public void exitArgCommon(CSourceParser.ArgCommonContext ctx) {
        List<Integer> types = ctx.argumentFlags().argumentFlag().stream().map(x -> x.token.getType()).toList();
        boolean input = CollectionUtils.containsAny(types, INPUT);
        boolean output = CollectionUtils.containsAny(types, OUTPUT);
        boolean required = CollectionUtils.containsAny(types, REQUIRED);
        Preconditions.checkArgument(input ^ output);
        ctx.arguments.formattedName(JavaNaming.parameterName((String) getString(ctx.name).value()))
                .description((String) getString(ctx.description).value())
                .label((String) getString(ctx.label).value())
                .direction(input ? Direction.IN : Direction.OUT)
                .isDeprecated(types.contains(VIPS_ARGUMENT_DEPRECATED))
                .isRequired(required);


        super.exitArgCommon(ctx);
    }

    protected ValueHolder getString(CSourceParser.StringLiteralContext ctx){
        if(ctx != null){
            return ctx.accept(new StringVisitor());
        }
        return null;
    }

    @Override
    public void exitArgDouble(CSourceParser.ArgDoubleContext ctx) {
        ctx.arguments
                .type(TypeName.DOUBLE)
                .macroType(MacroType.DOUBLE)
                .defaultValue(ctx.defaultValue.accept(new DoubleVisitor(context)))
                .minValue(ctx.low.accept(new DoubleVisitor(context)))
                .maxValue(ctx.high.accept(new DoubleVisitor(context)));
        super.exitArgDouble(ctx);
    }

    @Override
    public void exitArgInt(CSourceParser.ArgIntContext ctx) {
        ctx.arguments
                .type(TypeName.LONG)
                .macroType(MacroType.INT)
                .defaultValue(ctx.defaultValue.accept(new LongVisitor(context)))
                .minValue(ctx.low.accept(new LongVisitor(context)))
                .maxValue(ctx.high.accept(new LongVisitor(context)));
        super.exitArgInt(ctx);
    }

    @Override
    public void exitArgBool(CSourceParser.ArgBoolContext ctx) {
        ctx.arguments
                .type(TypeName.BOOLEAN)
                .macroType(MacroType.BOOLEAN);
        Optional.ofNullable(ctx.defaultValue)
                .map(token-> token.getText())
                        .ifPresent(text-> {
                            if("TRUE".equalsIgnoreCase(text)){
                                ctx.arguments.defaultValue(new ValueHolder(true));
                            } else if("FALSE".equalsIgnoreCase(text)){
                                ctx.arguments.defaultValue(new ValueHolder(false));
                            } else {
                                log.warn("Unknown Boolean Identifier: {}", text);
                            }
                        });
        super.exitArgBool(ctx);
    }

    @Override
    public void exitArgEnum(CSourceParser.ArgEnumContext ctx) {
        JavaCaseFormat javaName = JavaNaming.getJavaClassNameMacroName(ctx.type.getText());
        var type = JavaTypeMapping.getType(javaName.getJavaName());
        ctx.arguments
                .type(type)
                .macroType(MacroType.ENUM)
                .defaultValue(new ValueHolder((ClassName) type,  Optional.ofNullable(ctx.defaultValue.getText()).flatMap(x-> JavaNaming.getConstant(x)).orElse(null)));
        super.exitArgEnum(ctx);
    }

    @Override
    public void exitArgImage(CSourceParser.ArgImageContext ctx) {
        ctx.arguments
                .type(JavaTypeMapping.JVipsImage_class)
                .macroType(MacroType.IMAGE)
                .isImage(true);

    }

    @Override
    public void exitArgUInt(CSourceParser.ArgUIntContext ctx) {
        ctx.arguments
                .type(TypeName.LONG)
                .macroType(MacroType.UINT)
                .defaultValue(ctx.defaultValue.accept(new LongVisitor(context)))
                .minValue(ctx.low.accept(new LongVisitor(context)))
                .maxValue(ctx.high.accept(new LongVisitor(context)));

        super.exitArgUInt(ctx);
    }


    // Can be an empty string or null

    @Override
    public void exitArgString(CSourceParser.ArgStringContext ctx) {
        ctx.arguments.type(JavaTypeMapping.String_class).macroType(MacroType.STRING);
        super.exitArgString(ctx);
    }

    @Override
    public void exitArgFlags(CSourceParser.ArgFlagsContext ctx) {
        ctx.arguments.type(TypeName.INT).macroType(MacroType.FLAGS);
        super.exitArgFlags(ctx);
    }

    @Override
    public void exitArgPointer(CSourceParser.ArgPointerContext ctx) {
        ctx.arguments.type(TypeName.LONG).macroType(MacroType.POINTER);
        super.exitArgPointer(ctx);
    }

    @Override
    public void exitArgBoxed(CSourceParser.ArgBoxedContext ctx) {
        if("VIPS_TYPE_ARRAY_DOUBLE".equals(ctx.type.getText())){
            ctx.arguments.type(JavaTypeMapping.Double_Array);
        } else if("VIPS_TYPE_ARRAY_INT".equals(ctx.type.getText())){
            ctx.arguments.type(JavaTypeMapping.Int_Array);
        } else if("VIPS_TYPE_ARRAY_IMAGE".equals(ctx.type.getText())){
            ctx.arguments.type(JavaTypeMapping.VipsImage_Array_class);
            ctx.arguments.isImage(true);
        }
        ctx.arguments.macroType(MacroType.BOXED);
    }

    @Override
    public void exitArgObject(CSourceParser.ArgObjectContext ctx) {
        JavaCaseFormat javaName = JavaNaming.getJavaClassNameMacroName(ctx.type.getText());
        ctx.arguments
                .type( JavaTypeMapping.getType(javaName.getJavaName()))
                .macroType(MacroType.OBJECT);
        super.exitArgObject(ctx);
    }

    @Override
    public void exitArgInterpolate(CSourceParser.ArgInterpolateContext ctx) {
        ctx.arguments
                .type(JavaTypeMapping.String_class)
                .macroType(MacroType.INTERPOLATE);
        super.exitArgInterpolate(ctx);
    }

    static class DoubleVisitor extends CSourceParserBaseVisitor<ValueHolder> {

        private final GlobalPluginContext context;

        DoubleVisitor(GlobalPluginContext context) {
            this.context = context;
        }

        @Override
        public ValueHolder visitDLCILiteral(CSourceParser.DLCILiteralContext ctx) {
            return new ValueHolder(Double.valueOf(ctx.getText()));
        }

        @Override
        public ValueHolder visitDLCDLiteral(CSourceParser.DLCDLiteralContext ctx) {
             return  new ValueHolder(Double.valueOf(ctx.getText()));
        }

        @Override
        public ValueHolder visitDLCInfinity(CSourceParser.DLCInfinityContext ctx) {
            var cn = ClassName.get(Double.class);
            var negInf = ImmutableJavaCaseFormat.builder().javaName("NEGATIVE_INFINITY").nativeName("NEGATIVE_INFINITY").build();
            var posInf = ImmutableJavaCaseFormat.builder().javaName("POSITIVE_INFINITY").nativeName("POSITIVE_INFINITY").build();
            return ctx.OP_MINUS() != null ?  new ValueHolder(cn, negInf, Double.NEGATIVE_INFINITY)  : new ValueHolder(cn, posInf, Double.POSITIVE_INFINITY);
        }

        @Override
        public ValueHolder visitDLCConstant(CSourceParser.DLCConstantContext ctx) {
            String value = context.constants().get(ctx.IDENTIFIER().getText());
            String text = ctx.IDENTIFIER().getText();
            return JavaNaming.getConstant(text)
                    .map(x-> TypeRegistration.resolve(x))
                    .map((cf)->{
                        return new ValueHolder(cf.first(), cf.second(), ctx.OP_MINUS() != null ? -Double.parseDouble(value) : Double.parseDouble(value), ctx.OP_MINUS() != null, false);

                    }).orElse(null);


        }
    }

    static class LongVisitor extends CSourceParserBaseVisitor<ValueHolder> {

        private final GlobalPluginContext context;

        LongVisitor(GlobalPluginContext context) {
            this.context = context;
        }

        @Override
        public ValueHolder visitILCLiteral(CSourceParser.ILCLiteralContext ctx) {
            return new ValueHolder(Long.parseLong(ctx.getText()));
        }

        @Override
        public ValueHolder visitILCMin(CSourceParser.ILCMinContext ctx) {
            var cn = ClassName.get(Long.class);
            var negInf = ImmutableJavaCaseFormat.builder().javaName("MIN_VALUE").nativeName("MIN_VALUE").build();
            return  new ValueHolder(cn, negInf, Long.MIN_VALUE);

        }

        @Override
        public ValueHolder visitILCMaxMinusOne(CSourceParser.ILCMaxMinusOneContext ctx) {
            var cn = ClassName.get(Long.class);
            var negInf = ImmutableJavaCaseFormat.builder().javaName("MAX_VALUE").nativeName("MAX_VALUE").build();
            return  new ValueHolder(cn, negInf, Long.MAX_VALUE, false, true);

        }

        @Override
        public ValueHolder visitILCMax(CSourceParser.ILCMaxContext ctx) {
            var cn = ClassName.get(Long.class);
            var negInf = ImmutableJavaCaseFormat.builder().javaName("MAX_VALUE").nativeName("MAX_VALUE").build();
            return  new ValueHolder(cn, negInf, Long.MAX_VALUE);
        }

        @Override
        public ValueHolder visitILCConstant(CSourceParser.ILCConstantContext ctx) {
            String value = context.constants().get(ctx.IDENTIFIER().getText());
            String text = ctx.IDENTIFIER().getText();
            return JavaNaming.getConstant(text)
                    .map(x-> TypeRegistration.resolve(x))
                    .map((cf)->{
                        return new ValueHolder(cf.first(), cf.second(), ctx.OP_MINUS() != null ? -Long.parseLong(value) : Long.parseLong(value), ctx.OP_MINUS() != null, false);

                    }).orElse(null);

        }
    }

    static class StringVisitor extends CSourceParserBaseVisitor<ValueHolder> {

//        @Override
//        public String visitStringLiteralInternal(CSourceParser.StringLiteralInternalContext ctx) {
//            if(ctx.STRING != null){
//                ctx.sb.append(ctx.STRING.getText());
//            }
//            return super.visitStringLiteralInternal(ctx);
//        }

        @Override
        public ValueHolder visitStringLiteral(CSourceParser.StringLiteralContext ctx) {
            return new ValueHolder(ctx.sb.toString());
        }

        @Override
        public ValueHolder visitSLCLiteral(CSourceParser.SLCLiteralContext ctx) {
           return  new ValueHolder(ctx.stringLiteral().sb.toString());
        }

        @Override
        public ValueHolder visitSLCConstant(CSourceParser.SLCConstantContext ctx) {
            return super.visitSLCConstant(ctx);
        }
    }
}
