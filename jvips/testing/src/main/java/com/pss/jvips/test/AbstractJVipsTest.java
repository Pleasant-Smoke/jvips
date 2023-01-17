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

package com.pss.jvips.test;

import com.pss.jvips.common.context.*;
import org.assertj.core.data.Offset;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This is easier with Spock but oh well
 */
public abstract class AbstractJVipsTest<Arguments extends VipsConfig, DataType, UnderlyingSession> {


    protected abstract List<String> commands();
    protected final InputStream command() throws IOException {

        ProcessBuilder pb = new ProcessBuilder("vips")
                .command(commands())
                .command(".png");
        Process start = pb.start();
        InputStream in = start.getInputStream();
        return in;
    }

    protected abstract Arguments getConfig();

    public void test() throws IOException {
        VipsOperationContextFactory<DataType, UnderlyingSession> factory = VipsGlobalContextFactory.Singleton.create(getConfig());

        try(VipsOperationContext<DataType, UnderlyingSession> context = factory.create()){
            ByteBuffer image = getImage(context);
            image.flip();
            compareImages(command(), new ByteBufferInputStream(image));
        } catch (VipsException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract ByteBuffer getImage(VipsOperationContext<DataType, UnderlyingSession> context);

    public void compareImages(InputStream in1, InputStream in2) throws IOException {
        BufferedImage img1 = ImageIO.read(in1);
        BufferedImage img2 = ImageIO.read(in2);
        assertThat(img1.getWidth()).as("Widths must be equal").isEqualTo(img2.getWidth());
        assertThat(img1.getHeight()).as("Heights must be equal").isEqualTo(img2.getHeight());
        int[] pix1 = img1.getRGB(0, 0, img1.getWidth(), img1.getHeight(), null, 0, img1.getWidth());
        int[] pix2 = img2.getRGB(0, 0, img1.getWidth(), img1.getHeight(), null, 0, img1.getWidth());
        double difference = 0;
        ColorModel cm = img1.getColorModel();
        for(int i = 0; i < pix1.length; i++){
            int p1 = pix1[i];
            int p2 = pix2[i];
            double a = Math.abs(cm.getAlpha(p1) - cm.getAlpha(p2));
            double r = Math.abs(cm.getRed(p1) - cm.getRed(p2));
            double g = Math.abs(cm.getGreen(p1) - cm.getGreen(p2));
            double b = Math.abs(cm.getBlue(p1) - cm.getBlue(p2));
            double res = (a + r + g + b);
            if(res != 0.0){
                difference += (res / 4.0);
            }
        }

        double result = difference / pix1.length;
        assertThat(result).isCloseTo(0, Offset.offset(5.0));
    }

    static class ByteBufferInputStream extends InputStream {

        private final ByteBuffer bb;

        ByteBufferInputStream(ByteBuffer bb) {
            this.bb = bb;
        }

        @Override
        public int read() throws IOException {
            if(bb.hasRemaining()){
                return bb.get();
            }
            return -1;
        }
    }
}
