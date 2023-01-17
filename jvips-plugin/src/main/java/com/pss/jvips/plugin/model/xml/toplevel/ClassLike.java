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

import com.pss.jvips.plugin.model.xml.NativeField;
import com.pss.jvips.plugin.model.xml.AbstractDocumentedType;
import com.pss.jvips.plugin.model.xml.executable.AbstractExecutable;
import com.pss.jvips.plugin.model.xml.executable.Constructor;
import com.pss.jvips.plugin.model.xml.executable.Method;
import com.pss.jvips.plugin.model.xml.executable.VirtualMethod;
import com.squareup.javapoet.ClassName;
import jakarta.xml.bind.annotation.*;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@XmlAccessorType(XmlAccessType.NONE)
public abstract class ClassLike extends AbstractDocumentedType implements TopLevelType {

    public static final List<String> SUPER_CLASS_FIELD = List.of("parent_object", "parent_class");

    @XmlTransient
    protected transient ClassName className;

    @XmlTransient
    protected ClassName factoryClassName;

    @XmlAttribute(name = "parent")
    protected String parent;

    @XmlAttribute(name = "c-type")
    protected String cname;

    @XmlElement(name = "constructor")
    protected List<Constructor> constructors = new ArrayList<>();

    @XmlElements({
            @XmlElement(name = "method", type = Method.class),
            @XmlElement(name = "function", type = Function.class),
            @XmlElement(name = "virtual-method", type = VirtualMethod.class)
    })
    protected List<AbstractExecutable> executables = new ArrayList<>();

    @XmlTransient
    //@XmlElement(name = "method")
    protected List<Method> methods = new ArrayList<>();

    @XmlTransient
    //@XmlElement(name = "function")
    protected List<Function> functions = new ArrayList<>();

    @XmlTransient
    protected List<VirtualMethod> virtualMethods = new ArrayList<>();


    // Only occurs on method enums bitfields and class
    @Nullable
    @XmlAttribute(name = "glib-get-property")
    protected String getProperty;

    @XmlElement(name = "field")
    protected List<NativeField> fields = new ArrayList<>();

    @XmlElement(name = "property")
    protected List<NativeField> properties = new ArrayList<>();

    public List<NativeField> getFields() {
        return fields;
    }

    public void setFields(List<NativeField> fields) {
        this.fields = fields;
    }


    public List<AbstractExecutable> getExecutables() {
        return executables;
    }

    public List<VirtualMethod> getVirtualMethods() {
        return virtualMethods;
    }

    public void setVirtualMethods(List<VirtualMethod> virtualMethods) {
        this.virtualMethods = virtualMethods;
    }

    public String getGetProperty() {
        return getProperty;
    }

    public void setGetProperty(String getProperty) {
        this.getProperty = getProperty;
    }

    public void setExecutables(List<AbstractExecutable> executables) {
        this.executables = executables;
    }

    public List<Function> getFunctions() {
        return functions;
    }

    public void setFunctions(List<Function> functions) {
        this.functions = functions;
    }

    public List<Constructor> getConstructors() {
        return constructors;
    }

    public void setConstructors(List<Constructor> constructors) {
        this.constructors = constructors;
    }

    public List<Method> getMethods() {
        return methods;
    }

    public void setMethods(List<Method> methods) {
        this.methods = methods;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public List<NativeField> getProperties() {
        return properties;
    }

    public void setProperties(List<NativeField> properties) {
        this.properties = properties;
    }


    public Optional<String> parent() {
        return Optional.ofNullable(parent);
    }

    public ClassName getFactoryClassName() {
        return factoryClassName;
    }

    public void setFactoryClassName(ClassName factoryClassName) {
        this.factoryClassName = factoryClassName;
    }

    @XmlTransient
    public ClassName getClassName() {
        return className;
    }

    @XmlTransient
    public void setClassName(ClassName className) {
        this.className = className;
    }


}
