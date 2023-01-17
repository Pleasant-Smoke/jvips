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

package com.pss.jvips.panama;

import org.junit.jupiter.api.Test;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;

import static java.lang.foreign.MemoryAddress.NULL;
import static org.libvips.LibVips.C_POINTER;
import static org.libvips.LibVips.*;

public class PanamaExample {

    @Test
    public void example() {
        try (var scope = MemorySession.openShared()) {
            MemorySegment file = scope.allocateUtf8String("no46.jpeg");
            MemoryAddress imageAddress = vips_image_new_from_file(file, NULL);
            MemorySegment imageOut = scope.allocate(C_POINTER);
            int i = vips_resize(imageAddress, imageOut, .5, NULL);
            if (i == -1) {

                return;
            }
            MemoryAddress address = imageOut.get(C_POINTER, 0);
            var fileOut = scope.allocateUtf8String("no46-25.jpeg");
            int i1 = vips_jpegsave(address, fileOut, NULL);
        }
    }
}
