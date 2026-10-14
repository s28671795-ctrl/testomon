/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression;

import java.util.ArrayList;
import org.h2.command.query.Query;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.message.DbException;
import org.h2.result.ResultInterface;
import org.h2.table.ColumnResolver;
import org.h2.table.TableFilter;
import org.h2.util.StringUtils;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueArray;

public final class ArrayConstructorByQuery
extends Expression {
    private final Query query;
    private TypeInfo componentType;
    private TypeInfo type;

    public ArrayConstructorByQuery(Query query) {
        this.query = query;
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        return StringUtils.indent(stringBuilder.append("ARRAY ("), this.query.getPlanSQL(n), 4, false).append(')');
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        this.query.setSession(sessionLocal);
        ArrayList<Value> arrayList = new ArrayList<Value>();
        try (ResultInterface resultInterface = this.query.query(0L);){
            while (resultInterface.next()) {
                arrayList.add(resultInterface.currentRow()[0]);
            }
        }
        return ValueArray.get(this.componentType, arrayList.toArray(new Value[0]), sessionLocal);
    }

    @Override
    public TypeInfo getType() {
        return this.type;
    }

    @Override
    public void mapColumns(ColumnResolver columnResolver, int n, int n2) {
        this.query.mapColumns(columnResolver, n + 1);
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.query.prepare();
        if (this.query.getColumnCount() != 1) {
            throw DbException.get(90052);
        }
        this.componentType = this.query.getExpressions().get(0).getType();
        this.type = TypeInfo.getTypeInfo(40, -1L, -1, this.componentType);
        return this;
    }

    @Override
    public void setEvaluatable(TableFilter tableFilter, boolean bl) {
        this.query.setEvaluatable(tableFilter, bl);
    }

    @Override
    public void updateAggregate(SessionLocal sessionLocal, int n) {
        this.query.updateAggregate(sessionLocal, n);
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        return this.query.isEverything(expressionVisitor);
    }

    @Override
    public int getCost() {
        return this.query.getCostAsExpression();
    }
}

