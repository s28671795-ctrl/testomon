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
import liquibase.structure.core.Column;
import liquibase.structure.core.Index;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Table;
import liquibase.util.StringUtil;

public class IndexExistsPrecondition
extends AbstractPrecondition {
    private String catalogName;
    private String schemaName;
    private String tableName;
    private String columnNames;
    private String indexName;

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

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

    public String getIndexName() {
        return this.indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getColumnNames() {
        return this.columnNames;
    }

    public void setColumnNames(String columnNames) {
        this.columnNames = columnNames;
    }

    @Override
    public Warnings warn(Database database) {
        return new Warnings();
    }

    @Override
    public ValidationErrors validate(Database database) {
        ValidationErrors validationErrors = new ValidationErrors();
        if (this.getIndexName() == null && (this.getTableName() == null || this.getColumnNames() == null)) {
            validationErrors.addError("indexName OR (tableName and columnNames) is required");
        }
        return validationErrors;
    }

    @Override
    public void check(Database database, DatabaseChangeLog changeLog, ChangeSet changeSet, ChangeExecListener changeExecListener) throws PreconditionFailedException, PreconditionErrorException {
        try {
            Schema schema = new Schema(this.getCatalogName(), this.getSchemaName());
            Index example = new Index();
            String tableName = StringUtil.trimToNull(this.getTableName());
            if (tableName != null) {
                example.setRelation(new Table().setName(database.correctObjectName(this.getTableName(), Table.class)).setSchema(schema));
            }
            example.setName(database.correctObjectName(this.getIndexName(), Index.class));
            if (StringUtil.trimToNull(this.getColumnNames()) != null) {
                for (String column : this.getColumnNames().split("\\s*,\\s*")) {
                    example.addColumn(new Column(database.correctObjectName(column, Column.class)));
                }
            }
            if (!SnapshotGeneratorFactory.getInstance().has(example, database)) {
                String name = "";
                if (this.getIndexName() != null) {
                    name = name + database.escapeObjectName(this.getIndexName(), Index.class);
                }
                if (tableName != null) {
                    name = name + " on " + database.escapeObjectName(this.getTableName(), Table.class);
                    if (StringUtil.trimToNull(this.getColumnNames()) != null) {
                        name = name + " columns " + this.getColumnNames();
                    }
                }
                throw new PreconditionFailedException("Index " + name + " does not exist", changeLog, this);
            }
        }
        catch (Exception e) {
            if (e instanceof PreconditionFailedException) {
                throw (PreconditionFailedException)e;
            }
            throw new PreconditionErrorException(e, changeLog, this);
        }
    }

    @Override
    public String getName() {
        return "indexExists";
    }

    public String toString() {
        String string = "Index Exists Precondition: ";
        if (this.getIndexName() != null) {
            string = string + this.getIndexName();
        }
        if (this.tableName != null) {
            string = string + " on " + this.getTableName();
            if (StringUtil.trimToNull(this.getColumnNames()) != null) {
                string = string + " columns " + this.getColumnNames();
            }
        }
        return string;
    }
}

