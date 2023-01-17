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

package com.pss.jvips.plugin.service.constants;

import com.google.common.base.MoreObjects;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;
import com.pss.jvips.plugin.constants.ImmutableVipsConstant;
import com.pss.jvips.plugin.constants.VipsConstant;
import com.pss.jvips.plugin.model.xml.Namespace;
import com.pss.jvips.plugin.model.xml.toplevel.Constant;
import com.pss.jvips.plugin.naming.JavaCaseFormat;
import com.pss.jvips.plugin.service.deprecation.ConstantsDeprecation;
import com.pss.jvips.plugin.service.naming.constants.ConstantsClassHandlerDelegate;
import com.pss.jvips.plugin.service.naming.constants.ConstantsRegistrationDelegate;
import com.pss.jvips.plugin.service.types.TypeMappingService;
import com.pss.jvips.plugin.util.History;
import com.pss.jvips.plugin.util.Revision;
import com.squareup.javapoet.ClassName;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ConstantsServiceImpl implements ConstantsService {

    protected final Map<String, VipsConstant> constants = new HashMap<>();

    protected final TypeMappingService typeMappingService;
    protected final ConstantsClassHandlerDelegate classHandler;
    protected final ConstantsRegistrationDelegate registration;
    protected final ConstantsDeprecation deprecation;


    public ConstantsServiceImpl(TypeMappingService typeMappingService,
                                ConstantsClassHandlerDelegate classHandler,
                                ConstantsRegistrationDelegate registration, ConstantsDeprecation deprecation) {
        this.typeMappingService = typeMappingService;
        this.classHandler = classHandler;
        this.registration = registration;
        this.deprecation = deprecation;
    }

    @Override
    public void registerConstants(History<Namespace> namespace){
        Table<String, Revision, Sameness> values = HashBasedTable.create();


        for (Constant constant : namespace.target().getConstants()) {
            History<ClassName> className = classHandler.getClassName(constant.getSourcePosition().getFilename());
            History<JavaCaseFormat> caseFormat = registration.registerConstant(constant.getIdentifier());


            var target =  ImmutableSameness.builder()
                    .className(className.target())
                    .name(caseFormat.target())
                    .value(constant.getValue())
                    .constant(constant)
                    .build();

            var previous = ImmutableSameness.builder().from(target)
                    .className(className.previous())
                    .name(caseFormat.previous())
                    .build();

            values.put(caseFormat.target().getNativeName(), Revision.TARGET_TARGET, target);
            values.put(caseFormat.previous().getNativeName(), Revision.TARGET_PREVIOUS, previous);

            if(namespace.same()){
                values.put(caseFormat.target().getNativeName(), Revision.PREVIOUS_TARGET, target);
                values.put(caseFormat.previous().getNativeName(), Revision.PREVIOUS_PREVIOUS, previous);
            }
        }



        if(!namespace.same()){


            for (Constant constant : namespace.previous().getConstants()) {
                History<ClassName> className = classHandler.getClassName(constant.getSourcePosition().getFilename());
                History<JavaCaseFormat> caseFormat = registration.registerConstant(constant.getIdentifier());

                var target =  ImmutableSameness.builder().className(className.target()).name(caseFormat.target()).value(constant.getValue()).constant(constant).build();
                var previous = ImmutableSameness.builder().className(className.previous()).name(caseFormat.previous()).value(constant.getValue()).constant(constant).build();

                values.put(caseFormat.target().getNativeName(), Revision.PREVIOUS_TARGET, target);
                values.put(caseFormat.previous().getNativeName(), Revision.PREVIOUS_PREVIOUS, previous);
            }
        }
        List<VipsConstant> vipsConstants = new ArrayList<>();
        for (Map.Entry<String, Map<Revision, Sameness>> revisionsEntry : values.rowMap().entrySet()) {
            String nativeName = revisionsEntry.getKey();
            var samenessEntries = revisionsEntry.getValue();
            @Nullable Sameness targetTarget = samenessEntries.get(Revision.TARGET_TARGET);
            @Nullable Sameness targetPrevious = samenessEntries.get(Revision.TARGET_PREVIOUS);
            @Nullable Sameness previousTarget = samenessEntries.get(Revision.PREVIOUS_TARGET);
            @Nullable Sameness previousPrevious = samenessEntries.get(Revision.PREVIOUS_PREVIOUS);

            if(targetTarget != null
                    && Objects.equals(targetTarget, targetPrevious)
                    && Objects.equals(targetPrevious, previousTarget)
                    && Objects.equals(previousTarget, previousPrevious)){

                VipsConstant builder = ImmutableVipsConstant.builder()
                        .name(targetTarget.name())
                        .className(targetTarget.className())
                        .documentation(targetTarget.constant().documentation().orElse(null))
                        .value(targetTarget.value())
                        .path(targetTarget.constant().getSourcePosition().getFilename())
                        .type(typeMappingService.getType(targetTarget.constant().getType().getName()).target()).build();
                vipsConstants.add(builder);
            } else {
                Sameness any = MoreObjects.firstNonNull(targetTarget, previousTarget);
                deprecation.handle(values, any.constant(), nativeName, targetTarget, targetPrevious, previousTarget, previousPrevious)
                            .ifPresent(x-> {
                                vipsConstants.add(x);
                            });

            }
        }
        vipsConstants.forEach(x-> {
            constants.put(x.name().getNativeName(), x);
        });
    }

    @Override
    public ConstantsModel getConstantsModel(){
        ConstantsModel constantsModel = new ConstantsModel();
        constants.values().forEach(x-> constantsModel.getConstantTypeMap().put(x.className(), x));
        return constantsModel;
    }

    @Override
    public Optional<VipsConstant> lookupConstant(String name){
        return Optional.ofNullable(constants.get(name));
    }

}
