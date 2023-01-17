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

import com.pss.jvips.plugin.util.DeprecationAction;
import com.pss.jvips.plugin.util.DeprecationType;
import com.pss.jvips.plugin.util.Revision;
import jakarta.xml.bind.annotation.XmlAttribute;

public class Deprecations {

    @XmlAttribute(name = "native-symbol")
    protected String nativeSymbol;

    @XmlAttribute(name = "previous-native-symbol")
    protected String previousNativeSymbol;

    @XmlAttribute(name = "java-symbol")
    protected String javaSymbol;

    @XmlAttribute(name = "previous-java-symbol")
    protected String previousJavaSymbol;

    @XmlAttribute(name = "action")
    protected DeprecationAction action;

    @XmlAttribute(name = "type")
    protected DeprecationType type;

    @XmlAttribute(name = "previous")
    protected Revision previous;



    public DeprecationType getType() {
        return type;
    }

    public Deprecations setType(DeprecationType type) {
        this.type = type;
        return this;
    }

    public String getNativeSymbol() {
        return nativeSymbol;
    }

    public Deprecations setNativeSymbol(String nativeSymbol) {
        this.nativeSymbol = nativeSymbol;
        return this;
    }

    public String getPreviousNativeSymbol() {
        return previousNativeSymbol;
    }

    public Deprecations setPreviousNativeSymbol(String previousNativeSymbol) {
        this.previousNativeSymbol = previousNativeSymbol;
        return this;
    }

    public String getJavaSymbol() {
        return javaSymbol;
    }

    public Deprecations setJavaSymbol(String javaSymbol) {
        this.javaSymbol = javaSymbol;
        return this;
    }

    public String getPreviousJavaSymbol() {
        return previousJavaSymbol;
    }

    public Deprecations setPreviousJavaSymbol(String previousJavaSymbol) {
        this.previousJavaSymbol = previousJavaSymbol;
        return this;
    }

    public DeprecationAction getAction() {
        return action;
    }

    public Deprecations setAction(DeprecationAction action) {
        this.action = action;
        return this;
    }

    public Revision getPrevious() {
        return previous;
    }

    public Deprecations setPrevious(Revision previous) {
        this.previous = previous;
        return this;
    }
}
