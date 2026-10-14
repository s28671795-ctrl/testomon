/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.java8.java.util.function;

import java.lang.reflect.Method;
import org.codehaus.commons.compiler.util.reflect.Classes;

public interface Consumer<T> {
    public static final Class<?> CLASS = Classes.load("java.util.function.Consumer");
    public static final Method METHOD_accept__T = Classes.getDeclaredMethod(CLASS, "accept", Object.class);

    public void accept(T var1);
}

