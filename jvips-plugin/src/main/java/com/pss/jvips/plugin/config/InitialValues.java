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

import com.pss.jvips.plugin.context.OperationContext;
import com.pss.jvips.plugin.context.Version;
import com.pss.jvips.plugin.util.History;
import se.jbee.inject.Env;
import se.jbee.inject.Name;
import se.jbee.inject.bind.Bindings;
import se.jbee.inject.bind.ModuleWith;
import se.jbee.inject.binder.BinderModuleWith;
import se.jbee.lang.Type;
import se.jbee.lang.TypeVariable;

import java.lang.reflect.ParameterizedType;
import java.nio.file.Path;

import static se.jbee.lang.Type.raw;

public abstract class InitialValues<T> extends BinderModuleWith<T> {


    protected final Name name;

    protected InitialValues() {
        this.name = Name.named(name());
    }

    protected abstract String name();

    @Override
    public void declare(Bindings bindings, Env env) {
        Type<?> valueType = raw(getClass()).toSuperType(ModuleWith.class).parameter(0);
        @SuppressWarnings("unchecked")
        final T value = valueType.rawType == Env.class
                ? (T) env
                : (T) env.property(name, valueType,
                getClass());
        declare(bindings, env, value);
    }

    public static class OutputPath extends InitialValues<Path> {

        @Override
        protected void declare(Path property) {
            bind(name, Path.class).to(property);
        }

        @Override
        protected String name() {
            return "output";
        }
    }

    public static class TargetVersion extends BinderModuleWith<History<Version>> {

        @Override
        protected void declare(History<Version> property) {
            bind(Type.raw(History.class).parameterized(Version.class)).to(property);
        }
    }


    public static class Context extends BinderModuleWith<OperationContext> {

        @Override
        protected void declare(OperationContext property) {
            bind(OperationContext.class).to(property);
        }

    }

}
