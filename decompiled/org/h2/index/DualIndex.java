/*
 * Decompiled with CFR 0.152.
 */
package org.h2.index;

import org.h2.command.query.AllColumnsForPlan;
import org.h2.engine.SessionLocal;
import org.h2.index.Cursor;
import org.h2.index.DualCursor;
import org.h2.index.SingleRowCursor;
import org.h2.index.VirtualTableIndex;
import org.h2.result.Row;
import org.h2.result.SearchRow;
import org.h2.result.SortOrder;
import org.h2.table.DualTable;
import org.h2.table.IndexColumn;
import org.h2.table.TableFilter;
import org.h2.value.Value;

public class DualIndex
extends VirtualTableIndex {
    public DualIndex(DualTable dualTable) {
        super(dualTable, "DUAL_INDEX", new IndexColumn[0]);
    }

    @Override
    public Cursor find(SessionLocal sessionLocal, SearchRow searchRow, SearchRow searchRow2) {
        return new DualCursor();
    }

    @Override
    public double getCost(SessionLocal sessionLocal, int[] nArray, TableFilter[] tableFilterArray, int n, SortOrder sortOrder, AllColumnsForPlan allColumnsForPlan) {
        return 1.0;
    }

    @Override
    public String getCreateSQL() {
        return null;
    }

    @Override
    public boolean canGetFirstOrLast() {
        return true;
    }

    @Override
    public Cursor findFirstOrLast(SessionLocal sessionLocal, boolean bl) {
        return new SingleRowCursor(Row.get(Value.EMPTY_VALUES, 1));
    }

    @Override
    public String getPlanSQL() {
        return "dual index";
    }
}

