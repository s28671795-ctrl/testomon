/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.metadata.provider;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.AnnotatedArrayType;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.groups.ConvertGroup;
import org.hibernate.validator.group.GroupSequenceProvider;
import org.hibernate.validator.internal.engine.ConstraintCreationContext;
import org.hibernate.validator.internal.engine.valueextraction.ArrayElement;
import org.hibernate.validator.internal.metadata.aggregated.CascadingMetaDataBuilder;
import org.hibernate.validator.internal.metadata.core.AnnotationProcessingOptions;
import org.hibernate.validator.internal.metadata.core.AnnotationProcessingOptionsImpl;
import org.hibernate.validator.internal.metadata.core.MetaConstraint;
import org.hibernate.validator.internal.metadata.core.MetaConstraints;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.metadata.provider.MetaDataProvider;
import org.hibernate.validator.internal.metadata.raw.BeanConfiguration;
import org.hibernate.validator.internal.metadata.raw.ConfigurationSource;
import org.hibernate.validator.internal.metadata.raw.ConstrainedElement;
import org.hibernate.validator.internal.metadata.raw.ConstrainedExecutable;
import org.hibernate.validator.internal.metadata.raw.ConstrainedField;
import org.hibernate.validator.internal.metadata.raw.ConstrainedParameter;
import org.hibernate.validator.internal.metadata.raw.ConstrainedType;
import org.hibernate.validator.internal.properties.Callable;
import org.hibernate.validator.internal.properties.Constrainable;
import org.hibernate.validator.internal.properties.javabean.JavaBeanAnnotatedConstrainable;
import org.hibernate.validator.internal.properties.javabean.JavaBeanAnnotatedElement;
import org.hibernate.validator.internal.properties.javabean.JavaBeanExecutable;
import org.hibernate.validator.internal.properties.javabean.JavaBeanField;
import org.hibernate.validator.internal.properties.javabean.JavaBeanHelper;
import org.hibernate.validator.internal.properties.javabean.JavaBeanParameter;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.ReflectionHelper;
import org.hibernate.validator.internal.util.annotation.ConstraintAnnotationDescriptor;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.util.logging.Messages;
import org.hibernate.validator.internal.util.privilegedactions.GetDeclaredConstructors;
import org.hibernate.validator.internal.util.privilegedactions.GetDeclaredFields;
import org.hibernate.validator.internal.util.privilegedactions.GetDeclaredMethods;
import org.hibernate.validator.internal.util.privilegedactions.GetMethods;
import org.hibernate.validator.internal.util.privilegedactions.NewInstance;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

public class AnnotationMetaDataProvider
implements MetaDataProvider {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    private final ConstraintCreationContext constraintCreationContext;
    private final AnnotationProcessingOptions annotationProcessingOptions;
    private final JavaBeanHelper javaBeanHelper;
    private final BeanConfiguration<Object> objectBeanConfiguration;

    public AnnotationMetaDataProvider(ConstraintCreationContext constraintCreationContext, JavaBeanHelper javaBeanHelper, AnnotationProcessingOptions annotationProcessingOptions) {
        this.constraintCreationContext = constraintCreationContext;
        this.javaBeanHelper = javaBeanHelper;
        this.annotationProcessingOptions = annotationProcessingOptions;
        this.objectBeanConfiguration = this.retrieveBeanConfiguration(Object.class);
    }

    @Override
    public AnnotationProcessingOptions getAnnotationProcessingOptions() {
        return new AnnotationProcessingOptionsImpl();
    }

    public <T> BeanConfiguration<T> getBeanConfiguration(Class<T> beanClass) {
        if (Object.class.equals(beanClass)) {
            return this.objectBeanConfiguration;
        }
        return this.retrieveBeanConfiguration(beanClass);
    }

    private <T> BeanConfiguration<T> retrieveBeanConfiguration(Class<T> beanClass) {
        Set<ConstrainedElement> constrainedElements = this.getFieldMetaData(beanClass);
        constrainedElements.addAll(this.getMethodMetaData(beanClass));
        constrainedElements.addAll(this.getConstructorMetaData(beanClass));
        Set<MetaConstraint<?>> classLevelConstraints = this.getClassLevelConstraints(beanClass);
        if (!classLevelConstraints.isEmpty()) {
            ConstrainedType classLevelMetaData = new ConstrainedType(ConfigurationSource.ANNOTATION, beanClass, classLevelConstraints);
            constrainedElements.add(classLevelMetaData);
        }
        return new BeanConfiguration<T>(ConfigurationSource.ANNOTATION, beanClass, constrainedElements, this.getDefaultGroupSequence(beanClass), this.getDefaultGroupSequenceProvider(beanClass));
    }

    private List<Class<?>> getDefaultGroupSequence(Class<?> beanClass) {
        GroupSequence groupSequenceAnnotation = beanClass.getAnnotation(GroupSequence.class);
        return groupSequenceAnnotation != null ? Arrays.asList(groupSequenceAnnotation.value()) : null;
    }

    private <T> DefaultGroupSequenceProvider<? super T> getDefaultGroupSequenceProvider(Class<T> beanClass) {
        GroupSequenceProvider groupSequenceProviderAnnotation = beanClass.getAnnotation(GroupSequenceProvider.class);
        if (groupSequenceProviderAnnotation != null) {
            Class<? extends DefaultGroupSequenceProvider<?>> providerClass = groupSequenceProviderAnnotation.value();
            return this.newGroupSequenceProviderClassInstance(beanClass, providerClass);
        }
        return null;
    }

    private <T> DefaultGroupSequenceProvider<? super T> newGroupSequenceProviderClassInstance(Class<T> beanClass, Class<? extends DefaultGroupSequenceProvider<? super T>> providerClass) {
        Method[] providerMethods;
        for (Method method2 : providerMethods = this.run(GetMethods.action(providerClass))) {
            Class<?>[] paramTypes = method2.getParameterTypes();
            if (!"getValidationGroups".equals(method2.getName()) || method2.isBridge() || paramTypes.length != 1 || !paramTypes[0].isAssignableFrom(beanClass)) continue;
            return this.run(NewInstance.action(providerClass, "the default group sequence provider"));
        }
        throw LOG.getWrongDefaultGroupSequenceProviderTypeException(beanClass);
    }

    private Set<MetaConstraint<?>> getClassLevelConstraints(Class<?> clazz) {
        if (this.annotationProcessingOptions.areClassLevelConstraintsIgnoredFor(clazz)) {
            return Collections.emptySet();
        }
        List<ConstraintDescriptorImpl<?>> classLevelConstraintDescriptors = this.findConstraints(null, clazz.getDeclaredAnnotations(), ConstraintLocation.ConstraintLocationKind.TYPE);
        if (classLevelConstraintDescriptors.isEmpty()) {
            return Collections.emptySet();
        }
        HashSet<MetaConstraint<?>> classLevelConstraints = CollectionHelper.newHashSet(classLevelConstraintDescriptors.size());
        ConstraintLocation location = ConstraintLocation.forClass(clazz);
        for (ConstraintDescriptorImpl<?> constraintDescriptor : classLevelConstraintDescriptors) {
            classLevelConstraints.add(MetaConstraints.create(this.constraintCreationContext.getTypeResolutionHelper(), this.constraintCreationContext.getValueExtractorManager(), this.constraintCreationContext.getConstraintValidatorManager(), constraintDescriptor, location));
        }
        return classLevelConstraints;
    }

    private Set<ConstrainedElement> getFieldMetaData(Class<?> beanClass) {
        HashSet<ConstrainedElement> propertyMetaData = CollectionHelper.newHashSet();
        for (Field field2 : this.run(GetDeclaredFields.action(beanClass))) {
            JavaBeanField javaBeanField;
            if (Modifier.isStatic(field2.getModifiers()) || field2.isSynthetic() || this.annotationProcessingOptions.areMemberConstraintsIgnoredFor(javaBeanField = this.javaBeanHelper.field(field2))) continue;
            propertyMetaData.add(this.findPropertyMetaData(javaBeanField));
        }
        return propertyMetaData;
    }

    private ConstrainedField findPropertyMetaData(JavaBeanField javaBeanField) {
        Set<MetaConstraint<?>> constraints = this.convertToMetaConstraints(this.findConstraints(javaBeanField, ConstraintLocation.ConstraintLocationKind.FIELD), javaBeanField);
        CascadingMetaDataBuilder cascadingMetaDataBuilder = this.findCascadingMetaData(javaBeanField);
        Set<MetaConstraint<?>> typeArgumentsConstraints = this.findTypeAnnotationConstraints(javaBeanField);
        return new ConstrainedField(ConfigurationSource.ANNOTATION, javaBeanField, constraints, typeArgumentsConstraints, cascadingMetaDataBuilder);
    }

    private Set<MetaConstraint<?>> convertToMetaConstraints(List<ConstraintDescriptorImpl<?>> constraintDescriptors, JavaBeanField javaBeanField) {
        if (constraintDescriptors.isEmpty()) {
            return Collections.emptySet();
        }
        HashSet<MetaConstraint<?>> constraints = CollectionHelper.newHashSet(constraintDescriptors.size());
        ConstraintLocation location = ConstraintLocation.forField(javaBeanField);
        for (ConstraintDescriptorImpl<?> constraintDescription : constraintDescriptors) {
            constraints.add(MetaConstraints.create(this.constraintCreationContext.getTypeResolutionHelper(), this.constraintCreationContext.getValueExtractorManager(), this.constraintCreationContext.getConstraintValidatorManager(), constraintDescription, location));
        }
        return constraints;
    }

    private Set<ConstrainedExecutable> getConstructorMetaData(Class<?> clazz) {
        Executable[] declaredConstructors = this.run(GetDeclaredConstructors.action(clazz));
        return this.getMetaData(declaredConstructors);
    }

    private Set<ConstrainedExecutable> getMethodMetaData(Class<?> clazz) {
        Executable[] declaredMethods = this.run(GetDeclaredMethods.action(clazz));
        return this.getMetaData(declaredMethods);
    }

    private Set<ConstrainedExecutable> getMetaData(Executable[] executableElements) {
        HashSet<ConstrainedExecutable> executableMetaData = CollectionHelper.newHashSet();
        for (Executable executable : executableElements) {
            if (Modifier.isStatic(executable.getModifiers()) || executable.isSynthetic()) continue;
            executableMetaData.add(this.findExecutableMetaData(executable));
        }
        return executableMetaData;
    }

    private ConstrainedExecutable findExecutableMetaData(Executable executable) {
        CascadingMetaDataBuilder cascadingMetaDataBuilder;
        Set<MetaConstraint<?>> typeArgumentsConstraints;
        Set<MetaConstraint<?>> returnValueConstraints;
        JavaBeanExecutable<?> javaBeanExecutable = this.javaBeanHelper.executable(executable);
        List<ConstrainedParameter> parameterConstraints = this.getParameterMetaData(javaBeanExecutable);
        Map<ConstraintDescriptorImpl.ConstraintType, List<ConstraintDescriptorImpl>> executableConstraints = this.findConstraints(javaBeanExecutable, ConstraintLocation.ConstraintLocationKind.of(javaBeanExecutable.getConstrainedElementKind())).stream().collect(Collectors.groupingBy(ConstraintDescriptorImpl::getConstraintType));
        Set<Object> crossParameterConstraints = this.annotationProcessingOptions.areCrossParameterConstraintsIgnoredFor(javaBeanExecutable) ? Collections.emptySet() : this.convertToMetaConstraints(executableConstraints.get((Object)ConstraintDescriptorImpl.ConstraintType.CROSS_PARAMETER), javaBeanExecutable);
        if (this.annotationProcessingOptions.areReturnValueConstraintsIgnoredFor(javaBeanExecutable)) {
            returnValueConstraints = Collections.emptySet();
            typeArgumentsConstraints = Collections.emptySet();
            cascadingMetaDataBuilder = CascadingMetaDataBuilder.nonCascading();
        } else {
            typeArgumentsConstraints = this.findTypeAnnotationConstraints(javaBeanExecutable);
            returnValueConstraints = this.convertToMetaConstraints(executableConstraints.get((Object)ConstraintDescriptorImpl.ConstraintType.GENERIC), javaBeanExecutable);
            cascadingMetaDataBuilder = this.findCascadingMetaData(javaBeanExecutable);
        }
        return new ConstrainedExecutable(ConfigurationSource.ANNOTATION, javaBeanExecutable, parameterConstraints, crossParameterConstraints, returnValueConstraints, typeArgumentsConstraints, cascadingMetaDataBuilder);
    }

    private Set<MetaConstraint<?>> convertToMetaConstraints(List<ConstraintDescriptorImpl<?>> constraintDescriptors, Callable callable) {
        if (constraintDescriptors == null || constraintDescriptors.isEmpty()) {
            return Collections.emptySet();
        }
        HashSet<MetaConstraint<?>> constraints = CollectionHelper.newHashSet(constraintDescriptors.size());
        ConstraintLocation returnValueLocation = ConstraintLocation.forReturnValue(callable);
        ConstraintLocation crossParameterLocation = ConstraintLocation.forCrossParameter(callable);
        for (ConstraintDescriptorImpl<?> constraintDescriptor : constraintDescriptors) {
            ConstraintLocation location = constraintDescriptor.getConstraintType() == ConstraintDescriptorImpl.ConstraintType.GENERIC ? returnValueLocation : crossParameterLocation;
            constraints.add(MetaConstraints.create(this.constraintCreationContext.getTypeResolutionHelper(), this.constraintCreationContext.getValueExtractorManager(), this.constraintCreationContext.getConstraintValidatorManager(), constraintDescriptor, location));
        }
        return constraints;
    }

    private List<ConstrainedParameter> getParameterMetaData(JavaBeanExecutable<?> javaBeanExecutable) {
        if (!javaBeanExecutable.hasParameters()) {
            return Collections.emptyList();
        }
        List<JavaBeanParameter> parameters = javaBeanExecutable.getParameters();
        ArrayList<ConstrainedParameter> metaData = new ArrayList<ConstrainedParameter>(parameters.size());
        int i = 0;
        for (JavaBeanParameter parameter : parameters) {
            Set<MetaConstraint<?>> parameterConstraints;
            if (this.annotationProcessingOptions.areParameterConstraintsIgnoredFor(javaBeanExecutable, i)) {
                metaData.add(new ConstrainedParameter(ConfigurationSource.ANNOTATION, javaBeanExecutable, parameter.getGenericType(), i, Collections.emptySet(), Collections.emptySet(), CascadingMetaDataBuilder.nonCascading()));
                ++i;
                continue;
            }
            List<ConstraintDescriptorImpl<?>> constraintDescriptors = this.findConstraints(javaBeanExecutable, parameter, ConstraintLocation.ConstraintLocationKind.PARAMETER);
            if (!constraintDescriptors.isEmpty()) {
                parameterConstraints = CollectionHelper.newHashSet(constraintDescriptors.size());
                ConstraintLocation location = ConstraintLocation.forParameter(javaBeanExecutable, i);
                for (ConstraintDescriptorImpl<?> constraintDescriptorImpl : constraintDescriptors) {
                    parameterConstraints.add(MetaConstraints.create(this.constraintCreationContext.getTypeResolutionHelper(), this.constraintCreationContext.getValueExtractorManager(), this.constraintCreationContext.getConstraintValidatorManager(), constraintDescriptorImpl, location));
                }
            } else {
                parameterConstraints = Collections.emptySet();
            }
            Set<MetaConstraint<?>> typeArgumentsConstraints = this.findTypeAnnotationConstraintsForExecutableParameter(javaBeanExecutable, parameter);
            CascadingMetaDataBuilder cascadingMetaData = this.findCascadingMetaData(parameter);
            metaData.add(new ConstrainedParameter(ConfigurationSource.ANNOTATION, javaBeanExecutable, parameter.getGenericType(), i, parameterConstraints, typeArgumentsConstraints, cascadingMetaData));
            ++i;
        }
        return metaData;
    }

    private List<ConstraintDescriptorImpl<?>> findConstraints(JavaBeanAnnotatedConstrainable constrainable, ConstraintLocation.ConstraintLocationKind kind) {
        return this.findConstraints((Constrainable)constrainable, constrainable, kind);
    }

    private List<ConstraintDescriptorImpl<?>> findConstraints(Constrainable constrainable, JavaBeanAnnotatedElement annotatedElement, ConstraintLocation.ConstraintLocationKind kind) {
        ArrayList<ConstraintDescriptorImpl<?>> metaData = CollectionHelper.newArrayList();
        for (Annotation annotation : annotatedElement.getDeclaredAnnotations()) {
            metaData.addAll(this.findConstraintAnnotations(constrainable, annotation, kind));
        }
        return metaData;
    }

    private List<ConstraintDescriptorImpl<?>> findConstraints(Constrainable constrainable, Annotation[] annotations, ConstraintLocation.ConstraintLocationKind kind) {
        if (annotations.length == 0) {
            return Collections.emptyList();
        }
        ArrayList<ConstraintDescriptorImpl<?>> metaData = CollectionHelper.newArrayList();
        for (Annotation annotation : annotations) {
            metaData.addAll(this.findConstraintAnnotations(constrainable, annotation, kind));
        }
        return metaData;
    }

    protected <A extends Annotation> List<ConstraintDescriptorImpl<?>> findConstraintAnnotations(Constrainable constrainable, A annotation, ConstraintLocation.ConstraintLocationKind type) {
        if (this.constraintCreationContext.getConstraintHelper().isJdkAnnotation(annotation.annotationType())) {
            return Collections.emptyList();
        }
        ArrayList constraints = CollectionHelper.newArrayList();
        Class<? extends Annotation> annotationType = annotation.annotationType();
        if (this.constraintCreationContext.getConstraintHelper().isConstraintAnnotation(annotationType)) {
            constraints.add(annotation);
        } else if (this.constraintCreationContext.getConstraintHelper().isMultiValueConstraint(annotationType)) {
            constraints.addAll(this.constraintCreationContext.getConstraintHelper().getConstraintsFromMultiValueConstraint(annotation));
        }
        return constraints.stream().map(c -> this.buildConstraintDescriptor(constrainable, c, type)).collect(Collectors.toList());
    }

    private Map<Class<?>, Class<?>> getGroupConversions(AnnotatedType annotatedType) {
        return this.getGroupConversions(annotatedType.getAnnotation(ConvertGroup.class), annotatedType.getAnnotation(ConvertGroup.List.class));
    }

    private Map<Class<?>, Class<?>> getGroupConversions(ConvertGroup groupConversion, ConvertGroup.List groupConversionList) {
        if (groupConversion == null && (groupConversionList == null || groupConversionList.value().length == 0)) {
            return Collections.emptyMap();
        }
        HashMap<Class<?>, Class<?>> groupConversions = CollectionHelper.newHashMap();
        if (groupConversion != null) {
            groupConversions.put(groupConversion.from(), groupConversion.to());
        }
        if (groupConversionList != null) {
            for (ConvertGroup conversion : groupConversionList.value()) {
                if (groupConversions.containsKey(conversion.from())) {
                    throw LOG.getMultipleGroupConversionsForSameSourceException(conversion.from(), CollectionHelper.asSet((Class)groupConversions.get(conversion.from()), conversion.to()));
                }
                groupConversions.put(conversion.from(), conversion.to());
            }
        }
        return groupConversions;
    }

    private <A extends Annotation> ConstraintDescriptorImpl<A> buildConstraintDescriptor(Constrainable constrainable, A annotation, ConstraintLocation.ConstraintLocationKind type) {
        return new ConstraintDescriptorImpl<A>(this.constraintCreationContext.getConstraintHelper(), constrainable, new ConstraintAnnotationDescriptor<A>(annotation), type);
    }

    private <T> T run(PrivilegedAction<T> action) {
        return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
    }

    protected Set<MetaConstraint<?>> findTypeAnnotationConstraints(JavaBeanField javaBeanField) {
        return this.findTypeArgumentsConstraints(javaBeanField, new TypeArgumentFieldLocation(javaBeanField), javaBeanField.getAnnotatedType());
    }

    protected Set<MetaConstraint<?>> findTypeAnnotationConstraints(JavaBeanExecutable<?> javaBeanExecutable) {
        return this.findTypeArgumentsConstraints(javaBeanExecutable, new TypeArgumentReturnValueLocation(javaBeanExecutable), javaBeanExecutable.getAnnotatedType());
    }

    private CascadingMetaDataBuilder findCascadingMetaData(JavaBeanParameter javaBeanParameter) {
        Map<TypeVariable<?>, CascadingMetaDataBuilder> containerElementTypesCascadingMetaData = this.getTypeParametersCascadingMetadata(javaBeanParameter.getAnnotatedType(), javaBeanParameter.getTypeParameters());
        try {
            return this.getCascadingMetaData(javaBeanParameter, containerElementTypesCascadingMetaData);
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            LOG.warn(Messages.MESSAGES.constraintOnConstructorOfNonStaticInnerClass(), ex);
            return CascadingMetaDataBuilder.nonCascading();
        }
    }

    private CascadingMetaDataBuilder findCascadingMetaData(JavaBeanField javaBeanField) {
        Map<TypeVariable<?>, CascadingMetaDataBuilder> containerElementTypesCascadingMetaData = this.getTypeParametersCascadingMetadata(javaBeanField.getAnnotatedType(), javaBeanField.getTypeParameters());
        return this.getCascadingMetaData(javaBeanField, containerElementTypesCascadingMetaData);
    }

    private CascadingMetaDataBuilder findCascadingMetaData(JavaBeanExecutable<?> javaBeanExecutable) {
        Map<TypeVariable<?>, CascadingMetaDataBuilder> containerElementTypesCascadingMetaData = this.getTypeParametersCascadingMetadata(javaBeanExecutable.getAnnotatedType(), javaBeanExecutable.getTypeParameters());
        return this.getCascadingMetaData(javaBeanExecutable, containerElementTypesCascadingMetaData);
    }

    private Map<TypeVariable<?>, CascadingMetaDataBuilder> getTypeParametersCascadingMetadata(AnnotatedType annotatedType, TypeVariable<?>[] typeParameters) {
        if (annotatedType instanceof AnnotatedArrayType) {
            return this.getTypeParametersCascadingMetaDataForArrayType((AnnotatedArrayType)annotatedType);
        }
        if (annotatedType instanceof AnnotatedParameterizedType) {
            return this.getTypeParametersCascadingMetaDataForParameterizedType((AnnotatedParameterizedType)annotatedType, typeParameters);
        }
        return Collections.emptyMap();
    }

    private Map<TypeVariable<?>, CascadingMetaDataBuilder> getTypeParametersCascadingMetaDataForParameterizedType(AnnotatedParameterizedType annotatedParameterizedType, TypeVariable<?>[] typeParameters) {
        HashMap<TypeVariable<?>, CascadingMetaDataBuilder> typeParametersCascadingMetadata = CollectionHelper.newHashMap(typeParameters.length);
        AnnotatedType[] annotatedTypeArguments = annotatedParameterizedType.getAnnotatedActualTypeArguments();
        int i = 0;
        for (AnnotatedType annotatedTypeArgument : annotatedTypeArguments) {
            Map<TypeVariable<?>, CascadingMetaDataBuilder> nestedTypeParametersCascadingMetadata = this.getTypeParametersCascadingMetaDataForAnnotatedType(annotatedTypeArgument);
            typeParametersCascadingMetadata.put(typeParameters[i], new CascadingMetaDataBuilder(annotatedParameterizedType.getType(), typeParameters[i], annotatedTypeArgument.isAnnotationPresent(Valid.class), nestedTypeParametersCascadingMetadata, this.getGroupConversions(annotatedTypeArgument)));
            ++i;
        }
        return typeParametersCascadingMetadata;
    }

    private Map<TypeVariable<?>, CascadingMetaDataBuilder> getTypeParametersCascadingMetaDataForArrayType(AnnotatedArrayType annotatedArrayType) {
        return Collections.emptyMap();
    }

    private Map<TypeVariable<?>, CascadingMetaDataBuilder> getTypeParametersCascadingMetaDataForAnnotatedType(AnnotatedType annotatedType) {
        if (annotatedType instanceof AnnotatedArrayType) {
            return this.getTypeParametersCascadingMetaDataForArrayType((AnnotatedArrayType)annotatedType);
        }
        if (annotatedType instanceof AnnotatedParameterizedType) {
            return this.getTypeParametersCascadingMetaDataForParameterizedType((AnnotatedParameterizedType)annotatedType, ReflectionHelper.getClassFromType(annotatedType.getType()).getTypeParameters());
        }
        return Collections.emptyMap();
    }

    protected Set<MetaConstraint<?>> findTypeAnnotationConstraintsForExecutableParameter(JavaBeanExecutable<?> javaBeanExecutable, JavaBeanParameter javaBeanParameter) {
        try {
            return this.findTypeArgumentsConstraints(javaBeanExecutable, new TypeArgumentExecutableParameterLocation(javaBeanExecutable, javaBeanParameter.getIndex()), javaBeanParameter.getAnnotatedType());
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            LOG.warn(Messages.MESSAGES.constraintOnConstructorOfNonStaticInnerClass(), ex);
            return Collections.emptySet();
        }
    }

    private Set<MetaConstraint<?>> findTypeArgumentsConstraints(Constrainable constrainable, TypeArgumentLocation location, AnnotatedType annotatedType) {
        if (!(annotatedType instanceof AnnotatedParameterizedType)) {
            return Collections.emptySet();
        }
        HashSet typeArgumentConstraints = new HashSet();
        if (annotatedType instanceof AnnotatedArrayType) {
            AnnotatedArrayType annotatedArrayType = (AnnotatedArrayType)annotatedType;
            Type validatedType = annotatedArrayType.getAnnotatedGenericComponentType().getType();
            ArrayElement arrayElementTypeArgument = new ArrayElement(annotatedArrayType);
            typeArgumentConstraints.addAll(this.findTypeUseConstraints(constrainable, annotatedArrayType, arrayElementTypeArgument, location, validatedType));
            typeArgumentConstraints.addAll(this.findTypeArgumentsConstraints(constrainable, new NestedTypeArgumentLocation(location, arrayElementTypeArgument, validatedType), annotatedArrayType.getAnnotatedGenericComponentType()));
        } else if (annotatedType instanceof AnnotatedParameterizedType) {
            AnnotatedParameterizedType annotatedParameterizedType = (AnnotatedParameterizedType)annotatedType;
            int i = 0;
            for (TypeVariable<Class<?>> typeVariable : ReflectionHelper.getClassFromType(annotatedType.getType()).getTypeParameters()) {
                AnnotatedType annotatedTypeParameter = annotatedParameterizedType.getAnnotatedActualTypeArguments()[i];
                Type validatedType = annotatedTypeParameter.getType();
                typeArgumentConstraints.addAll(this.findTypeUseConstraints(constrainable, annotatedTypeParameter, typeVariable, location, validatedType));
                if (validatedType instanceof ParameterizedType) {
                    typeArgumentConstraints.addAll(this.findTypeArgumentsConstraints(constrainable, new NestedTypeArgumentLocation(location, typeVariable, validatedType), annotatedTypeParameter));
                }
                ++i;
            }
        }
        return typeArgumentConstraints.isEmpty() ? Collections.emptySet() : typeArgumentConstraints;
    }

    private Set<MetaConstraint<?>> findTypeUseConstraints(Constrainable constrainable, AnnotatedType typeArgument, TypeVariable<?> typeVariable, TypeArgumentLocation location, Type type) {
        List<ConstraintDescriptorImpl<?>> constraintDescriptors = this.findConstraints(constrainable, typeArgument.getAnnotations(), ConstraintLocation.ConstraintLocationKind.TYPE_USE);
        if (constraintDescriptors.isEmpty()) {
            return Collections.emptySet();
        }
        HashSet<MetaConstraint<?>> constraints = CollectionHelper.newHashSet(constraintDescriptors.size());
        ConstraintLocation constraintLocation = ConstraintLocation.forTypeArgument(location.toConstraintLocation(), typeVariable, type);
        for (ConstraintDescriptorImpl<?> constraintDescriptor : constraintDescriptors) {
            constraints.add(MetaConstraints.create(this.constraintCreationContext.getTypeResolutionHelper(), this.constraintCreationContext.getValueExtractorManager(), this.constraintCreationContext.getConstraintValidatorManager(), constraintDescriptor, constraintLocation));
        }
        return constraints;
    }

    private CascadingMetaDataBuilder getCascadingMetaData(JavaBeanAnnotatedElement annotatedElement, Map<TypeVariable<?>, CascadingMetaDataBuilder> containerElementTypesCascadingMetaData) {
        return CascadingMetaDataBuilder.annotatedObject(annotatedElement.getType(), annotatedElement.isAnnotationPresent(Valid.class), containerElementTypesCascadingMetaData, this.getGroupConversions(annotatedElement.getAnnotatedType()));
    }

    private static class TypeArgumentFieldLocation
    implements TypeArgumentLocation {
        private final JavaBeanField javaBeanField;

        private TypeArgumentFieldLocation(JavaBeanField javaBeanField) {
            this.javaBeanField = javaBeanField;
        }

        @Override
        public ConstraintLocation toConstraintLocation() {
            return ConstraintLocation.forField(this.javaBeanField);
        }
    }

    private static interface TypeArgumentLocation {
        public ConstraintLocation toConstraintLocation();
    }

    private static class TypeArgumentReturnValueLocation
    implements TypeArgumentLocation {
        private final JavaBeanExecutable<?> javaBeanExecutable;

        private TypeArgumentReturnValueLocation(JavaBeanExecutable<?> javaBeanExecutable) {
            this.javaBeanExecutable = javaBeanExecutable;
        }

        @Override
        public ConstraintLocation toConstraintLocation() {
            return ConstraintLocation.forReturnValue(this.javaBeanExecutable);
        }
    }

    private static class TypeArgumentExecutableParameterLocation
    implements TypeArgumentLocation {
        private final JavaBeanExecutable<?> javaBeanExecutable;
        private final int index;

        private TypeArgumentExecutableParameterLocation(JavaBeanExecutable<?> javaBeanExecutable, int index) {
            this.javaBeanExecutable = javaBeanExecutable;
            this.index = index;
        }

        @Override
        public ConstraintLocation toConstraintLocation() {
            return ConstraintLocation.forParameter(this.javaBeanExecutable, this.index);
        }
    }

    private static class NestedTypeArgumentLocation
    implements TypeArgumentLocation {
        private final TypeArgumentLocation parentLocation;
        private final TypeVariable<?> typeParameter;
        private final Type typeOfAnnotatedElement;

        private NestedTypeArgumentLocation(TypeArgumentLocation parentLocation, TypeVariable<?> typeParameter, Type typeOfAnnotatedElement) {
            this.parentLocation = parentLocation;
            this.typeParameter = typeParameter;
            this.typeOfAnnotatedElement = typeOfAnnotatedElement;
        }

        @Override
        public ConstraintLocation toConstraintLocation() {
            return ConstraintLocation.forTypeArgument(this.parentLocation.toConstraintLocation(), this.typeParameter, this.typeOfAnnotatedElement);
        }
    }
}

