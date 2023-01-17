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

import org.semver4j.Semver;

public enum Version {

    LATEST("0.0.1-SNAPSHOT","8.13.0", "8.13.3");

    private final Semver javaVersion;
    private final Semver vipsVersionLow;
    private final Semver vipsVersionHigh;

    Version(String javaVersion, String vipsVersionLow, String vipsVersionHigh) {
        this.javaVersion = Semver.parse(javaVersion);
        this.vipsVersionLow = Semver.parse(vipsVersionLow);
        this.vipsVersionHigh = Semver.parse(vipsVersionHigh);
    }


}
