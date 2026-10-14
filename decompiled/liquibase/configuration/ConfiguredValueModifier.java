/*
 * Decompiled with CFR 0.152.
 */
package liquibase.configuration;

import liquibase.configuration.ConfiguredValue;
import liquibase.plugin.Plugin;

public interface ConfiguredValueModifier<DataType>
extends Plugin {
    public int getOrder();

    public void override(ConfiguredValue<DataType> var1);

    default public String override(String value) {
        ConfiguredValue valueWrapper = new ConfiguredValue(value, null, null);
        valueWrapper.override(value, "argument");
        this.override(valueWrapper);
        return String.valueOf(valueWrapper.getValue());
    }
}

