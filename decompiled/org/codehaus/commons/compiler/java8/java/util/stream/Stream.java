/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.java8.java.util.stream;

import java.lang.reflect.Method;
import org.codehaus.commons.compiler.java8.java.util.function.Consumer;
import org.codehaus.commons.compiler.java8.java.util.function.Consumers;
import org.codehaus.commons.compiler.util.reflect.Classes;
import org.codehaus.commons.compiler.util.reflect.Methods;

public class Stream<T> {
    private static final Class<?> CLASS = Classes.load("java.util.stream.Stream");
    private static final Method METHOD_forEach = Classes.getDeclaredMethod(CLASS, "forEach", Consumer.CLASS);
    private final Object delegate;

    public Stream(Object delegate) {
        this.delegate = delegate;
    }

    public void forEach(Consumer<? super T> action) {
        Methods.invoke(METHOD_forEach, this.delegate, Consumers.from(action));
    }
}

