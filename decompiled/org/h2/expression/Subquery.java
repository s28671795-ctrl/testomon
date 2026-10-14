/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import org.h2.command.query.Query;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionList;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.ValueExpression;
import org.h2.message.DbException;
import org.h2.result.ResultInterface;
import org.h2.table.ColumnResolver;
import org.h2.table.TableFilter;
import org.h2.value.ExtTypeInfoRow;
import org.h2.value.TypeInfo;
import org.h2.value.Typed;
import org.h2.value.Value;
import org.h2.value.ValueNull;
import org.h2.value.ValueRow;

public final class Subquery
extends Expression {
    private final Query query;
    private Expression expression;
    private Value nullValue;
    private HashSet<ColumnResolver> outerResolvers = new HashSet();

    public Subquery(Query query) {
        this.query = query;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        this.query.setSession(sessionLocal);
        try (ResultInterface resultInterface = this.query.query(2L);){
            if (!resultInterface.next()) {
                Value value = this.nullValue;
                return value;
            }
            Value value = this.readRow(resultInterface);
            if (resultInterface.hasNext()) {
                throw DbException.get(90053);
            }
            Value value2 = value;
            return value2;
        }
    }

    public ArrayList<Value> getAllRows(SessionLocal sessionLocal) {
        ArrayList<Value> arrayList = new ArrayList<Value>();
        this.query.setSession(sessionLocal);
        try (ResultInterface resultInterface = this.query.query(Integer.MAX_VALUE);){
            while (resultInterface.next()) {
                arrayList.add(this.readRow(resultInterface));
            }
        }
        return arrayList;
    }

    private Value readRow(ResultInterface resultInterface) {
        Value[] valueArray = resultInterface.currentRow();
        int n = resultInterface.getVisibleColumnCount();
        return n == 1 ? valueArray[0] : ValueRow.get(this.getType(), n == valueArray.length ? valueArray : Arrays.copyOf(valueArray, n));
    }

    @Override
    public TypeInfo getType() {
        return this.expression.getType();
    }

    @Override
    public void mapColumns(ColumnResolver columnResolver, int n, int n2) {
        this.outerResolvers.add(columnResolver);
        this.query.mapColumns(columnResolver, n + 1);
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        Expression expression;
        this.query.prepare();
        if (this.query.isConstantQuery()) {
            this.setType();
            return ValueExpression.get(this.getValue(sessionLocal));
        }
        if (this.outerResolvers != null && sessionLocal.getDatabase().getSettings().optimizeSimpleSingleRowSubqueries && (expression = this.query.getIfSingleRow()) != null && expression.isEverything(ExpressionVisitor.getDecrementQueryLevelVisitor(this.outerResolvers, 0))) {
            expression.isEverything(ExpressionVisitor.getDecrementQueryLevelVisitor(this.outerResolvers, 1));
            return expression.optimize(sessionLocal);
        }
        this.outerResolvers = null;
        this.setType();
        return this;
    }

    private void setType() {
        ArrayList<Expression> arrayList = this.query.getExpressions();
        int n = this.query.getColumnCount();
        if (n == 1) {
            this.expression = arrayList.get(0);
            this.nullValue = ValueNull.INSTANCE;
        } else {
            Typed[] typedArray = new Expression[n];
            Value[] valueArray = new Value[n];
            for (int i = 0; i < n; ++i) {
                typedArray[i] = arrayList.get(i);
                valueArray[i] = ValueNull.INSTANCE;
            }
            ExpressionList expressionList = new ExpressionList((Expression[])typedArray, false);
            expressionList.initializeType();
            this.expression = expressionList;
            this.nullValue = ValueRow.get(new ExtTypeInfoRow(typedArray), valueArray);
        }
    }

    @Override
    public void setEvaluatable(TableFilter tableFilter, boolean bl) {
        this.query.setEvaluatable(tableFilter, bl);
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        return stringBuilder.append('(').append(this.query.getPlanSQL(n)).append(')');
    }

    @Override
    public void updateAggregate(SessionLocal sessionLocal, int n) {
        this.query.updateAggregate(sessionLocal, n);
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        return this.query.isEverything(expressionVisitor);
    }

    public Query getQuery() {
        return this.query;
    }

    @Override
    public int getCost() {
        return this.query.getCostAsExpression();
    }

    @Override
    public boolean isConstant() {
        return this.query.isConstantQuery();
    }
}

