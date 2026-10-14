/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine;

import java.time.Duration;
import javax.validation.ClockProvider;
import javax.validation.MessageInterpolator;
import javax.validation.ParameterNameProvider;
import javax.validation.TraversableResolver;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext;
import org.hibernate.validator.internal.engine.constraintvalidation.HibernateConstraintValidatorInitializationContextImpl;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.internal.util.ExecutableParameterNameProvider;
import org.hibernate.validator.messageinterpolation.ExpressionLanguageFeatureLevel;
import org.hibernate.validator.spi.scripting.ScriptEvaluatorFactory;

public class ValidatorFactoryScopedContext {
    private final MessageInterpolator messageInterpolator;
    private final TraversableResolver traversableResolver;
    private final ExecutableParameterNameProvider parameterNameProvider;
    private final ClockProvider clockProvider;
    private final Duration temporalValidationTolerance;
    private final ScriptEvaluatorFactory scriptEvaluatorFactory;
    private final boolean failFast;
    private final boolean traversableResolverResultCacheEnabled;
    private final Object constraintValidatorPayload;
    private final ExpressionLanguageFeatureLevel constraintExpressionLanguageFeatureLevel;
    private final ExpressionLanguageFeatureLevel customViolationExpressionLanguageFeatureLevel;
    private final HibernateConstraintValidatorInitializationContextImpl constraintValidatorInitializationContext;

    ValidatorFactoryScopedContext(MessageInterpolator messageInterpolator, TraversableResolver traversableResolver, ExecutableParameterNameProvider parameterNameProvider, ClockProvider clockProvider, Duration temporalValidationTolerance, ScriptEvaluatorFactory scriptEvaluatorFactory, boolean failFast, boolean traversableResolverResultCacheEnabled, Object constraintValidatorPayload, ExpressionLanguageFeatureLevel constraintExpressionLanguageFeatureLevel, ExpressionLanguageFeatureLevel customViolationExpressionLanguageFeatureLevel) {
        this(messageInterpolator, traversableResolver, parameterNameProvider, clockProvider, temporalValidationTolerance, scriptEvaluatorFactory, failFast, traversableResolverResultCacheEnabled, constraintValidatorPayload, constraintExpressionLanguageFeatureLevel, customViolationExpressionLanguageFeatureLevel, new HibernateConstraintValidatorInitializationContextImpl(scriptEvaluatorFactory, clockProvider, temporalValidationTolerance));
    }

    private ValidatorFactoryScopedContext(MessageInterpolator messageInterpolator, TraversableResolver traversableResolver, ExecutableParameterNameProvider parameterNameProvider, ClockProvider clockProvider, Duration temporalValidationTolerance, ScriptEvaluatorFactory scriptEvaluatorFactory, boolean failFast, boolean traversableResolverResultCacheEnabled, Object constraintValidatorPayload, ExpressionLanguageFeatureLevel constraintExpressionLanguageFeatureLevel, ExpressionLanguageFeatureLevel customViolationExpressionLanguageFeatureLevel, HibernateConstraintValidatorInitializationContextImpl constraintValidatorInitializationContext) {
        this.messageInterpolator = messageInterpolator;
        this.traversableResolver = traversableResolver;
        this.parameterNameProvider = parameterNameProvider;
        this.clockProvider = clockProvider;
        this.temporalValidationTolerance = temporalValidationTolerance;
        this.scriptEvaluatorFactory = scriptEvaluatorFactory;
        this.failFast = failFast;
        this.traversableResolverResultCacheEnabled = traversableResolverResultCacheEnabled;
        this.constraintValidatorPayload = constraintValidatorPayload;
        this.constraintExpressionLanguageFeatureLevel = constraintExpressionLanguageFeatureLevel;
        this.customViolationExpressionLanguageFeatureLevel = customViolationExpressionLanguageFeatureLevel;
        this.constraintValidatorInitializationContext = constraintValidatorInitializationContext;
    }

    public MessageInterpolator getMessageInterpolator() {
        return this.messageInterpolator;
    }

    public TraversableResolver getTraversableResolver() {
        return this.traversableResolver;
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

    public HibernateConstraintValidatorInitializationContext getConstraintValidatorInitializationContext() {
        return this.constraintValidatorInitializationContext;
    }

    public ExpressionLanguageFeatureLevel getConstraintExpressionLanguageFeatureLevel() {
        return this.constraintExpressionLanguageFeatureLevel;
    }

    public ExpressionLanguageFeatureLevel getCustomViolationExpressionLanguageFeatureLevel() {
        return this.customViolationExpressionLanguageFeatureLevel;
    }

    static class Builder {
        private final ValidatorFactoryScopedContext defaultContext;
        private MessageInterpolator messageInterpolator;
        private TraversableResolver traversableResolver;
        private ExecutableParameterNameProvider parameterNameProvider;
        private ClockProvider clockProvider;
        private ScriptEvaluatorFactory scriptEvaluatorFactory;
        private Duration temporalValidationTolerance;
        private boolean failFast;
        private boolean traversableResolverResultCacheEnabled;
        private Object constraintValidatorPayload;
        private ExpressionLanguageFeatureLevel constraintExpressionLanguageFeatureLevel;
        private ExpressionLanguageFeatureLevel customViolationExpressionLanguageFeatureLevel;
        private HibernateConstraintValidatorInitializationContextImpl constraintValidatorInitializationContext;

        Builder(ValidatorFactoryScopedContext defaultContext) {
            Contracts.assertNotNull(defaultContext, "Default context cannot be null.");
            this.defaultContext = defaultContext;
            this.messageInterpolator = defaultContext.messageInterpolator;
            this.traversableResolver = defaultContext.traversableResolver;
            this.parameterNameProvider = defaultContext.parameterNameProvider;
            this.clockProvider = defaultContext.clockProvider;
            this.scriptEvaluatorFactory = defaultContext.scriptEvaluatorFactory;
            this.temporalValidationTolerance = defaultContext.temporalValidationTolerance;
            this.failFast = defaultContext.failFast;
            this.traversableResolverResultCacheEnabled = defaultContext.traversableResolverResultCacheEnabled;
            this.constraintValidatorPayload = defaultContext.constraintValidatorPayload;
            this.constraintExpressionLanguageFeatureLevel = defaultContext.constraintExpressionLanguageFeatureLevel;
            this.customViolationExpressionLanguageFeatureLevel = defaultContext.customViolationExpressionLanguageFeatureLevel;
            this.constraintValidatorInitializationContext = defaultContext.constraintValidatorInitializationContext;
        }

        public Builder setMessageInterpolator(MessageInterpolator messageInterpolator) {
            this.messageInterpolator = messageInterpolator == null ? this.defaultContext.messageInterpolator : messageInterpolator;
            return this;
        }

        public Builder setTraversableResolver(TraversableResolver traversableResolver) {
            this.traversableResolver = traversableResolver == null ? this.defaultContext.traversableResolver : traversableResolver;
            return this;
        }

        public Builder setParameterNameProvider(ParameterNameProvider parameterNameProvider) {
            this.parameterNameProvider = parameterNameProvider == null ? this.defaultContext.parameterNameProvider : new ExecutableParameterNameProvider(parameterNameProvider);
            return this;
        }

        public Builder setClockProvider(ClockProvider clockProvider) {
            this.clockProvider = clockProvider == null ? this.defaultContext.clockProvider : clockProvider;
            return this;
        }

        public Builder setTemporalValidationTolerance(Duration temporalValidationTolerance) {
            this.temporalValidationTolerance = temporalValidationTolerance == null ? Duration.ZERO : temporalValidationTolerance.abs();
            return this;
        }

        public Builder setScriptEvaluatorFactory(ScriptEvaluatorFactory scriptEvaluatorFactory) {
            this.scriptEvaluatorFactory = scriptEvaluatorFactory == null ? this.defaultContext.scriptEvaluatorFactory : scriptEvaluatorFactory;
            return this;
        }

        public Builder setFailFast(boolean failFast) {
            this.failFast = failFast;
            return this;
        }

        public Builder setTraversableResolverResultCacheEnabled(boolean traversableResolverResultCacheEnabled) {
            this.traversableResolverResultCacheEnabled = traversableResolverResultCacheEnabled;
            return this;
        }

        public Builder setConstraintValidatorPayload(Object constraintValidatorPayload) {
            this.constraintValidatorPayload = constraintValidatorPayload;
            return this;
        }

        public Builder setConstraintExpressionLanguageFeatureLevel(ExpressionLanguageFeatureLevel expressionLanguageFeatureLevel) {
            this.constraintExpressionLanguageFeatureLevel = expressionLanguageFeatureLevel;
            return this;
        }

        public Builder setCustomViolationExpressionLanguageFeatureLevel(ExpressionLanguageFeatureLevel expressionLanguageFeatureLevel) {
            this.customViolationExpressionLanguageFeatureLevel = expressionLanguageFeatureLevel;
            return this;
        }

        public ValidatorFactoryScopedContext build() {
            return new ValidatorFactoryScopedContext(this.messageInterpolator, this.traversableResolver, this.parameterNameProvider, this.clockProvider, this.temporalValidationTolerance, this.scriptEvaluatorFactory, this.failFast, this.traversableResolverResultCacheEnabled, this.constraintValidatorPayload, this.constraintExpressionLanguageFeatureLevel, this.customViolationExpressionLanguageFeatureLevel, HibernateConstraintValidatorInitializationContextImpl.of(this.constraintValidatorInitializationContext, this.scriptEvaluatorFactory, this.clockProvider, this.temporalValidationTolerance));
        }
    }
}

