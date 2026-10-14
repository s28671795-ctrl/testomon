/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

@FunctionalInterface
interface ThrowingObjIntFunction<T, R, E extends Throwable> {
    public R apply(T var1, int var2) throws E;
}

