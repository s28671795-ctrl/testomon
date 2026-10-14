/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

@FunctionalInterface
interface ThrowingPredicate<T, E extends Throwable> {
    public boolean test(T var1) throws E;

    default public ThrowingPredicate<T, E> negate() {
        return t2 -> !this.test(t2);
    }
}

