/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression;

import java.util.ArrayList;
import java.util.HashMap;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionColumn;
import org.h2.expression.ExpressionVisitor;
import org.h2.message.DbException;
import org.h2.table.Column;
import org.h2.table.ColumnResolver;
import org.h2.table.TableFilter;
import org.h2.util.StringUtils;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

public final class Wildcard
extends Expression {
    private final String schema;
    private final String table;
    private ArrayList<ExpressionColumn> exceptColumns;

    public Wildcard(String string, String string2) {
        this.schema = string;
        this.table = string2;
    }

    public ArrayList<ExpressionColumn> getExceptColumns() {
        return this.exceptColumns;
    }

    public void setExceptColumns(ArrayList<ExpressionColumn> arrayList) {
        this.exceptColumns = arrayList;
    }

    public HashMap<Column, ExpressionColumn> mapExceptColumns() {
        HashMap<Column, ExpressionColumn> hashMap = new HashMap<Column, ExpressionColumn>();
        for (ExpressionColumn expressionColumn : this.exceptColumns) {
            Column column = expressionColumn.getColumn();
            if (column == null) {
                throw expressionColumn.getColumnException(42122);
            }
            if (hashMap.putIfAbsent(column, expressionColumn) == null) continue;
            throw expressionColumn.getColumnException(42121);
        }
        return hashMap;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        throw DbException.getInternalError(this.toString());
    }

    @Override
    public TypeInfo getType() {
        throw DbException.getInternalError(this.toString());
    }

    @Override
    public void mapColumns(ColumnResolver columnResolver, int n, int n2) {
        if (this.exceptColumns != null) {
            for (ExpressionColumn expressionColumn : this.exceptColumns) {
                expressionColumn.mapColumns(columnResolver, n, n2);
            }
        }
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        throw DbException.get(42000, this.table);
    }

    @Override
    public void setEvaluatable(TableFilter tableFilter, boolean bl) {
        throw DbException.getInternalError(this.toString());
    }

    @Override
    public String getTableAlias() {
        return this.table;
    }

    @Override
    public String getSchemaName() {
        return this.schema;
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        if (this.table != null) {
            StringUtils.quoteIdentifier(stringBuilder, this.table).append('.');
        }
        stringBuilder.append('*');
        if (this.exceptColumns != null) {
            Wildcard.writeExpressions(stringBuilder.append(" EXCEPT ("), this.exceptColumns, n).append(')');
        }
        return stringBuilder;
    }

    @Override
    public void updateAggregate(SessionLocal sessionLocal, int n) {
        throw DbException.getInternalError(this.toString());
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        if (expressionVisitor.getType() == 8) {
            return true;
        }
        throw DbException.getInternalError(Integer.toString(expressionVisitor.getType()));
    }

    @Override
    public int getCost() {
        throw DbException.getInternalError(this.toString());
    }
}

