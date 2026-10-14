/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.cfg.context;

import org.hibernate.validator.cfg.context.ConstructorConstraintMappingContext;
import org.hibernate.validator.internal.cfg.context.ExecutableConstraintMappingContextImpl;
import org.hibernate.validator.internal.cfg.context.TypeConstraintMappingContextImpl;
import org.hibernate.validator.internal.properties.javabean.JavaBeanConstructor;

class ConstructorConstraintMappingContextImpl
extends ExecutableConstraintMappingContextImpl
implements ConstructorConstraintMappingContext {
    <T> ConstructorConstraintMappingContextImpl(TypeConstraintMappingContextImpl<T> typeContext, JavaBeanConstructor constructor) {
        super(typeContext, constructor);
    }

    @Override
    public ConstructorConstraintMappingContext ignoreAnnotations(boolean ignoreAnnotations) {
        this.typeContext.mapping.getAnnotationProcessingOptions().ignoreConstraintAnnotationsOnMember(this.callable, ignoreAnnotations);
        return this;
    }
}

