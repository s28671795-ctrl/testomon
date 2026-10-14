/*
 * Decompiled with CFR 0.152.
 */
package liquibase.configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;
import liquibase.Scope;
import liquibase.configuration.AbstractConfigurationValueProvider;
import liquibase.configuration.ConfigurationValueConverter;
import liquibase.configuration.ConfigurationValueObfuscator;
import liquibase.configuration.ConfigurationValueProvider;
import liquibase.configuration.ConfigurationValueUtils;
import liquibase.configuration.ConfiguredValue;
import liquibase.configuration.LiquibaseConfiguration;
import liquibase.configuration.ProvidedValue;
import liquibase.util.ObjectUtil;
import liquibase.util.StringUtil;

public class ConfigurationDefinition<DataType>
implements Comparable<ConfigurationDefinition<DataType>> {
    private final String key;
    private final Set<String> aliasKeys = new TreeSet<String>();
    private final Class<DataType> dataType;
    private String description;
    private DataType defaultValue;
    private String defaultValueDescription;
    private boolean commonlyUsed;
    private boolean internal;
    private ConfigurationValueConverter<DataType> valueConverter;
    private ConfigurationValueObfuscator<DataType> valueObfuscator;
    private static final String ALLOWED_KEY_REGEX = "[a-zA-Z0-9._]+";
    private static final Pattern ALLOWED_KEY_PATTERN = Pattern.compile("[a-zA-Z0-9._]+");
    private boolean loggedUsingDefault = false;
    private boolean hidden = false;

    private ConfigurationDefinition(String key, Class<DataType> dataType) throws IllegalArgumentException {
        if (!ALLOWED_KEY_PATTERN.matcher(key).matches()) {
            throw new IllegalArgumentException("Invalid key format: " + key);
        }
        this.key = key;
        this.dataType = dataType;
        this.valueConverter = value -> ObjectUtil.convert(value, dataType);
    }

    public DataType getCurrentValue() {
        Object value = this.getCurrentConfiguredValue().getProvidedValue().getValue();
        try {
            return (DataType)value;
        }
        catch (ClassCastException e) {
            throw new IllegalArgumentException("The current value of " + this.key + " not the expected type: " + e.getMessage(), e);
        }
    }

    public ConfigurationValueConverter<DataType> getValueConverter() {
        return this.valueConverter;
    }

    public DataType getCurrentValueObfuscated() {
        return this.getCurrentConfiguredValue().getValueObfuscated();
    }

    public ConfiguredValue<DataType> getCurrentConfiguredValue() {
        return this.getCurrentConfiguredValue(new ConfigurationValueProvider[0]);
    }

    public ConfiguredValue<DataType> getCurrentConfiguredValue(ConfigurationValueProvider ... additionalValueProviders) {
        LiquibaseConfiguration liquibaseConfiguration = Scope.getCurrentScope().getSingleton(LiquibaseConfiguration.class);
        ArrayList<String> keyList = new ArrayList<String>();
        keyList.add(this.getKey());
        keyList.addAll(this.getAliasKeys());
        ConfiguredValue<DataType> configurationValue = liquibaseConfiguration.getCurrentConfiguredValue(this.valueConverter, this.valueObfuscator, additionalValueProviders, keyList.toArray(new String[0]));
        if (!configurationValue.found()) {
            this.defaultValue = this.getDefaultValue();
            if (this.defaultValue != null) {
                DataType obfuscatedValue = this.valueObfuscator == null ? this.defaultValue : this.valueObfuscator.obfuscate(this.defaultValue);
                if (!this.loggedUsingDefault) {
                    Scope.getCurrentScope().getLog(this.getClass()).fine("Configuration " + this.key + " is using the default value of " + obfuscatedValue);
                    this.loggedUsingDefault = true;
                }
                configurationValue.override(new DefaultValueProvider(this.getDefaultValue()).getProvidedValue(this.key));
            }
        }
        ProvidedValue providedValue = configurationValue.getProvidedValue();
        Object originalValue = providedValue.getValue();
        try {
            Object finalValue = ConfigurationValueUtils.convertDataType(providedValue.getActualKey(), originalValue, this.valueConverter);
            if (originalValue != finalValue) {
                configurationValue.override(new ConvertedValueProvider<Object>(finalValue, providedValue).getProvidedValue(this.key));
            }
            return configurationValue;
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("An invalid " + providedValue.getSourceDescription().toLowerCase() + " value " + providedValue.getActualKey() + " detected: " + StringUtil.lowerCaseFirst(e.getMessage()), e);
        }
    }

    public String getKey() {
        return this.key;
    }

    public Set<String> getAliasKeys() {
        return this.aliasKeys;
    }

    public Class<DataType> getDataType() {
        return this.dataType;
    }

    public String getDescription() {
        return this.description;
    }

    public DataType getDefaultValue() {
        return this.defaultValue;
    }

    public String getDefaultValueDescription() {
        return this.defaultValueDescription;
    }

    public boolean getCommonlyUsed() {
        return this.commonlyUsed;
    }

    public boolean isInternal() {
        return this.internal;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    @Override
    public int compareTo(ConfigurationDefinition o) {
        return this.getKey().compareTo(o.getKey());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ConfigurationDefinition that = (ConfigurationDefinition)o;
        return Objects.equals(this.key, that.key);
    }

    public int hashCode() {
        return Objects.hash(this.key);
    }

    public boolean equalsKey(String key) {
        if (key == null) {
            return false;
        }
        if (this.getKey().equalsIgnoreCase(key)) {
            return true;
        }
        for (String alias : this.getAliasKeys()) {
            if (!alias.equalsIgnoreCase(key)) continue;
            return true;
        }
        return false;
    }

    static final class DefaultValueProvider
    extends AbstractConfigurationValueProvider {
        private final Object value;

        public DefaultValueProvider(Object value) {
            this.value = value;
        }

        @Override
        public int getPrecedence() {
            return -1;
        }

        @Override
        public ProvidedValue getProvidedValue(String ... keyAndAliases) {
            return new ProvidedValue(keyAndAliases[0], keyAndAliases[0], this.value, "Default value", this);
        }
    }

    private static final class ConvertedValueProvider<DataType>
    extends AbstractConfigurationValueProvider {
        private final DataType value;
        private final String originalSource;
        private final String actualKey;

        public ConvertedValueProvider(DataType value, ProvidedValue originalProvidedValue) {
            this.value = value;
            this.actualKey = originalProvidedValue.getActualKey();
            this.originalSource = originalProvidedValue.getSourceDescription();
        }

        @Override
        public int getPrecedence() {
            return -1;
        }

        @Override
        public ProvidedValue getProvidedValue(String ... keyAndAliases) {
            return new ProvidedValue(keyAndAliases[0], this.actualKey, this.value, this.originalSource, this);
        }
    }

    public static class Building<DataType> {
        private final ConfigurationDefinition<DataType> definition;
        private final String defaultKeyPrefix;

        private Building(ConfigurationDefinition<DataType> definition, String defaultKeyPrefix) {
            this.definition = definition;
            this.defaultKeyPrefix = defaultKeyPrefix;
        }

        public Building<DataType> addAliasKey(String alias) {
            if (!ALLOWED_KEY_PATTERN.matcher(alias).matches()) {
                throw new IllegalArgumentException("Invalid alias format: " + alias);
            }
            ((ConfigurationDefinition)this.definition).aliasKeys.add(alias);
            return this;
        }

        public Building<DataType> setDescription(String description) {
            ((ConfigurationDefinition)this.definition).description = description;
            return this;
        }

        public Building<DataType> setDefaultValue(DataType defaultValue, String defaultValueDescription) {
            ((ConfigurationDefinition)this.definition).defaultValue = defaultValue;
            ((ConfigurationDefinition)this.definition).defaultValueDescription = defaultValueDescription;
            if (defaultValue != null && defaultValueDescription == null) {
                ((ConfigurationDefinition)this.definition).defaultValueDescription = String.valueOf(defaultValue);
            }
            return this;
        }

        public Building<DataType> setDefaultValue(DataType defaultValue) {
            ((ConfigurationDefinition)this.definition).defaultValue = defaultValue;
            return this;
        }

        public Building<DataType> setValueHandler(ConfigurationValueConverter<DataType> handler) {
            ((ConfigurationDefinition)this.definition).valueConverter = handler;
            return this;
        }

        public Building<DataType> setValueObfuscator(ConfigurationValueObfuscator<DataType> handler) {
            ((ConfigurationDefinition)this.definition).valueObfuscator = handler;
            return this;
        }

        public Building<DataType> setCommonlyUsed(boolean commonlyUsed) {
            ((ConfigurationDefinition)this.definition).commonlyUsed = commonlyUsed;
            return this;
        }

        public Building<DataType> setInternal(boolean internal) {
            ((ConfigurationDefinition)this.definition).internal = internal;
            return this;
        }

        public Building<DataType> setHidden(boolean hidden) {
            ((ConfigurationDefinition)this.definition).hidden = hidden;
            return this;
        }

        public Building<DataType> addAliases(Collection<String> aliases) {
            for (String alias : aliases) {
                if (alias.contains(".")) continue;
                alias = this.defaultKeyPrefix + "." + alias;
                this.addAliasKey(alias);
            }
            return this;
        }

        public ConfigurationDefinition<DataType> build() {
            Scope.getCurrentScope().getSingleton(LiquibaseConfiguration.class).registerDefinition(this.definition);
            return this.definition;
        }

        public ConfigurationDefinition<DataType> buildTemporary() {
            return this.definition;
        }
    }

    public static class Builder {
        private final String defaultKeyPrefix;

        public Builder(String defaultKeyPrefix) {
            if (!ALLOWED_KEY_PATTERN.matcher(defaultKeyPrefix).matches()) {
                throw new IllegalArgumentException("Invalid prefix format: " + defaultKeyPrefix);
            }
            this.defaultKeyPrefix = defaultKeyPrefix;
        }

        public <T> Building<T> define(String key, Class<T> dataType) {
            ConfigurationDefinition definition = new ConfigurationDefinition(this.defaultKeyPrefix + "." + key, dataType);
            return new Building(definition, this.defaultKeyPrefix);
        }
    }
}

