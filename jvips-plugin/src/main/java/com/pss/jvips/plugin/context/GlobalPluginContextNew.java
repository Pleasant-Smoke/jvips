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

package com.pss.jvips.plugin.context;

import com.pss.jvips.plugin.constants.VipsConstant;
import com.pss.jvips.plugin.model.xml.executable.AbstractExecutable;
import com.pss.jvips.plugin.util.MethodOptionalParametersDocumentation;
import com.squareup.javapoet.ClassName;
import org.semver4j.Semver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GlobalPluginContextNew  {

    OperationContext operationContext;
    List<String> functionNames;
    Map<String, String> constants;
    Map<String, List<VipsConstant>> _constants;
    Map<UUID, MethodOptionalParametersDocumentation> singularOptionalParams;
    Map<UUID, ClassName> dtoOptionalParameters;

    protected Version target;
    protected Version previous;

    protected Semver vipsPreviousVersion;
    protected Semver vipsCurrentVersion;
    protected Semver jvipsVersion;

    public boolean isDTOOptionalParam(AbstractExecutable executable){
        return dtoOptionalParameters.containsKey(executable.getUuid());
    }

    public ClassName getDtoOptionalParam(AbstractExecutable executable){
        return dtoOptionalParameters.get(executable.getUuid());
    }

    public boolean isSingularOptionalParam(AbstractExecutable executable){
        return singularOptionalParams.containsKey(executable.getUuid());
    }

    public MethodOptionalParametersDocumentation getSingularOptionalParam(AbstractExecutable executable){
        return singularOptionalParams.get(executable.getUuid());
    }

    public Semver getVipsPreviousVersion() {
        return vipsPreviousVersion;
    }

    public GlobalPluginContextNew setVipsPreviousVersion(Semver vipsPreviousVersion) {
        this.vipsPreviousVersion = vipsPreviousVersion;
        return this;
    }

    public Semver getVipsCurrentVersion() {
        return vipsCurrentVersion;
    }

    public GlobalPluginContextNew setVipsCurrentVersion(Semver vipsCurrentVersion) {
        this.vipsCurrentVersion = vipsCurrentVersion;
        return this;
    }

    public Semver getJvipsVersion() {
        return jvipsVersion;
    }

    public GlobalPluginContextNew setJvipsVersion(Semver jvipsVersion) {
        this.jvipsVersion = jvipsVersion;
        return this;
    }

    public boolean vipsVersionsSame(){
        return target == previous;
    }

    public Version getTarget() {
        return target;
    }

    public GlobalPluginContextNew setTarget(Version target) {
        this.target = target;
        return this;
    }

    public Version getPrevious() {
        return previous;
    }

    public GlobalPluginContextNew setPrevious(Version previous) {
        this.previous = previous;
        return this;
    }
}
