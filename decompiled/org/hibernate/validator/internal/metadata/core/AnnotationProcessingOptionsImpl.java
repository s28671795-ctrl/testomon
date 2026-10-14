/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.metadata.core;

import java.lang.invoke.MethodHandles;
import java.util.Map;
import org.hibernate.validator.internal.metadata.core.AnnotationProcessingOptions;
import org.hibernate.validator.internal.properties.Constrainable;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class AnnotationProcessingOptionsImpl
implements AnnotationProcessingOptions {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    private final Map<Class<?>, Boolean> ignoreAnnotationDefaults = CollectionHelper.newHashMap();
    private final Map<Class<?>, Boolean> annotationIgnoresForClasses = CollectionHelper.newHashMap();
    private final Map<Constrainable, Boolean> annotationIgnoredForMembers = CollectionHelper.newHashMap();
    private final Map<Constrainable, Boolean> annotationIgnoresForReturnValues = CollectionHelper.newHashMap();
    private final Map<Constrainable, Boolean> annotationIgnoresForCrossParameter = CollectionHelper.newHashMap();
    private final Map<ExecutableParameterKey, Boolean> annotationIgnoresForMethodParameter = CollectionHelper.newHashMap();

    @Override
    public boolean areMemberConstraintsIgnoredFor(Constrainable constrainable) {
        Class<?> clazz = constrainable.getDeclaringClass();
        Boolean annotationIgnoredForMember = this.annotationIgnoredForMembers.get(constrainable);
        if (annotationIgnoredForMember != null) {
            return annotationIgnoredForMember;
        }
        return this.areAllConstraintAnnotationsIgnoredFor(clazz);
    }

    @Override
    public boolean areReturnValueConstraintsIgnoredFor(Constrainable constrainable) {
        Boolean annotationIgnoreForReturnValue = this.annotationIgnoresForReturnValues.get(constrainable);
        if (annotationIgnoreForReturnValue != null) {
            return annotationIgnoreForReturnValue;
        }
        return this.areMemberConstraintsIgnoredFor(constrainable);
    }

    @Override
    public boolean areCrossParameterConstraintsIgnoredFor(Constrainable constrainable) {
        Boolean annotationIgnoreForCrossParameter = this.annotationIgnoresForCrossParameter.get(constrainable);
        if (annotationIgnoreForCrossParameter != null) {
            return annotationIgnoreForCrossParameter;
        }
        return this.areMemberConstraintsIgnoredFor(constrainable);
    }

    @Override
    public boolean areParameterConstraintsIgnoredFor(Constrainable constrainable, int index) {
        ExecutableParameterKey key = new ExecutableParameterKey(constrainable, index);
        Boolean annotationIgnoreForMethodParameter = this.annotationIgnoresForMethodParameter.get(key);
        if (annotationIgnoreForMethodParameter != null) {
            return annotationIgnoreForMethodParameter;
        }
        return this.areMemberConstraintsIgnoredFor(constrainable);
    }

    @Override
    public boolean areClassLevelConstraintsIgnoredFor(Class<?> clazz) {
        Boolean annotationIgnoreForClass = this.annotationIgnoresForClasses.get(clazz);
        boolean ignoreAnnotation = annotationIgnoreForClass != null ? annotationIgnoreForClass.booleanValue() : this.areAllConstraintAnnotationsIgnoredFor(clazz);
        if (LOG.isDebugEnabled() && ignoreAnnotation) {
            LOG.debugf("Class level annotation are getting ignored for %s.", (Object)clazz.getName());
        }
        return ignoreAnnotation;
    }

    @Override
    public void merge(AnnotationProcessingOptions annotationProcessingOptions) {
        AnnotationProcessingOptionsImpl annotationProcessingOptionsImpl = (AnnotationProcessingOptionsImpl)annotationProcessingOptions;
        this.ignoreAnnotationDefaults.putAll(annotationProcessingOptionsImpl.ignoreAnnotationDefaults);
        this.annotationIgnoresForClasses.putAll(annotationProcessingOptionsImpl.annotationIgnoresForClasses);
        this.annotationIgnoredForMembers.putAll(annotationProcessingOptionsImpl.annotationIgnoredForMembers);
        this.annotationIgnoresForReturnValues.putAll(annotationProcessingOptionsImpl.annotationIgnoresForReturnValues);
        this.annotationIgnoresForCrossParameter.putAll(annotationProcessingOptionsImpl.annotationIgnoresForCrossParameter);
        this.annotationIgnoresForMethodParameter.putAll(annotationProcessingOptionsImpl.annotationIgnoresForMethodParameter);
    }

    public void ignoreAnnotationConstraintForClass(Class<?> clazz, Boolean b) {
        if (b == null) {
            this.ignoreAnnotationDefaults.put(clazz, Boolean.TRUE);
        } else {
            this.ignoreAnnotationDefaults.put(clazz, b);
        }
    }

    public void ignoreConstraintAnnotationsOnMember(Constrainable member, Boolean b) {
        this.annotationIgnoredForMembers.put(member, b);
    }

    public void ignoreConstraintAnnotationsForReturnValue(Constrainable member, Boolean b) {
        this.annotationIgnoresForReturnValues.put(member, b);
    }

    public void ignoreConstraintAnnotationsForCrossParameterConstraint(Constrainable member, Boolean b) {
        this.annotationIgnoresForCrossParameter.put(member, b);
    }

    public void ignoreConstraintAnnotationsOnParameter(Constrainable member, int index, Boolean b) {
        ExecutableParameterKey key = new ExecutableParameterKey(member, index);
        this.annotationIgnoresForMethodParameter.put(key, b);
    }

    public void ignoreClassLevelConstraintAnnotations(Class<?> clazz, boolean b) {
        this.annotationIgnoresForClasses.put(clazz, b);
    }

    private boolean areAllConstraintAnnotationsIgnoredFor(Class<?> clazz) {
        return this.ignoreAnnotationDefaults.containsKey(clazz) && this.ignoreAnnotationDefaults.get(clazz) != false;
    }

    public class ExecutableParameterKey {
        private final Constrainable constrainable;
        private final int index;

        public ExecutableParameterKey(Constrainable constrainable, int index) {
            this.constrainable = constrainable;
            this.index = index;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            ExecutableParameterKey that = (ExecutableParameterKey)o;
            if (this.index != that.index) {
                return false;
            }
            return !(this.constrainable != null ? !this.constrainable.equals(that.constrainable) : that.constrainable != null);
        }

        public int hashCode() {
            int result = this.constrainable != null ? this.constrainable.hashCode() : 0;
            result = 31 * result + this.index;
            return result;
        }
    }
}

