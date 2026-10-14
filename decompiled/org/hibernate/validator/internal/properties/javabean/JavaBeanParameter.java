/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.properties.javabean;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import org.hibernate.validator.internal.properties.javabean.JavaBeanAnnotatedElement;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.util.logging.Messages;

public class JavaBeanParameter
implements JavaBeanAnnotatedElement {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    private static final Annotation[] EMPTY_PARAMETER_ANNOTATIONS = new Annotation[0];
    private final int index;
    private final Parameter parameter;
    private final Class<?> type;
    private final Type genericType;

    JavaBeanParameter(int index, Parameter parameter, Class<?> type, Type genericType) {
        this.index = index;
        this.parameter = parameter;
        this.type = type;
        this.genericType = genericType;
    }

    public int getIndex() {
        return this.index;
    }

    @Override
    public Class<?> getType() {
        return this.type;
    }

    @Override
    public AnnotatedType getAnnotatedType() {
        return this.parameter.getAnnotatedType();
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        try {
            return this.parameter.getDeclaredAnnotations();
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            LOG.warn(Messages.MESSAGES.constraintOnConstructorOfNonStaticInnerClass(), ex);
            return EMPTY_PARAMETER_ANNOTATIONS;
        }
    }

    @Override
    public Type getGenericType() {
        return this.genericType;
    }

    @Override
    public TypeVariable<?>[] getTypeParameters() {
        return this.parameter.getType().getTypeParameters();
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
        return this.parameter.getAnnotation(annotationClass);
    }
}

