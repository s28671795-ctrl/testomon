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
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Table;

public class TableExistsPrecondition
extends AbstractPrecondition {
    private String catalogName;
    private String schemaName;
    private String tableName;

    public String getCatalogName() {
        return this.catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getSchemaName() {
        return this.schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
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
            String correctedTableName = database.correctObjectName(this.getTableName(), Table.class);
            if (!SnapshotGeneratorFactory.getInstance().has(new Table().setName(correctedTableName).setSchema(new Schema(this.getCatalogName(), this.getSchemaName())), database)) {
                throw new PreconditionFailedException("Table " + database.escapeTableName(this.getCatalogName(), this.getSchemaName(), this.getTableName()) + " does not exist", changeLog, this);
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
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    @Override
    public String getName() {
        return "tableExists";
    }
}

