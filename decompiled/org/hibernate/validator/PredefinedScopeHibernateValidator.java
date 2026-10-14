/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator;

import javax.validation.Configuration;
import javax.validation.ValidatorFactory;
import javax.validation.spi.BootstrapState;
import javax.validation.spi.ConfigurationState;
import javax.validation.spi.ValidationProvider;
import org.hibernate.validator.Incubating;
import org.hibernate.validator.PredefinedScopeHibernateValidatorConfiguration;
import org.hibernate.validator.internal.engine.PredefinedScopeConfigurationImpl;
import org.hibernate.validator.internal.engine.PredefinedScopeValidatorFactoryImpl;

@Incubating
public class PredefinedScopeHibernateValidator
implements ValidationProvider<PredefinedScopeHibernateValidatorConfiguration> {
    @Override
    public PredefinedScopeHibernateValidatorConfiguration createSpecializedConfiguration(BootstrapState state) {
        return new PredefinedScopeConfigurationImpl(this);
    }

    @Override
    public Configuration<?> createGenericConfiguration(BootstrapState state) {
        return new PredefinedScopeConfigurationImpl(state);
    }

    @Override
    public ValidatorFactory buildValidatorFactory(ConfigurationState configurationState) {
        return new PredefinedScopeValidatorFactoryImpl(configurationState);
    }
}

