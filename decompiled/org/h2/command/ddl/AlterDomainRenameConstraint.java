/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.AlterDomain;
import org.h2.constraint.Constraint;
import org.h2.constraint.ConstraintDomain;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.schema.Domain;
import org.h2.schema.Schema;

public class AlterDomainRenameConstraint
extends AlterDomain {
    private String constraintName;
    private String newConstraintName;

    public AlterDomainRenameConstraint(SessionLocal sessionLocal, Schema schema) {
        super(sessionLocal, schema);
    }

    public void setConstraintName(String string) {
        this.constraintName = string;
    }

    public void setNewConstraintName(String string) {
        this.newConstraintName = string;
    }

    @Override
    long update(Schema schema, Domain domain) {
        Constraint constraint = this.getSchema().findConstraint(this.session, this.constraintName);
        if (constraint == null || constraint.getConstraintType() != Constraint.Type.DOMAIN || ((ConstraintDomain)constraint).getDomain() != domain) {
            throw DbException.get(90057, this.constraintName);
        }
        if (this.getSchema().findConstraint(this.session, this.newConstraintName) != null || this.newConstraintName.equals(this.constraintName)) {
            throw DbException.get(90045, this.newConstraintName);
        }
        this.getDatabase().renameSchemaObject(this.session, constraint, this.newConstraintName);
        return 0L;
    }

    @Override
    public int getType() {
        return 101;
    }
}

