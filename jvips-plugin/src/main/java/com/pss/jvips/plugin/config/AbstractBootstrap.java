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

package com.pss.jvips.plugin.config;

import se.jbee.inject.Scope;
import se.jbee.inject.binder.BinderModule;
import se.jbee.inject.binder.BootstrapperBundle;
import se.jbee.inject.binder.Supply;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

public abstract class AbstractBootstrap extends BootstrapperBundle {

    @Override
    protected final void bootstrap() {
        install(InitialValues.OutputPath.class);
        install(InitialValues.TargetVersion.class);
        install(InitialValues.Context.class);
        install(SortedSetBinder.class);
        bootstrapInternal();
    }

    protected abstract void bootstrapInternal();

    public static class SortedSetBinder extends BinderModule {

        private static final Supply.ArrayBridge<SortedSet<?>> BRIDGE = elems -> new TreeSet<>(Arrays.asList(elems));

        @Override
        protected void declare() {
            per(Scope.dependency)
                    .starbind(SortedSet.class)
                    .toSupplier(BRIDGE);
        }
    }
}
