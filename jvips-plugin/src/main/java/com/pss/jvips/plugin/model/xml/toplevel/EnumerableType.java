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

import com.pss.jvips.plugin.naming.JavaNaming;
import com.pss.jvips.plugin.naming.JavaTypeMapping;
import com.pss.jvips.plugin.naming.Packages;
import com.pss.jvips.plugin.naming.TypeRegistration;
import com.pss.jvips.plugin.model.xml.AbstractDocumentedType;
import com.pss.jvips.plugin.model.xml.Member;
import com.squareup.javapoet.ClassName;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.*;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.NONE)
public abstract class EnumerableType extends AbstractDocumentedType implements TopLevelType {

    @XmlTransient
    protected ClassName className;

    @XmlAttribute(name = "c-type")
    protected String ctype;

    // Only occurs on method enums bitfields and class
    @Nullable
    @XmlAttribute(name = "glib-get-property")
    protected String getProperty;

    @XmlElement(name = "member")
    protected List<Member> members = new ArrayList<>();

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public ClassName getClassName() {
        return className;
    }

    public void setClassName(ClassName className) {
        this.className = className;
    }



}
