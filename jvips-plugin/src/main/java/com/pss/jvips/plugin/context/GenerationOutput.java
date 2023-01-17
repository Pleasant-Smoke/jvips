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

package com.pss.jvips.plugin.context;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public abstract class GenerationOutput<T> {



    public abstract T write(JavaFile file) throws IOException;


    public static GenerationOutput<Path> forPath(Path path){
        return new PathOutput(path);
    }

    public static GenerationOutput<StringBuilder> forString(){
        return new StringOutput();
    }

    private static class PathOutput extends GenerationOutput<Path> {
        protected final Path path;

        private PathOutput(Path path) {
            this.path = path;
        }

        @Override
        public Path write(JavaFile file) throws IOException {
            return file.writeToPath(path, StandardCharsets.UTF_8);
        }
    }

    private static class StringOutput extends GenerationOutput<StringBuilder> {

        protected StringBuilder sb = new StringBuilder(512);

        @Override
        public StringBuilder write(JavaFile file) throws IOException {
            StringBuilder sb = new StringBuilder(512);
            file.writeTo(sb);
            return sb;
        }
    }
}
