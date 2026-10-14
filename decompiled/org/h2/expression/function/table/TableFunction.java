/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function.table;

import java.util.Arrays;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionWithVariableParameters;
import org.h2.expression.function.NamedExpression;
import org.h2.message.DbException;
import org.h2.result.ResultInterface;
import org.h2.util.HasSQL;

public abstract class TableFunction
implements HasSQL,
NamedExpression,
ExpressionWithVariableParameters {
    protected Expression[] args;
    private int argsCount;

    protected TableFunction(Expression[] expressionArray) {
        this.args = expressionArray;
    }

    @Override
    public void addParameter(Expression expression) {
        int n = this.args.length;
        if (this.argsCount >= n) {
            this.args = Arrays.copyOf(this.args, n * 2);
        }
        this.args[this.argsCount++] = expression;
    }

    @Override
    public void doneWithParameters() throws DbException {
        if (this.args.length != this.argsCount) {
            this.args = Arrays.copyOf(this.args, this.argsCount);
        }
    }

    public abstract ResultInterface getValue(SessionLocal var1);

    public abstract ResultInterface getValueTemplate(SessionLocal var1);

    public void optimize(SessionLocal sessionLocal) {
        int n = this.args.length;
        for (int i = 0; i < n; ++i) {
            this.args[i] = this.args[i].optimize(sessionLocal);
        }
    }

    public abstract boolean isDeterministic();

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        return Expression.writeExpressions(stringBuilder.append(this.getName()).append('('), this.args, n).append(')');
    }
}

