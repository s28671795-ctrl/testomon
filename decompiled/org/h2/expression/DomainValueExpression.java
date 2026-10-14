/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression;

import org.h2.constraint.DomainColumnResolver;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.Operation0;
import org.h2.message.DbException;
import org.h2.table.ColumnResolver;
import org.h2.util.ParserUtil;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

public final class DomainValueExpression
extends Operation0 {
    private DomainColumnResolver columnResolver;

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        return this.columnResolver.getValue(null);
    }

    @Override
    public TypeInfo getType() {
        return this.columnResolver.getValueType();
    }

    @Override
    public void mapColumns(ColumnResolver columnResolver, int n, int n2) {
        if (columnResolver instanceof DomainColumnResolver) {
            this.columnResolver = (DomainColumnResolver)columnResolver;
        }
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        if (this.columnResolver == null) {
            throw DbException.get(42122, "VALUE");
        }
        return this;
    }

    @Override
    public boolean isValueSet() {
        return this.columnResolver.getValue(null) != null;
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        String string;
        if (this.columnResolver != null && (string = this.columnResolver.getColumnName()) != null) {
            return ParserUtil.quoteIdentifier(stringBuilder, string, n);
        }
        return stringBuilder.append("VALUE");
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        return true;
    }

    @Override
    public int getCost() {
        return 1;
    }
}

