/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.AlterTable;
import org.h2.constraint.Constraint;
import org.h2.constraint.ConstraintActionType;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.table.Table;

public class AlterTableDropConstraint
extends AlterTable {
    private String constraintName;
    private final boolean ifExists;
    private ConstraintActionType dropAction;

    public AlterTableDropConstraint(SessionLocal sessionLocal, Schema schema, boolean bl) {
        super(sessionLocal, schema);
        this.ifExists = bl;
        this.dropAction = this.getDatabase().getSettings().dropRestrict ? ConstraintActionType.RESTRICT : ConstraintActionType.CASCADE;
    }

    public void setConstraintName(String string) {
        this.constraintName = string;
    }

    public void setDropAction(ConstraintActionType constraintActionType) {
        this.dropAction = constraintActionType;
    }

    @Override
    public long update(Table table) {
        Constraint.Type type;
        Constraint constraint = this.getSchema().findConstraint(this.session, this.constraintName);
        if (constraint == null || (type = constraint.getConstraintType()) == Constraint.Type.DOMAIN || constraint.getTable() != table) {
            if (!this.ifExists) {
                throw DbException.get(90057, this.constraintName);
            }
        } else {
            Table table2 = constraint.getRefTable();
            if (table2 != table) {
                this.session.getUser().checkTableRight(table2, 32);
            }
            if (type == Constraint.Type.PRIMARY_KEY || type == Constraint.Type.UNIQUE) {
                for (Constraint constraint2 : constraint.getTable().getConstraints()) {
                    if (constraint2.getReferencedConstraint() != constraint) continue;
                    if (this.dropAction == ConstraintActionType.RESTRICT) {
                        throw DbException.get(90152, constraint.getTraceSQL(), constraint2.getTraceSQL());
                    }
                    Table table3 = constraint2.getTable();
                    if (table3 == table || table3 == table2) continue;
                    this.session.getUser().checkTableRight(table3, 32);
                }
            }
            this.getDatabase().removeSchemaObject(this.session, constraint);
        }
        return 0L;
    }

    @Override
    public int getType() {
        return 14;
    }
}

