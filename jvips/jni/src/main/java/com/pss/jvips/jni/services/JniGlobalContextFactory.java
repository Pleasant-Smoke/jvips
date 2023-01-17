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

package com.pss.jvips.jni.services;

import com.google.auto.service.AutoService;
import com.pss.jvips.common.cleaner.VipsCleaner;
import com.pss.jvips.common.context.VipsGlobalContextFactory;
import com.pss.jvips.common.context.VipsOperationContext;
import com.pss.jvips.common.context.VipsOperationContextFactory;
import com.pss.jvips.jni.config.VipsJNIConfig;
import jvips.VipsNative;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ServiceLoader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@AutoService(VipsGlobalContextFactory.class)
public class JniGlobalContextFactory implements VipsGlobalContextFactory<VipsJNIConfig, Long, Void> {

    private static final Logger log = LoggerFactory.getLogger(JniGlobalContextFactory.class);

    private volatile VipsOperationContextFactory<Long, Void> instance;
    private VipsCleaner<ByteBuffer> cleaner;
    private ExecutorService executorService;

    @Override
    public VipsOperationContextFactory<Long, Void> create(VipsJNIConfig args) {
        if(instance == null){
            synchronized (JniGlobalContextFactory.class){
                if(instance == null){
                    try {
                        load("jvips_jni");
                    } catch (IOException e) {
                        throw new RuntimeException("Could not load vips_jni", e);
                    }
                    VipsNative.init(args.getName(), args.getLogger(), args.getLevel(), args.isUnsafe());
                    instance = ServiceLoader.load(VipsOperationContextFactory.class).findFirst().orElseThrow();
                    cleaner = ServiceLoader.load(VipsCleaner.class).findFirst().orElseThrow();
                    executorService = Executors.newSingleThreadExecutor(r -> new Thread(r, "vips-cleaner"));
                    executorService.submit(cleaner);
                    Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
                }
            }
        }
        return instance;
    }

    @Override
    public void shutdown() {
        cleaner.shutdown();
        executorService.shutdown();
    }

    private static void load(String name) throws IOException {
        try {
            System.loadLibrary(name);
        } catch (Throwable t){
            String libName = System.mapLibraryName(name);
            File temp;
            try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(libName)) {
                if (in == null) {
                    log.error("Could not load lib '{}' via classloader", libName);
                    throw new RuntimeException("Could not load lib '" + libName + "' via classloader");
                }
                byte[] buffer = new byte[1024];
                int read;
                temp = File.createTempFile(libName, "");
                temp.deleteOnExit();
                try (FileOutputStream fos = new FileOutputStream(temp)) {
                    while ((read = in.read(buffer)) != -1) {
                        fos.write(buffer, 0, read);
                    }
                }
            }
            System.load(temp.getAbsolutePath());
        }
    }
}
