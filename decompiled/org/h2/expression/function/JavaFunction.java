/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.ValueExpression;
import org.h2.expression.function.NamedExpression;
import org.h2.message.DbException;
import org.h2.schema.FunctionAlias;
import org.h2.table.ColumnResolver;
import org.h2.table.TableFilter;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

public final class JavaFunction
extends Expression
implements NamedExpression {
    private final FunctionAlias functionAlias;
    private final FunctionAlias.JavaMethod javaMethod;
    private final Expression[] args;

    public JavaFunction(FunctionAlias functionAlias, Expression[] expressionArray) {
        this.functionAlias = functionAlias;
        this.javaMethod = functionAlias.findJavaMethod(expressionArray);
        if (this.javaMethod.getDataType() == null) {
            throw DbException.get(90022, this.getName());
        }
        this.args = expressionArray;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        return this.javaMethod.getValue(sessionLocal, this.args, false);
    }

    @Override
    public TypeInfo getType() {
        return this.javaMethod.getDataType();
    }

    @Override
    public void mapColumns(ColumnResolver columnResolver, int n, int n2) {
        for (Expression expression : this.args) {
            expression.mapColumns(columnResolver, n, n2);
        }
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        boolean bl = this.functionAlias.isDeterministic();
        int n = this.args.length;
        for (int i = 0; i < n; ++i) {
            Expression expression;
            this.args[i] = expression = this.args[i].optimize(sessionLocal);
            bl &= expression.isConstant();
        }
        if (bl) {
            return ValueExpression.get(this.getValue(sessionLocal));
        }
        return this;
    }

    @Override
    public void setEvaluatable(TableFilter tableFilter, boolean bl) {
        for (Expression expression : this.args) {
            if (expression == null) continue;
            expression.setEvaluatable(tableFilter, bl);
        }
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        return JavaFunction.writeExpressions(this.functionAlias.getSQL(stringBuilder, n).append('('), this.args, n).append(')');
    }

    @Override
    public void updateAggregate(SessionLocal sessionLocal, int n) {
        for (Expression expression : this.args) {
            if (expression == null) continue;
            expression.updateAggregate(sessionLocal, n);
        }
    }

    @Override
    public String getName() {
        return this.functionAlias.getName();
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        switch (expressionVisitor.getType()) {
            case 2: 
            case 5: 
            case 8: {
                if (this.functionAlias.isDeterministic()) break;
                return false;
            }
            case 7: {
                expressionVisitor.addDependency(this.functionAlias);
                break;
            }
        }
        for (Expression expression : this.args) {
            if (expression == null || expression.isEverything(expressionVisitor)) continue;
            return false;
        }
        return true;
    }

    @Override
    public int getCost() {
        int n = this.javaMethod.hasConnectionParam() ? 25 : 5;
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

