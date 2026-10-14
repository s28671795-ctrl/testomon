/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import liquibase.change.AbstractChange;
import liquibase.change.ChangeStatus;
import liquibase.change.ChangeWithColumns;
import liquibase.change.ColumnConfig;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.database.Database;
import liquibase.database.core.DB2Database;
import liquibase.database.core.Db2zDatabase;
import liquibase.database.core.SQLiteDatabase;
import liquibase.exception.DatabaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.exception.ValidationErrors;
import liquibase.snapshot.InvalidExampleException;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.statement.AbstractSqlStatement;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.DropColumnStatement;
import liquibase.statement.core.ReorganizeTableStatement;
import liquibase.structure.core.Column;
import liquibase.structure.core.Index;
import liquibase.structure.core.Table;
import liquibase.util.StringUtil;

@DatabaseChange(name="dropColumn", description="Drop existing column(s).", priority=1, appliesTo={"column"})
public class DropColumnChange
extends AbstractChange
implements ChangeWithColumns<ColumnConfig> {
    private String catalogName;
    private String schemaName;
    private String tableName;
    private String columnName;
    private List<ColumnConfig> columns = new ArrayList<ColumnConfig>();

    @Override
    public boolean generateStatementsVolatile(Database database) {
        if (database instanceof SQLiteDatabase) {
            return true;
        }
        return super.generateStatementsVolatile(database);
    }

    @Override
    public boolean supports(Database database) {
        return database instanceof SQLiteDatabase || !(database instanceof Db2zDatabase) && super.supports(database);
    }

    @Override
    public ValidationErrors validate(Database database) {
        if (database instanceof SQLiteDatabase) {
            ValidationErrors validationErrors = new ValidationErrors();
            validationErrors.checkRequiredField("tableName", this.tableName);
            validationErrors.checkRequiredField("columnName", this.columnName);
            return validationErrors;
        }
        return super.validate(database);
    }

    @DatabaseChangeProperty(requiredForDatabase={"none"}, mustEqualExisting="column", description="Name of the column to drop, if dropping a single column. Ignore if nested columns are defined")
    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @DatabaseChangeProperty(since="3.0", mustEqualExisting="column.relation.schema.catalog", description="Name of the database catalog")
    public String getCatalogName() {
        return this.catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    @DatabaseChangeProperty(mustEqualExisting="column.relation.schema", description="Name of the database schema")
    public String getSchemaName() {
        return this.schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    @DatabaseChangeProperty(description="Name of the table containing the column to drop", mustEqualExisting="column.relation")
    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        try {
            if (this.isMultiple()) {
                return this.generateMultipleColumns(database);
            }
            return this.generateSingleColumn(database);
        }
        catch (DatabaseException e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    private SqlStatement[] generateMultipleColumns(Database database) throws DatabaseException {
        ArrayList<SqlStatement> statements = new ArrayList<SqlStatement>();
        ArrayList<DropColumnStatement> dropStatements = new ArrayList<DropColumnStatement>();
        if (database instanceof SQLiteDatabase) {
            statements.addAll(Arrays.asList(this.generateStatementsForSQLiteDatabase(database)));
        } else {
            for (ColumnConfig column : this.columns) {
                dropStatements.add(new DropColumnStatement(this.getCatalogName(), this.getSchemaName(), this.getTableName(), column.getName()));
            }
        }
        if (dropStatements.size() == 1) {
            statements.add((SqlStatement)dropStatements.get(0));
        } else if (dropStatements.size() > 1) {
            statements.add(new DropColumnStatement(dropStatements));
        }
        if (database instanceof DB2Database) {
            statements.add(new ReorganizeTableStatement(this.getCatalogName(), this.getSchemaName(), this.getTableName()));
        }
        return statements.toArray(SqlStatement.EMPTY_SQL_STATEMENT);
    }

    private SqlStatement[] generateSingleColumn(Database database) throws DatabaseException {
        if (database instanceof SQLiteDatabase) {
            return this.generateStatementsForSQLiteDatabase(database);
        }
        ArrayList<AbstractSqlStatement> statements = new ArrayList<AbstractSqlStatement>();
        statements.add(new DropColumnStatement(this.getCatalogName(), this.getSchemaName(), this.getTableName(), this.getColumnName()));
        if (database instanceof DB2Database) {
            statements.add(new ReorganizeTableStatement(this.getCatalogName(), this.getSchemaName(), this.getTableName()));
        }
        return statements.toArray(SqlStatement.EMPTY_SQL_STATEMENT);
    }

    @Override
    public ChangeStatus checkStatus(Database database) {
        try {
            return new ChangeStatus().assertComplete(!SnapshotGeneratorFactory.getInstance().has(new Column(Table.class, this.getCatalogName(), this.getSchemaName(), this.getTableName(), this.getColumnName()), database), "Column exists");
        }
        catch (DatabaseException | InvalidExampleException e) {
            return new ChangeStatus().unknown(e);
        }
    }

    private SqlStatement[] generateStatementsForSQLiteDatabase(Database database) throws DatabaseException {
        SqlStatement[] sqlStatements = null;
        final Set removedColumnNames = this.columns.stream().map(ColumnConfig::getName).collect(Collectors.toSet());
        SQLiteDatabase.AlterTableVisitor alterTableVisitor = new SQLiteDatabase.AlterTableVisitor(){

            @Override
            public ColumnConfig[] getColumnsToAdd() {
                return new ColumnConfig[0];
            }

            @Override
            public boolean copyThisColumn(ColumnConfig column) {
                return !removedColumnNames.contains(column.getName());
            }

            @Override
            public boolean createThisColumn(ColumnConfig column) {
                return !removedColumnNames.contains(column.getName());
            }

            @Override
            public boolean createThisIndex(Index index) {
                boolean indexContainsColumn = false;
                for (Column column : index.getColumns()) {
                    if (!removedColumnNames.contains(column.getName())) continue;
                    indexContainsColumn = true;
                    break;
                }
                return !indexContainsColumn;
            }
        };
        List<SqlStatement> statements = SQLiteDatabase.getAlterTableStatements(alterTableVisitor, database, this.getCatalogName(), this.getSchemaName(), this.getTableName());
        if (statements.size() > 0) {
            sqlStatements = new SqlStatement[statements.size()];
            return statements.toArray(sqlStatements);
        }
        return sqlStatements;
    }

    @Override
    public String getConfirmationMessage() {
        if (this.isMultiple()) {
            ArrayList<String> names = new ArrayList<String>(this.columns.size());
            for (ColumnConfig column : this.columns) {
                names.add(column.getName());
            }
            return "Columns " + StringUtil.join(names, ",") + " dropped from " + this.getTableName();
        }
        return "Column " + this.getTableName() + "." + this.getColumnName() + " dropped";
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    @Override
    public Object getSerializableFieldValue(String field2) {
        Object value = super.getSerializableFieldValue(field2);
        if ("columns".equals(field2) && ((List)value).isEmpty()) {
            return null;
        }
        return value;
    }

    @Override
    public void addColumn(ColumnConfig column) {
        this.columns.add(column);
    }

    @Override
    @DatabaseChangeProperty(description="Columns to be dropped if dropping multiple columns. If this is populated, the columnName attribute is ignored.", requiredForDatabase={"none"})
    public List<ColumnConfig> getColumns() {
        return this.columns;
    }

    @Override
    public void setColumns(List<ColumnConfig> columns) {
        this.columns = columns;
    }

    private boolean isMultiple() {
        return this.columns != null && !this.columns.isEmpty();
    }
}

