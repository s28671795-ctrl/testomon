/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import java.util.ArrayList;
import java.util.Arrays;
import liquibase.Scope;
import liquibase.change.AbstractChange;
import liquibase.change.Change;
import liquibase.change.ChangeFactory;
import liquibase.change.ChangeStatus;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.change.core.AddForeignKeyConstraintChange;
import liquibase.change.core.AddNotNullConstraintChange;
import liquibase.change.core.AddPrimaryKeyChange;
import liquibase.change.core.DropForeignKeyConstraintChange;
import liquibase.change.core.DropTableChange;
import liquibase.database.Database;
import liquibase.database.core.DB2Database;
import liquibase.database.core.Db2zDatabase;
import liquibase.database.core.HsqlDatabase;
import liquibase.database.core.InformixDatabase;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.SybaseASADatabase;
import liquibase.datatype.DataTypeFactory;
import liquibase.exception.ValidationErrors;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.statement.NotNullConstraint;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.CreateTableStatement;
import liquibase.statement.core.RawSqlStatement;
import liquibase.statement.core.ReorganizeTableStatement;
import liquibase.structure.core.Column;
import liquibase.structure.core.ForeignKey;
import liquibase.structure.core.Table;

@DatabaseChange(name="addLookupTable", priority=1, appliesTo={"column"}, description="Creates a lookup table containing values stored in a column and creates a foreign key to the new table.")
public class AddLookupTableChange
extends AbstractChange {
    private String existingTableCatalogName;
    private String existingTableSchemaName;
    private String existingTableName;
    private String existingColumnName;
    private String newTableCatalogName;
    private String newTableSchemaName;
    private String newTableName;
    private String newColumnName;
    private String newColumnDataType;
    private String constraintName;

    @Override
    public ValidationErrors validate(Database database) {
        ValidationErrors errors2 = super.validate(database);
        if (database instanceof Db2zDatabase && this.getNewColumnDataType() == null) {
            errors2.addError("newColumnDataType is required for " + Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(this).getName() + " on " + database.getShortName());
        }
        return errors2;
    }

    @DatabaseChangeProperty(description="Name of the database catalog of the existing table")
    public String getExistingTableCatalogName() {
        return this.existingTableCatalogName;
    }

    public void setExistingTableCatalogName(String existingTableCatalogName) {
        this.existingTableCatalogName = existingTableCatalogName;
    }

    @DatabaseChangeProperty(mustEqualExisting="column.relation.schema", description="Name of the database schema where the table containing data to extract resides")
    public String getExistingTableSchemaName() {
        return this.existingTableSchemaName;
    }

    public void setExistingTableSchemaName(String existingTableSchemaName) {
        this.existingTableSchemaName = existingTableSchemaName;
    }

    @DatabaseChangeProperty(mustEqualExisting="column.relation", description="Name of the table containing the data to extract", exampleValue="address")
    public String getExistingTableName() {
        return this.existingTableName;
    }

    public void setExistingTableName(String existingTableName) {
        this.existingTableName = existingTableName;
    }

    @DatabaseChangeProperty(mustEqualExisting="column", description="Name of the column containing the data to extract", exampleValue="state")
    public String getExistingColumnName() {
        return this.existingColumnName;
    }

    public void setExistingColumnName(String existingColumnName) {
        this.existingColumnName = existingColumnName;
    }

    @DatabaseChangeProperty(since="3.0", description="Name of the database catalog for the lookup table")
    public String getNewTableCatalogName() {
        return this.newTableCatalogName;
    }

    public void setNewTableCatalogName(String newTableCatalogName) {
        this.newTableCatalogName = newTableCatalogName;
    }

    @DatabaseChangeProperty(description="Name of the database schema for the lookup table")
    public String getNewTableSchemaName() {
        return this.newTableSchemaName;
    }

    public void setNewTableSchemaName(String newTableSchemaName) {
        this.newTableSchemaName = newTableSchemaName;
    }

    @DatabaseChangeProperty(description="Name of lookup table to create", exampleValue="state")
    public String getNewTableName() {
        return this.newTableName;
    }

    public void setNewTableName(String newTableName) {
        this.newTableName = newTableName;
    }

    @DatabaseChangeProperty(description="Name of the column in the new table to create", exampleValue="abbreviation")
    public String getNewColumnName() {
        return this.newColumnName;
    }

    public void setNewColumnName(String newColumnName) {
        this.newColumnName = newColumnName;
    }

    @DatabaseChangeProperty(description="Data type of the new table column", exampleValue="char(2)")
    public String getNewColumnDataType() {
        return this.newColumnDataType;
    }

    public void setNewColumnDataType(String newColumnDataType) {
        this.newColumnDataType = newColumnDataType;
    }

    @DatabaseChangeProperty(description="Name of the foreign key constraint to create between the existing table and the lookup table", exampleValue="fk_address_state")
    public String getConstraintName() {
        return this.constraintName;
    }

    public String getFinalConstraintName() {
        if (this.constraintName == null) {
            return ("FK_" + this.getExistingTableName() + "_" + this.getNewTableName()).toUpperCase();
        }
        return this.constraintName;
    }

    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }

    @Override
    public boolean supports(Database database) {
        if (database instanceof HsqlDatabase) {
            return false;
        }
        return super.supports(database);
    }

    @Override
    protected Change[] createInverses() {
        DropForeignKeyConstraintChange dropFK = new DropForeignKeyConstraintChange();
        dropFK.setBaseTableSchemaName(this.getExistingTableSchemaName());
        dropFK.setBaseTableName(this.getExistingTableName());
        dropFK.setConstraintName(this.getFinalConstraintName());
        DropTableChange dropTable = new DropTableChange();
        dropTable.setSchemaName(this.getNewTableSchemaName());
        dropTable.setTableName(this.getNewTableName());
        return new Change[]{dropFK, dropTable};
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        String newTableCatalogName = this.getNewTableCatalogName();
        String newTableSchemaName = this.getNewTableSchemaName();
        String existingTableCatalogName = this.getExistingTableCatalogName();
        String existingTableSchemaName = this.getExistingTableSchemaName();
        SqlStatement[] createTablesSQL = new SqlStatement[]{new RawSqlStatement("CREATE TABLE " + database.escapeTableName(newTableCatalogName, newTableSchemaName, this.getNewTableName()) + " AS SELECT DISTINCT " + database.escapeObjectName(this.getExistingColumnName(), Column.class) + " AS " + database.escapeObjectName(this.getNewColumnName(), Column.class) + " FROM " + database.escapeTableName(existingTableCatalogName, existingTableSchemaName, this.getExistingTableName()) + " WHERE " + database.escapeObjectName(this.getExistingColumnName(), Column.class) + " IS NOT NULL")};
        if (database instanceof MSSQLDatabase) {
            createTablesSQL = new SqlStatement[]{new RawSqlStatement("SELECT DISTINCT " + database.escapeObjectName(this.getExistingColumnName(), Column.class) + " AS " + database.escapeObjectName(this.getNewColumnName(), Column.class) + " INTO " + database.escapeTableName(newTableCatalogName, newTableSchemaName, this.getNewTableName()) + " FROM " + database.escapeTableName(existingTableCatalogName, existingTableSchemaName, this.getExistingTableName()) + " WHERE " + database.escapeObjectName(this.getExistingColumnName(), Column.class) + " IS NOT NULL")};
        } else if (database instanceof SybaseASADatabase) {
            createTablesSQL = new SqlStatement[]{new RawSqlStatement("SELECT DISTINCT " + database.escapeObjectName(this.getExistingColumnName(), Column.class) + " AS " + database.escapeObjectName(this.getNewColumnName(), Column.class) + " INTO " + database.escapeTableName(newTableCatalogName, newTableSchemaName, this.getNewTableName()) + " FROM " + database.escapeTableName(existingTableCatalogName, existingTableSchemaName, this.getExistingTableName()) + " WHERE " + database.escapeObjectName(this.getExistingColumnName(), Column.class) + " IS NOT NULL")};
        } else if (database instanceof Db2zDatabase) {
            CreateTableStatement tableStatement = new CreateTableStatement(newTableCatalogName, newTableSchemaName, this.getNewTableName());
            if (this.getNewColumnName() != null) {
                tableStatement.addColumn(this.getNewColumnName(), DataTypeFactory.getInstance().fromDescription(this.getNewColumnDataType(), database));
                tableStatement.addColumnConstraint(new NotNullConstraint(this.getNewColumnName()));
            }
            createTablesSQL = new SqlStatement[]{tableStatement, new RawSqlStatement("INSERT INTO " + database.escapeTableName(newTableCatalogName, newTableSchemaName, this.getNewTableName()) + " SELECT DISTINCT " + database.escapeObjectName(this.getExistingColumnName(), Column.class) + " FROM " + database.escapeTableName(existingTableCatalogName, existingTableSchemaName, this.getExistingTableName()) + " WHERE " + database.escapeObjectName(this.getExistingColumnName(), Column.class) + " IS NOT NULL")};
        } else if (database instanceof DB2Database) {
            createTablesSQL = new SqlStatement[]{new RawSqlStatement("CREATE TABLE " + database.escapeTableName(newTableCatalogName, newTableSchemaName, this.getNewTableName()) + " AS (SELECT " + database.escapeObjectName(this.getExistingColumnName(), Column.class) + " AS " + database.escapeObjectName(this.getNewColumnName(), Column.class) + " FROM " + database.escapeTableName(existingTableCatalogName, existingTableSchemaName, this.getExistingTableName()) + ") WITH NO DATA"), new RawSqlStatement("INSERT INTO " + database.escapeTableName(newTableCatalogName, newTableSchemaName, this.getNewTableName()) + " SELECT DISTINCT " + database.escapeObjectName(this.getExistingColumnName(), Column.class) + " FROM " + database.escapeTableName(existingTableCatalogName, existingTableSchemaName, this.getExistingTableName()) + " WHERE " + database.escapeObjectName(this.getExistingColumnName(), Column.class) + " IS NOT NULL")};
        } else if (database instanceof InformixDatabase) {
            createTablesSQL = new SqlStatement[]{new RawSqlStatement("CREATE TABLE " + database.escapeTableName(newTableCatalogName, newTableSchemaName, this.getNewTableName()) + " ( " + database.escapeObjectName(this.getNewColumnName(), Column.class) + " " + this.getNewColumnDataType() + " )"), new RawSqlStatement("INSERT INTO " + database.escapeTableName(newTableCatalogName, newTableSchemaName, this.getNewTableName()) + " ( " + database.escapeObjectName(this.getNewColumnName(), Column.class) + " ) SELECT DISTINCT " + database.escapeObjectName(this.getExistingColumnName(), Column.class) + " FROM " + database.escapeTableName(existingTableCatalogName, existingTableSchemaName, this.getExistingTableName()) + " WHERE " + database.escapeObjectName(this.getExistingColumnName(), Column.class) + " IS NOT NULL")};
        }
        ArrayList<SqlStatement> statements = new ArrayList<SqlStatement>(Arrays.asList(createTablesSQL));
        if (!(database instanceof OracleDatabase) && !(database instanceof Db2zDatabase)) {
            AddNotNullConstraintChange addNotNullChange = new AddNotNullConstraintChange();
            addNotNullChange.setSchemaName(newTableSchemaName);
            addNotNullChange.setTableName(this.getNewTableName());
            addNotNullChange.setColumnName(this.getNewColumnName());
            addNotNullChange.setColumnDataType(this.getNewColumnDataType());
            statements.addAll(Arrays.asList(addNotNullChange.generateStatements(database)));
        }
        if (database instanceof DB2Database) {
            statements.add(new ReorganizeTableStatement(newTableCatalogName, newTableSchemaName, this.getNewTableName()));
        }
        AddPrimaryKeyChange addPKChange = new AddPrimaryKeyChange();
        addPKChange.setSchemaName(newTableSchemaName);
        addPKChange.setTableName(this.getNewTableName());
        addPKChange.setColumnNames(this.getNewColumnName());
        statements.addAll(Arrays.asList(addPKChange.generateStatements(database)));
        if (database instanceof DB2Database) {
            statements.add(new ReorganizeTableStatement(newTableCatalogName, newTableSchemaName, this.getNewTableName()));
        }
        AddForeignKeyConstraintChange addFKChange = new AddForeignKeyConstraintChange();
        addFKChange.setBaseTableSchemaName(existingTableSchemaName);
        addFKChange.setBaseTableName(this.getExistingTableName());
        addFKChange.setBaseColumnNames(this.getExistingColumnName());
        addFKChange.setReferencedTableSchemaName(newTableSchemaName);
        addFKChange.setReferencedTableName(this.getNewTableName());
        addFKChange.setReferencedColumnNames(this.getNewColumnName());
        addFKChange.setConstraintName(this.getFinalConstraintName());
        statements.addAll(Arrays.asList(addFKChange.generateStatements(database)));
        return statements.toArray(SqlStatement.EMPTY_SQL_STATEMENT);
    }

    @Override
    public ChangeStatus checkStatus(Database database) {
        ChangeStatus result = new ChangeStatus();
        try {
            Table newTableExample = new Table(this.getNewTableCatalogName(), this.getNewTableSchemaName(), this.getNewTableName());
            Column newColumnExample = new Column(Table.class, this.getNewTableCatalogName(), this.getNewTableSchemaName(), this.getNewTableName(), this.getNewColumnName());
            ForeignKey foreignKeyExample = new ForeignKey(this.getConstraintName(), this.getExistingTableCatalogName(), this.getExistingTableSchemaName(), this.getExistingTableName(), new Column[0]);
            foreignKeyExample.setPrimaryKeyTable(newTableExample);
            foreignKeyExample.setForeignKeyColumns(Column.listFromNames(this.getExistingColumnName()));
            foreignKeyExample.setPrimaryKeyColumns(Column.listFromNames(this.getNewColumnName()));
            result.assertComplete(SnapshotGeneratorFactory.getInstance().has(newTableExample, database), "New table does not exist");
            result.assertComplete(SnapshotGeneratorFactory.getInstance().has(newColumnExample, database), "New column does not exist");
            result.assertComplete(SnapshotGeneratorFactory.getInstance().has(foreignKeyExample, database), "Foreign key does not exist");
            return result;
        }
        catch (Exception e) {
            return result.unknown(e);
        }
    }

    @Override
    public String getConfirmationMessage() {
        return "Lookup table added for " + this.getExistingTableName() + "." + this.getExistingColumnName();
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }
}

