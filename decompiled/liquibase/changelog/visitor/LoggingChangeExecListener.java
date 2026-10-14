/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog.visitor;

import liquibase.Scope;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.visitor.AbstractChangeExecListener;
import liquibase.database.Database;
import liquibase.exception.PreconditionErrorException;
import liquibase.exception.PreconditionFailedException;
import liquibase.precondition.core.PreconditionContainer;

public class LoggingChangeExecListener
extends AbstractChangeExecListener {
    @Override
    public void willRun(ChangeSet changeSet, DatabaseChangeLog databaseChangeLog, Database database, ChangeSet.RunStatus runStatus) {
        Scope.getCurrentScope().getLog(this.getClass()).info("EVENT: willRun fired");
    }

    @Override
    public void ran(ChangeSet changeSet, DatabaseChangeLog databaseChangeLog, Database database, ChangeSet.ExecType execType) {
        Scope.getCurrentScope().getLog(this.getClass()).info("EVENT: ran fired");
    }

    @Override
    public void willRollback(ChangeSet changeSet, DatabaseChangeLog databaseChangeLog, Database database) {
        Scope.getCurrentScope().getLog(this.getClass()).info("EVENT: willRollback fired");
    }

    @Override
    public void rollbackFailed(ChangeSet changeSet, DatabaseChangeLog databaseChangeLog, Database database, Exception e) {
        Scope.getCurrentScope().getLog(this.getClass()).info("EVENT: rollbackFailed fired");
    }

    @Override
    public void rolledBack(ChangeSet changeSet, DatabaseChangeLog databaseChangeLog, Database database) {
        Scope.getCurrentScope().getLog(this.getClass()).info("rolledBack");
    }

    @Override
    public void preconditionFailed(PreconditionFailedException error, PreconditionContainer.FailOption onFail) {
        Scope.getCurrentScope().getLog(this.getClass()).info("EVENT: preconditionFailed");
    }

    @Override
    public void preconditionErrored(PreconditionErrorException error, PreconditionContainer.ErrorOption onError2) {
        Scope.getCurrentScope().getLog(this.getClass()).info("EVENT: preconditionErrored");
    }
}

