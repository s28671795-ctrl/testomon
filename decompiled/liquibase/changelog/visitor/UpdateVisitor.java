/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog.visitor;

import java.util.Objects;
import java.util.Set;
import liquibase.ChecksumVersion;
import liquibase.Scope;
import liquibase.change.CheckSum;
import liquibase.changelog.ChangeLogHistoryService;
import liquibase.changelog.ChangeLogHistoryServiceFactory;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.filter.ChangeSetFilterResult;
import liquibase.changelog.filter.ShouldRunChangeSetFilter;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.changelog.visitor.ChangeSetVisitor;
import liquibase.database.Database;
import liquibase.database.ObjectQuotingStrategy;
import liquibase.exception.DatabaseException;
import liquibase.exception.DatabaseHistoryException;
import liquibase.exception.LiquibaseException;
import liquibase.exception.MigrationFailedException;
import liquibase.executor.Executor;
import liquibase.executor.ExecutorService;
import liquibase.executor.LoggingExecutor;

public class UpdateVisitor
implements ChangeSetVisitor {
    private final Database database;
    private ChangeExecListener execListener;
    private ShouldRunChangeSetFilter shouldRunChangeSetFilter;

    @Deprecated
    public UpdateVisitor(Database database) {
        this.database = database;
    }

    public UpdateVisitor(Database database, ChangeExecListener execListener) {
        this(database);
        this.execListener = execListener;
    }

    public UpdateVisitor(Database database, ChangeExecListener execListener, ShouldRunChangeSetFilter shouldRunChangeSetFilter) {
        this(database);
        this.execListener = execListener;
        this.shouldRunChangeSetFilter = shouldRunChangeSetFilter;
    }

    @Override
    public ChangeSetVisitor.Direction getDirection() {
        return ChangeSetVisitor.Direction.FORWARD;
    }

    @Override
    public void visit(ChangeSet changeSet, DatabaseChangeLog databaseChangeLog, Database database, Set<ChangeSetFilterResult> filterResults) throws LiquibaseException {
        this.logMdcData(changeSet);
        boolean isAccepted = this.shouldRunChangeSetFilter == null || this.shouldRunChangeSetFilter.accepts(changeSet).isAccepted();
        CheckSum oldChecksum = UpdateVisitor.updateCheckSumIfRequired(changeSet);
        if (isAccepted) {
            this.executeAcceptedChange(changeSet, databaseChangeLog, database);
            this.database.commit();
        } else if (oldChecksum == null || oldChecksum.getVersion() < ChecksumVersion.latest().getVersion()) {
            UpdateVisitor.upgradeCheckSumVersionForAlreadyExecutedOrNullChange(changeSet, database, oldChecksum);
            this.database.commit();
        }
    }

    private static CheckSum updateCheckSumIfRequired(ChangeSet changeSet) {
        CheckSum oldChecksum = changeSet.getStoredCheckSum();
        if (oldChecksum == null || oldChecksum.getVersion() < ChecksumVersion.latest().getVersion()) {
            changeSet.clearCheckSum();
            changeSet.setStoredCheckSum(changeSet.generateCheckSum(ChecksumVersion.latest()));
        }
        return oldChecksum;
    }

    private static void upgradeCheckSumVersionForAlreadyExecutedOrNullChange(ChangeSet changeSet, Database database, CheckSum oldChecksum) throws DatabaseException {
        Executor executor2 = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", database);
        if (!(executor2 instanceof LoggingExecutor) && oldChecksum != null) {
            Scope.getCurrentScope().getUI().sendMessage(String.format("Upgrading checksum for Changeset %s from %s to %s.", changeSet, oldChecksum, changeSet.getStoredCheckSum()));
        }
        ChangeLogHistoryService changeLogService = Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class).getChangeLogService(database);
        changeLogService.replaceChecksum(changeSet);
    }

    private void executeAcceptedChange(ChangeSet changeSet, DatabaseChangeLog databaseChangeLog, Database database) throws DatabaseException, DatabaseHistoryException, MigrationFailedException {
        ChangeSet.ExecType execType;
        Executor executor2 = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", database);
        if (!(executor2 instanceof LoggingExecutor)) {
            Scope.getCurrentScope().getUI().sendMessage("Running Changeset: " + changeSet);
        }
        ChangeSet.RunStatus runStatus = this.database.getRunStatus(changeSet);
        Scope.getCurrentScope().getLog(this.getClass()).fine("Running Changeset: " + changeSet);
        this.fireWillRun(changeSet, databaseChangeLog, database, runStatus);
        ObjectQuotingStrategy previousStr = this.database.getObjectQuotingStrategy();
        try {
            execType = changeSet.execute(databaseChangeLog, this.execListener, this.database);
        }
        catch (MigrationFailedException e) {
            this.fireRunFailed(changeSet, databaseChangeLog, database, e);
            throw e;
        }
        if (!Objects.equals((Object)runStatus, (Object)ChangeSet.RunStatus.NOT_RAN) && Objects.equals((Object)execType, (Object)ChangeSet.ExecType.EXECUTED)) {
            execType = ChangeSet.ExecType.RERAN;
        }
        this.addAttributesForMdc(changeSet, execType);
        this.database.setObjectQuotingStrategy(previousStr);
        this.database.markChangeSetExecStatus(changeSet, execType);
        this.fireRan(changeSet, databaseChangeLog, database, execType);
    }

    protected void fireRunFailed(ChangeSet changeSet, DatabaseChangeLog databaseChangeLog, Database database, MigrationFailedException e) {
        if (this.execListener != null) {
            this.execListener.runFailed(changeSet, databaseChangeLog, database, e);
        }
    }

    protected void fireWillRun(ChangeSet changeSet, DatabaseChangeLog databaseChangeLog, Database database2, ChangeSet.RunStatus runStatus) {
        if (this.execListener != null) {
            this.execListener.willRun(changeSet, databaseChangeLog, this.database, runStatus);
        }
    }

    protected void fireRan(ChangeSet changeSet, DatabaseChangeLog databaseChangeLog, Database database2, ChangeSet.ExecType execType) {
        if (this.execListener != null) {
            this.execListener.ran(changeSet, databaseChangeLog, this.database, execType);
        }
    }

    private void addAttributesForMdc(ChangeSet changeSet, ChangeSet.ExecType execType) {
        changeSet.setAttribute("updateExecType", (Object)execType);
        ChangeLogHistoryService changelogService = Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class).getChangeLogService(this.database);
        String deploymentId = changelogService.getDeploymentId();
        changeSet.setAttribute("deploymentId", deploymentId);
    }
}

