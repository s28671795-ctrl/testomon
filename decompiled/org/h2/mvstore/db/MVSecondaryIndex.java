/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.db;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import org.h2.command.query.AllColumnsForPlan;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.index.Cursor;
import org.h2.index.IndexType;
import org.h2.index.SingleRowCursor;
import org.h2.message.DbException;
import org.h2.mvstore.MVMap;
import org.h2.mvstore.MVStore;
import org.h2.mvstore.MVStoreException;
import org.h2.mvstore.db.MVIndex;
import org.h2.mvstore.db.MVTable;
import org.h2.mvstore.db.NullValueDataType;
import org.h2.mvstore.db.RowDataType;
import org.h2.mvstore.tx.Transaction;
import org.h2.mvstore.tx.TransactionMap;
import org.h2.mvstore.type.DataType;
import org.h2.result.Row;
import org.h2.result.RowFactory;
import org.h2.result.SearchRow;
import org.h2.result.SortOrder;
import org.h2.table.IndexColumn;
import org.h2.table.TableFilter;
import org.h2.value.Value;
import org.h2.value.ValueNull;
import org.h2.value.VersionedValue;

public final class MVSecondaryIndex
extends MVIndex<SearchRow, Value> {
    private final MVTable mvTable;
    private final TransactionMap<SearchRow, Value> dataMap;

    public MVSecondaryIndex(Database database, MVTable mVTable, int n, String string, IndexColumn[] indexColumnArray, int n2, IndexType indexType) {
        super(mVTable, n, string, indexColumnArray, n2, indexType);
        this.mvTable = mVTable;
        if (!this.database.isStarting()) {
            MVSecondaryIndex.checkIndexColumnTypes(indexColumnArray);
        }
        String string2 = "index." + this.getId();
        RowDataType rowDataType = this.getRowFactory().getRowDataType();
        Transaction transaction = this.mvTable.getTransactionBegin();
        this.dataMap = transaction.openMap(string2, rowDataType, NullValueDataType.INSTANCE);
        this.dataMap.map.setVolatile(!mVTable.isPersistData() || !indexType.isPersistent());
        if (!database.isStarting()) {
            this.dataMap.clear();
        }
        transaction.commit();
        if (!rowDataType.equals(this.dataMap.getKeyType())) {
            throw DbException.getInternalError("Incompatible key type, expected " + rowDataType + " but got " + this.dataMap.getKeyType() + " for index " + string);
        }
    }

    @Override
    public void addRowsToBuffer(List<Row> list, String string) {
        MVMap<SearchRow, Value> mVMap = this.openMap(string);
        for (Row row : list) {
            SearchRow searchRow = this.getRowFactory().createRow();
            searchRow.copyFrom(row);
            mVMap.append(searchRow, ValueNull.INSTANCE);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void addBufferedRows(List<String> list) {
        Object object;
        int n = list.size();
        PriorityQueue<Source> priorityQueue = new PriorityQueue<Source>(n, new Source.Comparator(this.getRowFactory().getRowDataType()));
        for (String object2 : list) {
            Iterator<Object> iterator2 = this.openMap(object2).keyIterator(null);
            if (!iterator2.hasNext()) continue;
            priorityQueue.offer(new Source(iterator2));
        }
        try {
            while (!priorityQueue.isEmpty()) {
                object = (Source)priorityQueue.poll();
                SearchRow searchRow = ((Source)object).next();
                if (this.needsUniqueCheck(searchRow)) {
                    this.checkUnique(false, this.dataMap, searchRow, Long.MIN_VALUE);
                }
                this.dataMap.putCommitted(searchRow, ValueNull.INSTANCE);
                if (!((Source)object).hasNext()) continue;
                priorityQueue.offer((Source)object);
            }
        }
        finally {
            object = this.database.getStore().getMvStore();
            for (String string : list) {
                ((MVStore)object).removeMap(string);
            }
        }
    }

    private MVMap<SearchRow, Value> openMap(String string) {
        RowDataType rowDataType = this.getRowFactory().getRowDataType();
        MVMap.BasicBuilder basicBuilder = ((MVMap.Builder)new MVMap.Builder().singleWriter().keyType((DataType)rowDataType)).valueType((DataType)NullValueDataType.INSTANCE);
        Object m4 = this.database.getStore().getMvStore().openMap(string, basicBuilder);
        if (!rowDataType.equals(((MVMap)m4).getKeyType())) {
            throw DbException.getInternalError("Incompatible key type, expected " + rowDataType + " but got " + ((MVMap)m4).getKeyType() + " for map " + string);
        }
        return m4;
    }

    @Override
    public void close(SessionLocal sessionLocal) {
    }

    @Override
    public void add(SessionLocal sessionLocal, Row row) {
        TransactionMap<SearchRow, Value> transactionMap = this.getMap(sessionLocal);
        SearchRow searchRow = this.convertToKey(row, null);
        boolean bl = this.needsUniqueCheck(row);
        if (bl) {
            boolean bl2 = !sessionLocal.getTransaction().allowNonRepeatableRead();
            this.checkUnique(bl2, transactionMap, row, Long.MIN_VALUE);
        }
        try {
            transactionMap.put(searchRow, ValueNull.INSTANCE);
        }
        catch (MVStoreException mVStoreException) {
            throw this.mvTable.convertException(mVStoreException);
        }
        if (bl) {
            this.checkUnique(false, transactionMap, row, row.getKey());
        }
    }

    private void checkUnique(boolean bl, TransactionMap<SearchRow, Value> transactionMap, SearchRow searchRow, long l) {
        SearchRow searchRow2;
        TransactionMap.TMIterator<SearchRow, Value, SearchRow> tMIterator;
        RowFactory rowFactory = this.getUniqueRowFactory();
        SearchRow searchRow3 = rowFactory.createRow();
        searchRow3.copyFrom(searchRow);
        searchRow3.setKey(Long.MIN_VALUE);
        SearchRow searchRow4 = rowFactory.createRow();
        searchRow4.copyFrom(searchRow);
        searchRow4.setKey(Long.MAX_VALUE);
        if (bl) {
            tMIterator = transactionMap.keyIterator(searchRow3, searchRow4);
            while ((searchRow2 = tMIterator.fetchNext()) != null) {
                if (l == searchRow2.getKey() || transactionMap.isDeletedByCurrentTransaction(searchRow2)) continue;
                throw this.getDuplicateKeyException(searchRow2.toString());
            }
        }
        tMIterator = transactionMap.keyIteratorUncommitted(searchRow3, searchRow4);
        while ((searchRow2 = tMIterator.fetchNext()) != null) {
            if (l == searchRow2.getKey()) continue;
            if (transactionMap.getImmediate(searchRow2) != null) {
                throw this.getDuplicateKeyException(searchRow2.toString());
            }
            throw DbException.get(90131, this.table.getName());
        }
    }

    @Override
    public void remove(SessionLocal sessionLocal, Row row) {
        SearchRow searchRow = this.convertToKey(row, null);
        TransactionMap<SearchRow, Value> transactionMap = this.getMap(sessionLocal);
        try {
            if (transactionMap.remove(searchRow) == null) {
                StringBuilder stringBuilder = new StringBuilder();
                this.getSQL(stringBuilder, 3).append(": ").append(row.getKey());
                throw DbException.get(90112, stringBuilder.toString());
            }
        }
        catch (MVStoreException mVStoreException) {
            throw this.mvTable.convertException(mVStoreException);
        }
    }

    @Override
    public void update(SessionLocal sessionLocal, Row row, Row row2) {
        SearchRow searchRow;
        SearchRow searchRow2 = this.convertToKey(row, null);
        if (!this.rowsAreEqual(searchRow2, searchRow = this.convertToKey(row2, null))) {
            super.update(sessionLocal, row, row2);
        }
    }

    private boolean rowsAreEqual(SearchRow searchRow, SearchRow searchRow2) {
        if (searchRow == searchRow2) {
            return true;
        }
        for (int n : this.columnIds) {
            Value value;
            Value value2 = searchRow.getValue(n);
            if (Objects.equals(value2, value = searchRow2.getValue(n))) continue;
            return false;
        }
        return searchRow.getKey() == searchRow2.getKey();
    }

    @Override
    public Cursor find(SessionLocal sessionLocal, SearchRow searchRow, SearchRow searchRow2) {
        return this.find(sessionLocal, searchRow, false, searchRow2);
    }

    private Cursor find(SessionLocal sessionLocal, SearchRow searchRow, boolean bl, SearchRow searchRow2) {
        SearchRow searchRow3 = this.convertToKey(searchRow, bl);
        SearchRow searchRow4 = this.convertToKey(searchRow2, Boolean.TRUE);
        return new MVStoreCursor(sessionLocal, this.getMap(sessionLocal).keyIterator(searchRow3, searchRow4), this.mvTable);
    }

    private SearchRow convertToKey(SearchRow searchRow, Boolean bl) {
        if (searchRow == null) {
            return null;
        }
        SearchRow searchRow2 = this.getRowFactory().createRow();
        searchRow2.copyFrom(searchRow);
        if (bl != null) {
            searchRow2.setKey(bl != false ? Long.MAX_VALUE : Long.MIN_VALUE);
        }
        return searchRow2;
    }

    @Override
    public MVTable getTable() {
        return this.mvTable;
    }

    @Override
    public double getCost(SessionLocal sessionLocal, int[] nArray, TableFilter[] tableFilterArray, int n, SortOrder sortOrder, AllColumnsForPlan allColumnsForPlan) {
        try {
            return 10L * this.getCostRangeIndex(nArray, this.dataMap.sizeAsLongMax(), tableFilterArray, n, sortOrder, false, allColumnsForPlan);
        }
        catch (MVStoreException mVStoreException) {
            throw DbException.get(90007, mVStoreException, new String[0]);
        }
    }

    @Override
    public void remove(SessionLocal sessionLocal) {
        TransactionMap<SearchRow, Value> transactionMap = this.getMap(sessionLocal);
        if (!transactionMap.isClosed()) {
            Transaction transaction = sessionLocal.getTransaction();
            transaction.removeMap(transactionMap);
        }
    }

    @Override
    public void truncate(SessionLocal sessionLocal) {
        TransactionMap<SearchRow, Value> transactionMap = this.getMap(sessionLocal);
        transactionMap.clear();
    }

    @Override
    public boolean canGetFirstOrLast() {
        return true;
    }

    @Override
    public Cursor findFirstOrLast(SessionLocal sessionLocal, boolean bl) {
        SearchRow searchRow;
        TransactionMap.TMIterator<SearchRow, Value, SearchRow> tMIterator = this.getMap(sessionLocal).keyIterator(null, (SearchRow)(!bl ? 1 : 0));
        while ((searchRow = tMIterator.fetchNext()) != null) {
            if (searchRow.getValue(this.columnIds[0]) == ValueNull.INSTANCE) continue;
            return new SingleRowCursor(this.mvTable.getRow(sessionLocal, searchRow.getKey()));
        }
        return new SingleRowCursor(null);
    }

    @Override
    public boolean needRebuild() {
        try {
            return this.dataMap.sizeAsLongMax() == 0L;
        }
        catch (MVStoreException mVStoreException) {
            throw DbException.get(90007, mVStoreException, new String[0]);
        }
    }

    @Override
    public long getRowCount(SessionLocal sessionLocal) {
        TransactionMap<SearchRow, Value> transactionMap = this.getMap(sessionLocal);
        return transactionMap.sizeAsLong();
    }

    @Override
    public long getRowCountApproximation(SessionLocal sessionLocal) {
        try {
            return this.dataMap.sizeAsLongMax();
        }
        catch (MVStoreException mVStoreException) {
            throw DbException.get(90007, mVStoreException, new String[0]);
        }
    }

    @Override
    public long getDiskSpaceUsed() {
        return 0L;
    }

    @Override
    public boolean canFindNext() {
        return true;
    }

    @Override
    public Cursor findNext(SessionLocal sessionLocal, SearchRow searchRow, SearchRow searchRow2) {
        return this.find(sessionLocal, searchRow, true, searchRow2);
    }

    private TransactionMap<SearchRow, Value> getMap(SessionLocal sessionLocal) {
        if (sessionLocal == null) {
            return this.dataMap;
        }
        Transaction transaction = sessionLocal.getTransaction();
        return this.dataMap.getInstance(transaction);
    }

    @Override
    public MVMap<SearchRow, VersionedValue<Value>> getMVMap() {
        return this.dataMap.map;
    }

    static final class MVStoreCursor
    implements Cursor {
        private final SessionLocal session;
        private final TransactionMap.TMIterator<SearchRow, Value, SearchRow> it;
        private final MVTable mvTable;
        private SearchRow current;
        private Row row;

        MVStoreCursor(SessionLocal sessionLocal, TransactionMap.TMIterator<SearchRow, Value, SearchRow> tMIterator, MVTable mVTable) {
            this.session = sessionLocal;
            this.it = tMIterator;
            this.mvTable = mVTable;
        }

        @Override
        public Row get() {
            SearchRow searchRow;
            if (this.row == null && (searchRow = this.getSearchRow()) != null) {
                this.row = this.mvTable.getRow(this.session, searchRow.getKey());
            }
            return this.row;
        }

        @Override
        public SearchRow getSearchRow() {
            return this.current;
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

    private static final class Source {
        private final Iterator<SearchRow> iterator;
        SearchRow currentRowData;

        public Source(Iterator<SearchRow> iterator2) {
            assert (iterator2.hasNext());
            this.iterator = iterator2;
            this.currentRowData = iterator2.next();
        }

        public boolean hasNext() {
            boolean bl = this.iterator.hasNext();
            if (bl) {
                this.currentRowData = this.iterator.next();
            }
            return bl;
        }

        public SearchRow next() {
            return this.currentRowData;
        }

        static final class Comparator
        implements java.util.Comparator<Source> {
            private final DataType<SearchRow> type;

            public Comparator(DataType<SearchRow> dataType) {
                this.type = dataType;
            }

            @Override
            public int compare(Source source2, Source source3) {
                return this.type.compare(source2.currentRowData, source3.currentRowData);
            }
        }
    }
}

