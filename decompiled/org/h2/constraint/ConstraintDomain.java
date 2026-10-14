/*
 * Decompiled with CFR 0.152.
 */
package org.h2.constraint;

import java.util.HashSet;
import org.h2.command.Parser;
import org.h2.command.ddl.AlterDomain;
import org.h2.command.query.AllColumnsForPlan;
import org.h2.constraint.Constraint;
import org.h2.constraint.DomainColumnResolver;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.index.Index;
import org.h2.message.DbException;
import org.h2.result.Row;
import org.h2.schema.Domain;
import org.h2.schema.Schema;
import org.h2.table.Column;
import org.h2.table.PlanItem;
import org.h2.table.Table;
import org.h2.table.TableFilter;
import org.h2.util.StringUtils;
import org.h2.value.Value;
import org.h2.value.ValueNull;

public class ConstraintDomain
extends Constraint {
    private Domain domain;
    private Expression expr;
    private DomainColumnResolver resolver;

    public ConstraintDomain(Schema schema, int n, String string, Domain domain) {
        super(schema, n, string, null);
        this.domain = domain;
        this.resolver = new DomainColumnResolver(domain.getDataType());
    }

    @Override
    public Constraint.Type getConstraintType() {
        return Constraint.Type.DOMAIN;
    }

    public Domain getDomain() {
        return this.domain;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setExpression(SessionLocal sessionLocal, Expression expression) {
        expression.mapColumns(this.resolver, 0, 0);
        expression = expression.optimize(sessionLocal);
        ConstraintDomain constraintDomain = this;
        synchronized (constraintDomain) {
            this.resolver.setValue(ValueNull.INSTANCE);
            expression.getValue(sessionLocal);
        }
        this.expr = expression;
    }

    @Override
    public String getCreateSQLWithoutIndexes() {
        return this.getCreateSQL();
    }

    @Override
    public String getCreateSQL() {
        StringBuilder stringBuilder = new StringBuilder("ALTER DOMAIN ");
        this.domain.getSQL(stringBuilder, 0).append(" ADD CONSTRAINT ");
        this.getSQL(stringBuilder, 0);
        if (this.comment != null) {
            stringBuilder.append(" COMMENT ");
            StringUtils.quoteStringSQL(stringBuilder, this.comment);
        }
        stringBuilder.append(" CHECK");
        this.expr.getEnclosedSQL(stringBuilder, 0).append(" NOCHECK");
        return stringBuilder.toString();
    }

    @Override
    public void removeChildrenAndResources(SessionLocal sessionLocal) {
        this.domain.removeConstraint(this);
        this.database.removeMeta(sessionLocal, this.getId());
        this.domain = null;
        this.expr = null;
        this.invalidate();
    }

    @Override
    public void checkRow(SessionLocal sessionLocal, Table table, Row row, Row row2) {
        throw DbException.getInternalError(this.toString());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void check(SessionLocal sessionLocal, Value value) {
        Value value2;
        ConstraintDomain constraintDomain = this;
        synchronized (constraintDomain) {
            this.resolver.setValue(value);
            value2 = this.expr.getValue(sessionLocal);
        }
        if (value2.isFalse()) {
            throw DbException.get(23513, this.expr.getTraceSQL());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Expression getCheckConstraint(SessionLocal sessionLocal, String string) {
        String string2;
        if (string != null) {
            String string3;
            ConstraintDomain constraintDomain = this;
            synchronized (constraintDomain) {
                try {
                    this.resolver.setColumnName(string);
                    string3 = this.expr.getSQL(0);
                }
                finally {
                    this.resolver.resetColumnName();
                }
            }
            return new Parser(sessionLocal).parseExpression(string3);
        }
        ConstraintDomain constraintDomain = this;
        synchronized (constraintDomain) {
            string2 = this.expr.getSQL(0);
        }
        return new Parser(sessionLocal).parseDomainConstraintExpression(string2);
    }

    @Override
    public boolean usesIndex(Index index) {
        return false;
    }

    @Override
    public void setIndexOwner(Index index) {
        throw DbException.getInternalError(this.toString());
    }

    @Override
    public HashSet<Column> getReferencedColumns(Table table) {
        HashSet<Column> hashSet = new HashSet<Column>();
        this.expr.isEverything(ExpressionVisitor.getColumnsVisitor(hashSet, table));
        return hashSet;
    }

    @Override
    public Expression getExpression() {
        return this.expr;
    }

    @Override
    public boolean isBefore() {
        return true;
    }

    @Override
    public void checkExistingData(SessionLocal sessionLocal) {
        if (sessionLocal.getDatabase().isStarting()) {
            return;
        }
        new CheckExistingData(sessionLocal, this.domain);
    }

    @Override
    public void rebuild() {
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        return this.expr.isEverything(expressionVisitor);
    }

    private class CheckExistingData {
        private final SessionLocal session;

        CheckExistingData(SessionLocal sessionLocal, Domain domain) {
            this.session = sessionLocal;
            this.checkDomain(null, domain);
        }

        private boolean checkColumn(Domain domain, Column column) {
            Table table = column.getTable();
            TableFilter tableFilter = new TableFilter(this.session, table, null, true, null, 0, null);
            TableFilter[] tableFilterArray = new TableFilter[]{tableFilter};
            PlanItem planItem = tableFilter.getBestPlanItem(this.session, tableFilterArray, 0, new AllColumnsForPlan(tableFilterArray));
            tableFilter.setPlanItem(planItem);
            tableFilter.prepare();
            tableFilter.startQuery(this.session);
            tableFilter.reset();
            while (tableFilter.next()) {
                ConstraintDomain.this.check(this.session, tableFilter.getValue(column));
            }
            return false;
        }

        private boolean checkDomain(Domain domain, Domain domain2) {
            AlterDomain.forAllDependencies(this.session, domain2, this::checkColumn, this::checkDomain, false);
            return false;
        }
    }
}

