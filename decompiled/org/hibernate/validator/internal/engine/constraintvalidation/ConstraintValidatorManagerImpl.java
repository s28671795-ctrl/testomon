/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.constraintvalidation;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.constraints.Null;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext;
import org.hibernate.validator.internal.engine.constraintvalidation.AbstractConstraintValidatorManagerImpl;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.internal.util.annotation.ConstraintAnnotationDescriptor;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class ConstraintValidatorManagerImpl
extends AbstractConstraintValidatorManagerImpl {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    private static final ConstraintValidator<?, ?> DUMMY_CONSTRAINT_VALIDATOR = new ConstraintValidator<Null, Object>(){

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {
            return false;
        }
    };
    private volatile ConstraintValidatorFactory mostRecentlyUsedNonDefaultConstraintValidatorFactory;
    private volatile HibernateConstraintValidatorInitializationContext mostRecentlyUsedNonDefaultConstraintValidatorInitializationContext;
    private final Object mostRecentlyUsedNonDefaultConstraintValidatorFactoryAndInitializationContextMutex = new Object();
    private final ConcurrentHashMap<CacheKey, ConstraintValidator<?, ?>> constraintValidatorCache = new ConcurrentHashMap();

    public ConstraintValidatorManagerImpl(ConstraintValidatorFactory defaultConstraintValidatorFactory, HibernateConstraintValidatorInitializationContext defaultConstraintValidatorInitializationContext) {
        super(defaultConstraintValidatorFactory, defaultConstraintValidatorInitializationContext);
    }

    @Override
    public boolean isPredefinedScope() {
        return false;
    }

    @Override
    public <A extends Annotation> ConstraintValidator<A, ?> getInitializedValidator(Type validatedValueType, ConstraintDescriptorImpl<A> descriptor, ConstraintValidatorFactory constraintValidatorFactory, HibernateConstraintValidatorInitializationContext initializationContext) {
        Contracts.assertNotNull(validatedValueType);
        Contracts.assertNotNull(descriptor);
        Contracts.assertNotNull(constraintValidatorFactory);
        Contracts.assertNotNull(initializationContext);
        CacheKey key = new CacheKey(descriptor.getAnnotationDescriptor(), validatedValueType, constraintValidatorFactory, initializationContext);
        ConstraintValidator<Object, ?> constraintValidator = this.constraintValidatorCache.get(key);
        if (constraintValidator == null) {
            constraintValidator = this.createAndInitializeValidator(validatedValueType, descriptor, constraintValidatorFactory, initializationContext);
            constraintValidator = this.cacheValidator(key, constraintValidator);
        } else {
            LOG.tracef("Constraint validator %s found in cache.", (Object)constraintValidator);
        }
        return DUMMY_CONSTRAINT_VALIDATOR == constraintValidator ? null : constraintValidator;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private <A extends Annotation> ConstraintValidator<A, ?> cacheValidator(CacheKey key, ConstraintValidator<A, ?> constraintValidator) {
        if (key.getConstraintValidatorFactory() != this.getDefaultConstraintValidatorFactory() && key.getConstraintValidatorFactory() != this.mostRecentlyUsedNonDefaultConstraintValidatorFactory || key.getConstraintValidatorInitializationContext() != this.getDefaultConstraintValidatorInitializationContext() && key.getConstraintValidatorInitializationContext() != this.mostRecentlyUsedNonDefaultConstraintValidatorInitializationContext) {
            Object object = this.mostRecentlyUsedNonDefaultConstraintValidatorFactoryAndInitializationContextMutex;
            synchronized (object) {
                if (key.constraintValidatorFactory != this.mostRecentlyUsedNonDefaultConstraintValidatorFactory || key.constraintValidatorInitializationContext != this.mostRecentlyUsedNonDefaultConstraintValidatorInitializationContext) {
                    this.clearEntries(this.mostRecentlyUsedNonDefaultConstraintValidatorFactory, this.mostRecentlyUsedNonDefaultConstraintValidatorInitializationContext);
                    this.mostRecentlyUsedNonDefaultConstraintValidatorFactory = key.getConstraintValidatorFactory();
                    this.mostRecentlyUsedNonDefaultConstraintValidatorInitializationContext = key.getConstraintValidatorInitializationContext();
                }
            }
        }
        ConstraintValidator<A, ?> cached = this.constraintValidatorCache.putIfAbsent(key, constraintValidator != null ? constraintValidator : DUMMY_CONSTRAINT_VALIDATOR);
        return cached != null ? cached : constraintValidator;
    }

    private void clearEntries(ConstraintValidatorFactory constraintValidatorFactory, HibernateConstraintValidatorInitializationContext constraintValidatorInitializationContext) {
        Iterator<Map.Entry<CacheKey, ConstraintValidator<?, ?>>> cacheEntries = this.constraintValidatorCache.entrySet().iterator();
        while (cacheEntries.hasNext()) {
            Map.Entry<CacheKey, ConstraintValidator<?, ?>> cacheEntry = cacheEntries.next();
            if (cacheEntry.getKey().getConstraintValidatorFactory() != constraintValidatorFactory || cacheEntry.getKey().getConstraintValidatorInitializationContext() != constraintValidatorInitializationContext) continue;
            constraintValidatorFactory.releaseInstance(cacheEntry.getValue());
            cacheEntries.remove();
        }
    }

    @Override
    public void clear() {
        for (Map.Entry<CacheKey, ConstraintValidator<?, ?>> entry : this.constraintValidatorCache.entrySet()) {
            entry.getKey().getConstraintValidatorFactory().releaseInstance(entry.getValue());
        }
        this.constraintValidatorCache.clear();
    }

    public int numberOfCachedConstraintValidatorInstances() {
        return this.constraintValidatorCache.size();
    }

    private static final class CacheKey {
        private ConstraintAnnotationDescriptor<?> annotationDescriptor;
        private Type validatedType;
        private ConstraintValidatorFactory constraintValidatorFactory;
        private HibernateConstraintValidatorInitializationContext constraintValidatorInitializationContext;
        private int hashCode;

        private CacheKey(ConstraintAnnotationDescriptor<?> annotationDescriptor, Type validatorType, ConstraintValidatorFactory constraintValidatorFactory, HibernateConstraintValidatorInitializationContext constraintValidatorInitializationContext) {
            this.annotationDescriptor = annotationDescriptor;
            this.validatedType = validatorType;
            this.constraintValidatorFactory = constraintValidatorFactory;
            this.constraintValidatorInitializationContext = constraintValidatorInitializationContext;
            this.hashCode = this.createHashCode();
        }

        public ConstraintValidatorFactory getConstraintValidatorFactory() {
            return this.constraintValidatorFactory;
        }

        public HibernateConstraintValidatorInitializationContext getConstraintValidatorInitializationContext() {
            return this.constraintValidatorInitializationContext;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null) {
                return false;
            }
            CacheKey other = (CacheKey)o;
            if (!this.annotationDescriptor.equals(other.annotationDescriptor)) {
                return false;
            }
            if (!this.validatedType.equals(other.validatedType)) {
                return false;
            }
            if (!this.constraintValidatorFactory.equals(other.constraintValidatorFactory)) {
                return false;
            }
            return this.constraintValidatorInitializationContext.equals(other.constraintValidatorInitializationContext);
        }

        public int hashCode() {
            return this.hashCode;
        }

        private int createHashCode() {
            int result = this.annotationDescriptor.hashCode();
            result = 31 * result + this.validatedType.hashCode();
            result = 31 * result + this.constraintValidatorFactory.hashCode();
            result = 31 * result + this.constraintValidatorInitializationContext.hashCode();
            return result;
        }
    }
}

