/*
 * Decompiled with CFR 0.152.
 */
package org.h2.constraint;

import java.util.HashSet;
import org.h2.constraint.Constraint;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.index.Index;
import org.h2.message.DbException;
import org.h2.result.ResultInterface;
import org.h2.result.Row;
import org.h2.schema.Schema;
import org.h2.table.Column;
import org.h2.table.Table;
import org.h2.table.TableFilter;
import org.h2.util.StringUtils;
import org.h2.value.Value;

public class ConstraintCheck
extends Constraint {
    private TableFilter filter;
    private Expression expr;

    public ConstraintCheck(Schema schema, int n, String string, Table table) {
        super(schema, n, string, table);
    }

    @Override
    public Constraint.Type getConstraintType() {
        return Constraint.Type.CHECK;
    }

    public void setTableFilter(TableFilter tableFilter) {
        this.filter = tableFilter;
    }

    public void setExpression(Expression expression) {
        this.expr = expression;
    }

    @Override
    public String getCreateSQLForCopy(Table table, String string) {
        StringBuilder stringBuilder = new StringBuilder("ALTER TABLE ");
        table.getSQL(stringBuilder, 0).append(" ADD CONSTRAINT ");
        if (table.isHidden()) {
            stringBuilder.append("IF NOT EXISTS ");
        }
        stringBuilder.append(string);
        if (this.comment != null) {
            stringBuilder.append(" COMMENT ");
            StringUtils.quoteStringSQL(stringBuilder, this.comment);
        }
        stringBuilder.append(" CHECK");
        this.expr.getEnclosedSQL(stringBuilder, 0).append(" NOCHECK");
        return stringBuilder.toString();
    }

    private String getShortDescription() {
        StringBuilder stringBuilder = new StringBuilder().append(this.getName()).append(": ");
        this.expr.getTraceSQL();
        return stringBuilder.toString();
    }

    @Override
    public String getCreateSQLWithoutIndexes() {
        return this.getCreateSQL();
    }

    @Override
    public String getCreateSQL() {
        return this.getCreateSQLForCopy(this.table, this.getSQL(0));
    }

    @Override
    public void removeChildrenAndResources(SessionLocal sessionLocal) {
        this.table.removeConstraint(this);
        this.database.removeMeta(sessionLocal, this.getId());
        this.filter = null;
        this.expr = null;
        this.table = null;
        this.invalidate();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void checkRow(SessionLocal sessionLocal, Table table, Row row, Row row2) {
        boolean bl;
        if (row2 == null) {
            return;
        }
        try {
            Value value;
            ConstraintCheck constraintCheck = this;
            synchronized (constraintCheck) {
                this.filter.set(row2);
                value = this.expr.getValue(sessionLocal);
            }
            bl = value.isFalse();
        }
        catch (DbException dbException) {
            throw DbException.get(23514, dbException, this.getShortDescription());
        }
        if (bl) {
            throw DbException.get(23513, this.getShortDescription());
        }
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
        StringBuilder stringBuilder = new StringBuilder().append("SELECT NULL FROM ");
        this.filter.getTable().getSQL(stringBuilder, 0).append(" WHERE NOT ");
        this.expr.getSQL(stringBuilder, 0, 0);
        String string = stringBuilder.toString();
        ResultInterface resultInterface = sessionLocal.prepare(string).query(1L);
        if (resultInterface.next()) {
            throw DbException.get(23513, this.getName());
        }
    }

    @Override
    public void rebuild() {
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        return this.expr.isEverything(expressionVisitor);
    }
}

