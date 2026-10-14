/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.lang3.function;

import java.util.Objects;

@FunctionalInterface
public interface FailableBiPredicate<T, U, E extends Throwable> {
    public static final FailableBiPredicate FALSE = (t2, u) -> false;
    public static final FailableBiPredicate TRUE = (t2, u) -> true;

    public static <T, U, E extends Throwable> FailableBiPredicate<T, U, E> falsePredicate() {
        return FALSE;
    }

    public static <T, U, E extends Throwable> FailableBiPredicate<T, U, E> truePredicate() {
        return TRUE;
    }

    default public FailableBiPredicate<T, U, E> and(FailableBiPredicate<? super T, ? super U, E> other) {
        Objects.requireNonNull(other);
        return (t2, u) -> this.test(t2, u) && other.test(t2, u);
    }

    default public FailableBiPredicate<T, U, E> negate() {
        return (t2, u) -> !this.test(t2, u);
    }

    default public FailableBiPredicate<T, U, E> or(FailableBiPredicate<? super T, ? super U, E> other) {
        Objects.requireNonNull(other);
        return (t2, u) -> this.test(t2, u) || other.test(t2, u);
    }

    public boolean test(T var1, U var2) throws E;
}

