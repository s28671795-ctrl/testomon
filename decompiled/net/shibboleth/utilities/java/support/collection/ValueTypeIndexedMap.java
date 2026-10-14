/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.collection;

import com.google.common.annotations.Beta;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Beta
public class ValueTypeIndexedMap<KeyType, ValueType>
implements Map<KeyType, ValueType> {
    private Map<Class<?>, Map<KeyType, ValueType>> index;
    private Map<KeyType, ValueType> map;
    private Set<Class<?>> types;

    public ValueTypeIndexedMap() {
        this(new HashSet());
    }

    public ValueTypeIndexedMap(Map<KeyType, ValueType> newMap, Collection<Class<?>> newTypes) {
        this.map = newMap;
        this.types = new HashSet(newTypes);
        this.index = new HashMap();
        this.rebuildIndex();
    }

    public ValueTypeIndexedMap(Collection<Class<?>> newTypes) {
        this(new HashMap(), newTypes);
    }

    @Override
    public void clear() {
        this.map.clear();
        this.rebuildIndex();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.map.containsValue(value);
    }

    @Override
    public Set<Map.Entry<KeyType, ValueType>> entrySet() {
        return this.map.entrySet();
    }

    @Override
    public ValueType get(Object key) {
        return this.map.get(key);
    }

    public Set<Class<?>> getTypes() {
        return this.types;
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public Set<KeyType> keySet() {
        return this.map.keySet();
    }

    private Boolean matchType(Class<?> type, Object object) {
        return type.isInstance(object) || type == NullValue.class && object == null;
    }

    @Override
    public ValueType put(KeyType key, ValueType value) {
        ValueType oldValue = this.map.put(key, value);
        for (Class<?> clazz : this.index.keySet()) {
            Class type = clazz == null ? NullValue.class : clazz;
            if (this.matchType(type, value).booleanValue()) {
                this.index.get(type).put(key, value);
                continue;
            }
            if (!this.matchType(type, oldValue).booleanValue()) continue;
            this.index.get(type).remove(key);
        }
        return oldValue;
    }

    @Override
    public void putAll(Map<? extends KeyType, ? extends ValueType> t2) {
        for (KeyType key : t2.keySet()) {
            this.put(key, t2.get(key));
        }
    }

    public void rebuildIndex() {
        this.index.clear();
        for (Class<?> clazz : this.types) {
            Class type = clazz == null ? NullValue.class : clazz;
            this.index.put(type, new HashMap());
            for (KeyType key : this.map.keySet()) {
                ValueType value = this.map.get(key);
                if (!this.matchType(type, value).booleanValue()) continue;
                this.index.get(type).put(key, value);
            }
        }
    }

    @Override
    public ValueType remove(Object key) {
        ValueType value = this.map.remove(key);
        for (Class<?> type : this.index.keySet()) {
            if (!type.isInstance(value)) continue;
            this.index.get(type).remove(key);
        }
        return value;
    }

    public void setTypes(Collection<Class<?>> newTypes) {
        this.types = new HashSet(newTypes);
    }

    @Override
    public int size() {
        return this.map.size();
    }

    public <SubType extends ValueType> Map<KeyType, SubType> subMap(Class<SubType> type) {
        Class<Object> key = type;
        if (key == null) {
            key = NullValue.class;
        }
        if (this.index.containsKey(key)) {
            return Collections.unmodifiableMap(this.index.get(key));
        }
        return Collections.emptyMap();
    }

    @Override
    public int hashCode() {
        return this.map.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        return this.map.equals(((ValueTypeIndexedMap)obj).map);
    }

    public String toString() {
        return this.map.toString();
    }

    @Override
    public Collection<ValueType> values() {
        return this.map.values();
    }

    private static class NullValue {
        private NullValue() {
        }
    }
}

