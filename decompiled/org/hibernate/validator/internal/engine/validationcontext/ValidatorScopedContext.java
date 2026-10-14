/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.validationcontext;

import java.time.Duration;
import javax.validation.ClockProvider;
import javax.validation.MessageInterpolator;
import org.hibernate.validator.internal.engine.ValidatorFactoryScopedContext;
import org.hibernate.validator.internal.util.ExecutableParameterNameProvider;
import org.hibernate.validator.messageinterpolation.ExpressionLanguageFeatureLevel;
import org.hibernate.validator.spi.scripting.ScriptEvaluatorFactory;

public class ValidatorScopedContext {
    private final MessageInterpolator messageInterpolator;
    private final ExecutableParameterNameProvider parameterNameProvider;
    private final ClockProvider clockProvider;
    private final Duration temporalValidationTolerance;
    private final ScriptEvaluatorFactory scriptEvaluatorFactory;
    private final boolean failFast;
    private final boolean traversableResolverResultCacheEnabled;
    private final Object constraintValidatorPayload;
    private final ExpressionLanguageFeatureLevel constraintExpressionLanguageFeatureLevel;
    private final ExpressionLanguageFeatureLevel customViolationExpressionLanguageFeatureLevel;

    public ValidatorScopedContext(ValidatorFactoryScopedContext validatorFactoryScopedContext) {
        this.messageInterpolator = validatorFactoryScopedContext.getMessageInterpolator();
        this.parameterNameProvider = validatorFactoryScopedContext.getParameterNameProvider();
        this.clockProvider = validatorFactoryScopedContext.getClockProvider();
        this.temporalValidationTolerance = validatorFactoryScopedContext.getTemporalValidationTolerance();
        this.scriptEvaluatorFactory = validatorFactoryScopedContext.getScriptEvaluatorFactory();
        this.failFast = validatorFactoryScopedContext.isFailFast();
        this.traversableResolverResultCacheEnabled = validatorFactoryScopedContext.isTraversableResolverResultCacheEnabled();
        this.constraintValidatorPayload = validatorFactoryScopedContext.getConstraintValidatorPayload();
        this.constraintExpressionLanguageFeatureLevel = validatorFactoryScopedContext.getConstraintExpressionLanguageFeatureLevel();
        this.customViolationExpressionLanguageFeatureLevel = validatorFactoryScopedContext.getCustomViolationExpressionLanguageFeatureLevel();
    }

    public MessageInterpolator getMessageInterpolator() {
        return this.messageInterpolator;
    }

    public ExecutableParameterNameProvider getParameterNameProvider() {
        return this.parameterNameProvider;
    }

    public ClockProvider getClockProvider() {
        return this.clockProvider;
    }

    public Duration getTemporalValidationTolerance() {
        return this.temporalValidationTolerance;
    }

    public ScriptEvaluatorFactory getScriptEvaluatorFactory() {
        return this.scriptEvaluatorFactory;
    }

    public boolean isFailFast() {
        return this.failFast;
    }

    public boolean isTraversableResolverResultCacheEnabled() {
        return this.traversableResolverResultCacheEnabled;
    }

    public Object getConstraintValidatorPayload() {
        return this.constraintValidatorPayload;
    }

    public ExpressionLanguageFeatureLevel getConstraintExpressionLanguageFeatureLevel() {
        return this.constraintExpressionLanguageFeatureLevel;
    }

    public ExpressionLanguageFeatureLevel getCustomViolationExpressionLanguageFeatureLevel() {
        return this.customViolationExpressionLanguageFeatureLevel;
    }
}

