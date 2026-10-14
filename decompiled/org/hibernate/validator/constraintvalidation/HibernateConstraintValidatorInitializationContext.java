/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.constraintvalidation;

import java.time.Duration;
import javax.validation.ClockProvider;
import org.hibernate.validator.Incubating;
import org.hibernate.validator.spi.scripting.ScriptEvaluator;

@Incubating
public interface HibernateConstraintValidatorInitializationContext {
    public ScriptEvaluator getScriptEvaluatorForLanguage(String var1);

    public ClockProvider getClockProvider();

    @Incubating
    public Duration getTemporalValidationTolerance();
}

