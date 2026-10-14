/*
 * Decompiled with CFR 0.152.
 */
package org.h2.table;

import java.util.ArrayList;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.index.Index;
import org.h2.index.RangeIndex;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.table.Column;
import org.h2.table.IndexColumn;
import org.h2.table.TableType;
import org.h2.table.VirtualTable;
import org.h2.value.TypeInfo;

public class RangeTable
extends VirtualTable {
    public static final String NAME = "SYSTEM_RANGE";
    public static final String ALIAS = "GENERATE_SERIES";
    private Expression min;
    private Expression max;
    private Expression step;
    private boolean optimized;
    private final RangeIndex index;

    public RangeTable(Schema schema, Expression expression, Expression expression2) {
        super(schema, 0, NAME);
        this.min = expression;
        this.max = expression2;
        Column[] columnArray = new Column[]{new Column("X", TypeInfo.TYPE_BIGINT)};
        this.setColumns(columnArray);
        this.index = new RangeIndex(this, IndexColumn.wrap(columnArray));
    }

    public RangeTable(Schema schema, Expression expression, Expression expression2, Expression expression3) {
        this(schema, expression, expression2);
        this.step = expression3;
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        stringBuilder.append(NAME).append('(');
        this.min.getUnenclosedSQL(stringBuilder, n).append(", ");
        this.max.getUnenclosedSQL(stringBuilder, n);
        if (this.step != null) {
            this.step.getUnenclosedSQL(stringBuilder.append(", "), n);
        }
        return stringBuilder.append(')');
    }

    @Override
    public boolean canGetRowCount(SessionLocal sessionLocal) {
        return true;
    }

    @Override
    public long getRowCount(SessionLocal sessionLocal) {
        long l = this.getStep(sessionLocal);
        if (l == 0L) {
            throw DbException.get(90142);
        }
        long l2 = this.getMax(sessionLocal) - this.getMin(sessionLocal);
        if (l > 0L ? l2 < 0L : l2 > 0L) {
            return 0L;
        }
        return l2 / l + 1L;
    }

    @Override
    public TableType getTableType() {
        return TableType.SYSTEM_TABLE;
    }

    @Override
    public Index getScanIndex(SessionLocal sessionLocal) {
        return this.index;
    }

    @Override
    public ArrayList<Index> getIndexes() {
        ArrayList<Index> arrayList = new ArrayList<Index>(2);
        arrayList.add(this.index);
        arrayList.add(this.index);
        return arrayList;
    }

    public long getMin(SessionLocal sessionLocal) {
        this.optimize(sessionLocal);
        return this.min.getValue(sessionLocal).getLong();
    }

    public long getMax(SessionLocal sessionLocal) {
        this.optimize(sessionLocal);
        return this.max.getValue(sessionLocal).getLong();
    }

    public long getStep(SessionLocal sessionLocal) {
        this.optimize(sessionLocal);
        if (this.step == null) {
            return 1L;
        }
        return this.step.getValue(sessionLocal).getLong();
    }

    private void optimize(SessionLocal sessionLocal) {
        if (!this.optimized) {
            this.min = this.min.optimize(sessionLocal);
            this.max = this.max.optimize(sessionLocal);
            if (this.step != null) {
                this.step = this.step.optimize(sessionLocal);
            }
            this.optimized = true;
        }
    }

    @Override
    public long getMaxDataModificationId() {
        return 0L;
    }

    @Override
    public long getRowCountApproximation(SessionLocal sessionLocal) {
        return 100L;
    }

    @Override
    public boolean isDeterministic() {
        return true;
    }
}

