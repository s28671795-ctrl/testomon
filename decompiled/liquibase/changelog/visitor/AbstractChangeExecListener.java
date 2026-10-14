/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog.visitor;

import liquibase.change.Change;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.database.Database;
import liquibase.exception.PreconditionErrorException;
import liquibase.exception.PreconditionFailedException;
import liquibase.precondition.core.PreconditionContainer;

public abstract class AbstractChangeExecListener
implements ChangeExecListener {
    @Override
    public void willRun(ChangeSet changeSet, DatabaseChangeLog databaseChangeLog, Database database, ChangeSet.RunStatus runStatus) {
    }

    @Override
    public void ran(ChangeSet changeSet, DatabaseChangeLog databaseChangeLog, Database database, ChangeSet.ExecType execType) {
    }

    @Override
    public void willRollback(ChangeSet changeSet, DatabaseChangeLog databaseChangeLog, Database database) {
    }

    @Override
    public void rollbackFailed(ChangeSet changeSet, DatabaseChangeLog databaseChangeLog, Database database, Exception e) {
    }

    @Override
    public void rolledBack(ChangeSet changeSet, DatabaseChangeLog databaseChangeLog, Database database) {
    }

    @Override
    public void preconditionFailed(PreconditionFailedException error, PreconditionContainer.FailOption onFail) {
    }

    @Override
    public void preconditionErrored(PreconditionErrorException error, PreconditionContainer.ErrorOption onError2) {
    }

    @Override
    public void willRun(Change change, ChangeSet changeSet, DatabaseChangeLog changeLog, Database database) {
    }

    @Override
    public void ran(Change change, ChangeSet changeSet, DatabaseChangeLog changeLog, Database database) {
    }

    @Override
    public void runFailed(ChangeSet changeSet, DatabaseChangeLog databaseChangeLog, Database database, Exception exception) {
    }
}

