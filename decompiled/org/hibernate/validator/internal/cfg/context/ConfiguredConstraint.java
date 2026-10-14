/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.cfg.context;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.validation.ValidationException;
import org.hibernate.validator.cfg.AnnotationDef;
import org.hibernate.validator.cfg.ConstraintDef;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.properties.Callable;
import org.hibernate.validator.internal.properties.javabean.JavaBeanField;
import org.hibernate.validator.internal.properties.javabean.JavaBeanGetter;
import org.hibernate.validator.internal.util.annotation.AnnotationDescriptor;
import org.hibernate.validator.internal.util.annotation.ConstraintAnnotationDescriptor;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.util.privilegedactions.GetDeclaredMethodHandle;

class ConfiguredConstraint<A extends Annotation> {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    private static final MethodHandle CREATE_ANNOTATION_DESCRIPTOR_METHOD_HANDLE = ConfiguredConstraint.run(GetDeclaredMethodHandle.andMakeAccessible(MethodHandles.lookup(), AnnotationDef.class, "createAnnotationDescriptor", new Class[0]));
    private final ConstraintDef<?, A> constraint;
    private final ConstraintLocation location;

    private ConfiguredConstraint(ConstraintDef<?, A> constraint, ConstraintLocation location) {
        this.constraint = constraint;
        this.location = location;
    }

    static <A extends Annotation> ConfiguredConstraint<A> forType(ConstraintDef<?, A> constraint, Class<?> beanType) {
        return new ConfiguredConstraint<A>(constraint, ConstraintLocation.forClass(beanType));
    }

    static <A extends Annotation> ConfiguredConstraint<A> forField(ConstraintDef<?, A> constraint, JavaBeanField javaBeanField) {
        return new ConfiguredConstraint<A>(constraint, ConstraintLocation.forField(javaBeanField));
    }

    static <A extends Annotation> ConfiguredConstraint<A> forGetter(ConstraintDef<?, A> constraint, JavaBeanGetter javaBeanGetter) {
        return ConfiguredConstraint.forExecutable(constraint, javaBeanGetter);
    }

    public static <A extends Annotation> ConfiguredConstraint<A> forParameter(ConstraintDef<?, A> constraint, Callable callable, int parameterIndex) {
        return new ConfiguredConstraint<A>(constraint, ConstraintLocation.forParameter(callable, parameterIndex));
    }

    public static <A extends Annotation> ConfiguredConstraint<A> forExecutable(ConstraintDef<?, A> constraint, Callable callable) {
        return new ConfiguredConstraint<A>(constraint, ConstraintLocation.forReturnValue(callable));
    }

    public static <A extends Annotation> ConfiguredConstraint<A> forCrossParameter(ConstraintDef<?, A> constraint, Callable callable) {
        return new ConfiguredConstraint<A>(constraint, ConstraintLocation.forCrossParameter(callable));
    }

    public static <A extends Annotation> ConfiguredConstraint<A> forTypeArgument(ConstraintDef<?, A> constraint, ConstraintLocation delegate, TypeVariable<?> typeArgument, Type typeOfAnnotatedElement) {
        return new ConfiguredConstraint<A>(constraint, ConstraintLocation.forTypeArgument(delegate, typeArgument, typeOfAnnotatedElement));
    }

    public ConstraintDef<?, A> getConstraint() {
        return this.constraint;
    }

    public ConstraintLocation getLocation() {
        return this.location;
    }

    public ConstraintAnnotationDescriptor<A> createAnnotationDescriptor() {
        try {
            AnnotationDescriptor annotationDescriptor = CREATE_ANNOTATION_DESCRIPTOR_METHOD_HANDLE.invoke(this.constraint);
            return new ConstraintAnnotationDescriptor(annotationDescriptor);
        }
        catch (Throwable e) {
            if (e instanceof ValidationException) {
                throw (ValidationException)e;
            }
            throw LOG.getUnableToCreateAnnotationDescriptor(this.constraint.getClass(), e);
        }
    }

    public String toString() {
        return this.constraint.toString();
    }

    private static <V> V run(PrivilegedAction<V> action) {
        return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
    }
}

