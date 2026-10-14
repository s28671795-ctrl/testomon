/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import liquibase.util.ObjectUtil;

public class SmartMap
implements Map<String, Object> {
    private final SortedMap<String, Object> values = new TreeMap<String, Object>();

    @Override
    public int size() {
        return this.values.size();
    }

    @Override
    public boolean isEmpty() {
        return this.values.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.values.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.values.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return this.values.get(key);
    }

    public <T> T get(String key, Class<T> type) {
        return ObjectUtil.convert(this.values.get(key), type);
    }

    public <T> T get(String key, T defaultValue) {
        Class type = defaultValue == null ? Object.class : defaultValue.getClass();
        Class<Object> value = this.get(key, (T)type);
        if (value == null) {
            return defaultValue;
        }
        return (T)value;
    }

    @Override
    public Object put(String key, Object value) {
        if (value == null) {
            return this.values.remove(key);
        }
        return this.values.put(key, value);
    }

    public SmartMap set(String key, Object value) {
        this.put(key, value);
        return this;
    }

    @Override
    public Object remove(Object key) {
        return this.values.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ?> m4) {
        this.values.putAll(m4);
    }

    @Override
    public void clear() {
        this.values.clear();
    }

    @Override
    public boolean equals(Object o) {
        return this.values.equals(o);
    }

    @Override
    public int hashCode() {
        return this.values.hashCode();
    }

    @Override
    public Set<String> keySet() {
        return this.values.keySet();
    }

    @Override
    public Collection<Object> values() {
        return this.values.values();
    }

    @Override
    public Set<Map.Entry<String, Object>> entrySet() {
        return this.values.entrySet();
    }
}

