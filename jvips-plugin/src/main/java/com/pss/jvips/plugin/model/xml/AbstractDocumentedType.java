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

package com.pss.jvips.plugin.model.xml;

import com.pss.jvips.plugin.naming.JavaCaseFormat;
import com.pss.jvips.plugin.model.base.IdentifierDocumentedType;
import jakarta.xml.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@XmlAccessorType(XmlAccessType.NONE)
public abstract class AbstractDocumentedType implements IdentifierDocumentedType {

    @XmlTransient
    protected final UUID uuid = UUID.randomUUID();

    @XmlTransient
    protected JavaCaseFormat javaName;

    @XmlAttribute
    protected String name;

    @XmlAttribute(name = "c-identifier")
    protected String identifier;

    @XmlElement(name = "doc")
    protected Documentation documentation;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public Documentation getDocumentation() {
        return documentation;
    }

    @Override
    public void setDocumentation(Documentation documentation) {
        this.documentation = documentation;
    }

    public JavaCaseFormat getJavaName() {
        return javaName;
    }

    public void setJavaName(JavaCaseFormat javaName) {
        this.javaName = javaName;
    }

    public String javaName(){
        return getJavaName().getJavaName();
    }

    public String nativeName(){
        return getJavaName().getNativeName();
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractDocumentedType that = (AbstractDocumentedType) o;

        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return uuid != null ? uuid.hashCode() : 0;
    }
}
