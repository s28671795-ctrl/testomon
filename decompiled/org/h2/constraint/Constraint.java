/*
 * Decompiled with CFR 0.152.
 */
package org.h2.constraint;

import java.util.HashSet;
import org.h2.constraint.ConstraintUnique;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.index.Index;
import org.h2.result.Row;
import org.h2.schema.Schema;
import org.h2.schema.SchemaObject;
import org.h2.table.Column;
import org.h2.table.Table;

public abstract class Constraint
extends SchemaObject
implements Comparable<Constraint> {
    protected Table table;

    Constraint(Schema schema, int n, String string, Table table) {
        super(schema, n, string, 1);
        this.table = table;
        if (table != null) {
            this.setTemporary(table.isTemporary());
        }
    }

    public abstract Type getConstraintType();

    public abstract void checkRow(SessionLocal var1, Table var2, Row var3, Row var4);

    public abstract boolean usesIndex(Index var1);

    public abstract void setIndexOwner(Index var1);

    public abstract HashSet<Column> getReferencedColumns(Table var1);

    public Expression getExpression() {
        return null;
    }

    public abstract String getCreateSQLWithoutIndexes();

    public abstract boolean isBefore();

    public abstract void checkExistingData(SessionLocal var1);

    public abstract void rebuild();

    public Index getIndex() {
        return null;
    }

    public ConstraintUnique getReferencedConstraint() {
        return null;
    }

    @Override
    public int getType() {
        return 5;
    }

    public Table getTable() {
        return this.table;
    }

    public Table getRefTable() {
        return this.table;
    }

    @Override
    public int compareTo(Constraint constraint) {
        if (this == constraint) {
            return 0;
        }
        return Integer.compare(this.getConstraintType().ordinal(), constraint.getConstraintType().ordinal());
    }

    @Override
    public boolean isHidden() {
        return this.table != null && this.table.isHidden();
    }

    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        return true;
    }

    public static enum Type {
        CHECK,
        PRIMARY_KEY,
        UNIQUE,
        REFERENTIAL,
        DOMAIN;


        public String getSqlName() {
            if (this == PRIMARY_KEY) {
                return "PRIMARY KEY";
            }
            if (this == REFERENTIAL) {
                return "FOREIGN KEY";
            }
            return this.name();
        }
    }
}

