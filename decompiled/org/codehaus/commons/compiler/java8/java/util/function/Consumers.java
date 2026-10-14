/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.java8.java.util.function;

import org.codehaus.commons.compiler.java8.java.util.function.Consumer;
import org.codehaus.commons.compiler.util.reflect.Classes;
import org.codehaus.commons.compiler.util.reflect.Methods;
import org.codehaus.commons.compiler.util.reflect.Proxies;

public final class Consumers {
    private Consumers() {
    }

    public static <T> Consumer<T> from(final Object delegate) {
        return new Consumer<T>(){

            @Override
            public void accept(T t2) {
                Methods.invoke(Consumer.METHOD_accept__T, delegate, t2);
            }
        };
    }

    public static <T> Object from(Consumer<T> delegate) {
        return Proxies.newInstance(delegate, Consumer.METHOD_accept__T, Classes.getDeclaredMethod(delegate.getClass(), "accept", Object.class));
    }
}

