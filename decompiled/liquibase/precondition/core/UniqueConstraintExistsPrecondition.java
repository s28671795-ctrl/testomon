/*
 * Decompiled with CFR 0.152.
 */
package liquibase.precondition.core;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.database.Database;
import liquibase.exception.DatabaseException;
import liquibase.exception.PreconditionErrorException;
import liquibase.exception.PreconditionFailedException;
import liquibase.exception.ValidationErrors;
import liquibase.exception.Warnings;
import liquibase.precondition.AbstractPrecondition;
import liquibase.snapshot.InvalidExampleException;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Column;
import liquibase.structure.core.UniqueConstraint;
import liquibase.util.StringUtil;

public class UniqueConstraintExistsPrecondition
extends AbstractPrecondition {
    private String catalogName;
    private String schemaName;
    private String tableName;
    private String columnNames;
    private String constraintName;

    public String getConstraintName() {
        return this.constraintName;
    }

    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
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

    public String getColumnNames() {
        return this.columnNames;
    }

    public void setColumnNames(String columnNames) {
        this.columnNames = columnNames;
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    @Override
    public String getName() {
        return "uniqueConstraintExists";
    }

    @Override
    public Warnings warn(Database database) {
        return new Warnings();
    }

    @Override
    public ValidationErrors validate(Database database) {
        ValidationErrors validationErrors = new ValidationErrors(this);
        validationErrors.checkRequiredField("tableName", this.getTableName());
        if (StringUtil.trimToNull(this.getConstraintName()) == null && StringUtil.trimToNull(this.getColumnNames()) == null) {
            validationErrors.addError("constraintName OR columnNames is required for " + this.getName());
        }
        return validationErrors;
    }

    @Override
    public void check(Database database, DatabaseChangeLog changeLog, ChangeSet changeSet, ChangeExecListener changeExecListener) throws PreconditionFailedException, PreconditionErrorException {
        String schemaName = this.getSchema(database);
        UniqueConstraint example = new UniqueConstraint(StringUtil.trimToNull(this.getConstraintName()), StringUtil.trimToNull(this.getCatalogName()), StringUtil.trimToNull(schemaName), StringUtil.trimToNull(this.getTableName()), new Column[0]);
        String columnNames = StringUtil.trimToNull(this.getColumnNames());
        if (columnNames != null) {
            example.setColumns(this.toColumns(database, columnNames));
        }
        try {
            if (!SnapshotGeneratorFactory.getInstance().has(example, database)) {
                throw new PreconditionFailedException(String.format("%s does not exist", example), changeLog, this);
            }
        }
        catch (DatabaseException | InvalidExampleException e) {
            throw new PreconditionErrorException(e, changeLog, this);
        }
    }

    private String getSchema(Database database) {
        String schemaName = this.getSchemaName();
        if (schemaName == null) {
            schemaName = database.getDefaultSchemaName();
        }
        return schemaName;
    }

    private List<Column> toColumns(Database database, String columnNames) {
        return Arrays.stream(columnNames.split("\\s*,\\s*")).map(columnName -> database.correctObjectName((String)columnName, (Class<? extends DatabaseObject>)Column.class)).map(Column::new).collect(Collectors.toList());
    }

    public String toString() {
        String string = "Unique Constraint Exists Precondition: ";
        if (this.getConstraintName() != null) {
            string = string + this.getConstraintName();
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

