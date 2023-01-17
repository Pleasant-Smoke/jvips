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
import com.pss.jvips.plugin.model.xml.type.AbstractNativeType;
import com.pss.jvips.plugin.model.xml.type.NativeArrayType;
import com.pss.jvips.plugin.model.xml.type.NativeType;
import jakarta.xml.bind.annotation.*;
import org.checkerframework.checker.nullness.qual.Nullable;

@XmlAccessorType(XmlAccessType.NONE)
public class NativeField {

    @XmlTransient
    protected JavaCaseFormat javaName;

    @XmlAttribute
    protected String name;

    @Nullable
    @XmlAttribute
    protected Integer readable;

    @Nullable
    @XmlAttribute
    protected Integer writable;

    @Nullable
    @XmlAttribute(name="private")
    protected Integer _private;

    @Nullable
    @XmlElements({
            @XmlElement(name = "type", type = NativeType.class),
            @XmlElement(name = "array", type = NativeArrayType.class),
    })
    protected AbstractNativeType type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getReadable() {
        return readable;
    }

    public void setReadable(Integer readable) {
        this.readable = readable;
    }

    public Integer getWritable() {
        return writable;
    }

    public void setWritable(Integer writable) {
        this.writable = writable;
    }

    public Integer getPrivate() {
        return _private;
    }

    public void setPrivate(Integer _private) {
        this._private = _private;
    }

    public AbstractNativeType getType() {
        return type;
    }

    public void setType(AbstractNativeType type) {
        this.type = type;
    }

    public JavaCaseFormat getJavaName() {
        return javaName;
    }

    public void setJavaName(JavaCaseFormat javaName) {
        this.javaName = javaName;
    }


}
