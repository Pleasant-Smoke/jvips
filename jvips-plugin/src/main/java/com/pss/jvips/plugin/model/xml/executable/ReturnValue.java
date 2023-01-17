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

package com.pss.jvips.plugin.model.xml.executable;

import com.pss.jvips.plugin.model.xml.Documentation;
import com.pss.jvips.plugin.model.base.Typeable;
import com.pss.jvips.plugin.model.xml.type.NativeType;
import jakarta.xml.bind.annotation.XmlElement;

public class ReturnValue implements Typeable {

    @XmlElement(name = "doc")
    protected Documentation documentation;

    @XmlElement
    protected NativeType type;


    public Documentation getDocumentation() {
        return documentation;
    }


    public void setDocumentation(Documentation documentation) {
        this.documentation = documentation;
    }

    public NativeType getType() {
        return type;
    }

    public void setType(NativeType type) {
        this.type = type;
    }
}
