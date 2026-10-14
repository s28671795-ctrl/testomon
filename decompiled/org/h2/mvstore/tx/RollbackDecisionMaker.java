/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.tx;

import org.h2.mvstore.MVMap;
import org.h2.mvstore.tx.Record;
import org.h2.mvstore.tx.TransactionStore;
import org.h2.value.VersionedValue;

final class RollbackDecisionMaker
extends MVMap.DecisionMaker<Record<?, ?>> {
    private final TransactionStore store;
    private final long transactionId;
    private final long toLogId;
    private final TransactionStore.RollbackListener listener;
    private MVMap.Decision decision;

    RollbackDecisionMaker(TransactionStore transactionStore, long l, long l2, TransactionStore.RollbackListener rollbackListener) {
        this.store = transactionStore;
        this.transactionId = l;
        this.toLogId = l2;
        this.listener = rollbackListener;
    }

    @Override
    public MVMap.Decision decide(Record record, Record record2) {
        assert (this.decision == null);
        if (record == null) {
            this.decision = MVMap.Decision.ABORT;
        } else {
            int n;
            MVMap<Object, VersionedValue<Object>> mVMap;
            long l;
            VersionedValue<Object> versionedValue = record.oldValue;
            if ((versionedValue == null || (l = versionedValue.getOperationId()) == 0L || (long)TransactionStore.getTransactionId(l) == this.transactionId && TransactionStore.getLogId(l) < this.toLogId) && (mVMap = this.store.openMap(n = record.mapId)) != null && !mVMap.isClosed()) {
                Object k = record.key;
                VersionedValue versionedValue2 = (VersionedValue)mVMap.operate(k, versionedValue, MVMap.DecisionMaker.DEFAULT);
                this.listener.onRollback(mVMap, k, versionedValue2, versionedValue);
            }
            this.decision = MVMap.Decision.REMOVE;
        }
        return this.decision;
    }

    @Override
    public void reset() {
        this.decision = null;
    }

    public String toString() {
        return "rollback-" + this.transactionId;
    }
}

