/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.db;

import java.util.Iterator;
import java.util.List;
import org.h2.command.query.AllColumnsForPlan;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.index.Cursor;
import org.h2.index.IndexType;
import org.h2.index.SpatialIndex;
import org.h2.message.DbException;
import org.h2.mvstore.MVMap;
import org.h2.mvstore.MVStoreException;
import org.h2.mvstore.Page;
import org.h2.mvstore.db.MVIndex;
import org.h2.mvstore.db.MVTable;
import org.h2.mvstore.db.NullValueDataType;
import org.h2.mvstore.db.SpatialKey;
import org.h2.mvstore.rtree.MVRTreeMap;
import org.h2.mvstore.rtree.Spatial;
import org.h2.mvstore.tx.Transaction;
import org.h2.mvstore.tx.TransactionMap;
import org.h2.mvstore.tx.VersionedValueType;
import org.h2.mvstore.type.DataType;
import org.h2.result.Row;
import org.h2.result.SearchRow;
import org.h2.result.SortOrder;
import org.h2.table.Column;
import org.h2.table.IndexColumn;
import org.h2.table.TableFilter;
import org.h2.value.Value;
import org.h2.value.ValueGeometry;
import org.h2.value.ValueNull;
import org.h2.value.VersionedValue;

public class MVSpatialIndex
extends MVIndex<Spatial, Value>
implements SpatialIndex {
    final MVTable mvTable;
    private final TransactionMap<Spatial, Value> dataMap;
    private final MVRTreeMap<VersionedValue<Value>> spatialMap;

    public MVSpatialIndex(Database database, MVTable mVTable, int n, String string, IndexColumn[] indexColumnArray, int n2, IndexType indexType) {
        super(mVTable, n, string, indexColumnArray, n2, indexType);
        if (indexColumnArray.length != 1) {
            throw DbException.getUnsupportedException("Can only index one column");
        }
        IndexColumn indexColumn = indexColumnArray[0];
        if ((indexColumn.sortType & 1) != 0) {
            throw DbException.getUnsupportedException("Cannot index in descending order");
        }
        if ((indexColumn.sortType & 2) != 0) {
            throw DbException.getUnsupportedException("Nulls first is not supported");
        }
        if ((indexColumn.sortType & 4) != 0) {
            throw DbException.getUnsupportedException("Nulls last is not supported");
        }
        if (indexColumn.column.getType().getValueType() != 37) {
            throw DbException.getUnsupportedException("Spatial index on non-geometry column, " + indexColumn.column.getCreateSQL());
        }
        this.mvTable = mVTable;
        if (!this.database.isStarting()) {
            MVSpatialIndex.checkIndexColumnTypes(indexColumnArray);
        }
        String string2 = "index." + this.getId();
        VersionedValueType versionedValueType = new VersionedValueType(NullValueDataType.INSTANCE);
        MVMap.BasicBuilder basicBuilder = new MVRTreeMap.Builder().valueType((DataType)versionedValueType);
        this.spatialMap = (MVRTreeMap)database.getStore().getMvStore().openMap(string2, basicBuilder);
        Transaction transaction = this.mvTable.getTransactionBegin();
        this.dataMap = transaction.openMapX(this.spatialMap);
        this.dataMap.map.setVolatile(!mVTable.isPersistData() || !indexType.isPersistent());
        transaction.commit();
    }

    @Override
    public void addRowsToBuffer(List<Row> list, String string) {
        throw DbException.getInternalError();
    }

    @Override
    public void addBufferedRows(List<String> list) {
        throw DbException.getInternalError();
    }

    @Override
    public void close(SessionLocal sessionLocal) {
    }

    @Override
    public void add(SessionLocal sessionLocal, Row row) {
        Spatial spatial;
        SpatialKeyIterator spatialKeyIterator;
        MVRTreeMap.RTreeCursor<VersionedValue<Value>> rTreeCursor;
        TransactionMap<Spatial, Value> transactionMap = this.getMap(sessionLocal);
        SpatialKey spatialKey = this.getKey(row);
        if (spatialKey.isNull()) {
            return;
        }
        if (this.uniqueColumnColumn > 0) {
            rTreeCursor = this.spatialMap.findContainedKeys(spatialKey);
            spatialKeyIterator = new SpatialKeyIterator(transactionMap, rTreeCursor, false);
            while (spatialKeyIterator.hasNext()) {
                spatial = (Spatial)spatialKeyIterator.next();
                if (!spatial.equalsIgnoringId(spatialKey)) continue;
                throw this.getDuplicateKeyException(spatialKey.toString());
            }
        }
        try {
            transactionMap.put(spatialKey, ValueNull.INSTANCE);
        }
        catch (MVStoreException mVStoreException) {
            throw this.mvTable.convertException(mVStoreException);
        }
        if (this.uniqueColumnColumn > 0) {
            rTreeCursor = this.spatialMap.findContainedKeys(spatialKey);
            spatialKeyIterator = new SpatialKeyIterator(transactionMap, rTreeCursor, true);
            while (spatialKeyIterator.hasNext()) {
                spatial = (Spatial)spatialKeyIterator.next();
                if (!spatial.equalsIgnoringId(spatialKey) || transactionMap.isSameTransaction(spatial)) continue;
                transactionMap.remove(spatialKey);
                if (transactionMap.getImmediate(spatial) != null) {
                    throw this.getDuplicateKeyException(spatial.toString());
                }
                throw DbException.get(90131, this.table.getName());
            }
        }
    }

    @Override
    public void remove(SessionLocal sessionLocal, Row row) {
        SpatialKey spatialKey = this.getKey(row);
        if (spatialKey.isNull()) {
            return;
        }
        TransactionMap<Spatial, Value> transactionMap = this.getMap(sessionLocal);
        try {
            Value value = transactionMap.remove(spatialKey);
            if (value == null) {
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
    public Cursor find(SessionLocal sessionLocal, SearchRow searchRow, SearchRow searchRow2) {
        Iterator<Object> iterator2 = this.spatialMap.keyIterator(null);
        TransactionMap<Spatial, Value> transactionMap = this.getMap(sessionLocal);
        SpatialKeyIterator spatialKeyIterator = new SpatialKeyIterator(transactionMap, iterator2, false);
        return new MVStoreCursor(sessionLocal, spatialKeyIterator, this.mvTable);
    }

    @Override
    public Cursor findByGeometry(SessionLocal sessionLocal, SearchRow searchRow, SearchRow searchRow2, SearchRow searchRow3) {
        if (searchRow3 == null) {
            return this.find(sessionLocal, searchRow, searchRow2);
        }
        MVRTreeMap.RTreeCursor<VersionedValue<Value>> rTreeCursor = this.spatialMap.findIntersectingKeys(this.getKey(searchRow3));
        TransactionMap<Spatial, Value> transactionMap = this.getMap(sessionLocal);
        SpatialKeyIterator spatialKeyIterator = new SpatialKeyIterator(transactionMap, rTreeCursor, false);
        return new MVStoreCursor(sessionLocal, spatialKeyIterator, this.mvTable);
    }

    public Value getBounds(SessionLocal sessionLocal) {
        FindBoundsCursor findBoundsCursor = new FindBoundsCursor(this.spatialMap.getRootPage(), new SpatialKey(0L, new float[0]), sessionLocal, this.getMap(sessionLocal), this.columnIds[0]);
        while (findBoundsCursor.hasNext()) {
            findBoundsCursor.next();
        }
        return findBoundsCursor.getBounds();
    }

    public Value getEstimatedBounds(SessionLocal sessionLocal) {
        Page page = this.spatialMap.getRootPage();
        int n = page.getKeyCount();
        if (n > 0) {
            Spatial spatial = (Spatial)page.getKey(0);
            float f = spatial.min(0);
            float f2 = spatial.max(0);
            float f3 = spatial.min(1);
            float f4 = spatial.max(1);
            for (int i = 1; i < n; ++i) {
                spatial = (Spatial)page.getKey(i);
                float f5 = spatial.min(0);
                float f6 = spatial.max(0);
                float f7 = spatial.min(1);
                float f8 = spatial.max(1);
                if (f5 < f) {
                    f = f5;
                }
                if (f6 > f2) {
                    f2 = f6;
                }
                if (f7 < f3) {
                    f3 = f7;
                }
                if (!(f8 > f4)) continue;
                f4 = f8;
            }
            return ValueGeometry.fromEnvelope(new double[]{f, f2, f3, f4});
        }
        return ValueNull.INSTANCE;
    }

    private SpatialKey getKey(SearchRow searchRow) {
        double[] dArray;
        Value value = searchRow.getValue(this.columnIds[0]);
        if (value == ValueNull.INSTANCE || (dArray = value.convertToGeometry(null).getEnvelopeNoCopy()) == null) {
            return new SpatialKey(searchRow.getKey(), new float[0]);
        }
        return new SpatialKey(searchRow.getKey(), (float)dArray[0], (float)dArray[1], (float)dArray[2], (float)dArray[3]);
    }

    @Override
    public MVTable getTable() {
        return this.mvTable;
    }

    @Override
    public double getCost(SessionLocal sessionLocal, int[] nArray, TableFilter[] tableFilterArray, int n, SortOrder sortOrder, AllColumnsForPlan allColumnsForPlan) {
        return MVSpatialIndex.getCostRangeIndex(nArray, this.columns);
    }

    public static long getCostRangeIndex(int[] nArray, Column[] columnArray) {
        if (columnArray.length == 0) {
            return Long.MAX_VALUE;
        }
        for (Column column : columnArray) {
            int n = column.getColumnId();
            int n2 = nArray[n];
            if ((n2 & 0x10) == 16) continue;
            return Long.MAX_VALUE;
        }
        return 2L;
    }

    @Override
    public void remove(SessionLocal sessionLocal) {
        TransactionMap<Spatial, Value> transactionMap = this.getMap(sessionLocal);
        if (!transactionMap.isClosed()) {
            Transaction transaction = sessionLocal.getTransaction();
            transaction.removeMap(transactionMap);
        }
    }

    @Override
    public void truncate(SessionLocal sessionLocal) {
        TransactionMap<Spatial, Value> transactionMap = this.getMap(sessionLocal);
        transactionMap.clear();
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
        TransactionMap<Spatial, Value> transactionMap = this.getMap(sessionLocal);
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

    private TransactionMap<Spatial, Value> getMap(SessionLocal sessionLocal) {
        if (sessionLocal == null) {
            return this.dataMap;
        }
        Transaction transaction = sessionLocal.getTransaction();
        return this.dataMap.getInstance(transaction);
    }

    @Override
    public MVMap<Spatial, VersionedValue<Value>> getMVMap() {
        return this.dataMap.map;
    }

    private final class FindBoundsCursor
    extends MVRTreeMap.RTreeCursor<VersionedValue<Value>> {
        private final SessionLocal session;
        private final TransactionMap<Spatial, Value> map;
        private final int columnId;
        private boolean hasBounds;
        private float bminxf;
        private float bmaxxf;
        private float bminyf;
        private float bmaxyf;
        private double bminxd;
        private double bmaxxd;
        private double bminyd;
        private double bmaxyd;

        FindBoundsCursor(Page<Spatial, VersionedValue<Value>> page, Spatial spatial, SessionLocal sessionLocal, TransactionMap<Spatial, Value> transactionMap, int n) {
            super(page, spatial);
            this.session = sessionLocal;
            this.map = transactionMap;
            this.columnId = n;
        }

        @Override
        protected boolean check(boolean bl, Spatial spatial, Spatial spatial2) {
            float f = spatial.min(0);
            float f2 = spatial.max(0);
            float f3 = spatial.min(1);
            float f4 = spatial.max(1);
            if (bl) {
                if (this.hasBounds) {
                    if ((f <= this.bminxf || f2 >= this.bmaxxf || f3 <= this.bminyf || f4 >= this.bmaxyf) && this.map.containsKey(spatial)) {
                        double[] dArray = ((ValueGeometry)MVSpatialIndex.this.mvTable.getRow(this.session, spatial.getId()).getValue(this.columnId)).getEnvelopeNoCopy();
                        double d = dArray[0];
                        double d2 = dArray[1];
                        double d3 = dArray[2];
                        double d4 = dArray[3];
                        if (d < this.bminxd) {
                            this.bminxf = f;
                            this.bminxd = d;
                        }
                        if (d2 > this.bmaxxd) {
                            this.bmaxxf = f2;
                            this.bmaxxd = d2;
                        }
                        if (d3 < this.bminyd) {
                            this.bminyf = f3;
                            this.bminyd = d3;
                        }
                        if (d4 > this.bmaxyd) {
                            this.bmaxyf = f4;
                            this.bmaxyd = d4;
                        }
                    }
                } else if (this.map.containsKey(spatial)) {
                    this.hasBounds = true;
                    double[] dArray = ((ValueGeometry)MVSpatialIndex.this.mvTable.getRow(this.session, spatial.getId()).getValue(this.columnId)).getEnvelopeNoCopy();
                    this.bminxf = f;
                    this.bminxd = dArray[0];
                    this.bmaxxf = f2;
                    this.bmaxxd = dArray[1];
                    this.bminyf = f3;
                    this.bminyd = dArray[2];
                    this.bmaxyf = f4;
                    this.bmaxyd = dArray[3];
                }
            } else if (this.hasBounds) {
                if (f <= this.bminxf || f2 >= this.bmaxxf || f3 <= this.bminyf || f4 >= this.bmaxyf) {
                    return true;
                }
            } else {
                return true;
            }
            return false;
        }

        Value getBounds() {
            return this.hasBounds ? ValueGeometry.fromEnvelope(new double[]{this.bminxd, this.bmaxxd, this.bminyd, this.bmaxyd}) : ValueNull.INSTANCE;
        }
    }

    private static class SpatialKeyIterator
    implements Iterator<Spatial> {
        private final TransactionMap<Spatial, Value> map;
        private final Iterator<Spatial> iterator;
        private final boolean includeUncommitted;
        private Spatial current;

        SpatialKeyIterator(TransactionMap<Spatial, Value> transactionMap, Iterator<Spatial> iterator2, boolean bl) {
            this.map = transactionMap;
            this.iterator = iterator2;
            this.includeUncommitted = bl;
            this.fetchNext();
        }

        private void fetchNext() {
            while (this.iterator.hasNext()) {
                this.current = this.iterator.next();
                if (!this.includeUncommitted && !this.map.containsKey(this.current)) continue;
                return;
            }
            this.current = null;
        }

        @Override
        public boolean hasNext() {
            return this.current != null;
        }

        @Override
        public Spatial next() {
            Spatial spatial = this.current;
            this.fetchNext();
            return spatial;
        }
    }

    private static class MVStoreCursor
    implements Cursor {
        private final SessionLocal session;
        private final Iterator<Spatial> it;
        private final MVTable mvTable;
        private Spatial current;
        private SearchRow searchRow;
        private Row row;

        MVStoreCursor(SessionLocal sessionLocal, Iterator<Spatial> iterator2, MVTable mVTable) {
            this.session = sessionLocal;
            this.it = iterator2;
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
            if (this.searchRow == null && this.current != null) {
                this.searchRow = this.mvTable.getTemplateRow();
                this.searchRow.setKey(this.current.getId());
            }
            return this.searchRow;
        }

        @Override
        public boolean next() {
            this.current = this.it.hasNext() ? this.it.next() : null;
            this.searchRow = null;
            this.row = null;
            return this.current != null;
        }

        @Override
        public boolean previous() {
            throw DbException.getUnsupportedException("previous");
        }
    }
}

