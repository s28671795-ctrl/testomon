/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

@FunctionalInterface
interface ThrowingIntFunction<R, E extends Throwable> {
    public R apply(int var1) throws E;
}

