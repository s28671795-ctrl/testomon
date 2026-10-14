/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.dml;

import java.util.HashSet;
import org.h2.command.Prepared;
import org.h2.command.dml.FilteredDataChangeStatement;
import org.h2.command.dml.Insert;
import org.h2.command.dml.SetClauseList;
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

public final class Update
extends FilteredDataChangeStatement {
    private SetClauseList setClauseList;
    private Insert onDuplicateKeyInsert;

    public Update(SessionLocal sessionLocal) {
        super(sessionLocal);
    }

    public void setSetClauseList(SetClauseList setClauseList) {
        this.setClauseList = setClauseList;
    }

    @Override
    public long update(ResultTarget resultTarget, DataChangeDeltaTable.ResultOption resultOption) {
        this.targetTableFilter.startQuery(this.session);
        this.targetTableFilter.reset();
        Table table = this.targetTableFilter.getTable();
        try (LocalResult localResult = LocalResult.forTable(this.session, table);){
            Value value;
            this.session.getUser().checkTableRight(table, 8);
            table.fire(this.session, 2, true);
            table.lock(this.session, 1);
            this.setCurrentRowNumber(0L);
            long l = 0L;
            long l2 = -1L;
            if (this.fetchExpr != null && ((value = this.fetchExpr.getValue(this.session)) == ValueNull.INSTANCE || (l2 = value.getLong()) < 0L)) {
                throw DbException.getInvalidValueException("FETCH", value);
            }
            while (this.nextRow(l2, l)) {
                value = this.targetTableFilter.get();
                if (table.isRowLockable()) {
                    Row row = table.lockRow(this.session, (Row)value, -1);
                    if (row == null) continue;
                    if (!((Row)value).hasSharedData(row)) {
                        value = row;
                        this.targetTableFilter.set((Row)value);
                        if (this.condition != null && !this.condition.getBooleanValue(this.session)) continue;
                    }
                }
                if (!this.setClauseList.prepareUpdate(table, this.session, resultTarget, resultOption, localResult, (Row)value, this.onDuplicateKeyInsert != null)) continue;
                ++l;
            }
            Update.doUpdate(this, this.session, table, localResult);
            table.fire(this.session, 2, false);
            long l3 = l;
            return l3;
        }
    }

    static void doUpdate(Prepared prepared, SessionLocal sessionLocal, Table table, LocalResult localResult) {
        localResult.done();
        table.updateRows(prepared, sessionLocal, localResult);
        if (table.fireRow()) {
            localResult.reset();
            while (localResult.next()) {
                Row row = localResult.currentRowForTable();
                localResult.next();
                Row row2 = localResult.currentRowForTable();
                table.fireAfterRow(sessionLocal, row, row2, false);
            }
        }
    }

    @Override
    public String getPlanSQL(int n) {
        StringBuilder stringBuilder = new StringBuilder("UPDATE ");
        this.targetTableFilter.getPlanSQL(stringBuilder, false, n);
        this.setClauseList.getSQL(stringBuilder, n);
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
        this.setClauseList.mapAndOptimize(this.session, this.targetTableFilter, null);
        TableFilter[] tableFilterArray = new TableFilter[]{this.targetTableFilter};
        PlanItem planItem = this.targetTableFilter.getBestPlanItem(this.session, tableFilterArray, 0, new AllColumnsForPlan(tableFilterArray));
        this.targetTableFilter.setPlanItem(planItem);
        this.targetTableFilter.prepare();
    }

    @Override
    public int getType() {
        return 68;
    }

    @Override
    public String getStatementName() {
        return "UPDATE";
    }

    @Override
    public void collectDependencies(HashSet<DbObject> hashSet) {
        ExpressionVisitor expressionVisitor = ExpressionVisitor.getDependenciesVisitor(hashSet);
        if (this.condition != null) {
            this.condition.isEverything(expressionVisitor);
        }
        this.setClauseList.isEverything(expressionVisitor);
    }

    public Insert getOnDuplicateKeyInsert() {
        return this.onDuplicateKeyInsert;
    }

    void setOnDuplicateKeyInsert(Insert insert) {
        this.onDuplicateKeyInsert = insert;
    }
}

