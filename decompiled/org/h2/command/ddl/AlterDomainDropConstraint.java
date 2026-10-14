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

public class AlterDomainDropConstraint
extends AlterDomain {
    private String constraintName;
    private final boolean ifConstraintExists;

    public AlterDomainDropConstraint(SessionLocal sessionLocal, Schema schema, boolean bl) {
        super(sessionLocal, schema);
        this.ifConstraintExists = bl;
    }

    public void setConstraintName(String string) {
        this.constraintName = string;
    }

    @Override
    long update(Schema schema, Domain domain) {
        Constraint constraint = schema.findConstraint(this.session, this.constraintName);
        if (constraint == null || constraint.getConstraintType() != Constraint.Type.DOMAIN || ((ConstraintDomain)constraint).getDomain() != domain) {
            if (!this.ifConstraintExists) {
                throw DbException.get(90057, this.constraintName);
            }
        } else {
            this.getDatabase().removeSchemaObject(this.session, constraint);
        }
        return 0L;
    }

    @Override
    public int getType() {
        return 93;
    }
}

