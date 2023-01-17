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

package com.pss.jvips.common.context;

import com.pss.jvips.common.util.Conditions;

public class VipsConfig {

    private static final int processors = Runtime.getRuntime().availableProcessors();

    protected int threadPoolMinimum = 1 | processors - 1;

    protected int threadPoolMaximum = processors * 2;

    public int getThreadPoolMinimum() {
        return threadPoolMinimum;
    }

    public VipsConfig setThreadPoolMinimum(int threadPoolMinimum) {
        Conditions.requireUnchecked(threadPoolMinimum > 0,
                "Thread Pool Minimum must be greater than 0, received %s", threadPoolMinimum);
        this.threadPoolMinimum = threadPoolMinimum;
        return this;
    }

    public int getThreadPoolMaximum() {
        return threadPoolMaximum;
    }

    public VipsConfig setThreadPoolMaximum(int threadPoolMaximum) {
        Conditions.requireUnchecked(threadPoolMaximum > 0,
                "Thread Pool Maximum must be greater than 0, received %s", threadPoolMaximum);
        this.threadPoolMaximum = threadPoolMaximum;
        return this;
    }
}
