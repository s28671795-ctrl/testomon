/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.inject.internal.Errors;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Predicate;

public interface KotlinSupportInterface {
    public Annotation[] getAnnotations(Field var1);

    public boolean isNullable(Field var1);

    public Predicate<Integer> getIsParameterKotlinNullablePredicate(Constructor<?> var1);

    public Predicate<Integer> getIsParameterKotlinNullablePredicate(Method var1);

    public void checkConstructorParameterAnnotations(Constructor<?> var1, Errors var2);

    public boolean isLocalClass(Class<?> var1);
}

