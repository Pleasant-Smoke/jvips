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

import com.pss.jvips.plugin.actions.GenerateVipsSourcesAction;
import com.pss.jvips.plugin.actions.VipsErrorMessagesAction;
import com.pss.jvips.plugin.util.SourceLoader;
import com.pss.jvips.plugin.context.GlobalPluginContext;
import com.pss.jvips.plugin.context.OperationContext;
import org.apache.commons.io.IOUtils;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Deprecated
public abstract class AbstractGenerateVipsSource implements Plugin<Project> {

    private static final Logger log = LoggerFactory.getLogger(AbstractGenerateVipsSource.class);

    private static final Map<String, String> config = Map.of("group", "vips");

    protected abstract OperationContext context();

    @Override
    public void apply(Project project) {

        File file = project.file("vips.properties");
        if(file.isFile()){
            Properties properties = new Properties();
            try {
                properties.load(file.toURI().toURL().openStream());
                SourceLoader.setSrcDir(Paths.get(properties.getProperty("vips.location")
                        .replace("~", System.getProperty("user.home"))).toAbsolutePath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        List<String> functionNames = List.of();
        try {
            functionNames = List.of(IOUtils.resourceToString("function-list.txt", StandardCharsets.UTF_8, this.getClass().getClassLoader()).split(","));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        NamedDomainObjectContainer<ErrorMessages> container = project.container(ErrorMessages.class);
        project.getExtensions().add("messages", container);
        project.task(config, "generateVipsSources").doLast(new GenerateVipsSourcesAction(new GlobalPluginContext(context(), functionNames)));

        project.task(config, "generateErrorMessages").doLast(new VipsErrorMessagesAction(container));
    }









}
