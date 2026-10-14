/*
 * Decompiled with CFR 0.152.
 */
package org.h2.index;

import org.h2.command.query.AllColumnsForPlan;
import org.h2.engine.SessionLocal;
import org.h2.index.Cursor;
import org.h2.index.VirtualTableCursor;
import org.h2.index.VirtualTableIndex;
import org.h2.message.DbException;
import org.h2.result.SearchRow;
import org.h2.result.SortOrder;
import org.h2.table.FunctionTable;
import org.h2.table.IndexColumn;
import org.h2.table.TableFilter;
import org.h2.table.VirtualConstructedTable;

public class VirtualConstructedTableIndex
extends VirtualTableIndex {
    private final VirtualConstructedTable table;

    public VirtualConstructedTableIndex(VirtualConstructedTable virtualConstructedTable, IndexColumn[] indexColumnArray) {
        super(virtualConstructedTable, null, indexColumnArray);
        this.table = virtualConstructedTable;
    }

    @Override
    public boolean isFindUsingFullTableScan() {
        return true;
    }

    @Override
    public Cursor find(SessionLocal sessionLocal, SearchRow searchRow, SearchRow searchRow2) {
        return new VirtualTableCursor(this, searchRow, searchRow2, this.table.getResult(sessionLocal));
    }

    @Override
    public double getCost(SessionLocal sessionLocal, int[] nArray, TableFilter[] tableFilterArray, int n, SortOrder sortOrder, AllColumnsForPlan allColumnsForPlan) {
        if (nArray != null) {
            throw DbException.getUnsupportedException("Virtual table");
        }
        long l = this.table.canGetRowCount(sessionLocal) ? this.table.getRowCountApproximation(sessionLocal) : (long)this.database.getSettings().estimatedFunctionTableRows;
        return l * 10L;
    }

    @Override
    public String getPlanSQL() {
        return this.table instanceof FunctionTable ? "function" : "table scan";
    }

    @Override
    public boolean canScan() {
        return false;
    }
}

