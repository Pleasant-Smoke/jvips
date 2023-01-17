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

package com.pss.jvips.plugin.antlr.csource;

import com.pss.jvips.plugin.service.executables.arguments.EarlyStageArgumentDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * Visited Code Block
 *
 * Represents visiting a source code method example:
 *
 * [source,C]
 * ----
 * static void
 * vips_reduce_class_init( VipsReduceClass *class )
 * {
 * 	GObjectClass *gobject_class = G_OBJECT_CLASS( class );
 * 	VipsObjectClass *vobject_class = VIPS_OBJECT_CLASS( class );
 * 	VipsOperationClass *operation_class = VIPS_OPERATION_CLASS( class );
 *
 * 	VIPS_DEBUG_MSG( "vips_reduce_class_init\n" );
 *
 * 	gobject_class->set_property = vips_object_set_property;
 * 	gobject_class->get_property = vips_object_get_property;
 *
 * 	vobject_class->nickname = "reduce"; <1>
 * 	vobject_class->description = _( "reduce an image" ); <2>
 * 	vobject_class->build = vips_reduce_build;
 *
 * 	operation_class->flags = VIPS_OPERATION_SEQUENTIAL;
 *
 * 	VIPS_ARG_DOUBLE( class, "hshrink", 8, <3>
 * 		_( "Hshrink" ),
 * 		_( "Horizontal shrink factor" ),
 * 		VIPS_ARGUMENT_REQUIRED_INPUT,
 * 		G_STRUCT_OFFSET( VipsReduce, hshrink ),
 * 		1.0, 1000000.0, 1.0 );
 *
 * 	VIPS_ARG_DOUBLE( class, "vshrink", 9, <3>
 * 		_( "Vshrink" ),
 * 		_( "Vertical shrink factor" ),
 * 		VIPS_ARGUMENT_REQUIRED_INPUT,
 * 		G_STRUCT_OFFSET( VipsReduce, vshrink ),
 * 		1.0, 1000000.0, 1.0 );
 *
 * 	VIPS_ARG_ENUM( class, "kernel", 3, <3>
 * 		_( "Kernel" ),
 * 		_( "Resampling kernel" ),
 * 		VIPS_ARGUMENT_OPTIONAL_INPUT,
 * 		G_STRUCT_OFFSET( VipsReduce, kernel ),
 * 		VIPS_TYPE_KERNEL, VIPS_KERNEL_LANCZOS3 );
 *
 * 	VIPS_ARG_DOUBLE( class, "gap", 4, <3>
 * 		_( "Gap" ),
 * 		_( "Reducing gap" ),
 * 		VIPS_ARGUMENT_OPTIONAL_INPUT,
 * 		G_STRUCT_OFFSET( VipsReduce, gap ),
 * 		0.0, 1000000.0, 0.0 );
 * ----
 * <1> The nickname `nickname`, if this ends in `_base` then it all the other Visited code blocks in the same file will
 * inherit the arguments
 * <2> The Description `description`
 * <3> Example of arguments `arguments`
 *
 * WARNING: arguments List implementation must be mutable!
 */
public class VisitedCodeBlock {

    public static final VisitedCodeBlock NULL_BLOCK = new VisitedCodeBlock("", "", Map.of());

    private final String nickname;
    private final String description;
    // MUST BE MUTABLE
    private final Map<String, EarlyStageArgumentDTO> arguments;

    public VisitedCodeBlock(String nickname, String description,
                     Map<String, EarlyStageArgumentDTO> arguments){
        this.nickname = nickname;
        this.description = description;
        this.arguments = new HashMap<>(arguments);
    }

    public String nickname(){
        return nickname;
    }

    public String description(){
        return description;
    }

    public Map<String, EarlyStageArgumentDTO> arguments(){
        return arguments;
    }
}
