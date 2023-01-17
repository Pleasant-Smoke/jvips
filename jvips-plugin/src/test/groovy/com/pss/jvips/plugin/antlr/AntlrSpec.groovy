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

package com.pss.jvips.plugin.antlr

import com.pss.jvips.plugin.antlr.csource.MacroType
import com.pss.jvips.plugin.model.xml.types.Direction
import com.pss.jvips.plugin.naming.ImmutableJavaCaseFormat
import com.pss.jvips.plugin.naming.JavaCaseFormat
import com.pss.jvips.plugin.service.constants.ConstantsService
import com.pss.jvips.plugin.service.executables.ExecutableDTO
import com.pss.jvips.plugin.service.executables.ImmutableExecutableDTO
import com.pss.jvips.plugin.service.executables.arguments.ArgumentDTO
import com.pss.jvips.plugin.service.executables.arguments.ImmutableEarlyStageArgumentDTO
import com.pss.jvips.plugin.service.executables.arguments.ImmutableOptionalArgumentDTO
import com.pss.jvips.plugin.service.naming.method.MethodAndParameterNamingService
import com.pss.jvips.plugin.service.types.TypeMappingService
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.TypeName
import spock.lang.Shared
import spock.lang.Specification

class AntlrSpec extends Specification {

//    @Shared
//    static String text = """Optional arguments:
//
//* @shrink: %gint, shrink by this much on load
//* @fail_on: #VipsFailOn, types of read error to fail on
//* @autorotate: %gboolean, rotate image upright during load
//
//Read a JPEG file into a VIPS image. It can read most 8-bit JPEG images,
//including CMYK and YCbCr.
//
//@shrink means shrink by this integer factor during load.  Possible values
//are 1, 2, 4 and 8. Shrinking during read is very much faster than
//decompressing the whole image and then shrinking later.
//
//Use @fail_on to set the type of error that will cause load to fail. By
//default, loaders are permissive, that is, #VIPS_FAIL_ON_NONE.
//
//Setting @autorotate to %TRUE will make the loader interpret the
//orientation tag and automatically rotate the image appropriately during
//load.
//
//If @autorotate is %FALSE, the metadata field #VIPS_META_ORIENTATION is set
//to the value of the orientation tag. Applications may read and interpret
//this field
//as they wish later in processing. See vips_autorot(). Save
//operations will use #VIPS_META_ORIENTATION, if present, to set the
//orientation of output images.
//
//Example:
//
//|[
//vips_jpegload( "fred.jpg", &amp;amp;out,
//\t"shrink", 8,
//\t"fail_on", VIPS_FAIL_ON_TRUNCATED,
//\tNULL );
//]|
//
//Any embedded ICC profiles are ignored: you always just get the RGB from
//the file. Instead, the embedded profile will be attached to the image as
//#VIPS_META_ICC_NAME. You need to use something like
//vips_icc_import() to get CIE values from the file.
//
//EXIF metadata is attached as #VIPS_META_EXIF_NAME, IPTC as
//#VIPS_META_IPTC_NAME, and XMP as #VIPS_META_XMP_NAME.
//
//The int metadata item "jpeg-multiscan" is set to the result of
//jpeg_has_multiple_scans(). Interlaced jpeg images need a large amount of
//memory to load, so this field gives callers a chance to handle these
//images differently.
//
//The string-valued field "jpeg-chroma-subsample" gives the chroma subsample
//in standard notation. 4:4:4 means no subsample, 4:2:0 means YCbCr with
//Cb and Cr subsampled horizontally and vertically, 4:4:4:4 means a CMYK
//image with no subsampling.
//
//The EXIF thumbnail, if present, is attached to the image as
//"jpeg-thumbnail-data". See vips_image_get_blob().
//
//See also: vips_jpegload_buffer(), vips_image_new_from_file(), vips_autorot().
//"""
//    @Shared
//    static String result = """Read a JPEG file into a VIPS image. It can read most 8-bit JPEG images,
//including CMYK and YCbCr.
//
//{@link VipsJpegLoadParams#shrink} means shrink by this integer factor during load.  Possible values
//are 1, 2, 4 and 8. Shrinking during read is very much faster than
//decompressing the whole image and then shrinking later.
//
//Use {@link VipsJpegLoadParams#failOn} to set the type of error that will cause load to fail. By
//default, loaders are permissive, that is, {@link VipsFailOn#FAIL_ON_NONE}.
//
//Setting {@link VipsJpegLoadParams#autorotate} to `true` will make the loader interpret the
//orientation tag and automatically rotate the image appropriately during
//load.
//
//If {@link VipsJpegLoadParams#autorotate} is `false`, the metadata {@see VipsConstants#VIPS_META_ORIENTATION} field is set
//to the value of the orientation tag. Applications may read and interpret
//this field
//as they wish later in processing. See {@link VipsOperations#autorot}. Save
//operations will use {@see VipsConstants#VIPS_META_ORIENTATION}, if present, to set the
//orientation of output images.
//
//.Example:
//[source,c]
//----
//vips_jpegload( "fred.jpg", &amp;out,
//\t"shrink", 8,
//\t"fail_on", VIPS_FAIL_ON_TRUNCATED,
//\tNULL );
//----
//
//Any embedded ICC profiles are ignored: you always just get the RGB from
//the file. Instead, the embedded profile will be attached to the image as
//. You need to use something like
//{@link VipsOperations#iccImport} to get CIE values from the file.
//
//EXIF metadata is attached as , IPTC as
//, and XMP as .
//
//The int metadata item "jpeg-multiscan" is set to the result of
//{@link VipsOperations#jpegHasMultipleScans}. Interlaced jpeg images need a large amount of
//memory to load, so this field gives callers a chance to handle these
//images differently.
//
//The string-valued field "jpeg-chroma-subsample" gives the chroma subsample
//in standard notation. 4:4:4 means no subsample, 4:2:0 means YCbCr with
//Cb and Cr subsampled horizontally and vertically, 4:4:4:4 means a CMYK
//image with no subsampling.
//
//The EXIF thumbnail, if present, is attached to the image as
//"jpeg-thumbnail-data". See {@link VipsOperations#getBlob}.
//
//@see VipsOperations#jpegLoadBuffer
//@see VipsOperations#newFromFile
//@see VipsOperations#autorot
//
//@param filename file to load
//
//"""


    @Shared
    static String text = """Optional arguments:

* @shrink: %gint, shrink by this much on load
* @fail_on: #VipsFailOn, types of read error to fail on
* @autorotate: %gboolean, rotate image upright during load

Read a JPEG file into a VIPS image. It can read most 8-bit JPEG images,
including CMYK and YCbCr.

@shrink means shrink by this integer factor during load.  Possible values
are 1, 2, 4 and 8. Shrinking during read is very much faster than
decompressing the whole image and then shrinking later.
"""
    @Shared
    static String result =
"""\
Read a JPEG file into a VIPS image. It can read most 8-bit JPEG images, including CMYK and YCbCr.

{@link com.pss.jvips.VipsJpegLoadParams#shrink} means shrink by this integer factor during load. 
Possible values are 1, 2, 4 and 8. Shrinking during read is very much faster than decompressing the
whole image and then shrinking later."""

    def "Check Parser Can Parse"(){
        given:
        MethodAndParameterNamingService namingService = Stub(MethodAndParameterNamingService)
        ConstantsService constantsService = Stub(ConstantsService)
        TypeMappingService mappingService = new TypeMappingService()
        ParserFactoryImpl factory = new ParserFactoryImpl(mappingService, namingService, constantsService)
        ArgumentDTO dto1 = ImmutableEarlyStageArgumentDTO.builder()
            .name("any")
        .description("any")
        .direction(Direction.IN)
        .macroType(MacroType.INT)
        .type(TypeName.INT)
        .formattedName(ImmutableJavaCaseFormat.builder().nativeName("shrink").javaName("shrink").build())
        .isDeprecated(false)
        .isImage(false)
        .isRequired(false)
        .label("").build();
        ExecutableDTO dto = ImmutableExecutableDTO.builder().name(
                ImmutableJavaCaseFormat.builder().javaName("jpegLoad").nativeName("vips_jpegload").build())

                .nameTokens (["jpeg", "load"]).returnType(TypeName.INT)
                .hasOptionalParameters(true)
                .hasDtoOptionalParameter(true)
                .hasSingularOptionalParameter(false)
                .dtoClassName(ClassName.get("com.pss.jvips", "VipsJpegLoadParams"))
                .addAllOptionalExecutableArguments([
                        ImmutableOptionalArgumentDTO.builder()
                                .argumentDTO(dto1)
                                .className(ClassName.get("com.pss.jvips", "VipsJpegLoadParams"))
                        .build()
                ])
                .build();
        when:
        println('Testing')
        def resultBlock = factory.getMethodDoc(dto, text).documentation().toString()
        then:
        assert resultBlock == result

    }
}
