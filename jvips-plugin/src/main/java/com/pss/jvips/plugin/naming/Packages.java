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

package com.pss.jvips.plugin.naming;

public class Packages {

    public static final String commonBase = "com.pss.jvips.common";



    public static final String foreign = "java.lang.foreign";

    public static final String util = commonBase + ".util";








    public static class Common {

        public static final String base = "com.pss.jvips.common";
        public static final String serialization = base + ".serialization";
        public static final String annotation = base + ".annotations";
        public static final String util = base + ".util";
        public static final String context = base + ".context";
        public static final String impl = base + ".impl";
        public static final String image = base + ".image";
        public static final String data = base + ".data";
        public static final String api = base + ".api";

        public static class Generated implements GeneratedPackages {
            public static final String base = Common.base + ".generated";

            public static final String enumerations = base + ".types";

            public static final String result = base + ".result";

            public static final String function = base + ".function";

            public static final String service = base + ".service";

            public static final String callbacks = base + ".callback";
            public static final String constants = base + ".constants";

            public static final String arguments = base + ".args";

            public static final String bits = base + ".bits";

            public static final String factory = base + ".factory";

            public static final String messages = base + ".messages";

            // Records
            public static final String dto = base + ".dto";

            @Override
            public String arguments(){
                return arguments;
            }

            @Override
            public String result(){
                return result;
            }



        }


    }

    public static class Jni {
        public static final String base = "com.pss.jvips.jni";
        public static final String jni =  "jvips";

        public static class Generated implements GeneratedPackages {
            public static final String base = Jni.base + ".generated";

            public static final String enumerations = base + ".types";

            public static final String result = base + ".result";

            public static final String function = base + ".function";

            public static final String service = base + ".service";

            public static final String callbacks = base + ".callback";

            public static final String arguments = base + ".args";

            @Override
            public String arguments(){
                return arguments;
            }

            @Override
            public String result(){
                return result;
            }

        }
    }


    public static class Panama {

        public static final String base = "com.pss.jvips.panama";
        public static final String vips = "org.libvips";

        public static class Generated implements GeneratedPackages {

            public static final String base = Panama.base + ".generated";

            public static final String enumerations = base + ".types";

            public static final String result = base + ".result";

            public static final String function = base + ".function";

            public static final String service = base + ".service";

            public static final String callbacks = base + ".callback";

            public static final String arguments = base + ".args";

            @Override
            public String arguments(){
                return arguments;
            }

            @Override
            public String result(){
                return result;
            }

        }
    }
}
