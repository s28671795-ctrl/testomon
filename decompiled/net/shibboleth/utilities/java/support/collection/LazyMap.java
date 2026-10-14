/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.collection;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class LazyMap<KeyType, ValueType>
implements Map<KeyType, ValueType>,
Serializable {
    private static final long serialVersionUID = 121425595164176639L;
    private Map<KeyType, ValueType> delegate = Collections.emptyMap();

    @Override
    public void clear() {
        this.delegate = Collections.emptyMap();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.delegate.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.delegate.containsValue(value);
    }

    @Override
    public Set<Map.Entry<KeyType, ValueType>> entrySet() {
        this.delegate = this.buildMap();
        return this.delegate.entrySet();
    }

    @Override
    public ValueType get(Object key) {
        return this.delegate.get(key);
    }

    @Override
    public boolean isEmpty() {
        return this.delegate.isEmpty();
    }

    @Override
    public Set<KeyType> keySet() {
        this.delegate = this.buildMap();
        return this.delegate.keySet();
    }

    @Override
    public ValueType put(KeyType key, ValueType value) {
        if (this.delegate.isEmpty()) {
            this.delegate = Collections.singletonMap(key, value);
            return null;
        }
        this.delegate = this.buildMap();
        return this.delegate.put(key, value);
    }

    @Override
    public void putAll(Map<? extends KeyType, ? extends ValueType> t2) {
        this.delegate = this.buildMap();
        this.delegate.putAll(t2);
    }

    @Override
    public ValueType remove(Object key) {
        this.delegate = this.buildMap();
        return this.delegate.remove(key);
    }

    @Override
    public int size() {
        return this.delegate.size();
    }

    @Override
    public Collection<ValueType> values() {
        this.delegate = this.buildMap();
        return this.delegate.values();
    }

    protected Map<KeyType, ValueType> buildMap() {
        if (this.delegate instanceof HashMap) {
            return this.delegate;
        }
        return new HashMap<KeyType, ValueType>(this.delegate);
    }

    public String toString() {
        return this.delegate.toString();
    }

    @Override
    public int hashCode() {
        return this.delegate.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        return this.delegate.equals(((LazyMap)obj).delegate);
    }
}

