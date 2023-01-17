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
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlList;

import java.util.ArrayList;
import java.util.List;

/**
 * Remaps Optional Argument Class because the functions do not share a common tokens, the tokens
 * are split by `_` but also certain keywords like `save` will be split
 *
 * For instance:
 *
 * `vips_jpegsave_buffer()` will split into 'vips', 'jpeg', 'save', and 'buffer'
 *
 * `vips_jpegsave_file()` will split into 'vips', 'jpeg', 'save', and 'file'
 *
 * Since both methods share the same optional arguments the list of tokens will be intersected
 * then the first method that was split, will go over each token to see if that token is contained in the intersecting
 * result
 *
 * .Example of Methods with no common tokens with the same optional parameters
 * [language,xml]
 * ====
 *         <remap-optional-arg to="VipsBandable">
 *             <identifier>vips_hist_equal</identifier>
 *             <identifier>vips_hist_find</identifier>
 *             <identifier>vips_maplut</identifier>
 *             <identifier>vips_msb</identifier>
 *         </remap-optional-arg>
 * ====
 *
 */
public class RemapOptionalArg {

    @XmlAttribute(name = "to")
    protected String to;

    @XmlList
    @XmlElement(name = "identifier")
    protected List<String> identifiers = new ArrayList<>();

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public List<String> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<String> identifiers) {
        this.identifiers = identifiers;
    }

}
