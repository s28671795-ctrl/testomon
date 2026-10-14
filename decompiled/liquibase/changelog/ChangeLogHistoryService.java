/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog;

import java.util.Date;
import java.util.List;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.RanChangeSet;
import liquibase.database.Database;
import liquibase.exception.DatabaseException;
import liquibase.exception.DatabaseHistoryException;
import liquibase.exception.LiquibaseException;
import liquibase.plugin.Plugin;

public interface ChangeLogHistoryService
extends Plugin {
    public int getPriority();

    public boolean supports(Database var1);

    public void setDatabase(Database var1);

    public void reset();

    public void init() throws DatabaseException;

    public void upgradeChecksums(DatabaseChangeLog var1, Contexts var2, LabelExpression var3) throws DatabaseException;

    public List<RanChangeSet> getRanChangeSets() throws DatabaseException;

    @Deprecated
    default public List<RanChangeSet> getRanChangeSets(boolean allowChecksumsUpgrade) throws DatabaseException {
        return this.getRanChangeSets();
    }

    public RanChangeSet getRanChangeSet(ChangeSet var1) throws DatabaseException, DatabaseHistoryException;

    public ChangeSet.RunStatus getRunStatus(ChangeSet var1) throws DatabaseException, DatabaseHistoryException;

    public Date getRanDate(ChangeSet var1) throws DatabaseException, DatabaseHistoryException;

    public void setExecType(ChangeSet var1, ChangeSet.ExecType var2) throws DatabaseException;

    public void removeFromHistory(ChangeSet var1) throws DatabaseException;

    public int getNextSequenceValue() throws LiquibaseException;

    public void tag(String var1) throws DatabaseException;

    public boolean tagExists(String var1) throws DatabaseException;

    public void clearAllCheckSums() throws LiquibaseException;

    public void destroy() throws DatabaseException;

    public String getDeploymentId();

    public void resetDeploymentId();

    public void generateDeploymentId();

    public boolean isDatabaseChecksumsCompatible();

    default public void replaceChecksum(ChangeSet changeSet) throws DatabaseException {
    }
}

