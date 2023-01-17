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

package jvips;


import com.pss.jvips.common.context.VipsException;
import com.pss.jvips.common.util.Conditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * todo: The disc threshold can be set with the "--vips-disc-threshold" command-line argument, or the VIPS_DISC_THRESHOLD
 * environment variable. The value is a simple integer, but can take a unit postfix of "k", "m" or "g" to indicate
 * kilobytes, megabytes or gigabytes. The default threshold is 100 MB.
 */
public class VipsNative {

    private static final Logger log = LoggerFactory.getLogger(VipsNative.class);
    public static final Logger jni = LoggerFactory.getLogger(VipsNative.class.getName() + "#JNI");

    private static final AtomicBoolean loaded = new AtomicBoolean(false);

    private static final long MAX_LONG = 0x7fffffffffffffffL;
    private static final int MAX_INT = 0x7fffffff;

    public static final long BIT_FIELD = 1;
    public static final long BOOLEAN = 2;
    public static final long BYTE = 3; // char
    public static final long BYTE_BUFFER = 4;
    public static final long CALLBACK = 5;
    public static final long DOUBLE = 6;
    public static final long DOUBLE_BUFFER = 7;
    public static final long ENUM = 8;
    public static final long INT = 9;
    public static final long LONG = 10;
    public static final long POINTER = 11;
    public static final long SHORT = 12;
    public static final long STRING = 13;
    public static final long ERROR = 14;
    public static final long IMAGE = 15;
    public static final long IMAGE_ARRAY = 16;
    public static final long INSTANCE = 17;
    public static final long VOID = 18;
    public static final long VIPS_ARRAY_DOUBLE = 19;
    public static final long VIPS_ARRAY_INT = 20;
    public static final long ULONG = 21;

    public static final long BLOB = 100;
    public static final long TARGET = 101;
    public static final long SOURCE = 102;

    public static final long IN = 1;
    public static final long OUT = 2;


    public static final long ERROR_GENERAL = -1;
    public static final long ERROR_OPERATION_NULL = -2;
    public static final long ERROR_ARGUMENTS_NULL = -3;
    public static final long ERROR_ARGUMENTS_MINUS_ONE_NOT_MULTIPLE_OF_3 = -4;
    public static final long ERROR_IN_PARAMETER_IS_NULL = -5;
    public static final long ERROR_INCOMPATIBLE_JAVA_TYPE = -6;
    public static final long ERROR_INCOMPATIBLE_TYPE_FLAG = -7;
    public static final long ERROR_INCOMPATIBLE_IN_OR_OUT_FLAG = -8;
    public static final long ERROR_INCOMPATIBLE_SECONDARY_TYPE_FLAG = -9;
    public static final long ERROR_VIPS_OBJECT_POINTER_IS_NULL = -10;
    public static final long VIPS_OPERATION_FAILED = -11;



    public static native void init(String name, Logger log, int level, boolean unsafe);

    private static native long callNative(String operation, Object[] in, Object[] out, String[] messages);

    public static void call(String operation, Object[] in, Object[] out, String[] messages) throws VipsException {
        Conditions.require(operation != null && !"".equals(operation), "Operation was null or empty");
        Conditions.require(in != null && ((in.length % 3) == 0), "In array was null or length not a multiple of 3");
        Conditions.require(out != null && ((out.length % 3) == 0), "Out array was null or length not a multiple of 3 or empty");
        Conditions.require(messages != null && messages.length == 1, "Message array was null or empty");
        long value = callNative(operation, in, out, messages);
        if(value == -2){
            throw new VipsException("Catastrophic error unable to show error");
        } else if(value == -1){
            throw new VipsException(messages[0]);
        }
    }

    public static <T> T newImageFromFile(String filename, long id) throws VipsException {
        Conditions.require(filename != null && !"".equals(filename), "Filename was null or empty");
        return newImageFromFile0(filename, id);
    }

    private static native <T> T newImageFromFile0(String filename, long id);

    private static native void shutdown();

    public static native int nativeIncreaseRefCount(long address);

    public static native int unrefNative(long address, boolean zero);


}
