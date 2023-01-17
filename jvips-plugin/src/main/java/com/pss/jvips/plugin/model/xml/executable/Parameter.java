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

import com.pss.jvips.plugin.model.xml.type.NativeType;
import com.pss.jvips.plugin.naming.JavaCaseFormat;
import com.pss.jvips.plugin.model.base.DocumentedType;
import com.pss.jvips.plugin.model.xml.Documentation;
import com.pss.jvips.plugin.model.base.Typeable;
import com.pss.jvips.plugin.model.xml.type.AbstractNativeType;
import com.pss.jvips.plugin.model.xml.types.Direction;
import com.pss.jvips.plugin.model.xml.types.TransferOwnership;
import com.pss.jvips.plugin.xml.IntegerBooleanAdapter;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;
import java.util.Optional;
@XmlAccessorType(XmlAccessType.NONE)
public class Parameter implements Typeable, DocumentedType {

    // Parameter names that are out params that refer to size
    public static final List<String> POSSIBLE_LENGTH_OR_SIZE = List.of("n", "length", "sizeof_type", "len", "size");

    public static final List<String> EXCLUSIONS = List.of("va_list", "GObject.Object", "GObject.Value");

    @XmlTransient
    protected Integer index;

    @XmlTransient
    protected JavaCaseFormat javaName;

    @Nullable
    @XmlTransient
    protected Parameter length;

    /**
     * Hacky workaround for vips_linear
     */
    @XmlTransient
    protected boolean writeLength = true;

    @Nullable
    @XmlAttribute
    protected String name;

    @Nullable
    @XmlElementRef
    protected AbstractNativeType type;

    @XmlAttribute(name = "transfer-ownership")
    protected TransferOwnership transferOwnership;

    @XmlAttribute
    protected Direction direction = Direction.IN;

    @Nullable
    @XmlAttribute(name = "caller-allocates")
    protected Boolean callerAllocates;

    @Nullable
    @XmlAttribute
    @XmlJavaTypeAdapter(IntegerBooleanAdapter.class)
    protected Boolean optional;

    @Nullable
    @XmlAttribute
    @XmlJavaTypeAdapter(IntegerBooleanAdapter.class)
    protected Boolean allowNone;

    @XmlElement(name = "doc")
    protected Documentation documentation;

    public boolean isPossibleSizeOrLengthParam(){
        return isOut() && name().map(POSSIBLE_LENGTH_OR_SIZE::contains).orElse(Boolean.FALSE);
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<String> name(){
        return Optional.ofNullable(name);
    }

    public Documentation getDocumentation() {
        return documentation;
    }

    public void setDocumentation(Documentation documentation) {
        this.documentation = documentation;
    }

    public TransferOwnership getTransferOwnership() {
        return transferOwnership;
    }

    public void setTransferOwnership(TransferOwnership transferOwnership) {
        this.transferOwnership = transferOwnership;
    }

    public boolean isWriteLength() {
        return writeLength;
    }

    public void setWriteLength(boolean writeLength) {
        this.writeLength = writeLength;
    }

    public boolean isExcluded(){
        return Optional.ofNullable(getType()).map(x-> {
            if (type instanceof NativeType nt) {
                return EXCLUSIONS.contains(nt.getName());
            }
            return false;
        }).orElse(false);
    }

    @Override
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

    public boolean isIn(){
        return !isOut();
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Boolean getCallerAllocates() {
        return callerAllocates == null ? Boolean.FALSE : Boolean.TRUE;
    }

    public void setCallerAllocates(Boolean callerAllocates) {
        this.callerAllocates = callerAllocates;
    }

    public Boolean getOptional() {
        return optional == null ? Boolean.FALSE : optional;
    }

    public void setOptional(Boolean optional) {
        this.optional = optional;
    }

    public Boolean getAllowNone() {
        return allowNone  == null ? Boolean.FALSE : allowNone;
    }

    public void setAllowNone(Boolean allowNone) {
        this.allowNone = allowNone;
    }

    public boolean isVarArg(){
        return "...".equals(name);
    }

    public boolean isOut(){
        return "out".equals(name) || Direction.OUT == direction;
    }

    public Parameter getLength() {
        return length;
    }

    public void setLength(Parameter length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "Parameter{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", transferOwnership=" + transferOwnership +
                ", doc=" + documentation +
                '}';
    }

}
