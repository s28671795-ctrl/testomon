/*
 * Decompiled with CFR 0.152.
 */
package liquibase.configuration;

import liquibase.command.CommandScope;
import liquibase.configuration.ProvidedValue;

public interface ConfigurationValueProvider {
    public int getPrecedence();

    public ProvidedValue getProvidedValue(String ... var1);

    public void validate(CommandScope var1) throws IllegalArgumentException;
}

