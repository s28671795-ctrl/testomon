/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.db;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.h2.command.query.AllColumnsForPlan;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.index.Cursor;
import org.h2.index.IndexType;
import org.h2.index.SingleRowCursor;
import org.h2.message.DbException;
import org.h2.mvstore.MVMap;
import org.h2.mvstore.MVStoreException;
import org.h2.mvstore.db.MVIndex;
import org.h2.mvstore.db.MVTable;
import org.h2.mvstore.db.RowDataType;
import org.h2.mvstore.tx.Transaction;
import org.h2.mvstore.tx.TransactionMap;
import org.h2.mvstore.type.LongDataType;
import org.h2.result.Row;
import org.h2.result.SearchRow;
import org.h2.result.SortOrder;
import org.h2.table.Column;
import org.h2.table.IndexColumn;
import org.h2.table.TableFilter;
import org.h2.value.Value;
import org.h2.value.ValueLob;
import org.h2.value.ValueNull;
import org.h2.value.VersionedValue;

public class MVPrimaryIndex
extends MVIndex<Long, SearchRow> {
    private final MVTable mvTable;
    private final String mapName;
    private final TransactionMap<Long, SearchRow> dataMap;
    private final AtomicLong lastKey = new AtomicLong();
    private int mainIndexColumn = -1;

    public MVPrimaryIndex(Database database, MVTable mVTable, int n, IndexColumn[] indexColumnArray, IndexType indexType) {
        super(mVTable, n, mVTable.getName() + "_DATA", indexColumnArray, 0, indexType);
        this.mvTable = mVTable;
        RowDataType rowDataType = mVTable.getRowFactory().getRowDataType();
        this.mapName = "table." + this.getId();
        Transaction transaction = this.mvTable.getTransactionBegin();
        this.dataMap = transaction.openMap(this.mapName, LongDataType.INSTANCE, rowDataType);
        this.dataMap.map.setVolatile(!mVTable.isPersistData() || !indexType.isPersistent());
        if (!database.isStarting()) {
            this.dataMap.clear();
        }
        transaction.commit();
        Long l = (Long)this.dataMap.map.lastKey();
        this.lastKey.set(l == null ? 0L : l);
    }

    @Override
    public String getCreateSQL() {
        return null;
    }

    @Override
    public String getPlanSQL() {
        return this.table.getSQL(new StringBuilder(), 3).append(".tableScan").toString();
    }

    public void setMainIndexColumn(int n) {
        this.mainIndexColumn = n;
    }

    public int getMainIndexColumn() {
        return this.mainIndexColumn;
    }

    @Override
    public void close(SessionLocal sessionLocal) {
    }

    @Override
    public void add(SessionLocal sessionLocal, Row row) {
        long l;
        Value value;
        if (this.mainIndexColumn == -1) {
            if (row.getKey() == 0L) {
                row.setKey(this.lastKey.incrementAndGet());
            }
        } else {
            long l2 = row.getValue(this.mainIndexColumn).getLong();
            row.setKey(l2);
        }
        if (this.mvTable.getContainsLargeObject()) {
            int n = row.getColumnCount();
            for (int i = 0; i < n; ++i) {
                Value value2 = row.getValue(i);
                if (!(value2 instanceof ValueLob)) continue;
                value = ((ValueLob)value2).copy(this.database, this.getId());
                sessionLocal.removeAtCommitStop((ValueLob)value);
                if (value2 == value) continue;
                row.setValue(i, value);
            }
        }
        TransactionMap<Long, SearchRow> transactionMap = this.getMap(sessionLocal);
        long l3 = row.getKey();
        try {
            value = (Row)transactionMap.putIfAbsent(l3, row);
            if (value != null) {
                int n = 90131;
                if (transactionMap.getImmediate(l3) != null || transactionMap.getFromSnapshot(l3) != null) {
                    n = 23505;
                }
                DbException dbException = DbException.get(n, this.getDuplicatePrimaryKeyMessage(this.mainIndexColumn).append(' ').append(value).toString());
                dbException.setSource(this);
                throw dbException;
            }
        }
        catch (MVStoreException mVStoreException) {
            throw this.mvTable.convertException(mVStoreException);
        }
        while (l3 > (l = this.lastKey.get()) && !this.lastKey.compareAndSet(l, l3)) {
        }
    }

    @Override
    public void remove(SessionLocal sessionLocal, Row row) {
        Object object;
        if (this.mvTable.getContainsLargeObject()) {
            int n = row.getColumnCount();
            for (int i = 0; i < n; ++i) {
                object = row.getValue(i);
                if (!(object instanceof ValueLob)) continue;
                sessionLocal.removeAtCommit((ValueLob)object);
            }
        }
        TransactionMap<Long, SearchRow> transactionMap = this.getMap(sessionLocal);
        try {
            Row row2 = (Row)transactionMap.remove(row.getKey());
            if (row2 == null) {
                object = new StringBuilder();
                this.getSQL((StringBuilder)object, 3).append(": ").append(row.getKey());
                throw DbException.get(90112, ((StringBuilder)object).toString());
            }
        }
        catch (MVStoreException mVStoreException) {
            throw this.mvTable.convertException(mVStoreException);
        }
    }

    @Override
    public void update(SessionLocal sessionLocal, Row row, Row row2) {
        Object object;
        long l;
        if (this.mainIndexColumn != -1) {
            l = row2.getValue(this.mainIndexColumn).getLong();
            row2.setKey(l);
        }
        l = row.getKey();
        assert (this.mainIndexColumn != -1 || l != 0L);
        assert (l == row2.getKey()) : l + " != " + row2.getKey();
        if (this.mvTable.getContainsLargeObject()) {
            int n = row.getColumnCount();
            for (int i = 0; i < n; ++i) {
                Value value;
                object = row.getValue(i);
                if (object == (value = row2.getValue(i))) continue;
                if (object instanceof ValueLob) {
                    sessionLocal.removeAtCommit((ValueLob)object);
                }
                if (!(value instanceof ValueLob)) continue;
                ValueLob valueLob = ((ValueLob)value).copy(this.database, this.getId());
                sessionLocal.removeAtCommitStop(valueLob);
                if (value == valueLob) continue;
                row2.setValue(i, valueLob);
            }
        }
        TransactionMap<Long, SearchRow> transactionMap = this.getMap(sessionLocal);
        try {
            Row row3 = (Row)transactionMap.put(l, row2);
            if (row3 == null) {
                object = new StringBuilder();
                this.getSQL((StringBuilder)object, 3).append(": ").append(l);
                throw DbException.get(90112, ((StringBuilder)object).toString());
            }
        }
        catch (MVStoreException mVStoreException) {
            throw this.mvTable.convertException(mVStoreException);
        }
        if (row2.getKey() > this.lastKey.get()) {
            this.lastKey.set(row2.getKey());
        }
    }

    Row lockRow(SessionLocal sessionLocal, Row row, int n) {
        TransactionMap<Long, SearchRow> transactionMap = this.getMap(sessionLocal);
        long l = row.getKey();
        return this.lockRow(transactionMap, l, n);
    }

    private Row lockRow(TransactionMap<Long, SearchRow> transactionMap, long l, int n) {
        try {
            return MVPrimaryIndex.setRowKey((Row)transactionMap.lock(l, n), l);
        }
        catch (MVStoreException mVStoreException) {
            throw this.mvTable.convertLockException(mVStoreException);
        }
    }

    @Override
    public Cursor find(SessionLocal sessionLocal, SearchRow searchRow, SearchRow searchRow2) {
        long l = this.extractPKFromRow(searchRow, Long.MIN_VALUE);
        long l2 = this.extractPKFromRow(searchRow2, Long.MAX_VALUE);
        return this.find(sessionLocal, l, l2);
    }

    private long extractPKFromRow(SearchRow searchRow, long l) {
        Value value;
        long l2 = searchRow == null ? l : (this.mainIndexColumn == -1 ? searchRow.getKey() : ((value = searchRow.getValue(this.mainIndexColumn)) == null ? searchRow.getKey() : (value == ValueNull.INSTANCE ? 0L : value.getLong())));
        return l2;
    }

    @Override
    public MVTable getTable() {
        return this.mvTable;
    }

    @Override
    public Row getRow(SessionLocal sessionLocal, long l) {
        TransactionMap<Long, SearchRow> transactionMap = this.getMap(sessionLocal);
        Row row = (Row)transactionMap.getFromSnapshot(l);
        if (row == null) {
            throw DbException.get(90143, this.getTraceSQL(), String.valueOf(l));
        }
        return MVPrimaryIndex.setRowKey(row, l);
    }

    @Override
    public double getCost(SessionLocal sessionLocal, int[] nArray, TableFilter[] tableFilterArray, int n, SortOrder sortOrder, AllColumnsForPlan allColumnsForPlan) {
        try {
            return 10L * this.getCostRangeIndex(nArray, this.dataMap.sizeAsLongMax(), tableFilterArray, n, sortOrder, true, allColumnsForPlan);
        }
        catch (MVStoreException mVStoreException) {
            throw DbException.get(90007, mVStoreException, new String[0]);
        }
    }

    @Override
    public int getColumnIndex(Column column) {
        return -1;
    }

    @Override
    public boolean isFirstColumn(Column column) {
        return false;
    }

    @Override
    public void remove(SessionLocal sessionLocal) {
        TransactionMap<Long, SearchRow> transactionMap = this.getMap(sessionLocal);
        if (!transactionMap.isClosed()) {
            Transaction transaction = sessionLocal.getTransaction();
            transaction.removeMap(transactionMap);
        }
    }

    @Override
    public void truncate(SessionLocal sessionLocal) {
        if (this.mvTable.getContainsLargeObject()) {
            this.database.getLobStorage().removeAllForTable(this.table.getId());
        }
        this.getMap(sessionLocal).clear();
    }

    @Override
    public boolean canGetFirstOrLast() {
        return true;
    }

    @Override
    public Cursor findFirstOrLast(SessionLocal sessionLocal, boolean bl) {
        TransactionMap<Long, SearchRow> transactionMap = this.getMap(sessionLocal);
        Map.Entry<Long, SearchRow> entry = bl ? transactionMap.firstEntry() : transactionMap.lastEntry();
        return new SingleRowCursor(entry != null ? MVPrimaryIndex.setRowKey((Row)entry.getValue(), entry.getKey()) : null);
    }

    @Override
    public boolean needRebuild() {
        return false;
    }

    @Override
    public long getRowCount(SessionLocal sessionLocal) {
        return this.getMap(sessionLocal).sizeAsLong();
    }

    public long getRowCountMax() {
        return this.dataMap.sizeAsLongMax();
    }

    @Override
    public long getRowCountApproximation(SessionLocal sessionLocal) {
        return this.getRowCountMax();
    }

    @Override
    public long getDiskSpaceUsed() {
        return this.dataMap.map.getRootPage().getDiskSpaceUsed();
    }

    public String getMapName() {
        return this.mapName;
    }

    @Override
    public void addRowsToBuffer(List<Row> list, String string) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addBufferedRows(List<String> list) {
        throw new UnsupportedOperationException();
    }

    private Cursor find(SessionLocal sessionLocal, Long l, Long l2) {
        TransactionMap<Long, SearchRow> transactionMap = this.getMap(sessionLocal);
        if (l != null && l2 != null && l.longValue() == l2.longValue()) {
            return new SingleRowCursor(MVPrimaryIndex.setRowKey((Row)transactionMap.getFromSnapshot(l), l));
        }
        return new MVStoreCursor(transactionMap.entryIterator(l, l2));
    }

    @Override
    public boolean isRowIdIndex() {
        return true;
    }

    TransactionMap<Long, SearchRow> getMap(SessionLocal sessionLocal) {
        if (sessionLocal == null) {
            return this.dataMap;
        }
        Transaction transaction = sessionLocal.getTransaction();
        return this.dataMap.getInstance(transaction);
    }

    @Override
    public MVMap<Long, VersionedValue<SearchRow>> getMVMap() {
        return this.dataMap.map;
    }

    private static Row setRowKey(Row row, long l) {
        if (row != null && row.getKey() == 0L) {
            row.setKey(l);
        }
        return row;
    }

    static final class MVStoreCursor
    implements Cursor {
        private final TransactionMap.TMIterator<Long, SearchRow, Map.Entry<Long, SearchRow>> it;
        private Map.Entry<Long, SearchRow> current;
        private Row row;

        public MVStoreCursor(TransactionMap.TMIterator<Long, SearchRow, Map.Entry<Long, SearchRow>> tMIterator) {
            this.it = tMIterator;
        }

        @Override
        public Row get() {
            if (this.row == null && this.current != null) {
                this.row = (Row)this.current.getValue();
                if (this.row.getKey() == 0L) {
                    this.row.setKey(this.current.getKey());
                }
            }
            return this.row;
        }

        @Override
        public SearchRow getSearchRow() {
            return this.get();
        }

        @Override
        public boolean next() {
            this.current = this.it.fetchNext();
            this.row = null;
            return this.current != null;
        }

        @Override
        public boolean previous() {
            throw DbException.getUnsupportedException("previous");
        }
    }
}

