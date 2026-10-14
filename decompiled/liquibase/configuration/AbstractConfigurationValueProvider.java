/*
 * Decompiled with CFR 0.152.
 */
package liquibase.configuration;

import liquibase.command.CommandScope;
import liquibase.configuration.ConfigurationValueProvider;

public abstract class AbstractConfigurationValueProvider
implements ConfigurationValueProvider {
    @Override
    public void validate(CommandScope commandScope) throws IllegalArgumentException {
    }
}

