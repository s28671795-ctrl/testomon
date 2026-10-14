/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import java.util.ArrayList;
import java.util.List;
import liquibase.Scope;
import liquibase.change.AbstractChange;
import liquibase.change.ChangeStatus;
import liquibase.change.ChangeWithColumns;
import liquibase.change.ColumnConfig;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.change.DbmsTargetedChange;
import liquibase.change.core.LoadDataChange;
import liquibase.database.Database;
import liquibase.database.core.InformixDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.statement.InsertExecutablePreparedStatement;
import liquibase.statement.SequenceCurrentValueFunction;
import liquibase.statement.SequenceNextValueFunction;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.InsertStatement;
import liquibase.structure.core.Column;

@DatabaseChange(name="insert", description="Inserts data into an existing table", priority=1, appliesTo={"table"})
public class InsertDataChange
extends AbstractChange
implements ChangeWithColumns<ColumnConfig>,
DbmsTargetedChange {
    private String catalogName;
    private String schemaName;
    private String tableName;
    private List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
    private String dbms;

    @Override
    public ValidationErrors validate(Database database) {
        ValidationErrors validate = super.validate(database);
        validate.checkRequiredField("columns", this.columns);
        return validate;
    }

    @DatabaseChangeProperty(mustEqualExisting="table.catalog", since="3.0", description="Name of the database catalog")
    public String getCatalogName() {
        return this.catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    @DatabaseChangeProperty(mustEqualExisting="table.schema", description="Name of the database schema")
    public String getSchemaName() {
        return this.schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    @DatabaseChangeProperty(mustEqualExisting="table", description="Name of the table to insert data into")
    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    @DatabaseChangeProperty(mustEqualExisting="table.column", description="Data to insert into columns", requiredForDatabase={"all"})
    public List<ColumnConfig> getColumns() {
        return this.columns;
    }

    @Override
    public void setColumns(List<ColumnConfig> columns) {
        this.columns = columns;
    }

    @Override
    public void addColumn(ColumnConfig column) {
        this.columns.add(column);
    }

    public void removeColumn(ColumnConfig column) {
        this.columns.remove(column);
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        boolean needsPreparedStatement = false;
        for (ColumnConfig column : this.columns) {
            if (column.getValueBlobFile() != null) {
                needsPreparedStatement = true;
            }
            if (column.getValueClobFile() != null) {
                needsPreparedStatement = true;
            }
            if (LoadDataChange.LOAD_DATA_TYPE.BLOB.name().equalsIgnoreCase(column.getType())) {
                needsPreparedStatement = true;
            }
            if (LoadDataChange.LOAD_DATA_TYPE.CLOB.name().equalsIgnoreCase(column.getType())) {
                needsPreparedStatement = true;
            }
            if (needsPreparedStatement || !(database instanceof InformixDatabase) || column.getValue() == null) continue;
            try {
                Column snapshot = SnapshotGeneratorFactory.getInstance().createSnapshot(new Column(column), database);
                if (snapshot == null) continue;
                needsPreparedStatement = true;
            }
            catch (Exception snapshot) {}
        }
        if (needsPreparedStatement) {
            return new SqlStatement[]{new InsertExecutablePreparedStatement(database, this.catalogName, this.schemaName, this.tableName, this.columns, this.getChangeSet(), Scope.getCurrentScope().getResourceAccessor())};
        }
        InsertStatement statement = new InsertStatement(this.getCatalogName(), this.getSchemaName(), this.getTableName());
        for (ColumnConfig column : this.columns) {
            if (database.supportsAutoIncrement() && column.isAutoIncrement() != null && column.isAutoIncrement().booleanValue()) continue;
            Object valueObject = column.getValueObject();
            if (valueObject instanceof SequenceNextValueFunction) {
                ((SequenceNextValueFunction)valueObject).setSchemaName(this.getSchemaName());
            }
            if (valueObject instanceof SequenceCurrentValueFunction) {
                ((SequenceCurrentValueFunction)valueObject).setSchemaName(this.getSchemaName());
            }
            statement.addColumnValue(column.getName(), column.getValueObject());
        }
        return new SqlStatement[]{statement};
    }

    @Override
    public ChangeStatus checkStatus(Database database) {
        return new ChangeStatus().unknown("Cannot check insertData status");
    }

    @Override
    public String getConfirmationMessage() {
        return "New row inserted into " + this.getTableName();
    }

    @Override
    @DatabaseChangeProperty(since="3.0", exampleValue="h2, oracle", description="Specifies which database type(s) a changeset is to be used for. See valid database type names on Supported Databases docs page. Separate multiple databases with commas. Specify that a changeset is not applicable to a particular database type by prefixing with !. The keywords 'all' and 'none' are also available.")
    public String getDbms() {
        return this.dbms;
    }

    @Override
    public void setDbms(String dbms) {
        this.dbms = dbms;
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }
}

