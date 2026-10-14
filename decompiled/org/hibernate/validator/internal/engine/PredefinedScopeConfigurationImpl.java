/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine;

import java.util.Collections;
import java.util.Locale;
import java.util.Set;
import javax.validation.spi.BootstrapState;
import javax.validation.spi.ConfigurationState;
import javax.validation.spi.ValidationProvider;
import org.hibernate.validator.PredefinedScopeHibernateValidatorConfiguration;
import org.hibernate.validator.internal.engine.AbstractConfigurationImpl;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.internal.util.logging.Messages;

public class PredefinedScopeConfigurationImpl
extends AbstractConfigurationImpl<PredefinedScopeHibernateValidatorConfiguration>
implements PredefinedScopeHibernateValidatorConfiguration,
ConfigurationState {
    private Set<String> builtinConstraints = Collections.emptySet();
    private Set<Class<?>> beanClassesToInitialize;

    public PredefinedScopeConfigurationImpl(BootstrapState state) {
        super(state);
    }

    public PredefinedScopeConfigurationImpl(ValidationProvider<?> validationProvider) {
        super(validationProvider);
    }

    @Override
    public PredefinedScopeHibernateValidatorConfiguration builtinConstraints(Set<String> constraints) {
        this.builtinConstraints = CollectionHelper.toImmutableSet(constraints);
        return (PredefinedScopeHibernateValidatorConfiguration)this.thisAsT();
    }

    @Override
    public PredefinedScopeHibernateValidatorConfiguration initializeBeanMetaData(Set<Class<?>> beanMetaDataToInitialize) {
        this.beanClassesToInitialize = CollectionHelper.toImmutableSet(beanMetaDataToInitialize);
        return (PredefinedScopeHibernateValidatorConfiguration)this.thisAsT();
    }

    public Set<String> getBuiltinConstraints() {
        return this.builtinConstraints;
    }

    public Set<Class<?>> getBeanClassesToInitialize() {
        return this.beanClassesToInitialize;
    }

    @Override
    public PredefinedScopeHibernateValidatorConfiguration initializeLocales(Set<Locale> localesToInitialize) {
        Contracts.assertNotNull(localesToInitialize, Messages.MESSAGES.parameterMustNotBeNull("localesToInitialize"));
        this.locales(localesToInitialize);
        return (PredefinedScopeHibernateValidatorConfiguration)this.thisAsT();
    }

    @Override
    protected boolean preloadResourceBundles() {
        return true;
    }
}

