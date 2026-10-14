/*
 * Decompiled with CFR 0.152.
 */
package org.h2.schema;

import java.util.ArrayList;
import org.h2.constraint.Constraint;
import org.h2.constraint.ConstraintDomain;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ValueExpression;
import org.h2.schema.Schema;
import org.h2.schema.SchemaObject;
import org.h2.table.ColumnTemplate;
import org.h2.util.Utils;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

public final class Domain
extends SchemaObject
implements ColumnTemplate {
    private TypeInfo type;
    private Domain domain;
    private Expression defaultExpression;
    private Expression onUpdateExpression;
    private ArrayList<ConstraintDomain> constraints;

    public Domain(Schema schema, int n, String string) {
        super(schema, n, string, 8);
    }

    @Override
    public String getDropSQL() {
        StringBuilder stringBuilder = new StringBuilder("DROP DOMAIN IF EXISTS ");
        return this.getSQL(stringBuilder, 0).toString();
    }

    @Override
    public String getCreateSQL() {
        StringBuilder stringBuilder = this.getSQL(new StringBuilder("CREATE DOMAIN "), 0).append(" AS ");
        if (this.domain != null) {
            this.domain.getSQL(stringBuilder, 0);
        } else {
            this.type.getSQL(stringBuilder, 0);
        }
        if (this.defaultExpression != null) {
            this.defaultExpression.getUnenclosedSQL(stringBuilder.append(" DEFAULT "), 0);
        }
        if (this.onUpdateExpression != null) {
            this.onUpdateExpression.getUnenclosedSQL(stringBuilder.append(" ON UPDATE "), 0);
        }
        return stringBuilder.toString();
    }

    public void setDataType(TypeInfo typeInfo) {
        this.type = typeInfo;
    }

    public TypeInfo getDataType() {
        return this.type;
    }

    @Override
    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    @Override
    public Domain getDomain() {
        return this.domain;
    }

    @Override
    public void setDefaultExpression(SessionLocal sessionLocal, Expression expression) {
        if (expression != null && (expression = expression.optimize(sessionLocal)).isConstant()) {
            expression = ValueExpression.get(expression.getValue(sessionLocal));
        }
        this.defaultExpression = expression;
    }

    @Override
    public Expression getDefaultExpression() {
        return this.defaultExpression;
    }

    @Override
    public Expression getEffectiveDefaultExpression() {
        return this.defaultExpression != null ? this.defaultExpression : (this.domain != null ? this.domain.getEffectiveDefaultExpression() : null);
    }

    @Override
    public String getDefaultSQL() {
        return this.defaultExpression == null ? null : this.defaultExpression.getUnenclosedSQL(new StringBuilder(), 0).toString();
    }

    @Override
    public void setOnUpdateExpression(SessionLocal sessionLocal, Expression expression) {
        if (expression != null && (expression = expression.optimize(sessionLocal)).isConstant()) {
            expression = ValueExpression.get(expression.getValue(sessionLocal));
        }
        this.onUpdateExpression = expression;
    }

    @Override
    public Expression getOnUpdateExpression() {
        return this.onUpdateExpression;
    }

    @Override
    public Expression getEffectiveOnUpdateExpression() {
        return this.onUpdateExpression != null ? this.onUpdateExpression : (this.domain != null ? this.domain.getEffectiveOnUpdateExpression() : null);
    }

    @Override
    public String getOnUpdateSQL() {
        return this.onUpdateExpression == null ? null : this.onUpdateExpression.getUnenclosedSQL(new StringBuilder(), 0).toString();
    }

    @Override
    public void prepareExpressions(SessionLocal sessionLocal) {
        if (this.defaultExpression != null) {
            this.defaultExpression = this.defaultExpression.optimize(sessionLocal);
        }
        if (this.onUpdateExpression != null) {
            this.onUpdateExpression = this.onUpdateExpression.optimize(sessionLocal);
        }
        if (this.domain != null) {
            this.domain.prepareExpressions(sessionLocal);
        }
    }

    public void addConstraint(ConstraintDomain constraintDomain) {
        if (this.constraints == null) {
            this.constraints = Utils.newSmallArrayList();
        }
        if (!this.constraints.contains(constraintDomain)) {
            this.constraints.add(constraintDomain);
        }
    }

    public ArrayList<ConstraintDomain> getConstraints() {
        return this.constraints;
    }

    public void removeConstraint(Constraint constraint) {
        if (this.constraints != null) {
            this.constraints.remove(constraint);
        }
    }

    @Override
    public int getType() {
        return 12;
    }

    @Override
    public void removeChildrenAndResources(SessionLocal sessionLocal) {
        if (this.constraints != null && !this.constraints.isEmpty()) {
            for (ConstraintDomain constraintDomain : this.constraints.toArray(new ConstraintDomain[0])) {
                this.database.removeSchemaObject(sessionLocal, constraintDomain);
            }
            this.constraints = null;
        }
        this.database.removeMeta(sessionLocal, this.getId());
    }

    public void checkConstraints(SessionLocal sessionLocal, Value value) {
        if (this.constraints != null) {
            for (ConstraintDomain constraintDomain : this.constraints) {
                constraintDomain.check(sessionLocal, value);
            }
        }
        if (this.domain != null) {
            this.domain.checkConstraints(sessionLocal, value);
        }
    }
}

