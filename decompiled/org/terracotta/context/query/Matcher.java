/*
 * Decompiled with CFR 0.152.
 */
package org.terracotta.context.query;

import java.lang.reflect.Method;

public abstract class Matcher<T> {
    private Class<? extends T> boundType = Matcher.getSafeType(this.getClass());

    private static <T extends Matcher<?>> Class<?> getSafeType(Class<T> fromClass) {
        for (Class<T> c = fromClass; c != Object.class; c = c.getSuperclass()) {
            for (Method method2 : c.getDeclaredMethods()) {
                if (!method2.getName().equals("matchesSafely") || method2.getParameterTypes().length != 1 || method2.isSynthetic()) continue;
                return method2.getParameterTypes()[0];
            }
        }
        throw new AssertionError();
    }

    public final boolean matches(Object object) {
        if (this.boundType.isAssignableFrom(object.getClass())) {
            return this.matchesSafely(this.boundType.cast(object));
        }
        return false;
    }

    protected abstract boolean matchesSafely(T var1);
}

