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

package com.pss.jvips.plugin.overrides;

import com.pss.jvips.plugin.util.Utils;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "vips-overrides")
public class VipsOverrides {


    private static final VipsOverrides overrides;

    static {
        try{
            JAXBContext jaxbContext = JAXBContext.newInstance(VipsOverrides.class);
            overrides = (VipsOverrides) jaxbContext.createUnmarshaller()
                    .unmarshal(Utils.getResource("Overrides.xml"));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public static VipsOverrides getOverrides(){
        return overrides;
    }
    @XmlElement(name = "remap-optional-arg")
    @XmlElementWrapper(name = "remap-optional-args")
    protected List<RemapOptionalArg> remappedArgs = new ArrayList<>();


    @XmlElementWrapper(name = "manual-renames")
    @XmlElement(name = "manual-rename")
    protected List<ManualRename> renames = new ArrayList<>();


    /**
     * The GIR file for some reason is pointing some of the foreign functions to `foreign.c`
     */
    @XmlElementWrapper(name = "remap-files")
    @XmlElement(name = "remap-file")
    protected List<RemapFile> remappedFiles = new ArrayList<>();

    @XmlElementWrapper(name = "deprecations")
    @XmlElement(name = "deprecation")
    protected List<Deprecations> deprecations = new ArrayList<>();


    public List<RemapOptionalArg> getRemappedArgs() {
        return remappedArgs;
    }

    public void setRemappedArgs(List<RemapOptionalArg> remappedArgs) {
        this.remappedArgs = remappedArgs;
    }

    public List<Deprecations> getDeprecations() {
        return deprecations;
    }

    public VipsOverrides setDeprecations(List<Deprecations> deprecations) {
        this.deprecations = deprecations;
        return this;
    }

    public List<ManualRename> getRenames() {
        return renames;
    }

    public void setRenames(List<ManualRename> renames) {
        this.renames = renames;
    }

    public List<RemapFile> getRemappedFiles() {
        return remappedFiles;
    }

    public void setRemappedFiles(List<RemapFile> remappedFiles) {
        this.remappedFiles = remappedFiles;
    }


    public String remap(String name, String defaultFile){

            return remappedFiles.stream()
                    .filter(x -> name.contains(x.getContains()))
                    .map(x -> x.getFile())
                    .findFirst()
                    .orElse(defaultFile);

    }

    public String rename(String nativeName, String defaultResult){
        return renames.stream()
                .filter((ManualRename rename)-> rename.getFrom().equals(nativeName))
                .findFirst()
                .map((ManualRename rename)-> rename.getTo())
                .orElse(defaultResult);
    }
}
