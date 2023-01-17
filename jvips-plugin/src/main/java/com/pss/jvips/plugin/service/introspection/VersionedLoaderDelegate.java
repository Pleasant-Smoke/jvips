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

package com.pss.jvips.plugin.service.introspection;

import com.pss.jvips.plugin.context.GlobalPluginContextNew;
import com.pss.jvips.plugin.context.Version;
import com.pss.jvips.plugin.model.xml.Namespace;
import com.pss.jvips.plugin.overrides.VipsOverrides;
import com.pss.jvips.plugin.service.VersionedDelegate;
import com.pss.jvips.plugin.util.History;

import java.util.List;
import java.util.SortedSet;

public class VersionedLoaderDelegate extends VersionedDelegate<VersionedLoader> {

    protected History<Namespace> namespace;
    protected History<List<String>> whitelist;
    protected History<VipsOverrides> overrides;

    public VersionedLoaderDelegate(History<Version> context, SortedSet<VersionedLoader> services) {
        super(context, services);
    }


    public History<Namespace> loadGObjectIntrospection() {
        if(namespace == null) {
            Namespace latest = getTarget().loadGObjectIntrospection(context.target());
            if (context.same()) {
                namespace = new History<>(latest);
            } else {
                Namespace previous = getPrevious().loadGObjectIntrospection(context.previous());
                namespace = new History<>(latest, previous);
            }
        }
        return namespace;
    }

    public History<List<String>> loadFunctionWhitelist() {
        if(whitelist == null){
            var latest = getTarget().loadFunctionWhitelist(context.target());
            if(context.same()){
                whitelist = new History<>(latest);
            } else {
                var previous = getPrevious().loadFunctionWhitelist(context.previous());
                whitelist = new History<>(latest, previous);
            }
        }
        return whitelist;
    }

    public History<VipsOverrides> loadOverrides() {
        if(overrides == null) {
            var latest = getTarget().loadOverrides(context.target());
            if (context.same()) {
                overrides = new History<>(latest);
            } else {
                var previous = getPrevious().loadOverrides(context.previous());
                overrides =  new History<>(latest, previous);
            }
        }
        return overrides;
    }
}
