/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator;

import java.time.Duration;
import javax.validation.ClockProvider;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.MessageInterpolator;
import javax.validation.ParameterNameProvider;
import javax.validation.TraversableResolver;
import javax.validation.ValidatorContext;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.Incubating;

public interface HibernateValidatorContext
extends ValidatorContext {
    @Override
    public HibernateValidatorContext messageInterpolator(MessageInterpolator var1);

    @Override
    public HibernateValidatorContext traversableResolver(TraversableResolver var1);

    @Override
    public HibernateValidatorContext constraintValidatorFactory(ConstraintValidatorFactory var1);

    @Override
    public HibernateValidatorContext parameterNameProvider(ParameterNameProvider var1);

    @Override
    public HibernateValidatorContext clockProvider(ClockProvider var1);

    @Override
    public HibernateValidatorContext addValueExtractor(ValueExtractor<?> var1);

    public HibernateValidatorContext failFast(boolean var1);

    public HibernateValidatorContext allowOverridingMethodAlterParameterConstraint(boolean var1);

    public HibernateValidatorContext allowMultipleCascadedValidationOnReturnValues(boolean var1);

    public HibernateValidatorContext allowParallelMethodsDefineParameterConstraints(boolean var1);

    public HibernateValidatorContext enableTraversableResolverResultCache(boolean var1);

    @Incubating
    public HibernateValidatorContext temporalValidationTolerance(Duration var1);

    @Incubating
    public HibernateValidatorContext constraintValidatorPayload(Object var1);
}

