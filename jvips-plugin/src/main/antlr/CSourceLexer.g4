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
lexer grammar CSourceLexer;
@lexer::header {package com.pss.jvips.plugin.antlr;
}
// The nuclear option


fragment Digit: [0-9];
fragment IdentifierStart: [a-zA-Z_];
fragment IdentifierRest: (IdentifierStart|Digit);
fragment Whitespace: [ \t\n\r];


COMMENT_BLOCK: '/*' -> skip, pushMode(COMMENTS);
VOBJ_SKIP: ('object_class->nickname'|'vobject_class->nickname') (',' | ' )') -> skip;
VOBJ_NICKNAME: ('object_class->nickname'|'vobject_class->nickname') -> pushMode(ARG_PARSING);


SKIPABLE: .+? -> skip;

mode ARG_PARSING;

INT_MIN: 'INT_MIN';
INT_MAX: 'INT_MAX';
COMMENT_START: '/*' -> skip, pushMode(COMMENTS);

VOBJ_DESCRIPTION: 'v'? 'object_class->description';

VOBJ_BUILD: 'v'? 'object_class->build';
INFINITY: 'INFINITY';
OPERATION_FLAGS: 'operation_class->' ~[;]+ ';' -> skip;
FOR_LOOP: 'for(' ~[)]+ ')' -> skip;
LOAD_SKIP: 'load_class->' ~[;]+ ';' -> skip;
FOREIGN_SKIP: 'foreign_class->' ~[;]+ ';' -> skip;
SAVE_SKIP: 'save_class->' ~[;]+ ';' -> skip;
COLOR_SKIP: 'colour_class->' ~[;]+ ';' -> skip;
BANDARY_SKIP: 'bandary_class->' ~[;]+ ';' -> skip;
THUMBNAIL_SKIP: 'thumbnail_class->' ~[;]+ ';' -> skip;
SUMMARY_SKIP: 'object_class->summary_class' ~[;]+ ';' -> skip;
ACLASS_SKIP: 'aclass->' ~[;]+ ';' -> skip;
SCLASS_SKIP: 'sclass->' ~[;]+ ';' -> skip;
CLASS_SKIP: 'class->' ~[;]+ ';' -> skip;
HCLASS_SKIP: 'hclass->' ~[;]+ ';' -> skip;
CMS_SKIP: 'cmsSetLogErrorHandler(' ~[;]+ ';' -> skip;
ARITHMATIC_SKIP: 'vips_arithmetic_set_format_table(' ~[;]+ ';' -> skip;

INLINECOMMENT_SKIP: '//' ~[\n]+ -> skip;

DOUBLE_LITERAL: '-'? Digit+ '.' Digit+;
INTEGER_LITERAL: '-'? Digit+;
COMMA: ',';
EQUAL: '=';
PARANS_OPEN_UNDERSCORE: '_(';
PARANS_OPEN: '(';
PARANS_CLOSE: ')';
DBL_QUOTE: '"' ->pushMode(STR_LIT);
//STRING_LITERAL: '"' ~["]+ '"';
BIT_OR: '|';
SEMI_COLON: ';';
CURLY_END: '}' -> popMode;
WS: Whitespace -> skip;
// From include/vips/object.h

OP_MINUS: '-';

VIPS_ARG_INT: 'VIPS_ARG_INT';
VIPS_ARG_IMAGE: 'VIPS_ARG_IMAGE';
VIPS_ARG_OBJECT: 'VIPS_ARG_OBJECT';
VIPS_ARG_INTERPOLATE: 'VIPS_ARG_INTERPOLATE';
VIPS_ARG_BOOL: 'VIPS_ARG_BOOL';
VIPS_ARG_DOUBLE: 'VIPS_ARG_DOUBLE';
VIPS_ARG_BOXED: 'VIPS_ARG_BOXED';
VIPS_ARG_UINT64: 'VIPS_ARG_UINT64';
VIPS_ARG_ENUM: 'VIPS_ARG_ENUM';
VIPS_ARG_FLAGS: 'VIPS_ARG_FLAGS';
VIPS_ARG_STRING: 'VIPS_ARG_STRING';
VIPS_ARG_POINTER: 'VIPS_ARG_POINTER';

VIPS_ARGUMENT_REQUIRED_INPUT: 'VIPS_ARGUMENT_REQUIRED_INPUT';
VIPS_ARGUMENT_OPTIONAL_INPUT: 'VIPS_ARGUMENT_OPTIONAL_INPUT';
VIPS_ARGUMENT_REQUIRED_OUTPUT: 'VIPS_ARGUMENT_REQUIRED_OUTPUT';
VIPS_ARGUMENT_OPTIONAL_OUTPUT: 'VIPS_ARGUMENT_OPTIONAL_OUTPUT';

VIPS_ARGUMENT_NONE: 'VIPS_ARGUMENT_NONE';
VIPS_ARGUMENT_REQUIRED: 'VIPS_ARGUMENT_REQUIRED';
VIPS_ARGUMENT_CONSTRUCT: 'VIPS_ARGUMENT_CONSTRUCT';
VIPS_ARGUMENT_SET_ONCE: 'VIPS_ARGUMENT_SET_ONCE';
VIPS_ARGUMENT_SET_ALWAYS: 'VIPS_ARGUMENT_SET_ALWAYS';
VIPS_ARGUMENT_INPUT: 'VIPS_ARGUMENT_INPUT';
VIPS_ARGUMENT_OUTPUT: 'VIPS_ARGUMENT_OUTPUT';
VIPS_ARGUMENT_DEPRECATED: 'VIPS_ARGUMENT_DEPRECATED';
VIPS_ARGUMENT_MODIFY: 'VIPS_ARGUMENT_MODIFY';

G_STRUCT_OFFSET: 'G_STRUCT_OFFSET';
IDENTIFIER: IdentifierStart IdentifierRest*;

mode STR_LIT;
STRING: ~["]+;
DBL_QUOTE_END: '"' -> popMode;

mode COMMENTS;
END_COMMENT: '*/' -> skip, popMode;
COMMENT: .+? -> skip;