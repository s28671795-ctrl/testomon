/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.query;

import java.util.ArrayList;
import org.h2.command.query.Select;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionColumn;
import org.h2.table.Column;
import org.h2.table.ColumnResolver;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

public class SelectListColumnResolver
implements ColumnResolver {
    private final Select select;
    private final Expression[] expressions;
    private final Column[] columns;

    SelectListColumnResolver(Select select) {
        this.select = select;
        int n = select.getColumnCount();
        this.columns = new Column[n];
        this.expressions = new Expression[n];
        ArrayList<Expression> arrayList = select.getExpressions();
        SessionLocal sessionLocal = select.getSession();
        for (int i = 0; i < n; ++i) {
            Expression expression = arrayList.get(i);
            this.columns[i] = new Column(expression.getAlias(sessionLocal, i), TypeInfo.TYPE_NULL, null, i);
            this.expressions[i] = expression.getNonAliasExpression();
        }
    }

    @Override
    public Column[] getColumns() {
        return this.columns;
    }

    @Override
    public Column findColumn(String string) {
        Database database = this.select.getSession().getDatabase();
        for (Column column : this.columns) {
            if (!database.equalsIdentifiers(column.getName(), string)) continue;
            return column;
        }
        return null;
    }

    @Override
    public Select getSelect() {
        return this.select;
    }

    @Override
    public Value getValue(Column column) {
        return null;
    }

    @Override
    public Expression optimize(ExpressionColumn expressionColumn, Column column) {
        return this.expressions[column.getColumnId()];
    }
}

