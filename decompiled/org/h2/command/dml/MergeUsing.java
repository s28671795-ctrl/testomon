/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.dml;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import org.h2.command.Prepared;
import org.h2.command.dml.DataChangeStatement;
import org.h2.command.dml.SetClauseList;
import org.h2.command.dml.Update;
import org.h2.command.query.AllColumnsForPlan;
import org.h2.engine.DbObject;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.Parameter;
import org.h2.expression.ValueExpression;
import org.h2.message.DbException;
import org.h2.result.LocalResult;
import org.h2.result.ResultTarget;
import org.h2.result.Row;
import org.h2.table.Column;
import org.h2.table.DataChangeDeltaTable;
import org.h2.table.PlanItem;
import org.h2.table.Table;
import org.h2.table.TableFilter;
import org.h2.util.HasSQL;
import org.h2.util.Utils;
import org.h2.value.Value;

public final class MergeUsing
extends DataChangeStatement {
    TableFilter targetTableFilter;
    TableFilter sourceTableFilter;
    Expression onCondition;
    private ArrayList<When> when = Utils.newSmallArrayList();
    private final HashSet<Long> targetRowidsRemembered = new HashSet();

    public MergeUsing(SessionLocal sessionLocal, TableFilter tableFilter) {
        super(sessionLocal);
        this.targetTableFilter = tableFilter;
    }

    @Override
    public long update(ResultTarget resultTarget, DataChangeDeltaTable.ResultOption resultOption) {
        boolean bl;
        long l = 0L;
        this.targetRowidsRemembered.clear();
        this.checkRights();
        this.setCurrentRowNumber(0L);
        this.sourceTableFilter.startQuery(this.session);
        this.sourceTableFilter.reset();
        Table table = this.targetTableFilter.getTable();
        table.fire(this.session, this.evaluateTriggerMasks(), true);
        table.lock(this.session, 1);
        this.setCurrentRowNumber(0L);
        long l2 = 0L;
        Row row = null;
        Row row2 = null;
        boolean bl2 = bl = table.getRowIdColumn() != null;
        while (this.sourceTableFilter.next()) {
            Row row3 = this.sourceTableFilter.get();
            if (row2 != null) {
                if (row3 != row2) {
                    Row row4 = this.targetTableFilter.get();
                    this.sourceTableFilter.set(row2);
                    this.targetTableFilter.set(table.getNullRow());
                    l += (long)this.merge(true, resultTarget, resultOption);
                    this.sourceTableFilter.set(row3);
                    this.targetTableFilter.set(row4);
                    ++l2;
                }
                row2 = null;
            }
            this.setCurrentRowNumber(l2 + 1L);
            boolean bl3 = this.targetTableFilter.isNullRow();
            if (!bl3) {
                long l3;
                Row row5 = this.targetTableFilter.get();
                if (table.isRowLockable()) {
                    Row row6 = table.lockRow(this.session, row5, -1);
                    if (row6 == null) {
                        if (row == row3) continue;
                        row2 = row3;
                        continue;
                    }
                    if (!row5.hasSharedData(row6)) {
                        row5 = row6;
                        this.targetTableFilter.set(row5);
                        if (!this.onCondition.getBooleanValue(this.session)) {
                            if (row == row3) continue;
                            row2 = row3;
                            continue;
                        }
                    }
                }
                if (bl && !this.targetRowidsRemembered.add(l3 = row5.getKey())) {
                    throw DbException.get(23505, "Merge using ON column expression, duplicate _ROWID_ target record already processed:_ROWID_=" + l3 + ":in:" + this.targetTableFilter.getTable());
                }
            }
            l += (long)this.merge(bl3, resultTarget, resultOption);
            ++l2;
            row = row3;
        }
        if (row2 != null) {
            this.sourceTableFilter.set(row2);
            this.targetTableFilter.set(table.getNullRow());
            l += (long)this.merge(true, resultTarget, resultOption);
        }
        this.targetRowidsRemembered.clear();
        table.fire(this.session, this.evaluateTriggerMasks(), false);
        return l;
    }

    private int merge(boolean bl, ResultTarget resultTarget, DataChangeDeltaTable.ResultOption resultOption) {
        for (When when : this.when) {
            Expression expression;
            if (when.getClass() == WhenNotMatched.class != bl || (expression = when.andCondition) != null && !expression.getBooleanValue(this.session)) continue;
            when.merge(this.session, resultTarget, resultOption);
            return 1;
        }
        return 0;
    }

    private int evaluateTriggerMasks() {
        int n = 0;
        for (When when : this.when) {
            n |= when.evaluateTriggerMasks();
        }
        return n;
    }

    private void checkRights() {
        for (When when : this.when) {
            when.checkRights();
        }
        this.session.getUser().checkTableRight(this.targetTableFilter.getTable(), 1);
        this.session.getUser().checkTableRight(this.sourceTableFilter.getTable(), 1);
    }

    @Override
    public String getPlanSQL(int n) {
        StringBuilder stringBuilder = new StringBuilder("MERGE INTO ");
        this.targetTableFilter.getPlanSQL(stringBuilder, false, n);
        this.sourceTableFilter.getPlanSQL(stringBuilder.append('\n').append("USING "), false, n);
        this.onCondition.getSQL(stringBuilder.append('\n').append("ON "), n);
        for (When when : this.when) {
            when.getSQL(stringBuilder.append('\n'), n);
        }
        return stringBuilder.toString();
    }

    @Override
    void doPrepare() {
        this.onCondition.addFilterConditions(this.sourceTableFilter);
        this.onCondition.addFilterConditions(this.targetTableFilter);
        this.onCondition.mapColumns(this.sourceTableFilter, 0, 0);
        this.onCondition.mapColumns(this.targetTableFilter, 0, 0);
        this.onCondition = this.onCondition.optimize(this.session);
        this.onCondition.createIndexConditions(this.session, this.targetTableFilter);
        TableFilter[] tableFilterArray = new TableFilter[]{this.sourceTableFilter, this.targetTableFilter};
        this.sourceTableFilter.addJoin(this.targetTableFilter, true, this.onCondition);
        PlanItem planItem = this.sourceTableFilter.getBestPlanItem(this.session, tableFilterArray, 0, new AllColumnsForPlan(tableFilterArray));
        this.sourceTableFilter.setPlanItem(planItem);
        this.sourceTableFilter.prepare();
        boolean bl = false;
        boolean bl2 = false;
        Iterator<When> iterator2 = this.when.iterator();
        while (iterator2.hasNext()) {
            When when = iterator2.next();
            if (!when.prepare(this.session)) {
                iterator2.remove();
                continue;
            }
            if (when.getClass() == WhenNotMatched.class) {
                if (bl) {
                    iterator2.remove();
                    continue;
                }
                if (when.andCondition != null) continue;
                bl = true;
                continue;
            }
            if (bl2) {
                iterator2.remove();
                continue;
            }
            if (when.andCondition != null) continue;
            bl2 = true;
        }
    }

    public void setSourceTableFilter(TableFilter tableFilter) {
        this.sourceTableFilter = tableFilter;
    }

    public TableFilter getSourceTableFilter() {
        return this.sourceTableFilter;
    }

    public void setOnCondition(Expression expression) {
        this.onCondition = expression;
    }

    public Expression getOnCondition() {
        return this.onCondition;
    }

    public ArrayList<When> getWhen() {
        return this.when;
    }

    public void addWhen(When when) {
        this.when.add(when);
    }

    @Override
    public Table getTable() {
        return this.targetTableFilter.getTable();
    }

    public void setTargetTableFilter(TableFilter tableFilter) {
        this.targetTableFilter = tableFilter;
    }

    public TableFilter getTargetTableFilter() {
        return this.targetTableFilter;
    }

    @Override
    public int getType() {
        return 62;
    }

    @Override
    public String getStatementName() {
        return "MERGE";
    }

    @Override
    public void collectDependencies(HashSet<DbObject> hashSet) {
        hashSet.add(this.targetTableFilter.getTable());
        hashSet.add(this.sourceTableFilter.getTable());
        ExpressionVisitor expressionVisitor = ExpressionVisitor.getDependenciesVisitor(hashSet);
        for (When when : this.when) {
            when.collectDependencies(expressionVisitor);
        }
        this.onCondition.isEverything(expressionVisitor);
    }

    public final class WhenNotMatched
    extends When {
        private Column[] columns;
        private final Boolean overridingSystem;
        private final Expression[] values;

        public WhenNotMatched(Column[] columnArray, Boolean bl, Expression[] expressionArray) {
            this.columns = columnArray;
            this.overridingSystem = bl;
            this.values = expressionArray;
        }

        @Override
        void merge(SessionLocal sessionLocal, ResultTarget resultTarget, DataChangeDeltaTable.ResultOption resultOption) {
            Table table = MergeUsing.this.targetTableFilter.getTable();
            Row row = table.getTemplateRow();
            Expression[] expressionArray = this.values;
            int n = this.columns.length;
            for (int i = 0; i < n; ++i) {
                Column column = this.columns[i];
                int n2 = column.getColumnId();
                Expression expression = expressionArray[i];
                if (expression == ValueExpression.DEFAULT) continue;
                try {
                    row.setValue(n2, expression.getValue(sessionLocal));
                    continue;
                }
                catch (DbException dbException) {
                    dbException.addSQL("INSERT -- " + Prepared.getSimpleSQL(expressionArray));
                    throw dbException;
                }
            }
            table.convertInsertRow(sessionLocal, row, this.overridingSystem);
            if (resultOption == DataChangeDeltaTable.ResultOption.NEW) {
                resultTarget.addRow((Value[])row.getValueList().clone());
            }
            if (!table.fireBeforeRow(sessionLocal, null, row)) {
                table.addRow(sessionLocal, row);
                DataChangeDeltaTable.collectInsertedFinalRow(sessionLocal, table, resultTarget, resultOption, row);
                table.fireAfterRow(sessionLocal, null, row, false);
            } else {
                DataChangeDeltaTable.collectInsertedFinalRow(sessionLocal, table, resultTarget, resultOption, row);
            }
        }

        @Override
        boolean prepare(SessionLocal sessionLocal) {
            boolean bl = super.prepare(sessionLocal);
            TableFilter tableFilter = MergeUsing.this.targetTableFilter;
            TableFilter tableFilter2 = MergeUsing.this.sourceTableFilter;
            if (this.columns == null) {
                this.columns = tableFilter.getTable().getColumns();
            }
            if (this.values.length != this.columns.length) {
                throw DbException.get(21002);
            }
            int n = this.values.length;
            for (int i = 0; i < n; ++i) {
                Expression expression = this.values[i];
                expression.mapColumns(tableFilter, 0, 0);
                expression.mapColumns(tableFilter2, 0, 0);
                expression = expression.optimize(sessionLocal);
                if (expression instanceof Parameter) {
                    ((Parameter)expression).setColumn(this.columns[i]);
                }
                this.values[i] = expression;
            }
            return bl;
        }

        @Override
        int evaluateTriggerMasks() {
            return 1;
        }

        @Override
        void checkRights() {
            MergeUsing.this.getSession().getUser().checkTableRight(MergeUsing.this.targetTableFilter.getTable(), 4);
        }

        @Override
        void collectDependencies(ExpressionVisitor expressionVisitor) {
            super.collectDependencies(expressionVisitor);
            for (Expression expression : this.values) {
                expression.isEverything(expressionVisitor);
            }
        }

        @Override
        public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
            super.getSQL(stringBuilder, n).append("INSERT (");
            Column.writeColumns(stringBuilder, this.columns, n).append(")\nVALUES (");
            return Expression.writeExpressions(stringBuilder, this.values, n).append(')');
        }
    }

    public final class WhenMatchedThenUpdate
    extends When {
        private SetClauseList setClauseList;

        public void setSetClauseList(SetClauseList setClauseList) {
            this.setClauseList = setClauseList;
        }

        @Override
        void merge(SessionLocal sessionLocal, ResultTarget resultTarget, DataChangeDeltaTable.ResultOption resultOption) {
            TableFilter tableFilter = MergeUsing.this.targetTableFilter;
            Table table = tableFilter.getTable();
            try (LocalResult localResult = LocalResult.forTable(sessionLocal, table);){
                this.setClauseList.prepareUpdate(table, sessionLocal, resultTarget, resultOption, localResult, tableFilter.get(), false);
                Update.doUpdate(MergeUsing.this, sessionLocal, table, localResult);
            }
        }

        @Override
        boolean prepare(SessionLocal sessionLocal) {
            boolean bl = super.prepare(sessionLocal);
            this.setClauseList.mapAndOptimize(sessionLocal, MergeUsing.this.targetTableFilter, MergeUsing.this.sourceTableFilter);
            return bl;
        }

        @Override
        int evaluateTriggerMasks() {
            return 2;
        }

        @Override
        void checkRights() {
            MergeUsing.this.getSession().getUser().checkTableRight(MergeUsing.this.targetTableFilter.getTable(), 8);
        }

        @Override
        void collectDependencies(ExpressionVisitor expressionVisitor) {
            super.collectDependencies(expressionVisitor);
            this.setClauseList.isEverything(expressionVisitor);
        }

        @Override
        public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
            return this.setClauseList.getSQL(super.getSQL(stringBuilder, n).append("UPDATE"), n);
        }
    }

    public final class WhenMatchedThenDelete
    extends When {
        @Override
        void merge(SessionLocal sessionLocal, ResultTarget resultTarget, DataChangeDeltaTable.ResultOption resultOption) {
            TableFilter tableFilter = MergeUsing.this.targetTableFilter;
            Table table = tableFilter.getTable();
            Row row = tableFilter.get();
            if (resultOption == DataChangeDeltaTable.ResultOption.OLD) {
                resultTarget.addRow(row.getValueList());
            }
            if (!table.fireRow() || !table.fireBeforeRow(sessionLocal, row, null)) {
                table.removeRow(sessionLocal, row);
                table.fireAfterRow(sessionLocal, row, null, false);
            }
        }

        @Override
        int evaluateTriggerMasks() {
            return 4;
        }

        @Override
        void checkRights() {
            MergeUsing.this.getSession().getUser().checkTableRight(MergeUsing.this.targetTableFilter.getTable(), 2);
        }

        @Override
        public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
            return super.getSQL(stringBuilder, n).append("DELETE");
        }
    }

    public abstract class When
    implements HasSQL {
        Expression andCondition;

        When() {
        }

        public void setAndCondition(Expression expression) {
            this.andCondition = expression;
        }

        abstract void merge(SessionLocal var1, ResultTarget var2, DataChangeDeltaTable.ResultOption var3);

        boolean prepare(SessionLocal sessionLocal) {
            if (this.andCondition != null) {
                this.andCondition.mapColumns(MergeUsing.this.targetTableFilter, 0, 0);
                this.andCondition.mapColumns(MergeUsing.this.sourceTableFilter, 0, 0);
                this.andCondition = this.andCondition.optimize(sessionLocal);
                if (this.andCondition.isConstant()) {
                    if (this.andCondition.getBooleanValue(sessionLocal)) {
                        this.andCondition = null;
                    } else {
                        return false;
                    }
                }
            }
            return true;
        }

        abstract int evaluateTriggerMasks();

        abstract void checkRights();

        void collectDependencies(ExpressionVisitor expressionVisitor) {
            if (this.andCondition != null) {
                this.andCondition.isEverything(expressionVisitor);
            }
        }

        @Override
        public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
            stringBuilder.append("WHEN ");
            if (this.getClass() == WhenNotMatched.class) {
                stringBuilder.append("NOT ");
            }
            stringBuilder.append("MATCHED");
            if (this.andCondition != null) {
                this.andCondition.getUnenclosedSQL(stringBuilder.append(" AND "), n);
            }
            return stringBuilder.append(" THEN ");
        }
    }
}

