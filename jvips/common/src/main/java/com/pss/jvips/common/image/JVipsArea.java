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

package com.pss.jvips.common.image;

import com.pss.jvips.common.api.GType;
import com.pss.jvips.common.impl.Addressable;

import java.nio.ByteBuffer;

public interface JVipsArea<T> extends Addressable<T> {

    ByteBuffer getData();

    /* 0 if not known */
    long length();

    /* If this area represents an array, the number of elements in the
     * array. Equal to length / sizeof(element).
     */
    long n();

    /* If we are holding an array (for example, an array of double), the
     * GType of the elements and their size. 0 for not known.
     *
     * n is always length / sizeof_type, we keep it as a member for
     * convenience.
     */
    GType type();

    long sizeOfType();
}
