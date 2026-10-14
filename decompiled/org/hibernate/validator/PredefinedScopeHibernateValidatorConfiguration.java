/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator;

import java.util.Locale;
import java.util.Set;
import org.hibernate.validator.BaseHibernateValidatorConfiguration;
import org.hibernate.validator.Incubating;

@Incubating
public interface PredefinedScopeHibernateValidatorConfiguration
extends BaseHibernateValidatorConfiguration<PredefinedScopeHibernateValidatorConfiguration> {
    @Incubating
    public PredefinedScopeHibernateValidatorConfiguration builtinConstraints(Set<String> var1);

    @Incubating
    public PredefinedScopeHibernateValidatorConfiguration initializeBeanMetaData(Set<Class<?>> var1);

    @Deprecated
    @Incubating
    public PredefinedScopeHibernateValidatorConfiguration initializeLocales(Set<Locale> var1);
}

