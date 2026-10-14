/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.cfg.context;

import org.hibernate.validator.cfg.context.MethodConstraintMappingContext;
import org.hibernate.validator.internal.cfg.context.ExecutableConstraintMappingContextImpl;
import org.hibernate.validator.internal.cfg.context.TypeConstraintMappingContextImpl;
import org.hibernate.validator.internal.properties.javabean.JavaBeanMethod;

class MethodConstraintMappingContextImpl
extends ExecutableConstraintMappingContextImpl
implements MethodConstraintMappingContext {
    MethodConstraintMappingContextImpl(TypeConstraintMappingContextImpl<?> typeContext, JavaBeanMethod callable) {
        super(typeContext, callable);
    }

    @Override
    public MethodConstraintMappingContext ignoreAnnotations(boolean ignoreAnnotations) {
        this.typeContext.mapping.getAnnotationProcessingOptions().ignoreConstraintAnnotationsOnMember(this.callable, ignoreAnnotations);
        return this;
    }
}

