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

package com.pss.jvips.plugin.model.xml.toplevel;

import com.pss.jvips.plugin.model.xml.Documentation;
import com.pss.jvips.plugin.model.xml.SourcePosition;
import com.pss.jvips.plugin.model.xml.type.NativeType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

import java.util.Optional;


public class Constant implements TopLevelType {

    @XmlAttribute
    protected String name;

    @XmlAttribute
    protected String value;

    @XmlAttribute(name = "c-type")
    protected String identifier;

    @XmlElement(name = "source-position")
    protected SourcePosition sourcePosition;

    @XmlElement(name = "type")
    protected NativeType type;

    @XmlElement(name = "doc")
    protected Documentation documentation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public SourcePosition getSourcePosition() {
        return sourcePosition;
    }

    public void setSourcePosition(SourcePosition sourcePosition) {
        this.sourcePosition = sourcePosition;
    }

    public NativeType getType() {
        return type;
    }

    public Constant setType(NativeType type) {
        this.type = type;
        return this;
    }

    public Optional<String> documentation(){
        if(documentation != null){
            return Optional.of(documentation.getDocumentation());
        }
        return Optional.empty();
    }

    public Documentation getDocumentation() {
        return documentation;
    }

    public Constant setDocumentation(Documentation documentation) {
        this.documentation = documentation;
        return this;
    }
}
