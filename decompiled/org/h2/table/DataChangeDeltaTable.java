/*
 * Decompiled with CFR 0.152.
 */
package org.h2.table;

import org.h2.command.dml.DataChangeStatement;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionColumn;
import org.h2.result.LocalResult;
import org.h2.result.ResultInterface;
import org.h2.result.ResultTarget;
import org.h2.result.Row;
import org.h2.schema.Schema;
import org.h2.table.Column;
import org.h2.table.Table;
import org.h2.table.VirtualConstructedTable;

public class DataChangeDeltaTable
extends VirtualConstructedTable {
    private final DataChangeStatement statement;
    private final ResultOption resultOption;
    private final Expression[] expressions;

    public static void collectInsertedFinalRow(SessionLocal sessionLocal, Table table, ResultTarget resultTarget, ResultOption resultOption, Row row) {
        Column column;
        if (sessionLocal.getMode().takeInsertedIdentity && (column = table.getIdentityColumn()) != null) {
            sessionLocal.setLastIdentity(row.getValue(column.getColumnId()));
        }
        if (resultOption == ResultOption.FINAL) {
            resultTarget.addRow(row.getValueList());
        }
    }

    public DataChangeDeltaTable(Schema schema, SessionLocal sessionLocal, DataChangeStatement dataChangeStatement, ResultOption resultOption) {
        super(schema, 0, dataChangeStatement.getStatementName());
        this.statement = dataChangeStatement;
        this.resultOption = resultOption;
        Table table = dataChangeStatement.getTable();
        Column[] columnArray = table.getColumns();
        int n = columnArray.length;
        Column[] columnArray2 = new Column[n];
        for (int i = 0; i < n; ++i) {
            columnArray2[i] = columnArray[i].getClone();
        }
        this.setColumns(columnArray2);
        Expression[] expressionArray = new Expression[n];
        String string = this.getName();
        for (int i = 0; i < n; ++i) {
            expressionArray[i] = new ExpressionColumn(this.database, null, string, columnArray2[i].getName());
        }
        this.expressions = expressionArray;
    }

    @Override
    public boolean canGetRowCount(SessionLocal sessionLocal) {
        return false;
    }

    @Override
    public long getRowCount(SessionLocal sessionLocal) {
        return Long.MAX_VALUE;
    }

    @Override
    public long getRowCountApproximation(SessionLocal sessionLocal) {
        return Long.MAX_VALUE;
    }

    @Override
    public ResultInterface getResult(SessionLocal sessionLocal) {
        this.statement.prepare();
        int n = this.expressions.length;
        LocalResult localResult = new LocalResult(sessionLocal, this.expressions, n, n);
        localResult.setForDataChangeDeltaTable();
        this.statement.update(localResult, this.resultOption);
        return localResult;
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        return stringBuilder.append(this.resultOption.name()).append(" TABLE (").append(this.statement.getSQL()).append(')');
    }

    @Override
    public boolean isDeterministic() {
        return false;
    }

    public static enum ResultOption {
        OLD,
        NEW,
        FINAL;

    }
}

