/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.tx;

import java.util.function.Function;
import org.h2.mvstore.DataUtils;
import org.h2.mvstore.MVMap;
import org.h2.mvstore.tx.Record;
import org.h2.mvstore.tx.Transaction;
import org.h2.mvstore.tx.TransactionStore;
import org.h2.mvstore.tx.VersionedValueCommitted;
import org.h2.mvstore.tx.VersionedValueUncommitted;
import org.h2.mvstore.type.DataType;
import org.h2.value.VersionedValue;

class TxDecisionMaker<K, V>
extends MVMap.DecisionMaker<VersionedValue<V>> {
    private final int mapId;
    protected K key;
    private V value;
    private final Transaction transaction;
    private long undoKey;
    private long lastOperationId;
    private Transaction blockingTransaction;
    private MVMap.Decision decision;
    private V lastValue;

    TxDecisionMaker(int n, Transaction transaction) {
        this.mapId = n;
        this.transaction = transaction;
    }

    void initialize(K k, V v) {
        this.key = k;
        this.value = v;
        this.decision = null;
        this.reset();
    }

    @Override
    public MVMap.Decision decide(VersionedValue<V> versionedValue, VersionedValue<V> versionedValue2) {
        int n;
        long l;
        assert (this.decision == null);
        if (versionedValue == null || (l = versionedValue.getOperationId()) == 0L || this.isThisTransaction(n = TransactionStore.getTransactionId(l))) {
            this.logAndDecideToPut(versionedValue, versionedValue == null ? null : (V)versionedValue.getCommittedValue());
        } else if (this.isCommitted(n)) {
            V v = versionedValue.getCurrentValue();
            this.logAndDecideToPut(v == null ? null : VersionedValueCommitted.getInstance(v), v);
        } else if (this.getBlockingTransaction() != null) {
            this.lastValue = versionedValue.getCurrentValue();
            this.decision = MVMap.Decision.ABORT;
        } else if (this.isRepeatedOperation(l)) {
            V v = versionedValue.getCommittedValue();
            this.logAndDecideToPut(v == null ? null : VersionedValueCommitted.getInstance(v), v);
        } else {
            this.decision = MVMap.Decision.REPEAT;
        }
        return this.decision;
    }

    @Override
    public final void reset() {
        if (this.decision != MVMap.Decision.REPEAT) {
            this.lastOperationId = 0L;
            if (this.decision == MVMap.Decision.PUT) {
                this.transaction.logUndo();
            }
        }
        this.blockingTransaction = null;
        this.decision = null;
        this.lastValue = null;
    }

    @Override
    public <T extends VersionedValue<V>> T selectValue(T t2, T t3) {
        return (T)VersionedValueUncommitted.getInstance(this.undoKey, this.getNewValue(t2), this.lastValue);
    }

    V getNewValue(VersionedValue<V> versionedValue) {
        return this.value;
    }

    MVMap.Decision logAndDecideToPut(VersionedValue<V> versionedValue, V v) {
        this.undoKey = this.transaction.log(new Record<K, V>(this.mapId, this.key, versionedValue));
        this.lastValue = v;
        return this.setDecision(MVMap.Decision.PUT);
    }

    final MVMap.Decision decideToAbort(V v) {
        this.lastValue = v;
        return this.setDecision(MVMap.Decision.ABORT);
    }

    final boolean allowNonRepeatableRead() {
        return this.transaction.allowNonRepeatableRead();
    }

    final MVMap.Decision getDecision() {
        return this.decision;
    }

    final Transaction getBlockingTransaction() {
        return this.blockingTransaction;
    }

    final V getLastValue() {
        return this.lastValue;
    }

    final boolean isThisTransaction(int n) {
        return n == this.transaction.transactionId;
    }

    final boolean isCommitted(int n) {
        boolean bl;
        Transaction transaction;
        TransactionStore transactionStore = this.transaction.store;
        do {
            transaction = transactionStore.getTransaction(n);
            bl = transactionStore.committingTransactions.get().get(n);
        } while (transaction != transactionStore.getTransaction(n));
        if (!bl) {
            this.blockingTransaction = transaction;
        }
        return bl;
    }

    final boolean isRepeatedOperation(long l) {
        if (l == this.lastOperationId) {
            return true;
        }
        this.lastOperationId = l;
        return false;
    }

    final MVMap.Decision setDecision(MVMap.Decision decision) {
        this.decision = decision;
        return this.decision;
    }

    public final String toString() {
        return "txdm " + this.transaction.transactionId;
    }

    public static final class RepeatableReadLockDecisionMaker<K, V>
    extends LockDecisionMaker<K, V> {
        private final DataType<VersionedValue<V>> valueType;
        private final Function<K, V> snapshotValueSupplier;

        RepeatableReadLockDecisionMaker(int n, Transaction transaction, DataType<VersionedValue<V>> dataType, Function<K, V> function) {
            super(n, transaction);
            this.valueType = dataType;
            this.snapshotValueSupplier = function;
        }

        @Override
        MVMap.Decision logAndDecideToPut(VersionedValue<V> versionedValue, V v) {
            V v2 = this.snapshotValueSupplier.apply(this.key);
            if (v2 != null && (versionedValue == null || this.valueType.compare(VersionedValueCommitted.getInstance(v2), versionedValue) != 0)) {
                throw DataUtils.newMVStoreException(105, "", new Object[0]);
            }
            return super.logAndDecideToPut(versionedValue, v);
        }
    }

    public static class LockDecisionMaker<K, V>
    extends TxDecisionMaker<K, V> {
        LockDecisionMaker(int n, Transaction transaction) {
            super(n, transaction);
        }

        @Override
        public MVMap.Decision decide(VersionedValue<V> versionedValue, VersionedValue<V> versionedValue2) {
            MVMap.Decision decision = super.decide(versionedValue, versionedValue2);
            if (versionedValue == null) {
                assert (decision == MVMap.Decision.PUT);
                decision = this.setDecision(MVMap.Decision.REMOVE);
            }
            return decision;
        }

        @Override
        V getNewValue(VersionedValue<V> versionedValue) {
            return versionedValue == null ? null : (V)versionedValue.getCurrentValue();
        }
    }

    public static final class PutIfAbsentDecisionMaker<K, V>
    extends TxDecisionMaker<K, V> {
        private final Function<K, V> oldValueSupplier;

        PutIfAbsentDecisionMaker(int n, Transaction transaction, Function<K, V> function) {
            super(n, transaction);
            this.oldValueSupplier = function;
        }

        @Override
        public MVMap.Decision decide(VersionedValue<V> versionedValue, VersionedValue<V> versionedValue2) {
            int n;
            assert (this.getDecision() == null);
            if (versionedValue == null) {
                V v = this.getValueInSnapshot();
                if (v != null) {
                    return this.decideToAbort(v);
                }
                return this.logAndDecideToPut(null, null);
            }
            long l = versionedValue.getOperationId();
            if (l == 0L || this.isThisTransaction(n = TransactionStore.getTransactionId(l))) {
                V v;
                if (versionedValue.getCurrentValue() != null) {
                    return this.decideToAbort(versionedValue.getCurrentValue());
                }
                if (l == 0L && (v = this.getValueInSnapshot()) != null) {
                    return this.decideToAbort(v);
                }
                return this.logAndDecideToPut(versionedValue, versionedValue.getCommittedValue());
            }
            if (this.isCommitted(n)) {
                if (versionedValue.getCurrentValue() != null) {
                    return this.decideToAbort(versionedValue.getCurrentValue());
                }
                V v = this.getValueInSnapshot();
                if (v != null) {
                    return this.decideToAbort(v);
                }
                return this.logAndDecideToPut(null, null);
            }
            if (this.getBlockingTransaction() != null) {
                return this.decideToAbort(versionedValue.getCurrentValue());
            }
            if (this.isRepeatedOperation(l)) {
                V v = versionedValue.getCommittedValue();
                if (v != null) {
                    return this.decideToAbort(v);
                }
                return this.logAndDecideToPut(null, null);
            }
            return this.setDecision(MVMap.Decision.REPEAT);
        }

        private V getValueInSnapshot() {
            return this.allowNonRepeatableRead() ? null : (V)this.oldValueSupplier.apply(this.key);
        }
    }
}

