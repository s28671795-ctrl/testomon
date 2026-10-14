/*
 * Decompiled with CFR 0.152.
 */
package liquibase.precondition.core;

import liquibase.Scope;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.database.Database;
import liquibase.exception.PreconditionErrorException;
import liquibase.exception.PreconditionFailedException;
import liquibase.exception.ValidationErrors;
import liquibase.exception.Warnings;
import liquibase.executor.ExecutorService;
import liquibase.precondition.AbstractPrecondition;
import liquibase.statement.core.TableIsEmptyStatement;

public class TableIsEmptyPrecondition
extends AbstractPrecondition {
    private String catalogName;
    private String schemaName;
    private String tableName;

    @Override
    public void check(Database database, DatabaseChangeLog changeLog, ChangeSet changeSet, ChangeExecListener changeExecListener) throws PreconditionFailedException, PreconditionErrorException {
        try {
            TableIsEmptyStatement statement = new TableIsEmptyStatement(this.getCatalogName(), this.getSchemaName(), this.getTableName());
            int result = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", database).queryForInt(statement);
            if (result > 0) {
                throw new PreconditionFailedException("Table " + this.getTableName() + " is not empty.", changeLog, this);
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
    public Warnings warn(Database database) {
        return new Warnings();
    }

    @Override
    public ValidationErrors validate(Database database) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("tableName", this.tableName);
        return validationErrors;
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    @Override
    public String getName() {
        return "tableIsEmpty";
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getCatalogName() {
        return this.catalogName;
    }

    public String getSchemaName() {
        return this.schemaName;
    }

    public String getTableName() {
        return this.tableName;
    }
}

