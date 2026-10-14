/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.collect.Ordering;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;

public final class DeclaredMembers {
    private DeclaredMembers() {
    }

    public static Field[] getDeclaredFields(Class<?> type) {
        return (Field[])Arrays.stream(type.getDeclaredFields()).sorted(Comparator.comparing(Field::getName).thenComparing(Field::getType, Comparator.comparing(Class::getName))).toArray(Field[]::new);
    }

    public static Method[] getDeclaredMethods(Class<?> type) {
        return (Method[])Arrays.stream(type.getDeclaredMethods()).sorted(Comparator.comparing(Method::getName).thenComparing(Method::getReturnType, Comparator.comparing(Class::getName)).thenComparing(method2 -> Arrays.asList(method2.getParameterTypes()), Ordering.from(Comparator.comparing(Class::getName)).lexicographical())).toArray(Method[]::new);
    }
}

