/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionColumn;
import org.h2.expression.ExpressionVisitor;
import org.h2.table.ColumnResolver;
import org.h2.table.TableFilter;
import org.h2.util.ParserUtil;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

public final class Alias
extends Expression {
    private final String alias;
    private Expression expr;
    private final boolean aliasColumnName;

    public Alias(Expression expression, String string, boolean bl) {
        this.expr = expression;
        this.alias = string;
        this.aliasColumnName = bl;
    }

    @Override
    public Expression getNonAliasExpression() {
        return this.expr;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        return this.expr.getValue(sessionLocal);
    }

    @Override
    public TypeInfo getType() {
        return this.expr.getType();
    }

    @Override
    public void mapColumns(ColumnResolver columnResolver, int n, int n2) {
        this.expr.mapColumns(columnResolver, n, n2);
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.expr = this.expr.optimize(sessionLocal);
        return this;
    }

    @Override
    public void setEvaluatable(TableFilter tableFilter, boolean bl) {
        this.expr.setEvaluatable(tableFilter, bl);
    }

    @Override
    public boolean isIdentity() {
        return this.expr.isIdentity();
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        this.expr.getUnenclosedSQL(stringBuilder, n).append(" AS ");
        return ParserUtil.quoteIdentifier(stringBuilder, this.alias, n);
    }

    @Override
    public void updateAggregate(SessionLocal sessionLocal, int n) {
        this.expr.updateAggregate(sessionLocal, n);
    }

    @Override
    public String getAlias(SessionLocal sessionLocal, int n) {
        return this.alias;
    }

    @Override
    public String getColumnNameForView(SessionLocal sessionLocal, int n) {
        return this.alias;
    }

    @Override
    public int getNullable() {
        return this.expr.getNullable();
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        return this.expr.isEverything(expressionVisitor);
    }

    @Override
    public int getCost() {
        return this.expr.getCost();
    }

    @Override
    public String getSchemaName() {
        if (this.aliasColumnName) {
            return null;
        }
        return this.expr.getSchemaName();
    }

    @Override
    public String getTableName() {
        if (this.aliasColumnName) {
            return null;
        }
        return this.expr.getTableName();
    }

    @Override
    public String getColumnName(SessionLocal sessionLocal, int n) {
        if (!(this.expr instanceof ExpressionColumn) || this.aliasColumnName) {
            return this.alias;
        }
        return this.expr.getColumnName(sessionLocal, n);
    }
}

