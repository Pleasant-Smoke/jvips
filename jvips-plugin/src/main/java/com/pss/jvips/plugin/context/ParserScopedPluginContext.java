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

package com.pss.jvips.plugin.context;

import com.pss.jvips.plugin.model.xml.executable.AbstractExecutable;
import com.pss.jvips.plugin.util.MethodOptionalParametersDocumentation;
import com.squareup.javapoet.ClassName;
import org.antlr.v4.runtime.BufferedTokenStream;

import java.util.Map;
import java.util.UUID;

public record ParserScopedPluginContext(ScopedPluginContext scopedContext, BufferedTokenStream tokenStream) {
    public GlobalPluginContext globalContext() {
        return scopedContext.globalContext();
    }

    public AbstractExecutable currentExecutable() {
        return scopedContext.currentExecutable();
    }

    public ClassName currentClass() {
        return scopedContext.currentClass();
    }

    public OperationContext operationContext() {
        return scopedContext.operationContext();
    }

    public Map<String, String> constants() {
        return scopedContext.constants();
    }

    public Map<UUID, MethodOptionalParametersDocumentation> singularOptionalParams() {
        return scopedContext.singularOptionalParams();
    }

    public Map<UUID, ClassName> dtoOptionalParameters() {
        return scopedContext.dtoOptionalParameters();
    }

    public boolean isDTOOptionalParam(AbstractExecutable executable) {
        return scopedContext.isDTOOptionalParam(executable);
    }

    public ClassName getDtoOptionalParam(AbstractExecutable executable) {
        return scopedContext.getDtoOptionalParam(executable);
    }

    public boolean isSingularOptionalParam(AbstractExecutable executable) {
        return scopedContext.isSingularOptionalParam(executable);
    }

    public MethodOptionalParametersDocumentation getSingularOptionalParam(AbstractExecutable executable) {
        return scopedContext.getSingularOptionalParam(executable);
    }
}
