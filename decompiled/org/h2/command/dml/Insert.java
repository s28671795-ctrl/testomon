/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.dml;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import org.h2.command.Command;
import org.h2.command.dml.CommandWithValues;
import org.h2.command.dml.Update;
import org.h2.command.query.Query;
import org.h2.engine.DbObject;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionColumn;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.Parameter;
import org.h2.expression.ValueExpression;
import org.h2.expression.condition.Comparison;
import org.h2.expression.condition.ConditionAndOr;
import org.h2.index.Index;
import org.h2.message.DbException;
import org.h2.mvstore.db.MVPrimaryIndex;
import org.h2.result.ResultInterface;
import org.h2.result.ResultTarget;
import org.h2.result.Row;
import org.h2.table.Column;
import org.h2.table.DataChangeDeltaTable;
import org.h2.table.Table;
import org.h2.util.HasSQL;
import org.h2.value.Value;

public final class Insert
extends CommandWithValues
implements ResultTarget {
    private Table table;
    private Column[] columns;
    private Query query;
    private long rowNumber;
    private boolean insertFromSelect;
    private Boolean overridingSystem;
    private HashMap<Column, Expression> duplicateKeyAssignmentMap;
    private Value[] onDuplicateKeyRow;
    private boolean ignore;
    private ResultTarget deltaChangeCollector;
    private DataChangeDeltaTable.ResultOption deltaChangeCollectionMode;

    public Insert(SessionLocal sessionLocal) {
        super(sessionLocal);
    }

    @Override
    public void setCommand(Command command) {
        super.setCommand(command);
        if (this.query != null) {
            this.query.setCommand(command);
        }
    }

    @Override
    public Table getTable() {
        return this.table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public void setColumns(Column[] columnArray) {
        this.columns = columnArray;
    }

    public void setIgnore(boolean bl) {
        this.ignore = bl;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public void setOverridingSystem(Boolean bl) {
        this.overridingSystem = bl;
    }

    public void addAssignmentForDuplicate(Column column, Expression expression) {
        if (this.duplicateKeyAssignmentMap == null) {
            this.duplicateKeyAssignmentMap = new HashMap();
        }
        if (this.duplicateKeyAssignmentMap.putIfAbsent(column, expression) != null) {
            throw DbException.get(42121, column.getName());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public long update(ResultTarget resultTarget, DataChangeDeltaTable.ResultOption resultOption) {
        this.deltaChangeCollector = resultTarget;
        this.deltaChangeCollectionMode = resultOption;
        try {
            long l = this.insertRows();
            return l;
        }
        finally {
            this.deltaChangeCollector = null;
            this.deltaChangeCollectionMode = null;
        }
    }

    private long insertRows() {
        this.session.getUser().checkTableRight(this.table, 4);
        this.setCurrentRowNumber(0L);
        this.table.fire(this.session, 1, true);
        this.rowNumber = 0L;
        int n = this.valuesExpressionList.size();
        if (n > 0) {
            int n2 = this.columns.length;
            for (int i = 0; i < n; ++i) {
                Row row = this.table.getTemplateRow();
                Expression[] expressionArray = (Expression[])this.valuesExpressionList.get(i);
                this.setCurrentRowNumber(i + 1);
                for (int j = 0; j < n2; ++j) {
                    Column column = this.columns[j];
                    int n3 = column.getColumnId();
                    Expression expression = expressionArray[j];
                    if (expression == ValueExpression.DEFAULT) continue;
                    try {
                        row.setValue(n3, expression.getValue(this.session));
                        continue;
                    }
                    catch (DbException dbException) {
                        throw this.setRow(dbException, i, Insert.getSimpleSQL(expressionArray));
                    }
                }
                ++this.rowNumber;
                this.table.convertInsertRow(this.session, row, this.overridingSystem);
                if (this.deltaChangeCollectionMode == DataChangeDeltaTable.ResultOption.NEW) {
                    this.deltaChangeCollector.addRow((Value[])row.getValueList().clone());
                }
                if (!this.table.fireBeforeRow(this.session, null, row)) {
                    this.table.lock(this.session, 1);
                    try {
                        this.table.addRow(this.session, row);
                    }
                    catch (DbException dbException) {
                        if (this.handleOnDuplicate(dbException, null)) {
                            ++this.rowNumber;
                            continue;
                        }
                        --this.rowNumber;
                        continue;
                    }
                    DataChangeDeltaTable.collectInsertedFinalRow(this.session, this.table, this.deltaChangeCollector, this.deltaChangeCollectionMode, row);
                    this.table.fireAfterRow(this.session, null, row, false);
                    continue;
                }
                DataChangeDeltaTable.collectInsertedFinalRow(this.session, this.table, this.deltaChangeCollector, this.deltaChangeCollectionMode, row);
            }
        } else {
            this.table.lock(this.session, 1);
            if (this.insertFromSelect) {
                this.query.query(0L, this);
            } else {
                ResultInterface resultInterface = this.query.query(0L);
                while (resultInterface.next()) {
                    Value[] valueArray = resultInterface.currentRow();
                    try {
                        this.addRow(valueArray);
                    }
                    catch (DbException dbException) {
                        if (this.handleOnDuplicate(dbException, valueArray)) {
                            ++this.rowNumber;
                            continue;
                        }
                        --this.rowNumber;
                    }
                }
                resultInterface.close();
            }
        }
        this.table.fire(this.session, 1, false);
        return this.rowNumber;
    }

    @Override
    public void addRow(Value ... valueArray) {
        Row row = this.table.getTemplateRow();
        this.setCurrentRowNumber(++this.rowNumber);
        int n = this.columns.length;
        for (int i = 0; i < n; ++i) {
            row.setValue(this.columns[i].getColumnId(), valueArray[i]);
        }
        this.table.convertInsertRow(this.session, row, this.overridingSystem);
        if (this.deltaChangeCollectionMode == DataChangeDeltaTable.ResultOption.NEW) {
            this.deltaChangeCollector.addRow((Value[])row.getValueList().clone());
        }
        if (!this.table.fireBeforeRow(this.session, null, row)) {
            this.table.addRow(this.session, row);
            DataChangeDeltaTable.collectInsertedFinalRow(this.session, this.table, this.deltaChangeCollector, this.deltaChangeCollectionMode, row);
            this.table.fireAfterRow(this.session, null, row, false);
        } else {
            DataChangeDeltaTable.collectInsertedFinalRow(this.session, this.table, this.deltaChangeCollector, this.deltaChangeCollectionMode, row);
        }
    }

    @Override
    public long getRowCount() {
        return this.rowNumber;
    }

    @Override
    public void limitsWereApplied() {
    }

    @Override
    public String getPlanSQL(int n) {
        StringBuilder stringBuilder = new StringBuilder("INSERT INTO ");
        this.table.getSQL(stringBuilder, n).append('(');
        Column.writeColumns(stringBuilder, this.columns, n);
        stringBuilder.append(")\n");
        if (this.insertFromSelect) {
            stringBuilder.append("DIRECT ");
        }
        if (!this.valuesExpressionList.isEmpty()) {
            stringBuilder.append("VALUES ");
            int n2 = 0;
            if (this.valuesExpressionList.size() > 1) {
                stringBuilder.append('\n');
            }
            for (Expression[] expressionArray : this.valuesExpressionList) {
                if (n2++ > 0) {
                    stringBuilder.append(",\n");
                }
                Expression.writeExpressions(stringBuilder.append('('), expressionArray, n).append(')');
            }
        } else {
            stringBuilder.append(this.query.getPlanSQL(n));
        }
        return stringBuilder.toString();
    }

    @Override
    void doPrepare() {
        if (this.columns == null) {
            this.columns = !this.valuesExpressionList.isEmpty() && ((Expression[])this.valuesExpressionList.get(0)).length == 0 ? new Column[0] : this.table.getColumns();
        }
        if (!this.valuesExpressionList.isEmpty()) {
            for (Expression[] expressionArray : this.valuesExpressionList) {
                if (expressionArray.length != this.columns.length) {
                    throw DbException.get(21002);
                }
                int n = expressionArray.length;
                for (int i = 0; i < n; ++i) {
                    Expression expression = expressionArray[i];
                    if (expression == null) continue;
                    if ((expression = expression.optimize(this.session)) instanceof Parameter) {
                        Parameter parameter = (Parameter)expression;
                        parameter.setColumn(this.columns[i]);
                    }
                    expressionArray[i] = expression;
                }
            }
        } else {
            this.query.prepare();
            if (this.query.getColumnCount() != this.columns.length) {
                throw DbException.get(21002);
            }
        }
    }

    @Override
    public int getType() {
        return 61;
    }

    @Override
    public String getStatementName() {
        return "INSERT";
    }

    public void setInsertFromSelect(boolean bl) {
        this.insertFromSelect = bl;
    }

    @Override
    public boolean isCacheable() {
        return this.duplicateKeyAssignmentMap == null;
    }

    private boolean handleOnDuplicate(DbException dbException, Value[] valueArray) {
        if (dbException.getErrorCode() != 23505) {
            throw dbException;
        }
        if (this.duplicateKeyAssignmentMap == null) {
            if (this.ignore) {
                return false;
            }
            throw dbException;
        }
        int n = this.columns.length;
        Expression[] expressionArray = valueArray == null ? (Expression[])this.valuesExpressionList.get((int)this.getCurrentRowNumber() - 1) : new Expression[n];
        this.onDuplicateKeyRow = new Value[this.table.getColumns().length];
        for (int i = 0; i < n; ++i) {
            Value value;
            if (valueArray != null) {
                value = valueArray[i];
                expressionArray[i] = ValueExpression.get(value);
            } else {
                value = expressionArray[i].getValue(this.session);
            }
            this.onDuplicateKeyRow[this.columns[i].getColumnId()] = value;
        }
        StringBuilder stringBuilder = new StringBuilder("UPDATE ");
        this.table.getSQL(stringBuilder, 0).append(" SET ");
        boolean bl = false;
        for (Map.Entry<Column, Expression> object2 : this.duplicateKeyAssignmentMap.entrySet()) {
            if (bl) {
                stringBuilder.append(", ");
            }
            bl = true;
            object2.getKey().getSQL(stringBuilder, 0).append('=');
            object2.getValue().getUnenclosedSQL(stringBuilder, 0);
        }
        stringBuilder.append(" WHERE ");
        Index index = (Index)dbException.getSource();
        if (index == null) {
            throw DbException.getUnsupportedException("Unable to apply ON DUPLICATE KEY UPDATE, no index found!");
        }
        this.prepareUpdateCondition(index, expressionArray).getUnenclosedSQL(stringBuilder, 0);
        String string = stringBuilder.toString();
        Update update = (Update)this.session.prepare(string);
        update.setOnDuplicateKeyInsert(this);
        for (Parameter parameter : update.getParameters()) {
            Parameter parameter2 = (Parameter)this.parameters.get(parameter.getIndex());
            parameter.setValue(parameter2.getValue(this.session));
        }
        boolean bl2 = update.update() > 0L;
        this.onDuplicateKeyRow = null;
        return bl2;
    }

    private Expression prepareUpdateCondition(Index index, Expression[] expressionArray) {
        Column[] columnArray;
        HasSQL hasSQL;
        if (index instanceof MVPrimaryIndex) {
            hasSQL = (MVPrimaryIndex)index;
            columnArray = new Column[]{hasSQL.getIndexColumns()[hasSQL.getMainIndexColumn()].column};
        } else {
            columnArray = index.getColumns();
        }
        hasSQL = null;
        block0: for (Column column : columnArray) {
            ExpressionColumn expressionColumn = new ExpressionColumn(this.getDatabase(), this.table.getSchema().getName(), this.table.getName(), column.getName());
            for (int i = 0; i < this.columns.length; ++i) {
                if (!expressionColumn.getColumnName(this.session, i).equals(this.columns[i].getName())) continue;
                if (hasSQL == null) {
                    hasSQL = new Comparison(0, expressionColumn, expressionArray[i], false);
                    continue block0;
                }
                hasSQL = new ConditionAndOr(0, (Expression)hasSQL, new Comparison(0, expressionColumn, expressionArray[i], false));
                continue block0;
            }
        }
        return hasSQL;
    }

    public Value getOnDuplicateKeyValue(int n) {
        return this.onDuplicateKeyRow[n];
    }

    @Override
    public void collectDependencies(HashSet<DbObject> hashSet) {
        ExpressionVisitor expressionVisitor = ExpressionVisitor.getDependenciesVisitor(hashSet);
        if (!this.valuesExpressionList.isEmpty()) {
            Iterator iterator2 = this.valuesExpressionList.iterator();
            while (iterator2.hasNext()) {
                Expression[] expressionArray;
                for (Expression expression : expressionArray = (Expression[])iterator2.next()) {
                    expression.isEverything(expressionVisitor);
                }
            }
        } else {
            this.query.isEverything(expressionVisitor);
        }
    }
}

