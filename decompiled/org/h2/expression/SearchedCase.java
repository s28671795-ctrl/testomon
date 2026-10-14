/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression;

import org.h2.engine.CastDataProvider;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.OperationN;
import org.h2.expression.SimpleCase;
import org.h2.expression.ValueExpression;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueNull;

public final class SearchedCase
extends OperationN {
    public SearchedCase() {
        super(new Expression[4]);
    }

    public SearchedCase(Expression[] expressionArray) {
        super(expressionArray);
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        int n = this.args.length - 1;
        for (int i = 0; i < n; i += 2) {
            if (!this.args[i].getBooleanValue(sessionLocal)) continue;
            return this.args[i + 1].getValue(sessionLocal).convertTo(this.type, (CastDataProvider)sessionLocal);
        }
        if ((n & 1) == 0) {
            return this.args[n].getValue(sessionLocal).convertTo(this.type, (CastDataProvider)sessionLocal);
        }
        return ValueNull.INSTANCE;
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        TypeInfo typeInfo = TypeInfo.TYPE_UNKNOWN;
        int n = this.args.length - 1;
        boolean bl = true;
        for (int i = 0; i < n; i += 2) {
            Expression expression = this.args[i].optimize(sessionLocal);
            Expression expression2 = this.args[i + 1].optimize(sessionLocal);
            if (bl) {
                if (expression.isConstant()) {
                    if (expression.getBooleanValue(sessionLocal)) {
                        return expression2;
                    }
                } else {
                    bl = false;
                }
            }
            this.args[i] = expression;
            this.args[i + 1] = expression2;
            typeInfo = SimpleCase.combineTypes(typeInfo, expression2);
        }
        if ((n & 1) == 0) {
            Expression expression = this.args[n].optimize(sessionLocal);
            if (bl) {
                return expression;
            }
            this.args[n] = expression;
            typeInfo = SimpleCase.combineTypes(typeInfo, expression);
        } else if (bl) {
            return ValueExpression.NULL;
        }
        if (typeInfo.getValueType() == -1) {
            typeInfo = TypeInfo.TYPE_VARCHAR;
        }
        this.type = typeInfo;
        return this;
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        stringBuilder.append("CASE");
        int n2 = this.args.length - 1;
        for (int i = 0; i < n2; i += 2) {
            stringBuilder.append(" WHEN ");
            this.args[i].getUnenclosedSQL(stringBuilder, n);
            stringBuilder.append(" THEN ");
            this.args[i + 1].getUnenclosedSQL(stringBuilder, n);
        }
        if ((n2 & 1) == 0) {
            stringBuilder.append(" ELSE ");
            this.args[n2].getUnenclosedSQL(stringBuilder, n);
        }
        return stringBuilder.append(" END");
    }
}

