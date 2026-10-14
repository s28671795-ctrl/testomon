/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.extension;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.cxf.extension.Registry;

public class RegistryImpl<K, T>
implements Registry<K, T> {
    protected final Map<K, T> entries;

    protected RegistryImpl() {
        this(null);
    }

    protected RegistryImpl(Map<K, T> e) {
        if (null == e) {
            e = new ConcurrentHashMap<K, T>(16, 0.75f, 4);
        } else if (!(e instanceof ConcurrentHashMap)) {
            e = new ConcurrentHashMap<K, T>(e);
        }
        this.entries = e;
    }

    protected void loadDynamic() {
    }

    @Override
    public void register(K k, T t2) {
        this.entries.put(k, t2);
    }

    @Override
    public void unregister(K k) {
        this.entries.remove(k);
    }

    @Override
    public T get(K k) {
        this.loadDynamic();
        return this.entries.get(k);
    }
}

