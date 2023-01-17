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

package com.pss.jvips.plugin.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Can't deal with JaxB not handling prefixes without giving a headache
 *
 * Filters all `c:*` namespaced attributes in an XML file with `c-*`
 */
public class NameSpaceFilteringStream extends InputStream {

    private static final int C = 'c';

    private static final int G = 'g';
    private static final int L = 'l';
    private static final int I = 'i';
    private static final int B = 'b';

    private static final int COLON = ':';

    private static final int DASH = '-';

    private final int[] chars = new int[]{'\0', '\0', '\0', '\0'};

    private final InputStream in;

    public NameSpaceFilteringStream(InputStream in) {
       this.in = in;
    }

    @Override
    public int read() throws IOException {
        int read = in.read();
        if((read == COLON && chars[3] == C)){
            read = DASH;
        } else if(read == COLON
                && chars[0] == G
                && chars[1] == L
                && chars[2] == I
                && chars[3] == B){
            read = DASH;
        }

        chars[0] = chars[1];
        chars[1] = chars[2];
        chars[2] = chars[3];
        chars[3] = read;

        return read;
    }

}
