/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util.reflect;

import java.lang.reflect.Method;

public final class Classes {
    private Classes() {
    }

    public static Class<?> load(String className) {
        return Classes.load(ClassLoader.getSystemClassLoader(), className);
    }

    public static Class<?> load(ClassLoader classLoader, String className) {
        try {
            return classLoader.loadClass(className);
        }
        catch (Exception e) {
            throw new AssertionError((Object)e);
        }
    }

    public static Method getDeclaredMethod(Class<?> declaringClass, String methodName, Class<?> ... parameterTypes) {
        try {
            return declaringClass.getDeclaredMethod(methodName, parameterTypes);
        }
        catch (Exception e) {
            throw new AssertionError((Object)e);
        }
    }
}

