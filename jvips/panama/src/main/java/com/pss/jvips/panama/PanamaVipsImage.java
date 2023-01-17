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

import com.pss.jvips.common.image.JVipsImage;
import static org.libvips.VipsImage.*;

import java.lang.foreign.MemorySegment;

public class PanamaVipsImage implements JVipsImage<MemorySegment> {

    private final MemorySegment segment;
    private final long id;

    public PanamaVipsImage(MemorySegment segment, long id) {
        this.segment = segment;
        this.id = id;
    }

    @Override
    public MemorySegment getAddress() {
        return segment;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public int getXSize() {
        return Xsize$get(segment);
    }

    @Override
    public int getYSize() {
        return Ysize$get(segment);
    }

    @Override
    public int getBands() {
        return Bands$get(segment);
    }

    @Override
    public int getBandFmt() {
        return BandFmt$get(segment);
    }

    @Override
    public int getCoding() {
        return Coding$get(segment);
    }

    @Override
    public int getType() {
        return Type$get(segment);
    }

    @Override
    public double getXRes() {
        return Xres$get(segment);
    }

    @Override
    public double getYRes() {
        return Yres$get(segment);
    }

    @Override
    public int getXOffset() {
        return Xoffset$get(segment);
    }

    @Override
    public int getYOffset() {
        return Yoffset$get(segment);
    }

    @Override
    public int getLength() {
        return Length$get(segment);
    }

    @Override
    public short getCompression() {
        return Compression$get(segment);
    }

    @Override
    public short getLevel() {
        return Level$get(segment);
    }
}
