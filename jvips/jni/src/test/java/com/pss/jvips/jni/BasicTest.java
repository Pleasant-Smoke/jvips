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

package com.pss.jvips.jni;

import com.pss.jvips.common.context.VipsException;
import com.pss.jvips.common.context.VipsGlobalContextFactory;
import com.pss.jvips.common.context.VipsOperationContext;
import com.pss.jvips.common.context.VipsOperationContextFactory;
import com.pss.jvips.common.generated.OptionalParameterFactory;
import com.pss.jvips.common.generated.args.VipsResizeParams;
import com.pss.jvips.common.generated.types.VipsKernel;
import com.pss.jvips.common.image.JVipsImage;
import com.pss.jvips.jni.config.VipsJNIConfig;
import com.pss.jvips.jni.generated.JniVipsOperations;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

public class BasicTest {

    @SuppressWarnings("unchecked")
    @Test
    public void testLoadFile() throws VipsException {
        VipsOperationContextFactory<Long, Void> contextFactory = VipsGlobalContextFactory.Singleton.create(new VipsJNIConfig().setUnsafe(true).setLevel(3));
        try(VipsOperationContext<Long, Void> context = contextFactory.create()){
            JniVipsOperations ops = new JniVipsOperations(context);
            JVipsImage<Long> image = ops.newImageFromFile(Paths.get("../testing/test-image-1.jpg").toAbsolutePath().toString());

            assert image != null;
            assert image.getXSize() == 5000;

            VipsResizeParams<Long, Void> vipsResizeParams = OptionalParameterFactory.INSTANCE.forResize()
                    .setKernel(VipsKernel.VIPS_KERNEL_CUBIC);

            JVipsImage<Long> image2 = ops.resize(image, .5, vipsResizeParams);
            assert image2.getXSize() == 2500;
        }

    }

}
