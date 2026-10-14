/*
 * Decompiled with CFR 0.152.
 */
package org.h2.index;

import java.util.ArrayList;
import org.h2.command.query.AllColumnsForPlan;
import org.h2.engine.SessionLocal;
import org.h2.index.Cursor;
import org.h2.index.Index;
import org.h2.index.IndexType;
import org.h2.index.MetaCursor;
import org.h2.message.DbException;
import org.h2.result.Row;
import org.h2.result.SearchRow;
import org.h2.result.SortOrder;
import org.h2.table.Column;
import org.h2.table.IndexColumn;
import org.h2.table.MetaTable;
import org.h2.table.TableFilter;

public class MetaIndex
extends Index {
    private final MetaTable meta;
    private final boolean scan;

    public MetaIndex(MetaTable metaTable, IndexColumn[] indexColumnArray, boolean bl) {
        super(metaTable, 0, null, indexColumnArray, 0, IndexType.createNonUnique(true));
        this.meta = metaTable;
        this.scan = bl;
    }

    @Override
    public void close(SessionLocal sessionLocal) {
    }

    @Override
    public void add(SessionLocal sessionLocal, Row row) {
        throw DbException.getUnsupportedException("META");
    }

    @Override
    public void remove(SessionLocal sessionLocal, Row row) {
        throw DbException.getUnsupportedException("META");
    }

    @Override
    public Cursor find(SessionLocal sessionLocal, SearchRow searchRow, SearchRow searchRow2) {
        ArrayList<Row> arrayList = this.meta.generateRows(sessionLocal, searchRow, searchRow2);
        return new MetaCursor(arrayList);
    }

    @Override
    public double getCost(SessionLocal sessionLocal, int[] nArray, TableFilter[] tableFilterArray, int n, SortOrder sortOrder, AllColumnsForPlan allColumnsForPlan) {
        if (this.scan) {
            return 10000.0;
        }
        return this.getCostRangeIndex(nArray, 1000L, tableFilterArray, n, sortOrder, false, allColumnsForPlan);
    }

    @Override
    public void truncate(SessionLocal sessionLocal) {
        throw DbException.getUnsupportedException("META");
    }

    @Override
    public void remove(SessionLocal sessionLocal) {
        throw DbException.getUnsupportedException("META");
    }

    @Override
    public int getColumnIndex(Column column) {
        if (this.scan) {
            return -1;
        }
        return super.getColumnIndex(column);
    }

    @Override
    public boolean isFirstColumn(Column column) {
        if (this.scan) {
            return false;
        }
        return super.isFirstColumn(column);
    }

    @Override
    public void checkRename() {
        throw DbException.getUnsupportedException("META");
    }

    @Override
    public boolean needRebuild() {
        return false;
    }

    @Override
    public String getCreateSQL() {
        return null;
    }

    @Override
    public long getRowCount(SessionLocal sessionLocal) {
        return 1000L;
    }

    @Override
    public long getRowCountApproximation(SessionLocal sessionLocal) {
        return 1000L;
    }

    @Override
    public long getDiskSpaceUsed() {
        return this.meta.getDiskSpaceUsed();
    }

    @Override
    public String getPlanSQL() {
        return "meta";
    }
}

