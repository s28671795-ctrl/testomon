/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.constraintvalidation;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.metadata.ConstraintDescriptor;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidator;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorDescriptor;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorManager;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.TypeHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public abstract class AbstractConstraintValidatorManagerImpl
implements ConstraintValidatorManager {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    private final ConstraintValidatorFactory defaultConstraintValidatorFactory;
    private final HibernateConstraintValidatorInitializationContext defaultConstraintValidatorInitializationContext;

    public AbstractConstraintValidatorManagerImpl(ConstraintValidatorFactory defaultConstraintValidatorFactory, HibernateConstraintValidatorInitializationContext defaultConstraintValidatorInitializationContext) {
        this.defaultConstraintValidatorFactory = defaultConstraintValidatorFactory;
        this.defaultConstraintValidatorInitializationContext = defaultConstraintValidatorInitializationContext;
    }

    @Override
    public ConstraintValidatorFactory getDefaultConstraintValidatorFactory() {
        return this.defaultConstraintValidatorFactory;
    }

    @Override
    public HibernateConstraintValidatorInitializationContext getDefaultConstraintValidatorInitializationContext() {
        return this.defaultConstraintValidatorInitializationContext;
    }

    protected <A extends Annotation> ConstraintValidator<A, ?> createAndInitializeValidator(Type validatedValueType, ConstraintDescriptorImpl<A> descriptor, ConstraintValidatorFactory constraintValidatorFactory, HibernateConstraintValidatorInitializationContext initializationContext) {
        ConstraintValidatorDescriptor<A> validatorDescriptor = this.findMatchingValidatorDescriptor(descriptor, validatedValueType);
        if (validatorDescriptor == null) {
            return null;
        }
        ConstraintValidator<A, ?> constraintValidator = validatorDescriptor.newInstance(constraintValidatorFactory);
        this.initializeValidator(descriptor, constraintValidator, initializationContext);
        return constraintValidator;
    }

    private <A extends Annotation> ConstraintValidatorDescriptor<A> findMatchingValidatorDescriptor(ConstraintDescriptorImpl<A> descriptor, Type validatedValueType) {
        Map<Type, ConstraintValidatorDescriptor<A>> availableValidatorDescriptors = TypeHelper.getValidatorTypes(descriptor.getAnnotationType(), descriptor.getMatchingConstraintValidatorDescriptors());
        List<Type> discoveredSuitableTypes = this.findSuitableValidatorTypes(validatedValueType, availableValidatorDescriptors.keySet());
        this.resolveAssignableTypes(discoveredSuitableTypes);
        if (discoveredSuitableTypes.size() == 0) {
            return null;
        }
        if (discoveredSuitableTypes.size() > 1) {
            throw LOG.getMoreThanOneValidatorFoundForTypeException(validatedValueType, discoveredSuitableTypes);
        }
        Type suitableType = discoveredSuitableTypes.get(0);
        return availableValidatorDescriptors.get(suitableType);
    }

    private <A extends Annotation> List<Type> findSuitableValidatorTypes(Type type, Iterable<Type> availableValidatorTypes) {
        ArrayList<Type> determinedSuitableTypes = CollectionHelper.newArrayList();
        for (Type validatorType : availableValidatorTypes) {
            if (!TypeHelper.isAssignable(validatorType, type) || determinedSuitableTypes.contains(validatorType)) continue;
            determinedSuitableTypes.add(validatorType);
        }
        return determinedSuitableTypes;
    }

    private <A extends Annotation> void initializeValidator(ConstraintDescriptor<A> descriptor, ConstraintValidator<A, ?> constraintValidator, HibernateConstraintValidatorInitializationContext initializationContext) {
        try {
            if (constraintValidator instanceof HibernateConstraintValidator) {
                ((HibernateConstraintValidator)constraintValidator).initialize(descriptor, initializationContext);
            }
            constraintValidator.initialize(descriptor.getAnnotation());
        }
        catch (RuntimeException e) {
            if (e instanceof ConstraintDeclarationException) {
                throw e;
            }
            throw LOG.getUnableToInitializeConstraintValidatorException(constraintValidator.getClass(), e);
        }
    }

    private void resolveAssignableTypes(List<Type> assignableTypes) {
        if (assignableTypes.size() == 0 || assignableTypes.size() == 1) {
            return;
        }
        ArrayList<Type> typesToRemove = new ArrayList<Type>();
        do {
            typesToRemove.clear();
            Type type = assignableTypes.get(0);
            for (int i = 1; i < assignableTypes.size(); ++i) {
                if (TypeHelper.isAssignable(type, assignableTypes.get(i))) {
                    typesToRemove.add(type);
                    continue;
                }
                if (!TypeHelper.isAssignable(assignableTypes.get(i), type)) continue;
                typesToRemove.add(assignableTypes.get(i));
            }
            assignableTypes.removeAll(typesToRemove);
        } while (typesToRemove.size() > 0);
    }
}

