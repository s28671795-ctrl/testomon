/*
 * Decompiled with CFR 0.152.
 */
package org.h2.index;

import java.util.ArrayList;
import java.util.Arrays;
import org.h2.command.query.AllColumnsForPlan;
import org.h2.engine.NullsDistinct;
import org.h2.engine.SessionLocal;
import org.h2.index.Cursor;
import org.h2.index.IndexType;
import org.h2.message.DbException;
import org.h2.result.Row;
import org.h2.result.RowFactory;
import org.h2.result.SearchRow;
import org.h2.result.SortOrder;
import org.h2.schema.SchemaObject;
import org.h2.table.Column;
import org.h2.table.IndexColumn;
import org.h2.table.Table;
import org.h2.table.TableFilter;
import org.h2.util.StringUtils;
import org.h2.value.CompareMode;
import org.h2.value.DataType;
import org.h2.value.Typed;
import org.h2.value.Value;
import org.h2.value.ValueNull;

public abstract class Index
extends SchemaObject {
    protected IndexColumn[] indexColumns;
    protected Column[] columns;
    protected int[] columnIds;
    protected final int uniqueColumnColumn;
    protected final Table table;
    protected final IndexType indexType;
    private final RowFactory rowFactory;
    private final RowFactory uniqueRowFactory;

    protected static void checkIndexColumnTypes(IndexColumn[] indexColumnArray) {
        for (IndexColumn indexColumn : indexColumnArray) {
            if (DataType.isIndexable(indexColumn.column.getType())) continue;
            throw DbException.getUnsupportedException("Index on column: " + indexColumn.column.getCreateSQL());
        }
    }

    protected Index(Table table, int n, String string, IndexColumn[] indexColumnArray, int n2, IndexType indexType) {
        super(table.getSchema(), n, string, 5);
        Object object;
        this.uniqueColumnColumn = n2;
        this.indexType = indexType;
        this.table = table;
        if (indexColumnArray != null) {
            this.indexColumns = indexColumnArray;
            this.columns = new Column[indexColumnArray.length];
            int n3 = this.columns.length;
            this.columnIds = new int[n3];
            for (int i = 0; i < n3; ++i) {
                this.columns[i] = object = indexColumnArray[i].column;
                this.columnIds[i] = ((Column)object).getColumnId();
            }
        }
        RowFactory rowFactory = this.database.getRowFactory();
        CompareMode compareMode = this.database.getCompareMode();
        object = this.table.getColumns();
        this.rowFactory = rowFactory.createRowFactory(this.database, compareMode, this.database, (Typed[])object, indexType.isScan() ? null : indexColumnArray, true);
        RowFactory rowFactory2 = n2 > 0 ? (indexColumnArray == null || n2 == indexColumnArray.length ? this.rowFactory : rowFactory.createRowFactory(this.database, compareMode, this.database, (Typed[])object, Arrays.copyOf(indexColumnArray, n2), true)) : null;
        this.uniqueRowFactory = rowFactory2;
    }

    @Override
    public final int getType() {
        return 1;
    }

    @Override
    public void removeChildrenAndResources(SessionLocal sessionLocal) {
        this.table.removeIndex(this);
        this.remove(sessionLocal);
        this.database.removeMeta(sessionLocal, this.getId());
    }

    @Override
    public final boolean isHidden() {
        return this.table.isHidden();
    }

    @Override
    public String getCreateSQLForCopy(Table table, String string) {
        StringBuilder stringBuilder = new StringBuilder("CREATE ");
        stringBuilder.append(this.indexType.getSQL(true));
        stringBuilder.append(' ');
        if (this.table.isHidden()) {
            stringBuilder.append("IF NOT EXISTS ");
        }
        stringBuilder.append(string);
        stringBuilder.append(" ON ");
        table.getSQL(stringBuilder, 0);
        if (this.comment != null) {
            stringBuilder.append(" COMMENT ");
            StringUtils.quoteStringSQL(stringBuilder, this.comment);
        }
        return this.getColumnListSQL(stringBuilder, 0).toString();
    }

    private StringBuilder getColumnListSQL(StringBuilder stringBuilder, int n) {
        stringBuilder.append('(');
        int n2 = this.indexColumns.length;
        if (this.uniqueColumnColumn > 0 && this.uniqueColumnColumn < n2) {
            IndexColumn.writeColumns(stringBuilder, this.indexColumns, 0, this.uniqueColumnColumn, n).append(") INCLUDE(");
            IndexColumn.writeColumns(stringBuilder, this.indexColumns, this.uniqueColumnColumn, n2, n);
        } else {
            IndexColumn.writeColumns(stringBuilder, this.indexColumns, 0, n2, n);
        }
        return stringBuilder.append(')');
    }

    @Override
    public String getCreateSQL() {
        return this.getCreateSQLForCopy(this.table, this.getSQL(0));
    }

    public String getPlanSQL() {
        return this.getSQL(11);
    }

    public abstract void close(SessionLocal var1);

    public abstract void add(SessionLocal var1, Row var2);

    public abstract void remove(SessionLocal var1, Row var2);

    public void update(SessionLocal sessionLocal, Row row, Row row2) {
        this.remove(sessionLocal, row);
        this.add(sessionLocal, row2);
    }

    public boolean isFindUsingFullTableScan() {
        return false;
    }

    public abstract Cursor find(SessionLocal var1, SearchRow var2, SearchRow var3);

    public abstract double getCost(SessionLocal var1, int[] var2, TableFilter[] var3, int var4, SortOrder var5, AllColumnsForPlan var6);

    public abstract void remove(SessionLocal var1);

    public abstract void truncate(SessionLocal var1);

    public boolean canGetFirstOrLast() {
        return false;
    }

    public boolean canFindNext() {
        return false;
    }

    public Cursor findNext(SessionLocal sessionLocal, SearchRow searchRow, SearchRow searchRow2) {
        throw DbException.getInternalError(this.toString());
    }

    public Cursor findFirstOrLast(SessionLocal sessionLocal, boolean bl) {
        throw DbException.getInternalError(this.toString());
    }

    public abstract boolean needRebuild();

    public abstract long getRowCount(SessionLocal var1);

    public abstract long getRowCountApproximation(SessionLocal var1);

    public long getDiskSpaceUsed() {
        return 0L;
    }

    public final int compareRows(SearchRow searchRow, SearchRow searchRow2) {
        if (searchRow == searchRow2) {
            return 0;
        }
        int n = this.indexColumns.length;
        for (int i = 0; i < n; ++i) {
            int n2 = this.columnIds[i];
            Value value = searchRow.getValue(n2);
            Value value2 = searchRow2.getValue(n2);
            if (value == null || value2 == null) {
                return 0;
            }
            int n3 = this.compareValues(value, value2, this.indexColumns[i].sortType);
            if (n3 == 0) continue;
            return n3;
        }
        return 0;
    }

    private int compareValues(Value value, Value value2, int n) {
        boolean bl;
        if (value == value2) {
            return 0;
        }
        boolean bl2 = bl = value == ValueNull.INSTANCE;
        if (bl || value2 == ValueNull.INSTANCE) {
            return this.table.getDatabase().getDefaultNullOrdering().compareNull(bl, n);
        }
        int n2 = this.table.compareValues(this.database, value, value2);
        if ((n & 1) != 0) {
            n2 = -n2;
        }
        return n2;
    }

    public int getColumnIndex(Column column) {
        int n = this.columns.length;
        for (int i = 0; i < n; ++i) {
            if (!this.columns[i].equals(column)) continue;
            return i;
        }
        return -1;
    }

    public boolean isFirstColumn(Column column) {
        return column.equals(this.columns[0]);
    }

    public final IndexColumn[] getIndexColumns() {
        return this.indexColumns;
    }

    public final Column[] getColumns() {
        return this.columns;
    }

    public final int getUniqueColumnCount() {
        return this.uniqueColumnColumn;
    }

    public final IndexType getIndexType() {
        return this.indexType;
    }

    public Table getTable() {
        return this.table;
    }

    public Row getRow(SessionLocal sessionLocal, long l) {
        throw DbException.getUnsupportedException(this.toString());
    }

    public boolean isRowIdIndex() {
        return false;
    }

    public boolean canScan() {
        return true;
    }

    public DbException getDuplicateKeyException(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        this.getSQL(stringBuilder, 3).append(" ON ");
        this.table.getSQL(stringBuilder, 3);
        this.getColumnListSQL(stringBuilder, 3);
        if (string != null) {
            stringBuilder.append(" VALUES ").append(string);
        }
        DbException dbException = DbException.get(23505, stringBuilder.toString());
        dbException.setSource(this);
        return dbException;
    }

    protected StringBuilder getDuplicatePrimaryKeyMessage(int n) {
        StringBuilder stringBuilder = new StringBuilder("PRIMARY KEY ON ");
        this.table.getSQL(stringBuilder, 3);
        if (n >= 0 && n < this.indexColumns.length) {
            stringBuilder.append('(');
            this.indexColumns[n].getSQL(stringBuilder, 3).append(')');
        }
        return stringBuilder;
    }

    protected final long getCostRangeIndex(int[] nArray, long l, TableFilter[] tableFilterArray, int n, SortOrder sortOrder, boolean bl, AllColumnsForPlan allColumnsForPlan) {
        int n2;
        ArrayList<Column> arrayList;
        int n3;
        boolean bl2;
        int n4 = 0;
        long l2 = l += 1000L;
        if (nArray != null) {
            int n5 = 0;
            int n6 = this.columns.length;
            bl2 = false;
            while (n5 < n6) {
                int n7;
                if (((n7 = nArray[n3 = ((Column)((Object)(arrayList = this.columns[n5++]))).getColumnId()]) & 1) == 1) {
                    if (n5 > 0 && n5 == this.uniqueColumnColumn) {
                        l2 = 3L;
                        break;
                    }
                    long l3 = l * (long)(n4 = 100 - (100 - n4) * (100 - ((Column)((Object)arrayList)).getSelectivity()) / 100) / 100L;
                    if (l3 <= 0L) {
                        l3 = 1L;
                    }
                    l2 = 2L + Math.max(l / l3, 1L);
                    continue;
                }
                if ((n7 & 6) == 6) {
                    l2 = 2L + l2 / 4L;
                    bl2 = true;
                    break;
                }
                if ((n7 & 2) == 2) {
                    l2 = 2L + l2 / 3L;
                    bl2 = true;
                    break;
                }
                if ((n7 & 4) == 4) {
                    l2 /= 3L;
                    bl2 = true;
                    break;
                }
                if (n7 != 0) break;
                --n5;
                break;
            }
            if (bl2) {
                while (n5 < n6 && nArray[this.columns[n5].getColumnId()] != 0) {
                    ++n5;
                    --l2;
                }
            }
            l2 += (long)(n6 - n5);
        }
        long l4 = 0L;
        if (sortOrder != null) {
            l4 = 100L + l / 10L;
        }
        if (sortOrder != null && !bl) {
            bl2 = true;
            int n8 = 0;
            int[] nArray2 = sortOrder.getSortTypesWithNullOrdering();
            TableFilter tableFilter = tableFilterArray == null ? null : tableFilterArray[n];
            n2 = nArray2.length;
            for (int i = 0; i < n2 && i < this.indexColumns.length; ++i) {
                Column[] columnArray = sortOrder.getColumn(i, tableFilter);
                if (columnArray == null) {
                    bl2 = false;
                    break;
                }
                IndexColumn indexColumn = this.indexColumns[i];
                if (!columnArray.equals(indexColumn.column)) {
                    bl2 = false;
                    break;
                }
                int n9 = nArray2[i];
                if (n9 != indexColumn.sortType) {
                    bl2 = false;
                    break;
                }
                ++n8;
            }
            if (bl2) {
                l4 = 100 - n8;
            }
        }
        if (!bl && allColumnsForPlan != null) {
            bl2 = false;
            arrayList = allColumnsForPlan.get(this.getTable());
            if (arrayList != null) {
                n3 = this.table.getMainIndexColumn();
                block3: for (Column column : arrayList) {
                    n2 = column.getColumnId();
                    if (n2 == -1 || n2 == n3) continue;
                    for (Column column2 : this.columns) {
                        if (column == column2) continue block3;
                    }
                    bl2 = true;
                    break;
                }
            }
        } else {
            bl2 = true;
        }
        long l5 = bl ? l2 + l4 + 20L : (bl2 ? l2 + l2 + l4 + 20L : l2 + l4 + (long)this.columns.length);
        return l5;
    }

    public final boolean needsUniqueCheck(SearchRow searchRow) {
        NullsDistinct nullsDistinct = this.indexType.getEffectiveNullsDistinct();
        return nullsDistinct != null && (nullsDistinct == NullsDistinct.NOT_DISTINCT || this.needsUniqueCheck(searchRow, nullsDistinct == NullsDistinct.DISTINCT));
    }

    private boolean needsUniqueCheck(SearchRow searchRow, boolean bl) {
        if (bl) {
            for (int i = 0; i < this.uniqueColumnColumn; ++i) {
                int n = this.columnIds[i];
                if (searchRow.getValue(n) != ValueNull.INSTANCE) continue;
                return false;
            }
            return true;
        }
        for (int i = 0; i < this.uniqueColumnColumn; ++i) {
            int n = this.columnIds[i];
            if (searchRow.getValue(n) == ValueNull.INSTANCE) continue;
            return true;
        }
        return false;
    }

    public RowFactory getRowFactory() {
        return this.rowFactory;
    }

    public RowFactory getUniqueRowFactory() {
        return this.uniqueRowFactory;
    }
}

