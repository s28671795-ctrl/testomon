/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.tx;

import org.h2.mvstore.tx.TransactionStore;
import org.h2.mvstore.tx.VersionedValueCommitted;
import org.h2.value.VersionedValue;

class VersionedValueUncommitted<T>
extends VersionedValueCommitted<T> {
    private final long operationId;
    private final T committedValue;

    private VersionedValueUncommitted(long l, T t2, T t3) {
        super(t2);
        assert (l != 0L);
        this.operationId = l;
        this.committedValue = t3;
    }

    static <X> VersionedValue<X> getInstance(long l, X x, X x2) {
        return new VersionedValueUncommitted<X>(l, x, x2);
    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public long getOperationId() {
        return this.operationId;
    }

    @Override
    public T getCommittedValue() {
        return this.committedValue;
    }

    @Override
    public String toString() {
        return super.toString() + " " + TransactionStore.getTransactionId(this.operationId) + "/" + TransactionStore.getLogId(this.operationId) + " " + this.committedValue;
    }
}

