/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog.visitor;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import liquibase.Scope;
import liquibase.changelog.ChangeLogHistoryServiceFactory;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.filter.ChangeSetFilterResult;
import liquibase.changelog.visitor.ChangeLogSyncListener;
import liquibase.changelog.visitor.ChangeSetVisitor;
import liquibase.database.Database;
import liquibase.exception.LiquibaseException;
import liquibase.logging.mdc.MdcObject;
import liquibase.util.ISODateFormat;

public class ChangeLogSyncVisitor
implements ChangeSetVisitor {
    private Database database;
    private ChangeLogSyncListener listener;

    public ChangeLogSyncVisitor(Database database) {
        this.database = database;
    }

    public ChangeLogSyncVisitor(Database database, ChangeLogSyncListener listener) {
        this.database = database;
        this.listener = listener;
    }

    @Override
    public ChangeSetVisitor.Direction getDirection() {
        return ChangeSetVisitor.Direction.FORWARD;
    }

    @Override
    public void visit(ChangeSet changeSet, DatabaseChangeLog databaseChangeLog, Database database, Set<ChangeSetFilterResult> filterResults) throws LiquibaseException {
        block16: {
            try {
                this.preRunMdc(changeSet);
                this.database.markChangeSetExecStatus(changeSet, ChangeSet.ExecType.EXECUTED);
                this.postRunMdc(changeSet);
                if (this.listener != null) {
                    this.listener.markedRan(changeSet, databaseChangeLog, database);
                }
            }
            catch (Exception e) {
                Date stop = new Date();
                changeSet.setOperationStopTime(stop);
                try (MdcObject stopTime = Scope.getCurrentScope().addMdcValue("changesetOperationStop", new ISODateFormat().format(stop));
                     MdcObject changelogSyncOutcome = Scope.getCurrentScope().addMdcValue("changesetSyncOutcome", "fail");){
                    Scope.getCurrentScope().getLog(this.getClass()).fine("Failed syncing changeset");
                }
                if (this.listener == null) break block16;
                this.listener.markedRanFailed(changeSet, databaseChangeLog, database, e);
            }
        }
    }

    private void preRunMdc(ChangeSet changeSet) {
        Date start2 = new Date();
        Scope.getCurrentScope().addMdcValue("changesetOperationStart", new ISODateFormat().format(start2));
        changeSet.setOperationStartTime(start2);
        this.logMdcData(changeSet);
        changeSet.addChangeSetMdcProperties();
    }

    private void postRunMdc(ChangeSet changeSet) {
        try {
            ChangeLogHistoryServiceFactory instance = Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class);
            String deploymentId = instance.getChangeLogService(this.database).getDeploymentId();
            Scope.getCurrentScope().addMdcValue("deploymentId", deploymentId);
        }
        catch (Exception e) {
            Scope.getCurrentScope().getLog(this.getClass()).fine("Failed to retrieve deployment ID for MDC", e);
        }
        AtomicInteger changesetCount = (AtomicInteger)((Object)Scope.getCurrentScope().get("changesetCount", AtomicInteger.class));
        if (changesetCount != null) {
            changesetCount.getAndIncrement();
        }
        Date stop = new Date();
        changeSet.setOperationStopTime(stop);
        try (MdcObject stopTime = Scope.getCurrentScope().addMdcValue("changesetOperationStop", new ISODateFormat().format(stop));
             MdcObject changelogSyncOutcome = Scope.getCurrentScope().addMdcValue("changesetSyncOutcome", "success");){
            Scope.getCurrentScope().getLog(this.getClass()).fine("Finished syncing changeset");
        }
    }
}

