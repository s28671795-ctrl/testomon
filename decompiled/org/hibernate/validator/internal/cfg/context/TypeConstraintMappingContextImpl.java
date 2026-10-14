/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.cfg.context;

import java.lang.annotation.ElementType;
import java.lang.invoke.MethodHandles;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.hibernate.validator.cfg.ConstraintDef;
import org.hibernate.validator.cfg.context.ConstructorConstraintMappingContext;
import org.hibernate.validator.cfg.context.MethodConstraintMappingContext;
import org.hibernate.validator.cfg.context.PropertyConstraintMappingContext;
import org.hibernate.validator.cfg.context.TypeConstraintMappingContext;
import org.hibernate.validator.internal.cfg.context.AbstractPropertyConstraintMappingContextImpl;
import org.hibernate.validator.internal.cfg.context.ConfiguredConstraint;
import org.hibernate.validator.internal.cfg.context.ConstraintMappingContextImplBase;
import org.hibernate.validator.internal.cfg.context.ConstructorConstraintMappingContextImpl;
import org.hibernate.validator.internal.cfg.context.DefaultConstraintMapping;
import org.hibernate.validator.internal.cfg.context.ExecutableConstraintMappingContextImpl;
import org.hibernate.validator.internal.cfg.context.FieldConstraintMappingContextImpl;
import org.hibernate.validator.internal.cfg.context.GetterConstraintMappingContextImpl;
import org.hibernate.validator.internal.cfg.context.MethodConstraintMappingContextImpl;
import org.hibernate.validator.internal.engine.ConstraintCreationContext;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.metadata.raw.BeanConfiguration;
import org.hibernate.validator.internal.metadata.raw.ConfigurationSource;
import org.hibernate.validator.internal.metadata.raw.ConstrainedElement;
import org.hibernate.validator.internal.metadata.raw.ConstrainedType;
import org.hibernate.validator.internal.properties.Constrainable;
import org.hibernate.validator.internal.properties.javabean.JavaBeanConstructor;
import org.hibernate.validator.internal.properties.javabean.JavaBeanField;
import org.hibernate.validator.internal.properties.javabean.JavaBeanGetter;
import org.hibernate.validator.internal.properties.javabean.JavaBeanHelper;
import org.hibernate.validator.internal.properties.javabean.JavaBeanMethod;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.internal.util.ExecutableHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.util.logging.Messages;
import org.hibernate.validator.internal.util.privilegedactions.NewInstance;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

public final class TypeConstraintMappingContextImpl<C>
extends ConstraintMappingContextImplBase
implements TypeConstraintMappingContext<C> {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    private final JavaBeanHelper javaBeanHelper;
    private final Class<C> beanClass;
    private final Set<ExecutableConstraintMappingContextImpl> executableContexts = CollectionHelper.newHashSet();
    private final Set<AbstractPropertyConstraintMappingContextImpl<?>> propertyContexts = CollectionHelper.newHashSet();
    private final Set<Constrainable> configuredMembers = CollectionHelper.newHashSet();
    private List<Class<?>> defaultGroupSequence;
    private Class<? extends DefaultGroupSequenceProvider<? super C>> defaultGroupSequenceProviderClass;

    TypeConstraintMappingContextImpl(JavaBeanHelper javaBeanHelper, DefaultConstraintMapping mapping, Class<C> beanClass) {
        super(mapping);
        this.javaBeanHelper = javaBeanHelper;
        this.beanClass = beanClass;
        mapping.getAnnotationProcessingOptions().ignoreAnnotationConstraintForClass(beanClass, Boolean.FALSE);
    }

    @Override
    public TypeConstraintMappingContext<C> constraint(ConstraintDef<?, ?> definition) {
        this.addConstraint(ConfiguredConstraint.forType(definition, this.beanClass));
        return this;
    }

    @Override
    @Deprecated
    public TypeConstraintMappingContext<C> ignoreAnnotations() {
        return this.ignoreAnnotations(true);
    }

    @Override
    public TypeConstraintMappingContext<C> ignoreAnnotations(boolean ignoreAnnotations) {
        this.mapping.getAnnotationProcessingOptions().ignoreClassLevelConstraintAnnotations(this.beanClass, ignoreAnnotations);
        return this;
    }

    @Override
    public TypeConstraintMappingContext<C> ignoreAllAnnotations() {
        this.mapping.getAnnotationProcessingOptions().ignoreAnnotationConstraintForClass(this.beanClass, Boolean.TRUE);
        return this;
    }

    @Override
    public TypeConstraintMappingContext<C> defaultGroupSequence(Class<?> ... defaultGroupSequence) {
        this.defaultGroupSequence = Arrays.asList(defaultGroupSequence);
        return this;
    }

    @Override
    public TypeConstraintMappingContext<C> defaultGroupSequenceProviderClass(Class<? extends DefaultGroupSequenceProvider<? super C>> defaultGroupSequenceProviderClass) {
        this.defaultGroupSequenceProviderClass = defaultGroupSequenceProviderClass;
        return this;
    }

    @Override
    @Deprecated
    public PropertyConstraintMappingContext property(String property, ElementType elementType) {
        Contracts.assertNotNull((Object)elementType, "The element type must not be null.");
        if (!ElementType.FIELD.equals((Object)elementType) && !ElementType.METHOD.equals((Object)elementType)) {
            throw LOG.getElementTypeHasToBeFieldOrMethodException();
        }
        if (ElementType.FIELD == elementType) {
            return this.field(property);
        }
        return this.getter(property);
    }

    @Override
    public PropertyConstraintMappingContext field(String property) {
        Contracts.assertNotEmpty(property, Messages.MESSAGES.propertyNameMustNotBeEmpty());
        Optional<JavaBeanField> foundField = this.javaBeanHelper.findDeclaredField(this.beanClass, property);
        if (!foundField.isPresent()) {
            throw LOG.getUnableToFindPropertyWithAccessException(this.beanClass, property, ElementType.FIELD);
        }
        JavaBeanField javaBeanField = foundField.get();
        if (this.configuredMembers.contains(javaBeanField)) {
            throw LOG.getPropertyHasAlreadyBeConfiguredViaProgrammaticApiException(this.beanClass, property);
        }
        FieldConstraintMappingContextImpl context = new FieldConstraintMappingContextImpl(this, javaBeanField);
        this.configuredMembers.add(javaBeanField);
        this.propertyContexts.add(context);
        return context;
    }

    @Override
    public PropertyConstraintMappingContext getter(String property) {
        Contracts.assertNotEmpty(property, Messages.MESSAGES.propertyNameMustNotBeEmpty());
        Optional<JavaBeanGetter> foundGetter = this.javaBeanHelper.findDeclaredGetter(this.beanClass, property);
        if (!foundGetter.isPresent()) {
            throw LOG.getUnableToFindPropertyWithAccessException(this.beanClass, property, ElementType.METHOD);
        }
        JavaBeanGetter javaBeanGetter = foundGetter.get();
        if (this.configuredMembers.contains(javaBeanGetter)) {
            throw LOG.getPropertyHasAlreadyBeConfiguredViaProgrammaticApiException(this.beanClass, property);
        }
        GetterConstraintMappingContextImpl context = new GetterConstraintMappingContextImpl(this, javaBeanGetter);
        this.configuredMembers.add(javaBeanGetter);
        this.propertyContexts.add(context);
        return context;
    }

    @Override
    public MethodConstraintMappingContext method(String name, Class<?> ... parameterTypes) {
        Contracts.assertNotNull(name, Messages.MESSAGES.methodNameMustNotBeNull());
        Optional<JavaBeanMethod> foundMethod = this.javaBeanHelper.findDeclaredMethod(this.beanClass, name, parameterTypes);
        if (!foundMethod.isPresent()) {
            throw LOG.getBeanDoesNotContainMethodException(this.beanClass, name, parameterTypes);
        }
        JavaBeanMethod javaBeanMethod = foundMethod.get();
        if (this.configuredMembers.contains(javaBeanMethod)) {
            throw LOG.getMethodHasAlreadyBeenConfiguredViaProgrammaticApiException(this.beanClass, ExecutableHelper.getExecutableAsString(name, parameterTypes));
        }
        MethodConstraintMappingContextImpl context = new MethodConstraintMappingContextImpl(this, javaBeanMethod);
        this.configuredMembers.add(javaBeanMethod);
        this.executableContexts.add(context);
        return context;
    }

    @Override
    public ConstructorConstraintMappingContext constructor(Class<?> ... parameterTypes) {
        Optional<JavaBeanConstructor> foundConstructor = this.javaBeanHelper.findDeclaredConstructor(this.beanClass, parameterTypes);
        if (!foundConstructor.isPresent()) {
            throw LOG.getBeanDoesNotContainConstructorException(this.beanClass, parameterTypes);
        }
        JavaBeanConstructor javaBeanConstructor = foundConstructor.get();
        if (this.configuredMembers.contains(javaBeanConstructor)) {
            throw LOG.getConstructorHasAlreadyBeConfiguredViaProgrammaticApiException(this.beanClass, ExecutableHelper.getExecutableAsString(this.beanClass.getSimpleName(), parameterTypes));
        }
        ConstructorConstraintMappingContextImpl context = new ConstructorConstraintMappingContextImpl(this, javaBeanConstructor);
        this.configuredMembers.add(javaBeanConstructor);
        this.executableContexts.add(context);
        return context;
    }

    BeanConfiguration<C> build(ConstraintCreationContext constraintCreationContext) {
        return new BeanConfiguration<C>(ConfigurationSource.API, this.beanClass, this.buildConstraintElements(constraintCreationContext), this.defaultGroupSequence, this.getDefaultGroupSequenceProvider());
    }

    private Set<ConstrainedElement> buildConstraintElements(ConstraintCreationContext constraintCreationContext) {
        HashSet<ConstrainedElement> elements = CollectionHelper.newHashSet();
        elements.add(new ConstrainedType(ConfigurationSource.API, this.beanClass, this.getConstraints(constraintCreationContext)));
        for (ExecutableConstraintMappingContextImpl executableConstraintMappingContextImpl : this.executableContexts) {
            elements.add(executableConstraintMappingContextImpl.build(constraintCreationContext));
        }
        for (AbstractPropertyConstraintMappingContextImpl abstractPropertyConstraintMappingContextImpl : this.propertyContexts) {
            elements.add(abstractPropertyConstraintMappingContextImpl.build(constraintCreationContext));
        }
        return elements;
    }

    private DefaultGroupSequenceProvider<? super C> getDefaultGroupSequenceProvider() {
        return this.defaultGroupSequenceProviderClass != null ? this.run(NewInstance.action(this.defaultGroupSequenceProviderClass, "default group sequence provider")) : null;
    }

    Class<?> getBeanClass() {
        return this.beanClass;
    }

    @Override
    protected ConstraintDescriptorImpl.ConstraintType getConstraintType() {
        return ConstraintDescriptorImpl.ConstraintType.GENERIC;
    }

    private <T> T run(PrivilegedAction<T> action) {
        return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
    }
}

