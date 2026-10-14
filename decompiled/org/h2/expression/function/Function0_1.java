/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.function.NamedExpression;
import org.h2.table.ColumnResolver;
import org.h2.table.TableFilter;
import org.h2.value.TypeInfo;

public abstract class Function0_1
extends Expression
implements NamedExpression {
    protected Expression arg;
    protected TypeInfo type;

    protected Function0_1(Expression expression) {
        this.arg = expression;
    }

    @Override
    public TypeInfo getType() {
        return this.type;
    }

    @Override
    public void mapColumns(ColumnResolver columnResolver, int n, int n2) {
        if (this.arg != null) {
            this.arg.mapColumns(columnResolver, n, n2);
        }
    }

    @Override
    public void setEvaluatable(TableFilter tableFilter, boolean bl) {
        if (this.arg != null) {
            this.arg.setEvaluatable(tableFilter, bl);
        }
    }

    @Override
    public void updateAggregate(SessionLocal sessionLocal, int n) {
        if (this.arg != null) {
            this.arg.updateAggregate(sessionLocal, n);
        }
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        return this.arg == null || this.arg.isEverything(expressionVisitor);
    }

    @Override
    public int getCost() {
        int n = 1;
        if (this.arg != null) {
            n += this.arg.getCost();
        }
        return n;
    }

    @Override
    public int getSubexpressionCount() {
        return this.arg != null ? 1 : 0;
    }

    @Override
    public Expression getSubexpression(int n) {
        if (n == 0 && this.arg != null) {
            return this.arg;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        stringBuilder.append(this.getName()).append('(');
        if (this.arg != null) {
            this.arg.getUnenclosedSQL(stringBuilder, n);
        }
        return stringBuilder.append(')');
    }
}

