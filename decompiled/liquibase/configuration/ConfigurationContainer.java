/*
 * Decompiled with CFR 0.152.
 */
package liquibase.configuration;

import java.util.Set;
import liquibase.configuration.ConfigurationProperty;

public interface ConfigurationContainer {
    public ConfigurationProperty getProperty(String var1);

    public Set<ConfigurationProperty> getProperties();

    public <T> T getValue(String var1, Class<T> var2);

    public void setValue(String var1, Object var2);

    public String getNamespace();
}

