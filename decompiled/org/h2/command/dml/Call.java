/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.dml;

import org.h2.command.Prepared;
import org.h2.engine.SessionLocal;
import org.h2.expression.Alias;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionColumn;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.function.table.TableFunction;
import org.h2.result.LocalResult;
import org.h2.result.ResultInterface;
import org.h2.table.Column;
import org.h2.value.Value;

public class Call
extends Prepared {
    private Expression expression;
    private TableFunction tableFunction;
    private Expression[] expressions;

    public Call(SessionLocal sessionLocal) {
        super(sessionLocal);
    }

    @Override
    public ResultInterface queryMeta() {
        int n = this.expressions.length;
        LocalResult localResult = new LocalResult(this.session, this.expressions, n, n);
        localResult.done();
        return localResult;
    }

    @Override
    public long update() {
        if (this.tableFunction != null) {
            return super.update();
        }
        Value value = this.expression.getValue(this.session);
        int n = value.getValueType();
        switch (n) {
            case -1: 
            case 0: {
                return 0L;
            }
        }
        return value.getInt();
    }

    @Override
    public ResultInterface query(long l) {
        this.setCurrentRowNumber(1L);
        if (this.tableFunction != null) {
            return this.tableFunction.getValue(this.session);
        }
        LocalResult localResult = new LocalResult(this.session, this.expressions, 1, 1);
        localResult.addRow(this.expression.getValue(this.session));
        localResult.done();
        return localResult;
    }

    @Override
    public void prepare() {
        if (this.tableFunction != null) {
            this.prepareAlways = true;
            this.tableFunction.optimize(this.session);
            ResultInterface resultInterface = this.tableFunction.getValueTemplate(this.session);
            int n = resultInterface.getVisibleColumnCount();
            this.expressions = new Expression[n];
            for (int i = 0; i < n; ++i) {
                String string = resultInterface.getColumnName(i);
                String string2 = resultInterface.getAlias(i);
                Expression expression = new ExpressionColumn(this.getDatabase(), new Column(string, resultInterface.getColumnType(i)));
                if (!string2.equals(string)) {
                    expression = new Alias(expression, string2, false);
                }
                this.expressions[i] = expression;
            }
        } else {
            this.expression = this.expression.optimize(this.session);
            this.expressions = new Expression[]{this.expression};
        }
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public void setTableFunction(TableFunction tableFunction) {
        this.tableFunction = tableFunction;
    }

    @Override
    public boolean isQuery() {
        return true;
    }

    @Override
    public boolean isTransactional() {
        return true;
    }

    @Override
    public boolean isReadOnly() {
        return this.tableFunction == null && this.expression.isEverything(ExpressionVisitor.READONLY_VISITOR);
    }

    @Override
    public int getType() {
        return 57;
    }

    @Override
    public boolean isCacheable() {
        return this.tableFunction == null;
    }
}

