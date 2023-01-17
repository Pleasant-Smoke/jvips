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

package com.pss.jvips.plugin.actions;

import com.pss.jvips.plugin.GenerateVipSourcesConfig;
import com.pss.jvips.plugin.context.GlobalPluginContext;
import com.pss.jvips.plugin.context.OperationContext;
import com.pss.jvips.plugin.context.Version;
import com.pss.jvips.plugin.util.History;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Nested;
import org.gradle.api.tasks.TaskAction;
import se.jbee.inject.Env;
import se.jbee.inject.Injector;
import se.jbee.inject.bootstrap.Bootstrap;
import se.jbee.lang.Type;

import java.nio.file.Path;
import java.util.Objects;

public abstract class GenerateSourcesTask extends DefaultTask {

    @Nested
    public abstract GenerateVipSourcesConfig getConfig();

    @TaskAction
    protected void run(){
        OperationContext context = Objects.requireNonNull(getConfig().getOperationContext().get());
        Version target = Objects.requireNonNull(getConfig().getOperationTargetVersion().get());
        Version previous = Objects.requireNonNull(getConfig().getOperationPreviousVersion().get());
        run(context, target, previous, getProject().getBuildDir().toPath());
    }

    public static void run(OperationContext context, Version target, Version previous, Path output){
        Env env = Bootstrap.DEFAULT_ENV
                .with(OperationContext.class, context)
                .with(GlobalPluginContext.class, new GlobalPluginContext(context))
                .with(Type.raw(History.class).parameterized(Version.class), new History<>(target, previous))
                .with("output", Path.class, output.resolve("generated/src/main/java"));

        Injector injector = Bootstrap.injector(env, context.getConfig());
        CodeGenerator codeGenerator = injector.resolve(CodeGenerator.class);
        codeGenerator.generate();
    }

    @Override
    public String getGroup() {
        return "vips";
    }
}
