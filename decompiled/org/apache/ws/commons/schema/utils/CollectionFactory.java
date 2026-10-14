/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema.utils;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class CollectionFactory {
    private static final String PROTECT_READ_ONLY_COLLECTIONS_PROP = "org.apache.ws.commons.schema.protectReadOnlyCollections";
    private static final boolean DEFAULT_PROTECT_READ_ONLY_COLLECTIONS;
    private static final ThreadLocal<Boolean> PROTECT_READ_ONLY_COLLECTIONS;

    private CollectionFactory() {
    }

    public static <T> List<T> getList(Class<T> type) {
        return Collections.synchronizedList(new ArrayList());
    }

    public static <T> Set<T> getSet(Class<T> type) {
        return Collections.synchronizedSet(new HashSet());
    }

    private static boolean isProtected() {
        Boolean b = PROTECT_READ_ONLY_COLLECTIONS.get();
        if (b == null) {
            return DEFAULT_PROTECT_READ_ONLY_COLLECTIONS;
        }
        return b;
    }

    public static void setProtected(boolean b) {
        PROTECT_READ_ONLY_COLLECTIONS.set(b);
    }

    public static void clearProtection() {
        PROTECT_READ_ONLY_COLLECTIONS.remove();
    }

    public static <T> List<T> getProtectedList(List<T> list) {
        if (CollectionFactory.isProtected()) {
            return Collections.unmodifiableList(list);
        }
        return list;
    }

    public static <K, V> Map<K, V> getProtectedMap(Map<K, V> map) {
        if (CollectionFactory.isProtected()) {
            return Collections.unmodifiableMap(map);
        }
        return map;
    }

    public static void withSchemaModifiable(Runnable action) {
        Boolean saved = PROTECT_READ_ONLY_COLLECTIONS.get();
        try {
            PROTECT_READ_ONLY_COLLECTIONS.set(Boolean.FALSE);
            action.run();
        }
        finally {
            if (saved == null) {
                PROTECT_READ_ONLY_COLLECTIONS.remove();
            } else {
                PROTECT_READ_ONLY_COLLECTIONS.set(saved);
            }
        }
    }

    static {
        PROTECT_READ_ONLY_COLLECTIONS = new ThreadLocal();
        DEFAULT_PROTECT_READ_ONLY_COLLECTIONS = AccessController.doPrivileged(new PrivilegedAction<Boolean>(){

            @Override
            public Boolean run() {
                return Boolean.parseBoolean(System.getProperty(CollectionFactory.PROTECT_READ_ONLY_COLLECTIONS_PROP));
            }
        });
    }
}

