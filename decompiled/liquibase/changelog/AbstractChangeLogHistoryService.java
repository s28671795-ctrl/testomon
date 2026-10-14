/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import liquibase.ChecksumVersion;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Scope;
import liquibase.changelog.ChangeLogHistoryService;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.RanChangeSet;
import liquibase.changelog.filter.ContextChangeSetFilter;
import liquibase.changelog.filter.DbmsChangeSetFilter;
import liquibase.database.Database;
import liquibase.exception.DatabaseException;
import liquibase.exception.DatabaseHistoryException;
import liquibase.executor.ExecutorService;
import liquibase.statement.core.UpdateChangeSetChecksumStatement;

public abstract class AbstractChangeLogHistoryService
implements ChangeLogHistoryService {
    private Database database;
    private String deploymentId;

    @Override
    public void setDatabase(Database database) {
        this.database = database;
    }

    @Override
    public void reset() {
    }

    @Override
    public ChangeSet.RunStatus getRunStatus(ChangeSet changeSet) throws DatabaseException, DatabaseHistoryException {
        RanChangeSet foundRan = this.getRanChangeSet(changeSet);
        if (foundRan == null) {
            return ChangeSet.RunStatus.NOT_RAN;
        }
        if (foundRan.getLastCheckSum() == null) {
            try {
                Scope.getCurrentScope().getLog(this.getClass()).info("Updating NULL md5sum for " + changeSet.toString());
                this.replaceChecksum(changeSet);
            }
            catch (DatabaseException e) {
                throw new DatabaseException(e);
            }
            return ChangeSet.RunStatus.ALREADY_RAN;
        }
        if (foundRan.getLastCheckSum().equals(changeSet.generateCheckSum(ChecksumVersion.enumFromChecksumVersion(foundRan.getLastCheckSum().getVersion())))) {
            return ChangeSet.RunStatus.ALREADY_RAN;
        }
        if (changeSet.shouldRunOnChange()) {
            return ChangeSet.RunStatus.RUN_AGAIN;
        }
        return ChangeSet.RunStatus.INVALID_MD5SUM;
    }

    @Override
    public void upgradeChecksums(DatabaseChangeLog databaseChangeLog, Contexts contexts, LabelExpression labels) throws DatabaseException {
        for (RanChangeSet ranChangeSet : this.getRanChangeSets()) {
            if (ranChangeSet.getLastCheckSum() != null) continue;
            List<ChangeSet> changeSets = databaseChangeLog.getChangeSets(ranChangeSet);
            for (ChangeSet changeSet : changeSets) {
                if (changeSet == null || !new ContextChangeSetFilter(contexts).accepts(changeSet).isAccepted() || !new DbmsChangeSetFilter(this.getDatabase()).accepts(changeSet).isAccepted()) continue;
                Scope.getCurrentScope().getLog(this.getClass()).fine("Updating null or out of date checksum on changeSet " + changeSet + " to correct value");
                this.replaceChecksum(changeSet);
            }
        }
    }

    @Override
    public RanChangeSet getRanChangeSet(ChangeSet changeSet) throws DatabaseException, DatabaseHistoryException {
        for (RanChangeSet ranChange : this.getRanChangeSets()) {
            if (!ranChange.isSameAs(changeSet)) continue;
            return ranChange;
        }
        return null;
    }

    @Override
    public Date getRanDate(ChangeSet changeSet) throws DatabaseException, DatabaseHistoryException {
        RanChangeSet ranChange = this.getRanChangeSet(changeSet);
        if (ranChange == null) {
            return null;
        }
        return ranChange.getDateExecuted();
    }

    public String getLastDeploymentId() throws DatabaseException {
        List<RanChangeSet> ranChangeSetsList = this.getRanChangeSets();
        if (ranChangeSetsList == null || ranChangeSetsList.isEmpty()) {
            return null;
        }
        RanChangeSet lastRanChangeSet = ranChangeSetsList.get(ranChangeSetsList.size() - 1);
        return lastRanChangeSet.getDeploymentId();
    }

    @Override
    public void replaceChecksum(ChangeSet changeSet) throws DatabaseException {
        Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", this.getDatabase()).execute(new UpdateChangeSetChecksumStatement(changeSet));
        this.getDatabase().commit();
        this.reset();
    }

    @Override
    public String getDeploymentId() {
        return this.deploymentId;
    }

    @Override
    public void resetDeploymentId() {
        this.deploymentId = null;
    }

    @Override
    public void generateDeploymentId() {
        if (this.deploymentId == null) {
            long time = new Date().getTime();
            String dateString = String.valueOf(time);
            DecimalFormat decimalFormat = new DecimalFormat("0000000000");
            this.deploymentId = dateString.length() > 9 ? dateString.substring(dateString.length() - 10) : decimalFormat.format(time);
        }
    }

    public Database getDatabase() {
        return this.database;
    }
}

