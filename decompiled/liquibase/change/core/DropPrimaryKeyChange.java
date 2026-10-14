/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import java.util.List;
import liquibase.change.AbstractChange;
import liquibase.change.ChangeStatus;
import liquibase.change.ColumnConfig;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.database.Database;
import liquibase.database.core.SQLiteDatabase;
import liquibase.exception.DatabaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.DropPrimaryKeyStatement;
import liquibase.structure.core.Column;
import liquibase.structure.core.Index;
import liquibase.structure.core.PrimaryKey;

@DatabaseChange(name="dropPrimaryKey", description="Drops an existing primary key", priority=1, appliesTo={"primaryKey"})
public class DropPrimaryKeyChange
extends AbstractChange {
    private String catalogName;
    private String schemaName;
    private String tableName;
    private String constraintName;
    private Boolean dropIndex;

    @Override
    public boolean generateStatementsVolatile(Database database) {
        return database instanceof SQLiteDatabase;
    }

    @DatabaseChangeProperty(mustEqualExisting="primaryKey.catalog", description="Name of the database catalog")
    public String getCatalogName() {
        return this.catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    @DatabaseChangeProperty(mustEqualExisting="primaryKey.schema", description="Name of the database schema")
    public String getSchemaName() {
        return this.schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    @DatabaseChangeProperty(mustEqualExisting="primaryKey.table", description="Name of the table to drop the primary key from")
    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @DatabaseChangeProperty(mustEqualExisting="primaryKey", description="Name of the primary key to drop")
    public String getConstraintName() {
        return this.constraintName;
    }

    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }

    @DatabaseChangeProperty(description="Whether to drop the index associated with the primary key")
    public Boolean getDropIndex() {
        return this.dropIndex;
    }

    public void setDropIndex(Boolean dropIndex) {
        this.dropIndex = dropIndex;
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        if (database instanceof SQLiteDatabase) {
            return this.generateStatementsForSQLiteDatabase(database);
        }
        DropPrimaryKeyStatement statement = new DropPrimaryKeyStatement(this.getCatalogName(), this.getSchemaName(), this.getTableName(), this.getConstraintName());
        statement.setDropIndex(this.dropIndex);
        return new SqlStatement[]{statement};
    }

    @Override
    public ChangeStatus checkStatus(Database database) {
        try {
            return new ChangeStatus().assertComplete(!SnapshotGeneratorFactory.getInstance().has(new PrimaryKey(this.getConstraintName(), this.getCatalogName(), this.getSchemaName(), this.getTableName(), new Column[0]), database), "Primary key exists");
        }
        catch (Exception e) {
            return new ChangeStatus().unknown(e);
        }
    }

    private SqlStatement[] generateStatementsForSQLiteDatabase(Database database) {
        List<SqlStatement> statements;
        SqlStatement[] sqlStatements = null;
        SQLiteDatabase.AlterTableVisitor alterTableVisitor = new SQLiteDatabase.AlterTableVisitor(){

            @Override
            public ColumnConfig[] getColumnsToAdd() {
                return new ColumnConfig[0];
            }

            @Override
            public boolean copyThisColumn(ColumnConfig column) {
                if (column.getConstraints().isPrimaryKey().booleanValue()) {
                    column.getConstraints().setPrimaryKey(false);
                }
                return true;
            }

            @Override
            public boolean createThisColumn(ColumnConfig column) {
                if (column.getConstraints().isPrimaryKey().booleanValue()) {
                    column.getConstraints().setPrimaryKey(false);
                }
                return true;
            }

            @Override
            public boolean createThisIndex(Index index) {
                return true;
            }
        };
        try {
            statements = SQLiteDatabase.getAlterTableStatements(alterTableVisitor, database, this.getCatalogName(), this.getSchemaName(), this.getTableName());
        }
        catch (DatabaseException e) {
            throw new UnexpectedLiquibaseException(e);
        }
        if (statements.size() > 0) {
            sqlStatements = new SqlStatement[statements.size()];
            return statements.toArray(sqlStatements);
        }
        return sqlStatements;
    }

    @Override
    public String getConfirmationMessage() {
        return "Primary key dropped from " + this.getTableName();
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }
}

