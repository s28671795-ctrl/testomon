/*
 * Decompiled with CFR 0.152.
 */
package liquibase.precondition.core;

import java.util.ArrayList;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.database.Database;
import liquibase.exception.PreconditionErrorException;
import liquibase.exception.PreconditionFailedException;
import liquibase.exception.Warnings;
import liquibase.precondition.FailedPrecondition;
import liquibase.precondition.Precondition;
import liquibase.precondition.PreconditionLogic;

public class AndPrecondition
extends PreconditionLogic {
    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    @Override
    public Warnings warn(Database database) {
        return new Warnings();
    }

    @Override
    public void check(Database database, DatabaseChangeLog changeLog, ChangeSet changeSet, ChangeExecListener changeExecListener) throws PreconditionFailedException, PreconditionErrorException {
        boolean allPassed = true;
        ArrayList<FailedPrecondition> failures = new ArrayList<FailedPrecondition>();
        for (Precondition precondition : this.getNestedPreconditions()) {
            try {
                precondition.check(database, changeLog, changeSet, changeExecListener);
            }
            catch (PreconditionFailedException e) {
                failures.addAll(e.getFailedPreconditions());
                allPassed = false;
                break;
            }
        }
        if (!allPassed) {
            throw new PreconditionFailedException(failures);
        }
    }

    @Override
    public String getName() {
        return "and";
    }
}

