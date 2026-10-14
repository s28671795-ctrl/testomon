/*
 * Decompiled with CFR 0.152.
 */
package liquibase.configuration;

import java.math.BigDecimal;
import java.util.List;
import liquibase.configuration.ConfigurationDefinition;
import liquibase.configuration.ConfigurationValueHandler;
import liquibase.configuration.core.DeprecatedConfigurationValueProvider;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.util.StringUtil;

public class ConfigurationProperty {
    private final ConfigurationDefinition.Building definitionBuilder;
    private ConfigurationDefinition definition;
    private final String namespace;

    public ConfigurationProperty(String namespace, ConfigurationDefinition.Building definitionBuilder) {
        this.namespace = namespace;
        this.definitionBuilder = definitionBuilder;
        this.definition = definitionBuilder.buildTemporary();
    }

    public String getName() {
        return this.definition.getKey().replace(this.namespace + ".", "");
    }

    public String getNamespace() {
        return this.namespace;
    }

    public Class getType() {
        return this.definition.getDataType();
    }

    protected Object valueOf(Object value) {
        Class type = this.definition.getDataType();
        if (value == null) {
            return value;
        }
        if (type.isAssignableFrom(value.getClass())) {
            return value;
        }
        if (value instanceof String) {
            if (type.equals(Boolean.class)) {
                return Boolean.valueOf((String)value);
            }
            if (type.equals(Integer.class)) {
                return Integer.valueOf((String)value);
            }
            if (type.equals(BigDecimal.class)) {
                return new BigDecimal((String)value);
            }
            if (type.equals(Long.class)) {
                return Long.valueOf((String)value);
            }
            if (type.equals(List.class)) {
                return StringUtil.splitAndTrim((String)value, ",");
            }
            throw new UnexpectedLiquibaseException("Cannot parse property " + value.getClass().getSimpleName() + " to a " + type.getSimpleName());
        }
        throw new UnexpectedLiquibaseException("Could not convert " + value.getClass().getSimpleName() + " to a " + type.getSimpleName());
    }

    public Object getValue() {
        return this.definition.getCurrentValue();
    }

    public <T> T getValue(Class<T> type) {
        if (!this.definition.getDataType().isAssignableFrom(type)) {
            throw new UnexpectedLiquibaseException("Property " + this.definition.getDataType() + " on is of type " + this.definition.getDataType().getSimpleName() + ", not " + type.getSimpleName());
        }
        return (T)this.definition.getCurrentValue();
    }

    public void setValue(Object value) {
        DeprecatedConfigurationValueProvider.setData(this.definition, value);
    }

    public ConfigurationProperty addAlias(String ... aliases) {
        if (aliases != null) {
            for (String alias : aliases) {
                this.definitionBuilder.addAliasKey(alias);
            }
            this.definition = this.definitionBuilder.buildTemporary();
        }
        return this;
    }

    public String getDescription() {
        return this.definition.getDescription();
    }

    public ConfigurationProperty setDescription(String description) {
        this.definitionBuilder.setDescription(description);
        this.definition = this.definitionBuilder.buildTemporary();
        return this;
    }

    public Object getDefaultValue() {
        return this.definition.getDefaultValue();
    }

    public ConfigurationProperty setDefaultValue(Object defaultValue) {
        this.definitionBuilder.setDefaultValue(defaultValue);
        this.definition = this.definitionBuilder.buildTemporary();
        return this;
    }

    public boolean getWasOverridden() {
        return !this.definition.getCurrentConfiguredValue().wasDefaultValueUsed();
    }

    public ConfigurationProperty setValueHandler(ConfigurationValueHandler handler) {
        this.definitionBuilder.setValueHandler(handler::convert);
        this.definition = this.definitionBuilder.buildTemporary();
        return this;
    }
}

