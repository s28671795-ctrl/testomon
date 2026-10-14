/*
 * Decompiled with CFR 0.152.
 */
package org.h2.table;

import java.util.ArrayList;
import org.h2.command.query.TableValueConstructor;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.result.ResultInterface;
import org.h2.result.SimpleResult;
import org.h2.schema.Schema;
import org.h2.table.Column;
import org.h2.table.VirtualConstructedTable;

public class TableValueConstructorTable
extends VirtualConstructedTable {
    private final ArrayList<ArrayList<Expression>> rows;

    public TableValueConstructorTable(Schema schema, SessionLocal sessionLocal, Column[] columnArray, ArrayList<ArrayList<Expression>> arrayList) {
        super(schema, 0, "VALUES");
        this.setColumns(columnArray);
        this.rows = arrayList;
    }

    @Override
    public boolean canGetRowCount(SessionLocal sessionLocal) {
        return true;
    }

    @Override
    public long getRowCount(SessionLocal sessionLocal) {
        return this.rows.size();
    }

    @Override
    public long getRowCountApproximation(SessionLocal sessionLocal) {
        return this.rows.size();
    }

    @Override
    public ResultInterface getResult(SessionLocal sessionLocal) {
        SimpleResult simpleResult = new SimpleResult();
        for (Column column : this.columns) {
            simpleResult.addColumn(column.getName(), column.getType());
        }
        TableValueConstructor.getVisibleResult(sessionLocal, simpleResult, this.columns, this.rows);
        return simpleResult;
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        stringBuilder.append('(');
        TableValueConstructor.getValuesSQL(stringBuilder, n, this.rows);
        return stringBuilder.append(')');
    }

    @Override
    public boolean isDeterministic() {
        return true;
    }
}

