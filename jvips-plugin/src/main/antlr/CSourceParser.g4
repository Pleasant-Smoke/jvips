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
parser grammar CSourceParser;

options {
    tokenVocab=CSourceLexer;
}

@parser::header {package com.pss.jvips.plugin.antlr;

import com.pss.jvips.plugin.service.executables.arguments.ImmutableEarlyStageArgumentDTO;
}

// Should probably use a visitor instead to cutdown on the noise!

// Args could be empty
codeBlocks: codeBlock* EOF;
codeBlock: vobjectNickname vobjectDescription? vobjectBuild?  args* CURLY_END;

stringLiteralInternal[StringBuilder sb]: DBL_QUOTE (STRING {sb.append($STRING.text);} )? DBL_QUOTE_END;

// Appearantly string literals don't need a concat operator
stringLiteral returns[StringBuilder sb = new StringBuilder()]: stringLiteralInternal[$sb]+?;

stringLiteralOrConstant locals[boolean unknownConstant = false]:
  sb = stringLiteral #SLCLiteral
| IDENTIFIER #SLCConstant
;

integerLiteralOrConstant locals[boolean unknownConstant = false, Long value = null]:
      INTEGER_LITERAL  #ILCLiteral
    | INT_MIN  #ILCMin
    | INT_MAX OP_MINUS INTEGER_LITERAL  #ILCMaxMinusOne
    | INT_MAX  #ILCMax
    | OP_MINUS? IDENTIFIER #ILCConstant
   ;

// might want to push this into a listener
doubleLiteralOrConstant locals[boolean unknownConstant = false, Double value = null]:
     INTEGER_LITERAL  #DLCILiteral // Could use the same but meh
   | DOUBLE_LITERAL  #DLCDLiteral
   | OP_MINUS? INFINITY #DLCInfinity
   | OP_MINUS? IDENTIFIER #DLCConstant
   ;


vobjectNickname: VOBJ_NICKNAME EQUAL stringLiteral SEMI_COLON;
vobjectDescription: VOBJ_DESCRIPTION EQUAL PARANS_OPEN_UNDERSCORE stringLiteral PARANS_CLOSE SEMI_COLON;
vobjectBuild: VOBJ_BUILD EQUAL IDENTIFIER SEMI_COLON;
//operationFlags: OPERATION_FLAGS EQUAL IDENTIFIER SEMI_COLON;

//argumentFlag locals[boolean input = true, boolean required = false, boolean skip = false;]:
//                VIPS_ARGUMENT_REQUIRED_INPUT {$required = true;}
//              | VIPS_ARGUMENT_OPTIONAL_INPUT
//              | VIPS_ARGUMENT_REQUIRED_OUTPUT {$required = true; $input = false;}
//              | VIPS_ARGUMENT_OPTIONAL_OUTPUT {$input = false;}
//              | VIPS_ARGUMENT_NONE
//              | VIPS_ARGUMENT_REQUIRED {$required = true;}
//              | VIPS_ARGUMENT_CONSTRUCT
//              | VIPS_ARGUMENT_SET_ONCE
//              | VIPS_ARGUMENT_SET_ALWAYS
//              | VIPS_ARGUMENT_INPUT
//              | VIPS_ARGUMENT_OUTPUT {$input = false;}
//              | VIPS_ARGUMENT_DEPRECATED {$skip = true;}
//              | VIPS_ARGUMENT_MODIFY;

argumentFlag:
              token = ( VIPS_ARGUMENT_REQUIRED_INPUT
              | VIPS_ARGUMENT_OPTIONAL_INPUT
              | VIPS_ARGUMENT_REQUIRED_OUTPUT
              | VIPS_ARGUMENT_OPTIONAL_OUTPUT
              | VIPS_ARGUMENT_NONE
              | VIPS_ARGUMENT_REQUIRED
              | VIPS_ARGUMENT_CONSTRUCT
              | VIPS_ARGUMENT_SET_ONCE
              | VIPS_ARGUMENT_SET_ALWAYS
              | VIPS_ARGUMENT_INPUT
              | VIPS_ARGUMENT_OUTPUT
              | VIPS_ARGUMENT_DEPRECATED
              | VIPS_ARGUMENT_MODIFY);

argumentFlags: argumentFlag (BIT_OR argumentFlag)*;

// argumentFlags: argumentFlag (BIT_OR argumentFlag)*;

argCommon[ImmutableEarlyStageArgumentDTO.Builder arguments]:  IDENTIFIER COMMA
            name = stringLiteral COMMA
            INTEGER_LITERAL COMMA
            PARANS_OPEN_UNDERSCORE label = stringLiteral PARANS_CLOSE COMMA
            PARANS_OPEN_UNDERSCORE description = stringLiteral PARANS_CLOSE COMMA
            argumentFlags COMMA
            G_STRUCT_OFFSET PARANS_OPEN
                IDENTIFIER COMMA
                IDENTIFIER
            PARANS_CLOSE ;

argDouble[ImmutableEarlyStageArgumentDTO.Builder arguments]
    :
    VIPS_ARG_DOUBLE PARANS_OPEN
    argCommon[$arguments] COMMA
    low = doubleLiteralOrConstant COMMA
    high = doubleLiteralOrConstant COMMA
    defaultValue = doubleLiteralOrConstant
    PARANS_CLOSE
    SEMI_COLON;

argInt[ImmutableEarlyStageArgumentDTO.Builder arguments]
    :
    VIPS_ARG_INT PARANS_OPEN
    argCommon[$arguments] COMMA
    low = integerLiteralOrConstant COMMA
    high = integerLiteralOrConstant COMMA
    defaultValue = integerLiteralOrConstant
    PARANS_CLOSE
    SEMI_COLON;


argBool[ImmutableEarlyStageArgumentDTO.Builder arguments]: VIPS_ARG_BOOL PARANS_OPEN
    argCommon[$arguments] COMMA
    defaultValue = IDENTIFIER //{$defText = $defaultValue.text;}
    PARANS_CLOSE
    SEMI_COLON;

// Enumes use the following macro format `VIPS_TYPE_INTENT` becomes identifier/ctype/glibtype = VipsIntent
// and the name becomes `Intent`
argEnum[ImmutableEarlyStageArgumentDTO.Builder arguments]: VIPS_ARG_ENUM PARANS_OPEN
             argCommon[$arguments] COMMA
             type = IDENTIFIER COMMA
             defaultValue = IDENTIFIER
             PARANS_CLOSE
             SEMI_COLON;

argImage[ImmutableEarlyStageArgumentDTO.Builder arguments]: VIPS_ARG_IMAGE PARANS_OPEN
             argCommon[$arguments]
             PARANS_CLOSE
             SEMI_COLON;

// Living dangerous here
argUInt[ImmutableEarlyStageArgumentDTO.Builder arguments]
    :
    VIPS_ARG_UINT64 PARANS_OPEN
    argCommon[$arguments] COMMA
    low = integerLiteralOrConstant COMMA
    high = integerLiteralOrConstant COMMA
    defaultValue = integerLiteralOrConstant
    PARANS_CLOSE
    SEMI_COLON;

argString[ImmutableEarlyStageArgumentDTO.Builder arguments]: VIPS_ARG_STRING PARANS_OPEN
    argCommon[$arguments] COMMA
    defaultValue =  stringLiteralOrConstant
    PARANS_CLOSE
    SEMI_COLON;



argFlags[ImmutableEarlyStageArgumentDTO.Builder arguments]: VIPS_ARG_FLAGS PARANS_OPEN
         argCommon[$arguments] COMMA
         type = IDENTIFIER COMMA
         defaultValue = IDENTIFIER
         PARANS_CLOSE
         SEMI_COLON;

argPointer[ImmutableEarlyStageArgumentDTO.Builder arguments]: VIPS_ARG_POINTER PARANS_OPEN
             argCommon[$arguments]
             PARANS_CLOSE
             SEMI_COLON;

argBoxed[ImmutableEarlyStageArgumentDTO.Builder arguments]: VIPS_ARG_BOXED PARANS_OPEN
    argCommon[$arguments] COMMA
    type = IDENTIFIER
    PARANS_CLOSE
    SEMI_COLON;

argObject[ImmutableEarlyStageArgumentDTO.Builder arguments]: VIPS_ARG_OBJECT PARANS_OPEN
    argCommon[$arguments] COMMA
    type = IDENTIFIER
    PARANS_CLOSE
    SEMI_COLON;

argInterpolate[ImmutableEarlyStageArgumentDTO.Builder arguments]: VIPS_ARG_INTERPOLATE PARANS_OPEN
    argCommon[$arguments]
    PARANS_CLOSE
    SEMI_COLON;

args locals[ImmutableEarlyStageArgumentDTO.Builder arguments = ImmutableEarlyStageArgumentDTO.builder()]:
    argInt[$arguments]
    | argDouble[$arguments]
    | argBool[$arguments]
    | argEnum[$arguments]
    | argImage[$arguments]
    | argString[$arguments]
    | argUInt[$arguments]
    | argFlags[$arguments]
    | argPointer[$arguments]
    | argBoxed[$arguments]
    | argInterpolate[$arguments]
    | argObject[$arguments];