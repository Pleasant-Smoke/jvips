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

import com.pss.jvips.plugin.context.GlobalPluginContextNew;
import com.pss.jvips.plugin.context.Version;
import com.pss.jvips.plugin.model.xml.Namespace;
import com.pss.jvips.plugin.model.xml.executable.AbstractExecutable;
import com.pss.jvips.plugin.service.VersionedDelegate;
import com.pss.jvips.plugin.util.History;

import java.util.List;
import java.util.SortedSet;

public class ExecutableFilterDelegate extends VersionedDelegate<ExecutableFilter> {

    public ExecutableFilterDelegate(History<Version> context, SortedSet<ExecutableFilter> services) {
        super(context, services);
    }

    public History<List<AbstractExecutable>> filterExecutablesCurrent(History<Namespace> namespace, History<List<String>> whitelist){
        var target = getTarget().filterExecutablesHistorical(namespace.target(), whitelist.target());
        if(!context.same()){
            var previous = getPrevious().filterExecutablesHistorical(namespace.previous(), whitelist.previous());
            return new History<>(target, previous);
        }
        return new History<>(target);
    }


}
