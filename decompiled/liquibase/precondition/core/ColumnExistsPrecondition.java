/*
 * Decompiled with CFR 0.152.
 */
package liquibase.precondition.core;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.database.Database;
import liquibase.database.core.FirebirdDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.exception.PreconditionErrorException;
import liquibase.exception.PreconditionFailedException;
import liquibase.exception.ValidationErrors;
import liquibase.exception.Warnings;
import liquibase.precondition.AbstractPrecondition;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.structure.core.Column;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Table;
import liquibase.util.StringUtil;

public class ColumnExistsPrecondition
extends AbstractPrecondition {
    private String catalogName;
    private String schemaName;
    private String tableName;
    private String columnName;

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

    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
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
        if (this.canCheckFast(database)) {
            this.checkFast(database, changeLog);
        } else {
            this.checkUsingSnapshot(database, changeLog);
        }
    }

    private void checkUsingSnapshot(Database database, DatabaseChangeLog changeLog) throws PreconditionFailedException, PreconditionErrorException {
        Column example = new Column();
        if (StringUtil.trimToNull(this.getTableName()) != null) {
            String schemaName = this.getSchemaName();
            if (schemaName == null) {
                schemaName = database.getDefaultSchemaName();
            }
            example.setRelation(new Table().setName(database.correctObjectName(this.getTableName(), Table.class)).setSchema(new Schema(this.getCatalogName(), schemaName)));
        }
        example.setName(database.correctObjectName(this.getColumnName(), Column.class));
        try {
            if (!SnapshotGeneratorFactory.getInstance().has(example, database)) {
                throw new PreconditionFailedException("Column '" + database.escapeColumnName(this.catalogName, this.schemaName, this.getTableName(), this.getColumnName()) + "' does not exist", changeLog, this);
            }
        }
        catch (LiquibaseException e) {
            throw new PreconditionErrorException(e, changeLog, this);
        }
    }

    private boolean canCheckFast(Database database) {
        if (this.getCatalogName() != null) {
            return false;
        }
        if (!(database.getConnection() instanceof JdbcConnection)) {
            return false;
        }
        if (this.getColumnName() == null) {
            return false;
        }
        if (!this.getColumnName().matches("(?i)[a-z][a-z_0-9]*")) {
            return false;
        }
        return this.getSchemaName() != null || database.getDefaultSchemaName() != null;
    }

    private void checkFast(Database database, DatabaseChangeLog changeLog) throws PreconditionFailedException, PreconditionErrorException {
        try {
            String schemaName = this.getSchemaName();
            if (schemaName == null) {
                schemaName = database.getDefaultSchemaName();
            }
            String tableName = this.getTableName();
            String columnName = this.getColumnName();
            if (database instanceof PostgresDatabase) {
                this.makeSureColumnExistsInPostgres(database, changeLog, schemaName, tableName, columnName);
            } else {
                this.makeSureColumnExistsInOtherDBs(database, changeLog, schemaName, tableName, columnName);
            }
        }
        catch (DatabaseException e) {
            throw new PreconditionErrorException(e, changeLog, this);
        }
    }

    private void makeSureColumnExistsInOtherDBs(Database database, DatabaseChangeLog changeLog, String schemaName, String tableName, String columnName) throws PreconditionFailedException {
        String sql = database instanceof FirebirdDatabase ? String.format("select t.%s from %s t where 0=1", database.escapeColumnNameList(columnName), database.escapeObjectName(tableName, Table.class)) : String.format("select t.%s from %s.%s t where 0=1", database.escapeColumnNameList(columnName), database.escapeObjectName(schemaName, Schema.class), database.escapeObjectName(tableName, Table.class));
        try (PreparedStatement statement2 = ((JdbcConnection)database.getConnection()).prepareStatement(sql);){
            ResultSet rs = statement2.executeQuery();
            if (rs != null) {
                rs.close();
            }
        }
        catch (SQLException | DatabaseException e) {
            throw new PreconditionFailedException(String.format("Column %s.%s.%s does not exist", schemaName, tableName, columnName), changeLog, this);
        }
    }

    private void makeSureColumnExistsInPostgres(Database database, DatabaseChangeLog changeLog, String schemaName, String tableName, String columnName) throws PreconditionFailedException, PreconditionErrorException, DatabaseException {
        String sql = "SELECT 1 FROM pg_attribute a WHERE EXISTS (SELECT 1 FROM pg_class JOIN pg_catalog.pg_namespace ns ON ns.oid = pg_class.relnamespace WHERE lower(ns.nspname) = ? AND lower(relname) = lower(?) AND pg_class.oid = a.attrelid) AND lower(a.attname) = lower(?);";
        try (PreparedStatement statement = ((JdbcConnection)database.getConnection()).prepareStatement(sql);){
            statement.setString(1, schemaName.toLowerCase());
            statement.setString(2, tableName);
            statement.setString(3, columnName);
            try (ResultSet rs = statement.executeQuery();){
                if (rs.next()) {
                    return;
                }
                throw new PreconditionFailedException(String.format("Column %s.%s.%s does not exist", schemaName, tableName, columnName), changeLog, this);
            }
        }
        catch (SQLException e) {
            throw new PreconditionErrorException(e, changeLog, this);
        }
    }

    @Override
    public String getName() {
        return "columnExists";
    }
}

