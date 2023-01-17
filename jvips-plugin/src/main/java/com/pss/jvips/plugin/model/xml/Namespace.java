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


import com.pss.jvips.plugin.model.xml.toplevel.*;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


public class Namespace {

    @XmlAttribute
    protected String name;

    @XmlAttribute
    protected String version;

    @XmlAttribute(name = "shared-library")
    protected String sharedLibrary;

    @XmlAttribute(name = "c-identifier-prefixes")
    protected String identifierPrefixes;

    @XmlAttribute(name = "c-symbol-prefixes")
    protected String symbolPrefixes;


    @XmlAnyElement(lax = true)
    @XmlElements({
            @XmlElement(name = "alias", type = Alias.class),
            @XmlElement(name = "bitfield", type = BitField.class),
            @XmlElement(name = "callback", type = Callback.class),
            @XmlElement(name = "class", type = NativeClass.class),
            @XmlElement(name = "constant", type = Constant.class),
            @XmlElement(name = "function-macro", type = FunctionMacro.class),
            @XmlElement(name = "record", type = NativeRecord.class),
            @XmlElement(name = "enumeration", type = Enumeration.class),
            @XmlElement(name = "function", type = Function.class),

    })
    protected List<TopLevelType> declarations = new ArrayList<>();

    @XmlTransient
    protected List<NativeClass> classes = new ArrayList<>();

    @XmlTransient
    protected List<Enumeration> enums = new ArrayList<>();

    @XmlTransient
    protected List<NativeRecord> records = new ArrayList<>();

    @XmlTransient
    protected List<Callback> callbacks = new ArrayList<>();

    @XmlTransient
    protected List<Function> functions = new ArrayList<>();

    @XmlTransient
    protected List<Constant> constants = new ArrayList<>();

    @XmlTransient
    protected List<BitField> bitFields = new ArrayList<>();




    public List<BitField> getBitFields() {
        return bitFields;
    }

    public void setBitFields(List<BitField> bitFields) {
        this.bitFields = bitFields;
    }

    public List<Constant> getConstants() {
        return constants;
    }

    public void setConstants(List<Constant> constants) {
        this.constants = constants;
    }

    public List<NativeRecord> getRecords() {
        return records;
    }

    public void setRecords(List<NativeRecord> GLibRecords) {
        this.records = GLibRecords;
    }

    public List<Callback> getCallbacks() {
        return callbacks;
    }

    public void setCallbacks(List<Callback> callbacks) {
        this.callbacks = callbacks;
    }

    public List<Function> getFunctions() {
        return functions;
    }

    public void setFunctions(List<Function> functions) {
        this.functions = functions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSharedLibrary() {
        return sharedLibrary;
    }

    public void setSharedLibrary(String sharedLibrary) {
        this.sharedLibrary = sharedLibrary;
    }

    public String getIdentifierPrefixes() {
        return identifierPrefixes;
    }

    public void setIdentifierPrefixes(String identifierPrefixes) {
        this.identifierPrefixes = identifierPrefixes;
    }

    public String getSymbolPrefixes() {
        return symbolPrefixes;
    }

    public void setSymbolPrefixes(String symbolPrefixes) {
        this.symbolPrefixes = symbolPrefixes;
    }

    public List<TopLevelType> getDeclarations() {
        return declarations;
    }

    public void setDeclarations(List<TopLevelType> declarations) {
        this.declarations = declarations;
    }

    public List<NativeClass> getClazzes() {
        return classes;
    }

    public void setClazzes(List<NativeClass> classes) {
        this.classes = classes;
    }

    public List<Enumeration> getEnums() {
        return enums;
    }

    public void setEnums(List<Enumeration> enums) {
        this.enums = enums;
    }

    @Override
    public String toString() {
        return "Namespace{" +
                "name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", sharedLibrary='" + sharedLibrary + '\'' +
                ", identifierPrefixes='" + identifierPrefixes + '\'' +
                ", symbolPrefixes='" + symbolPrefixes + '\'' +
                ", declarations=" + declarations +
                '}';
    }


    @SuppressWarnings("unused")
    void afterUnmarshal(Unmarshaller unmarshaller, Object parent){
        declarations.forEach(d-> {
            if(d instanceof NativeRecord r){
                records.add(r);
            } else if(d instanceof NativeClass c){
                classes.add(c);
            } else if(d instanceof Enumeration e){
                enums.add(e);
            } else if(d instanceof Function f){
                functions.add(f);
            } else if(d instanceof Callback cb){
                callbacks.add(cb);
            } else if(d instanceof Constant co){
                constants.add(co);
            } else if(d instanceof BitField b){
                bitFields.add(b);
            }
        });
    }


}
