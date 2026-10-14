/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.function.Function;

@FunctionalInterface
public interface Function1<T1, R>
extends Function<T1, R> {
    @Override
    public R apply(T1 var1);
}

