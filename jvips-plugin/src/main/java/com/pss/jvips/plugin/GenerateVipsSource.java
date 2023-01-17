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

import com.pss.jvips.plugin.actions.GenerateSourcesTask;
import com.pss.jvips.plugin.actions.GenerateVipsSourcesAction;
import com.pss.jvips.plugin.context.GlobalPluginContext;
import com.pss.jvips.plugin.util.SourceLoader;
import org.gradle.api.Action;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

public class GenerateVipsSource implements Plugin<Project> {

    private static final Logger log = LoggerFactory.getLogger(GenerateVipsSource.class);

    private static final Map<String, String> config = Map.of("group", "vips");




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

                throw new RuntimeException("Unable to locate vips.properties, vips.location", e);
            }
        }


        GenerateVipSourcesConfig vips = project.getExtensions().create("vips", GenerateVipSourcesConfig.class);
        project.getTasks().register("generateVipsSources", GenerateSourcesTask.class, task-> {
            var config = task.getConfig();
            config.getContext().set(vips.getContext());
            config.getTargetVersion().set(vips.getTargetVersion());
            config.getPreviousVersion().set(vips.getPreviousVersion());
        });

    }









}
