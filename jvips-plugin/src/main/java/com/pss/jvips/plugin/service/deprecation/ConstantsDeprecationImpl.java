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

package com.pss.jvips.plugin.service.deprecation;

import com.google.common.base.Preconditions;
import com.google.common.collect.Table;
import com.pss.jvips.plugin.constants.ImmutableVipsConstant;
import com.pss.jvips.plugin.constants.VipsConstant;
import com.pss.jvips.plugin.model.xml.toplevel.Constant;
import com.pss.jvips.plugin.overrides.Deprecations;
import com.pss.jvips.plugin.overrides.VipsOverrides;
import com.pss.jvips.plugin.service.constants.Sameness;
import com.pss.jvips.plugin.service.introspection.VersionedLoaderDelegate;
import com.pss.jvips.plugin.service.types.TypeMappingService;
import com.pss.jvips.plugin.util.DeprecationAction;
import com.pss.jvips.plugin.util.DeprecationType;
import com.pss.jvips.plugin.util.Revision;
import com.pss.jvips.plugin.util.Utils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.pss.jvips.plugin.util.DeprecationType.NATIVE_RENAME;
import static com.pss.jvips.plugin.util.Revision.*;

public class ConstantsDeprecationImpl implements ConstantsDeprecation {

    protected final TypeMappingService typeMappingService;
    protected final VersionedLoaderDelegate loader;

    public ConstantsDeprecationImpl(TypeMappingService typeMappingService, VersionedLoaderDelegate overrides) {
        this.typeMappingService = typeMappingService;
        this.loader = overrides;
    }

    @Override
    public Optional<VipsConstant> handle(Table<String, Revision, Sameness> values, Constant value, String nativeName,
                                         Sameness targetTarget, Sameness targetPrevious,
                                         Sameness previousTarget, Sameness previousPrevious) {
        var overrides = loader.loadOverrides().target();
        Map<DeprecationType, Deprecations> deprecations = overrides.getDeprecations().stream()
                .filter(x -> x.getNativeSymbol().equals(nativeName))
                .collect(Collectors.toMap(Deprecations::getType, x -> x));
        if(deprecations.containsKey(NATIVE_RENAME) && deprecations.get(NATIVE_RENAME).getAction() == DeprecationAction.IGNORE){
            checkArgument(targetTarget == null, "targetTarget should be null");
            return Optional.empty();
        }

        ImmutableVipsConstant.Builder builder = ImmutableVipsConstant.builder()
                .name(targetTarget.name())
                .className(targetTarget.className())
                .documentation(value.documentation().orElse(null))
                .value(value.getValue())
                .path(value.getSourcePosition().getFilename())
                .type(typeMappingService.getType(value.getType().getName()).target());

        if(deprecations.values().stream().allMatch(x-> x.getAction() == DeprecationAction.REMOVE)){
            return Optional.of(builder.build());
        }
        ImmutableSymbolDeprecation.Builder symbolDeprecation = ImmutableSymbolDeprecation.builder();
        for(var deprecation : deprecations.values()) {

            Sameness previous = switch (deprecation.getPrevious()) {
                case TARGET_TARGET -> throw new RuntimeException();
                case TARGET_PREVIOUS -> targetPrevious;
                case PREVIOUS_TARGET -> previousTarget;
                case PREVIOUS_PREVIOUS -> previousPrevious;
            };

            switch (deprecation.getType()) {

                case NATIVE_RENAME -> {
                    var prev = deprecation.getPrevious();
                    checkArgument(prev == PREVIOUS_PREVIOUS || prev == PREVIOUS_TARGET);
                    checkArgument(previousTarget == null && previousPrevious == null);
                    Utils.requireNotNullOrEmpty(deprecation.getPreviousNativeSymbol());
                    Map<Revision, Sameness> row = values.row(deprecation.getPreviousNativeSymbol());
                    checkArgument(!row.containsKey(TARGET_TARGET) && !row.containsKey(TARGET_PREVIOUS));
                    Sameness sameness = Objects.requireNonNull(row.get(deprecation.getPrevious()));

                }
                case NATIVE_LOCATION_CHANGE -> {
                    checkArgument(previous != null);
                    symbolDeprecation
                            .relocated(previous.className());
                }
                case NATIVE_METHOD_CHANGE -> {
                    throw new RuntimeException();
                }
                case GENERATOR_LOCATION_CHANGE -> {
                    checkArgument(previous != null);
                   symbolDeprecation
                            .relocated(previous.className());
                }
                case GENERATOR_NAMING_CHANGE -> {
                    checkArgument(previous != null);

                    symbolDeprecation
                            .rename(previous.name());
                }
                case GENERATOR_METHOD_CHANGE -> {
                    throw new RuntimeException();
                }
            }
        }

        return Optional.of(builder.deprecation(symbolDeprecation.build()).build());
    }


}
