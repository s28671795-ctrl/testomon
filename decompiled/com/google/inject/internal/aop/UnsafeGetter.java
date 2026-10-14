/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal.aop;

import java.lang.reflect.Field;
import sun.misc.Unsafe;

final class UnsafeGetter {
    private UnsafeGetter() {
    }

    static Unsafe getUnsafe() throws ReflectiveOperationException {
        try {
            return Unsafe.getUnsafe();
        }
        catch (SecurityException securityException) {
            Class<Unsafe> k = Unsafe.class;
            for (Field f : k.getDeclaredFields()) {
                f.setAccessible(true);
                Object x = f.get(null);
                if (!k.isInstance(x)) continue;
                return (Unsafe)k.cast(x);
            }
            throw new NoSuchFieldError("the Unsafe");
        }
    }
}

