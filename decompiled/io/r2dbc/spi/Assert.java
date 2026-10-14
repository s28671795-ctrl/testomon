/*
 * Decompiled with CFR 0.152.
 */
package io.r2dbc.spi;

import io.r2dbc.spi.Nullable;

abstract class Assert {
    private Assert() {
    }

    static String requireNonEmpty(String s2, String message) {
        if (s2.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return s2;
    }

    static <T> T requireNonNull(@Nullable T t2, String message) {
        if (t2 == null) {
            throw new IllegalArgumentException(message);
        }
        return t2;
    }
}

