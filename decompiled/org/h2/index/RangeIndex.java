/*
 * Decompiled with CFR 0.152.
 */
package org.h2.index;

import org.h2.command.query.AllColumnsForPlan;
import org.h2.engine.SessionLocal;
import org.h2.index.Cursor;
import org.h2.index.RangeCursor;
import org.h2.index.SingleRowCursor;
import org.h2.index.VirtualTableIndex;
import org.h2.message.DbException;
import org.h2.result.Row;
import org.h2.result.SearchRow;
import org.h2.result.SortOrder;
import org.h2.table.IndexColumn;
import org.h2.table.RangeTable;
import org.h2.table.TableFilter;
import org.h2.value.Value;
import org.h2.value.ValueBigint;

public class RangeIndex
extends VirtualTableIndex {
    private final RangeTable rangeTable;

    public RangeIndex(RangeTable rangeTable, IndexColumn[] indexColumnArray) {
        super(rangeTable, "RANGE_INDEX", indexColumnArray);
        this.rangeTable = rangeTable;
    }

    @Override
    public Cursor find(SessionLocal sessionLocal, SearchRow searchRow, SearchRow searchRow2) {
        long l;
        long l2 = this.rangeTable.getMin(sessionLocal);
        long l3 = this.rangeTable.getMax(sessionLocal);
        long l4 = this.rangeTable.getStep(sessionLocal);
        if (l4 == 0L) {
            throw DbException.get(90142);
        }
        if (searchRow != null) {
            try {
                l = searchRow.getValue(0).getLong();
                if (l4 > 0L) {
                    if (l > l2) {
                        l2 += (l - l2 + l4 - 1L) / l4 * l4;
                    }
                } else if (l > l3) {
                    l3 = l;
                }
            }
            catch (DbException dbException) {
                // empty catch block
            }
        }
        if (searchRow2 != null) {
            try {
                l = searchRow2.getValue(0).getLong();
                if (l4 > 0L) {
                    if (l < l3) {
                        l3 = l;
                    }
                } else if (l < l2) {
                    l2 -= (l2 - l - l4 - 1L) / l4 * l4;
                }
            }
            catch (DbException dbException) {
                // empty catch block
            }
        }
        return new RangeCursor(l2, l3, l4);
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
        long l = this.rangeTable.getMin(sessionLocal);
        long l2 = this.rangeTable.getMax(sessionLocal);
        long l3 = this.rangeTable.getStep(sessionLocal);
        if (l3 == 0L) {
            throw DbException.get(90142);
        }
        return new SingleRowCursor((l3 > 0L ? l <= l2 : l >= l2) ? Row.get(new Value[]{ValueBigint.get(bl ^ l >= l2 ? l : l2)}, 1) : null);
    }

    @Override
    public String getPlanSQL() {
        return "range index";
    }
}

