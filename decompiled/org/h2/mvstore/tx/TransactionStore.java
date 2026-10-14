/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.tx;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import org.h2.engine.IsolationLevel;
import org.h2.mvstore.Cursor;
import org.h2.mvstore.DataUtils;
import org.h2.mvstore.MVMap;
import org.h2.mvstore.MVStore;
import org.h2.mvstore.RootReference;
import org.h2.mvstore.rtree.MVRTreeMap;
import org.h2.mvstore.rtree.SpatialDataType;
import org.h2.mvstore.tx.CommitDecisionMaker;
import org.h2.mvstore.tx.Record;
import org.h2.mvstore.tx.RollbackDecisionMaker;
import org.h2.mvstore.tx.Transaction;
import org.h2.mvstore.tx.TransactionMap;
import org.h2.mvstore.tx.VersionedBitSet;
import org.h2.mvstore.tx.VersionedValueType;
import org.h2.mvstore.type.DataType;
import org.h2.mvstore.type.LongDataType;
import org.h2.mvstore.type.MetaType;
import org.h2.mvstore.type.ObjectDataType;
import org.h2.mvstore.type.StringDataType;
import org.h2.util.StringUtils;
import org.h2.value.VersionedValue;

public class TransactionStore {
    final MVStore store;
    final int timeoutMillis;
    private final MVMap<Integer, Object[]> preparedTransactions;
    private final MVMap<String, DataType<?>> typeRegistry;
    final MVMap<Long, Record<?, ?>>[] undoLogs = new MVMap[65535];
    private final MVMap.Builder<Long, Record<?, ?>> undoLogBuilder;
    private final DataType<?> dataType;
    final AtomicReference<VersionedBitSet> openTransactions = new AtomicReference<VersionedBitSet>(new VersionedBitSet());
    final AtomicReference<BitSet> committingTransactions = new AtomicReference<BitSet>(new BitSet());
    private boolean init;
    private int maxTransactionId = 65535;
    private final AtomicReferenceArray<Transaction> transactions = new AtomicReferenceArray(65536);
    private static final String TYPE_REGISTRY_NAME = "_";
    public static final String UNDO_LOG_NAME_PREFIX = "undoLog";
    private static final char UNDO_LOG_COMMITTED = '-';
    private static final char UNDO_LOG_OPEN = '.';
    private static final int MAX_OPEN_TRANSACTIONS = 65535;
    private static final int LOG_ID_BITS = 40;
    private static final long LOG_ID_MASK = 0xFFFFFFFFFFL;
    private static final RollbackListener ROLLBACK_LISTENER_NONE = (mVMap, object, versionedValue, versionedValue2) -> {};

    private static String getUndoLogName(int n) {
        return n > 0 ? "undoLog." + n : "undoLog.";
    }

    public TransactionStore(MVStore mVStore) {
        this(mVStore, new ObjectDataType());
    }

    public TransactionStore(MVStore mVStore, DataType<?> dataType) {
        this(mVStore, new MetaType<Object>(null, mVStore.backgroundExceptionHandler), dataType, 0);
    }

    public TransactionStore(MVStore mVStore, MetaType<?> metaType, DataType<?> dataType, int n) {
        this.store = mVStore;
        this.dataType = dataType;
        this.timeoutMillis = n;
        this.typeRegistry = TransactionStore.openTypeRegistry(mVStore, metaType);
        this.preparedTransactions = mVStore.openMap("openTransactions", new MVMap.Builder());
        this.undoLogBuilder = this.createUndoLogBuilder();
    }

    MVMap.Builder<Long, Record<?, ?>> createUndoLogBuilder() {
        return ((MVMap.Builder)new MVMap.Builder().singleWriter().keyType((DataType)LongDataType.INSTANCE)).valueType((DataType)new Record.Type(this));
    }

    private static MVMap<String, DataType<?>> openTypeRegistry(MVStore mVStore, MetaType<?> metaType) {
        MVMap.BasicBuilder basicBuilder = ((MVMap.Builder)new MVMap.Builder().keyType((DataType)StringDataType.INSTANCE)).valueType(metaType);
        return mVStore.openMap(TYPE_REGISTRY_NAME, basicBuilder);
    }

    public void init() {
        this.init(ROLLBACK_LISTENER_NONE);
    }

    public void init(RollbackListener rollbackListener) {
        if (!this.init) {
            for (String string : this.store.getMapNames()) {
                if (!string.startsWith(UNDO_LOG_NAME_PREFIX)) continue;
                if (string.length() > UNDO_LOG_NAME_PREFIX.length()) {
                    boolean bl;
                    boolean bl2 = bl = string.charAt(UNDO_LOG_NAME_PREFIX.length()) == '-';
                    if (this.store.hasData(string)) {
                        int n = StringUtils.parseUInt31(string, UNDO_LOG_NAME_PREFIX.length() + 1, string.length());
                        VersionedBitSet versionedBitSet = this.openTransactions.get();
                        if (!versionedBitSet.get(n)) {
                            String string2;
                            int n2;
                            Object[] objectArray = this.preparedTransactions.get(n);
                            if (objectArray == null) {
                                n2 = 1;
                                string2 = null;
                            } else {
                                n2 = (Integer)objectArray[0];
                                string2 = (String)objectArray[1];
                            }
                            Object m4 = this.store.openMap(string, this.undoLogBuilder);
                            this.undoLogs[n] = m4;
                            Long l = (Long)((MVMap)m4).lastKey();
                            assert (l != null);
                            assert (TransactionStore.getTransactionId(l) == n);
                            long l2 = TransactionStore.getLogId(l) + 1L;
                            if (bl) {
                                this.store.renameMap((MVMap<?, ?>)m4, TransactionStore.getUndoLogName(n));
                                this.markUndoLogAsCommitted(n);
                            } else {
                                boolean bl3 = bl = l2 > 0xFFFFFFFFFFL;
                            }
                            if (bl) {
                                n2 = 3;
                                l = ((MVMap)m4).lowerKey((Long)l);
                                assert (l == null || TransactionStore.getTransactionId(l) == n);
                                l2 = l == null ? 0L : TransactionStore.getLogId(l) + 1L;
                            }
                            this.registerTransaction(n, n2, string2, l2, this.timeoutMillis, 0, IsolationLevel.READ_COMMITTED, rollbackListener);
                            continue;
                        }
                    }
                }
                if (this.store.isReadOnly()) continue;
                this.store.removeMap(string);
            }
            this.init = true;
        }
    }

    private void markUndoLogAsCommitted(int n) {
        this.addUndoLogRecord(n, 0xFFFFFFFFFFL, Record.COMMIT_MARKER);
    }

    public void endLeftoverTransactions() {
        List<Transaction> list = this.getOpenTransactions();
        for (Transaction transaction : list) {
            int n = transaction.getStatus();
            if (n == 3) {
                transaction.commit();
                continue;
            }
            if (n == 2) continue;
            transaction.rollback();
        }
    }

    int getMaxTransactionId() {
        return this.maxTransactionId;
    }

    public void setMaxTransactionId(int n) {
        DataUtils.checkArgument(n <= 65535, "Concurrent transactions limit is too high: {0}", n);
        this.maxTransactionId = n;
    }

    public boolean hasMap(String string) {
        return this.store.hasMap(string);
    }

    static long getOperationId(int n, long l) {
        DataUtils.checkArgument(n >= 0 && n < 0x1000000, "Transaction id out of range: {0}", n);
        DataUtils.checkArgument(l >= 0L && l <= 0xFFFFFFFFFFL, "Transaction log id out of range: {0}", l);
        return (long)n << 40 | l;
    }

    static int getTransactionId(long l) {
        return (int)(l >>> 40);
    }

    static long getLogId(long l) {
        return l & 0xFFFFFFFFFFL;
    }

    public List<Transaction> getOpenTransactions() {
        if (!this.init) {
            this.init();
        }
        ArrayList<Transaction> arrayList = new ArrayList<Transaction>();
        int n = 0;
        BitSet bitSet = this.openTransactions.get();
        while ((n = bitSet.nextSetBit(n + 1)) > 0) {
            Transaction transaction = this.getTransaction(n);
            if (transaction == null || transaction.getStatus() == 0) continue;
            arrayList.add(transaction);
        }
        return arrayList;
    }

    public synchronized void close() {
        this.store.commit();
    }

    public Transaction begin() {
        return this.begin(ROLLBACK_LISTENER_NONE, this.timeoutMillis, 0, IsolationLevel.READ_COMMITTED);
    }

    public Transaction begin(RollbackListener rollbackListener, int n, int n2, IsolationLevel isolationLevel) {
        Transaction transaction = this.registerTransaction(0, 1, null, 0L, n, n2, isolationLevel, rollbackListener);
        return transaction;
    }

    private Transaction registerTransaction(int n, int n2, String string, long l, int n3, int n4, IsolationLevel isolationLevel, RollbackListener rollbackListener) {
        long l2;
        int n5;
        Object object;
        Object object2;
        boolean bl;
        do {
            object2 = this.openTransactions.get();
            if (n == 0) {
                n5 = ((BitSet)object2).nextClearBit(1);
            } else {
                n5 = n;
                assert (!((BitSet)object2).get(n5));
            }
            if (n5 > this.maxTransactionId) {
                throw DataUtils.newMVStoreException(102, "There are {0} open transactions", n5 - 1);
            }
            object = ((VersionedBitSet)object2).clone();
            ((BitSet)object).set(n5);
            l2 = ((VersionedBitSet)object).getVersion() + 1L;
            ((VersionedBitSet)object).setVersion(l2);
        } while (!(bl = this.openTransactions.compareAndSet((VersionedBitSet)object2, (VersionedBitSet)object)));
        object2 = new Transaction(this, n5, l2, n2, string, l, n3, n4, isolationLevel, rollbackListener);
        assert (this.transactions.get(n5) == null);
        this.transactions.set(n5, (Transaction)object2);
        if (this.undoLogs[n5] == null) {
            object = TransactionStore.getUndoLogName(n5);
            Object m4 = this.store.openMap((String)object, this.undoLogBuilder);
            this.undoLogs[n5] = m4;
        }
        return object2;
    }

    void storeTransaction(Transaction transaction) {
        if (transaction.getStatus() == 2 || transaction.getName() != null) {
            Object[] objectArray = new Object[]{transaction.getStatus(), transaction.getName()};
            this.preparedTransactions.put(transaction.getId(), objectArray);
            transaction.wasStored = true;
        }
    }

    long addUndoLogRecord(int n, long l, Record<?, ?> record) {
        MVMap<Long, Record<?, ?>> mVMap = this.undoLogs[n];
        long l2 = TransactionStore.getOperationId(n, l);
        if (l == 0L && !mVMap.isEmpty()) {
            throw DataUtils.newMVStoreException(102, "An old transaction with the same id is still open: {0}", n);
        }
        mVMap.append(l2, record);
        return l2;
    }

    void removeUndoLogRecord(int n) {
        this.undoLogs[n].trimLast();
    }

    void removeMap(TransactionMap<?, ?> transactionMap) {
        this.store.removeMap(transactionMap.map);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void commit(Transaction transaction, boolean bl) {
        if (!this.store.isClosed()) {
            Cursor<Object, Record<?, ?>> cursor;
            int n = transaction.transactionId;
            MVMap<Long, Record<?, ?>> mVMap = this.undoLogs[n];
            if (bl) {
                this.removeUndoLogRecord(n);
                cursor = mVMap.cursor(null);
            } else {
                cursor = mVMap.cursor(null);
                this.markUndoLogAsCommitted(n);
            }
            this.flipCommittingTransactionsBit(n, true);
            CommitDecisionMaker commitDecisionMaker = new CommitDecisionMaker();
            try {
                while (cursor.hasNext()) {
                    Long l = cursor.next();
                    Record<?, ?> record = cursor.getValue();
                    int n2 = record.mapId;
                    MVMap mVMap2 = this.openMap(n2);
                    if (mVMap2 == null || mVMap2.isClosed()) continue;
                    Object k = record.key;
                    commitDecisionMaker.setUndoKey(l);
                    mVMap2.operate(k, null, commitDecisionMaker);
                }
            }
            finally {
                try {
                    mVMap.clear();
                }
                finally {
                    this.flipCommittingTransactionsBit(n, false);
                }
            }
        }
    }

    private void flipCommittingTransactionsBit(int n, boolean bl) {
        BitSet bitSet;
        BitSet bitSet2;
        boolean bl2;
        do {
            bitSet2 = this.committingTransactions.get();
            assert (bitSet2.get(n) != bl) : bl ? "Double commit" : "Mysterious bit's disappearance";
            bitSet = (BitSet)bitSet2.clone();
            bitSet.set(n, bl);
        } while (!(bl2 = this.committingTransactions.compareAndSet(bitSet2, bitSet)));
    }

    <K, V> MVMap<K, VersionedValue<V>> openVersionedMap(String string, DataType<K> dataType, DataType<V> dataType2) {
        VersionedValueType versionedValueType = dataType2 == null ? null : new VersionedValueType(dataType2);
        return this.openMap(string, dataType, versionedValueType);
    }

    public <K, V> MVMap<K, V> openMap(String string, DataType<K> dataType, DataType<V> dataType2) {
        return this.store.openMap(string, ((MVMap.Builder)new TxMapBuilder(this.typeRegistry, this.dataType).keyType(dataType)).valueType(dataType2));
    }

    <K, V> MVMap<K, VersionedValue<V>> openMap(int n) {
        MVMap mVMap = this.store.getMap(n);
        if (mVMap == null) {
            String string = this.store.getMapName(n);
            if (string == null) {
                return null;
            }
            TxMapBuilder txMapBuilder = new TxMapBuilder(this.typeRegistry, this.dataType);
            mVMap = this.store.openMap(n, txMapBuilder);
        }
        return mVMap;
    }

    <K, V> MVMap<K, VersionedValue<V>> getMap(int n) {
        MVMap mVMap = this.store.getMap(n);
        if (mVMap == null && !this.init) {
            mVMap = this.openMap(n);
        }
        assert (mVMap != null) : "map with id " + n + " is missing" + (this.init ? "" : " during initialization");
        return mVMap;
    }

    void endTransaction(Transaction transaction, boolean bl) {
        VersionedBitSet versionedBitSet;
        VersionedBitSet versionedBitSet2;
        boolean bl2;
        transaction.closeIt();
        int n = transaction.transactionId;
        this.transactions.set(n, null);
        do {
            versionedBitSet2 = this.openTransactions.get();
            assert (versionedBitSet2.get(n));
            versionedBitSet = versionedBitSet2.clone();
            versionedBitSet.clear(n);
        } while (!(bl2 = this.openTransactions.compareAndSet(versionedBitSet2, versionedBitSet)));
        if (bl) {
            boolean bl3 = transaction.wasStored;
            if (bl3 && !this.preparedTransactions.isClosed()) {
                this.preparedTransactions.remove(n);
            }
            if (this.store.isVersioningRequired()) {
                int n2;
                int n3;
                if (bl3 || this.store.getAutoCommitDelay() == 0) {
                    this.store.commit();
                } else if (this.isUndoEmpty() && (n3 = this.store.getUnsavedMemory()) * 4 > (n2 = this.store.getAutoCommitMemory()) * 3) {
                    this.store.tryCommit();
                }
            }
        }
    }

    RootReference<Long, Record<?, ?>>[] collectUndoLogRootReferences() {
        BitSet bitSet = this.openTransactions.get();
        RootReference[] rootReferenceArray = new RootReference[bitSet.length()];
        int n = bitSet.nextSetBit(0);
        while (n >= 0) {
            MVMap<Long, Record<?, ?>> mVMap = this.undoLogs[n];
            if (mVMap != null) {
                RootReference<Long, Record<?, ?>> rootReference = mVMap.getRoot();
                if (rootReference.needFlush()) {
                    return null;
                }
                rootReferenceArray[n] = rootReference;
            }
            n = bitSet.nextSetBit(n + 1);
        }
        return rootReferenceArray;
    }

    static long calculateUndoLogsTotalSize(RootReference<Long, Record<?, ?>>[] rootReferenceArray) {
        long l = 0L;
        for (RootReference<Long, Record<?, ?>> rootReference : rootReferenceArray) {
            if (rootReference == null) continue;
            l += rootReference.getTotalCount();
        }
        return l;
    }

    private boolean isUndoEmpty() {
        BitSet bitSet = this.openTransactions.get();
        int n = bitSet.nextSetBit(0);
        while (n >= 0) {
            MVMap<Long, Record<?, ?>> mVMap = this.undoLogs[n];
            if (mVMap != null && !mVMap.isEmpty()) {
                return false;
            }
            n = bitSet.nextSetBit(n + 1);
        }
        return true;
    }

    Transaction getTransaction(int n) {
        return this.transactions.get(n);
    }

    void rollbackTo(Transaction transaction, long l, long l2) {
        int n = transaction.getId();
        MVMap<Long, Record<?, ?>> mVMap = this.undoLogs[n];
        RollbackDecisionMaker rollbackDecisionMaker = new RollbackDecisionMaker(this, n, l2, transaction.listener);
        for (long i = l - 1L; i >= l2; --i) {
            Long l3 = TransactionStore.getOperationId(n, i);
            mVMap.operate(l3, null, rollbackDecisionMaker);
            rollbackDecisionMaker.reset();
        }
    }

    Iterator<Change> getChanges(final Transaction transaction, final long l, final long l2) {
        final MVMap<Long, Record<?, ?>> mVMap = this.undoLogs[transaction.getId()];
        return new Iterator<Change>(){
            private long logId;
            private Change current;
            {
                this.logId = l - 1L;
            }

            private void fetchNext() {
                int n = transaction.getId();
                while (this.logId >= l2) {
                    Long l3 = TransactionStore.getOperationId(n, this.logId);
                    Record record = (Record)mVMap.get(l3);
                    --this.logId;
                    if (record == null) {
                        if ((l3 = mVMap.floorKey(l3)) == null || TransactionStore.getTransactionId(l3) != n) break;
                        this.logId = TransactionStore.getLogId(l3);
                        continue;
                    }
                    int n2 = record.mapId;
                    MVMap mVMap2 = TransactionStore.this.openMap(n2);
                    if (mVMap2 == null) continue;
                    VersionedValue versionedValue = record.oldValue;
                    this.current = new Change(mVMap2.getName(), record.key, versionedValue == null ? null : versionedValue.getCurrentValue());
                    return;
                }
                this.current = null;
            }

            @Override
            public boolean hasNext() {
                if (this.current == null) {
                    this.fetchNext();
                }
                return this.current != null;
            }

            @Override
            public Change next() {
                if (!this.hasNext()) {
                    throw DataUtils.newUnsupportedOperationException("no data");
                }
                Change change = this.current;
                this.current = null;
                return change;
            }
        };
    }

    private static final class TxMapBuilder<K, V>
    extends MVMap.Builder<K, V> {
        private final MVMap<String, DataType<?>> typeRegistry;
        private final DataType defaultDataType;

        TxMapBuilder(MVMap<String, DataType<?>> mVMap, DataType<?> dataType) {
            this.typeRegistry = mVMap;
            this.defaultDataType = dataType;
        }

        private void registerDataType(DataType<?> dataType) {
            String string = TxMapBuilder.getDataTypeRegistrationKey(dataType);
            DataType<?> dataType2 = this.typeRegistry.putIfAbsent(string, dataType);
            if (dataType2 != null) {
                // empty if block
            }
        }

        static String getDataTypeRegistrationKey(DataType<?> dataType) {
            return Integer.toHexString(Objects.hashCode(dataType));
        }

        @Override
        public MVMap<K, V> create(MVStore mVStore, Map<String, Object> map) {
            DataType<?> dataType;
            DataType<Object> dataType2 = this.getKeyType();
            if (dataType2 == null) {
                dataType = (String)map.remove("key");
                if (dataType != null) {
                    dataType2 = this.typeRegistry.get(dataType);
                    if (dataType2 == null) {
                        throw DataUtils.newMVStoreException(106, "Data type with hash {0} can not be found", dataType);
                    }
                    this.setKeyType(dataType2);
                }
            } else {
                this.registerDataType(dataType2);
            }
            dataType = this.getValueType();
            if (dataType == null) {
                String string = (String)map.remove("val");
                if (string != null) {
                    dataType = this.typeRegistry.get(string);
                    if (dataType == null) {
                        throw DataUtils.newMVStoreException(106, "Data type with hash {0} can not be found", string);
                    }
                    this.setValueType(dataType);
                }
            } else {
                this.registerDataType(dataType);
            }
            if (this.getKeyType() == null) {
                this.setKeyType(this.defaultDataType);
                this.registerDataType(this.getKeyType());
            }
            if (this.getValueType() == null) {
                this.setValueType(new VersionedValueType(this.defaultDataType));
                this.registerDataType(this.getValueType());
            }
            map.put("store", mVStore);
            map.put("key", this.getKeyType());
            map.put("val", this.getValueType());
            return this.create(map);
        }

        @Override
        protected MVMap<K, V> create(Map<String, Object> map) {
            if ("rtree".equals(map.get("type"))) {
                MVRTreeMap mVRTreeMap = new MVRTreeMap(map, (SpatialDataType)this.getKeyType(), this.getValueType());
                return mVRTreeMap;
            }
            return new TMVMap(map, this.getKeyType(), this.getValueType());
        }

        private static final class TMVMap<K, V>
        extends MVMap<K, V> {
            private final String type;

            TMVMap(Map<String, Object> map, DataType<K> dataType, DataType<V> dataType2) {
                super(map, dataType, dataType2);
                this.type = (String)map.get("type");
            }

            private TMVMap(MVMap<K, V> mVMap) {
                super(mVMap);
                this.type = mVMap.getType();
            }

            @Override
            protected MVMap<K, V> cloneIt() {
                return new TMVMap<K, V>(this);
            }

            @Override
            public String getType() {
                return this.type;
            }

            @Override
            protected String asString(String string) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(super.asString(string));
                DataUtils.appendMap(stringBuilder, "key", TxMapBuilder.getDataTypeRegistrationKey(this.getKeyType()));
                DataUtils.appendMap(stringBuilder, "val", TxMapBuilder.getDataTypeRegistrationKey(this.getValueType()));
                return stringBuilder.toString();
            }
        }
    }

    public static interface RollbackListener {
        public void onRollback(MVMap<Object, VersionedValue<Object>> var1, Object var2, VersionedValue<Object> var3, VersionedValue<Object> var4);
    }

    public static class Change {
        public final String mapName;
        public final Object key;
        public final Object value;

        public Change(String string, Object object, Object object2) {
            this.mapName = string;
            this.key = object;
            this.value = object2;
        }
    }
}

