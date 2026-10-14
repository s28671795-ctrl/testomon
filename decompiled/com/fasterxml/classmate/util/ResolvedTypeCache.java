/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.classmate.util;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.types.TypePlaceHolder;
import com.fasterxml.classmate.util.ConcurrentTypeCache;
import com.fasterxml.classmate.util.LRUTypeCache;
import com.fasterxml.classmate.util.ResolvedTypeKey;
import java.io.Serializable;
import java.util.List;

public abstract class ResolvedTypeCache
implements Serializable {
    public static ResolvedTypeCache lruCache(int maxEntries) {
        return new LRUTypeCache(maxEntries);
    }

    public static ResolvedTypeCache concurrentCache(int maxEntries) {
        return new ConcurrentTypeCache(maxEntries);
    }

    public ResolvedTypeKey key(Class<?> simpleType) {
        return new ResolvedTypeKey(simpleType);
    }

    public ResolvedTypeKey key(Class<?> simpleType, ResolvedType[] tp) {
        int len;
        int n = len = tp == null ? 0 : tp.length;
        if (len == 0) {
            return new ResolvedTypeKey(simpleType);
        }
        for (int i = 0; i < len; ++i) {
            if (!(tp[i] instanceof TypePlaceHolder)) continue;
            return null;
        }
        return new ResolvedTypeKey(simpleType, tp);
    }

    public abstract ResolvedType find(ResolvedTypeKey var1);

    public abstract int size();

    public abstract void put(ResolvedTypeKey var1, ResolvedType var2);

    protected void _addForTest(ResolvedType type) {
        List<ResolvedType> tp = type.getTypeParameters();
        ResolvedType[] tpa = tp.toArray(new ResolvedType[0]);
        this.put(this.key(type.getErasedType(), tpa), type);
    }
}

