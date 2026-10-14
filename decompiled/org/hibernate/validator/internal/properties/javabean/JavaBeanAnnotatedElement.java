/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.properties.javabean;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public interface JavaBeanAnnotatedElement {
    public Type getType();

    public AnnotatedType getAnnotatedType();

    public Annotation[] getDeclaredAnnotations();

    public Type getGenericType();

    public TypeVariable<?>[] getTypeParameters();

    public <A extends Annotation> A getAnnotation(Class<A> var1);

    default public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        return this.getAnnotation(annotationClass) != null;
    }
}

