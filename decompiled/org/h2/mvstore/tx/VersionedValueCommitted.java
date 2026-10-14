/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.tx;

import org.h2.value.VersionedValue;

class VersionedValueCommitted<T>
extends VersionedValue<T> {
    public final T value;

    VersionedValueCommitted(T t2) {
        this.value = t2;
    }

    static <X> VersionedValue<X> getInstance(X x) {
        assert (x != null);
        return x instanceof VersionedValue ? (VersionedValue<Object>)x : new VersionedValueCommitted<X>(x);
    }

    @Override
    public T getCurrentValue() {
        return this.value;
    }

    @Override
    public T getCommittedValue() {
        return this.value;
    }

    public String toString() {
        return String.valueOf(this.value);
    }
}

