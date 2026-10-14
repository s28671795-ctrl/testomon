/*
 * Decompiled with CFR 0.152.
 */
package liquibase.precondition.core;

import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.database.Database;
import liquibase.database.DatabaseList;
import liquibase.exception.PreconditionErrorException;
import liquibase.exception.PreconditionFailedException;
import liquibase.exception.ValidationErrors;
import liquibase.exception.Warnings;
import liquibase.precondition.AbstractPrecondition;

public class DBMSPrecondition
extends AbstractPrecondition {
    private String type;

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    public String getType() {
        return this.type;
    }

    public void setType(String atype) {
        this.type = atype == null ? null : atype.toLowerCase();
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
        try {
            String dbType = database.getShortName();
            if (!DatabaseList.definitionMatches(this.type, database, false)) {
                throw new PreconditionFailedException("DBMS Precondition failed: expected " + this.type + ", got " + dbType, changeLog, this);
            }
        }
        catch (PreconditionFailedException e) {
            throw e;
        }
        catch (Exception e) {
            throw new PreconditionErrorException(e, changeLog, this);
        }
    }

    @Override
    public String getName() {
        return "dbms";
    }
}

