/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.db;

import java.util.List;
import org.h2.command.query.AllColumnsForPlan;
import org.h2.engine.SessionLocal;
import org.h2.index.Cursor;
import org.h2.index.IndexType;
import org.h2.message.DbException;
import org.h2.mvstore.MVMap;
import org.h2.mvstore.db.MVIndex;
import org.h2.mvstore.db.MVPrimaryIndex;
import org.h2.mvstore.db.MVTable;
import org.h2.result.Row;
import org.h2.result.RowFactory;
import org.h2.result.SearchRow;
import org.h2.result.SortOrder;
import org.h2.table.Column;
import org.h2.table.IndexColumn;
import org.h2.table.TableFilter;
import org.h2.value.VersionedValue;

public class MVDelegateIndex
extends MVIndex<Long, SearchRow> {
    private final MVPrimaryIndex mainIndex;

    public MVDelegateIndex(MVTable mVTable, int n, String string, MVPrimaryIndex mVPrimaryIndex, IndexType indexType) {
        super(mVTable, n, string, IndexColumn.wrap(new Column[]{mVTable.getColumn(mVPrimaryIndex.getMainIndexColumn())}), 1, indexType);
        this.mainIndex = mVPrimaryIndex;
        if (n < 0) {
            throw DbException.getInternalError(string);
        }
    }

    @Override
    public RowFactory getRowFactory() {
        return this.mainIndex.getRowFactory();
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
    public MVMap<Long, VersionedValue<SearchRow>> getMVMap() {
        return this.mainIndex.getMVMap();
    }

    @Override
    public void add(SessionLocal sessionLocal, Row row) {
    }

    @Override
    public Row getRow(SessionLocal sessionLocal, long l) {
        return this.mainIndex.getRow(sessionLocal, l);
    }

    @Override
    public boolean isRowIdIndex() {
        return true;
    }

    @Override
    public boolean canGetFirstOrLast() {
        return true;
    }

    @Override
    public void close(SessionLocal sessionLocal) {
    }

    @Override
    public Cursor find(SessionLocal sessionLocal, SearchRow searchRow, SearchRow searchRow2) {
        return this.mainIndex.find(sessionLocal, searchRow, searchRow2);
    }

    @Override
    public Cursor findFirstOrLast(SessionLocal sessionLocal, boolean bl) {
        return this.mainIndex.findFirstOrLast(sessionLocal, bl);
    }

    @Override
    public int getColumnIndex(Column column) {
        if (column.getColumnId() == this.mainIndex.getMainIndexColumn()) {
            return 0;
        }
        return -1;
    }

    @Override
    public boolean isFirstColumn(Column column) {
        return this.getColumnIndex(column) == 0;
    }

    @Override
    public double getCost(SessionLocal sessionLocal, int[] nArray, TableFilter[] tableFilterArray, int n, SortOrder sortOrder, AllColumnsForPlan allColumnsForPlan) {
        return 10L * this.getCostRangeIndex(nArray, this.mainIndex.getRowCountApproximation(sessionLocal), tableFilterArray, n, sortOrder, true, allColumnsForPlan);
    }

    @Override
    public boolean needRebuild() {
        return false;
    }

    @Override
    public void remove(SessionLocal sessionLocal, Row row) {
    }

    @Override
    public void update(SessionLocal sessionLocal, Row row, Row row2) {
    }

    @Override
    public void remove(SessionLocal sessionLocal) {
        this.mainIndex.setMainIndexColumn(-1);
    }

    @Override
    public void truncate(SessionLocal sessionLocal) {
    }

    @Override
    public long getRowCount(SessionLocal sessionLocal) {
        return this.mainIndex.getRowCount(sessionLocal);
    }

    @Override
    public long getRowCountApproximation(SessionLocal sessionLocal) {
        return this.mainIndex.getRowCountApproximation(sessionLocal);
    }
}

