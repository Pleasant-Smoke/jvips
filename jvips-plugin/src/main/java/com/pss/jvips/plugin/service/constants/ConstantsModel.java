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

package com.pss.jvips.plugin.service.constants;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.pss.jvips.plugin.constants.VipsConstant;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeSpec;

import java.util.HashMap;
import java.util.Map;

public class ConstantsModel {

    protected final ListMultimap<ClassName, VipsConstant> constantTypeMap = ArrayListMultimap.create();

    protected final Map<ClassName, TypeSpec.Builder> deprecations = new HashMap<>();

    public ListMultimap<ClassName, VipsConstant> getConstantTypeMap() {
        return constantTypeMap;
    }

    public Map<ClassName, TypeSpec.Builder> getDeprecations() {
        return deprecations;
    }
}
