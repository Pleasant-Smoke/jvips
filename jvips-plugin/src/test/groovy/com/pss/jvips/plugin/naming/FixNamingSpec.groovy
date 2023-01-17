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

package com.pss.jvips.plugin.naming


import spock.lang.Specification

class FixNamingSpec extends Specification {

    def "Fix casing on save or load methods"(){
        when:
        String name = 'jp2ksaveBuffer'
        def format = JavaNaming.methodName(name)
        then:
        format.javaName == "jp2kSaveBuffer"
    }

    def "Check Method Name Tokenization"(String input, List<String> expected){
        expect:
        MethodNameTokenizerOld.tokenizeCurrent(input) == expected

        where:
        input                   | expected
        "matrixload_source"     | ["matrix", "load", "source"]
        "jp2ksave_buffer"       | ["jp2k", "save", "buffer"]
        "notequal_const"        | ["not", "equal", "constant"]
        "labelregions"          | ["label", "regions"]
        "bandbool"              | ["band", "boolean"]
        "orimage"               | ["or", "image"]
        "eorimage"              | ["xor", "image"]
        "andimage_const"        | ["and", "image", "constant"]
        "andimage_const1"       | ["and", "image", "constant"]
        "bandjoin_const1"       | ["band", "join", "constant"]
        "moreeq"                | ["more", "equal"]
        "lesseq"                | ["less", "equal"]
        "lesseq_const"          | ["less", "equal", "constant"]
        "tilecache"             | ["tile", "cache"]
        "rshift"                | ["right", "shift"]
        "lshift"                | ["left", "shift"]
        "lshift_const1"         | ["left", "shift", "constant"]
        "hist_ismonotonic"      | ["histogram", "is", "monotonic"]
        "getpoint"              | ["get", "point"]
        "ifthenelse"            | ["if", "then", "else"]
        "gaussblur"             | ["gaussian", "blur"]
        "gaussnoise"            | ["gaussian", "noise"]
        "reduceh"               | ["reduce", "horizontal"]
        "reducev"               | ["reduce", "vertical"]
        "shrink"                | ["shrink"]
        "shrinkh"               | ["shrink", "horizontal"]
        "shrinkv"               | ["shrink", "vertical"]
    }
}
