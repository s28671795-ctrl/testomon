/*
 * Decompiled with CFR 0.152.
 */
package liquibase.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import liquibase.Scope;
import liquibase.SingletonObject;
import liquibase.configuration.AutoloadedConfigurations;
import liquibase.configuration.ConfigurationContainer;
import liquibase.configuration.ConfigurationDefinition;
import liquibase.configuration.ConfigurationValueConverter;
import liquibase.configuration.ConfigurationValueObfuscator;
import liquibase.configuration.ConfigurationValueProvider;
import liquibase.configuration.ConfiguredValue;
import liquibase.configuration.ConfiguredValueModifierFactory;
import liquibase.configuration.ProvidedValue;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.servicelocator.ServiceLocator;
import liquibase.util.StringUtil;

public class LiquibaseConfiguration
implements SingletonObject {
    private final SortedSet<ConfigurationValueProvider> configurationValueProviders;
    private final SortedSet<ConfigurationDefinition<?>> definitions = new TreeSet();
    public static final String REGISTERED_VALUE_PROVIDERS_KEY = "REGISTERED_VALUE_PROVIDERS";
    private final Map<String, String> lastLoggedKeyValues = new HashMap<String, String>();

    protected LiquibaseConfiguration() {
        this.configurationValueProviders = new TreeSet<ConfigurationValueProvider>((o1, o2) -> {
            if (o1.getPrecedence() < o2.getPrecedence()) {
                return -1;
            }
            if (o1.getPrecedence() > o2.getPrecedence()) {
                return 1;
            }
            return o1.getClass().getName().compareTo(o2.getClass().getName());
        });
    }

    public static LiquibaseConfiguration getInstance() {
        return Scope.getCurrentScope().getSingleton(LiquibaseConfiguration.class);
    }

    public void init(Scope scope) {
        this.configurationValueProviders.clear();
        ServiceLocator serviceLocator = scope.getServiceLocator();
        List<AutoloadedConfigurations> containers = serviceLocator.findInstances(AutoloadedConfigurations.class);
        for (AutoloadedConfigurations container : containers) {
            Scope.getCurrentScope().getLog(this.getClass()).fine("Found ConfigurationDefinitions in " + container.getClass().getName());
        }
        this.configurationValueProviders.addAll(serviceLocator.findInstances(ConfigurationValueProvider.class));
    }

    public void registerProvider(ConfigurationValueProvider valueProvider) {
        this.configurationValueProviders.add(valueProvider);
    }

    public boolean unregisterProvider(ConfigurationValueProvider valueProvider) {
        return this.configurationValueProviders.remove(valueProvider);
    }

    public boolean removeProvider(ConfigurationValueProvider provider) {
        return this.configurationValueProviders.remove(provider);
    }

    public <T extends ConfigurationContainer> T getConfiguration(Class<T> type) {
        try {
            return (T)((ConfigurationContainer)type.newInstance());
        }
        catch (Throwable e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    public SortedSet<ConfigurationValueProvider> getProviders() {
        return Collections.unmodifiableSortedSet(this.configurationValueProviders);
    }

    public <DataType> ConfiguredValue<DataType> getCurrentConfiguredValue(ConfigurationValueConverter<DataType> converter, ConfigurationValueObfuscator<DataType> obfuscator, String ... keyAndAliases) {
        return this.getCurrentConfiguredValue(converter, obfuscator, (ConfigurationValueProvider[])null, keyAndAliases);
    }

    public <DataType> ConfiguredValue<DataType> getCurrentConfiguredValue(ConfigurationValueConverter<DataType> converter, ConfigurationValueObfuscator<DataType> obfuscator, ConfigurationValueProvider[] additionalValueProviders, String ... keyAndAliases) {
        if (keyAndAliases == null || keyAndAliases.length == 0) {
            throw new IllegalArgumentException("Must specify at least one key");
        }
        ConfiguredValue<DataType> details = new ConfiguredValue<DataType>(keyAndAliases[0], converter, obfuscator);
        ArrayList<ConfigurationValueProvider> finalValueProviders = new ArrayList<ConfigurationValueProvider>(this.configurationValueProviders);
        if (additionalValueProviders != null) {
            finalValueProviders.addAll(Arrays.asList(additionalValueProviders));
        }
        for (ConfigurationValueProvider provider : finalValueProviders) {
            ProvidedValue providerValue = provider.getProvidedValue(keyAndAliases);
            if (providerValue == null) continue;
            details.override(providerValue);
        }
        Scope.getCurrentScope().getSingleton(ConfiguredValueModifierFactory.class).override(details);
        String foundValue = String.valueOf(details.getValue());
        if (!foundValue.equals(this.lastLoggedKeyValues.get(keyAndAliases[0]))) {
            this.lastLoggedKeyValues.put(keyAndAliases[0], foundValue);
            if (details.found()) {
                StringBuilder logMessage = new StringBuilder("Found '" + keyAndAliases[0] + "' configuration of '" + details.getValueObfuscated() + "'");
                boolean foundFirstValue = false;
                for (ProvidedValue providedValue : details.getProvidedValues()) {
                    String describe;
                    char[] chars;
                    logMessage.append("\n    ");
                    if (foundFirstValue) {
                        logMessage.append("Overrides ");
                    }
                    if ((chars = (describe = providedValue.describe()).toCharArray()).length >= 2 && Character.isUpperCase(chars[0]) && Character.isUpperCase(chars[1])) {
                        logMessage.append(describe);
                    } else {
                        logMessage.append(StringUtil.lowerCaseFirst(describe));
                    }
                    Object value = providedValue.getValue();
                    if (value != null) {
                        String finalValue = String.valueOf(value);
                        if (obfuscator != null) {
                            finalValue = "*****";
                        }
                        logMessage.append(" of '").append(finalValue).append("'");
                    }
                    foundFirstValue = true;
                }
                Scope.getCurrentScope().getLog(this.getClass()).fine(logMessage.toString());
            } else {
                Scope.getCurrentScope().getLog(this.getClass()).fine("No configuration value for " + StringUtil.join(keyAndAliases, " aka ") + " found");
            }
        }
        return details;
    }

    public void registerDefinition(ConfigurationDefinition<?> definition) {
        this.definitions.add(definition);
    }

    public SortedSet<ConfigurationDefinition<?>> getRegisteredDefinitions(boolean includeInternal) {
        TreeSet<ConfigurationDefinition> returnSet = new TreeSet<ConfigurationDefinition>();
        for (ConfigurationDefinition configurationDefinition : this.definitions) {
            if (!includeInternal && configurationDefinition.isInternal()) continue;
            returnSet.add(configurationDefinition);
        }
        return Collections.unmodifiableSortedSet(returnSet);
    }

    public ConfigurationDefinition<?> getRegisteredDefinition(String key) {
        for (ConfigurationDefinition configurationDefinition : this.getRegisteredDefinitions(true)) {
            ArrayList<String> keys2 = new ArrayList<String>();
            keys2.add(configurationDefinition.getKey());
            keys2.addAll(configurationDefinition.getAliasKeys());
            for (String keyName : keys2) {
                if (keyName.equalsIgnoreCase(key)) {
                    return configurationDefinition;
                }
                if (!keyName.replace(".", "").equalsIgnoreCase(key)) continue;
                return configurationDefinition;
            }
        }
        return null;
    }
}

