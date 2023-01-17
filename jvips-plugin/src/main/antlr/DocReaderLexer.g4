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
lexer grammar DocReaderLexer;

@lexer::header {package com.pss.jvips.plugin.antlr;
}

@lexer::members{
   // public static final int WHITESPACE = 1;

    protected Token previousToken;

    	public Token nextToken() {
    	    this.previousToken = _token;
    	    return super.nextToken();
    	}

}
channels {WHITESPACE}
fragment NonDigit: [a-zA-Z_];
fragment Digit: [0-9];


fragment NewlineFrag: ('\r' '\n'? | '\n');
fragment EqualParam: Identifier+ '=' Identifier+;

// These should have been screaming snake case whoops

// There are some identifiers using `@output`
Output: 'output'{ if(this.previousToken != null && this.previousToken.getType() == At) {
    emit(new CommonToken(Identifier, "output"));
} else {
    emit(new CommonToken(Output, "output"));
}};
String: 'string';
UseThis: 'use this';
//DefaultsTo: 'defaults to';
//DefaultIs: 'default is';
DecimalNumber: Digit+ '.' Digit+;
IntegerNumber: Digit+;

//RESERVED: ('g'? 'u'? ('int'|'double'|'float'|'char') ([0-9][0-9])?) | 'NULL' | 'TRUE' | 'FALSE';
Identifier: NonDigit (NonDigit | Digit)*;
Paragraph: NewlineFrag NewlineFrag;
At: '@';
Hash: '#';
Percent: '%';
Star: '*';
SingleQuote: '\'';
ParansOpen: '(';
OtherDash:  '\u2010' .. '\u2014'; // Differnt hyphen types 2010 = '‐' // 2014 = '—'
ParansClose: ')';
BracketOpen: '[';
BracketClose: ']';
SemiColon: ';';
Equal: '=';
TripleDot: '...';
Dot: '.';
Dollar: '$';
Colon: ':';
DoubleQuote: '"';
CurlyOpen: '{';
CurlyClose: '}';
Comma: ',';
Dash: '-';
BackTick: '`';
CodeOpen: '|[' -> pushMode(C_CODE);


QuotedIdentifier: '"' Identifier '"';
//CommandLineParam: '--' Identifier+('-' Identifier+)*;
FileQueryParam: '[' EqualParam (',' (EqualParam|TripleDot) )*']';



OptionalArguments: 'Optional arguments:';
SeeAlso: See ' also' Colon?;
See: [Ss]'ee' ;



Whitespace: [ \t]  -> channel(WHITESPACE);

Newline : NewlineFrag;

EnvVar: '`' Identifier '`';
HtmlTag: '&' Identifier ';' Identifier | '&' '/' Identifier ';' Identifier| '&' Identifier ';' ;
Everything: ([=+a-zA-Z0-9.,<>\\/!^?|])+?;

mode C_CODE;
CodeClose: ']|' -> popMode;
All: ~[\]|]+;
EndBracket: ']';
Or: '|';