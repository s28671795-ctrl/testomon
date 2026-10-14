/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.metadata;

import java.lang.annotation.ElementType;
import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.ConstructorDescriptor;
import javax.validation.metadata.ElementDescriptor;
import javax.validation.metadata.MethodDescriptor;
import javax.validation.metadata.MethodType;
import javax.validation.metadata.PropertyDescriptor;
import javax.validation.metadata.Scope;
import org.hibernate.validator.internal.engine.ConstraintCreationContext;
import org.hibernate.validator.internal.engine.MethodValidationConfiguration;
import org.hibernate.validator.internal.engine.groups.Sequence;
import org.hibernate.validator.internal.engine.groups.ValidationOrderGenerator;
import org.hibernate.validator.internal.metadata.BeanMetaDataManager;
import org.hibernate.validator.internal.metadata.aggregated.BeanMetaData;
import org.hibernate.validator.internal.metadata.aggregated.BeanMetaDataBuilder;
import org.hibernate.validator.internal.metadata.aggregated.BeanMetaDataImpl;
import org.hibernate.validator.internal.metadata.aggregated.ExecutableMetaData;
import org.hibernate.validator.internal.metadata.aggregated.PropertyMetaData;
import org.hibernate.validator.internal.metadata.core.AnnotationProcessingOptions;
import org.hibernate.validator.internal.metadata.core.AnnotationProcessingOptionsImpl;
import org.hibernate.validator.internal.metadata.core.MetaConstraint;
import org.hibernate.validator.internal.metadata.facets.Cascadable;
import org.hibernate.validator.internal.metadata.provider.AnnotationMetaDataProvider;
import org.hibernate.validator.internal.metadata.provider.MetaDataProvider;
import org.hibernate.validator.internal.metadata.raw.BeanConfiguration;
import org.hibernate.validator.internal.properties.javabean.JavaBeanHelper;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.ExecutableHelper;
import org.hibernate.validator.internal.util.ExecutableParameterNameProvider;
import org.hibernate.validator.internal.util.classhierarchy.ClassHierarchyHelper;
import org.hibernate.validator.internal.util.classhierarchy.Filter;
import org.hibernate.validator.internal.util.classhierarchy.Filters;
import org.hibernate.validator.metadata.BeanMetaDataClassNormalizer;

public class PredefinedScopeBeanMetaDataManager
implements BeanMetaDataManager {
    private final BeanMetaDataClassNormalizer beanMetaDataClassNormalizer;
    private final ConcurrentMap<Class<?>, BeanMetaData<?>> beanMetaDataMap = new ConcurrentHashMap();

    public PredefinedScopeBeanMetaDataManager(ConstraintCreationContext constraintCreationContext, ExecutableHelper executableHelper, ExecutableParameterNameProvider parameterNameProvider, JavaBeanHelper javaBeanHelper, ValidationOrderGenerator validationOrderGenerator, List<MetaDataProvider> optionalMetaDataProviders, MethodValidationConfiguration methodValidationConfiguration, BeanMetaDataClassNormalizer beanMetaDataClassNormalizer, Set<Class<?>> beanClassesToInitialize) {
        AnnotationProcessingOptions annotationProcessingOptions = PredefinedScopeBeanMetaDataManager.getAnnotationProcessingOptionsFromNonDefaultProviders(optionalMetaDataProviders);
        AnnotationMetaDataProvider defaultProvider = new AnnotationMetaDataProvider(constraintCreationContext, javaBeanHelper, annotationProcessingOptions);
        ArrayList<MetaDataProvider> metaDataProviders = new ArrayList<MetaDataProvider>(optionalMetaDataProviders.size() + 1);
        metaDataProviders.add(defaultProvider);
        metaDataProviders.addAll(optionalMetaDataProviders);
        for (Class<?> validatedClass : beanClassesToInitialize) {
            Class<?> normalizedValidatedClass = beanMetaDataClassNormalizer.normalize(validatedClass);
            List<Class<?>> classHierarchy = ClassHierarchyHelper.getHierarchy(normalizedValidatedClass, Filters.excludeInterfaces());
            for (Class<?> hierarchyElement : classHierarchy) {
                if (this.beanMetaDataMap.containsKey(hierarchyElement)) continue;
                this.beanMetaDataMap.put(hierarchyElement, PredefinedScopeBeanMetaDataManager.createBeanMetaData(constraintCreationContext, executableHelper, parameterNameProvider, javaBeanHelper, validationOrderGenerator, optionalMetaDataProviders, methodValidationConfiguration, metaDataProviders, hierarchyElement));
            }
        }
        this.beanMetaDataClassNormalizer = beanMetaDataClassNormalizer;
    }

    @Override
    public <T> BeanMetaData<T> getBeanMetaData(Class<T> beanClass) {
        Class<T> normalizedBeanClass = this.beanMetaDataClassNormalizer.normalize(beanClass);
        BeanMetaData beanMetaData = (BeanMetaData)this.beanMetaDataMap.get(normalizedBeanClass);
        if (beanMetaData == null) {
            beanMetaData = this.beanMetaDataMap.computeIfAbsent(normalizedBeanClass, x$0 -> new UninitializedBeanMetaData((Class)x$0));
        }
        return beanMetaData;
    }

    @Override
    public void clear() {
        this.beanMetaDataMap.clear();
    }

    private static <T> BeanMetaDataImpl<T> createBeanMetaData(ConstraintCreationContext constraintCreationContext, ExecutableHelper executableHelper, ExecutableParameterNameProvider parameterNameProvider, JavaBeanHelper javaBeanHelper, ValidationOrderGenerator validationOrderGenerator, List<MetaDataProvider> optionalMetaDataProviders, MethodValidationConfiguration methodValidationConfiguration, List<MetaDataProvider> metaDataProviders, Class<T> clazz) {
        BeanMetaDataBuilder<T> builder = BeanMetaDataBuilder.getInstance(constraintCreationContext, executableHelper, parameterNameProvider, validationOrderGenerator, clazz, methodValidationConfiguration);
        for (MetaDataProvider provider : metaDataProviders) {
            for (BeanConfiguration<T> beanConfiguration : PredefinedScopeBeanMetaDataManager.getBeanConfigurationForHierarchy(provider, clazz)) {
                builder.add(beanConfiguration);
            }
        }
        return builder.build();
    }

    private static AnnotationProcessingOptions getAnnotationProcessingOptionsFromNonDefaultProviders(List<MetaDataProvider> optionalMetaDataProviders) {
        AnnotationProcessingOptionsImpl options = new AnnotationProcessingOptionsImpl();
        for (MetaDataProvider metaDataProvider : optionalMetaDataProviders) {
            options.merge(metaDataProvider.getAnnotationProcessingOptions());
        }
        return options;
    }

    private static <T> List<BeanConfiguration<? super T>> getBeanConfigurationForHierarchy(MetaDataProvider provider, Class<T> beanClass) {
        ArrayList<BeanConfiguration<T>> configurations = CollectionHelper.newArrayList();
        for (Class<T> clazz : ClassHierarchyHelper.getHierarchy(beanClass, new Filter[0])) {
            BeanConfiguration<T> configuration = provider.getBeanConfiguration(clazz);
            if (configuration == null) continue;
            configurations.add(configuration);
        }
        return configurations;
    }

    private static class UninitializedBeanMetaData<T>
    implements BeanMetaData<T> {
        private final Class<T> beanClass;
        private final BeanDescriptor beanDescriptor;
        private final List<Class<? super T>> classHierarchy;

        private UninitializedBeanMetaData(Class<T> beanClass) {
            this.beanClass = beanClass;
            this.classHierarchy = ClassHierarchyHelper.getHierarchy(beanClass, Filters.excludeInterfaces());
            this.beanDescriptor = new UninitializedBeanDescriptor(beanClass);
        }

        @Override
        public Iterable<Cascadable> getCascadables() {
            return Collections.emptyList();
        }

        @Override
        public boolean hasCascadables() {
            return false;
        }

        @Override
        public Class<T> getBeanClass() {
            return this.beanClass;
        }

        @Override
        public boolean hasConstraints() {
            return false;
        }

        @Override
        public BeanDescriptor getBeanDescriptor() {
            return this.beanDescriptor;
        }

        @Override
        public PropertyMetaData getMetaDataFor(String propertyName) {
            throw new IllegalStateException("Metadata has not been initialized for bean of type " + this.beanClass.getName());
        }

        @Override
        public List<Class<?>> getDefaultGroupSequence(T beanState) {
            throw new IllegalStateException("Metadata has not been initialized for bean of type " + this.beanClass.getName());
        }

        @Override
        public Iterator<Sequence> getDefaultValidationSequence(T beanState) {
            throw new IllegalStateException("Metadata has not been initialized for bean of type " + this.beanClass.getName());
        }

        @Override
        public boolean isDefaultGroupSequenceRedefined() {
            return false;
        }

        @Override
        public Set<MetaConstraint<?>> getMetaConstraints() {
            return Collections.emptySet();
        }

        @Override
        public Set<MetaConstraint<?>> getDirectMetaConstraints() {
            return Collections.emptySet();
        }

        @Override
        public Optional<ExecutableMetaData> getMetaDataFor(Executable executable) throws IllegalArgumentException {
            return Optional.empty();
        }

        @Override
        public List<Class<? super T>> getClassHierarchy() {
            return this.classHierarchy;
        }
    }

    private static class UninitializedConstaintFinder
    implements ElementDescriptor.ConstraintFinder {
        private static final UninitializedConstaintFinder INSTANCE = new UninitializedConstaintFinder();

        private UninitializedConstaintFinder() {
        }

        @Override
        public ElementDescriptor.ConstraintFinder unorderedAndMatchingGroups(Class<?> ... groups2) {
            return this;
        }

        @Override
        public ElementDescriptor.ConstraintFinder lookingAt(Scope scope) {
            return this;
        }

        @Override
        public ElementDescriptor.ConstraintFinder declaredOn(ElementType ... types) {
            return this;
        }

        @Override
        public Set<ConstraintDescriptor<?>> getConstraintDescriptors() {
            return Collections.emptySet();
        }

        @Override
        public boolean hasConstraints() {
            return false;
        }
    }

    private static class UninitializedBeanDescriptor
    implements BeanDescriptor {
        private final Class<?> elementClass;

        private UninitializedBeanDescriptor(Class<?> elementClass) {
            this.elementClass = elementClass;
        }

        @Override
        public boolean hasConstraints() {
            return false;
        }

        @Override
        public Class<?> getElementClass() {
            return this.elementClass;
        }

        @Override
        public Set<ConstraintDescriptor<?>> getConstraintDescriptors() {
            return Collections.emptySet();
        }

        @Override
        public ElementDescriptor.ConstraintFinder findConstraints() {
            return UninitializedConstaintFinder.INSTANCE;
        }

        @Override
        public boolean isBeanConstrained() {
            return false;
        }

        @Override
        public PropertyDescriptor getConstraintsForProperty(String propertyName) {
            return null;
        }

        @Override
        public Set<PropertyDescriptor> getConstrainedProperties() {
            return Collections.emptySet();
        }

        @Override
        public MethodDescriptor getConstraintsForMethod(String methodName, Class<?> ... parameterTypes) {
            return null;
        }

        @Override
        public Set<MethodDescriptor> getConstrainedMethods(MethodType methodType, MethodType ... methodTypes) {
            return Collections.emptySet();
        }

        @Override
        public ConstructorDescriptor getConstraintsForConstructor(Class<?> ... parameterTypes) {
            return null;
        }

        @Override
        public Set<ConstructorDescriptor> getConstrainedConstructors() {
            return Collections.emptySet();
        }
    }
}

