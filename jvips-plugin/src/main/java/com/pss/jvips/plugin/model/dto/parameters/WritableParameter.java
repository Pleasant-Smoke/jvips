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

package com.pss.jvips.plugin.model.dto.parameters;

import com.pss.jvips.plugin.naming.JavaTypeMapping;
import com.pss.jvips.plugin.out.OutParam;
import com.pss.jvips.plugin.context.GlobalPluginContext;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.pss.jvips.plugin.naming.JavaTypeMapping.VipsNative_class;

public abstract class WritableParameter {

    public static final String in$params = "in$params";
    public static final String in$index = "in$index";
    public static final String out$params = "out$params";
    public static final String out$index = "out$index";

    protected int order;

    protected boolean imageLocksCalled;
    protected boolean imageArrayLocksCalled;

    protected final GlobalPluginContext context;

    public WritableParameter(GlobalPluginContext context) {
        this.context = context;
    }


    /**
     * Return true if out index is needed
     *
     * @param in
     * @param out
     * @param builder
     * @param params
     * @return
     */
    public abstract boolean addArrayInitializer(int in, int out, CodeBlock.Builder builder, Iterator<WritableParameter> params);
    public boolean addArrayInitializerPanama(int in, int out, CodeBlock.Builder builder, Iterator<WritableParameter> params){
        if(params.hasNext()){
            return params.next().addArrayInitializer(in, out, builder, params);
        }
        return false;
    }

    /**
     * This is kind of similar to a filter chain, it short circuits on the first input image(s)
     * @param params
     * @return true if the method body has to be wrapped in a stamped lock
     */
    public boolean requiresLock(Iterator<WritableParameter> params){
        if(!params.hasNext()){
            return false;
        }
        return params.next().requiresLock(params);
    }

    /**
     * Add all *IN* parameters
     * @param builder
     */
    public abstract void addParameters(MethodSpec.Builder builder);

    /**
     * All parameters must be *BOXED* A singular optional parameter that is an *OUT* type must wrap the value in a
     * {@link JavaTypeMapping#Reference_class}, this parameter is nullable and overloaded to call it with a null value
     * @param builder the method builder
     * @return true if an optional param was added
     */
    public boolean addOptionalArgumentParameters(MethodSpec.Builder builder){
        return false;
    }

    /**
     * Check parameters, for composed parameters that take a {@link java.nio.ByteBuffer} check if it is off heap
     * @param builder the method builder
     */
    public void addPreInit(MethodSpec.Builder builder) {

    }


    public void registerOutParams(Set<OutParam> outParams){

    }


    public void addImageLocks(final List<CodeBlock> blocks){

        imageLocksCalled = true;
    }

    /**
     * Must be called last, as the array is the final param
     * @param blocks
     */
    public void addImageArrayLocks(final List<CodeBlock> blocks){
        //Preconditions.checkArgument(imageLocksCalled, "");
    }


    /**
     * Process out parameters
     * @param builder
     */
    public void addPostInit(CodeBlock.Builder builder) {

    }

    public  void writeJniInArray(CodeBlock.Builder builder) {

    }

    public void writeJniOutArray(CodeBlock.Builder builder) {


    }

    void writeInternal(CodeBlock.Builder builder, String arrayName, String indexName, String nativeName,
                       String flags, String value){
        builder.addStatement("$L[++$L] = $S", arrayName, indexName, nativeName);
        builder.addStatement("$L[++$L] = $T.$L", arrayName, indexName, VipsNative_class, flags);
        builder.addStatement("$L[++$L] = $L", arrayName, indexName, value);
    }

     boolean addArrayInitializerInternal(CodeBlock in, CodeBlock out, CodeBlock.Builder builder) {

        builder.addStatement("Object[] $L = new Object[$L]", in$params,  in);
        // Vips native needs to pass a non null value
        builder.addStatement("Object[] $L = new Object[$L]", out$params, out == null ? 0 : out);
         return out != null;
     }

    public String javaName(){
        return "";
    }

}
