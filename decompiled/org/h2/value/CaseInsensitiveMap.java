/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import java.util.HashMap;
import org.h2.util.StringUtils;

public class CaseInsensitiveMap<V>
extends HashMap<String, V> {
    private static final long serialVersionUID = 1L;

    public CaseInsensitiveMap() {
    }

    public CaseInsensitiveMap(int n) {
        super(n);
    }

    @Override
    public V get(Object object) {
        return super.get(StringUtils.toUpperEnglish((String)object));
    }

    @Override
    public V put(String string, V v) {
        return super.put(StringUtils.toUpperEnglish(string), v);
    }

    @Override
    public V putIfAbsent(String string, V v) {
        return super.putIfAbsent(StringUtils.toUpperEnglish(string), v);
    }

    @Override
    public boolean containsKey(Object object) {
        return super.containsKey(StringUtils.toUpperEnglish((String)object));
    }

    @Override
    public V remove(Object object) {
        return super.remove(StringUtils.toUpperEnglish((String)object));
    }
}

