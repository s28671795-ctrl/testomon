/*
 * Decompiled with CFR 0.152.
 */
package org.h2.result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;
import org.h2.engine.Database;
import org.h2.engine.Session;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionColumn;
import org.h2.message.DbException;
import org.h2.mvstore.db.MVTempResult;
import org.h2.result.ResultExternal;
import org.h2.result.ResultInterface;
import org.h2.result.ResultTarget;
import org.h2.result.Row;
import org.h2.result.SortOrder;
import org.h2.table.Column;
import org.h2.table.Table;
import org.h2.util.Utils;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBigint;
import org.h2.value.ValueLob;
import org.h2.value.ValueRow;

public class LocalResult
implements ResultInterface,
ResultTarget {
    private int maxMemoryRows;
    private final SessionLocal session;
    private int visibleColumnCount;
    private int resultColumnCount;
    private Expression[] expressions;
    private boolean forDataChangeDeltaTable;
    private long rowId;
    private long rowCount;
    private ArrayList<Value[]> rows;
    private SortOrder sort;
    private TreeMap<ValueRow, Value[]> distinctRows;
    private Value[] currentRow;
    private long offset;
    private long limit = -1L;
    private boolean fetchPercent;
    private SortOrder withTiesSortOrder;
    private boolean limitsWereApplied;
    private ResultExternal external;
    private boolean distinct;
    private int[] distinctIndexes;
    private boolean closed;
    private boolean containsLobs;
    private Boolean containsNull;

    public static LocalResult forTable(SessionLocal sessionLocal, Table table) {
        Column[] columnArray = table.getColumns();
        int n = columnArray.length;
        Expression[] expressionArray = new Expression[n + 1];
        Database database = sessionLocal.getDatabase();
        for (int i = 0; i < n; ++i) {
            expressionArray[i] = new ExpressionColumn(database, columnArray[i]);
        }
        Column column = table.getRowIdColumn();
        expressionArray[n] = column != null ? new ExpressionColumn(database, column) : new ExpressionColumn(database, null, table.getName());
        return new LocalResult(sessionLocal, expressionArray, n, n + 1);
    }

    public LocalResult() {
        this(null);
    }

    private LocalResult(SessionLocal sessionLocal) {
        this.session = sessionLocal;
    }

    public LocalResult(SessionLocal sessionLocal, Expression[] expressionArray, int n, int n2) {
        Database database;
        this.session = sessionLocal;
        this.maxMemoryRows = sessionLocal == null ? Integer.MAX_VALUE : ((database = sessionLocal.getDatabase()).isPersistent() && !database.isReadOnly() ? sessionLocal.getDatabase().getMaxMemoryRows() : Integer.MAX_VALUE);
        this.rows = Utils.newSmallArrayList();
        this.visibleColumnCount = n;
        this.resultColumnCount = n2;
        this.rowId = -1L;
        this.expressions = expressionArray;
    }

    @Override
    public boolean isLazy() {
        return false;
    }

    public void setMaxMemoryRows(int n) {
        this.maxMemoryRows = n;
    }

    public void setForDataChangeDeltaTable() {
        this.forDataChangeDeltaTable = true;
    }

    @Override
    public LocalResult createShallowCopy(Session session) {
        if (this.external == null && (this.rows == null || (long)this.rows.size() < this.rowCount)) {
            return null;
        }
        if (this.containsLobs) {
            return null;
        }
        ResultExternal resultExternal = null;
        if (this.external != null && (resultExternal = this.external.createShallowCopy()) == null) {
            return null;
        }
        LocalResult localResult = new LocalResult((SessionLocal)session);
        localResult.maxMemoryRows = this.maxMemoryRows;
        localResult.visibleColumnCount = this.visibleColumnCount;
        localResult.resultColumnCount = this.resultColumnCount;
        localResult.expressions = this.expressions;
        localResult.rowId = -1L;
        localResult.rowCount = this.rowCount;
        localResult.rows = this.rows;
        localResult.sort = this.sort;
        localResult.distinctRows = this.distinctRows;
        localResult.distinct = this.distinct;
        localResult.distinctIndexes = this.distinctIndexes;
        localResult.currentRow = null;
        localResult.offset = 0L;
        localResult.limit = -1L;
        localResult.external = resultExternal;
        localResult.containsNull = this.containsNull;
        return localResult;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sort = sortOrder;
    }

    public void setDistinct() {
        assert (this.distinctIndexes == null);
        this.distinct = true;
        this.distinctRows = new TreeMap(this.session.getDatabase().getCompareMode());
    }

    public void setDistinct(int[] nArray) {
        assert (!this.distinct);
        this.distinctIndexes = nArray;
        this.distinctRows = new TreeMap(this.session.getDatabase().getCompareMode());
    }

    private boolean isAnyDistinct() {
        return this.distinct || this.distinctIndexes != null;
    }

    public boolean containsDistinct(Value[] valueArray) {
        Object object;
        assert (valueArray.length == this.visibleColumnCount);
        if (this.external != null) {
            return this.external.contains(valueArray);
        }
        if (this.distinctRows == null) {
            this.distinctRows = new TreeMap(this.session.getDatabase().getCompareMode());
            object = this.rows.iterator();
            while (object.hasNext()) {
                Value[] valueArray2 = (Value[])object.next();
                ValueRow valueRow = this.getDistinctRow(valueArray2);
                this.distinctRows.put(valueRow, valueRow.getList());
            }
        }
        return this.distinctRows.get(object = ValueRow.get(valueArray)) != null;
    }

    public boolean containsNull() {
        Boolean bl = this.containsNull;
        if (bl == null) {
            bl = false;
            this.reset();
            block0: while (this.next()) {
                Value[] valueArray = this.currentRow;
                for (int i = 0; i < this.visibleColumnCount; ++i) {
                    if (!valueArray[i].containsNull()) continue;
                    bl = true;
                    break block0;
                }
            }
            this.reset();
            this.containsNull = bl;
        }
        return bl;
    }

    public void removeDistinct(Value[] valueArray) {
        if (!this.distinct) {
            throw DbException.getInternalError();
        }
        assert (valueArray.length == this.visibleColumnCount);
        if (this.distinctRows != null) {
            this.distinctRows.remove(ValueRow.get(valueArray));
            this.rowCount = this.distinctRows.size();
        } else {
            this.rowCount = this.external.removeRow(valueArray);
        }
    }

    @Override
    public void reset() {
        this.rowId = -1L;
        this.currentRow = null;
        if (this.external != null) {
            this.external.reset();
        }
    }

    public Row currentRowForTable() {
        int n = this.visibleColumnCount;
        Value[] valueArray = this.currentRow;
        Row row = this.session.getDatabase().getRowFactory().createRow(Arrays.copyOf(valueArray, n), -1);
        row.setKey(valueArray[n].getLong());
        return row;
    }

    @Override
    public Value[] currentRow() {
        return this.currentRow;
    }

    @Override
    public boolean next() {
        if (!this.closed && this.rowId < this.rowCount) {
            ++this.rowId;
            if (this.rowId < this.rowCount) {
                this.currentRow = this.external != null ? this.external.next() : this.rows.get((int)this.rowId);
                return true;
            }
            this.currentRow = null;
        }
        return false;
    }

    @Override
    public long getRowId() {
        return this.rowId;
    }

    @Override
    public boolean isAfterLast() {
        return this.rowId >= this.rowCount;
    }

    private void cloneLobs(Value[] valueArray) {
        for (int i = 0; i < valueArray.length; ++i) {
            Value value = valueArray[i];
            if (!(value instanceof ValueLob)) continue;
            if (this.forDataChangeDeltaTable) {
                this.containsLobs = true;
                continue;
            }
            ValueLob valueLob = ((ValueLob)value).copyToResult();
            if (valueLob == value) continue;
            this.containsLobs = true;
            valueArray[i] = this.session.addTemporaryLob(valueLob);
        }
    }

    private ValueRow getDistinctRow(Value[] valueArray) {
        if (this.distinctIndexes != null) {
            int n = this.distinctIndexes.length;
            Value[] valueArray2 = new Value[n];
            for (int i = 0; i < n; ++i) {
                valueArray2[i] = valueArray[this.distinctIndexes[i]];
            }
            valueArray = valueArray2;
        } else if (valueArray.length > this.visibleColumnCount) {
            valueArray = Arrays.copyOf(valueArray, this.visibleColumnCount);
        }
        return ValueRow.get(valueArray);
    }

    private void createExternalResult() {
        this.external = MVTempResult.of(this.session.getDatabase(), this.expressions, this.distinct, this.distinctIndexes, this.visibleColumnCount, this.resultColumnCount, this.sort);
    }

    public void addRowForTable(Row row) {
        int n = this.visibleColumnCount;
        Value[] valueArray = new Value[n + 1];
        for (int i = 0; i < n; ++i) {
            valueArray[i] = row.getValue(i);
        }
        valueArray[n] = ValueBigint.get(row.getKey());
        this.addRowInternal(valueArray);
    }

    @Override
    public void addRow(Value ... valueArray) {
        assert (valueArray.length == this.resultColumnCount);
        this.cloneLobs(valueArray);
        this.addRowInternal(valueArray);
    }

    private void addRowInternal(Value ... valueArray) {
        if (this.isAnyDistinct()) {
            if (this.distinctRows != null) {
                ValueRow valueRow = this.getDistinctRow(valueArray);
                Value[] valueArray2 = this.distinctRows.get(valueRow);
                if (valueArray2 == null || this.sort != null && this.sort.compare(valueArray2, valueArray) > 0) {
                    this.distinctRows.put(valueRow, valueArray);
                }
                this.rowCount = this.distinctRows.size();
                if (this.rowCount > (long)this.maxMemoryRows) {
                    this.createExternalResult();
                    this.rowCount = this.external.addRows(this.distinctRows.values());
                    this.distinctRows = null;
                }
            } else {
                this.rowCount = this.external.addRow(valueArray);
            }
        } else {
            this.rows.add(valueArray);
            ++this.rowCount;
            if (this.rows.size() > this.maxMemoryRows) {
                this.addRowsToDisk();
            }
        }
    }

    private void addRowsToDisk() {
        if (this.external == null) {
            this.createExternalResult();
        }
        this.rowCount = this.external.addRows(this.rows);
        this.rows.clear();
    }

    @Override
    public int getVisibleColumnCount() {
        return this.visibleColumnCount;
    }

    public void done() {
        if (this.external != null) {
            this.addRowsToDisk();
        } else {
            if (this.isAnyDistinct()) {
                this.rows = new ArrayList<Value[]>(this.distinctRows.values());
            }
            if (this.sort != null && this.limit != 0L && !this.limitsWereApplied) {
                boolean bl;
                boolean bl2 = bl = this.limit > 0L && this.withTiesSortOrder == null;
                if (this.offset > 0L || bl) {
                    int n = this.rows.size();
                    if (this.offset < (long)n) {
                        int n2 = (int)this.offset;
                        if (bl && this.limit < (long)(n - n2)) {
                            n = n2 + (int)this.limit;
                        }
                        this.sort.sort(this.rows, n2, n);
                    }
                } else {
                    this.sort.sort(this.rows);
                }
            }
        }
        this.applyOffsetAndLimit();
        this.reset();
    }

    private void applyOffsetAndLimit() {
        boolean bl;
        if (this.limitsWereApplied) {
            return;
        }
        long l = Math.max(this.offset, 0L);
        long l2 = this.limit;
        if (l == 0L && l2 < 0L && !this.fetchPercent || this.rowCount == 0L) {
            return;
        }
        if (this.fetchPercent) {
            if (l2 < 0L || l2 > 100L) {
                throw DbException.getInvalidValueException("FETCH PERCENT", l2);
            }
            l2 = (l2 * this.rowCount + 99L) / 100L;
        }
        boolean bl2 = bl = l >= this.rowCount || l2 == 0L;
        if (!bl) {
            long l3 = this.rowCount - l;
            long l4 = l2 = l2 < 0L ? l3 : Math.min(l3, l2);
            if (l == 0L && l3 <= l2) {
                return;
            }
        } else {
            l2 = 0L;
        }
        this.distinctRows = null;
        this.rowCount = l2;
        if (this.external == null) {
            if (bl) {
                this.rows.clear();
                return;
            }
            int n = (int)(l + l2);
            if (this.withTiesSortOrder != null) {
                Value[] valueArray = this.rows.get(n - 1);
                while (n < this.rows.size() && this.withTiesSortOrder.compare(valueArray, this.rows.get(n)) == 0) {
                    ++n;
                    ++this.rowCount;
                }
            }
            if (l != 0L || n != this.rows.size()) {
                this.rows = new ArrayList<Value[]>(this.rows.subList((int)l, n));
            }
        } else {
            if (bl) {
                this.external.close();
                this.external = null;
                return;
            }
            this.trimExternal(l, l2);
        }
    }

    private void trimExternal(long l, long l2) {
        ResultExternal resultExternal = this.external;
        this.external = null;
        resultExternal.reset();
        while (--l >= 0L) {
            resultExternal.next();
        }
        Value[] valueArray = null;
        while (--l2 >= 0L) {
            valueArray = resultExternal.next();
            this.rows.add(valueArray);
            if (this.rows.size() <= this.maxMemoryRows) continue;
            this.addRowsToDisk();
        }
        if (this.withTiesSortOrder != null && valueArray != null) {
            Value[] valueArray2 = valueArray;
            while ((valueArray = resultExternal.next()) != null && this.withTiesSortOrder.compare(valueArray2, valueArray) == 0) {
                this.rows.add(valueArray);
                ++this.rowCount;
                if (this.rows.size() <= this.maxMemoryRows) continue;
                this.addRowsToDisk();
            }
        }
        if (this.external != null) {
            this.addRowsToDisk();
        }
        resultExternal.close();
    }

    @Override
    public long getRowCount() {
        return this.rowCount;
    }

    @Override
    public void limitsWereApplied() {
        this.limitsWereApplied = true;
    }

    @Override
    public boolean hasNext() {
        return !this.closed && this.rowId < this.rowCount - 1L;
    }

    public void setLimit(long l) {
        this.limit = l;
    }

    public void setFetchPercent(boolean bl) {
        this.fetchPercent = bl;
    }

    public void setWithTies(SortOrder sortOrder) {
        assert (this.sort == null || this.sort == sortOrder);
        this.withTiesSortOrder = sortOrder;
    }

    @Override
    public boolean needToClose() {
        return this.external != null;
    }

    @Override
    public void close() {
        if (this.external != null) {
            this.external.close();
            this.external = null;
            this.closed = true;
        }
    }

    @Override
    public String getAlias(int n) {
        return this.expressions[n].getAlias(this.session, n);
    }

    @Override
    public String getTableName(int n) {
        return this.expressions[n].getTableName();
    }

    @Override
    public String getSchemaName(int n) {
        return this.expressions[n].getSchemaName();
    }

    @Override
    public String getColumnName(int n) {
        return this.expressions[n].getColumnName(this.session, n);
    }

    @Override
    public TypeInfo getColumnType(int n) {
        return this.expressions[n].getType();
    }

    @Override
    public int getNullable(int n) {
        return this.expressions[n].getNullable();
    }

    @Override
    public boolean isIdentity(int n) {
        return this.expressions[n].isIdentity();
    }

    public void setOffset(long l) {
        this.offset = l;
    }

    public String toString() {
        return super.toString() + " columns: " + this.visibleColumnCount + " rows: " + this.rowCount + " pos: " + this.rowId;
    }

    @Override
    public boolean isClosed() {
        return this.closed;
    }

    @Override
    public int getFetchSize() {
        return 0;
    }

    @Override
    public void setFetchSize(int n) {
    }
}

