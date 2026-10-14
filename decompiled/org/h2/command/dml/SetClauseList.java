/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.dml;

import java.util.ArrayList;
import java.util.Arrays;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionList;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.Parameter;
import org.h2.expression.ValueExpression;
import org.h2.message.DbException;
import org.h2.result.LocalResult;
import org.h2.result.ResultTarget;
import org.h2.result.Row;
import org.h2.table.Column;
import org.h2.table.ColumnResolver;
import org.h2.table.DataChangeDeltaTable;
import org.h2.table.Table;
import org.h2.util.HasSQL;
import org.h2.value.Value;
import org.h2.value.ValueArray;
import org.h2.value.ValueNull;

public final class SetClauseList
implements HasSQL {
    private final Table table;
    private final UpdateAction[] actions;
    private boolean onUpdate;

    public SetClauseList(Table table) {
        this.table = table;
        this.actions = new UpdateAction[table.getColumns().length];
    }

    public void addSingle(Column column, Expression[] expressionArray, Expression expression) {
        int n = column.getColumnId();
        if (this.actions[n] != null) {
            throw DbException.get(42121, column.getName());
        }
        if (expression != ValueExpression.DEFAULT) {
            this.actions[n] = new SetSimple(expressionArray, expression);
            if (expression instanceof Parameter) {
                ((Parameter)expression).setColumn(column);
            }
        } else {
            this.actions[n] = UpdateAction.SET_DEFAULT;
        }
    }

    public void addMultiple(ArrayList<Column> arrayList, ArrayList<Expression[]> arrayList2, Expression expression) {
        Object object;
        int n = arrayList.size();
        if (expression instanceof ExpressionList && !((ExpressionList)(object = (ExpressionList)expression)).isArray()) {
            if (n != ((ExpressionList)object).getSubexpressionCount()) {
                throw DbException.get(21002);
            }
            for (int i = 0; i < n; ++i) {
                this.addSingle(arrayList.get(i), arrayList2.get(i), ((ExpressionList)object).getSubexpression(i));
            }
            return;
        }
        if (n == 1) {
            this.addSingle(arrayList.get(0), arrayList2.get(0), expression);
        } else {
            int n2;
            object = new int[n];
            RowExpression rowExpression = new RowExpression(expression, (int[])object);
            int n3 = this.table.getColumns().length - 1;
            int n4 = 0;
            for (n2 = 0; n2 < n; ++n2) {
                int n5 = arrayList.get(n2).getColumnId();
                if (n5 < n3) {
                    n3 = n5;
                }
                if (n5 <= n4) continue;
                n4 = n5;
            }
            for (n2 = 0; n2 < n; ++n2) {
                Column column = arrayList.get(n2);
                int n6 = column.getColumnId();
                object[n2] = n6;
                if (this.actions[n6] != null) {
                    throw DbException.get(42121, column.getName());
                }
                this.actions[n6] = new SetMultiple(arrayList2.get(n2), rowExpression, n2, n6 == n3, n6 == n4);
            }
        }
    }

    boolean prepareUpdate(Table table, SessionLocal sessionLocal, ResultTarget resultTarget, DataChangeDeltaTable.ResultOption resultOption, LocalResult localResult, Row row, boolean bl) {
        int n;
        Column[] columnArray = table.getColumns();
        int n2 = columnArray.length;
        Row row2 = table.getTemplateRow();
        for (n = 0; n < n2; ++n) {
            Value value;
            UpdateAction updateAction = this.actions[n];
            Column column = columnArray[n];
            Value value2 = row.getValue(n);
            if (updateAction == null || updateAction == UpdateAction.ON_UPDATE) {
                value = column.isGenerated() ? null : value2;
            } else if (updateAction == UpdateAction.SET_DEFAULT) {
                value = !column.isIdentity() ? null : value2;
            } else {
                value = updateAction.update(sessionLocal, value2);
                if (value == ValueNull.INSTANCE && column.isDefaultOnNull()) {
                    value = !column.isIdentity() ? null : value2;
                } else if (column.isGeneratedAlways()) {
                    throw DbException.get(90154, column.getSQLWithTable(new StringBuilder(), 3).toString());
                }
            }
            row2.setValue(n, value);
        }
        row2.setKey(row.getKey());
        table.convertUpdateRow(sessionLocal, row2, false);
        n = 1;
        if (this.onUpdate) {
            if (!row.hasSameValues(row2)) {
                for (int i = 0; i < n2; ++i) {
                    if (this.actions[i] == UpdateAction.ON_UPDATE) {
                        row2.setValue(i, columnArray[i].getEffectiveOnUpdateExpression().getValue(sessionLocal));
                        continue;
                    }
                    if (!columnArray[i].isGenerated()) continue;
                    row2.setValue(i, null);
                }
                table.convertUpdateRow(sessionLocal, row2, false);
            } else if (bl) {
                n = 0;
            }
        } else if (bl && row.hasSameValues(row2)) {
            n = 0;
        }
        if (resultOption == DataChangeDeltaTable.ResultOption.OLD) {
            resultTarget.addRow(row.getValueList());
        } else if (resultOption == DataChangeDeltaTable.ResultOption.NEW) {
            resultTarget.addRow((Value[])row2.getValueList().clone());
        }
        if (!table.fireRow() || !table.fireBeforeRow(sessionLocal, row, row2)) {
            localResult.addRowForTable(row);
            localResult.addRowForTable(row2);
        }
        if (resultOption == DataChangeDeltaTable.ResultOption.FINAL) {
            resultTarget.addRow(row2.getValueList());
        }
        return n != 0;
    }

    boolean isEverything(ExpressionVisitor expressionVisitor) {
        for (UpdateAction updateAction : this.actions) {
            if (updateAction == null || updateAction.isEverything(expressionVisitor)) continue;
            return false;
        }
        return true;
    }

    void mapAndOptimize(SessionLocal sessionLocal, ColumnResolver columnResolver, ColumnResolver columnResolver2) {
        Column[] columnArray = this.table.getColumns();
        boolean bl = false;
        for (int i = 0; i < this.actions.length; ++i) {
            UpdateAction updateAction = this.actions[i];
            if (updateAction != null) {
                updateAction.mapAndOptimize(sessionLocal, columnResolver, columnResolver2);
                continue;
            }
            Column column = columnArray[i];
            if (column.getEffectiveOnUpdateExpression() == null) continue;
            this.actions[i] = UpdateAction.ON_UPDATE;
            bl = true;
        }
        this.onUpdate = bl;
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        Column[] columnArray = this.table.getColumns();
        stringBuilder.append("\nSET\n    ");
        boolean bl = false;
        for (int i = 0; i < this.actions.length; ++i) {
            Object object;
            UpdateAction updateAction = this.actions[i];
            if (updateAction == null || updateAction == UpdateAction.ON_UPDATE) continue;
            if (updateAction.getClass() == SetMultiple.class) {
                object = (SetMultiple)updateAction;
                if (!((SetMultiple)object).first) continue;
                if (bl) {
                    stringBuilder.append(",\n    ");
                }
                bl = true;
                RowExpression rowExpression = ((SetMultiple)object).row;
                stringBuilder.append('(');
                int[] nArray = rowExpression.columns;
                int n2 = nArray.length;
                for (int j = 0; j < n2; ++j) {
                    if (j > 0) {
                        stringBuilder.append(", ");
                    }
                    columnArray[nArray[j]].getSQL(stringBuilder, n);
                }
                rowExpression.expression.getUnenclosedSQL(stringBuilder.append(") = "), n);
                continue;
            }
            if (bl) {
                stringBuilder.append(",\n    ");
            }
            bl = true;
            object = columnArray[i];
            if (updateAction != UpdateAction.SET_DEFAULT) {
                updateAction.getSQL(stringBuilder, n, (Column)object);
                continue;
            }
            ((Column)object).getSQL(stringBuilder, n).append(" = DEFAULT");
        }
        return stringBuilder;
    }

    private static final class SetMultiple
    extends SetAction {
        final RowExpression row;
        private final int position;
        boolean first;
        private boolean last;

        SetMultiple(Expression[] expressionArray, RowExpression rowExpression, int n, boolean bl, boolean bl2) {
            super(expressionArray);
            this.row = rowExpression;
            this.position = n;
            this.first = bl;
            this.last = bl2;
        }

        @Override
        Value update(SessionLocal sessionLocal) {
            Value[] valueArray;
            if (this.first) {
                Value value = this.row.expression.getValue(sessionLocal);
                if (value == ValueNull.INSTANCE) {
                    throw DbException.get(22018, "NULL to assigned row value");
                }
                this.row.values = valueArray = value.convertToAnyRow().getList();
                if (valueArray.length != this.row.columns.length) {
                    throw DbException.get(21002);
                }
            } else {
                valueArray = this.row.values;
                if (this.last) {
                    this.row.values = null;
                }
            }
            return valueArray[this.position];
        }

        @Override
        boolean isEverything(ExpressionVisitor expressionVisitor) {
            return super.isEverything(expressionVisitor) && (!this.first || this.row.isEverything(expressionVisitor));
        }

        @Override
        void mapAndOptimize(SessionLocal sessionLocal, ColumnResolver columnResolver, ColumnResolver columnResolver2) {
            super.mapAndOptimize(sessionLocal, columnResolver, columnResolver2);
            if (this.first) {
                this.row.mapAndOptimize(sessionLocal, columnResolver, columnResolver2);
            }
        }
    }

    private static final class RowExpression {
        Expression expression;
        final int[] columns;
        Value[] values;

        RowExpression(Expression expression, int[] nArray) {
            this.expression = expression;
            this.columns = nArray;
        }

        boolean isEverything(ExpressionVisitor expressionVisitor) {
            return this.expression.isEverything(expressionVisitor);
        }

        void mapAndOptimize(SessionLocal sessionLocal, ColumnResolver columnResolver, ColumnResolver columnResolver2) {
            this.expression.mapColumns(columnResolver, 0, 0);
            if (columnResolver2 != null) {
                this.expression.mapColumns(columnResolver2, 0, 0);
            }
            this.expression = this.expression.optimize(sessionLocal);
        }
    }

    private static final class SetSimple
    extends SetAction {
        private Expression expression;

        SetSimple(Expression[] expressionArray, Expression expression) {
            super(expressionArray);
            this.expression = expression;
        }

        @Override
        Value update(SessionLocal sessionLocal) {
            return this.expression.getValue(sessionLocal);
        }

        @Override
        boolean isEverything(ExpressionVisitor expressionVisitor) {
            return super.isEverything(expressionVisitor) && this.expression.isEverything(expressionVisitor);
        }

        @Override
        void mapAndOptimize(SessionLocal sessionLocal, ColumnResolver columnResolver, ColumnResolver columnResolver2) {
            super.mapAndOptimize(sessionLocal, columnResolver, columnResolver2);
            this.expression.mapColumns(columnResolver, 0, 0);
            if (columnResolver2 != null) {
                this.expression.mapColumns(columnResolver2, 0, 0);
            }
            this.expression = this.expression.optimize(sessionLocal);
        }

        @Override
        void getSQL(StringBuilder stringBuilder, int n, Column column) {
            this.expression.getUnenclosedSQL(column.getSQL(stringBuilder, n).append(" = "), n);
        }
    }

    private static abstract class SetAction
    extends UpdateAction {
        private final Expression[] arrayIndexes;

        SetAction(Expression[] expressionArray) {
            this.arrayIndexes = expressionArray;
        }

        @Override
        boolean isEverything(ExpressionVisitor expressionVisitor) {
            if (this.arrayIndexes != null) {
                for (Expression expression : this.arrayIndexes) {
                    if (expression.isEverything(expressionVisitor)) continue;
                    return false;
                }
            }
            return true;
        }

        @Override
        void mapAndOptimize(SessionLocal sessionLocal, ColumnResolver columnResolver, ColumnResolver columnResolver2) {
            if (this.arrayIndexes != null) {
                for (Expression expression : this.arrayIndexes) {
                    expression.mapColumns(columnResolver, 0, 0);
                    if (columnResolver2 != null) {
                        expression.mapColumns(columnResolver2, 0, 0);
                    }
                    this.arrayIndexes[var4_4] = expression.optimize(sessionLocal);
                }
            }
        }

        @Override
        final Value update(SessionLocal sessionLocal, Value value) {
            Value value2 = this.update(sessionLocal);
            if (this.arrayIndexes != null) {
                value2 = this.updateArray(sessionLocal, value, value2, 0);
            }
            return value2;
        }

        private Value updateArray(SessionLocal sessionLocal, Value value, Value value2, int n) {
            Value[] valueArray;
            int n2 = this.arrayIndexes[n++].getValue(sessionLocal).getInt();
            int n3 = 65536;
            if (n2 < 0 || n2 > n3) {
                throw DbException.get(22034, Integer.toString(n2), "1.." + n3);
            }
            if (value == null) {
                valueArray = new Value[n2];
                for (int i = 0; i < n2 - 1; ++i) {
                    valueArray[i] = ValueNull.INSTANCE;
                }
            } else {
                if (value == ValueNull.INSTANCE) {
                    throw DbException.get(22035);
                }
                if (value.getValueType() != 40) {
                    throw DbException.get(22034, value.getType().getTraceSQL(), "ARRAY");
                }
                valueArray = ((ValueArray)value).getList();
                int n4 = valueArray.length;
                if (n2 <= n4) {
                    valueArray = (Value[])valueArray.clone();
                } else {
                    valueArray = Arrays.copyOf(valueArray, n2);
                    for (int i = n4; i < n2 - 1; ++i) {
                        valueArray[i] = ValueNull.INSTANCE;
                    }
                }
            }
            valueArray[n2 - 1] = n == this.arrayIndexes.length ? value2 : this.updateArray(sessionLocal, valueArray[n2 - 1], value2, n);
            return ValueArray.get(valueArray, sessionLocal);
        }

        abstract Value update(SessionLocal var1);
    }

    private static class UpdateAction {
        static UpdateAction ON_UPDATE = new UpdateAction();
        static UpdateAction SET_DEFAULT = new UpdateAction();

        UpdateAction() {
        }

        Value update(SessionLocal sessionLocal, Value value) {
            throw DbException.getInternalError();
        }

        boolean isEverything(ExpressionVisitor expressionVisitor) {
            return true;
        }

        void mapAndOptimize(SessionLocal sessionLocal, ColumnResolver columnResolver, ColumnResolver columnResolver2) {
        }

        void getSQL(StringBuilder stringBuilder, int n, Column column) {
            throw DbException.getInternalError();
        }
    }
}

