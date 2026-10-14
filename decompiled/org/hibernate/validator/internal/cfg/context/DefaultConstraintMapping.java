/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.cfg.context;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.Set;
import javax.validation.Constraint;
import org.hibernate.validator.cfg.ConstraintMapping;
import org.hibernate.validator.cfg.context.ConstraintDefinitionContext;
import org.hibernate.validator.cfg.context.TypeConstraintMappingContext;
import org.hibernate.validator.internal.cfg.context.ConstraintDefinitionContextImpl;
import org.hibernate.validator.internal.cfg.context.TypeConstraintMappingContextImpl;
import org.hibernate.validator.internal.engine.ConstraintCreationContext;
import org.hibernate.validator.internal.engine.constraintdefinition.ConstraintDefinitionContribution;
import org.hibernate.validator.internal.metadata.core.AnnotationProcessingOptionsImpl;
import org.hibernate.validator.internal.metadata.raw.BeanConfiguration;
import org.hibernate.validator.internal.properties.javabean.JavaBeanHelper;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.util.logging.Messages;

public class DefaultConstraintMapping
implements ConstraintMapping {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    private final JavaBeanHelper javaBeanHelper;
    private final AnnotationProcessingOptionsImpl annotationProcessingOptions;
    private final Set<Class<?>> configuredTypes;
    private final Set<TypeConstraintMappingContextImpl<?>> typeContexts;
    private final Set<Class<?>> definedConstraints;
    private final Set<ConstraintDefinitionContextImpl<?>> constraintContexts;

    public DefaultConstraintMapping(JavaBeanHelper javaBeanHelper) {
        this.javaBeanHelper = javaBeanHelper;
        this.annotationProcessingOptions = new AnnotationProcessingOptionsImpl();
        this.configuredTypes = CollectionHelper.newHashSet();
        this.typeContexts = CollectionHelper.newHashSet();
        this.definedConstraints = CollectionHelper.newHashSet();
        this.constraintContexts = CollectionHelper.newHashSet();
    }

    @Override
    public final <C> TypeConstraintMappingContext<C> type(Class<C> type) {
        Contracts.assertNotNull(type, Messages.MESSAGES.beanTypeMustNotBeNull());
        if (this.configuredTypes.contains(type)) {
            throw LOG.getBeanClassHasAlreadyBeConfiguredViaProgrammaticApiException(type);
        }
        TypeConstraintMappingContextImpl<C> typeContext = new TypeConstraintMappingContextImpl<C>(this.javaBeanHelper, this, type);
        this.typeContexts.add(typeContext);
        this.configuredTypes.add(type);
        return typeContext;
    }

    public final AnnotationProcessingOptionsImpl getAnnotationProcessingOptions() {
        return this.annotationProcessingOptions;
    }

    public Set<Class<?>> getConfiguredTypes() {
        return this.configuredTypes;
    }

    public Set<BeanConfiguration<?>> getBeanConfigurations(ConstraintCreationContext constraintCreationContext) {
        HashSet<BeanConfiguration<?>> configurations = CollectionHelper.newHashSet();
        for (TypeConstraintMappingContextImpl<?> typeContext : this.typeContexts) {
            configurations.add(typeContext.build(constraintCreationContext));
        }
        return configurations;
    }

    @Override
    public <A extends Annotation> ConstraintDefinitionContext<A> constraintDefinition(Class<A> annotationClass) {
        Contracts.assertNotNull(annotationClass, Messages.MESSAGES.annotationTypeMustNotBeNull());
        Contracts.assertTrue(annotationClass.isAnnotationPresent(Constraint.class), Messages.MESSAGES.annotationTypeMustBeAnnotatedWithConstraint());
        if (this.definedConstraints.contains(annotationClass)) {
            throw LOG.getConstraintHasAlreadyBeenConfiguredViaProgrammaticApiException(annotationClass);
        }
        ConstraintDefinitionContextImpl<A> constraintContext = new ConstraintDefinitionContextImpl<A>(this, annotationClass);
        this.constraintContexts.add(constraintContext);
        this.definedConstraints.add(annotationClass);
        return constraintContext;
    }

    public Set<ConstraintDefinitionContribution<?>> getConstraintDefinitionContributions() {
        HashSet<ConstraintDefinitionContribution<?>> contributions = CollectionHelper.newHashSet();
        for (ConstraintDefinitionContextImpl<?> constraintContext : this.constraintContexts) {
            contributions.add(constraintContext.build());
        }
        return contributions;
    }
}

