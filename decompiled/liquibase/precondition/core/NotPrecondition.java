/*
 * Decompiled with CFR 0.152.
 */
package liquibase.precondition.core;

import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.database.Database;
import liquibase.exception.PreconditionErrorException;
import liquibase.exception.PreconditionFailedException;
import liquibase.exception.Warnings;
import liquibase.precondition.Precondition;
import liquibase.precondition.PreconditionLogic;

public class NotPrecondition
extends PreconditionLogic {
    @Override
    public Warnings warn(Database database) {
        return new Warnings();
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    @Override
    public void check(Database database, DatabaseChangeLog changeLog, ChangeSet changeSet, ChangeExecListener changeExecListener) throws PreconditionFailedException, PreconditionErrorException {
        for (Precondition precondition : this.getNestedPreconditions()) {
            boolean threwException = false;
            try {
                precondition.check(database, changeLog, changeSet, changeExecListener);
            }
            catch (PreconditionFailedException e) {
                threwException = true;
            }
            if (threwException) continue;
            throw new PreconditionFailedException("Not precondition failed", changeLog, this);
        }
    }

    @Override
    public String getName() {
        return "not";
    }
}

