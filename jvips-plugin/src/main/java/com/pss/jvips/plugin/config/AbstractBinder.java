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

import com.pss.jvips.plugin.service.constants.ConstantsServiceImpl;
import com.pss.jvips.plugin.service.deprecation.ConstantsDeprecation;
import com.pss.jvips.plugin.service.deprecation.ConstantsDeprecationImpl;
import com.pss.jvips.plugin.service.executables.ExecutableFilter;
import com.pss.jvips.plugin.service.executables.ExecutableFilterDelegate;
import com.pss.jvips.plugin.service.executables.ExecutableFilterImpl;
import com.pss.jvips.plugin.service.introspection.VersionedLoader;
import com.pss.jvips.plugin.service.introspection.VersionedLoaderDelegate;
import com.pss.jvips.plugin.service.introspection.VersionedLoaderImpl;
import com.pss.jvips.plugin.service.naming.constants.*;
import com.pss.jvips.plugin.service.naming.method.*;
import com.pss.jvips.plugin.service.types.TypeMappingService;
import se.jbee.inject.Name;
import se.jbee.inject.Scope;
import se.jbee.inject.binder.BinderModule;
import se.jbee.inject.binder.Supply;
import se.jbee.inject.defaults.DefaultFeature;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

public abstract class AbstractBinder extends BinderModule {

    @Override
    protected void declare() {
        multibind(VersionedLoader.class).to(VersionedLoaderImpl.class);
        bind(VersionedLoaderDelegate.class).to(VersionedLoaderDelegate.class);

        multibind(ExecutableFilter.class).to(ExecutableFilterImpl.class);
        bind(ExecutableFilterDelegate.class).to(ExecutableFilterDelegate.class);

        multibind(MethodNameTokenizer.class).to(MethodNameTokenizerIml.class);
        bind(MethodNameTokenizerDelegate.class).to(MethodNameTokenizerDelegate.class);

        bind(ConstantsServiceImpl.class).to(ConstantsServiceImpl.class);
        bind(TypeMappingService.class).to(TypeMappingService.class);
        bind(ConstantsDeprecation.class).to(ConstantsDeprecationImpl.class);

        bind(MethodAndParameterNamingService.class).to(MethodAndParameterNamingServiceImpl.class);

        multibind(ConstantsClassHandler.class).to(ConstantsClassHandlerImpl.class);
        bind(ConstantsClassHandlerDelegate.class).to(ConstantsClassHandlerDelegate.class);

        multibind(ConstantsRegistration.class).to(ConstantsRegistrationImpl.class);
        bind(ConstantsRegistrationDelegate.class).to(ConstantsRegistrationDelegate.class);


        declareInternal();
    }


    protected void bindPath(Class clazz){
        bind(clazz).toConstructor();
        injectingInto(clazz).bind(Path.class).to(Name.named("output"), Path.class);
    }


    protected abstract void declareInternal();

}
