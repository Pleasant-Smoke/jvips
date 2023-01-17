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
parser grammar DocReaderParser;
options{
    tokenVocab=DocReaderLexer;
}

//@lexer::header {package com.pss.jvips.plugin.antlr;
//}

@parser::header {package com.pss.jvips.plugin.antlr;

import com.pss.jvips.plugin.naming.JavaCaseFormat;
import com.pss.jvips.plugin.naming.JavaNaming;
import com.pss.jvips.plugin.naming.JavaTypeMapping;
import com.squareup.javapoet.TypeName;


}
@parser::members {
 public enum TypesEnum {FUNCTION, PRIMITIVE, USER_TYPE, STRING, NONE};
}

documentation: (optionalArguments | codeBlock | mainBlocks |  seeAlsoDoclet)+;



functionType[boolean isOptionalArgument]
    locals[JavaCaseFormat javaName]: Identifier ParansOpen ParansClose {$javaName = JavaNaming.methodName($Identifier.text);};

primitiveType[boolean isOptionalArgument]
    locals[TypeName typeName]: Percent Identifier {$typeName = JavaTypeMapping.getType($Identifier.text);};

userType[boolean isOptionalArgument]
    locals[JavaCaseFormat javaName]: Hash Identifier {$javaName = JavaNaming.registerConstant($Identifier.text);};

parameter[boolean isOptionalArgument]
    locals[JavaCaseFormat javaName]: At Identifier {$javaName = JavaNaming.parameterName($Identifier.text);};

stringType[boolean isOptionalArgument]
    locals[TypeName typeName]: String {$typeName = JavaTypeMapping.getType($String.text);};

types[boolean isOptionalArgument] returns[TypesEnum getType]:
    functionType[isOptionalArgument] {$getType = TypesEnum.FUNCTION;}
    | stringType[isOptionalArgument] {$getType = TypesEnum.STRING;}
    | primitiveType[isOptionalArgument] {$getType = TypesEnum.PRIMITIVE;}
    | userType[isOptionalArgument] {$getType = TypesEnum.USER_TYPE;};




optionalArguments: OptionalArguments Paragraph optionalArgument+ Paragraph;

// ^\* @(_|\w)+\: (?!%|#) <- check for no type
optionalArgument locals[TypesEnum hasType = TypesEnum.NONE, boolean out = false]:
Star p=parameter[true] Colon (Output {$out = true;})? UseThis? (types[true] {$hasType = $types.getType;})? Comma? blocks[true, $p.ctx.Identifier.getText()]? Newline?;


seeAlsoDoclet: SeeAlso (types[false] ( Comma Newline? types[false])* Dot? | <EOF>);
seeAlsoInline: See Newline? types[false];

fileQuery: (DoubleQuote FileQueryParam DoubleQuote) | FileQueryParam;
cmdParam: ((DoubleQuote Identifier DoubleQuote) ) (Newline)?;
quotedIdent: QuotedIdentifier (Newline | Paragraph)?;
envVariable: EnvVar;
text: (Everything|Comma|Colon|See|HtmlTag|Dot|Dash|Identifier|CurlyOpen|CurlyClose| Percent
|DoubleQuote|BackTick|Star|BracketOpen|BracketClose|Equal|SingleQuote|SemiColon|OtherDash|Output|String|UseThis|DecimalNumber
|IntegerNumber
//|DefaultsTo|DefaultIs
)+;
//textInParans: ParansOpen (text (Newline text)+ | text ) ParansClose;
//typeInParans: ParansOpen (types (Newline types)+ | types ) ParansClose;
blockInParans: ParansOpen block[false]+ ParansClose;
quotedFile: DoubleQuote Everything  (FileQueryParam)? DoubleQuote;
code: CodeOpen innerCode CodeClose (Newline )?;
innerCode: (All|EndBracket|Or)+;
codeBlock: blocks[false, null] (Paragraph) code+ (Paragraph | <EOF>);
mainBlocks:  blocks[false, null] (Paragraph | <EOF>);

block[boolean inOptionalArg]:
    //  typeInferencing #TypeInferencingBlock
   // |
    types[false] #FunctionTypePrimitiveBlock
    | quotedFile #QuotedFileBlock
    | quotedIdent #QuotedIdentifierBlock
    | envVariable #EnvironmentalBlock
    | seeAlsoInline #SeeAlsoInlineBlock
    | blockInParans #BlockInParansBLock
    | parameter[false] #ParameterBlock
    | fileQuery #FileQueryBlock
    | cmdParam #CommandParamBlock
    | text #TextBlock
    | //{!$inOptionalArg}?
    Newline #NewlineBlock
    ;

blocks[boolean inOptionalArg, String optionalArg]: block[$inOptionalArg]+ ;

//typeInferencing: (UseThis | DefaultsTo | DefaultIs) (types[false]|DecimalNumber|IntegerNumber);