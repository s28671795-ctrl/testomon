/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.metadata.aggregated;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ElementKind;
import javax.validation.groups.Default;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstructorDescriptor;
import javax.validation.metadata.ElementDescriptor;
import javax.validation.metadata.PropertyDescriptor;
import org.hibernate.validator.internal.engine.groups.Sequence;
import org.hibernate.validator.internal.engine.groups.ValidationOrder;
import org.hibernate.validator.internal.engine.groups.ValidationOrderGenerator;
import org.hibernate.validator.internal.metadata.aggregated.BeanMetaData;
import org.hibernate.validator.internal.metadata.aggregated.ClassMetaData;
import org.hibernate.validator.internal.metadata.aggregated.ConstraintMetaData;
import org.hibernate.validator.internal.metadata.aggregated.ExecutableMetaData;
import org.hibernate.validator.internal.metadata.aggregated.PropertyMetaData;
import org.hibernate.validator.internal.metadata.core.MetaConstraint;
import org.hibernate.validator.internal.metadata.descriptor.BeanDescriptorImpl;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.metadata.descriptor.ExecutableDescriptorImpl;
import org.hibernate.validator.internal.metadata.facets.Cascadable;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.properties.Signature;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.ExecutableHelper;
import org.hibernate.validator.internal.util.classhierarchy.ClassHierarchyHelper;
import org.hibernate.validator.internal.util.classhierarchy.Filters;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

public final class BeanMetaDataImpl<T>
implements BeanMetaData<T> {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    private static final List<Class<?>> DEFAULT_GROUP_SEQUENCE = Collections.singletonList(Default.class);
    private final boolean hasConstraints;
    private final ValidationOrderGenerator validationOrderGenerator;
    private final Class<T> beanClass;
    private final Set<MetaConstraint<?>> allMetaConstraints;
    private final Set<MetaConstraint<?>> directMetaConstraints;
    private final Map<Signature, ExecutableMetaData> executableMetaDataMap;
    private final Set<Signature> unconstrainedExecutables;
    private final Map<String, PropertyMetaData> propertyMetaDataMap;
    private final Set<Cascadable> cascadedProperties;
    private final List<Class<?>> defaultGroupSequence;
    private final DefaultGroupSequenceProvider<? super T> defaultGroupSequenceProvider;
    private final ValidationOrder validationOrder;
    private final List<Class<? super T>> classHierarchyWithoutInterfaces;
    private final boolean defaultGroupSequenceRedefined;
    private final List<Class<?>> resolvedDefaultGroupSequence;
    private volatile BeanDescriptor beanDescriptor;

    public BeanMetaDataImpl(Class<T> beanClass, List<Class<?>> defaultGroupSequence, DefaultGroupSequenceProvider<? super T> defaultGroupSequenceProvider, Set<ConstraintMetaData> constraintMetaDataSet, ValidationOrderGenerator validationOrderGenerator) {
        this.validationOrderGenerator = validationOrderGenerator;
        this.beanClass = beanClass;
        this.propertyMetaDataMap = CollectionHelper.newHashMap();
        HashSet<PropertyMetaData> propertyMetaDataSet = CollectionHelper.newHashSet();
        HashSet<ExecutableMetaData> executableMetaDataSet = CollectionHelper.newHashSet();
        HashSet tmpUnconstrainedExecutables = CollectionHelper.newHashSet();
        boolean hasConstraints = false;
        HashSet allMetaConstraints = CollectionHelper.newHashSet();
        for (ConstraintMetaData constraintMetaData : constraintMetaDataSet) {
            boolean elementHasConstraints = constraintMetaData.isCascading() || constraintMetaData.isConstrained();
            hasConstraints |= elementHasConstraints;
            if (constraintMetaData.getKind() == ElementKind.PROPERTY) {
                propertyMetaDataSet.add((PropertyMetaData)constraintMetaData);
                continue;
            }
            if (constraintMetaData.getKind() == ElementKind.BEAN) {
                allMetaConstraints.addAll(((ClassMetaData)constraintMetaData).getAllConstraints());
                continue;
            }
            ExecutableMetaData executableMetaData = (ExecutableMetaData)constraintMetaData;
            if (elementHasConstraints) {
                executableMetaDataSet.add(executableMetaData);
                continue;
            }
            tmpUnconstrainedExecutables.addAll(executableMetaData.getSignatures());
        }
        HashSet cascadedProperties = CollectionHelper.newHashSet();
        for (PropertyMetaData propertyMetaData : propertyMetaDataSet) {
            this.propertyMetaDataMap.put(propertyMetaData.getName(), propertyMetaData);
            cascadedProperties.addAll(propertyMetaData.getCascadables());
            allMetaConstraints.addAll(propertyMetaData.getAllConstraints());
        }
        this.hasConstraints = hasConstraints;
        this.cascadedProperties = CollectionHelper.toImmutableSet(cascadedProperties);
        this.allMetaConstraints = CollectionHelper.toImmutableSet(allMetaConstraints);
        this.classHierarchyWithoutInterfaces = CollectionHelper.toImmutableList(ClassHierarchyHelper.getHierarchy(beanClass, Filters.excludeInterfaces()));
        DefaultGroupSequenceContext<? super T> defaultGroupSequenceContext = BeanMetaDataImpl.getDefaultGroupSequenceData(beanClass, defaultGroupSequence, defaultGroupSequenceProvider, validationOrderGenerator);
        this.defaultGroupSequenceProvider = defaultGroupSequenceContext.defaultGroupSequenceProvider;
        this.defaultGroupSequence = CollectionHelper.toImmutableList(defaultGroupSequenceContext.defaultGroupSequence);
        this.validationOrder = defaultGroupSequenceContext.validationOrder;
        this.directMetaConstraints = this.getDirectConstraints();
        this.executableMetaDataMap = CollectionHelper.toImmutableMap(this.bySignature(executableMetaDataSet));
        this.unconstrainedExecutables = CollectionHelper.toImmutableSet(tmpUnconstrainedExecutables);
        this.defaultGroupSequenceRedefined = this.defaultGroupSequence.size() > 1 || this.hasDefaultGroupSequenceProvider();
        this.resolvedDefaultGroupSequence = this.getDefaultGroupSequence(null);
    }

    @Override
    public Class<T> getBeanClass() {
        return this.beanClass;
    }

    @Override
    public boolean hasConstraints() {
        return this.hasConstraints;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public BeanDescriptor getBeanDescriptor() {
        BeanDescriptor beanDescriptor = this.beanDescriptor;
        if (beanDescriptor == null) {
            BeanMetaDataImpl beanMetaDataImpl = this;
            synchronized (beanMetaDataImpl) {
                beanDescriptor = this.beanDescriptor;
                if (beanDescriptor == null) {
                    this.beanDescriptor = beanDescriptor = BeanMetaDataImpl.createBeanDescriptor(this.beanClass, this.allMetaConstraints, this.propertyMetaDataMap, this.executableMetaDataMap, this.defaultGroupSequenceRedefined, this.resolvedDefaultGroupSequence);
                }
            }
        }
        return beanDescriptor;
    }

    public Set<Cascadable> getCascadables() {
        return this.cascadedProperties;
    }

    @Override
    public boolean hasCascadables() {
        return !this.cascadedProperties.isEmpty();
    }

    @Override
    public PropertyMetaData getMetaDataFor(String propertyName) {
        PropertyMetaData propertyMetaData = this.propertyMetaDataMap.get(propertyName);
        if (propertyMetaData == null) {
            throw LOG.getPropertyNotDefinedByValidatedTypeException(this.beanClass, propertyName);
        }
        return propertyMetaData;
    }

    @Override
    public Set<MetaConstraint<?>> getMetaConstraints() {
        return this.allMetaConstraints;
    }

    @Override
    public Set<MetaConstraint<?>> getDirectMetaConstraints() {
        return this.directMetaConstraints;
    }

    @Override
    public Optional<ExecutableMetaData> getMetaDataFor(Executable executable) {
        Signature signature = ExecutableHelper.getSignature(executable);
        if (this.unconstrainedExecutables.contains(signature)) {
            return Optional.empty();
        }
        ExecutableMetaData executableMetaData = this.executableMetaDataMap.get(signature);
        if (executableMetaData == null) {
            throw LOG.getMethodOrConstructorNotDefinedByValidatedTypeException(this.beanClass, executable);
        }
        return Optional.of(executableMetaData);
    }

    @Override
    public List<Class<?>> getDefaultGroupSequence(T beanState) {
        if (this.hasDefaultGroupSequenceProvider()) {
            List<Class<?>> providerDefaultGroupSequence = this.defaultGroupSequenceProvider.getValidationGroups(beanState);
            return BeanMetaDataImpl.getValidDefaultGroupSequence(this.beanClass, providerDefaultGroupSequence);
        }
        return this.defaultGroupSequence;
    }

    @Override
    public Iterator<Sequence> getDefaultValidationSequence(T beanState) {
        if (this.hasDefaultGroupSequenceProvider()) {
            List<Class<?>> providerDefaultGroupSequence = this.defaultGroupSequenceProvider.getValidationGroups(beanState);
            return this.validationOrderGenerator.getDefaultValidationOrder(this.beanClass, BeanMetaDataImpl.getValidDefaultGroupSequence(this.beanClass, providerDefaultGroupSequence)).getSequenceIterator();
        }
        return this.validationOrder.getSequenceIterator();
    }

    @Override
    public boolean isDefaultGroupSequenceRedefined() {
        return this.defaultGroupSequenceRedefined;
    }

    @Override
    public List<Class<? super T>> getClassHierarchy() {
        return this.classHierarchyWithoutInterfaces;
    }

    private static BeanDescriptor createBeanDescriptor(Class<?> beanClass, Set<MetaConstraint<?>> allMetaConstraints, Map<String, PropertyMetaData> propertyMetaDataMap, Map<Signature, ExecutableMetaData> executableMetaDataMap, boolean defaultGroupSequenceRedefined, List<Class<?>> resolvedDefaultGroupSequence) {
        Map<String, PropertyDescriptor> propertyDescriptors = BeanMetaDataImpl.getConstrainedPropertiesAsDescriptors(propertyMetaDataMap, defaultGroupSequenceRedefined, resolvedDefaultGroupSequence);
        Map<Signature, ExecutableDescriptorImpl> methodsDescriptors = BeanMetaDataImpl.getConstrainedMethodsAsDescriptors(executableMetaDataMap, defaultGroupSequenceRedefined, resolvedDefaultGroupSequence);
        Map<Signature, ConstructorDescriptor> constructorsDescriptors = BeanMetaDataImpl.getConstrainedConstructorsAsDescriptors(executableMetaDataMap, defaultGroupSequenceRedefined, resolvedDefaultGroupSequence);
        return new BeanDescriptorImpl(beanClass, BeanMetaDataImpl.getClassLevelConstraintsAsDescriptors(allMetaConstraints), propertyDescriptors, methodsDescriptors, constructorsDescriptors, defaultGroupSequenceRedefined, resolvedDefaultGroupSequence);
    }

    private static Set<ConstraintDescriptorImpl<?>> getClassLevelConstraintsAsDescriptors(Set<MetaConstraint<?>> constraints) {
        return constraints.stream().filter(c -> c.getConstraintLocationKind() == ConstraintLocation.ConstraintLocationKind.TYPE).map(MetaConstraint::getDescriptor).collect(Collectors.toSet());
    }

    private static Map<String, PropertyDescriptor> getConstrainedPropertiesAsDescriptors(Map<String, PropertyMetaData> propertyMetaDataMap, boolean defaultGroupSequenceIsRedefined, List<Class<?>> resolvedDefaultGroupSequence) {
        HashMap<String, PropertyDescriptor> theValue = CollectionHelper.newHashMap();
        for (Map.Entry<String, PropertyMetaData> entry : propertyMetaDataMap.entrySet()) {
            if (!entry.getValue().isConstrained() || entry.getValue().getName() == null) continue;
            theValue.put(entry.getKey(), (PropertyDescriptor)entry.getValue().asDescriptor(defaultGroupSequenceIsRedefined, (List)resolvedDefaultGroupSequence));
        }
        return theValue;
    }

    private static Map<Signature, ExecutableDescriptorImpl> getConstrainedMethodsAsDescriptors(Map<Signature, ExecutableMetaData> executableMetaDataMap, boolean defaultGroupSequenceIsRedefined, List<Class<?>> resolvedDefaultGroupSequence) {
        HashMap<Signature, ExecutableDescriptorImpl> constrainedMethodDescriptors = CollectionHelper.newHashMap();
        for (ExecutableMetaData executableMetaData : executableMetaDataMap.values()) {
            if (executableMetaData.getKind() != ElementKind.METHOD || !executableMetaData.isConstrained()) continue;
            ElementDescriptor descriptor = executableMetaData.asDescriptor(defaultGroupSequenceIsRedefined, (List)resolvedDefaultGroupSequence);
            for (Signature signature : executableMetaData.getSignatures()) {
                constrainedMethodDescriptors.put(signature, (ExecutableDescriptorImpl)descriptor);
            }
        }
        return constrainedMethodDescriptors;
    }

    private static Map<Signature, ConstructorDescriptor> getConstrainedConstructorsAsDescriptors(Map<Signature, ExecutableMetaData> executableMetaDataMap, boolean defaultGroupSequenceIsRedefined, List<Class<?>> resolvedDefaultGroupSequence) {
        HashMap<Signature, ConstructorDescriptor> constrainedMethodDescriptors = CollectionHelper.newHashMap();
        for (ExecutableMetaData executableMetaData : executableMetaDataMap.values()) {
            if (executableMetaData.getKind() != ElementKind.CONSTRUCTOR || !executableMetaData.isConstrained()) continue;
            constrainedMethodDescriptors.put(executableMetaData.getSignatures().iterator().next(), (ConstructorDescriptor)executableMetaData.asDescriptor(defaultGroupSequenceIsRedefined, (List)resolvedDefaultGroupSequence));
        }
        return constrainedMethodDescriptors;
    }

    private static <T> DefaultGroupSequenceContext<T> getDefaultGroupSequenceData(Class<?> beanClass, List<Class<?>> defaultGroupSequence, DefaultGroupSequenceProvider<? super T> defaultGroupSequenceProvider, ValidationOrderGenerator validationOrderGenerator) {
        if (defaultGroupSequence != null && defaultGroupSequenceProvider != null) {
            throw LOG.getInvalidDefaultGroupSequenceDefinitionException();
        }
        DefaultGroupSequenceContext context = new DefaultGroupSequenceContext();
        if (defaultGroupSequenceProvider != null) {
            context.defaultGroupSequenceProvider = defaultGroupSequenceProvider;
            context.defaultGroupSequence = Collections.emptyList();
            context.validationOrder = null;
        } else if (defaultGroupSequence != null && !defaultGroupSequence.isEmpty()) {
            context.defaultGroupSequence = BeanMetaDataImpl.getValidDefaultGroupSequence(beanClass, defaultGroupSequence);
            context.validationOrder = validationOrderGenerator.getDefaultValidationOrder(beanClass, context.defaultGroupSequence);
        } else {
            context.defaultGroupSequence = DEFAULT_GROUP_SEQUENCE;
            context.validationOrder = ValidationOrder.DEFAULT_SEQUENCE;
        }
        return context;
    }

    private Set<MetaConstraint<?>> getDirectConstraints() {
        HashSet constraints = CollectionHelper.newHashSet();
        HashSet<Class> classAndInterfaces = CollectionHelper.newHashSet();
        classAndInterfaces.add(this.beanClass);
        classAndInterfaces.addAll(ClassHierarchyHelper.getDirectlyImplementedInterfaces(this.beanClass));
        for (Class clazz : classAndInterfaces) {
            for (MetaConstraint<?> metaConstraint : this.allMetaConstraints) {
                if (!metaConstraint.getLocation().getDeclaringClass().equals(clazz)) continue;
                constraints.add(metaConstraint);
            }
        }
        return CollectionHelper.toImmutableSet(constraints);
    }

    private Map<Signature, ExecutableMetaData> bySignature(Set<ExecutableMetaData> executables) {
        HashMap<Signature, ExecutableMetaData> theValue = CollectionHelper.newHashMap();
        for (ExecutableMetaData executableMetaData : executables) {
            for (Signature signature : executableMetaData.getSignatures()) {
                theValue.put(signature, executableMetaData);
            }
        }
        return theValue;
    }

    private static List<Class<?>> getValidDefaultGroupSequence(Class<?> beanClass, List<Class<?>> groupSequence) {
        ArrayList validDefaultGroupSequence = new ArrayList();
        boolean groupSequenceContainsDefault = false;
        if (groupSequence != null) {
            for (Class<?> group : groupSequence) {
                if (group.getName().equals(beanClass.getName())) {
                    validDefaultGroupSequence.add(Default.class);
                    groupSequenceContainsDefault = true;
                    continue;
                }
                if (group.getName().equals(Default.class.getName())) {
                    throw LOG.getNoDefaultGroupInGroupSequenceException();
                }
                validDefaultGroupSequence.add(group);
            }
        }
        if (!groupSequenceContainsDefault) {
            throw LOG.getBeanClassMustBePartOfRedefinedDefaultGroupSequenceException(beanClass);
        }
        if (LOG.isTraceEnabled()) {
            LOG.tracef("Members of the default group sequence for bean %s are: %s.", (Object)beanClass.getName(), (Object)validDefaultGroupSequence);
        }
        return validDefaultGroupSequence;
    }

    private boolean hasDefaultGroupSequenceProvider() {
        return this.defaultGroupSequenceProvider != null;
    }

    public String toString() {
        return "BeanMetaDataImpl{beanClass=" + this.beanClass.getSimpleName() + ", constraintCount=" + this.getMetaConstraints().size() + ", cascadedPropertiesCount=" + this.cascadedProperties.size() + ", defaultGroupSequence=" + this.getDefaultGroupSequence(null) + '}';
    }

    private static class DefaultGroupSequenceContext<T> {
        List<Class<?>> defaultGroupSequence;
        DefaultGroupSequenceProvider<? super T> defaultGroupSequenceProvider;
        ValidationOrder validationOrder;

        private DefaultGroupSequenceContext() {
        }
    }
}

