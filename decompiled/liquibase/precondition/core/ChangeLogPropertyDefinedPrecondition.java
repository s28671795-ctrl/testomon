/*
 * Decompiled with CFR 0.152.
 */
package liquibase.precondition.core;

import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.database.Database;
import liquibase.exception.PreconditionErrorException;
import liquibase.exception.PreconditionFailedException;
import liquibase.exception.ValidationErrors;
import liquibase.exception.Warnings;
import liquibase.precondition.AbstractPrecondition;

public class ChangeLogPropertyDefinedPrecondition
extends AbstractPrecondition {
    private String property;
    private String value;

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    @Override
    public String getName() {
        return "changeLogPropertyDefined";
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public void setValue(String value) {
        this.value = value;
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
        ChangeLogParameters changeLogParameters = changeLog.getChangeLogParameters();
        if (changeLogParameters == null) {
            throw new PreconditionFailedException("No Changelog properties were set", changeLog, this);
        }
        Object propertyValue = changeLogParameters.getValue(this.property, changeLog);
        if (propertyValue == null) {
            throw new PreconditionFailedException("Changelog property '" + this.property + "' was not set", changeLog, this);
        }
        if (this.value != null && !propertyValue.toString().equals(this.value)) {
            throw new PreconditionFailedException("Expected changelog property '" + this.property + "' to have a value of '" + this.value + "'.  Got '" + propertyValue + "'", changeLog, this);
        }
    }

    public String getProperty() {
        return this.property;
    }

    public String getValue() {
        return this.value;
    }
}

