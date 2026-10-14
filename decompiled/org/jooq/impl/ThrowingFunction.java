/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

@FunctionalInterface
interface ThrowingFunction<T, R, E extends Throwable> {
    public R apply(T var1) throws E;
}

