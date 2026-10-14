/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

@FunctionalInterface
interface ThrowingIntPredicate<T, E extends Throwable> {
    public boolean test(T var1, int var2) throws E;

    default public ThrowingIntPredicate<T, E> negate() {
        return (t2, i) -> !this.test(t2, i);
    }
}

