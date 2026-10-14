/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.dml;

import org.h2.command.dml.DataChangeStatement;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.table.Table;
import org.h2.table.TableFilter;

abstract class FilteredDataChangeStatement
extends DataChangeStatement {
    Expression condition;
    TableFilter targetTableFilter;
    Expression fetchExpr;

    FilteredDataChangeStatement(SessionLocal sessionLocal) {
        super(sessionLocal);
    }

    @Override
    public final Table getTable() {
        return this.targetTableFilter.getTable();
    }

    public final void setTableFilter(TableFilter tableFilter) {
        this.targetTableFilter = tableFilter;
    }

    public final TableFilter getTableFilter() {
        return this.targetTableFilter;
    }

    public final void setCondition(Expression expression) {
        this.condition = expression;
    }

    public final Expression getCondition() {
        return this.condition;
    }

    public void setFetch(Expression expression) {
        this.fetchExpr = expression;
    }

    final boolean nextRow(long l, long l2) {
        if (l < 0L || l2 < l) {
            while (this.targetTableFilter.next()) {
                this.setCurrentRowNumber(l2 + 1L);
                if (this.condition != null && !this.condition.getBooleanValue(this.session)) continue;
                return true;
            }
        }
        return false;
    }

    final void appendFilterCondition(StringBuilder stringBuilder, int n) {
        if (this.condition != null) {
            stringBuilder.append("\nWHERE ");
            this.condition.getUnenclosedSQL(stringBuilder, n);
        }
        if (this.fetchExpr != null) {
            stringBuilder.append("\nFETCH FIRST ");
            String string = this.fetchExpr.getSQL(n, 2);
            if ("1".equals(string)) {
                stringBuilder.append("ROW ONLY");
            } else {
                stringBuilder.append(string).append(" ROWS ONLY");
            }
        }
    }
}

