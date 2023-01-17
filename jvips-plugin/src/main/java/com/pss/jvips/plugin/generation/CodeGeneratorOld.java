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

package com.pss.jvips.plugin.generation;

import com.pss.jvips.plugin.context.GlobalPluginContext;
import com.pss.jvips.plugin.context.OperationContext;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.nio.file.Path;

import static java.lang.String.format;

public abstract class CodeGeneratorOld<A, R> {

    protected final GlobalPluginContext context;
    protected final OperationContext operationContext;
    protected final Path output;

    public CodeGeneratorOld(Path output, GlobalPluginContext context, OperationContext operationContext) {
        this.context = context;
        this.operationContext = operationContext;
        this.output = output;
    }


    public final  R run(A arguments) {
        var type = getTypeSpec(arguments);
        Result<R> result = runInternal(type, arguments);
        if(context.operationContext() == operationContext){
            Path path = write(type, result.className());
            customizeWrite(path, arguments);
        }
        return result.result();
    }



    protected abstract Result<R> runInternal(final TypeSpec.Builder typeSpec, A arguments);



    protected TypeSpec.Builder getTypeSpec(A arguments) {
        throw new UnsupportedOperationException();
    }

    protected void customize(JavaFile.Builder customize) {

    }

    protected Path write(TypeSpec.Builder typeSpec, ClassName className) {
        try {
            JavaFile.Builder builder = JavaFile.builder(className.packageName(), typeSpec.build());
            customize(builder);
            JavaFile file = builder.build();
            return file.writeToPath(this.output);
        } catch (IOException e) {
            throw new RuntimeException(format("Error writing %s", className), e);
        }
    }

    protected void customizeWrite(Path path, A arguments){

    }

    public record Result<R>(R result, ClassName className){

    }
}
