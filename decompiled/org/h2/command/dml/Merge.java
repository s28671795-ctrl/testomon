/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.dml;

import java.util.ArrayList;
import java.util.HashSet;
import org.h2.command.Command;
import org.h2.command.dml.CommandWithValues;
import org.h2.command.dml.Update;
import org.h2.command.query.Query;
import org.h2.engine.DbObject;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.Parameter;
import org.h2.expression.ValueExpression;
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
import org.h2.value.ValueNull;

public final class Merge
extends CommandWithValues {
    private boolean isReplace;
    private Table table;
    private Column[] columns;
    private Column[] keys;
    private Query query;
    private Update update;

    public Merge(SessionLocal sessionLocal, boolean bl) {
        super(sessionLocal);
        this.isReplace = bl;
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

    public void setKeys(Column[] columnArray) {
        this.keys = columnArray;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    @Override
    public long update(ResultTarget resultTarget, DataChangeDeltaTable.ResultOption resultOption) {
        long l = 0L;
        this.session.getUser().checkTableRight(this.table, 4);
        this.session.getUser().checkTableRight(this.table, 8);
        this.setCurrentRowNumber(0L);
        if (!this.valuesExpressionList.isEmpty()) {
            int n = this.valuesExpressionList.size();
            for (int i = 0; i < n; ++i) {
                this.setCurrentRowNumber(i + 1);
                Expression[] expressionArray = (Expression[])this.valuesExpressionList.get(i);
                Row row = this.table.getTemplateRow();
                int n2 = this.columns.length;
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
                        throw this.setRow(dbException, l, Merge.getSimpleSQL(expressionArray));
                    }
                }
                l += (long)this.merge(row, expressionArray, resultTarget, resultOption);
            }
        } else {
            this.query.setNeverLazy(true);
            ResultInterface resultInterface = this.query.query(0L);
            this.table.fire(this.session, 3, true);
            this.table.lock(this.session, 1);
            while (resultInterface.next()) {
                Value[] valueArray = resultInterface.currentRow();
                Row row = this.table.getTemplateRow();
                this.setCurrentRowNumber(l);
                for (int i = 0; i < this.columns.length; ++i) {
                    row.setValue(this.columns[i].getColumnId(), valueArray[i]);
                }
                l += (long)this.merge(row, null, resultTarget, resultOption);
            }
            resultInterface.close();
            this.table.fire(this.session, 3, false);
        }
        return l;
    }

    /*
     * WARNING - void declaration
     */
    private int merge(Row row, Expression[] expressionArray, ResultTarget resultTarget, DataChangeDeltaTable.ResultOption resultOption) {
        long l;
        if (this.update == null) {
            l = 0L;
        } else {
            HasSQL hasSQL;
            ArrayList<Parameter> arrayList = this.update.getParameters();
            int n = 0;
            int n2 = this.columns.length;
            for (int i = 0; i < n2; ++i) {
                void hasSQL2;
                Column column = this.columns[i];
                if (column.isGeneratedAlways()) {
                    if (expressionArray != null && expressionArray[i] == ValueExpression.DEFAULT) continue;
                    throw DbException.get(90154, column.getSQLWithTable(new StringBuilder(), 3).toString());
                }
                Value value = row.getValue(column.getColumnId());
                if (value == null) {
                    hasSQL = column.getEffectiveDefaultExpression();
                    ValueNull valueNull = hasSQL != null ? ((Expression)hasSQL).getValue(this.session) : ValueNull.INSTANCE;
                }
                arrayList.get(n++).setValue((Value)hasSQL2);
            }
            for (Column column : this.keys) {
                hasSQL = row.getValue(column.getColumnId());
                if (hasSQL == null) {
                    throw DbException.get(90081, column.getTraceSQL());
                }
                arrayList.get(n++).setValue((Value)hasSQL);
            }
            l = this.update.update(resultTarget, resultOption);
        }
        if (l == 0L) {
            try {
                this.table.convertInsertRow(this.session, row, null);
                if (resultOption == DataChangeDeltaTable.ResultOption.NEW) {
                    resultTarget.addRow((Value[])row.getValueList().clone());
                }
                if (!this.table.fireBeforeRow(this.session, null, row)) {
                    this.table.lock(this.session, 1);
                    this.table.addRow(this.session, row);
                    DataChangeDeltaTable.collectInsertedFinalRow(this.session, this.table, resultTarget, resultOption, row);
                    this.table.fireAfterRow(this.session, null, row, false);
                } else {
                    DataChangeDeltaTable.collectInsertedFinalRow(this.session, this.table, resultTarget, resultOption, row);
                }
                return 1;
            }
            catch (DbException dbException) {
                Index index;
                if (dbException.getErrorCode() == 23505 && (index = (Index)dbException.getSource()) != null) {
                    boolean bl;
                    Column[] columnArray;
                    if (index instanceof MVPrimaryIndex) {
                        MVPrimaryIndex mVPrimaryIndex = (MVPrimaryIndex)index;
                        columnArray = new Column[]{mVPrimaryIndex.getIndexColumns()[mVPrimaryIndex.getMainIndexColumn()].column};
                    } else {
                        columnArray = index.getColumns();
                    }
                    if (columnArray.length <= this.keys.length) {
                        bl = true;
                        for (int i = 0; i < columnArray.length; ++i) {
                            if (columnArray[i] == this.keys[i]) continue;
                            bl = false;
                            break;
                        }
                    } else {
                        bl = false;
                    }
                    if (bl) {
                        throw DbException.get(90131, this.table.getName());
                    }
                }
                throw dbException;
            }
        }
        if (l == 1L) {
            return this.isReplace ? 2 : 1;
        }
        throw DbException.get(23505, this.table.getTraceSQL());
    }

    @Override
    public String getPlanSQL(int n) {
        StringBuilder stringBuilder = new StringBuilder(this.isReplace ? "REPLACE INTO " : "MERGE INTO ");
        this.table.getSQL(stringBuilder, n).append('(');
        Column.writeColumns(stringBuilder, this.columns, n);
        stringBuilder.append(')');
        if (!this.isReplace && this.keys != null) {
            stringBuilder.append(" KEY(");
            Column.writeColumns(stringBuilder, this.keys, n);
            stringBuilder.append(')');
        }
        stringBuilder.append('\n');
        if (!this.valuesExpressionList.isEmpty()) {
            stringBuilder.append("VALUES ");
            int n2 = 0;
            for (Expression[] expressionArray : this.valuesExpressionList) {
                if (n2++ > 0) {
                    stringBuilder.append(", ");
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
        Object object;
        if (this.columns == null) {
            this.columns = !this.valuesExpressionList.isEmpty() && ((Expression[])this.valuesExpressionList.get(0)).length == 0 ? new Column[0] : this.table.getColumns();
        }
        if (!this.valuesExpressionList.isEmpty()) {
            for (Expression[] expressionArray : this.valuesExpressionList) {
                if (expressionArray.length != this.columns.length) {
                    throw DbException.get(21002);
                }
                for (int i = 0; i < expressionArray.length; ++i) {
                    Expression hasSQL = expressionArray[i];
                    if (hasSQL == null) continue;
                    expressionArray[i] = hasSQL.optimize(this.session);
                }
            }
        } else {
            this.query.prepare();
            if (this.query.getColumnCount() != this.columns.length) {
                throw DbException.get(21002);
            }
        }
        if (this.keys == null) {
            object = this.table.getPrimaryKey();
            if (object == null) {
                throw DbException.get(90057, "PRIMARY KEY");
            }
            this.keys = ((Index)object).getColumns();
        }
        if (this.isReplace) {
            for (Column column : this.keys) {
                boolean column2 = false;
                for (Column column3 : this.columns) {
                    if (column3.getColumnId() != column.getColumnId()) continue;
                    column2 = true;
                    break;
                }
                if (column2) continue;
                return;
            }
        }
        object = this.table.getSQL(new StringBuilder("UPDATE "), 0).append(" SET ");
        boolean bl = false;
        for (Column column : this.columns) {
            if (column.isGeneratedAlways()) continue;
            if (bl) {
                ((StringBuilder)object).append(", ");
            }
            bl = true;
            column.getSQL((StringBuilder)object, 0).append("=?");
        }
        if (!bl) {
            throw DbException.getSyntaxError(this.sqlStatement, this.sqlStatement.length(), "Valid MERGE INTO statement with at least one updatable column");
        }
        Column.writeColumns(((StringBuilder)object).append(" WHERE "), this.keys, " AND ", "=?", 0);
        this.update = (Update)this.session.prepare(((StringBuilder)object).toString());
    }

    @Override
    public int getType() {
        return this.isReplace ? 63 : 62;
    }

    @Override
    public String getStatementName() {
        return this.isReplace ? "REPLACE" : "MERGE";
    }

    @Override
    public void collectDependencies(HashSet<DbObject> hashSet) {
        if (this.query != null) {
            this.query.collectDependencies(hashSet);
        }
    }
}

