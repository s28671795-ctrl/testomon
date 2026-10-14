/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog.visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import liquibase.Scope;
import liquibase.change.Change;
import liquibase.change.core.SQLFileChange;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.RollbackContainer;
import liquibase.changelog.filter.ChangeSetFilterResult;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.changelog.visitor.ChangeSetVisitor;
import liquibase.database.Database;
import liquibase.exception.LiquibaseException;
import liquibase.executor.Executor;
import liquibase.executor.ExecutorService;
import liquibase.executor.LoggingExecutor;

public class RollbackVisitor
implements ChangeSetVisitor {
    private final Database database;
    private ChangeExecListener execListener;
    private List<ChangeSet> processedChangesets = new ArrayList<ChangeSet>();

    @Deprecated
    public RollbackVisitor(Database database) {
        this.database = database;
    }

    public RollbackVisitor(Database database, ChangeExecListener listener) {
        this(database);
        this.execListener = listener;
    }

    public RollbackVisitor(Database database, ChangeExecListener listener, List<ChangeSet> processedChangesets) {
        this(database);
        this.execListener = listener;
        this.processedChangesets = processedChangesets;
    }

    @Override
    public ChangeSetVisitor.Direction getDirection() {
        return ChangeSetVisitor.Direction.REVERSE;
    }

    @Override
    public void visit(ChangeSet changeSet, DatabaseChangeLog databaseChangeLog, Database database, Set<ChangeSetFilterResult> filterResults) throws LiquibaseException {
        this.logMdcData(changeSet);
        Scope.getCurrentScope().addMdcValue("deploymentId", changeSet.getDeploymentId());
        Executor executor2 = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", database);
        if (!(executor2 instanceof LoggingExecutor)) {
            Scope.getCurrentScope().getUI().sendMessage("Rolling Back Changeset: " + changeSet);
        }
        this.sendRollbackWillRunEvent(changeSet, databaseChangeLog, database);
        try {
            changeSet.rollback(this.database, this.execListener);
        }
        catch (Exception e) {
            this.fireRollbackFailed(changeSet, databaseChangeLog, database, e);
            throw e;
        }
        this.database.removeRanStatus(changeSet);
        this.sendRollbackEvent(changeSet, databaseChangeLog, database);
        this.database.commit();
        this.checkForEmptyRollbackFile(changeSet);
        if (this.processedChangesets != null) {
            this.processedChangesets.add(changeSet);
        }
    }

    protected void fireRollbackFailed(ChangeSet changeSet, DatabaseChangeLog databaseChangeLog, Database database, Exception e) {
        if (this.execListener != null) {
            this.execListener.rollbackFailed(changeSet, databaseChangeLog, database, e);
        }
    }

    private void checkForEmptyRollbackFile(ChangeSet changeSet) {
        RollbackContainer container = changeSet.getRollback();
        List<Change> changes = container.getChanges();
        if (changes.isEmpty()) {
            return;
        }
        for (Change change : changes) {
            String sql;
            if (!(change instanceof SQLFileChange) || (sql = ((SQLFileChange)change).getSql()).length() != 0) continue;
            Scope.getCurrentScope().getLog(this.getClass()).info("\nNo rollback logic defined in empty rollback script. Changesets have been removed from\nthe DATABASECHANGELOG table but no other logic was performed.");
        }
    }

    private void sendRollbackWillRunEvent(ChangeSet changeSet, DatabaseChangeLog databaseChangeLog, Database database) {
        if (this.execListener != null) {
            this.execListener.willRollback(changeSet, databaseChangeLog, database);
        }
    }

    private void sendRollbackEvent(ChangeSet changeSet, DatabaseChangeLog databaseChangeLog, Database database) {
        if (this.execListener != null) {
            this.execListener.rolledBack(changeSet, databaseChangeLog, database);
        }
    }
}

