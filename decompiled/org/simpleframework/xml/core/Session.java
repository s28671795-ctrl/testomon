/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

final class Session
implements Map {
    private final Map map = new HashMap();
    private final boolean strict;

    public Session() {
        this(true);
    }

    public Session(boolean strict) {
        this.strict = strict;
    }

    public boolean isStrict() {
        return this.strict;
    }

    public Map getMap() {
        return this.map;
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public boolean containsKey(Object name) {
        return this.map.containsKey(name);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.map.containsValue(value);
    }

    public Object get(Object name) {
        return this.map.get(name);
    }

    public Object put(Object name, Object value) {
        return this.map.put(name, value);
    }

    public Object remove(Object name) {
        return this.map.remove(name);
    }

    public void putAll(Map data) {
        this.map.putAll(data);
    }

    public Set keySet() {
        return this.map.keySet();
    }

    public Collection values() {
        return this.map.values();
    }

    public Set entrySet() {
        return this.map.entrySet();
    }

    @Override
    public void clear() {
        this.map.clear();
    }
}

