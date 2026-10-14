/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.jooq.impl.Tools;

final class DataMap
extends AbstractMap<Object, Object> {
    final EnumSet<Tools.BooleanDataKey> internalSet = EnumSet.noneOf(Tools.BooleanDataKey.class);
    final EnumMap<Tools.SimpleDataKey, Object> internalMap = new EnumMap(Tools.SimpleDataKey.class);
    Map<Object, Object> externalMap;
    final Set<Map.Entry<Object, Object>> entrySet = new EntrySet();

    DataMap() {
    }

    @Override
    public final int size() {
        return this.internalSet.size() + this.internalMap().size() + this.external(false).size();
    }

    @Override
    public final boolean isEmpty() {
        return this.internalSet.isEmpty() && this.internalMap().isEmpty() && this.external(false).isEmpty();
    }

    @Override
    public final boolean containsKey(Object key) {
        return key instanceof Tools.BooleanDataKey ? this.internalSet.contains(key) : this.delegate(key, false).containsKey(key);
    }

    @Override
    public final boolean containsValue(Object value) {
        if (value instanceof Boolean) {
            if (((Boolean)value).booleanValue() && this.internalSet.size() > 0) {
                return true;
            }
            if (!((Boolean)value).booleanValue() && this.internalSet.size() < Tools.BooleanDataKey.values().length) {
                return true;
            }
        }
        return this.internalMap().containsValue(value) || this.external(false).containsValue(value);
    }

    @Override
    public final Object get(Object key) {
        return key instanceof Tools.BooleanDataKey ? (this.internalSet.contains(key) ? Boolean.valueOf(true) : (Boolean)null) : this.delegate(key, false).get(key);
    }

    @Override
    public final Object put(Object key, Object value) {
        return key instanceof Tools.BooleanDataKey ? (Boolean.TRUE.equals(value) ? (this.internalSet.add((Tools.BooleanDataKey)key) ? (Boolean)null : Boolean.TRUE) : (this.internalSet.remove(key) ? Boolean.TRUE : (Boolean)null)) : this.delegate(key, true).put(key, value);
    }

    @Override
    public final Object remove(Object key) {
        return key instanceof Tools.BooleanDataKey ? (this.internalSet.remove(key) ? Boolean.TRUE : (Boolean)null) : this.delegate(key, true).remove(key);
    }

    @Override
    public final void clear() {
        this.internalSet.clear();
        this.internalMap().clear();
        this.external(true).clear();
    }

    @Override
    public final Set<Map.Entry<Object, Object>> entrySet() {
        return this.entrySet;
    }

    private final Map<Object, Object> internalMap() {
        return this.internalMap;
    }

    private final Map<Object, Object> external(boolean initialise) {
        if (this.externalMap == null) {
            if (initialise) {
                this.externalMap = new HashMap<Object, Object>();
            } else {
                return Collections.emptyMap();
            }
        }
        return this.externalMap;
    }

    private final Map<Object, Object> delegate(Object key, boolean initialise) {
        return key instanceof Tools.SimpleDataKey ? this.internalMap() : this.external(initialise);
    }

    private class EntrySet
    extends AbstractSet<Map.Entry<Object, Object>> {
        private EntrySet() {
        }

        @Override
        public final Iterator<Map.Entry<Object, Object>> iterator() {
            return new Iterator<Map.Entry<Object, Object>>(){
                final Iterator<Tools.BooleanDataKey> internalSetIterator;
                final Iterator<Map.Entry<Object, Object>> internalMapIterator;
                final Iterator<Map.Entry<Object, Object>> externalMapIterator;
                {
                    this.internalSetIterator = DataMap.this.internalSet.iterator();
                    this.internalMapIterator = DataMap.this.internalMap().entrySet().iterator();
                    this.externalMapIterator = DataMap.this.external(false).entrySet().iterator();
                }

                @Override
                public final boolean hasNext() {
                    return this.internalSetIterator.hasNext() || this.internalMapIterator.hasNext() || this.externalMapIterator.hasNext();
                }

                @Override
                public final Map.Entry<Object, Object> next() {
                    return this.internalSetIterator.hasNext() ? new AbstractMap.SimpleImmutableEntry<Tools.BooleanDataKey, Boolean>(this.internalSetIterator.next(), true) : (this.internalMapIterator.hasNext() ? this.internalMapIterator.next() : this.externalMapIterator.next());
                }

                @Override
                public final void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }

        @Override
        public final int size() {
            return DataMap.this.size();
        }
    }
}

