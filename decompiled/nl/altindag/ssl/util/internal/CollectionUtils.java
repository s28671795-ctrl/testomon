/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.util.internal;

import java.util.List;

public final class CollectionUtils {
    private CollectionUtils() {
    }

    public static boolean isEmpty(List<?> collection) {
        return collection == null || collection.isEmpty();
    }
}

