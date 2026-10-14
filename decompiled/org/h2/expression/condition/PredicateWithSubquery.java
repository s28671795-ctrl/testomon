/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.condition;

import org.h2.command.query.Query;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.condition.Condition;
import org.h2.table.ColumnResolver;
import org.h2.table.TableFilter;
import org.h2.util.StringUtils;

abstract class PredicateWithSubquery
extends Condition {
    final Query query;

    PredicateWithSubquery(Query query) {
        this.query = query;
    }

    @Override
    public void mapColumns(ColumnResolver columnResolver, int n, int n2) {
        this.query.mapColumns(columnResolver, n + 1);
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.query.prepare();
        return this;
    }

    @Override
    public void setEvaluatable(TableFilter tableFilter, boolean bl) {
        this.query.setEvaluatable(tableFilter, bl);
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        return StringUtils.indent(stringBuilder.append('('), this.query.getPlanSQL(n), 4, false).append(')');
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

