/*
 * Decompiled with CFR 0.152.
 */
package liquibase.configuration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import liquibase.Scope;
import liquibase.configuration.ConfigurationDefinition;
import liquibase.configuration.ConfigurationProperty;
import liquibase.configuration.ConfiguredValue;
import liquibase.configuration.LiquibaseConfiguration;
import liquibase.configuration.core.DeprecatedConfigurationValueProvider;

public abstract class AbstractConfigurationContainer
implements liquibase.configuration.ConfigurationContainer {
    private final String namespace;
    private final ConfigurationContainer container;
    private final Map<String, ConfigurationProperty> properties = new HashMap<String, ConfigurationProperty>();

    public AbstractConfigurationContainer(String namespace) {
        this.namespace = namespace;
        this.container = new ConfigurationContainer();
    }

    protected ConfigurationContainer getContainer() {
        return this.container;
    }

    @Override
    public ConfigurationProperty getProperty(String propertyName) {
        return this.properties.get(propertyName);
    }

    @Override
    public Set<ConfigurationProperty> getProperties() {
        return new HashSet<ConfigurationProperty>(this.properties.values());
    }

    @Override
    public <T> T getValue(String propertyName, Class<T> returnType) {
        ConfiguredValue currentValue = Scope.getCurrentScope().getSingleton(LiquibaseConfiguration.class).getCurrentConfiguredValue(null, null, this.namespace + "." + propertyName);
        return (T)currentValue.getValue();
    }

    @Override
    public void setValue(String propertyName, Object value) {
        DeprecatedConfigurationValueProvider.setData(this.namespace + "." + propertyName, value);
    }

    @Override
    public String getNamespace() {
        return this.namespace;
    }

    protected class ConfigurationContainer {
        protected ConfigurationContainer() {
        }

        public ConfigurationProperty addProperty(String propertyName, Class type) {
            ConfigurationDefinition.Builder builder = new ConfigurationDefinition.Builder(AbstractConfigurationContainer.this.namespace);
            ConfigurationDefinition.Building newDefinition = builder.define(propertyName, type);
            ConfigurationProperty property = new ConfigurationProperty(AbstractConfigurationContainer.this.namespace, newDefinition);
            AbstractConfigurationContainer.this.properties.put(propertyName, property);
            return property;
        }

        public ConfigurationProperty getProperty(String propertyName) {
            return AbstractConfigurationContainer.this.getProperty(propertyName);
        }

        public <T> T getValue(String propertyName, Class<T> returnType) {
            return AbstractConfigurationContainer.this.getValue(propertyName, returnType);
        }

        public void setValue(String propertyName, Object value) {
            AbstractConfigurationContainer.this.setValue(propertyName, value);
        }
    }

    protected static class DelegatedConfigurationContainer
    extends AbstractConfigurationContainer {
        public DelegatedConfigurationContainer(String namespace) {
            super(namespace);
        }
    }
}

