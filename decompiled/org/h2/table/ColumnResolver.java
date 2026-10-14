/*
 * Decompiled with CFR 0.152.
 */
package org.h2.table;

import org.h2.command.query.Select;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionColumn;
import org.h2.table.Column;
import org.h2.table.TableFilter;
import org.h2.value.Value;

public interface ColumnResolver {
    default public String getTableAlias() {
        return null;
    }

    public Column[] getColumns();

    public Column findColumn(String var1);

    default public String getColumnName(Column column) {
        return column.getName();
    }

    default public boolean hasDerivedColumnList() {
        return false;
    }

    default public Column[] getSystemColumns() {
        return null;
    }

    default public Column getRowIdColumn() {
        return null;
    }

    default public String getSchemaName() {
        return null;
    }

    public Value getValue(Column var1);

    default public TableFilter getTableFilter() {
        return null;
    }

    default public Select getSelect() {
        return null;
    }

    default public Expression optimize(ExpressionColumn expressionColumn, Column column) {
        return expressionColumn;
    }
}

