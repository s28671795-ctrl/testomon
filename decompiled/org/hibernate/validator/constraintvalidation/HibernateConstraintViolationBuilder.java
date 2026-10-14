/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.constraintvalidation;

import javax.validation.ConstraintValidatorContext;
import org.hibernate.validator.Incubating;
import org.hibernate.validator.messageinterpolation.ExpressionLanguageFeatureLevel;

public interface HibernateConstraintViolationBuilder
extends ConstraintValidatorContext.ConstraintViolationBuilder {
    @Incubating
    default public HibernateConstraintViolationBuilder enableExpressionLanguage() {
        return this.enableExpressionLanguage(ExpressionLanguageFeatureLevel.DEFAULT);
    }

    @Incubating
    public HibernateConstraintViolationBuilder enableExpressionLanguage(ExpressionLanguageFeatureLevel var1);
}

