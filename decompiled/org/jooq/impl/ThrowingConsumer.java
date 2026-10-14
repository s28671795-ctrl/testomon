/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

@FunctionalInterface
interface ThrowingConsumer<T, E extends Throwable> {
    public void accept(T var1) throws E;
}

