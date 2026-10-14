/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.Utils.ExpiringMap;

import java.util.Map;

public interface ExpiringMap<K, V>
extends Map<K, V> {
    public boolean renewKey(K var1);

    public V put(K var1, V var2, long var3);
}

