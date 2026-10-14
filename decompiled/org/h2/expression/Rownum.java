/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression;

import org.h2.command.Prepared;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.Operation0;
import org.h2.expression.ValueExpression;
import org.h2.message.DbException;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBigint;

public final class Rownum
extends Operation0 {
    private final Prepared prepared;
    private boolean singleRow;

    public Rownum(Prepared prepared) {
        if (prepared == null) {
            throw DbException.getInternalError();
        }
        this.prepared = prepared;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        return ValueBigint.get(this.prepared.getCurrentRowNumber());
    }

    @Override
    public TypeInfo getType() {
        return TypeInfo.TYPE_BIGINT;
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        return stringBuilder.append("ROWNUM()");
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        return this.singleRow ? ValueExpression.get(ValueBigint.get(1L)) : this;
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        switch (expressionVisitor.getType()) {
            case 0: 
            case 1: 
            case 2: 
            case 3: 
            case 8: {
                return false;
            }
            case 11: {
                if (expressionVisitor.getQueryLevel() <= 0) break;
                this.singleRow = true;
            }
        }
        return true;
    }

    @Override
    public int getCost() {
        return 0;
    }
}

