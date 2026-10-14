/*
 * Decompiled with CFR 0.152.
 */
package liquibase.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import liquibase.configuration.AbstractConfigurationValueProvider;
import liquibase.configuration.ProvidedValue;
import liquibase.util.StringUtil;

public abstract class AbstractMapConfigurationValueProvider
extends AbstractConfigurationValueProvider {
    private final Map<String, ProvidedValue> knownValues = new HashMap<String, ProvidedValue>();
    private int knownValuesForHash = 0;
    private final Lock knownValuesLock = new ReentrantLock();

    protected abstract Map<?, ?> getMap();

    protected abstract String getSourceDescription();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ProvidedValue getProvidedValue(String ... keyAndAliases) {
        Lock lock = this.knownValuesLock;
        lock.lock();
        try {
            int currentValuesHash = this.getMapHash();
            if (currentValuesHash == this.knownValuesForHash) {
                for (String key : keyAndAliases) {
                    if (!this.knownValues.containsKey(key)) continue;
                    ProvidedValue providedValue = this.knownValues.get(key);
                    return providedValue;
                }
            } else {
                this.knownValues.clear();
                this.knownValuesForHash = currentValuesHash;
            }
        }
        finally {
            lock.unlock();
        }
        ProvidedValue providedValue = this.lookupProvidedValue(keyAndAliases);
        if (providedValue == null) {
            for (String key : keyAndAliases) {
                this.knownValues.put(key, null);
            }
        } else {
            this.knownValues.put(providedValue.getRequestedKey(), providedValue);
            this.knownValues.put(providedValue.getActualKey(), providedValue);
        }
        return providedValue;
    }

    protected int getMapHash() {
        Map<?, ?> map = this.getMap();
        if (map == null) {
            return -1;
        }
        return map.hashCode();
    }

    protected ProvidedValue lookupProvidedValue(String ... keyAndAliases) {
        if (keyAndAliases == null || keyAndAliases.length == 0) {
            return null;
        }
        Map<?, ?> sourceData = this.getMap();
        if (sourceData == null) {
            return null;
        }
        for (String key : keyAndAliases) {
            Object foundValue;
            if (sourceData.containsKey(key) && this.isValueSet(foundValue = sourceData.get(key))) {
                return new ProvidedValue(keyAndAliases[0], key, foundValue, this.getSourceDescription(), this);
            }
            for (Map.Entry<?, ?> entry : sourceData.entrySet()) {
                Object value;
                String actualKey = String.valueOf(entry.getKey());
                if (!this.keyMatches(key, actualKey) || !this.isValueSet(value = entry.getValue())) continue;
                return new ProvidedValue(keyAndAliases[0], actualKey, value, this.getSourceDescription(), this);
            }
        }
        return null;
    }

    protected boolean isValueSet(Object value) {
        if (value == null) {
            return false;
        }
        if (value instanceof String) {
            return StringUtil.isNotEmpty((String)value);
        }
        return true;
    }

    protected boolean keyMatches(String wantedKey, String storedKey) {
        if (wantedKey.equalsIgnoreCase(storedKey)) {
            return true;
        }
        if ((wantedKey = StringUtil.toKabobCase(wantedKey)).equalsIgnoreCase(storedKey)) {
            return true;
        }
        if ((wantedKey = wantedKey.replace(".", "-")).equalsIgnoreCase(storedKey)) {
            return true;
        }
        wantedKey = wantedKey.replace("-", "");
        return wantedKey.equalsIgnoreCase(storedKey);
    }
}

