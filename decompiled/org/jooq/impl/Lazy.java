/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.function.Supplier;

final class Lazy<T> {
    private final Supplier<? extends T> supplier;
    private volatile T value;

    static <T> Lazy<T> of(Supplier<? extends T> supplier) {
        return new Lazy<T>(supplier);
    }

    private Lazy(Supplier<? extends T> supplier) {
        this.supplier = supplier;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    T get() {
        if (this.value == null) {
            Lazy lazy = this;
            synchronized (lazy) {
                if (this.value == null) {
                    this.value = this.supplier.get();
                }
            }
        }
        return this.value;
    }
}

