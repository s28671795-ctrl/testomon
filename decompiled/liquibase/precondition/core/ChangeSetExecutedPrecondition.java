/*
 * Decompiled with CFR 0.152.
 */
package liquibase.precondition.core;

import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.RanChangeSet;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.database.Database;
import liquibase.database.ObjectQuotingStrategy;
import liquibase.exception.PreconditionErrorException;
import liquibase.exception.PreconditionFailedException;
import liquibase.exception.ValidationErrors;
import liquibase.exception.Warnings;
import liquibase.precondition.AbstractPrecondition;

public class ChangeSetExecutedPrecondition
extends AbstractPrecondition {
    private String changeLogFile;
    private String id;
    private String author;

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    @Override
    public Warnings warn(Database database) {
        return new Warnings();
    }

    @Override
    public ValidationErrors validate(Database database) {
        return new ValidationErrors();
    }

    @Override
    public void check(Database database, DatabaseChangeLog changeLog, ChangeSet changeSet, ChangeExecListener changeExecListener) throws PreconditionFailedException, PreconditionErrorException {
        RanChangeSet ranChangeSet;
        ObjectQuotingStrategy objectQuotingStrategy = changeSet == null ? ObjectQuotingStrategy.LEGACY : changeSet.getObjectQuotingStrategy();
        String changeLogFile = this.getChangeLogFile();
        if (changeLogFile == null) {
            changeLogFile = changeLog.getLogicalFilePath();
        }
        ChangeSet interestedChangeSet = new ChangeSet(this.getId(), this.getAuthor(), false, false, changeLogFile, null, null, false, objectQuotingStrategy, changeLog);
        try {
            ranChangeSet = database.getRanChangeSet(interestedChangeSet);
        }
        catch (Exception e) {
            throw new PreconditionErrorException(e, changeLog, this);
        }
        if (ranChangeSet == null || ranChangeSet.getExecType() == null || !ranChangeSet.getExecType().ran) {
            throw new PreconditionFailedException("Changeset '" + interestedChangeSet.toString(false) + "' has not been run", changeLog, this);
        }
    }

    @Override
    public String getName() {
        return "changeSetExecuted";
    }

    public String getChangeLogFile() {
        return this.changeLogFile;
    }

    public String getId() {
        return this.id;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setChangeLogFile(String changeLogFile) {
        this.changeLogFile = changeLogFile;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}

