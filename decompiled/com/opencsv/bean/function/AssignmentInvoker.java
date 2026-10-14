/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean.function;

import java.lang.reflect.InvocationTargetException;

@FunctionalInterface
public interface AssignmentInvoker<T, U> {
    public void invoke(T var1, U var2) throws IllegalAccessException, InvocationTargetException;
}

