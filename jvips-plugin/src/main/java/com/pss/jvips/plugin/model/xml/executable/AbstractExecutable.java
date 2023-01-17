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

import com.pss.jvips.plugin.model.dto.parameters.WritableParameter;
import com.pss.jvips.plugin.model.xml.Documentation;
import com.pss.jvips.plugin.naming.JavaCaseFormat;
import com.pss.jvips.plugin.naming.JavaNaming;
import com.pss.jvips.plugin.model.xml.AbstractDocumentedType;
import com.pss.jvips.plugin.model.base.Nameable;
import com.pss.jvips.plugin.util.MethodOptionalParametersDocumentation;
import com.pss.jvips.plugin.xml.IntegerBooleanAdapter;
import com.squareup.javapoet.MethodSpec;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.util.*;

@XmlAccessorType(XmlAccessType.NONE)
public abstract class AbstractExecutable extends AbstractDocumentedType implements Nameable {

    private static final Logger log = LoggerFactory.getLogger(Parameter.class);

    public enum OptionalArg {
        NONE, SINGULAR_IN, SINGULAR_OUT, DTO
    }

    @XmlTransient
    protected List<Parameter> varargs = new ArrayList<>();

    @XmlTransient
    protected boolean exclude;

    @XmlTransient
    protected MethodOptionalParametersDocumentation optionalParametersDocumentation;

    @XmlTransient
    protected Set<String> optionalParameterNames = new HashSet<>();

    @XmlTransient
    protected OptionalArg optionalArg;

    @XmlElementWrapper(name = "parameters")
    @XmlElements({
            @XmlElement(name = "parameter", type = Parameter.class),
            @XmlElement(name = "instance-parameter", type = InstanceParameter.class)
    })
    protected List<Parameter> parameters = new ArrayList<>();

    @XmlTransient
    protected MethodSpec baseMethod;

    /**
     * Return values are usually just a 1 or a -1 or 0 to indicate failure the native methods use a pointer to in the
     * method parameter and pass it to that.
     */
    @XmlElement(name="return-value")
    protected ReturnValue returnValue;

    @XmlJavaTypeAdapter(IntegerBooleanAdapter.class)
    @XmlAttribute
    @Nullable
    protected Boolean introspectable;

    // Only occurs on method enums bitfields and class
    @Nullable
    @XmlAttribute(name = "glib-get-property")
    protected String getProperty;

    // Only occurs on method
    @Nullable
    @XmlAttribute(name = "glib-set-property")
    protected String setProperty;


    @Nullable
    @XmlTransient
    protected Parameter instanceParameter;

    /**
     * Contains the required in params for the java method
     */
    @XmlTransient
    protected List<Parameter> javaMethodArguments = new ArrayList<>();


    /**
     * Contains the required in params for the java method
     */
    @XmlTransient
    protected List<Parameter> consolidatedJavaInArguments = new ArrayList<>();

    /**
     * Because Java data structures have size/length bounds, we have to pass a length argument, we could do this in
     * native code, but it will be easier just for the native code to grab a pointer and the length
     */
    @XmlTransient
    protected List<Parameter> consolidatedNativeInArguments = new ArrayList<>();


    /**
     * Contains the required in params for the java method
     */
    @XmlTransient
    protected List<Parameter> consolidatedJavaOutArguments = new ArrayList<>();

    /**
     * Because Java data structures have size/length bounds, we have to pass a length argument, we could do this in
     * native code, but it will be easier just for the native code to grab a pointer and the length
     */
    @XmlTransient
    protected List<Parameter> consolidatedNativeOutArguments = new ArrayList<>();

    /**
     * Under normal circumstantes there should only be 1 out param for an operation that is the VipsImage, the optional
     * arguments may have out parameters but since we pass an object or a "holder" object, that lets us just mutate them
     */
    @XmlTransient
    protected List<Parameter> outParameters = new ArrayList<>();

    /**
     * Parameters that contain a {@link ByteBuffer} or a {@link DoubleBuffer}
     */
    @XmlTransient
    protected List<Parameter> bufferParameters = new ArrayList<>();

    /**
     * Parameters that contain an array
     */
    @XmlTransient
    protected List<Parameter> arrayParameters = new ArrayList<>();

    protected List<WritableParameter> writableParams = new ArrayList<>();

    @Nullable
    @XmlTransient
    protected Parameter vargs;

    public boolean isExclude() {
        return exclude;
    }

    public void setExclude(boolean exclude) {
        this.exclude = exclude;
    }

    public List<WritableParameter> getWritableParams() {
        return writableParams;
    }

    public void setWritableParams(List<WritableParameter> writableParams) {
        this.writableParams = writableParams;
    }

    public List<Parameter> getVarargs() {
        return varargs;
    }

    public void setVarargs(List<Parameter> varargs) {
        this.varargs = varargs;
    }

    public MethodSpec getBaseMethod() {
        return baseMethod;
    }

    public void setBaseMethod(MethodSpec baseMethod) {
        this.baseMethod = baseMethod;
    }

    public String nativeReturnType(){
        return Optional.ofNullable(returnValue).map(rv-> rv.getNativeType()).orElse("");
    }

    public ReturnValue getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(ReturnValue returnValue) {
        this.returnValue = returnValue;
    }


    public List<Parameter> getArrayParameters() {
        return arrayParameters;
    }

    public void setArrayParameters(List<Parameter> arrayParameters) {
        this.arrayParameters = arrayParameters;
    }

    public List<Parameter> getBufferParameters() {
        return bufferParameters;
    }

    public void setBufferParameters(List<Parameter> bufferParameters) {
        this.bufferParameters = bufferParameters;
    }

    public Boolean getIntrospectable() {
        return introspectable;
    }

    public void setIntrospectable(Boolean introspectable) {
        this.introspectable = introspectable;
    }


    public List<Parameter> getConsolidatedNativeInArguments() {
        return consolidatedNativeInArguments;
    }

    public void setConsolidatedNativeInArguments(List<Parameter> consolidatedNativeInArguments) {
        this.consolidatedNativeInArguments = consolidatedNativeInArguments;
    }

    @Override
    public JavaCaseFormat getJavaName() {
        if(javaName == null && StringUtils.isNotBlank(name)){
            this.javaName = JavaNaming.methodName(identifier());
        }
        return this.javaName;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public String getGetProperty() {
        return getProperty;
    }

    public void setGetProperty(String getProperty) {
        this.getProperty = getProperty;
    }

    public String getSetProperty() {
        return setProperty;
    }

    public void setSetProperty(String setProperty) {
        this.setProperty = setProperty;
    }

    public Parameter getInstanceParameter() {
        return instanceParameter;
    }

    public void setInstanceParameter(Parameter instanceParameter) {
        this.instanceParameter = instanceParameter;
    }

    public List<Parameter> getConsolidatedJavaInArguments() {
        return consolidatedJavaInArguments;
    }

    public void setConsolidatedJavaInArguments(List<Parameter> consolidatedJavaInArguments) {
        this.consolidatedJavaInArguments = consolidatedJavaInArguments;
    }

    public List<Parameter> getOutParameters() {
        return outParameters;
    }

    public void setOutParameters(List<Parameter> outParameters) {
        this.outParameters = outParameters;
    }

    public Parameter getVargs() {
        return vargs;
    }

    public void setVargs(Parameter vargs) {
        this.vargs = vargs;
    }

    public List<Parameter> getJavaMethodArguments() {
        return javaMethodArguments;
    }

    public void setJavaMethodArguments(List<Parameter> javaMethodArguments) {
        this.javaMethodArguments = javaMethodArguments;
    }

    public Optional<String> filename(){
        return Optional.ofNullable(documentation).map(Documentation::getFilename);
    }

    public OptionalArg getOptionalArg() {
        return optionalArg;
    }

    public void setOptionalArg(OptionalArg optionalArg) {
        this.optionalArg = optionalArg;
    }

    public MethodOptionalParametersDocumentation getOptionalParametersDocumentation() {
        return optionalParametersDocumentation;
    }

    public void setOptionalParametersDocumentation(MethodOptionalParametersDocumentation optionalParametersDocumentation) {
        this.optionalParametersDocumentation = optionalParametersDocumentation;
    }

    @Override
    public String toString() {
        return "AbstractExecutableType{" +
                ", parameters=" + parameters +
                ", returnValue=" + returnValue +
                ", introspectable=" + introspectable +
                ", getProperty='" + getProperty + '\'' +
                ", setProperty='" + setProperty + '\'' +
                ", uuid=" + uuid +
                ", javaName=" + javaName +
                ", name='" + name + '\'' +
                ", identifier='" + identifier + '\'' +
                ", documentation=" + documentation +
                '}';
    }


}
