/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.tx;

import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.h2.engine.IsolationLevel;
import org.h2.mvstore.DataUtils;
import org.h2.mvstore.MVMap;
import org.h2.mvstore.MVStore;
import org.h2.mvstore.RootReference;
import org.h2.mvstore.tx.Record;
import org.h2.mvstore.tx.Snapshot;
import org.h2.mvstore.tx.TransactionMap;
import org.h2.mvstore.tx.TransactionStore;
import org.h2.mvstore.type.DataType;
import org.h2.value.VersionedValue;

public final class Transaction {
    public static final int STATUS_CLOSED = 0;
    public static final int STATUS_OPEN = 1;
    public static final int STATUS_PREPARED = 2;
    public static final int STATUS_COMMITTED = 3;
    private static final int STATUS_ROLLING_BACK = 4;
    private static final int STATUS_ROLLED_BACK = 5;
    private static final String[] STATUS_NAMES = new String[]{"CLOSED", "OPEN", "PREPARED", "COMMITTED", "ROLLING_BACK", "ROLLED_BACK"};
    static final int LOG_ID_BITS = 40;
    private static final int LOG_ID_BITS1 = 41;
    private static final long LOG_ID_LIMIT = 0x10000000000L;
    private static final long LOG_ID_MASK = 0x1FFFFFFFFFFL;
    private static final int STATUS_BITS = 4;
    private static final int STATUS_MASK = 15;
    final TransactionStore store;
    final TransactionStore.RollbackListener listener;
    final int transactionId;
    final long sequenceNum;
    private final AtomicLong statusAndLogId;
    private MVStore.TxCounter txCounter;
    private String name;
    boolean wasStored;
    int timeoutMillis;
    private final int ownerId;
    private volatile Transaction blockingTransaction;
    private String blockingMapName;
    private Object blockingKey;
    private volatile boolean notificationRequested;
    private RootReference<Long, Record<?, ?>>[] undoLogRootReferences;
    private final Map<Integer, TransactionMap<?, ?>> transactionMaps = new HashMap();
    final IsolationLevel isolationLevel;

    Transaction(TransactionStore transactionStore, int n, long l, int n2, String string, long l2, int n3, int n4, IsolationLevel isolationLevel, TransactionStore.RollbackListener rollbackListener) {
        this.store = transactionStore;
        this.transactionId = n;
        this.sequenceNum = l;
        this.statusAndLogId = new AtomicLong(Transaction.composeState(n2, l2, false));
        this.name = string;
        this.setTimeoutMillis(n3);
        this.ownerId = n4;
        this.isolationLevel = isolationLevel;
        this.listener = rollbackListener;
    }

    public int getId() {
        return this.transactionId;
    }

    public long getSequenceNum() {
        return this.sequenceNum;
    }

    public int getStatus() {
        return Transaction.getStatus(this.statusAndLogId.get());
    }

    RootReference<Long, Record<?, ?>>[] getUndoLogRootReferences() {
        return this.undoLogRootReferences;
    }

    private long setStatus(int n) {
        long l;
        long l2;
        long l3;
        do {
            boolean bl;
            l3 = this.statusAndLogId.get();
            l = Transaction.getLogId(l3);
            int n2 = Transaction.getStatus(l3);
            switch (n) {
                case 4: {
                    bl = n2 == 1;
                    break;
                }
                case 2: {
                    bl = n2 == 1;
                    break;
                }
                case 3: {
                    bl = n2 == 1 || n2 == 2 || n2 == 3;
                    break;
                }
                case 5: {
                    bl = n2 == 1 || n2 == 2 || n2 == 4;
                    break;
                }
                case 0: {
                    bl = n2 == 3 || n2 == 5;
                    break;
                }
                default: {
                    bl = false;
                }
            }
            if (bl) continue;
            throw DataUtils.newMVStoreException(103, "Transaction was illegally transitioned from {0} to {1}", Transaction.getStatusName(n2), Transaction.getStatusName(n));
        } while (!this.statusAndLogId.compareAndSet(l3, l2 = Transaction.composeState(n, l, Transaction.hasRollback(l3))));
        return l3;
    }

    public boolean hasChanges() {
        return Transaction.hasChanges(this.statusAndLogId.get());
    }

    public void setName(String string) {
        this.checkNotClosed();
        this.name = string;
        this.store.storeTransaction(this);
    }

    public String getName() {
        return this.name;
    }

    public int getBlockerId() {
        Transaction transaction = this.blockingTransaction;
        return transaction == null ? 0 : transaction.ownerId;
    }

    public long setSavepoint() {
        return this.getLogId();
    }

    public boolean hasStatementDependencies() {
        return !this.transactionMaps.isEmpty();
    }

    public IsolationLevel getIsolationLevel() {
        return this.isolationLevel;
    }

    boolean isReadCommitted() {
        return this.isolationLevel == IsolationLevel.READ_COMMITTED;
    }

    public boolean allowNonRepeatableRead() {
        return this.isolationLevel.allowNonRepeatableRead();
    }

    public void markStatementStart(HashSet<MVMap<Object, VersionedValue<Object>>> hashSet) {
        this.markStatementEnd();
        if (this.txCounter == null && this.store.store.isVersioningRequired()) {
            this.txCounter = this.store.store.registerVersionUsage();
        }
        if (hashSet != null && !hashSet.isEmpty()) {
            TransactionMap<Object, Object> transactionMap;
            BitSet bitSet;
            do {
                bitSet = this.store.committingTransactions.get();
                for (MVMap<Object, VersionedValue<Object>> mVMap : hashSet) {
                    transactionMap = this.openMapX(mVMap);
                    transactionMap.setStatementSnapshot(new Snapshot<Object, VersionedValue<Object>>(mVMap.flushAndGetRoot(), bitSet));
                }
                if (!this.isReadCommitted()) continue;
                this.undoLogRootReferences = this.store.collectUndoLogRootReferences();
            } while (bitSet != this.store.committingTransactions.get());
            for (MVMap<Object, VersionedValue<Object>> mVMap : hashSet) {
                transactionMap = this.openMapX(mVMap);
                transactionMap.promoteSnapshot();
            }
        }
    }

    public void markStatementEnd() {
        if (this.allowNonRepeatableRead()) {
            this.releaseSnapshot();
        }
        for (TransactionMap<?, ?> transactionMap : this.transactionMaps.values()) {
            transactionMap.setStatementSnapshot(null);
        }
    }

    private void markTransactionEnd() {
        if (!this.allowNonRepeatableRead()) {
            this.releaseSnapshot();
        }
    }

    private void releaseSnapshot() {
        this.transactionMaps.clear();
        this.undoLogRootReferences = null;
        MVStore.TxCounter txCounter = this.txCounter;
        if (txCounter != null) {
            this.txCounter = null;
            this.store.store.deregisterVersionUsage(txCounter);
        }
    }

    long log(Record<?, ?> record) {
        long l = this.statusAndLogId.getAndIncrement();
        long l2 = Transaction.getLogId(l);
        if (l2 >= 0x10000000000L) {
            throw DataUtils.newMVStoreException(104, "Transaction {0} has too many changes", this.transactionId);
        }
        int n = Transaction.getStatus(l);
        this.checkOpen(n);
        long l3 = this.store.addUndoLogRecord(this.transactionId, l2, record);
        return l3;
    }

    void logUndo() {
        long l = this.statusAndLogId.decrementAndGet();
        long l2 = Transaction.getLogId(l);
        if (l2 >= 0x10000000000L) {
            throw DataUtils.newMVStoreException(100, "Transaction {0} has internal error", this.transactionId);
        }
        int n = Transaction.getStatus(l);
        this.checkOpen(n);
        this.store.removeUndoLogRecord(this.transactionId);
    }

    public <K, V> TransactionMap<K, V> openMap(String string) {
        return this.openMap(string, null, null);
    }

    public <K, V> TransactionMap<K, V> openMap(String string, DataType<K> dataType, DataType<V> dataType2) {
        MVMap<K, VersionedValue<V>> mVMap = this.store.openVersionedMap(string, dataType, dataType2);
        return this.openMapX(mVMap);
    }

    public <K, V> TransactionMap<K, V> openMapX(MVMap<K, VersionedValue<V>> mVMap) {
        this.checkNotClosed();
        int n = mVMap.getId();
        TransactionMap<Object, Object> transactionMap = this.transactionMaps.get(n);
        if (transactionMap == null) {
            transactionMap = new TransactionMap<K, V>(this, mVMap);
            this.transactionMaps.put(n, transactionMap);
        }
        return transactionMap;
    }

    public void prepare() {
        this.setStatus(2);
        this.store.storeTransaction(this);
    }

    public void commit() {
        assert (this.store.openTransactions.get().get(this.transactionId));
        this.markTransactionEnd();
        Throwable throwable = null;
        boolean bl = false;
        int n = 1;
        try {
            long l = this.setStatus(3);
            bl = Transaction.hasChanges(l);
            n = Transaction.getStatus(l);
            if (bl) {
                this.store.commit(this, n == 3);
            }
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            if (Transaction.isActive(n)) {
                try {
                    this.store.endTransaction(this, bl);
                }
                catch (Throwable throwable3) {
                    if (throwable == null) {
                        throw throwable3;
                    }
                    throwable.addSuppressed(throwable3);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void rollbackToSavepoint(long l) {
        boolean bl;
        long l2 = this.setStatus(4);
        long l3 = Transaction.getLogId(l2);
        try {
            this.store.rollbackTo(this, l3, l);
        }
        finally {
            this.notifyAllWaitingTransactions();
            long l4 = Transaction.composeState(4, l3, Transaction.hasRollback(l2));
            long l5 = Transaction.composeState(1, l, true);
            while (!(bl = this.statusAndLogId.compareAndSet(l4, l5)) && this.statusAndLogId.get() == l4) {
            }
        }
        if (!bl) {
            throw DataUtils.newMVStoreException(103, "Transaction {0} concurrently modified while rollback to savepoint was in progress", this.transactionId);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void rollback() {
        this.markTransactionEnd();
        Throwable throwable = null;
        int n = 1;
        try {
            long l = this.setStatus(5);
            n = Transaction.getStatus(l);
            long l2 = Transaction.getLogId(l);
            if (l2 > 0L) {
                this.store.rollbackTo(this, l2, 0L);
            }
        }
        catch (Throwable throwable2) {
            n = this.getStatus();
            if (Transaction.isActive(n)) {
                throwable = throwable2;
                throw throwable2;
            }
        }
        finally {
            try {
                if (Transaction.isActive(n)) {
                    this.store.endTransaction(this, true);
                }
            }
            catch (Throwable throwable3) {
                if (throwable == null) {
                    throw throwable3;
                }
                throwable.addSuppressed(throwable3);
            }
        }
    }

    private static boolean isActive(int n) {
        return n != 0 && n != 3 && n != 5;
    }

    public Iterator<TransactionStore.Change> getChanges(long l) {
        return this.store.getChanges(this, this.getLogId(), l);
    }

    public void setTimeoutMillis(int n) {
        this.timeoutMillis = n > 0 ? n : this.store.timeoutMillis;
    }

    private long getLogId() {
        return Transaction.getLogId(this.statusAndLogId.get());
    }

    private void checkOpen(int n) {
        if (n != 1) {
            throw DataUtils.newMVStoreException(103, "Transaction {0} has status {1}, not OPEN", this.transactionId, Transaction.getStatusName(n));
        }
    }

    private void checkNotClosed() {
        if (this.getStatus() == 0) {
            throw DataUtils.newMVStoreException(4, "Transaction {0} is closed", this.transactionId);
        }
    }

    void closeIt() {
        this.transactionMaps.clear();
        long l = this.setStatus(0);
        this.store.store.deregisterVersionUsage(this.txCounter);
        if (Transaction.hasChanges(l) || Transaction.hasRollback(l)) {
            this.notifyAllWaitingTransactions();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void notifyAllWaitingTransactions() {
        if (this.notificationRequested) {
            Transaction transaction = this;
            synchronized (transaction) {
                this.notifyAll();
            }
        }
    }

    public boolean waitFor(Transaction transaction, String string, Object object, int n) {
        this.blockingTransaction = transaction;
        this.blockingMapName = string;
        this.blockingKey = object;
        if (this.isDeadlocked(transaction)) {
            this.tryThrowDeadLockException(false);
        }
        boolean bl = transaction.waitForThisToEnd(n == -1 ? this.timeoutMillis : n, this);
        this.blockingMapName = null;
        this.blockingKey = null;
        this.blockingTransaction = null;
        return bl;
    }

    private boolean isDeadlocked(Transaction transaction) {
        Transaction transaction2;
        Transaction transaction3 = transaction;
        Transaction transaction4 = transaction;
        for (int i = this.store.getMaxTransactionId(); (transaction2 = transaction4.blockingTransaction) != null && transaction4.getStatus() == 1 && i > 0; --i) {
            if (transaction2.sequenceNum > transaction3.sequenceNum) {
                transaction3 = transaction2;
            }
            if (transaction2 == this) {
                if (transaction3 == this) {
                    return true;
                }
                Transaction transaction5 = transaction3.blockingTransaction;
                if (transaction5 != null) {
                    transaction3.setStatus(4);
                    transaction5.notifyAllWaitingTransactions();
                    return false;
                }
            }
            transaction4 = transaction2;
        }
        return false;
    }

    private void tryThrowDeadLockException(boolean bl) {
        Transaction transaction;
        BitSet bitSet = new BitSet();
        StringBuilder stringBuilder = new StringBuilder(String.format("Transaction %d has been chosen as a deadlock victim. Details:%n", this.transactionId));
        Transaction transaction2 = this;
        while (!bitSet.get(transaction2.transactionId) && (transaction = transaction2.blockingTransaction) != null) {
            bitSet.set(transaction2.transactionId);
            stringBuilder.append(String.format("Transaction %d attempts to update map <%s> entry with key <%s> modified by transaction %s%n", transaction2.transactionId, transaction2.blockingMapName, transaction2.blockingKey, transaction2.blockingTransaction));
            if (transaction == this) {
                bl = true;
            }
            transaction2 = transaction;
        }
        if (bl) {
            throw DataUtils.newMVStoreException(105, "{0}", stringBuilder.toString());
        }
    }

    private synchronized boolean waitForThisToEnd(int n, Transaction transaction) {
        long l;
        int n2;
        long l2 = System.nanoTime();
        this.notificationRequested = true;
        while ((n2 = Transaction.getStatus(l = this.statusAndLogId.get())) != 0 && n2 != 5 && !Transaction.hasRollback(l)) {
            int n3;
            if (transaction.getStatus() != 1) {
                transaction.tryThrowDeadLockException(true);
            }
            if ((n3 = n - (int)((System.nanoTime() - l2) / 1000000L)) <= 0) {
                return false;
            }
            try {
                this.wait(n3);
            }
            catch (InterruptedException interruptedException) {
                return false;
            }
        }
        return true;
    }

    public <K, V> void removeMap(TransactionMap<K, V> transactionMap) {
        this.store.removeMap(transactionMap);
    }

    public String toString() {
        return this.transactionId + "(" + this.sequenceNum + ") " + this.stateToString();
    }

    private String stateToString() {
        return Transaction.stateToString(this.statusAndLogId.get());
    }

    private static String stateToString(long l) {
        return Transaction.getStatusName(Transaction.getStatus(l)) + (Transaction.hasRollback(l) ? "<" : "") + " " + Transaction.getLogId(l);
    }

    private static int getStatus(long l) {
        return (int)(l >>> 41) & 0xF;
    }

    private static long getLogId(long l) {
        return l & 0x1FFFFFFFFFFL;
    }

    private static boolean hasRollback(long l) {
        return (l & 0x200000000000L) != 0L;
    }

    private static boolean hasChanges(long l) {
        return Transaction.getLogId(l) != 0L;
    }

    private static long composeState(int n, long l, boolean bl) {
        assert (l < 0x10000000000L) : l;
        assert ((n & 0xFFFFFFF0) == 0) : n;
        if (bl) {
            n |= 0x10;
        }
        return (long)n << 41 | l;
    }

    private static String getStatusName(int n) {
        return n >= 0 && n < STATUS_NAMES.length ? STATUS_NAMES[n] : "UNKNOWN_STATUS_" + n;
    }
}

