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
package com.pss.jvips.plugin.naming;


import com.squareup.javapoet.*;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.intellij.lang.annotations.MagicConstant;
import org.intellij.lang.annotations.PrintFormat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.processing.Generated;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

public class JavaTypeMapping  {

    private static final Logger log = LoggerFactory.getLogger(JavaTypeMapping.class);

    public static final ClassName MemorySegment_class = ClassName.get(Packages.foreign, "MemorySegment");
    public static final ClassName MemoryAddress_class = ClassName.get(Packages.foreign, "MemoryAddress");
    public static final ClassName MemorySession_class = ClassName.get(Packages.foreign, "MemorySession");

    public static final ClassName VipsSerializable_class = ClassName.get(Packages.Common.serialization, "VipsSerializable");

    /**
     * Underlying Image address type, Long or MemorySegment
     */
    public static final TypeVariableName DataType_TypeVariable = TypeVariableName.get("DataType");

    /**
     * Underlying VipsContext, Void for JNI and MemorySession for Panama
     */
    public static final TypeVariableName UnderlyingSession_TypeVariable = TypeVariableName.get("UnderlyingSession");
    public static final ClassName VOID = (ClassName) TypeName.VOID.box();


    public static final List<TypeVariableName> VipsSerializable_TypeVariables = List.of(DataType_TypeVariable, UnderlyingSession_TypeVariable);

    public static final ParameterizedTypeName VipsSerializable_Parameterized = getSerializedTypeSignature(VipsSerializable_class);



    public static final ClassName VipsOperations_class = ClassName.get(Packages.Common.Generated.base, "VipsOperations");
    public static final ClassName Long_class = (ClassName) TypeName.LONG.box();




    public static final ParameterizedTypeName VipsOperations_T_class = ParameterizedTypeName.get(VipsOperations_class, DataType_TypeVariable, UnderlyingSession_TypeVariable);
    public static final ParameterizedTypeName VipsOperations_Long_class = ParameterizedTypeName.get(VipsOperations_class, Long_class, VOID);
    public static final ParameterizedTypeName VipsOperations_MemorySegment_class = ParameterizedTypeName.get(VipsOperations_class, MemorySegment_class, MemorySession_class);

    public static final ClassName VipsInterpolate_class = ClassName.get(Packages.Common.Generated.base, "VipsInterpolate");
    
    public static final ClassName JniVipsOperations_class = ClassName.get(Packages.Jni.Generated.base, "JniVipsOperations");
    public static final ClassName PanamaVipsOperations_class = ClassName.get(Packages.Panama.Generated.base, "PanamaVipsOperations");
    
    
    public static final ClassName VipsNative_class = ClassName.get(Packages.Jni.jni, "VipsNative");
    public static final ClassName LibVips_class = ClassName.get(Packages.Panama.vips, "LibVips");
    public static final ClassName VipsImage_class = ClassName.get(Packages.Panama.vips, "VipsImage");

    public static final ClassName Override_class = ClassName.get(Override.class);

    public static final ClassName JVipsImage_class = ClassName.get(Packages.Common.image, "JVipsImage");
    public static final ClassName JVipsArea_class = ClassName.get(Packages.Common.data, "JVipsArea");
    public static final ClassName JVipsSource_class = ClassName.get(Packages.Common.data, "JVipsSource");
    public static final ClassName JVipsBlob_class = ClassName.get(Packages.Common.image, "JVipsBlob");
    public static final ClassName JVipsObject_class = ClassName.get(Packages.Common.data, "JVipsObject");
    public static final ClassName JVipsTarget_class = ClassName.get(Packages.Common.data, "JVipsTarget");
    public static final ClassName JVipsObjectClass_class = ClassName.get(Packages.Common.data, "JVipsObjectClass");
    public static final ParameterizedTypeName JVipsImage_T_class = ParameterizedTypeName.get(JVipsImage_class, DataType_TypeVariable);
    public static final ParameterizedTypeName JVipsImage_Long_class = ParameterizedTypeName.get(JVipsImage_class, Long_class);
    public static final ParameterizedTypeName JVipsImage_MemorySession_class = ParameterizedTypeName.get(JVipsImage_class, MemorySegment_class);

    public static final ClassName Reference_class = ClassName.get(Packages.Common.api, "Reference");
    public static final ClassName VipsException_class = ClassName.get(Packages.Common.context, "VipsException");
    public static final ClassName ArrayUtil_class = ClassName.get(Packages.Common.util, "ArrayUtil");
    public static final ClassName Locking_class = ClassName.get(Packages.Common.util, "Locking");
    public static final ClassName VipsOperationContextAccess_class = ClassName.get(Packages.Common.util, "VipsOperationContextAccess");
    public static final ClassName VipsSharedSecret_class = ClassName.get(Packages.Common.util, "VipsSharedSecret");

    public static final ClassName NegativeEnum_class = ClassName.get(Packages.Common.api, "NegativeEnum");
    public static final ClassName VipsOperationContext_class = ClassName.get(Packages.Common.context, "VipsOperationContext");
    public static final ClassName VipsInternalOperationContext_class = ClassName.get(Packages.Common.impl, "VipsInternalOperationContext");

    public static final ParameterizedTypeName VipsOperationContext_T_class = ParameterizedTypeName.get(VipsOperationContext_class, DataType_TypeVariable, UnderlyingSession_TypeVariable);
    public static final ParameterizedTypeName VipsOperationContext_Long_class = ParameterizedTypeName.get(VipsOperationContext_class, TypeName.LONG.box(), VOID);
    public static final ParameterizedTypeName VipsOperationContext_MemorySession_class = ParameterizedTypeName.get(VipsOperationContext_class, MemorySession_class);


    public static final ClassName BufferUtils_class = ClassName.get(Packages.Common.util, "BufferUtils");

    public static final ClassName Parameter_class = ClassName.get(Packages.Common.Generated.messages, "Parameter");
    public static final ClassName Parameter$Builder_class = Parameter_class.nestedClass("Builder");

    public static final ClassName NativeExecutable_class = ClassName.get(Packages.Common.annotation, "NativeExecutable");
    public static final ClassName NativeParameter_class = ClassName.get(Packages.Common.annotation, "NativeParameter");
    public static final ClassName OptionalParameter_class = ClassName.get(Packages.Common.annotation, "OptionalParameter");

    public static final AnnotationSpec OptionalParameter_Annotation = AnnotationSpec.builder(OptionalParameter_class).build();

    public static final ClassName OptionalParameter$Direction_class = ClassName.get(Packages.Common.annotation, "OptionalParameter", "Direction");

    public static final ClassName MagicConstant_class = ClassName.get(MagicConstant.class);
    public static final ClassName PrintFormat_class = ClassName.get(PrintFormat.class);
    public static final ClassName Range_class = ClassName.get(Range.class);
    public static final ClassName NotNull_class = ClassName.get(NotNull.class);
    public static final ClassName Nullable_class = ClassName.get(org.jetbrains.annotations.Nullable.class);

    public static final ClassName ServiceLoader$Provider_class = ClassName.get(ServiceLoader.Provider.class);

    public static final ClassName OptionalParameterFactory_class = ClassName.get(Packages.Common.Generated.base, "OptionalParameterFactory");
    public static final ClassName AutoService_class = ClassName.get("com.google.auto.service", "AutoService");

    public static final ClassName OptionalJniParameterFactory_class = ClassName.get(Packages.Jni.Generated.base, "OptionalJniParameterFactory");
    public static final ClassName OptionalPanamaParameterFactory_class = ClassName.get(Packages.Panama.Generated.arguments, "OptionalPanamaParameterFactory");





    /**
     * VipsTarget uses file descriptor
     */
    public static final ClassName FileInputStream_class = ClassName.get(FileInputStream.class);
    public static final ClassName FileOutputStream_class = ClassName.get(FileOutputStream.class);
    public static final ClassName DatagramChannel_class = ClassName.get(DatagramChannel.class);
    public static final ClassName FileChannel_class = ClassName.get(FileChannel.class);
    public static final ClassName SocketChannel_class = ClassName.get(SocketChannel.class);


    public static final ArrayTypeName Int_Array = ArrayTypeName.of(TypeName.INT);
    public static final ArrayTypeName Double_Array = ArrayTypeName.of(TypeName.DOUBLE);
    public static final ArrayTypeName Object_Array = ArrayTypeName.of(TypeName.OBJECT);


    public static final ClassName Map_class = ClassName.get(Map.class);
    public static final ClassName List_class = ClassName.get(List.class);
    public static final ClassName ArrayList_class = ClassName.get(ArrayList.class);
    public static final ClassName ByteBuffer_class = ClassName.get(ByteBuffer.class);
    public static final ClassName DoubleBuffer_class = ClassName.get(DoubleBuffer.class);
    public static final ClassName String_class = ClassName.get(String.class);

    public static final ClassName ERROR_ENUM_CLASS = ClassName.get(Packages.Common.base,"JVipsErrors");

    public static final ArrayTypeName VipsImage_Array_class = ArrayTypeName.of(JVipsImage_class);

    // Intentionally not using Map#of(..)
    private static final Map<String, TypeName> types = new HashMap<>();

    private static final Set<TypeName> bitFields = new HashSet<>();

    private static final Set<TypeName> negativeEnums = new HashSet<>();
    private static final Set<TypeName> regularEnums = new HashSet<>();

    private static final Map<TypeName, String> typeConstants = new HashMap<>();
    private static final Map<TypeName, Boolean> requiresTypeParam = new HashMap<>();

    /**
     * Example Block:
     *
     * [code,C]
     * ----
     * 	VIPS_ARG_ENUM( class, "pcs", 6,
     * 		_( "PCS" ),
     * 		_( "Set Profile Connection Space" ),
     * 		VIPS_ARGUMENT_OPTIONAL_INPUT,
     * 		G_STRUCT_OFFSET( VipsIcc, pcs ),
     * 		VIPS_TYPE_PCS <1>, VIPS_PCS_LAB );
     * ----
     * <1> The C Source parser will turn this enum name to: VipsPcs, when it should be VipsPCS
     */
    private static final Map<String, String> shims = new HashMap<>();



    // VipsPel alias ??
    static {
        types.put("gint", TypeName.INT);
        types.put("guint8", TypeName.BYTE);
        types.put("guint", TypeName.LONG);
        types.put("gboolean", TypeName.BOOLEAN);
        types.put("int", TypeName.INT);
        types.put("gdouble", TypeName.DOUBLE);
        types.put("double", TypeName.DOUBLE);
        types.put("gfloat", TypeName.FLOAT);
        types.put("float", TypeName.FLOAT);
        types.put("glong", TypeName.LONG);
        types.put("guint64", TypeName.LONG);
        types.put("gsize", TypeName.LONG);
        types.put("gpointer", TypeName.LONG);
        types.put("none", TypeName.VOID);
        types.put("utf8", TypeName.get(String.class));
        types.put("string", TypeName.get(String.class));
        types.put("gchararray", TypeName.get(String.class));
        types.put("gchar", TypeName.BYTE);
        types.put("TRUE", TypeName.BOOLEAN);
        types.put("GType", TypeName.LONG); //https://gtk.developpez.com/doc/en/gobject/gobject-Type-Information.html#GType??
        types.put("Pel", TypeName.BYTE); // unsigned char
        types.put("VipsImage", JVipsImage_class);
        types.put("JVipsImage", JVipsImage_class);
        types.put("Image", JVipsImage_class);
        types.put("VipsSource", JVipsSource_class);
        types.put("Source", JVipsSource_class);
        types.put("Blob", JVipsBlob_class);
        types.put("VipsBlob", JVipsBlob_class);
        types.put("VipsTarget", JVipsTarget_class);
        types.put("Target", JVipsTarget_class);
        types.put("Object", JVipsObject_class);
        types.put("VipsObject", JVipsObject_class);
        types.put("VipsObjectClass", JVipsObjectClass_class);
        types.put("ObjectClass", JVipsObjectClass_class);


        // Array Double and Int just seem to hold just a few values
        types.put("VipsArrayDouble", Double_Array);
        types.put("VipsArrayInt", Int_Array);

        typeConstants.put(TypeName.BOOLEAN, "BOOLEAN");
        typeConstants.put(TypeName.BYTE, "BYTE");
        typeConstants.put(ByteBuffer_class, "BYTE_BUFFER");
        //typeConstants.put(TypeName.CALLBACK, "CALLBACK");
        typeConstants.put(TypeName.DOUBLE, "DOUBLE");
        typeConstants.put(DoubleBuffer_class, "DOUBLE_BUFFER");
        typeConstants.put(TypeName.INT, "INT");
        typeConstants.put(TypeName.LONG, "LONG");
        //typeConstants.put(TypeName.POINTER, "POINTER");
        typeConstants.put(TypeName.SHORT, "SHORT");
        typeConstants.put(String_class, "STRING");
       // typeConstants.put(TypeName.ERROR, "ERROR");
        typeConstants.put(JVipsImage_class, "IMAGE");
        typeConstants.put(VipsImage_Array_class, "IMAGE_ARRAY");
        //typeConstants.put(TypeName.INSTANCE, "INSTANCE");
        typeConstants.put(TypeName.VOID, "VOID");
        typeConstants.put(Double_Array, "VIPS_ARRAY_DOUBLE");
        typeConstants.put(Int_Array, "VIPS_ARRAY_INT");
        typeConstants.put(JVipsTarget_class, "TARGET");
        typeConstants.put(JVipsSource_class, "SOURCE");
        typeConstants.put(JVipsBlob_class, "BLOB");

       // typeConstants.put(TypeName.ULONG, "ULONG");
        shims.put("Pcs", "VipsPCS");
        shims.put("VipsPcs", "VipsPCS");
    }

    public static void registerEnum(ClassName className){
        regularEnums.add(className);
    }

    public static void registerNegativeEnum(ClassName className){
        negativeEnums.add(className);
    }


    public static boolean isRegularEnum(TypeName className){
        return regularEnums.contains(className);
    }

    public static boolean isNegativeEnum(TypeName className){
       return negativeEnums.contains(className);
    }

    public static String getTypeConstant(TypeName typeName){
        if(isRegularEnum(typeName) || isNegativeEnum(typeName)){
            return "ENUM";
        } else if(isBitField(typeName)){
            return "BIT_FIELD";
        }
        return typeConstants.get(typeName);
    }

    public static TypeName getType(@Nullable String ctype){
        return getType(ctype, false, "");
    }


    public static TypeName getType(@Nullable String ctype, boolean create, String _package){
        if(StringUtils.isEmpty(ctype)){
            log.warn("Type was {} returning java.lang.Object", ctype == null ? "<null>" : "<empty>");
        }
        ctype = shims.getOrDefault(ctype, ctype);
        TypeName typeName = types.get(ctype);

        if(typeName == null && (typeName = types.get("Vips" + ctype)) == null){

            if(create && StringUtils.isNotBlank(ctype)){
                if(!ctype.startsWith("Vips") && !ctype.startsWith("Panama") && !ctype.startsWith("Jni")){
                    ctype = "Vips" + ctype;
                }
                ClassName className = ClassName.get(_package, ctype);
                types.put(ctype, className);
                return className;
            }
            log.warn("Unable to deduce Java type from native library with type: {} returning java.lang.Object", ctype);
            return TypeName.OBJECT;
        }
        return typeName;
    }

    public static ClassName getClassName(JavaCaseFormat javaName, String _package){
        return getClassName(javaName.getJavaName(), _package);
    }

    public static ClassName getClassName(String javaName, String _package){
        return (ClassName) getType(javaName, true, _package);
    }


    public static boolean isBitField(TypeName className){
        return bitFields.contains(className);
    }

    public static void putBitField(TypeName className){
        bitFields.add(className);
    }


    public static AnnotationSpec generateMagicConstant(ClassName className){
        return AnnotationSpec
                .builder(MagicConstant_class)
                .addMember("flagsFromClass", "$T.class", className)
                .build();
    }

    public static boolean requiresTypeParam(TypeName typeSpec){
        return requiresTypeParam.getOrDefault(typeSpec, Boolean.FALSE);
    }


    public static void registerRequiresTypeParam(TypeName typeSpec){
        requiresTypeParam.put(typeSpec, Boolean.TRUE);
    }



    public static TypeName getUsableType(ClassName className){
        return isBitField(className) ? TypeName.LONG : className;
    }

    public static ParameterizedTypeName getSerializedTypeSignature(ClassName cn){
        return ParameterizedTypeName.get(cn, DataType_TypeVariable, UnderlyingSession_TypeVariable);
    }
}
