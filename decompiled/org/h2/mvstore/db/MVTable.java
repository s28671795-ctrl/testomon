/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.db;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.h2.command.ddl.CreateTableData;
import org.h2.constraint.Constraint;
import org.h2.constraint.ConstraintReferential;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.engine.SysProperties;
import org.h2.index.Cursor;
import org.h2.index.Index;
import org.h2.index.IndexType;
import org.h2.message.DbException;
import org.h2.message.Trace;
import org.h2.mode.DefaultNullOrdering;
import org.h2.mvstore.MVStoreException;
import org.h2.mvstore.db.MVDelegateIndex;
import org.h2.mvstore.db.MVIndex;
import org.h2.mvstore.db.MVPrimaryIndex;
import org.h2.mvstore.db.MVSecondaryIndex;
import org.h2.mvstore.db.MVSpatialIndex;
import org.h2.mvstore.db.Store;
import org.h2.mvstore.tx.Transaction;
import org.h2.mvstore.tx.TransactionStore;
import org.h2.result.Row;
import org.h2.result.SearchRow;
import org.h2.table.Column;
import org.h2.table.IndexColumn;
import org.h2.table.Table;
import org.h2.table.TableBase;
import org.h2.table.TableType;
import org.h2.util.DebuggingThreadLocal;
import org.h2.util.Utils;
import org.h2.value.DataType;
import org.h2.value.TypeInfo;

public class MVTable
extends TableBase {
    public static final DebuggingThreadLocal<String> WAITING_FOR_LOCK;
    public static final DebuggingThreadLocal<ArrayList<String>> EXCLUSIVE_LOCKS;
    public static final DebuggingThreadLocal<ArrayList<String>> SHARED_LOCKS;
    private static final String NO_EXTRA_INFO = "";
    private final boolean containsLargeObject;
    private volatile SessionLocal lockExclusiveSession;
    private final ConcurrentHashMap<SessionLocal, SessionLocal> lockSharedSessions = new ConcurrentHashMap();
    private Column rowIdColumn;
    private final MVPrimaryIndex primaryIndex;
    private final ArrayList<Index> indexes = Utils.newSmallArrayList();
    private final AtomicLong lastModificationId = new AtomicLong();
    private final ArrayDeque<SessionLocal> waitingSessions = new ArrayDeque();
    private final Trace traceLock;
    private final AtomicInteger changesUntilAnalyze;
    private int nextAnalyze;
    private final Store store;
    private final TransactionStore transactionStore;

    public MVTable(CreateTableData createTableData, Store store) {
        super(createTableData);
        this.isHidden = createTableData.isHidden;
        boolean bl = false;
        for (Column column : this.getColumns()) {
            if (!DataType.isLargeObject(column.getType().getValueType())) continue;
            bl = true;
            break;
        }
        this.containsLargeObject = bl;
        this.nextAnalyze = this.database.getSettings().analyzeAuto;
        this.changesUntilAnalyze = this.nextAnalyze <= 0 ? null : new AtomicInteger(this.nextAnalyze);
        this.store = store;
        this.transactionStore = store.getTransactionStore();
        this.traceLock = this.database.getTrace(7);
        this.primaryIndex = new MVPrimaryIndex(this.database, this, this.getId(), IndexColumn.wrap(this.getColumns()), IndexType.createScan(true));
        this.indexes.add(this.primaryIndex);
    }

    public String getMapName() {
        return this.primaryIndex.getMapName();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean lock(SessionLocal sessionLocal, int n) {
        if (this.database.getLockMode() == 0) {
            sessionLocal.registerTableAsUpdated(this);
            return false;
        }
        if (n == 0 && this.lockExclusiveSession == null) {
            return false;
        }
        if (this.lockExclusiveSession == sessionLocal) {
            return true;
        }
        if (n != 2 && this.lockSharedSessions.containsKey(sessionLocal)) {
            return true;
        }
        MVTable mVTable = this;
        synchronized (mVTable) {
            if (n != 2 && this.lockSharedSessions.containsKey(sessionLocal)) {
                return true;
            }
            sessionLocal.setWaitForLock(this, Thread.currentThread());
            if (SysProperties.THREAD_DEADLOCK_DETECTOR) {
                WAITING_FOR_LOCK.set(this.getName());
            }
            this.waitingSessions.addLast(sessionLocal);
            try {
                this.doLock1(sessionLocal, n);
            }
            finally {
                sessionLocal.setWaitForLock(null, null);
                if (SysProperties.THREAD_DEADLOCK_DETECTOR) {
                    WAITING_FOR_LOCK.remove();
                }
                this.waitingSessions.remove(sessionLocal);
            }
        }
        return false;
    }

    private void doLock1(SessionLocal sessionLocal, int n) {
        this.traceLock(sessionLocal, n, TraceLockEvent.TRACE_LOCK_REQUESTING_FOR, NO_EXTRA_INFO);
        long l = 0L;
        boolean bl = false;
        while (this.waitingSessions.getFirst() != sessionLocal || this.lockExclusiveSession != null || !this.doLock2(sessionLocal, n)) {
            if (bl) {
                ArrayList<SessionLocal> arrayList = this.checkDeadlock(sessionLocal, null, null);
                if (arrayList != null) {
                    throw DbException.get(40001, MVTable.getDeadlockDetails(arrayList, n));
                }
            } else {
                bl = true;
            }
            long l2 = System.nanoTime();
            if (l == 0L) {
                l = Utils.nanoTimePlusMillis(l2, sessionLocal.getLockTimeout());
            } else if (l2 - l >= 0L) {
                this.traceLock(sessionLocal, n, TraceLockEvent.TRACE_LOCK_TIMEOUT_AFTER, Integer.toString(sessionLocal.getLockTimeout()));
                throw DbException.get(50200, this.getName());
            }
            try {
                this.traceLock(sessionLocal, n, TraceLockEvent.TRACE_LOCK_WAITING_FOR, NO_EXTRA_INFO);
                long l3 = Math.min(100L, (l - l2) / 1000000L);
                if (l3 == 0L) {
                    l3 = 1L;
                }
                this.wait(l3);
            }
            catch (InterruptedException interruptedException) {
            }
        }
        return;
    }

    private boolean doLock2(SessionLocal sessionLocal, int n) {
        switch (n) {
            case 2: {
                int n2 = this.lockSharedSessions.size();
                if (n2 == 0) {
                    this.traceLock(sessionLocal, n, TraceLockEvent.TRACE_LOCK_ADDED_FOR, NO_EXTRA_INFO);
                    sessionLocal.registerTableAsLocked(this);
                } else if (n2 == 1 && this.lockSharedSessions.containsKey(sessionLocal)) {
                    this.traceLock(sessionLocal, n, TraceLockEvent.TRACE_LOCK_ADD_UPGRADED_FOR, NO_EXTRA_INFO);
                } else {
                    return false;
                }
                this.lockExclusiveSession = sessionLocal;
                if (!SysProperties.THREAD_DEADLOCK_DETECTOR) break;
                this.addLockToDebugList(EXCLUSIVE_LOCKS);
                break;
            }
            case 1: {
                if (this.lockSharedSessions.putIfAbsent(sessionLocal, sessionLocal) != null) break;
                this.traceLock(sessionLocal, n, TraceLockEvent.TRACE_LOCK_OK, NO_EXTRA_INFO);
                sessionLocal.registerTableAsLocked(this);
                if (!SysProperties.THREAD_DEADLOCK_DETECTOR) break;
                this.addLockToDebugList(SHARED_LOCKS);
            }
        }
        return true;
    }

    private void addLockToDebugList(DebuggingThreadLocal<ArrayList<String>> debuggingThreadLocal) {
        ArrayList<String> arrayList = debuggingThreadLocal.get();
        if (arrayList == null) {
            arrayList = new ArrayList();
            debuggingThreadLocal.set(arrayList);
        }
        arrayList.add(this.getName());
    }

    private void traceLock(SessionLocal sessionLocal, int n, TraceLockEvent traceLockEvent, String string) {
        if (this.traceLock.isDebugEnabled()) {
            this.traceLock.debug("{0} {1} {2} {3} {4}", sessionLocal.getId(), MVTable.lockTypeToString(n), traceLockEvent.getEventText(), this.getName(), string);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void unlock(SessionLocal sessionLocal) {
        if (this.database != null) {
            Object object;
            int n;
            if (this.lockExclusiveSession == sessionLocal) {
                n = 2;
                this.lockSharedSessions.remove(sessionLocal);
                this.lockExclusiveSession = null;
                if (SysProperties.THREAD_DEADLOCK_DETECTOR && (object = EXCLUSIVE_LOCKS.get()) != null) {
                    ((ArrayList)object).remove(this.getName());
                }
            } else {
                int n2 = n = this.lockSharedSessions.remove(sessionLocal) != null ? 1 : 0;
                if (SysProperties.THREAD_DEADLOCK_DETECTOR && (object = SHARED_LOCKS.get()) != null) {
                    ((ArrayList)object).remove(this.getName());
                }
            }
            this.traceLock(sessionLocal, n, TraceLockEvent.TRACE_LOCK_UNLOCK, NO_EXTRA_INFO);
            if (n != 0 && !this.waitingSessions.isEmpty()) {
                object = this;
                synchronized (object) {
                    this.notifyAll();
                }
            }
        }
    }

    @Override
    public void close(SessionLocal sessionLocal) {
    }

    @Override
    public Row getRow(SessionLocal sessionLocal, long l) {
        return this.primaryIndex.getRow(sessionLocal, l);
    }

    @Override
    public Index addIndex(SessionLocal sessionLocal, String string, int n, IndexColumn[] indexColumnArray, int n2, IndexType indexType, boolean bl, String string2) {
        MVIndex mVIndex;
        int n3;
        boolean bl2;
        indexColumnArray = MVTable.prepareColumns(this.database, indexColumnArray, indexType);
        boolean bl3 = bl2 = this.isTemporary() && !this.isGlobalTemporary();
        if (!bl2) {
            this.database.lockMeta(sessionLocal);
        }
        int n4 = n3 = this.primaryIndex.getMainIndexColumn() != -1 ? -1 : MVTable.getMainIndexColumn(indexType, indexColumnArray);
        if (this.database.isStarting()) {
            if (this.transactionStore.hasMap("index." + n)) {
                n3 = -1;
            }
        } else if (this.primaryIndex.getRowCountMax() != 0L) {
            n3 = -1;
        }
        if (n3 != -1) {
            this.primaryIndex.setMainIndexColumn(n3);
            mVIndex = new MVDelegateIndex(this, n, string, this.primaryIndex, indexType);
        } else {
            mVIndex = indexType.isSpatial() ? new MVSpatialIndex(sessionLocal.getDatabase(), this, n, string, indexColumnArray, n2, indexType) : new MVSecondaryIndex(sessionLocal.getDatabase(), this, n, string, indexColumnArray, n2, indexType);
        }
        if (mVIndex.needRebuild()) {
            this.rebuildIndex(sessionLocal, mVIndex, string);
        }
        mVIndex.setTemporary(this.isTemporary());
        if (mVIndex.getCreateSQL() != null) {
            mVIndex.setComment(string2);
            if (bl2) {
                sessionLocal.addLocalTempTableIndex(mVIndex);
            } else {
                this.database.addSchemaObject(sessionLocal, mVIndex);
            }
        }
        this.indexes.add(mVIndex);
        this.setModified();
        return mVIndex;
    }

    private void rebuildIndex(SessionLocal sessionLocal, MVIndex<?, ?> mVIndex, String string) {
        try {
            if (!sessionLocal.getDatabase().isPersistent() || mVIndex instanceof MVSpatialIndex) {
                this.rebuildIndexBuffered(sessionLocal, mVIndex);
            } else {
                this.rebuildIndexBlockMerge(sessionLocal, mVIndex);
            }
        }
        catch (DbException dbException) {
            this.getSchema().freeUniqueName(string);
            try {
                mVIndex.remove(sessionLocal);
            }
            catch (DbException dbException2) {
                this.trace.error(dbException2, "could not remove index");
                throw dbException2;
            }
            throw dbException;
        }
    }

    private void rebuildIndexBlockMerge(SessionLocal sessionLocal, MVIndex<?, ?> mVIndex) {
        Object object;
        long l;
        Index index = this.getScanIndex(sessionLocal);
        long l2 = l = index.getRowCount(sessionLocal);
        Cursor cursor = index.find(sessionLocal, null, null);
        long l3 = 0L;
        Store store = sessionLocal.getDatabase().getStore();
        int n = (int)Math.min(l2, (long)(this.database.getMaxMemoryRows() / 2));
        ArrayList<Row> arrayList = new ArrayList<Row>(n);
        String string = this.getName() + ':' + mVIndex.getName();
        ArrayList<String> arrayList2 = Utils.newSmallArrayList();
        while (cursor.next()) {
            object = cursor.get();
            arrayList.add((Row)object);
            this.database.setProgress(1, string, l3++, l2);
            if (arrayList.size() >= n) {
                MVTable.sortRows(arrayList, mVIndex);
                String string2 = store.nextTemporaryMapName();
                mVIndex.addRowsToBuffer(arrayList, string2);
                arrayList2.add(string2);
                arrayList.clear();
            }
            --l;
        }
        MVTable.sortRows(arrayList, mVIndex);
        if (!arrayList2.isEmpty()) {
            object = store.nextTemporaryMapName();
            mVIndex.addRowsToBuffer(arrayList, (String)object);
            arrayList2.add((String)object);
            arrayList.clear();
            mVIndex.addBufferedRows(arrayList2);
        } else {
            MVTable.addRowsToIndex(sessionLocal, arrayList, mVIndex);
        }
        if (l != 0L) {
            throw DbException.getInternalError("rowcount remaining=" + l + ' ' + this.getName());
        }
    }

    private void rebuildIndexBuffered(SessionLocal sessionLocal, Index index) {
        long l;
        Index index2 = this.getScanIndex(sessionLocal);
        long l2 = l = index2.getRowCount(sessionLocal);
        Cursor cursor = index2.find(sessionLocal, null, null);
        long l3 = 0L;
        int n = (int)Math.min(l2, (long)this.database.getMaxMemoryRows());
        ArrayList<Row> arrayList = new ArrayList<Row>(n);
        String string = this.getName() + ':' + index.getName();
        while (cursor.next()) {
            Row row = cursor.get();
            arrayList.add(row);
            this.database.setProgress(1, string, l3++, l2);
            if (arrayList.size() >= n) {
                MVTable.addRowsToIndex(sessionLocal, arrayList, index);
            }
            --l;
        }
        MVTable.addRowsToIndex(sessionLocal, arrayList, index);
        if (l != 0L) {
            throw DbException.getInternalError("rowcount remaining=" + l + ' ' + this.getName());
        }
    }

    @Override
    public void removeRow(SessionLocal sessionLocal, Row row) {
        this.syncLastModificationIdWithDatabase();
        Transaction transaction = sessionLocal.getTransaction();
        long l = transaction.setSavepoint();
        try {
            for (int i = this.indexes.size() - 1; i >= 0; --i) {
                Index index = this.indexes.get(i);
                index.remove(sessionLocal, row);
            }
        }
        catch (Throwable throwable) {
            try {
                transaction.rollbackToSavepoint(l);
            }
            catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
            throw DbException.convert(throwable);
        }
        this.analyzeIfRequired(sessionLocal);
    }

    @Override
    public long truncate(SessionLocal sessionLocal) {
        this.syncLastModificationIdWithDatabase();
        long l = this.getRowCountApproximation(sessionLocal);
        for (int i = this.indexes.size() - 1; i >= 0; --i) {
            Index index = this.indexes.get(i);
            index.truncate(sessionLocal);
        }
        if (this.changesUntilAnalyze != null) {
            this.changesUntilAnalyze.set(this.nextAnalyze);
        }
        return l;
    }

    @Override
    public void addRow(SessionLocal sessionLocal, Row row) {
        this.syncLastModificationIdWithDatabase();
        Transaction transaction = sessionLocal.getTransaction();
        long l = transaction.setSavepoint();
        try {
            for (Index index : this.indexes) {
                index.add(sessionLocal, row);
            }
        }
        catch (Throwable throwable) {
            try {
                transaction.rollbackToSavepoint(l);
            }
            catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
            throw DbException.convert(throwable);
        }
        this.analyzeIfRequired(sessionLocal);
    }

    @Override
    public void updateRow(SessionLocal sessionLocal, Row row, Row row2) {
        row2.setKey(row.getKey());
        this.syncLastModificationIdWithDatabase();
        Transaction transaction = sessionLocal.getTransaction();
        long l = transaction.setSavepoint();
        try {
            for (Index index : this.indexes) {
                index.update(sessionLocal, row, row2);
            }
        }
        catch (Throwable throwable) {
            try {
                transaction.rollbackToSavepoint(l);
            }
            catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
            throw DbException.convert(throwable);
        }
        this.analyzeIfRequired(sessionLocal);
    }

    @Override
    public Row lockRow(SessionLocal sessionLocal, Row row, int n) {
        Row row2 = this.primaryIndex.lockRow(sessionLocal, row, n);
        if (row2 == null || !row.hasSharedData(row2)) {
            this.syncLastModificationIdWithDatabase();
        }
        return row2;
    }

    private void analyzeIfRequired(SessionLocal sessionLocal) {
        if (this.changesUntilAnalyze != null && this.changesUntilAnalyze.decrementAndGet() == 0) {
            if (this.nextAnalyze <= 0x3FFFFFFF) {
                this.nextAnalyze *= 2;
            }
            this.changesUntilAnalyze.set(this.nextAnalyze);
            sessionLocal.markTableForAnalyze(this);
        }
    }

    @Override
    public Index getScanIndex(SessionLocal sessionLocal) {
        return this.primaryIndex;
    }

    @Override
    public ArrayList<Index> getIndexes() {
        return this.indexes;
    }

    @Override
    public long getMaxDataModificationId() {
        return this.lastModificationId.get();
    }

    @Override
    public void removeChildrenAndResources(SessionLocal sessionLocal) {
        if (this.containsLargeObject) {
            this.truncate(sessionLocal);
            this.database.getLobStorage().removeAllForTable(this.getId());
            this.database.lockMeta(sessionLocal);
        }
        this.database.getStore().removeTable(this);
        super.removeChildrenAndResources(sessionLocal);
        while (this.indexes.size() > 1) {
            Index index = this.indexes.get(1);
            index.remove(sessionLocal);
            if (index.getName() != null) {
                this.database.removeSchemaObject(sessionLocal, index);
            }
            this.indexes.remove(index);
        }
        this.primaryIndex.remove(sessionLocal);
        this.indexes.clear();
        this.close(sessionLocal);
        this.invalidate();
    }

    @Override
    public long getRowCount(SessionLocal sessionLocal) {
        return this.primaryIndex.getRowCount(sessionLocal);
    }

    @Override
    public long getRowCountApproximation(SessionLocal sessionLocal) {
        return this.primaryIndex.getRowCountApproximation(sessionLocal);
    }

    @Override
    public long getDiskSpaceUsed() {
        return this.primaryIndex.getDiskSpaceUsed();
    }

    Transaction getTransactionBegin() {
        return this.transactionStore.begin();
    }

    @Override
    public boolean isRowLockable() {
        return true;
    }

    public void commit() {
        if (this.database != null) {
            this.syncLastModificationIdWithDatabase();
        }
    }

    private void syncLastModificationIdWithDatabase() {
        long l;
        long l2 = this.database.getNextModificationDataId();
        while (l2 > (l = this.lastModificationId.get()) && !this.lastModificationId.compareAndSet(l, l2)) {
        }
    }

    DbException convertException(MVStoreException mVStoreException) {
        return this.convertException(mVStoreException, false);
    }

    DbException convertLockException(MVStoreException mVStoreException) {
        return this.convertException(mVStoreException, true);
    }

    private DbException convertException(MVStoreException mVStoreException, boolean bl) {
        int n = mVStoreException.getErrorCode();
        if (n == 101) {
            return DbException.get(bl ? 50200 : 90131, mVStoreException, this.getName());
        }
        if (n == 105) {
            return DbException.get(40001, mVStoreException, this.getName());
        }
        return this.store.convertMVStoreException(mVStoreException);
    }

    @Override
    public int getMainIndexColumn() {
        return this.primaryIndex.getMainIndexColumn();
    }

    private static void addRowsToIndex(SessionLocal sessionLocal, ArrayList<Row> arrayList, Index index) {
        MVTable.sortRows(arrayList, index);
        for (Row row : arrayList) {
            index.add(sessionLocal, row);
        }
        arrayList.clear();
    }

    private static String getDeadlockDetails(ArrayList<SessionLocal> arrayList, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        for (SessionLocal sessionLocal : arrayList) {
            Table table = sessionLocal.getWaitForLock();
            Thread thread2 = sessionLocal.getWaitForLockThread();
            stringBuilder.append("\nSession ").append(sessionLocal).append(" on thread ").append(thread2.getName()).append(" is waiting to lock ").append(table.toString()).append(" (").append(MVTable.lockTypeToString(n)).append(") while locking ");
            boolean bl = false;
            for (Table table2 : sessionLocal.getLocks()) {
                if (bl) {
                    stringBuilder.append(", ");
                }
                bl = true;
                stringBuilder.append(table2.toString());
                if (!(table2 instanceof MVTable)) continue;
                if (((MVTable)table2).lockExclusiveSession == sessionLocal) {
                    stringBuilder.append(" (exclusive)");
                    continue;
                }
                stringBuilder.append(" (shared)");
            }
            stringBuilder.append('.');
        }
        return stringBuilder.toString();
    }

    private static String lockTypeToString(int n) {
        return n == 0 ? "shared read" : (n == 1 ? "shared write" : "exclusive");
    }

    private static void sortRows(ArrayList<? extends SearchRow> arrayList, Index index) {
        arrayList.sort(index::compareRows);
    }

    @Override
    public boolean canDrop() {
        return true;
    }

    @Override
    public boolean canGetRowCount(SessionLocal sessionLocal) {
        return true;
    }

    @Override
    public boolean canTruncate() {
        ArrayList<Constraint> arrayList;
        if (this.getCheckForeignKeyConstraints() && this.database.getReferentialIntegrity() && (arrayList = this.getConstraints()) != null) {
            for (Constraint constraint : arrayList) {
                ConstraintReferential constraintReferential;
                if (constraint.getConstraintType() != Constraint.Type.REFERENTIAL || (constraintReferential = (ConstraintReferential)constraint).getRefTable() != this) continue;
                return false;
            }
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ArrayList<SessionLocal> checkDeadlock(SessionLocal sessionLocal, SessionLocal sessionLocal2, Set<SessionLocal> set) {
        Class<?> clazz = this.getClass();
        synchronized (clazz) {
            Object object2;
            if (sessionLocal2 == null) {
                sessionLocal2 = sessionLocal;
                set = new HashSet<SessionLocal>();
            } else {
                if (sessionLocal2 == sessionLocal) {
                    return new ArrayList<SessionLocal>(0);
                }
                if (set.contains(sessionLocal)) {
                    return null;
                }
            }
            set.add(sessionLocal);
            ArrayList<SessionLocal> arrayList = null;
            for (Object object2 : this.lockSharedSessions.keySet()) {
                Table table;
                if (object2 == sessionLocal || (table = ((SessionLocal)object2).getWaitForLock()) == null || (arrayList = table.checkDeadlock((SessionLocal)object2, sessionLocal2, set)) == null) continue;
                arrayList.add(sessionLocal);
                break;
            }
            SessionLocal sessionLocal3 = this.lockExclusiveSession;
            if (arrayList == null && sessionLocal3 != null && (object2 = sessionLocal3.getWaitForLock()) != null && (arrayList = ((Table)object2).checkDeadlock(sessionLocal3, sessionLocal2, set)) != null) {
                arrayList.add(sessionLocal);
            }
            return arrayList;
        }
    }

    @Override
    public void checkSupportAlter() {
    }

    public boolean getContainsLargeObject() {
        return this.containsLargeObject;
    }

    @Override
    public Column getRowIdColumn() {
        if (this.rowIdColumn == null) {
            this.rowIdColumn = new Column("_ROWID_", TypeInfo.TYPE_BIGINT, this, -1);
            this.rowIdColumn.setRowId(true);
            this.rowIdColumn.setNullable(false);
        }
        return this.rowIdColumn;
    }

    @Override
    public TableType getTableType() {
        return TableType.TABLE;
    }

    @Override
    public boolean isDeterministic() {
        return true;
    }

    @Override
    public boolean isLockedExclusively() {
        return this.lockExclusiveSession != null;
    }

    @Override
    public boolean isLockedExclusivelyBy(SessionLocal sessionLocal) {
        return this.lockExclusiveSession == sessionLocal;
    }

    @Override
    protected void invalidate() {
        super.invalidate();
        this.lockExclusiveSession = null;
    }

    @Override
    public String toString() {
        return this.getTraceSQL();
    }

    private static IndexColumn[] prepareColumns(Database database, IndexColumn[] indexColumnArray, IndexType indexType) {
        block7: {
            int n;
            block6: {
                if (!indexType.isPrimaryKey()) break block6;
                for (IndexColumn indexColumn : indexColumnArray) {
                    Column column = indexColumn.column;
                    if (!column.isNullable()) continue;
                    throw DbException.get(90023, column.getName());
                }
                for (IndexColumn indexColumn : indexColumnArray) {
                    indexColumn.column.setPrimaryKey(true);
                }
                break block7;
            }
            if (indexType.isSpatial()) break block7;
            int n2 = indexColumnArray.length;
            for (n = 0; n < n2 && (indexColumnArray[n].sortType & 6) != 0; ++n) {
            }
            if (n != n2) {
                indexColumnArray = (IndexColumn[])indexColumnArray.clone();
                DefaultNullOrdering defaultNullOrdering = database.getDefaultNullOrdering();
                while (n < n2) {
                    IndexColumn indexColumn = indexColumnArray[n];
                    int n3 = indexColumn.sortType;
                    int n4 = defaultNullOrdering.addExplicitNullOrdering(n3);
                    if (n4 != n3) {
                        IndexColumn indexColumn2 = new IndexColumn(indexColumn.columnName, n4);
                        indexColumn2.column = indexColumn.column;
                        indexColumnArray[n] = indexColumn2;
                    }
                    ++n;
                }
            }
        }
        return indexColumnArray;
    }

    static {
        if (SysProperties.THREAD_DEADLOCK_DETECTOR) {
            WAITING_FOR_LOCK = new DebuggingThreadLocal();
            EXCLUSIVE_LOCKS = new DebuggingThreadLocal();
            SHARED_LOCKS = new DebuggingThreadLocal();
        } else {
            WAITING_FOR_LOCK = null;
            EXCLUSIVE_LOCKS = null;
            SHARED_LOCKS = null;
        }
    }

    private static enum TraceLockEvent {
        TRACE_LOCK_OK("ok"),
        TRACE_LOCK_WAITING_FOR("waiting for"),
        TRACE_LOCK_REQUESTING_FOR("requesting for"),
        TRACE_LOCK_TIMEOUT_AFTER("timeout after "),
        TRACE_LOCK_UNLOCK("unlock"),
        TRACE_LOCK_ADDED_FOR("added for"),
        TRACE_LOCK_ADD_UPGRADED_FOR("add (upgraded) for ");

        private final String eventText;

        private TraceLockEvent(String string2) {
            this.eventText = string2;
        }

        public String getEventText() {
            return this.eventText;
        }
    }
}

