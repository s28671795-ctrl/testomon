/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.AlterDomain;
import org.h2.constraint.ConstraintDomain;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.message.DbException;
import org.h2.schema.Domain;
import org.h2.schema.Schema;

public class AlterDomainAddConstraint
extends AlterDomain {
    private String constraintName;
    private Expression checkExpression;
    private String comment;
    private boolean checkExisting;
    private final boolean ifNotExists;

    public AlterDomainAddConstraint(SessionLocal sessionLocal, Schema schema, boolean bl) {
        super(sessionLocal, schema);
        this.ifNotExists = bl;
    }

    private String generateConstraintName(Domain domain) {
        if (this.constraintName == null) {
            this.constraintName = this.getSchema().getUniqueDomainConstraintName(this.session, domain);
        }
        return this.constraintName;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    long update(Schema schema, Domain domain) {
        try {
            long l = this.tryUpdate(schema, domain);
            return l;
        }
        finally {
            this.getSchema().freeUniqueName(this.constraintName);
        }
    }

    private int tryUpdate(Schema schema, Domain domain) {
        if (this.constraintName != null && schema.findConstraint(this.session, this.constraintName) != null) {
            if (this.ifNotExists) {
                return 0;
            }
            throw DbException.get(90045, this.constraintName);
        }
        Database database = this.getDatabase();
        database.lockMeta(this.session);
        int n = this.getObjectId();
        String string = this.generateConstraintName(domain);
        ConstraintDomain constraintDomain = new ConstraintDomain(schema, n, string, domain);
        constraintDomain.setExpression(this.session, this.checkExpression);
        if (this.checkExisting) {
            constraintDomain.checkExistingData(this.session);
        }
        constraintDomain.setComment(this.comment);
        database.addSchemaObject(this.session, constraintDomain);
        domain.addConstraint(constraintDomain);
        return 0;
    }

    public void setConstraintName(String string) {
        this.constraintName = string;
    }

    public String getConstraintName() {
        return this.constraintName;
    }

    @Override
    public int getType() {
        return 92;
    }

    public void setCheckExpression(Expression expression) {
        this.checkExpression = expression;
    }

    public void setComment(String string) {
        this.comment = string;
    }

    public void setCheckExisting(boolean bl) {
        this.checkExisting = bl;
    }
}

