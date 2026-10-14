/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.component;

import java.io.Serializable;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MultiMap<K, V>
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 893592003056170756L;
    private final Map<K, List<V>> store = new LinkedHashMap<K, List<V>>();
    private static final String NEWLINE = AccessController.doPrivileged(new PrivilegedAction<String>(){

        @Override
        public String run() {
            return System.getProperty("line.separator");
        }
    });

    public MultiMap() {
    }

    public MultiMap(MultiMap<K, V> base) {
        this();
        for (Map.Entry<K, List<V>> e : base.entrySet()) {
            List<V> value = super.newList((Collection)e.getValue());
            if (value.isEmpty()) continue;
            this.store.put(e.getKey(), super.newList((Collection)e.getValue()));
        }
    }

    private List<V> newList(Collection<? extends V> initialVals) {
        if (null == initialVals) {
            return new LinkedList();
        }
        return new LinkedList<V>(initialVals);
    }

    public Set<K> keySet() {
        return this.store.keySet();
    }

    public final void add(K k, V v) {
        List<V> l = this.store.get(k);
        if (l == null) {
            l = this.newList(null);
            this.store.put(k, l);
        }
        l.add(v);
    }

    public void set(K k, Collection<? extends V> v) {
        List<V> addMe = this.newList(v);
        if (addMe.isEmpty()) {
            this.store.remove(k);
        } else {
            this.store.put(k, this.newList(v));
        }
    }

    public void set(K k, V v) {
        List<V> vlist = this.newList(null);
        vlist.add(v);
        this.store.put(k, vlist);
    }

    public final List<V> get(K k) {
        List<V> l = this.store.get(k);
        if (l == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(l);
    }

    public void mergeAll(MultiMap<K, V> another) {
        if (another == null) {
            return;
        }
        for (Map.Entry<K, List<V>> entry : another.entrySet()) {
            List<V> ourList = this.store.get(entry.getKey());
            if (null == ourList) {
                ourList = this.newList((Collection)entry.getValue());
                if (ourList.isEmpty()) continue;
                this.store.put(entry.getKey(), ourList);
                continue;
            }
            for (V v : entry.getValue()) {
                if (ourList.contains(v)) continue;
                ourList.add(v);
            }
        }
    }

    private final List<V> _get(K k) {
        List<V> l = this.store.get(k);
        if (l == null) {
            return Collections.emptyList();
        }
        return l;
    }

    public boolean containsKey(K k) {
        return !this.get(k).isEmpty();
    }

    public boolean contains(K k1, V k2) {
        List<V> list = this._get(k1);
        return list.contains(k2);
    }

    public List<V> remove(K key) {
        return this.store.remove(key);
    }

    public boolean remove(K key, V entry) {
        List<V> list = this.store.get(key);
        if (list == null) {
            return false;
        }
        boolean retVal = list.remove(entry);
        if (list.isEmpty()) {
            this.store.remove(key);
        }
        return retVal;
    }

    public V getOne(K k) {
        return this.getFirst(k);
    }

    private V getFirst(K k) {
        List<V> lst = this.store.get(k);
        if (null == lst) {
            return null;
        }
        if (lst.isEmpty()) {
            return null;
        }
        return lst.get(0);
    }

    public Set<Map.Entry<K, List<V>>> entrySet() {
        return this.store.entrySet();
    }

    public String toCommaSeparatedString() {
        StringBuilder buf = new StringBuilder();
        for (Map.Entry<K, List<V>> e : this.entrySet()) {
            for (V v : e.getValue()) {
                if (buf.length() > 0) {
                    buf.append(',');
                }
                buf.append(e.getKey()).append('=').append(v);
            }
        }
        return buf.toString();
    }

    public MultiMap<K, V> clone() throws CloneNotSupportedException {
        super.clone();
        return new MultiMap<K, V>(this);
    }

    public int size() {
        return this.store.size();
    }

    public int hashCode() {
        return this.store.hashCode();
    }

    public boolean equals(Object another) {
        if (another == null || !(another instanceof MultiMap)) {
            return false;
        }
        MultiMap other = (MultiMap)another;
        return this.store.equals(other.store);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (K key : this.store.keySet()) {
            builder.append(key).append(": ");
            builder.append(this.store.get(key));
            builder.append(NEWLINE);
        }
        builder.append("}");
        return builder.toString();
    }
}

