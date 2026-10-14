/*
 * Decompiled with CFR 0.152.
 */
package liquibase.configuration.core;

import java.util.Properties;
import liquibase.Scope;
import liquibase.configuration.AbstractConfigurationValueProvider;
import liquibase.configuration.ProvidedValue;

public class ScopeValueProvider
extends AbstractConfigurationValueProvider {
    @Override
    public int getPrecedence() {
        return 400;
    }

    @Override
    public ProvidedValue getProvidedValue(String ... keyAndAliases) {
        if (keyAndAliases == null || keyAndAliases.length == 0) {
            return null;
        }
        for (String key : keyAndAliases) {
            Class<Object> value = Scope.getCurrentScope().get(key, Object.class);
            if (value == null) continue;
            return new ProvidedValue(keyAndAliases[0], key, value, "Scoped value", this);
        }
        return null;
    }

    protected Properties getSystemProperties() {
        return System.getProperties();
    }
}

