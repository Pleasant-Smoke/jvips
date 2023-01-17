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

import com.pss.jvips.plugin.context.Version;
import com.pss.jvips.plugin.service.VersionedDelegate;
import com.pss.jvips.plugin.util.History;

import java.util.List;
import java.util.SortedSet;

public class ParameterNameTokenizerDelegate extends VersionedDelegate<ParameterNameTokenizer> {

    public ParameterNameTokenizerDelegate(History<Version> context, SortedSet<ParameterNameTokenizer> services) {
        super(context, services);
    }

    public History<List<String>> tokenize(String value) {
        var latest = getTarget().tokenize(value);
        if (context.same()) {
            return new History<>(latest);
        } else {
            var previous = getPrevious().tokenize(value);
            return new History<>(latest, previous);
        }
    }
}
