/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine;

import javax.validation.spi.BootstrapState;
import javax.validation.spi.ConfigurationState;
import javax.validation.spi.ValidationProvider;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.internal.engine.AbstractConfigurationImpl;

public class ConfigurationImpl
extends AbstractConfigurationImpl<HibernateValidatorConfiguration>
implements HibernateValidatorConfiguration,
ConfigurationState {
    public ConfigurationImpl(BootstrapState state) {
        super(state);
    }

    public ConfigurationImpl(ValidationProvider<?> provider) {
        super(provider);
    }

    @Override
    protected boolean preloadResourceBundles() {
        return false;
    }
}

