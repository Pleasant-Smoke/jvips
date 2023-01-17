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

package com.pss.jvips.plugin.actions.visitor;

import com.pss.jvips.plugin.model.xml.Namespace;
import com.pss.jvips.plugin.model.xml.toplevel.*;

@Deprecated
public class BaseTopLevelVisitor<T> {

    protected final Namespace namespace;
    protected T context;

    public BaseTopLevelVisitor(Namespace namespace, T context) {
        this.namespace = namespace;
        this.context = context;
    }

    public void loop(){
        for(TopLevelType tlt : namespace.getDeclarations()){
            if(tlt instanceof Alias a){
                handleAlias(a);
            } else if(tlt instanceof BitField bf){
                handleBitField(bf);
            } else if(tlt instanceof Callback cb){
                handleCallback(cb);
            } else if (tlt instanceof NativeClass clazz) {
                handleClass(clazz);
            } else if (tlt instanceof Constant con) {
                handleConstant(con);
            } else if (tlt instanceof FunctionMacro fm) {
                handleFunctionMacro(fm);
            } else if (tlt instanceof NativeRecord rec) {
                handleRecord(rec);
            } else if (tlt instanceof Enumeration e) {
                handleEnumeration(e);
            } else if (tlt instanceof Function f){
                handleFunction(f);
            }
        }
    }

    protected void handleAlias(Alias alias){

    }

    protected void handleBitField(BitField bitField){

    }

    protected void handleCallback(Callback cb){

    }

    protected void handleClass(NativeClass clazz){

    }

    protected void handleConstant(Constant constant){

    }

    protected void handleFunctionMacro(FunctionMacro macro){

    }

    protected void handleRecord(NativeRecord record){

    }

    protected void handleEnumeration(Enumeration enumeration){

    }

    protected void handleFunction(Function function){

    }

    public T getContext() {
        return context;
    }
}
