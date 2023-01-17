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

import com.pss.jvips.plugin.model.xml.executable.AbstractExecutable;
import com.pss.jvips.plugin.naming.JavaCaseFormat;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;

import java.util.Objects;
import java.util.function.Supplier;


public class EarlyStageOptionalParameter implements Comparable<EarlyStageOptionalParameter> {

    private int order;
    private AbstractExecutable executable;
    private JavaCaseFormat name;
    private CodeBlock javaDoc;
    private Supplier<TypeName> type;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public AbstractExecutable getExecutable() {
        return executable;
    }

    public void setExecutable(AbstractExecutable executable) {
        this.executable = executable;
    }

    public JavaCaseFormat getName() {
        return name;
    }

    public void setName(JavaCaseFormat name) {
        this.name = name;
    }

    public CodeBlock getDocumentation() {
        return javaDoc;
    }

    public void setJavaDoc(CodeBlock javaDoc) {
        this.javaDoc = javaDoc;
    }

    public Supplier<TypeName> getType() {
        return type;
    }

    public void setType(Supplier<TypeName> type) {
        this.type = type;
    }

    public final String javaName(){
        return getName().getJavaName();
    }

    public final String nativeName(){
        return getName().getNativeName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EarlyStageOptionalParameter parameter = (EarlyStageOptionalParameter) o;

        return Objects.equals(getName(), parameter.getName());
    }

    @Override
    public int hashCode() {
        return getName() != null ? getName().hashCode() : 0;
    }

    @Override
    public int compareTo(EarlyStageOptionalParameter o) {
        return Integer.compare(getOrder(), o.getOrder());
    }

}
