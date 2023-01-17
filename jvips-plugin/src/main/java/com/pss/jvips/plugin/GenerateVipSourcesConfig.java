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

package com.pss.jvips.plugin;

import com.pss.jvips.plugin.context.OperationContext;
import com.pss.jvips.plugin.context.Version;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;

public abstract class GenerateVipSourcesConfig {

    @Input
    public abstract Property<String> getContext();

    @Input
    public abstract Property<String> getTargetVersion();

    @Input
    public abstract Property<String> getPreviousVersion();

    @Internal
    public Provider<OperationContext> getOperationContext(){
        return getContext().map(OperationContext::valueOf);
    }

    @Internal
    public Provider<Version> getOperationTargetVersion(){
        return getTargetVersion().map(Version::valueOf);
    }

    @Internal
    public Provider<Version> getOperationPreviousVersion(){
        return getPreviousVersion().map(Version::valueOf);
    }

}
