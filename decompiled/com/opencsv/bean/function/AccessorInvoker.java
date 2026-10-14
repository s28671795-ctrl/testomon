/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean.function;

import java.lang.reflect.InvocationTargetException;

@FunctionalInterface
public interface AccessorInvoker<T, R> {
    public R invoke(T var1) throws IllegalAccessException, InvocationTargetException;
}

