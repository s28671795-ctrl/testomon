/*
 * Decompiled with CFR 0.152.
 */
package liquibase.precondition.core;

import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.database.Database;
import liquibase.exception.PreconditionErrorException;
import liquibase.exception.PreconditionFailedException;
import liquibase.exception.ValidationErrors;
import liquibase.exception.Warnings;
import liquibase.precondition.AbstractPrecondition;

public class RunningAsPrecondition
extends AbstractPrecondition {
    private String username = "";

    public void setUsername(String aUserName) {
        this.username = aUserName;
    }

    public String getUsername() {
        return this.username;
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
        String loggedusername = database.getConnection().getConnectionUserName();
        if (loggedusername != null && loggedusername.indexOf(64) >= 0) {
            loggedusername = loggedusername.substring(0, loggedusername.indexOf(64));
        }
        if (!this.username.equalsIgnoreCase(loggedusername)) {
            throw new PreconditionFailedException("RunningAs Precondition failed: expected " + this.username + ", was " + loggedusername, changeLog, this);
        }
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    @Override
    public String getName() {
        return "runningAs";
    }
}

