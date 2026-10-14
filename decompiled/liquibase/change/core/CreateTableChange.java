/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import java.util.ArrayList;
import java.util.List;
import liquibase.ChecksumVersion;
import liquibase.change.AbstractChange;
import liquibase.change.Change;
import liquibase.change.ChangeStatus;
import liquibase.change.ChangeWithColumns;
import liquibase.change.ColumnConfig;
import liquibase.change.ConstraintsConfig;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.change.core.DropTableChange;
import liquibase.database.Database;
import liquibase.database.core.MySQLDatabase;
import liquibase.datatype.DataTypeFactory;
import liquibase.datatype.LiquibaseDataType;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.exception.ValidationErrors;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.statement.AbstractSqlStatement;
import liquibase.statement.AutoIncrementConstraint;
import liquibase.statement.ColumnConstraint;
import liquibase.statement.ForeignKeyConstraint;
import liquibase.statement.NotNullConstraint;
import liquibase.statement.SqlStatement;
import liquibase.statement.UniqueConstraint;
import liquibase.statement.core.CreateTableStatement;
import liquibase.statement.core.SetColumnRemarksStatement;
import liquibase.statement.core.SetTableRemarksStatement;
import liquibase.structure.core.Column;
import liquibase.structure.core.PrimaryKey;
import liquibase.structure.core.Table;
import liquibase.util.ObjectUtil;
import liquibase.util.StringUtil;

@DatabaseChange(name="createTable", description="Creates a table", priority=1)
public class CreateTableChange
extends AbstractChange
implements ChangeWithColumns<ColumnConfig> {
    private List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
    private String tableType;
    private String catalogName;
    private String schemaName;
    private String tableName;
    private String tablespace;
    private String remarks;
    private Boolean ifNotExists;

    @Override
    public ValidationErrors validate(Database database) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.addAll(super.validate(database));
        if (this.columns != null) {
            for (ColumnConfig columnConfig : this.columns) {
                if (columnConfig.getType() == null && !ObjectUtil.defaultIfNull(columnConfig.getComputed(), false).booleanValue()) {
                    validationErrors.addError("column 'type' is required for all columns");
                }
                if (columnConfig.getName() != null) continue;
                validationErrors.addError("column 'name' is required for all columns");
            }
        }
        return validationErrors;
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        CreateTableStatement statement = this.generateCreateTableStatement();
        for (ColumnConfig column : this.getColumns()) {
            ConstraintsConfig constraints = column.getConstraints();
            boolean isAutoIncrement = column.isAutoIncrement() != null && column.isAutoIncrement() != false;
            Object defaultValue = column.getDefaultValueObject();
            LiquibaseDataType columnType = null;
            if (column.getType() != null) {
                columnType = DataTypeFactory.getInstance().fromDescription(column.getType() + (isAutoIncrement ? "{autoIncrement:true}" : ""), database);
                isAutoIncrement |= columnType.isAutoIncrement();
            }
            if (constraints != null && constraints.isPrimaryKey() != null && constraints.isPrimaryKey().booleanValue()) {
                statement.addPrimaryKeyColumn(column.getName(), columnType, defaultValue, constraints.getValidatePrimaryKey(), constraints.isDeferrable() != null && constraints.isDeferrable() != false, constraints.isInitiallyDeferred() != null && constraints.isInitiallyDeferred() != false, constraints.getPrimaryKeyName(), constraints.getPrimaryKeyTablespace(), column.getRemarks(), new ColumnConstraint[0]);
            } else {
                statement.addColumn(column.getName(), columnType, column.getDefaultValueConstraintName(), defaultValue, column.getRemarks(), new ColumnConstraint[0]);
            }
            if (constraints != null) {
                if (constraints.isNullable() != null && !constraints.isNullable().booleanValue()) {
                    NotNullConstraint notNullConstraint = new NotNullConstraint(column.getName()).setConstraintName(constraints.getNotNullConstraintName()).setValidateNullable(constraints.getValidateNullable() == null || constraints.getValidateNullable() != false);
                    statement.addColumnConstraint(notNullConstraint);
                }
                if (constraints.getReferences() != null || constraints.getReferencedTableName() != null && constraints.getReferencedColumnNames() != null) {
                    if (StringUtil.trimToNull(constraints.getForeignKeyName()) == null) {
                        throw new UnexpectedLiquibaseException("createTable with references requires foreignKeyName");
                    }
                    ForeignKeyConstraint fkConstraint = new ForeignKeyConstraint(constraints.getForeignKeyName(), constraints.getReferences(), constraints.getReferencedTableName(), constraints.getReferencedColumnNames());
                    fkConstraint.setReferencedTableCatalogName(constraints.getReferencedTableCatalogName());
                    fkConstraint.setReferencedTableSchemaName(constraints.getReferencedTableSchemaName());
                    fkConstraint.setColumn(column.getName());
                    fkConstraint.setDeleteCascade(constraints.isDeleteCascade() != null && constraints.isDeleteCascade() != false);
                    fkConstraint.setInitiallyDeferred(constraints.isInitiallyDeferred() != null && constraints.isInitiallyDeferred() != false);
                    fkConstraint.setDeferrable(constraints.isDeferrable() != null && constraints.isDeferrable() != false);
                    Boolean validate = constraints.getValidateForeignKey();
                    if (validate != null) {
                        fkConstraint.setValidateForeignKey(constraints.getValidateForeignKey());
                    }
                    statement.addColumnConstraint(fkConstraint);
                }
                if (constraints.isUnique() != null && constraints.isUnique().booleanValue()) {
                    statement.addColumnConstraint(new UniqueConstraint(constraints.getUniqueConstraintName(), constraints.getValidateUnique() == null || constraints.getValidateUnique() != false).addColumns(column.getName()));
                }
            }
            if (!isAutoIncrement) continue;
            statement.addColumnConstraint(new AutoIncrementConstraint(column.getName(), column.getStartWith(), column.getIncrementBy(), column.getGenerationType(), column.getDefaultOnNull()));
        }
        statement.setTablespace(StringUtil.trimToNull(this.getTablespace()));
        ArrayList<AbstractSqlStatement> statements = new ArrayList<AbstractSqlStatement>();
        statements.add(statement);
        if (StringUtil.trimToNull(this.remarks) != null && !(database instanceof MySQLDatabase)) {
            SetTableRemarksStatement remarksStatement = new SetTableRemarksStatement(this.catalogName, this.schemaName, this.tableName, this.remarks);
            if (SqlGeneratorFactory.getInstance().supports(remarksStatement, database)) {
                statements.add(remarksStatement);
            }
        }
        for (ColumnConfig column : this.getColumns()) {
            Boolean computed;
            String columnRemarks = StringUtil.trimToNull(column.getRemarks());
            if (columnRemarks != null && !(database instanceof MySQLDatabase)) {
                SetColumnRemarksStatement remarksStatement = new SetColumnRemarksStatement(this.catalogName, this.schemaName, this.tableName, column.getName(), columnRemarks, column.getType());
                if (SqlGeneratorFactory.getInstance().supports(remarksStatement, database)) {
                    statements.add(remarksStatement);
                }
            }
            if ((computed = column.getComputed()) == null || !computed.booleanValue()) continue;
            statement.setComputed(column.getName());
        }
        return statements.toArray(SqlStatement.EMPTY_SQL_STATEMENT);
    }

    protected CreateTableStatement generateCreateTableStatement() {
        return new CreateTableStatement(this.getCatalogName(), this.getSchemaName(), this.getTableName(), this.getRemarks(), this.getTableType(), Boolean.TRUE.equals(this.getIfNotExists()));
    }

    @Override
    protected Change[] createInverses() {
        DropTableChange inverse = new DropTableChange();
        inverse.setCatalogName(this.getCatalogName());
        inverse.setSchemaName(this.getSchemaName());
        inverse.setTableName(this.getTableName());
        return new Change[]{inverse};
    }

    @Override
    public ChangeStatus checkStatus(Database database) {
        try {
            Table example = (Table)new Table().setName(this.getTableName()).setSchema(this.getCatalogName(), this.getSchemaName());
            ChangeStatus status = new ChangeStatus();
            Table tableSnapshot = SnapshotGeneratorFactory.getInstance().createSnapshot(example, database);
            status.assertComplete(tableSnapshot != null, "Table does not exist");
            if (tableSnapshot != null) {
                for (ColumnConfig columnConfig : this.getColumns()) {
                    ConstraintsConfig constraints;
                    Column columnSnapshot = SnapshotGeneratorFactory.getInstance().createSnapshot(new Column(columnConfig).setRelation(tableSnapshot), database);
                    status.assertCorrect(columnSnapshot != null, "Column " + columnConfig.getName() + " is missing");
                    if (columnSnapshot == null || (constraints = columnConfig.getConstraints()) == null) continue;
                    if (constraints.isPrimaryKey() != null && constraints.isPrimaryKey().booleanValue()) {
                        PrimaryKey tablePk = tableSnapshot.getPrimaryKey();
                        status.assertCorrect(tablePk != null && tablePk.getColumnNamesAsList().contains(columnConfig.getName()), "Column " + columnConfig.getName() + " is not part of the primary key");
                    }
                    if (constraints.isNullable() == null) continue;
                    if (constraints.isNullable().booleanValue()) {
                        status.assertCorrect(columnSnapshot.isNullable() == null || columnSnapshot.isNullable() != false, "Column " + columnConfig.getName() + " nullability does not match");
                        continue;
                    }
                    status.assertCorrect(columnSnapshot.isNullable() != null && columnSnapshot.isNullable() == false, "Column " + columnConfig.getName() + " nullability does not match");
                }
            }
            return status;
        }
        catch (Exception e) {
            return new ChangeStatus().unknown(e);
        }
    }

    @Override
    @DatabaseChangeProperty(requiredForDatabase={"all"}, description="Column definitions")
    public List<ColumnConfig> getColumns() {
        if (this.columns == null) {
            return new ArrayList<ColumnConfig>();
        }
        return this.columns;
    }

    @Override
    public void setColumns(List<ColumnConfig> columns) {
        this.columns = columns;
    }

    @DatabaseChangeProperty(since="3.0", description="Name of the database catalog")
    public String getCatalogName() {
        return this.catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    @DatabaseChangeProperty(description="Name of the database schema")
    public String getSchemaName() {
        return this.schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    @DatabaseChangeProperty(description="Name of the table to create")
    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @DatabaseChangeProperty(description="Tablespace to create the table in. Corresponds to file group in mssql")
    public String getTablespace() {
        return this.tablespace;
    }

    public void setTablespace(String tablespace) {
        this.tablespace = tablespace;
    }

    @Override
    public void addColumn(ColumnConfig column) {
        this.columns.add(column);
    }

    @DatabaseChangeProperty(description="A brief descriptive comment to store in the table metadata")
    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String getConfirmationMessage() {
        return "Table " + this.tableName + " created";
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    @DatabaseChangeProperty(description="In some databases, specifies the type of the table (column-based, row-based...)")
    public String getTableType() {
        return this.tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    @DatabaseChangeProperty(description="If true, creates the table only if it does not already exist. Appends IF NOT EXISTS syntax to SQL query")
    public Boolean getIfNotExists() {
        return this.ifNotExists;
    }

    public void setIfNotExists(Boolean ifNotExists) {
        this.ifNotExists = ifNotExists;
    }

    @Override
    public String[] getExcludedFieldFilters(ChecksumVersion version) {
        return new String[]{"ifNotExists"};
    }
}

