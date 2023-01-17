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

package com.pss.jvips.plugin.generation.impl.dto;

import com.pss.jvips.plugin.antlr.csource.VisitedCodeBlock;
import com.pss.jvips.plugin.args.OptionalArgUtil;
import com.pss.jvips.plugin.generation.CodeGeneratorOld;
import com.pss.jvips.plugin.generation.impl.dto.factory.DTOTuple;
import com.pss.jvips.plugin.model.xml.types.Direction;
import com.pss.jvips.plugin.naming.JavaTypeMapping;
import com.pss.jvips.plugin.naming.Packages;
import com.pss.jvips.plugin.util.MethodOptionalParametersDocumentation;
import com.pss.jvips.plugin.context.GlobalPluginContext;
import com.pss.jvips.plugin.context.OperationContext;

import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static com.pss.jvips.plugin.naming.JavaTypeMapping.*;

/**
 * Out Params use `long $bitset` field to set/get
 */
public abstract class AbstractArgumentDTO extends CodeGeneratorOld<AbstractArgumentDTO.Args, Map<String, DTOTuple>> {




    public AbstractArgumentDTO(Path task, GlobalPluginContext context, OperationContext operationContext) {
        super(task, context, operationContext);
    }


    protected abstract Modifier[] modifiers();

    protected String generateName(Args args){
        List<String> listOfMethodNames = args.earlyStageResults.stream()
                .map(v -> v.getExecutable().nativeName())
                .collect(Collectors.toList());

        return OptionalArgUtil.getOptionalArgumentName(listOfMethodNames);
    }


    @Override
    protected Result<Map<String, DTOTuple>> runInternal(TypeSpec.Builder typeSpec, Args arguments) {
        arguments.earlyStageResults.forEach(x-> {

                arguments.classNames.put(x.getExecutable().javaName(), new DTOTuple(arguments.parentClassName, arguments.className));


        });

        buildDto(typeSpec, arguments);
        if(context.operationContext() == OperationContext.COMMON){
            arguments.earlyStageResults.forEach(x-> context.dtoOptionalParameters().put(x.getExecutable().getUuid(), arguments.className));
        } else {
            arguments.earlyStageResults.forEach(x-> context.dtoOptionalParameters().put(x.getExecutable().getUuid(), arguments.parentClassName));
        }

        return new Result<>(arguments.classNames, arguments.className);
    }

    protected abstract void buildDto(TypeSpec.Builder typeSpec, Args arguments);

    protected abstract String getPackage();

    protected String prefixName(String name){
        return context.operationContext().getPrefix() + name;
    }

    protected abstract TypeSpec.Builder addSuperClass(TypeSpec.Builder typeSpec, Args args);

    @Override
    protected TypeSpec.Builder getTypeSpec(Args arguments) {
        arguments.name = generateName(arguments);
        arguments.prefixedName = prefixName(arguments.name);
        arguments.className = JavaTypeMapping.getClassName(arguments.prefixedName, getPackage());
        arguments.parentClassName = JavaTypeMapping.getClassName(arguments.name, Packages.Common.Generated.arguments);
        //arguments.dtoTypeName = new ArgumentDTOTypeName(arguments.className);
        MethodOptionalParametersDocumentation visitor = arguments.earlyStageResults.get(0);
        Optional<VisitedCodeBlock> bl =  Optional.ofNullable(visitor.getSourceCodeVisited());

        arguments.hasOut = bl.map(x-> x.arguments().values().stream().anyMatch(y -> visitor.getParameterNames().contains(y.getName()) && y.getDirection() == Direction.OUT)).orElse(false);
        arguments.hasImage = bl.map(x-> x.arguments().values().stream().anyMatch(y -> visitor.getParameterNames().contains(y.getName()) && y.isImage())).orElse(false);
        return addSuperClass(TypeSpec.classBuilder(arguments.className).addModifiers(modifiers()), arguments);
    }



    @Override
    protected void customize(JavaFile.Builder customize) {

        customize.addStaticImport(OptionalParameter$Direction_class, "OUT");
    }

    public static class Args {

        protected boolean hasImage;
        protected boolean hasOut;
        protected String name;
        protected String prefixedName;
        protected ClassName className;
        protected ClassName parentClassName;
       // protected ArgumentDTOTypeName dtoTypeName;
        final Map<UUID, ClassName> methodToDtoName = new HashMap<>();
        private final Map<String, DTOTuple> classNames;
        private final List<MethodOptionalParametersDocumentation> earlyStageResults;


        public Args(Map<String, DTOTuple> classNames, List<MethodOptionalParametersDocumentation> earlyStageResults) {
            this.classNames = classNames;
            this.earlyStageResults = earlyStageResults;

        }

        public Map<String, DTOTuple> getClassNames() {
            return classNames;
        }

        public List<MethodOptionalParametersDocumentation> getEarlyStageResults() {
            return earlyStageResults;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ClassName getClassName() {
            return className;
        }

        public void setClassName(ClassName className) {
            this.className = className;
        }
    }
}
