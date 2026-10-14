/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.AlterTable;
import org.h2.constraint.Constraint;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.engine.User;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.table.Table;

public class AlterTableRenameConstraint
extends AlterTable {
    private String constraintName;
    private String newConstraintName;

    public AlterTableRenameConstraint(SessionLocal sessionLocal, Schema schema) {
        super(sessionLocal, schema);
    }

    public void setConstraintName(String string) {
        this.constraintName = string;
    }

    public void setNewConstraintName(String string) {
        this.newConstraintName = string;
    }

    @Override
    public long update(Table table) {
        Constraint constraint = this.getSchema().findConstraint(this.session, this.constraintName);
        Database database = this.getDatabase();
        if (constraint == null || constraint.getConstraintType() == Constraint.Type.DOMAIN || constraint.getTable() != table) {
            throw DbException.get(90057, this.constraintName);
        }
        if (this.getSchema().findConstraint(this.session, this.newConstraintName) != null || this.newConstraintName.equals(this.constraintName)) {
            throw DbException.get(90045, this.newConstraintName);
        }
        User user = this.session.getUser();
        Table table2 = constraint.getRefTable();
        if (table2 != table) {
            user.checkTableRight(table2, 32);
        }
        database.renameSchemaObject(this.session, constraint, this.newConstraintName);
        return 0L;
    }

    @Override
    public int getType() {
        return 85;
    }
}

