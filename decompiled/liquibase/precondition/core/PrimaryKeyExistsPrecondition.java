/*
 * Decompiled with CFR 0.152.
 */
package liquibase.precondition.core;

import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.database.Database;
import liquibase.database.core.DB2Database;
import liquibase.database.core.H2Database;
import liquibase.database.core.HsqlDatabase;
import liquibase.database.core.MySQLDatabase;
import liquibase.database.core.SQLiteDatabase;
import liquibase.exception.DatabaseException;
import liquibase.exception.PreconditionErrorException;
import liquibase.exception.PreconditionFailedException;
import liquibase.exception.ValidationErrors;
import liquibase.exception.Warnings;
import liquibase.precondition.AbstractPrecondition;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.structure.core.PrimaryKey;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Table;
import liquibase.util.StringUtil;

public class PrimaryKeyExistsPrecondition
extends AbstractPrecondition {
    private String catalogName;
    private String schemaName;
    private String primaryKeyName;
    private String tableName;

    @Override
    public Warnings warn(Database database) {
        return new Warnings();
    }

    @Override
    public ValidationErrors validate(Database database) {
        ValidationErrors validationErrors = new ValidationErrors();
        if (this.getPrimaryKeyName() == null && this.getTableName() == null) {
            validationErrors.addError("Either primaryKeyName or tableName must be set");
        }
        return validationErrors;
    }

    @Override
    public void check(Database database, DatabaseChangeLog changeLog, ChangeSet changeSet, ChangeExecListener changeExecListener) throws PreconditionFailedException, PreconditionErrorException {
        try {
            PrimaryKey example = new PrimaryKey();
            Table table = new Table();
            table.setSchema(new Schema(this.getCatalogName(), this.getSchemaName()));
            if (StringUtil.trimToNull(this.getTableName()) != null) {
                table.setName(this.getTableName());
            } else if (database instanceof H2Database || database instanceof MySQLDatabase || database instanceof HsqlDatabase || database instanceof SQLiteDatabase || database instanceof DB2Database) {
                throw new DatabaseException("Database driver requires a table name to be specified in order to search for a primary key.");
            }
            example.setTable(table);
            example.setName(this.getPrimaryKeyName());
            if (!SnapshotGeneratorFactory.getInstance().has(example, database)) {
                if (this.tableName != null) {
                    throw new PreconditionFailedException("Primary Key does not exist on " + database.escapeObjectName(this.getTableName(), Table.class), changeLog, this);
                }
                throw new PreconditionFailedException("Primary Key " + database.escapeObjectName(this.getPrimaryKeyName(), PrimaryKey.class) + " does not exist", changeLog, this);
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
        return "primaryKeyExists";
    }

    public String getCatalogName() {
        return this.catalogName;
    }

    public String getSchemaName() {
        return this.schemaName;
    }

    public String getPrimaryKeyName() {
        return this.primaryKeyName;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public void setPrimaryKeyName(String primaryKeyName) {
        this.primaryKeyName = primaryKeyName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}

