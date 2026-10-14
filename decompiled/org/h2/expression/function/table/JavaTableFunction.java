/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function.table;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.function.table.TableFunction;
import org.h2.message.DbException;
import org.h2.result.ResultInterface;
import org.h2.schema.FunctionAlias;

public final class JavaTableFunction
extends TableFunction {
    private final FunctionAlias functionAlias;
    private final FunctionAlias.JavaMethod javaMethod;

    public JavaTableFunction(FunctionAlias functionAlias, Expression[] expressionArray) {
        super(expressionArray);
        this.functionAlias = functionAlias;
        this.javaMethod = functionAlias.findJavaMethod(expressionArray);
        if (this.javaMethod.getDataType() != null) {
            throw DbException.get(90000, this.getName());
        }
    }

    @Override
    public ResultInterface getValue(SessionLocal sessionLocal) {
        return this.javaMethod.getTableValue(sessionLocal, this.args, false);
    }

    @Override
    public ResultInterface getValueTemplate(SessionLocal sessionLocal) {
        return this.javaMethod.getTableValue(sessionLocal, this.args, true);
    }

    @Override
    public void optimize(SessionLocal sessionLocal) {
        super.optimize(sessionLocal);
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        return Expression.writeExpressions(this.functionAlias.getSQL(stringBuilder, n).append('('), this.args, n).append(')');
    }

    @Override
    public String getName() {
        return this.functionAlias.getName();
    }

    @Override
    public boolean isDeterministic() {
        return this.functionAlias.isDeterministic();
    }
}

