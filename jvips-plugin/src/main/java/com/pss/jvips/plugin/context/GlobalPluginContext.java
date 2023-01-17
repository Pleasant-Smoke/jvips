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

import com.pss.jvips.plugin.constants.VipsConstant;
import com.pss.jvips.plugin.model.xml.executable.AbstractExecutable;
import com.pss.jvips.plugin.util.MethodOptionalParametersDocumentation;
import com.squareup.javapoet.ClassName;

import java.util.*;

public record GlobalPluginContext(OperationContext operationContext,
                                  List<String> functionNames,
                                  Map<String, String> constants,
                                  Map<String, List<VipsConstant>> _constants,
                                  Map<UUID, MethodOptionalParametersDocumentation> singularOptionalParams,
                                  Map<UUID, ClassName> dtoOptionalParameters)  {


    public GlobalPluginContext(OperationContext operationContext, List<String> functionNames){
        this(operationContext, functionNames, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());
    }
    public GlobalPluginContext(OperationContext operationContext){
        this(operationContext, new ArrayList<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());
    }

    public boolean isDTOOptionalParam(AbstractExecutable executable){
        return dtoOptionalParameters.containsKey(executable.getUuid());
    }

    public ClassName getDtoOptionalParam(AbstractExecutable executable){
        return dtoOptionalParameters.get(executable.getUuid());
    }

    public boolean isSingularOptionalParam(AbstractExecutable executable){
        return singularOptionalParams.containsKey(executable.getUuid());
    }

    public MethodOptionalParametersDocumentation getSingularOptionalParam(AbstractExecutable executable){
        return singularOptionalParams.get(executable.getUuid());
    }
}
