/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import liquibase.ChecksumVersion;
import liquibase.change.AbstractChange;
import liquibase.change.AddColumnConfig;
import liquibase.change.Change;
import liquibase.change.ChangeStatus;
import liquibase.change.ChangeWithColumns;
import liquibase.change.ColumnConfig;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.change.core.DropIndexChange;
import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.CreateIndexStatement;
import liquibase.structure.core.Column;
import liquibase.structure.core.Index;

@DatabaseChange(name="createIndex", description="Creates an index on an existing column or set of columns.", priority=1, appliesTo={"index"})
public class CreateIndexChange
extends AbstractChange
implements ChangeWithColumns<AddColumnConfig> {
    private String catalogName;
    private String schemaName;
    private String tableName;
    private String indexName;
    private Boolean unique;
    private String tablespace;
    private List<AddColumnConfig> columns = new ArrayList<AddColumnConfig>();
    private String associatedWith;
    private Boolean clustered;

    @DatabaseChangeProperty(mustEqualExisting="index", description="Name of the index to create")
    public String getIndexName() {
        return this.indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    @DatabaseChangeProperty(since="3.0", description="Name of the database catalog")
    public String getCatalogName() {
        return this.catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    @DatabaseChangeProperty(mustEqualExisting="index.schema", description="Name of the database schema")
    public String getSchemaName() {
        return this.schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    @DatabaseChangeProperty(mustEqualExisting="index.table", description="Name of the table to add the index on", exampleValue="person")
    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    @DatabaseChangeProperty(mustEqualExisting="index.column", description="Column(s) in the table to add the index on", requiredForDatabase={"all"})
    public List<AddColumnConfig> getColumns() {
        if (this.columns == null) {
            return new ArrayList<AddColumnConfig>();
        }
        return this.columns;
    }

    @Override
    public void setColumns(List<AddColumnConfig> columns) {
        this.columns = columns;
    }

    @Override
    public void addColumn(AddColumnConfig column) {
        this.columns.add(column);
    }

    @DatabaseChangeProperty(description="Tablepace to create the index in. Corresponds to file group in mssql")
    public String getTablespace() {
        return this.tablespace;
    }

    public void setTablespace(String tablespace) {
        this.tablespace = tablespace;
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        return new SqlStatement[]{new CreateIndexStatement(this.getIndexName(), this.getCatalogName(), this.getSchemaName(), this.getTableName(), this.isUnique(), this.getAssociatedWith(), this.getColumns().toArray(new AddColumnConfig[0])).setTablespace(this.getTablespace()).setClustered(this.getClustered())};
    }

    @Override
    protected Change[] createInverses() {
        DropIndexChange inverse = new DropIndexChange();
        inverse.setSchemaName(this.getSchemaName());
        inverse.setTableName(this.getTableName());
        inverse.setIndexName(this.getIndexName());
        return new Change[]{inverse};
    }

    @Override
    public ChangeStatus checkStatus(Database database) {
        ChangeStatus result = new ChangeStatus();
        try {
            Index snapshot;
            Index example = new Index(this.getIndexName(), this.getCatalogName(), this.getSchemaName(), this.getTableName(), new Column[0]);
            if (this.getColumns() != null) {
                for (ColumnConfig columnConfig : this.getColumns()) {
                    example.addColumn(new Column(columnConfig));
                }
            }
            result.assertComplete((snapshot = SnapshotGeneratorFactory.getInstance().createSnapshot(example, database)) != null, "Index does not exist");
            if (snapshot != null && this.isUnique() != null) {
                result.assertCorrect(this.isUnique().equals(snapshot.isUnique()), "Unique does not match");
            }
            return result;
        }
        catch (Exception e) {
            return result.unknown(e);
        }
    }

    @Override
    public String getConfirmationMessage() {
        return "Index " + this.getIndexName() + " created";
    }

    @DatabaseChangeProperty(description="Whether the index is unique (contains no duplicate values)", since="1.8")
    public Boolean isUnique() {
        return this.unique;
    }

    public void setUnique(Boolean isUnique) {
        this.unique = isUnique;
    }

    @DatabaseChangeProperty(description="Index associations. Valid values: primaryKey, foreignKey, uniqueConstriant, none")
    public String getAssociatedWith() {
        return this.associatedWith;
    }

    public void setAssociatedWith(String associatedWith) {
        this.associatedWith = associatedWith;
    }

    @DatabaseChangeProperty(description="Whether to create a clustered index")
    public Boolean getClustered() {
        return this.clustered;
    }

    public void setClustered(Boolean clustered) {
        this.clustered = clustered;
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    @Override
    public Object getSerializableFieldValue(String field2) {
        Object value = super.getSerializableFieldValue(field2);
        if (value != null && "columns".equals(field2)) {
            for (ColumnConfig config : (Collection)value) {
                config.setType(null);
                config.setAutoIncrement(null);
                config.setConstraints(null);
                config.setDefaultValue(null);
                config.setValue(null);
                config.setStartWith(null);
                config.setIncrementBy(null);
                config.setEncoding(null);
                config.setRemarks(null);
            }
        }
        return value;
    }

    @Override
    public ValidationErrors validate(Database database) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.addAll(super.validate(database));
        if (this.columns != null) {
            for (ColumnConfig columnConfig : this.columns) {
                if (columnConfig.getName() != null) continue;
                validationErrors.addError("column 'name' is required for all columns in an index");
            }
        }
        return validationErrors;
    }

    @Override
    public String[] getExcludedFieldFilters(ChecksumVersion version) {
        return new String[]{"associatedWith"};
    }
}

