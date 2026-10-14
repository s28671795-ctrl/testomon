/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.Utils;

import java.util.Collection;

public final class CollectionUtils {
    public static boolean isEmpty(Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> coll) {
        return !CollectionUtils.isEmpty(coll);
    }
}

