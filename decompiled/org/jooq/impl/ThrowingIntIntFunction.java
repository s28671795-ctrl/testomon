/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

@FunctionalInterface
interface ThrowingIntIntFunction<R, E extends Throwable> {
    public R apply(int var1, int var2) throws E;
}

