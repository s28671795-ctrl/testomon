/*
 * Decompiled with CFR 0.152.
 */
package org.h2.table;

import org.h2.engine.SessionLocal;
import org.h2.expression.function.table.TableFunction;
import org.h2.result.ResultInterface;
import org.h2.schema.Schema;
import org.h2.table.Column;
import org.h2.table.VirtualConstructedTable;

public class FunctionTable
extends VirtualConstructedTable {
    private final TableFunction function;

    public FunctionTable(Schema schema, SessionLocal sessionLocal, TableFunction tableFunction) {
        super(schema, 0, tableFunction.getName());
        this.function = tableFunction;
        tableFunction.optimize(sessionLocal);
        ResultInterface resultInterface = tableFunction.getValueTemplate(sessionLocal);
        int n = resultInterface.getVisibleColumnCount();
        Column[] columnArray = new Column[n];
        for (int i = 0; i < n; ++i) {
            columnArray[i] = new Column(resultInterface.getColumnName(i), resultInterface.getColumnType(i));
        }
        this.setColumns(columnArray);
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
        return this.function.getValue(sessionLocal);
    }

    @Override
    public String getSQL(int n) {
        return this.function.getSQL(n);
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        return stringBuilder.append(this.function.getSQL(n));
    }

    @Override
    public boolean isDeterministic() {
        return this.function.isDeterministic();
    }
}

