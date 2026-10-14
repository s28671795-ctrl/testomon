/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.function.BiFunction;

@FunctionalInterface
public interface Function2<T1, T2, R>
extends BiFunction<T1, T2, R> {
    @Override
    public R apply(T1 var1, T2 var2);
}

