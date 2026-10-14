/*
 * Decompiled with CFR 0.152.
 */
package liquibase.configuration;

import liquibase.configuration.ConfigurationValueProvider;

public class ProvidedValue {
    private final String requestedKey;
    private final String actualKey;
    private final String sourceDescription;
    private final ConfigurationValueProvider provider;
    private final Object value;

    public ProvidedValue(String requestedKey, String actualKey, Object value, String sourceDescription, ConfigurationValueProvider provider) {
        this.requestedKey = requestedKey;
        this.actualKey = actualKey;
        this.value = value;
        this.sourceDescription = sourceDescription;
        this.provider = provider;
    }

    public Object getValue() {
        return this.value;
    }

    public String getRequestedKey() {
        return this.requestedKey;
    }

    public String getActualKey() {
        return this.actualKey;
    }

    public String getSourceDescription() {
        return this.sourceDescription;
    }

    public ConfigurationValueProvider getProvider() {
        return this.provider;
    }

    public String describe() {
        return this.sourceDescription + " '" + this.actualKey + "'";
    }
}

