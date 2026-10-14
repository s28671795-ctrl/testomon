/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog.visitor;

import liquibase.change.Change;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.database.Database;
import liquibase.exception.PreconditionErrorException;
import liquibase.exception.PreconditionFailedException;
import liquibase.precondition.core.PreconditionContainer;

public interface ChangeExecListener {
    public void willRun(ChangeSet var1, DatabaseChangeLog var2, Database var3, ChangeSet.RunStatus var4);

    public void ran(ChangeSet var1, DatabaseChangeLog var2, Database var3, ChangeSet.ExecType var4);

    public void willRollback(ChangeSet var1, DatabaseChangeLog var2, Database var3);

    public void rolledBack(ChangeSet var1, DatabaseChangeLog var2, Database var3);

    public void preconditionFailed(PreconditionFailedException var1, PreconditionContainer.FailOption var2);

    public void preconditionErrored(PreconditionErrorException var1, PreconditionContainer.ErrorOption var2);

    public void willRun(Change var1, ChangeSet var2, DatabaseChangeLog var3, Database var4);

    public void ran(Change var1, ChangeSet var2, DatabaseChangeLog var3, Database var4);

    public void runFailed(ChangeSet var1, DatabaseChangeLog var2, Database var3, Exception var4);

    public void rollbackFailed(ChangeSet var1, DatabaseChangeLog var2, Database var3, Exception var4);
}

