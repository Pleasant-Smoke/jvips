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

package com.pss.jvips.plugin.service.executables;

import com.pss.jvips.plugin.model.xml.Namespace;
import com.pss.jvips.plugin.model.xml.executable.AbstractExecutable;

import java.util.List;
import java.util.stream.Collectors;

public class ExecutableFilterImpl implements ExecutableFilter {


    @Override
    public List<AbstractExecutable> filterExecutablesHistorical(Namespace namespace, List<String> whitelist){

        List<AbstractExecutable> collect = namespace.getClazzes().stream().filter(clazz -> "Image".equals(clazz.getName()))
                .flatMap(x -> x.getExecutables().stream())
                .filter(x -> whitelist.contains(x.getName()))
                .toList();

        List<AbstractExecutable> collect1 = namespace.getFunctions()
                .stream()
                .filter(x -> whitelist.contains(x.getName()))
                .collect(Collectors.toList());

        collect1.addAll(collect);
        return collect1;
    }

}
