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

package com.pss.jvips.plugin.service;

import com.google.common.base.Preconditions;
import com.pss.jvips.plugin.context.GlobalPluginContextNew;
import com.pss.jvips.plugin.context.Version;
import com.pss.jvips.plugin.util.History;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.SortedSet;

public abstract class VersionedDelegate<T extends VersionedService> {

    private static final Logger log = LoggerFactory.getLogger(VersionedDelegate.class);

    protected final History<Version> context;
    protected final SortedSet<T> services;

    protected VersionedDelegate(History<Version> context, SortedSet<T> services) {
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(services));
        this.context = context;
        this.services = services;
    }

    protected T getTarget(){
        Version target = context.target();
        for (T service : services) {
            if(target == service.max() || (service.min().ordinal() <= target.ordinal() && target.ordinal() >= service.max().ordinal())){
                return service;
            }
        }
        throw new RuntimeException("No implementation found");
    }

    protected T getPrevious(){
        Version target = context.previous();
        for (T service : services) {
            if(target == service.max() || (service.min().ordinal() <= target.ordinal() && target.ordinal() >= service.max().ordinal())){
                return service;
            }
        }
        throw new RuntimeException("No implementation found");
    }


    /**
     * Short cut for services where their really shouldn't be many modifications
     * @param <T>
     */
    public static class Singular<T extends VersionedService> extends VersionedDelegate<T> {

        private final T singular;

        public Singular(History<Version> context, SortedSet<T> services) {
            super(context, services);
            this.singular = services.first();
        }

        @Override
        protected T getTarget() {
            return singular;
        }

        @Override
        protected T getPrevious() {
            return singular;
        }
    }
}
