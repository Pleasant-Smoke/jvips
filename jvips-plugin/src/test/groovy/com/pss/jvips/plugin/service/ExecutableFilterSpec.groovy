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

package com.pss.jvips.plugin.service

import com.pss.jvips.plugin.context.GlobalPluginContext
import com.pss.jvips.plugin.context.OperationContext
import com.pss.jvips.plugin.context.Version
import com.pss.jvips.plugin.service.executables.ExecutableFilterImpl

import com.pss.jvips.plugin.service.introspection.VersionedLoaderImpl
import spock.lang.Specification

class ExecutableFilterSpec extends Specification {

    //@todo: fix missing `cast_shortcast_uint` and `fitsload_source`
    def "Make sure all methods are loaded"(){
        when:
        def loader = new VersionedLoaderImpl()
        def names = loader.loadFunctionWhitelist(Version.LATEST)
        def mutable = new ArrayList<String>()
        mutable.addAll(names)
        def namespace = loader.loadGObjectIntrospection(Version.LATEST)
        def context = new GlobalPluginContext(OperationContext.COMMON, names)
        def filter = new ExecutableFilterImpl()
        def result = filter.filterExecutables(namespace).name
        mutable.removeAll(result)
        mutable.remove("cast_shortcast_uint") // These 2 are not showing in the gir
        mutable.remove("fitsload_source")
        then:
        mutable.isEmpty()
    }
}
