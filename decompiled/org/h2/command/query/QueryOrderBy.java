/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.query;

import org.h2.expression.Expression;
import org.h2.result.SortOrder;

public class QueryOrderBy {
    public Expression expression;
    public Expression columnIndexExpr;
    public int sortType;

    public void getSQL(StringBuilder stringBuilder, int n) {
        (this.expression != null ? this.expression : this.columnIndexExpr).getUnenclosedSQL(stringBuilder, n);
        SortOrder.typeToString(stringBuilder, this.sortType);
    }
}

