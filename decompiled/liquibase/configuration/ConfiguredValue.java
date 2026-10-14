/*
 * Decompiled with CFR 0.152.
 */
package liquibase.configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import liquibase.configuration.AbstractConfigurationValueProvider;
import liquibase.configuration.ConfigurationDefinition;
import liquibase.configuration.ConfigurationValueConverter;
import liquibase.configuration.ConfigurationValueObfuscator;
import liquibase.configuration.ConfigurationValueUtils;
import liquibase.configuration.ProvidedValue;

public class ConfiguredValue<DataType> {
    private static final NoValueProvider NO_VALUE_PROVIDER = new NoValueProvider();
    private final List<ProvidedValue> providedValues = new ArrayList<ProvidedValue>();
    private final String key;
    private final ConfigurationValueObfuscator<DataType> valueObfuscator;
    private final ConfigurationValueConverter<DataType> valueConverter;

    protected ConfiguredValue(String key, ConfigurationValueConverter<DataType> converter, ConfigurationValueObfuscator<DataType> obfuscator) {
        this.key = key;
        this.valueObfuscator = obfuscator;
        this.valueConverter = converter == null ? value -> value : converter;
    }

    public DataType getValue() {
        ProvidedValue providedValue = this.getProvidedValue();
        if (providedValue == null) {
            return null;
        }
        return (DataType)ConfigurationValueUtils.convertDataType(providedValue.getActualKey(), providedValue.getValue(), this.valueConverter);
    }

    public DataType getValueObfuscated() {
        DataType rawValue = this.getValue();
        if (this.valueObfuscator != null) {
            return this.valueObfuscator.obfuscate(rawValue);
        }
        return rawValue;
    }

    public ProvidedValue getProvidedValue() {
        return this.getProvidedValues().get(0);
    }

    public boolean wasDefaultValueUsed() {
        ProvidedValue winningProvidedValue = this.getProvidedValue();
        return winningProvidedValue != null && winningProvidedValue.getProvider() instanceof ConfigurationDefinition.DefaultValueProvider;
    }

    public void override(ProvidedValue details) {
        if (details == null) {
            return;
        }
        this.providedValues.add(0, details);
    }

    public void override(Object newValue, String sourceDescription) {
        ProvidedValue thisValue = this.getProvidedValue();
        this.providedValues.add(0, new ProvidedValue(thisValue.getRequestedKey(), thisValue.getActualKey(), newValue, sourceDescription, thisValue.getProvider()));
    }

    public List<ProvidedValue> getProvidedValues() {
        if (this.providedValues.size() == 0) {
            return Collections.singletonList(NO_VALUE_PROVIDER.getProvidedValue(this.key));
        }
        return Collections.unmodifiableList(this.providedValues);
    }

    public boolean found() {
        return this.providedValues.size() > 0;
    }

    private static final class NoValueProvider
    extends AbstractConfigurationValueProvider {
        private NoValueProvider() {
        }

        @Override
        public int getPrecedence() {
            return -1;
        }

        @Override
        public ProvidedValue getProvidedValue(String ... keyAndAliases) {
            return new ProvidedValue(keyAndAliases[0], keyAndAliases[0], null, "No configured value found", this);
        }
    }
}

