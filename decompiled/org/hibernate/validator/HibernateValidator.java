/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator;

import javax.validation.Configuration;
import javax.validation.ValidatorFactory;
import javax.validation.spi.BootstrapState;
import javax.validation.spi.ConfigurationState;
import javax.validation.spi.ValidationProvider;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.internal.engine.ConfigurationImpl;
import org.hibernate.validator.internal.engine.ValidatorFactoryImpl;

public class HibernateValidator
implements ValidationProvider<HibernateValidatorConfiguration> {
    @Override
    public HibernateValidatorConfiguration createSpecializedConfiguration(BootstrapState state) {
        return (HibernateValidatorConfiguration)HibernateValidatorConfiguration.class.cast(new ConfigurationImpl(this));
    }

    @Override
    public Configuration<?> createGenericConfiguration(BootstrapState state) {
        return new ConfigurationImpl(state);
    }

    @Override
    public ValidatorFactory buildValidatorFactory(ConfigurationState configurationState) {
        return new ValidatorFactoryImpl(configurationState);
    }
}

