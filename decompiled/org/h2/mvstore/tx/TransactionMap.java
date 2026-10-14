/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.tx;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.BitSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import org.h2.engine.IsolationLevel;
import org.h2.mvstore.Cursor;
import org.h2.mvstore.DataUtils;
import org.h2.mvstore.MVMap;
import org.h2.mvstore.MVStoreException;
import org.h2.mvstore.RootReference;
import org.h2.mvstore.tx.Record;
import org.h2.mvstore.tx.Snapshot;
import org.h2.mvstore.tx.Transaction;
import org.h2.mvstore.tx.TransactionStore;
import org.h2.mvstore.tx.TxDecisionMaker;
import org.h2.mvstore.tx.VersionedValueCommitted;
import org.h2.mvstore.tx.VersionedValueUncommitted;
import org.h2.mvstore.type.DataType;
import org.h2.value.VersionedValue;

public final class TransactionMap<K, V>
extends AbstractMap<K, V> {
    public final MVMap<K, VersionedValue<V>> map;
    private final Transaction transaction;
    private Snapshot<K, VersionedValue<V>> snapshot;
    private Snapshot<K, VersionedValue<V>> statementSnapshot;
    private boolean hasChanges;
    private final TxDecisionMaker<K, V> txDecisionMaker;
    private final TxDecisionMaker<K, V> ifAbsentDecisionMaker;
    private final TxDecisionMaker<K, V> lockDecisionMaker;

    TransactionMap(Transaction transaction, MVMap<K, VersionedValue<V>> mVMap) {
        this.transaction = transaction;
        this.map = mVMap;
        this.txDecisionMaker = new TxDecisionMaker(mVMap.getId(), transaction);
        this.ifAbsentDecisionMaker = new TxDecisionMaker.PutIfAbsentDecisionMaker<Object, Object>(mVMap.getId(), transaction, this::getFromSnapshot);
        this.lockDecisionMaker = transaction.allowNonRepeatableRead() ? new TxDecisionMaker.LockDecisionMaker(mVMap.getId(), transaction) : new TxDecisionMaker.RepeatableReadLockDecisionMaker<Object, Object>(mVMap.getId(), transaction, mVMap.getValueType(), this::getFromSnapshot);
    }

    public TransactionMap<K, V> getInstance(Transaction transaction) {
        return transaction.openMapX(this.map);
    }

    @Override
    public int size() {
        long l = this.sizeAsLong();
        return l > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)l;
    }

    public long sizeAsLongMax() {
        return this.map.sizeAsLong();
    }

    public long sizeAsLong() {
        long l;
        RootReference<Long, Record<?, ?>>[] rootReferenceArray;
        Snapshot<K, VersionedValue<V>> snapshot;
        IsolationLevel isolationLevel = this.transaction.getIsolationLevel();
        if (!isolationLevel.allowNonRepeatableRead() && this.hasChanges) {
            return this.sizeAsLongRepeatableReadWithChanges();
        }
        do {
            snapshot = this.getSnapshot();
            rootReferenceArray = this.getTransaction().getUndoLogRootReferences();
        } while (!snapshot.equals(this.getSnapshot()));
        RootReference rootReference = snapshot.root;
        long l2 = rootReference.getTotalCount();
        long l3 = l = rootReferenceArray == null ? l2 : TransactionStore.calculateUndoLogsTotalSize(rootReferenceArray);
        if (l == 0L) {
            return l2;
        }
        return this.adjustSize(rootReferenceArray, rootReference, isolationLevel == IsolationLevel.READ_UNCOMMITTED ? null : snapshot.committingTransactions, l2, l);
    }

    private long adjustSize(RootReference<Long, Record<?, ?>>[] rootReferenceArray, RootReference<K, VersionedValue<V>> rootReference, BitSet bitSet, long l, long l2) {
        if (2L * l2 > l) {
            Cursor<Object, VersionedValue<V>> cursor = this.map.cursor(rootReference, null, null, false);
            while (cursor.hasNext()) {
                cursor.next();
                VersionedValue<V> versionedValue = cursor.getValue();
                assert (versionedValue != null);
                long l3 = versionedValue.getOperationId();
                if (l3 == 0L || !this.isIrrelevant(l3, versionedValue, bitSet)) continue;
                --l;
            }
        } else {
            assert (rootReferenceArray != null);
            for (RootReference<Long, Record<?, ?>> rootReference2 : rootReferenceArray) {
                if (rootReference2 == null) continue;
                Cursor<Object, Record<?, ?>> cursor = rootReference2.root.map.cursor(rootReference2, null, null, false);
                while (cursor.hasNext()) {
                    VersionedValue<V> versionedValue;
                    cursor.next();
                    Record<?, ?> record = cursor.getValue();
                    if (record.mapId != this.map.getId() || (versionedValue = this.map.get(rootReference.root, record.key)) == null) continue;
                    long l4 = cursor.getKey();
                    assert (l4 != 0L);
                    if (versionedValue.getOperationId() != l4 || !this.isIrrelevant(l4, versionedValue, bitSet)) continue;
                    --l;
                }
            }
        }
        return l;
    }

    private boolean isIrrelevant(long l, VersionedValue<?> versionedValue, BitSet bitSet) {
        int n;
        Object obj = bitSet == null ? versionedValue.getCurrentValue() : ((n = TransactionStore.getTransactionId(l)) == this.transaction.transactionId || bitSet.get(n) ? versionedValue.getCurrentValue() : versionedValue.getCommittedValue());
        return obj == null;
    }

    private long sizeAsLongRepeatableReadWithChanges() {
        long l = 0L;
        RepeatableIterator repeatableIterator = new RepeatableIterator(this, null, null, false, false);
        while (repeatableIterator.fetchNext() != null) {
            ++l;
        }
        return l;
    }

    @Override
    public V remove(Object object) {
        return this.set(object, null);
    }

    @Override
    public V put(K k, V v) {
        DataUtils.checkArgument(v != null, "The value may not be null", new Object[0]);
        return this.set(k, v);
    }

    @Override
    public V putIfAbsent(K k, V v) {
        DataUtils.checkArgument(v != null, "The value may not be null", new Object[0]);
        this.ifAbsentDecisionMaker.initialize(k, v);
        V v2 = this.set(k, this.ifAbsentDecisionMaker, -1);
        if (this.ifAbsentDecisionMaker.getDecision() == MVMap.Decision.ABORT) {
            v2 = this.ifAbsentDecisionMaker.getLastValue();
        }
        return v2;
    }

    public void append(K k, V v) {
        this.map.append(k, VersionedValueUncommitted.getInstance(this.transaction.log(new Record(this.map.getId(), k, null)), v, null));
        this.hasChanges = true;
    }

    public V lock(K k) {
        return this.lock(k, -1);
    }

    public V lock(K k, int n) {
        this.lockDecisionMaker.initialize(k, null);
        return this.set(k, this.lockDecisionMaker, n);
    }

    public V putCommitted(K k, V v) {
        DataUtils.checkArgument(v != null, "The value may not be null", new Object[0]);
        VersionedValue<V> versionedValue = VersionedValueCommitted.getInstance(v);
        VersionedValue<V> versionedValue2 = this.map.put(k, versionedValue);
        V v2 = versionedValue2 == null ? null : (V)versionedValue2.getCurrentValue();
        return v2;
    }

    private V set(K k, V v) {
        this.txDecisionMaker.initialize(k, v);
        return this.set(k, this.txDecisionMaker, -1);
    }

    private V set(Object object, TxDecisionMaker<K, V> txDecisionMaker, int n) {
        VersionedValue versionedValue;
        Transaction transaction;
        String string = null;
        do {
            assert (this.transaction.getBlockerId() == 0);
            Object object2 = object;
            versionedValue = this.map.operate(object2, null, txDecisionMaker);
            MVMap.Decision decision = txDecisionMaker.getDecision();
            assert (decision != null);
            assert (decision != MVMap.Decision.REPEAT);
            transaction = txDecisionMaker.getBlockingTransaction();
            if (decision != MVMap.Decision.ABORT || transaction == null) {
                this.hasChanges |= decision != MVMap.Decision.ABORT;
                V v = versionedValue == null ? null : (V)versionedValue.getCurrentValue();
                return v;
            }
            txDecisionMaker.reset();
            if (n == -2) {
                return null;
            }
            if (string != null) continue;
            string = this.map.getName();
        } while (n != 0 && this.transaction.waitFor(transaction, string, object, n));
        throw DataUtils.newMVStoreException(101, "Map entry <{0}> with key <{1}> and value {2} is locked by tx {3} and can not be updated by tx {4} within allocated time interval {5} ms.", string, object, versionedValue, transaction.transactionId, this.transaction.transactionId, n == -1 ? this.transaction.timeoutMillis : n);
    }

    public boolean tryRemove(K k) {
        return this.trySet(k, null);
    }

    public boolean tryPut(K k, V v) {
        DataUtils.checkArgument(v != null, "The value may not be null", new Object[0]);
        return this.trySet(k, v);
    }

    public boolean trySet(K k, V v) {
        try {
            this.set(k, v);
            return true;
        }
        catch (MVStoreException mVStoreException) {
            return false;
        }
    }

    @Override
    public V get(Object object) {
        return this.getImmediate(object);
    }

    public V getFromSnapshot(K k) {
        Snapshot<K, VersionedValue<V>> snapshot;
        switch (this.transaction.isolationLevel) {
            case READ_UNCOMMITTED: {
                Snapshot<K, VersionedValue<V>> snapshot2 = this.getStatementSnapshot();
                VersionedValue<V> versionedValue = this.map.get(snapshot2.root.root, k);
                if (versionedValue != null) {
                    return versionedValue.getCurrentValue();
                }
                return null;
            }
            case REPEATABLE_READ: 
            case SNAPSHOT: 
            case SERIALIZABLE: {
                long l;
                if (!this.transaction.hasChanges()) break;
                snapshot = this.getStatementSnapshot();
                VersionedValue<V> versionedValue = this.map.get(snapshot.root.root, k);
                if (versionedValue == null || (l = versionedValue.getOperationId()) == 0L || this.transaction.transactionId != TransactionStore.getTransactionId(l)) break;
                return versionedValue.getCurrentValue();
            }
        }
        snapshot = this.getSnapshot();
        return this.getFromSnapshot(snapshot.root, snapshot.committingTransactions, k);
    }

    private V getFromSnapshot(RootReference<K, VersionedValue<V>> rootReference, BitSet bitSet, K k) {
        int n;
        VersionedValue<V> versionedValue = this.map.get(rootReference.root, k);
        if (versionedValue == null) {
            return null;
        }
        long l = versionedValue.getOperationId();
        if (l != 0L && (n = TransactionStore.getTransactionId(l)) != this.transaction.transactionId && !bitSet.get(n)) {
            return versionedValue.getCommittedValue();
        }
        return versionedValue.getCurrentValue();
    }

    public V getImmediate(K k) {
        return (V)this.useSnapshot((rootReference, bitSet) -> this.getFromSnapshot((RootReference<K, VersionedValue<V>>)rootReference, (BitSet)bitSet, k));
    }

    Snapshot<K, VersionedValue<V>> getSnapshot() {
        return this.snapshot == null ? this.createSnapshot() : this.snapshot;
    }

    Snapshot<K, VersionedValue<V>> getStatementSnapshot() {
        return this.statementSnapshot == null ? this.createSnapshot() : this.statementSnapshot;
    }

    void setStatementSnapshot(Snapshot<K, VersionedValue<V>> snapshot) {
        this.statementSnapshot = snapshot;
    }

    void promoteSnapshot() {
        if (this.snapshot == null) {
            this.snapshot = this.statementSnapshot;
        }
    }

    Snapshot<K, VersionedValue<V>> createSnapshot() {
        return this.useSnapshot(Snapshot::new);
    }

    <R> R useSnapshot(BiFunction<RootReference<K, VersionedValue<V>>, BitSet, R> biFunction) {
        RootReference<K, VersionedValue<V>> rootReference;
        BitSet bitSet;
        AtomicReference<BitSet> atomicReference = this.transaction.store.committingTransactions;
        BitSet bitSet2 = atomicReference.get();
        do {
            bitSet = bitSet2;
            rootReference = this.map.getRoot();
        } while ((bitSet2 = atomicReference.get()) != bitSet);
        return biFunction.apply(rootReference, bitSet2);
    }

    @Override
    public boolean containsKey(Object object) {
        return this.getImmediate(object) != null;
    }

    public boolean isDeletedByCurrentTransaction(K k) {
        VersionedValue<V> versionedValue = this.map.get(k);
        if (versionedValue != null) {
            long l = versionedValue.getOperationId();
            return l != 0L && TransactionStore.getTransactionId(l) == this.transaction.transactionId && versionedValue.getCurrentValue() == null;
        }
        return false;
    }

    public boolean isSameTransaction(K k) {
        VersionedValue<V> versionedValue = this.map.get(k);
        if (versionedValue == null) {
            return false;
        }
        int n = TransactionStore.getTransactionId(versionedValue.getOperationId());
        return n == this.transaction.transactionId;
    }

    public boolean isClosed() {
        return this.map.isClosed();
    }

    @Override
    public void clear() {
        this.map.clear();
        this.hasChanges = true;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return new AbstractSet<Map.Entry<K, V>>(){

            @Override
            public Iterator<Map.Entry<K, V>> iterator() {
                return TransactionMap.this.entryIterator(null, null);
            }

            @Override
            public int size() {
                return TransactionMap.this.size();
            }

            @Override
            public boolean contains(Object object) {
                return TransactionMap.this.containsKey(object);
            }
        };
    }

    public Map.Entry<K, V> firstEntry() {
        return (Map.Entry)this.chooseIterator(null, null, false, true).fetchNext();
    }

    public K firstKey() {
        return (K)this.chooseIterator(null, null, false, false).fetchNext();
    }

    public Map.Entry<K, V> lastEntry() {
        return (Map.Entry)this.chooseIterator(null, null, true, true).fetchNext();
    }

    public K lastKey() {
        return (K)this.chooseIterator(null, null, true, false).fetchNext();
    }

    public Map.Entry<K, V> higherEntry(K k) {
        return this.higherLowerEntry(k, false);
    }

    public K higherKey(K k) {
        return this.higherLowerKey(k, false);
    }

    public Map.Entry<K, V> ceilingEntry(K k) {
        return (Map.Entry)this.chooseIterator(k, null, false, true).fetchNext();
    }

    public K ceilingKey(K k) {
        return (K)this.chooseIterator(k, null, false, false).fetchNext();
    }

    public Map.Entry<K, V> floorEntry(K k) {
        return (Map.Entry)this.chooseIterator(k, null, true, true).fetchNext();
    }

    public K floorKey(K k) {
        return (K)this.chooseIterator(k, null, true, false).fetchNext();
    }

    public Map.Entry<K, V> lowerEntry(K k) {
        return this.higherLowerEntry(k, true);
    }

    public K lowerKey(K k) {
        return this.higherLowerKey(k, true);
    }

    private Map.Entry<K, V> higherLowerEntry(K k, boolean bl) {
        TMIterator tMIterator = this.chooseIterator(k, null, bl, true);
        Map.Entry entry = (Map.Entry)tMIterator.fetchNext();
        if (entry != null && this.map.getKeyType().compare(k, entry.getKey()) == 0) {
            entry = (Map.Entry)tMIterator.fetchNext();
        }
        return entry;
    }

    private K higherLowerKey(K k, boolean bl) {
        TMIterator tMIterator = this.chooseIterator(k, null, bl, false);
        Object x = tMIterator.fetchNext();
        if (x != null && this.map.getKeyType().compare(k, x) == 0) {
            x = tMIterator.fetchNext();
        }
        return (K)x;
    }

    public Iterator<K> keyIterator(K k) {
        return this.chooseIterator(k, null, false, false);
    }

    public TMIterator<K, V, K> keyIterator(K k, boolean bl) {
        return this.chooseIterator(k, null, bl, false);
    }

    public TMIterator<K, V, K> keyIterator(K k, K k2) {
        return this.chooseIterator(k, k2, false, false);
    }

    public TMIterator<K, V, K> keyIteratorUncommitted(K k, K k2) {
        return new ValidationIterator(this, k, k2);
    }

    public TMIterator<K, V, Map.Entry<K, V>> entryIterator(K k, K k2) {
        return this.chooseIterator(k, k2, false, true);
    }

    private <X> TMIterator<K, V, X> chooseIterator(K k, K k2, boolean bl, boolean bl2) {
        switch (this.transaction.isolationLevel) {
            case READ_UNCOMMITTED: {
                return new UncommittedIterator(this, k, k2, bl, bl2);
            }
            case REPEATABLE_READ: 
            case SNAPSHOT: 
            case SERIALIZABLE: {
                if (!this.hasChanges) break;
                return new RepeatableIterator(this, k, k2, bl, bl2);
            }
        }
        return new CommittedIterator(this, k, k2, bl, bl2);
    }

    public Transaction getTransaction() {
        return this.transaction;
    }

    public DataType<K> getKeyType() {
        return this.map.getKeyType();
    }

    public static abstract class TMIterator<K, V, X>
    implements Iterator<X> {
        final int transactionId;
        final BitSet committingTransactions;
        protected final Cursor<K, VersionedValue<V>> cursor;
        private final boolean forEntries;
        X current;

        TMIterator(TransactionMap<K, V> transactionMap, K k, K k2, Snapshot<K, VersionedValue<V>> snapshot, boolean bl, boolean bl2) {
            Transaction transaction = transactionMap.getTransaction();
            this.transactionId = transaction.transactionId;
            this.forEntries = bl2;
            this.cursor = transactionMap.map.cursor(snapshot.root, k, k2, bl);
            this.committingTransactions = snapshot.committingTransactions;
        }

        final X toElement(K k, Object object) {
            return (X)(this.forEntries ? new AbstractMap.SimpleImmutableEntry<K, Object>(k, object) : k);
        }

        public abstract X fetchNext();

        @Override
        public final boolean hasNext() {
            return this.current != null || (this.current = this.fetchNext()) != null;
        }

        @Override
        public final X next() {
            X x = this.current;
            if (x == null) {
                x = this.fetchNext();
                if (x == null) {
                    throw new NoSuchElementException();
                }
            } else {
                this.current = null;
            }
            return x;
        }
    }

    private static final class RepeatableIterator<K, V, X>
    extends TMIterator<K, V, X> {
        private final DataType<K> keyType;
        private K snapshotKey;
        private Object snapshotValue;
        private final Cursor<K, VersionedValue<V>> uncommittedCursor;
        private K uncommittedKey;
        private V uncommittedValue;

        RepeatableIterator(TransactionMap<K, V> transactionMap, K k, K k2, boolean bl, boolean bl2) {
            super(transactionMap, k, k2, transactionMap.getSnapshot(), bl, bl2);
            this.keyType = transactionMap.map.getKeyType();
            Snapshot<K, VersionedValue<V>> snapshot = transactionMap.getStatementSnapshot();
            this.uncommittedCursor = transactionMap.map.cursor(snapshot.root, k, k2, bl);
        }

        @Override
        public X fetchNext() {
            X x = null;
            do {
                int n;
                if (this.snapshotKey == null) {
                    this.fetchSnapshot();
                }
                if (this.uncommittedKey == null) {
                    this.fetchUncommitted();
                }
                if (this.snapshotKey == null && this.uncommittedKey == null) break;
                int n2 = this.snapshotKey == null ? 1 : (n = this.uncommittedKey == null ? -1 : this.keyType.compare(this.snapshotKey, this.uncommittedKey));
                if (n < 0) {
                    x = this.toElement(this.snapshotKey, this.snapshotValue);
                    this.snapshotKey = null;
                    break;
                }
                if (this.uncommittedValue != null) {
                    x = this.toElement(this.uncommittedKey, this.uncommittedValue);
                }
                if (n == 0) {
                    this.snapshotKey = null;
                }
                this.uncommittedKey = null;
            } while (x == null);
            return x;
        }

        private void fetchSnapshot() {
            while (this.cursor.hasNext()) {
                int n;
                Object k = this.cursor.next();
                VersionedValue versionedValue = (VersionedValue)this.cursor.getValue();
                if (versionedValue == null) continue;
                Object t2 = versionedValue.getCommittedValue();
                long l = versionedValue.getOperationId();
                if (l != 0L && ((n = TransactionStore.getTransactionId(l)) == this.transactionId || this.committingTransactions.get(n))) {
                    t2 = versionedValue.getCurrentValue();
                }
                if (t2 == null) continue;
                this.snapshotKey = k;
                this.snapshotValue = t2;
                return;
            }
        }

        private void fetchUncommitted() {
            while (this.uncommittedCursor.hasNext()) {
                long l;
                K k = this.uncommittedCursor.next();
                VersionedValue<V> versionedValue = this.uncommittedCursor.getValue();
                if (versionedValue == null || (l = versionedValue.getOperationId()) == 0L || this.transactionId != TransactionStore.getTransactionId(l)) continue;
                this.uncommittedKey = k;
                this.uncommittedValue = versionedValue.getCurrentValue();
                return;
            }
        }
    }

    private static final class CommittedIterator<K, V, X>
    extends TMIterator<K, V, X> {
        CommittedIterator(TransactionMap<K, V> transactionMap, K k, K k2, boolean bl, boolean bl2) {
            super(transactionMap, k, k2, transactionMap.getSnapshot(), bl, bl2);
        }

        @Override
        public X fetchNext() {
            while (this.cursor.hasNext()) {
                int n;
                Object k = this.cursor.next();
                VersionedValue versionedValue = (VersionedValue)this.cursor.getValue();
                if (versionedValue == null) continue;
                long l = versionedValue.getOperationId();
                if (l != 0L && (n = TransactionStore.getTransactionId(l)) != this.transactionId && !this.committingTransactions.get(n)) {
                    Object t2 = versionedValue.getCommittedValue();
                    if (t2 == null) continue;
                    return this.toElement(k, t2);
                }
                Object t3 = versionedValue.getCurrentValue();
                if (t3 == null) continue;
                return this.toElement(k, t3);
            }
            return null;
        }
    }

    private static final class ValidationIterator<K, V, X>
    extends UncommittedIterator<K, V, X> {
        ValidationIterator(TransactionMap<K, V> transactionMap, K k, K k2) {
            super(transactionMap, k, k2, transactionMap.createSnapshot(), false, false);
        }

        @Override
        boolean shouldIgnoreRemoval(VersionedValue<?> versionedValue) {
            assert (versionedValue.getCurrentValue() == null);
            long l = versionedValue.getOperationId();
            if (l != 0L) {
                int n = TransactionStore.getTransactionId(l);
                return this.transactionId != n && !this.committingTransactions.get(n);
            }
            return false;
        }
    }

    private static class UncommittedIterator<K, V, X>
    extends TMIterator<K, V, X> {
        UncommittedIterator(TransactionMap<K, V> transactionMap, K k, K k2, boolean bl, boolean bl2) {
            super(transactionMap, k, k2, transactionMap.createSnapshot(), bl, bl2);
        }

        UncommittedIterator(TransactionMap<K, V> transactionMap, K k, K k2, Snapshot<K, VersionedValue<V>> snapshot, boolean bl, boolean bl2) {
            super(transactionMap, k, k2, snapshot, bl, bl2);
        }

        @Override
        public final X fetchNext() {
            while (this.cursor.hasNext()) {
                Object t2;
                Object k = this.cursor.next();
                VersionedValue versionedValue = (VersionedValue)this.cursor.getValue();
                if (versionedValue == null || (t2 = versionedValue.getCurrentValue()) == null && !this.shouldIgnoreRemoval(versionedValue)) continue;
                return this.toElement(k, t2);
            }
            return null;
        }

        boolean shouldIgnoreRemoval(VersionedValue<?> versionedValue) {
            return false;
        }
    }
}

