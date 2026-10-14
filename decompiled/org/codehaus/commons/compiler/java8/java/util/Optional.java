/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.java8.java.util;

import java.lang.reflect.Method;
import org.codehaus.commons.compiler.util.reflect.Classes;
import org.codehaus.commons.compiler.util.reflect.Methods;

public class Optional<T> {
    private static final Class<?> CLASS = Classes.load("java.util.Optional");
    private static final Method METHOD_get = Classes.getDeclaredMethod(CLASS, "get", new Class[0]);
    private final Object delegate;

    public Optional(Object delegate) {
        this.delegate = delegate;
    }

    public T get() {
        return (T)Methods.invoke(METHOD_get, this.delegate, new Object[0]);
    }
}

