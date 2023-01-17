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

import jakarta.xml.bind.annotation.XmlAttribute;

/**
 * Sometimes the file isn't correct in the gir file example:
 *
 * [language,xml]
 * ====
 *   <method name="heifsave" c:identifier="vips_heifsave" introspectable="0">
 *         <doc xml:space="preserve"
 *              filename="libvips/foreign/foreign.c" <1>
 *              line="2239">Optional arguments:
 * ====
 * <1> The correct file should be `libvips/foreign/heifsave.c`
 *
 */
public class RemapFile {

    @XmlAttribute(name = "contains")
    protected String contains;

    @XmlAttribute(name = "file")
    protected String file;

    public String getContains() {
        return contains;
    }

    public void setContains(String contains) {
        this.contains = contains;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
