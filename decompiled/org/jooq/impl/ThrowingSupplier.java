/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

@FunctionalInterface
interface ThrowingSupplier<T, E extends Throwable> {
    public T get() throws E;
}

