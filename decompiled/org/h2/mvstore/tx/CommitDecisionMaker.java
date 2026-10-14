/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.tx;

import org.h2.mvstore.MVMap;
import org.h2.mvstore.tx.TransactionStore;
import org.h2.mvstore.tx.VersionedValueCommitted;
import org.h2.value.VersionedValue;

final class CommitDecisionMaker<V>
extends MVMap.DecisionMaker<VersionedValue<V>> {
    private long undoKey;
    private MVMap.Decision decision;

    CommitDecisionMaker() {
    }

    void setUndoKey(long l) {
        this.undoKey = l;
        this.reset();
    }

    @Override
    public MVMap.Decision decide(VersionedValue<V> versionedValue, VersionedValue<V> versionedValue2) {
        assert (this.decision == null);
        this.decision = versionedValue == null || versionedValue.getOperationId() != this.undoKey ? MVMap.Decision.ABORT : (versionedValue.getCurrentValue() == null ? MVMap.Decision.REMOVE : MVMap.Decision.PUT);
        return this.decision;
    }

    @Override
    public <T extends VersionedValue<V>> T selectValue(T t2, T t3) {
        assert (this.decision == MVMap.Decision.PUT);
        assert (t2 != null);
        return (T)VersionedValueCommitted.getInstance(t2.getCurrentValue());
    }

    @Override
    public void reset() {
        this.decision = null;
    }

    public String toString() {
        return "commit " + TransactionStore.getTransactionId(this.undoKey);
    }
}

