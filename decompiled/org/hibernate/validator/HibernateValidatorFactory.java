/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator;

import java.time.Duration;
import javax.validation.ValidatorFactory;
import org.hibernate.validator.HibernateValidatorContext;
import org.hibernate.validator.Incubating;
import org.hibernate.validator.spi.nodenameprovider.PropertyNodeNameProvider;
import org.hibernate.validator.spi.properties.GetterPropertySelectionStrategy;
import org.hibernate.validator.spi.scripting.ScriptEvaluatorFactory;

public interface HibernateValidatorFactory
extends ValidatorFactory {
    @Incubating
    public ScriptEvaluatorFactory getScriptEvaluatorFactory();

    @Incubating
    public Duration getTemporalValidationTolerance();

    @Incubating
    public GetterPropertySelectionStrategy getGetterPropertySelectionStrategy();

    @Incubating
    public PropertyNodeNameProvider getPropertyNodeNameProvider();

    @Override
    public HibernateValidatorContext usingContext();
}

