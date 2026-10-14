/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.dml;

import java.util.HashSet;
import org.h2.command.dml.FilteredDataChangeStatement;
import org.h2.command.query.AllColumnsForPlan;
import org.h2.engine.DbObject;
import org.h2.engine.SessionLocal;
import org.h2.expression.ExpressionVisitor;
import org.h2.message.DbException;
import org.h2.result.LocalResult;
import org.h2.result.ResultTarget;
import org.h2.result.Row;
import org.h2.table.DataChangeDeltaTable;
import org.h2.table.PlanItem;
import org.h2.table.Table;
import org.h2.table.TableFilter;
import org.h2.value.Value;
import org.h2.value.ValueNull;

public final class Delete
extends FilteredDataChangeStatement {
    public Delete(SessionLocal sessionLocal) {
        super(sessionLocal);
    }

    @Override
    public long update(ResultTarget resultTarget, DataChangeDeltaTable.ResultOption resultOption) {
        Object object;
        this.targetTableFilter.startQuery(this.session);
        this.targetTableFilter.reset();
        Table table = this.targetTableFilter.getTable();
        this.session.getUser().checkTableRight(table, 2);
        table.fire(this.session, 4, true);
        table.lock(this.session, 1);
        long l = -1L;
        if (this.fetchExpr != null && ((object = this.fetchExpr.getValue(this.session)) == ValueNull.INSTANCE || (l = ((Value)object).getLong()) < 0L)) {
            throw DbException.getInvalidValueException("FETCH", object);
        }
        object = LocalResult.forTable(this.session, table);
        Throwable throwable = null;
        try {
            this.setCurrentRowNumber(0L);
            long l2 = 0L;
            while (this.nextRow(l, l2)) {
                Row row = this.targetTableFilter.get();
                if (table.isRowLockable()) {
                    Row row2 = table.lockRow(this.session, row, -1);
                    if (row2 == null) continue;
                    if (!row.hasSharedData(row2)) {
                        row = row2;
                        this.targetTableFilter.set(row);
                        if (this.condition != null && !this.condition.getBooleanValue(this.session)) continue;
                    }
                }
                if (resultOption == DataChangeDeltaTable.ResultOption.OLD) {
                    resultTarget.addRow(row.getValueList());
                }
                if (!table.fireRow() || !table.fireBeforeRow(this.session, row, null)) {
                    ((LocalResult)object).addRowForTable(row);
                }
                ++l2;
            }
            ((LocalResult)object).done();
            long l3 = 0L;
            while (((LocalResult)object).next()) {
                if ((++l3 & 0x7FL) == 0L) {
                    this.checkCanceled();
                }
                Row row = ((LocalResult)object).currentRowForTable();
                table.removeRow(this.session, row);
            }
            if (table.fireRow()) {
                ((LocalResult)object).reset();
                while (((LocalResult)object).next()) {
                    table.fireAfterRow(this.session, ((LocalResult)object).currentRowForTable(), null, false);
                }
            }
            table.fire(this.session, 4, false);
            long l4 = l2;
            return l4;
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            if (object != null) {
                if (throwable != null) {
                    try {
                        ((LocalResult)object).close();
                    }
                    catch (Throwable throwable3) {
                        throwable.addSuppressed(throwable3);
                    }
                } else {
                    ((LocalResult)object).close();
                }
            }
        }
    }

    @Override
    public String getPlanSQL(int n) {
        StringBuilder stringBuilder = new StringBuilder("DELETE FROM ");
        this.targetTableFilter.getPlanSQL(stringBuilder, false, n);
        this.appendFilterCondition(stringBuilder, n);
        return stringBuilder.toString();
    }

    @Override
    void doPrepare() {
        if (this.condition != null) {
            this.condition.mapColumns(this.targetTableFilter, 0, 0);
            this.condition = this.condition.optimizeCondition(this.session);
            if (this.condition != null) {
                this.condition.createIndexConditions(this.session, this.targetTableFilter);
            }
        }
        TableFilter[] tableFilterArray = new TableFilter[]{this.targetTableFilter};
        PlanItem planItem = this.targetTableFilter.getBestPlanItem(this.session, tableFilterArray, 0, new AllColumnsForPlan(tableFilterArray));
        this.targetTableFilter.setPlanItem(planItem);
        this.targetTableFilter.prepare();
    }

    @Override
    public int getType() {
        return 58;
    }

    @Override
    public String getStatementName() {
        return "DELETE";
    }

    @Override
    public void collectDependencies(HashSet<DbObject> hashSet) {
        ExpressionVisitor expressionVisitor = ExpressionVisitor.getDependenciesVisitor(hashSet);
        if (this.condition != null) {
            this.condition.isEverything(expressionVisitor);
        }
    }
}

