/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.constraintvalidation;

import javax.validation.ConstraintValidatorContext;
import org.hibernate.validator.Incubating;
import org.hibernate.validator.constraintvalidation.HibernateConstraintViolationBuilder;

public interface HibernateConstraintValidatorContext
extends ConstraintValidatorContext {
    @Override
    public HibernateConstraintViolationBuilder buildConstraintViolationWithTemplate(String var1);

    public HibernateConstraintValidatorContext addMessageParameter(String var1, Object var2);

    public HibernateConstraintValidatorContext addExpressionVariable(String var1, Object var2);

    public HibernateConstraintValidatorContext withDynamicPayload(Object var1);

    @Incubating
    public <C> C getConstraintValidatorPayload(Class<C> var1);
}

