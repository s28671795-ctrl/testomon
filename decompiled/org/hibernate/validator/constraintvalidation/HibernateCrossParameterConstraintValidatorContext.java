/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.constraintvalidation;

import java.util.List;
import org.hibernate.validator.Incubating;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

@Incubating
public interface HibernateCrossParameterConstraintValidatorContext
extends HibernateConstraintValidatorContext {
    public List<String> getMethodParameterNames();
}

