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

package com.pss.jvips.plugin.actions;

import com.pss.jvips.plugin.actions.visitor.MemberRegistrationVisitor;
import com.pss.jvips.plugin.actions.visitor.TypeRegistrationVisitor;

import com.pss.jvips.plugin.antlr.csource.ArgumentCommonMapper;
import com.pss.jvips.plugin.constants.VipsConstant;
import com.pss.jvips.plugin.generation.impl.ConstantGeneratorOld;
import com.pss.jvips.plugin.generation.impl.EnumCodeGenerator;
import com.pss.jvips.plugin.generation.impl.InterpolateGenerator;
import com.pss.jvips.plugin.generation.impl.dto.AbstractArgumentDTO;
import com.pss.jvips.plugin.generation.impl.dto.BaseArgumentDTO;
import com.pss.jvips.plugin.generation.impl.dto.JniArgumentDTO;
import com.pss.jvips.plugin.generation.impl.dto.PanamaArgumentDTO;
import com.pss.jvips.plugin.generation.impl.dto.factory.DTOFactoryServiceInterface;
import com.pss.jvips.plugin.generation.impl.dto.factory.JniDTOFactoryServiceInterface;
import com.pss.jvips.plugin.generation.impl.operations.GenerateAbstractOperations;
import com.pss.jvips.plugin.generation.impl.operations.GenerateJniOperations;
import com.pss.jvips.plugin.generation.impl.operations.GeneratePanamaOperations;
import com.pss.jvips.plugin.generation.impl.operations.OperationGenerator;

import com.pss.jvips.plugin.generation.impl.dto.factory.DTOTuple;

import com.pss.jvips.plugin.antlr.ParserFactoryOld;
import com.pss.jvips.plugin.antlr.csource.VisitedCodeBlock;
import com.pss.jvips.plugin.model.xml.executable.AbstractExecutable;
import com.pss.jvips.plugin.model.xml.executable.Constructor;
import com.pss.jvips.plugin.model.xml.toplevel.*;
import com.pss.jvips.plugin.model.xml.toplevel.Enumeration;
import com.pss.jvips.plugin.model.xml.Member;
import com.pss.jvips.plugin.model.xml.Namespace;
import com.pss.jvips.plugin.service.introspection.VersionedLoaderImpl;
import com.pss.jvips.plugin.util.MethodOptionalParametersDocumentation;
import com.pss.jvips.plugin.context.GlobalPluginContext;
import com.pss.jvips.plugin.context.OperationContext;
import com.pss.jvips.plugin.context.ScopedPluginContext;
import com.squareup.javapoet.*;
import org.apache.commons.collections4.CollectionUtils;
import org.gradle.api.Action;
import org.gradle.api.Task;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;

import static com.pss.jvips.plugin.naming.JavaTypeMapping.*;
import static java.lang.String.format;

/**
 * Vips save methods, also inherit from foreign.c
 */
@Deprecated
public class GenerateVipsSourcesAction implements Action<Task> {

    private static final Logger log = LoggerFactory.getLogger(GenerateVipsSourcesAction.class);

    //@todo: libvips/resample/interpolate.c has the interpolate types

    protected EnumCodeGenerator enumCodeGenerator;
    protected InterpolateGenerator interpolateGenerator;
    protected GenerateJniOperations generateJniImplementation;
    protected GenerateAbstractOperations generateAbstractOperations;
    protected GeneratePanamaOperations generatePanamaOperations;

    protected ConstantGeneratorOld constantGenerator;

    protected BaseArgumentDTO baseArgumentDTO;
    protected JniArgumentDTO jniArgumentDTO;
    protected PanamaArgumentDTO panamaArgumentDTO;

    private final GlobalPluginContext context;

    public GenerateVipsSourcesAction(GlobalPluginContext context) {
        this.context = context;
    }




    
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void execute(Task task) {
        final Path path = task.getProject().getBuildDir().toPath();
        this.enumCodeGenerator = new EnumCodeGenerator(path, context);
        this.interpolateGenerator = new InterpolateGenerator(path, context);
        this.generateJniImplementation = new GenerateJniOperations(path, context);
        this.generateAbstractOperations = new GenerateAbstractOperations(path, context);
        this.generatePanamaOperations = new GeneratePanamaOperations(path, context);
        this.jniArgumentDTO = new JniArgumentDTO(path, context);
        this.baseArgumentDTO = new BaseArgumentDTO(path, context);
        this.panamaArgumentDTO = new PanamaArgumentDTO(path, context);
        this.constantGenerator = new ConstantGeneratorOld(path, context);

        this.interpolateGenerator.run(null);
        Namespace namespace = VersionedLoaderImpl.loadXml();
        ExecutableRegistration executableRegistration = stageOneRegisterTypes(namespace);

        List<MethodOptionalParametersDocumentation> earlyStageResults = stageTwoGetEarlyStageOptionalParameters(executableRegistration);

        stageThreeRegisterBitFields(task, namespace);

        // For deduping optional dto
        Map<Set<String>, List<MethodOptionalParametersDocumentation>> dtoParams = new HashMap<>();

        Map<UUID, MethodOptionalParametersDocumentation> singularParam = new HashMap<>();

        //@todo: Java Doc needs a lookup for the new arg DTOs

        for (Enumeration enumeration : namespace.getEnums()) {
            this.enumCodeGenerator.run(enumeration);
        }

        if(context.operationContext() == OperationContext.COMMON){
            for (Map.Entry<String, List<VipsConstant>> entry : context._constants().entrySet()) {
                constantGenerator.run(new ConstantGeneratorOld.Constant(entry.getKey(), entry.getValue()));
            }
        }



        for (AbstractExecutable executable : executableRegistration.getExecutables()) {
            MethodOptionalParametersDocumentation optionalParametersDocumentation = executable.getOptionalParametersDocumentation();
            if(optionalParametersDocumentation != null && !optionalParametersDocumentation.getParameterNames().isEmpty()){
                String name = optionalParametersDocumentation.getExecutable().getName();

                Optional<VisitedCodeBlock> codeBlocks = optionalParametersDocumentation.getExecutable()
                            .filename()
                            .map(x-> ParserFactoryOld.loadCSource(x, name, context));

                codeBlocks.ifPresentOrElse(x-> {
                    if(!CollectionUtils.containsAll(x.arguments().keySet(), optionalParametersDocumentation.getParameterNames())){
                        Set<String> csource = new HashSet<>(x.arguments().keySet());
                        Set<String> girsource = new HashSet<>(optionalParametersDocumentation.getParameterNames());
                        girsource.removeAll(csource);
                        log.warn(" CSource parser did not catch all names for {}, girsource also contains: {}", name, girsource);
                    }
                    optionalParametersDocumentation.setSourceCodeVisited(ArgumentCommonMapper.instance.visitedBlock(x));
                }, ()->{
                    log.warn("Return from CSource parser was blank for {}, found: {}", name, optionalParametersDocumentation.getParameterNames());
                });




                if(optionalParametersDocumentation.getOptionalParameters().size() == 1){
                    singularParam.put(optionalParametersDocumentation.getExecutable().getUuid(), optionalParametersDocumentation);
                } else {
                    dtoParams.computeIfAbsent(optionalParametersDocumentation.getParameterNames(), (t)-> new ArrayList<>())
                            .add(optionalParametersDocumentation);
                }
            }
        }



        Map<UUID, ClassName> methodToDtoName = new HashMap<>();
        Map<String, DTOTuple> names = new HashMap<>();

        Map<TypeName, ClassName> factoryNames = new HashMap<>();
        switch (context.operationContext()){

            case COMMON -> {
                for (Map.Entry<Set<String>, List<MethodOptionalParametersDocumentation>> entry : dtoParams.entrySet()) {

                    baseArgumentDTO.run(new AbstractArgumentDTO.Args(names, entry.getValue()));

                }
                Map<String, MethodSpec> run = new DTOFactoryServiceInterface(task.getProject().getBuildDir().toPath(), context).run(names);
            }
            case JNI -> {
                for (Map.Entry<Set<String>, List<MethodOptionalParametersDocumentation>> entry : dtoParams.entrySet()) {
                    jniArgumentDTO.run(new AbstractArgumentDTO.Args(names, entry.getValue()));
                }
                new JniDTOFactoryServiceInterface(task.getProject().getBuildDir().toPath(), context).run(factoryNames);
            }

            case PANAMA -> {
                for (Map.Entry<Set<String>, List<MethodOptionalParametersDocumentation>> entry : dtoParams.entrySet()) {
                    panamaArgumentDTO.run(new AbstractArgumentDTO.Args(names, entry.getValue()));
                }
            }
        }









        context.singularOptionalParams().putAll(singularParam);
        context.dtoOptionalParameters().putAll(methodToDtoName);


        for (Callback callback : namespace.getCallbacks()) {

            TypeSpec.Builder builder = TypeSpec.interfaceBuilder(callback.getClassName())
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(FunctionalInterface.class);
           // addExecutables(builder, callback, "apply");
            if(context.operationContext() == OperationContext.COMMON) {
               // write(callback.getClassName(), builder, task);
            }
        }



        for (NativeRecord record : namespace.getRecords()) {
            if(!TypeRegistrationVisitor.EXCLUDED_RECORDS.contains(record.getName())) {
                TypeSpec.Builder typeSpec = TypeSpec.classBuilder(record.getClassName()).addModifiers(Modifier.PUBLIC);
                if(context.operationContext() == OperationContext.COMMON) {
                   // write(record.getClassName(), typeSpec, task);
                }
            }
        }

        List<AbstractExecutable> executables = new ArrayList<>();
//        for (NativeClass nativeClass : namespace.getClazzes()) {
//            if(nativeClass.getName().equals("Interpolate")){
//
//            } else if(!nativeClass.getName().equals("Image")) {
//
//                TypeSpec.Builder typeSpec = TypeSpec.classBuilder(nativeClass.getClassName()).addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT);
//                for (AbstractExecutable executableType : nativeClass.getExecutables()) {
//                   // addExecutables(typeSpec, executableType, null);
//                }
//                nativeClass.parent().map(name -> getType(name)).ifPresent(type -> {
//                    typeSpec.superclass(type);
//                });
//                if(context.operationContext() == OperationContext.COMMON) {
//                   // write(nativeClass.getClassName(), typeSpec, task);
//                }
//
//                //generateFactory(nativeClass, task);
//
//            } else {
//                executables.addAll(nativeClass.getExecutables());
//            }
//        }



        OperationGenerator.OperationGeneratorContext operationGeneratorContext = new OperationGenerator.OperationGeneratorContext(executableRegistration.getExecutables());
        switch (context.operationContext()){
            case COMMON -> generateAbstractOperations.run(operationGeneratorContext);
            case JNI -> generateJniImplementation.run(operationGeneratorContext);
            case PANAMA -> generatePanamaOperations.run(operationGeneratorContext);
        }





    }

    private  void stageThreeRegisterBitFields(Task task, Namespace namespace) {
        for (BitField bitField : namespace.getBitFields()) {
            TypeSpec.Builder builder = TypeSpec.classBuilder(bitField.getClassName())
                    .addModifiers(Modifier.PUBLIC);
            
            for (Member member : bitField.getMembers()) {
                FieldSpec field = FieldSpec.builder(TypeName.LONG, member.getIdentifier())
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                        .initializer(CodeBlock.of("$L", member.getValue()))
                        .build();
                builder.addField(field);
            }
            if(context.operationContext() == OperationContext.COMMON) {
                write(bitField.getClassName(), builder, task);
            }
        }
    }

    @NotNull
    private  List<MethodOptionalParametersDocumentation> stageTwoGetEarlyStageOptionalParameters(ExecutableRegistration namespace) {
        List<MethodOptionalParametersDocumentation> earlyStageParameters = new ArrayList<>();

        for (AbstractExecutable declaration : namespace.getExecutables()) {

                declaration.documentation().flatMap(x-> ParserFactoryOld.optionalParams(x, new ScopedPluginContext(context, declaration, VipsOperations_class))).ifPresent(es-> {
                    declaration.setOptionalParametersDocumentation(es);
                    earlyStageParameters.add(es);
                });

        }
        return earlyStageParameters;
    }

    private static void generateFactory(ClassLike cl, Task task){
        if(!cl.getConstructors().isEmpty()) {
            TypeSpec.Builder factory = TypeSpec.classBuilder(cl.getFactoryClassName());
            factory.addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT);
            for (Constructor constructor : cl.getConstructors()) {
                //addExecutables(factory, constructor, null);
            }
            write(cl.getFactoryClassName(), factory, task);
        }
    }


    private static Path write(ClassName className, TypeSpec.Builder typeSpec, Task task) {
        return write(className, typeSpec, task, (b)-> {});
    }

    private static Path write(ClassName className, TypeSpec.Builder typeSpec, Task task, Consumer<JavaFile.Builder> customizer) {
        try {

             JavaFile.Builder builder = JavaFile.builder(className.packageName(), typeSpec.build());
                    customizer.accept(builder);
                    return builder.build().writeToPath(task.getProject().getBuildDir().toPath().resolve("generated/src/main/java"));
        } catch (IOException e) {
            throw new RuntimeException(format("Error writing %s", className), e);
        }
    }









    /**
     * Register all the types globally
     *
     * @param namespace
     * @return
     */
    ExecutableRegistration stageOneRegisterTypes(Namespace namespace){
        new TypeRegistrationVisitor(namespace, context).loop();
        var reg = new MemberRegistrationVisitor(namespace, new ExecutableRegistration(context));
        reg.loop();
        return reg.getContext();
    }

    public static class ExecutableRegistration {

        private final Map<String, List<AbstractExecutable>> fileMapping = new HashMap<>();

        private final List<AbstractExecutable> executables = new ArrayList<>();

        private final GlobalPluginContext context;

        public ExecutableRegistration(GlobalPluginContext context) {
            this.context = context;
        }

        public GlobalPluginContext getContext() {
            return context;
        }

        public void addExecutable(AbstractExecutable executable){
            executables.add(executable);
        }

        public List<AbstractExecutable> getExecutables() {
            return executables;
        }

        public void putFileMapping(String file, AbstractExecutable executable){
            fileMapping.computeIfAbsent(file, (f)-> new ArrayList<>()).add(executable);
        }

        public Map<String, List<AbstractExecutable>> getFileMapping() {
            return fileMapping;
        }
    }
    
}
