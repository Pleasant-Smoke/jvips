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

package com.pss.jvips.plugin.actions;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimaps;
import com.pss.jvips.plugin.constants.VipsConstant;
import com.pss.jvips.plugin.generation.impl.ConstantGenerator;
import com.pss.jvips.plugin.model.xml.Namespace;
import com.pss.jvips.plugin.service.constants.ConstantsModel;
import com.pss.jvips.plugin.service.constants.ConstantsService;
import com.pss.jvips.plugin.service.executables.ExecutableFilterDelegate;
import com.pss.jvips.plugin.service.introspection.VersionedLoaderDelegate;
import com.pss.jvips.plugin.service.naming.method.MethodNameTokenizerDelegate;
import com.pss.jvips.plugin.util.History;
import com.squareup.javapoet.ClassName;

import java.util.List;
import java.util.Map;

public class CommonCodeGenerator implements CodeGenerator {

    protected final VersionedLoaderDelegate versionedLoader;
    protected final ExecutableFilterDelegate executableFilter;
    protected final MethodNameTokenizerDelegate methodNameTokenizer;
    //protected final EnumCodeGenerator enumCodeGenerator;
    protected final ConstantsService constantsService;
    protected final ConstantGenerator constantGenerator;

    public CommonCodeGenerator(VersionedLoaderDelegate versionedLoader,
                               ExecutableFilterDelegate executableFilter,
                               MethodNameTokenizerDelegate methodNameTokenizer,
                               // EnumCodeGenerator enumCodeGenerator,
                               ConstantsService constantsService, ConstantGenerator constantGenerator) {
        this.versionedLoader = versionedLoader;
        this.executableFilter = executableFilter;
        this.methodNameTokenizer = methodNameTokenizer;
        //this.enumCodeGenerator = enumCodeGenerator;
        this.constantsService = constantsService;
        this.constantGenerator = constantGenerator;
    }

    @Override
    public void generate() {
        History<Namespace> namespaceHistory = versionedLoader.loadGObjectIntrospection();
        constantsService.registerConstants(namespaceHistory);
        ConstantsModel constantsModel = constantsService.getConstantsModel();
        Map<ClassName, List<VipsConstant>> map = Multimaps.asMap(constantsModel.getConstantTypeMap());
        for (Map.Entry<ClassName, List<VipsConstant>> entry : map.entrySet()) {
            var arg = new ConstantGenerator.Constant(HashMultimap.create(), entry.getValue());
            constantGenerator.run(arg, entry.getKey());
        }

    }
}
