/*
 * Decompiled with CFR 0.152.
 */
package liquibase.configuration.core;

import java.util.HashMap;
import java.util.Map;
import liquibase.configuration.AbstractMapConfigurationValueProvider;
import liquibase.configuration.ConfigurationDefinition;

public class DeprecatedConfigurationValueProvider
extends AbstractMapConfigurationValueProvider {
    private static final Map<String, Object> data = new HashMap<String, Object>();

    @Override
    protected Map<?, ?> getMap() {
        return data;
    }

    @Override
    public int getPrecedence() {
        return 350;
    }

    public static void setData(String key, Object value) {
        data.put(key, value);
    }

    public static <T> void setData(ConfigurationDefinition<T> configuration, T value) {
        data.put(configuration.getKey(), value);
    }

    public static void clearData() {
        data.clear();
    }

    @Override
    protected String getSourceDescription() {
        return "Legacy configuration";
    }
}

