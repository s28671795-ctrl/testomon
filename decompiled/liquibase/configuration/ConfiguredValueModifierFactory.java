/*
 * Decompiled with CFR 0.152.
 */
package liquibase.configuration;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;
import liquibase.Scope;
import liquibase.SingletonObject;
import liquibase.configuration.ConfiguredValue;
import liquibase.configuration.ConfiguredValueModifier;
import liquibase.servicelocator.ServiceLocator;

public class ConfiguredValueModifierFactory
implements SingletonObject {
    private final TreeSet<ConfiguredValueModifier> allInstances = new TreeSet<ConfiguredValueModifier>(Comparator.comparingInt(ConfiguredValueModifier::getOrder));

    private ConfiguredValueModifierFactory() {
        ServiceLocator serviceLocator = Scope.getCurrentScope().getServiceLocator();
        this.allInstances.addAll(serviceLocator.findInstances(ConfiguredValueModifier.class));
    }

    public void register(ConfiguredValueModifier modifier) {
        this.allInstances.add(modifier);
    }

    public void unregister(ConfiguredValueModifier modifier) {
        this.allInstances.remove(modifier);
    }

    public void override(ConfiguredValue configuredValue) {
        for (ConfiguredValueModifier modifier : this.allInstances) {
            modifier.override(configuredValue);
        }
    }

    public String override(String configuredValue) {
        Iterator<ConfiguredValueModifier> iterator2 = this.allInstances.descendingIterator();
        while (iterator2.hasNext()) {
            ConfiguredValueModifier allInstance = iterator2.next();
            String overriddenValue = allInstance.override(configuredValue);
            if (configuredValue.equals(overriddenValue)) continue;
            return overriddenValue;
        }
        return configuredValue;
    }
}

