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

package com.pss.jvips.plugin.service.naming.method;

import com.pss.jvips.plugin.naming.ImmutableJavaCaseFormat;
import com.pss.jvips.plugin.naming.ImmutableTokenizedJavaCaseFormat;
import com.pss.jvips.plugin.naming.JavaCaseFormat;
import com.pss.jvips.plugin.naming.TokenizedJavaCaseFormat;
import com.pss.jvips.plugin.service.VersionedService;
import com.pss.jvips.plugin.util.History;
import com.pss.jvips.plugin.util.Utils;

import javax.lang.model.SourceVersion;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MethodAndParameterNamingServiceImpl implements MethodAndParameterNamingService {

    protected final Map<String, History<JavaCaseFormat>> parameterNames = new HashMap<>();
    protected final Map<String, History<TokenizedJavaCaseFormat>> methodNames = new HashMap<>();

    protected final MethodNameTokenizerDelegate methodNameTokenizer;
    protected final ParameterNameTokenizerDelegate parameterNameTokenizer;

    public MethodAndParameterNamingServiceImpl(MethodNameTokenizerDelegate methodNameTokenizer,
                                               ParameterNameTokenizerDelegate parameterNameTokenizer) {
        this.methodNameTokenizer = methodNameTokenizer;
        this.parameterNameTokenizer = parameterNameTokenizer;
    }

    @Override
    public History<TokenizedJavaCaseFormat> getTokenizedMethodName(String name){
        History<List<String>> tokenize = methodNameTokenizer.tokenize(name);
        TokenizedJavaCaseFormat javaName = getJavaName(name, tokenize.target());
        History<TokenizedJavaCaseFormat> value;
        if(!tokenize.same()){
            TokenizedJavaCaseFormat previous = getJavaName(name, tokenize.previous());
            value =  new History<>(javaName, previous);
        } else {
            value = new History<>(javaName);
        }
        methodNames.put(name, value);
        return value;
    }


    @Override
    public History<JavaCaseFormat> getParameterName(String name){
        History<List<String>> tokenize = parameterNameTokenizer.tokenize(name);
        JavaCaseFormat javaName = getJavaParameterName(name, tokenize.target());
        History<JavaCaseFormat> value;
        if(!tokenize.same()){
            JavaCaseFormat previous = getJavaParameterName(name, tokenize.previous());
            value =  new History<>(javaName, previous);
        } else {
            value = new History<>(javaName);
        }
        parameterNames.put(name, value);
        return value;
    }

    @Override
    public Optional<History<TokenizedJavaCaseFormat>> findMethod(String name){
        return Optional.ofNullable(methodNames.get(name));
    }

    @Override
    public Optional<History<JavaCaseFormat>> findParameter(String name){
        return Optional.ofNullable(parameterNames.get(name));
    }

    private static TokenizedJavaCaseFormat getJavaName(String name, List<String> tokens) {
        String result = Utils.caseTokens(tokens, false);
        String finalName = Utils.prefixNameIfJavaIdentifier("vips", result);
        return ImmutableTokenizedJavaCaseFormat.builder()
                .javaName(finalName)
                .nativeName(name)
                .tokens(tokens)
                .build();
    }

    private static JavaCaseFormat getJavaParameterName(String name, List<String> tokens) {
        String result = Utils.caseTokens(tokens, false);
        String finalName = Utils.postfixNameIfJavaIdentifier("Operation", result); // so far the only thing we have seen is boolean
        return ImmutableJavaCaseFormat.builder()
                .javaName(finalName)
                .nativeName(name)
                .build();
    }
}
