/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

@FunctionalInterface
interface ThrowingBiFunction<T1, T2, R, E extends Throwable> {
    public R apply(T1 var1, T2 var2) throws E;
}

