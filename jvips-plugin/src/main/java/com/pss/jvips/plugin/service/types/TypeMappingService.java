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

package com.pss.jvips.plugin.service.types;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.pss.jvips.plugin.naming.JavaCaseFormat;
import com.pss.jvips.plugin.util.History;
import com.pss.jvips.plugin.util.Utils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.util.HashMap;
import java.util.Map;

import static com.pss.jvips.plugin.naming.JavaTypeMapping.*;

public class TypeMappingService {

    protected final Table<SymbolTypes, String, History<TypeName>> symbolTypes = HashBasedTable.create();

    {
        symbolTypes.put(SymbolTypes.TYPE,"gint", new History<>(TypeName.INT));
        symbolTypes.put(SymbolTypes.TYPE,"guint8", new History<>(TypeName.BYTE));
        symbolTypes.put(SymbolTypes.TYPE,"guint", new History<>(TypeName.LONG));
        symbolTypes.put(SymbolTypes.TYPE,"gboolean", new History<>(TypeName.BOOLEAN));
        symbolTypes.put(SymbolTypes.TYPE,"int", new History<>(TypeName.INT));
        symbolTypes.put(SymbolTypes.TYPE,"gdouble", new History<>(TypeName.DOUBLE));
        symbolTypes.put(SymbolTypes.TYPE,"double", new History<>(TypeName.DOUBLE));
        symbolTypes.put(SymbolTypes.TYPE,"gfloat", new History<>(TypeName.FLOAT));
        symbolTypes.put(SymbolTypes.TYPE,"float", new History<>(TypeName.FLOAT));
        symbolTypes.put(SymbolTypes.TYPE,"glong", new History<>(TypeName.LONG));
        symbolTypes.put(SymbolTypes.TYPE,"guint64", new History<>(TypeName.LONG));
        symbolTypes.put(SymbolTypes.TYPE,"gsize", new History<>(TypeName.LONG));
        symbolTypes.put(SymbolTypes.TYPE,"gpointer", new History<>(TypeName.LONG));
        symbolTypes.put(SymbolTypes.TYPE,"none", new History<>(TypeName.VOID));
        symbolTypes.put(SymbolTypes.TYPE,"utf8", new History<>(String_class));
        symbolTypes.put(SymbolTypes.TYPE,"string", new History<>(String_class));
        symbolTypes.put(SymbolTypes.TYPE,"gchararray", new History<>(String_class));
        symbolTypes.put(SymbolTypes.TYPE,"gchar", new History<>(TypeName.BYTE));
        symbolTypes.put(SymbolTypes.TYPE,"TRUE", new History<>(TypeName.BOOLEAN));
        symbolTypes.put(SymbolTypes.TYPE,"GType", new History<>(TypeName.LONG)); //https://gtk.developpez.com/doc/en/gobject/gobject-Type-Information.html#GType??
        symbolTypes.put(SymbolTypes.TYPE,"Pel", new History<>(TypeName.BYTE)); // unsigned char
        symbolTypes.put(SymbolTypes.TYPE,"VipsImage", new History<>(JVipsImage_class));
        symbolTypes.put(SymbolTypes.TYPE,"JVipsImage", new History<>(JVipsImage_class));
        symbolTypes.put(SymbolTypes.TYPE,"Image", new History<>(JVipsImage_class));
        symbolTypes.put(SymbolTypes.TYPE,"VipsSource", new History<>(JVipsSource_class));
        symbolTypes.put(SymbolTypes.TYPE,"Source", new History<>(JVipsSource_class));
        symbolTypes.put(SymbolTypes.TYPE,"Blob", new History<>(JVipsBlob_class));
        symbolTypes.put(SymbolTypes.TYPE,"VipsBlob", new History<>(JVipsBlob_class));
        symbolTypes.put(SymbolTypes.TYPE,"VipsTarget", new History<>(JVipsTarget_class));
        symbolTypes.put(SymbolTypes.TYPE,"Target", new History<>(JVipsTarget_class));
        symbolTypes.put(SymbolTypes.TYPE,"Object", new History<>(JVipsObject_class));
        symbolTypes.put(SymbolTypes.TYPE,"VipsObject", new History<>(JVipsObject_class));
        symbolTypes.put(SymbolTypes.TYPE,"VipsObjectClass", new History<>(JVipsObjectClass_class));
        symbolTypes.put(SymbolTypes.TYPE,"ObjectClass", new History<>(JVipsObjectClass_class));
    }

    public void registerConstantSymbol(JavaCaseFormat name, History<TypeName> className){
        symbolTypes.put(SymbolTypes.CONSTANT, name.getNativeName(), className);
    }

    public History<TypeName> getType(String type){
        Preconditions.checkArgument(type != null);
        Map<SymbolTypes, History<TypeName>> column = symbolTypes.column(type);
        if(column.size() == 1){
            return Utils.getFirst(column.values());
        } else if(column.isEmpty()){
            throw new RuntimeException("Nothing for " + type + " were found");
        }
        throw new RuntimeException("To many instances of " + type + " were found");
    }
}
