/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression;

import java.util.Arrays;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.ExpressionWithVariableParameters;
import org.h2.message.DbException;
import org.h2.table.ColumnResolver;
import org.h2.table.TableFilter;
import org.h2.value.TypeInfo;

public abstract class OperationN
extends Expression
implements ExpressionWithVariableParameters {
    protected Expression[] args;
    protected int argsCount;
    protected TypeInfo type;

    protected OperationN(Expression[] expressionArray) {
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

    @Override
    public TypeInfo getType() {
        return this.type;
    }

    @Override
    public void mapColumns(ColumnResolver columnResolver, int n, int n2) {
        for (Expression expression : this.args) {
            expression.mapColumns(columnResolver, n, n2);
        }
    }

    protected boolean optimizeArguments(SessionLocal sessionLocal, boolean bl) {
        int n = this.args.length;
        for (int i = 0; i < n; ++i) {
            Expression expression;
            this.args[i] = expression = this.args[i].optimize(sessionLocal);
            if (!bl || expression.isConstant()) continue;
            bl = false;
        }
        return bl;
    }

    @Override
    public void setEvaluatable(TableFilter tableFilter, boolean bl) {
        for (Expression expression : this.args) {
            expression.setEvaluatable(tableFilter, bl);
        }
    }

    @Override
    public void updateAggregate(SessionLocal sessionLocal, int n) {
        for (Expression expression : this.args) {
            expression.updateAggregate(sessionLocal, n);
        }
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        for (Expression expression : this.args) {
            if (expression.isEverything(expressionVisitor)) continue;
            return false;
        }
        return true;
    }

    @Override
    public int getCost() {
        int n = this.args.length + 1;
        for (Expression expression : this.args) {
            n += expression.getCost();
        }
        return n;
    }

    @Override
    public int getSubexpressionCount() {
        return this.args.length;
    }

    @Override
    public Expression getSubexpression(int n) {
        return this.args[n];
    }
}

