/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.primitive;

import javax.annotation.Nullable;

public final class ObjectSupport {
    private ObjectSupport() {
    }

    public static int hashCode(@Nullable Object o) {
        if (o == null) {
            return 0;
        }
        return o.hashCode();
    }

    @Nullable
    public static <T> T firstNonNull(T ... objects) {
        if (objects == null) {
            return null;
        }
        for (T obj : objects) {
            if (obj == null) continue;
            return obj;
        }
        return null;
    }
}

