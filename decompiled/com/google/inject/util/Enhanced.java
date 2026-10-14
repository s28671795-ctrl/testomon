/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.util;

import java.util.Optional;

public final class Enhanced {
    private Enhanced() {
    }

    public static boolean isEnhanced(Class<?> clazz) {
        return clazz.getSimpleName().contains("$$EnhancerByGuice$$");
    }

    public static <T> Optional<Class<? super T>> unenhancedClass(Class<T> clazz) {
        return Enhanced.isEnhanced(clazz) ? Optional.of(clazz.getSuperclass()) : Optional.empty();
    }
}

