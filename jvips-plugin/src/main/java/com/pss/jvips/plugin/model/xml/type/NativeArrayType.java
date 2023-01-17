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

package com.pss.jvips.plugin.model.xml.type;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name ="array")
public class NativeArrayType extends AbstractNativeType {

    @XmlAttribute
    protected Integer length;

    @XmlAttribute(name = "zero-terminated")
    protected Integer zeroTerminated;

    @XmlElement(name = "type")
    protected NativeType componentType;

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getZeroTerminated() {
        return zeroTerminated;
    }

    public void setZeroTerminated(Integer zeroTerminated) {
        this.zeroTerminated = zeroTerminated;
    }

    public NativeType getComponentType() {
        return componentType;
    }

    public void setComponentType(NativeType componentType) {
        this.componentType = componentType;
    }

    @Override
    public String toString() {
        return "NativeArrayType{" +
                "length=" + length +
                ", zeroTerminated=" + zeroTerminated +
                ", type=" + componentType +
                ", cType='" + type + '\'' +
                '}';
    }
}
