/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import liquibase.change.AbstractChange;
import liquibase.change.AddColumnConfig;
import liquibase.change.Change;
import liquibase.change.ChangeStatus;
import liquibase.change.ChangeWithColumns;
import liquibase.change.ColumnConfig;
import liquibase.change.ConstraintsConfig;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.change.core.AddDefaultValueChange;
import liquibase.change.core.AddNotNullConstraintChange;
import liquibase.change.core.DropColumnChange;
import liquibase.change.core.DropDefaultValueChange;
import liquibase.change.visitor.AddColumnChangeVisitor;
import liquibase.change.visitor.ChangeVisitor;
import liquibase.database.Database;
import liquibase.database.core.DB2Database;
import liquibase.database.core.MySQLDatabase;
import liquibase.parser.core.ParsedNodeException;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.statement.AutoIncrementConstraint;
import liquibase.statement.ColumnConstraint;
import liquibase.statement.ForeignKeyConstraint;
import liquibase.statement.NotNullConstraint;
import liquibase.statement.PrimaryKeyConstraint;
import liquibase.statement.SqlStatement;
import liquibase.statement.UniqueConstraint;
import liquibase.statement.core.AddColumnStatement;
import liquibase.statement.core.ReorganizeTableStatement;
import liquibase.statement.core.SetColumnRemarksStatement;
import liquibase.statement.core.UpdateStatement;
import liquibase.structure.core.Column;
import liquibase.structure.core.PrimaryKey;
import liquibase.structure.core.Table;
import liquibase.util.ISODateFormat;
import liquibase.util.StringUtil;

@DatabaseChange(name="addColumn", priority=1, appliesTo={"table"}, description="Adds a new column to an existing table")
public class AddColumnChange
extends AbstractChange
implements ChangeWithColumns<AddColumnConfig> {
    private String catalogName;
    private String schemaName;
    private String tableName;
    private List<AddColumnConfig> columns = new ArrayList<AddColumnConfig>();

    @DatabaseChangeProperty(mustEqualExisting="relation.catalog", since="3.0", description="Name of the database catalog")
    public String getCatalogName() {
        return this.catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    @DatabaseChangeProperty(mustEqualExisting="relation.schema", description="Name of the database schema")
    public String getSchemaName() {
        return this.schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    @DatabaseChangeProperty(mustEqualExisting="table", description="Name of the table to add the column to")
    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    @DatabaseChangeProperty(requiredForDatabase={"all"}, description="Column constraint and foreign key information. Setting the \"defaultValue\" attribute specifies a default value for the column. Setting the \"value\" attribute sets all rows existing to the specified value without modifying the column default.")
    public List<AddColumnConfig> getColumns() {
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

    public void removeColumn(ColumnConfig column) {
        this.columns.remove(column);
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        ArrayList<SqlStatement> sql = new ArrayList<SqlStatement>();
        ArrayList<AddColumnStatement> addColumnStatements = new ArrayList<AddColumnStatement>();
        ArrayList<UpdateStatement> addColumnUpdateStatements = new ArrayList<UpdateStatement>();
        ArrayList<SqlStatement> addNotNullConstraintStatements = new ArrayList<SqlStatement>();
        if (this.getColumns().isEmpty()) {
            return new SqlStatement[]{new AddColumnStatement(this.catalogName, this.schemaName, this.tableName, null, null, null, new ColumnConstraint[0])};
        }
        for (AddColumnConfig addColumnConfig : this.getColumns()) {
            HashSet<ColumnConstraint> constraints = new HashSet<ColumnConstraint>();
            ConstraintsConfig constraintsConfig = addColumnConfig.getConstraints();
            if (constraintsConfig != null) {
                if (constraintsConfig.isNullable() != null && !constraintsConfig.isNullable().booleanValue()) {
                    if (addColumnConfig.getValueObject() != null) {
                        List<SqlStatement> sqlStatements = this.generateAddNotNullConstraintStatements(addColumnConfig, constraintsConfig, database);
                        addNotNullConstraintStatements.addAll(sqlStatements);
                    } else {
                        NotNullConstraint notNullConstraint = this.createNotNullConstraint(constraintsConfig);
                        constraints.add(notNullConstraint);
                    }
                }
                if (constraintsConfig.isUnique() != null && constraintsConfig.isUnique().booleanValue()) {
                    UniqueConstraint uniqueConstraint = new UniqueConstraint(constraintsConfig.getUniqueConstraintName());
                    if (constraintsConfig.getValidateUnique() != null && !constraintsConfig.getValidateUnique().booleanValue()) {
                        uniqueConstraint.setValidateUnique(false);
                    }
                    constraints.add(uniqueConstraint);
                }
                if (constraintsConfig.isPrimaryKey() != null && constraintsConfig.isPrimaryKey().booleanValue()) {
                    PrimaryKeyConstraint primaryKeyConstraint = new PrimaryKeyConstraint(constraintsConfig.getPrimaryKeyName());
                    if (constraintsConfig.getValidatePrimaryKey() != null && !constraintsConfig.getValidatePrimaryKey().booleanValue()) {
                        primaryKeyConstraint.setValidatePrimaryKey(false);
                    }
                    constraints.add(primaryKeyConstraint);
                }
                if (constraintsConfig.getReferences() != null || constraintsConfig.getReferencedColumnNames() != null && constraintsConfig.getReferencedTableName() != null) {
                    ForeignKeyConstraint foreignKeyConstraint = new ForeignKeyConstraint(constraintsConfig.getForeignKeyName(), constraintsConfig.getReferences(), constraintsConfig.getReferencedTableName(), constraintsConfig.getReferencedColumnNames());
                    if (constraintsConfig.getValidateForeignKey() != null && !constraintsConfig.getValidateForeignKey().booleanValue()) {
                        foreignKeyConstraint.setValidateForeignKey(false);
                    }
                    if (constraintsConfig.isDeleteCascade() != null) {
                        foreignKeyConstraint.setDeleteCascade(constraintsConfig.isDeleteCascade());
                    }
                    if (constraintsConfig.isDeferrable() != null) {
                        foreignKeyConstraint.setDeferrable(constraintsConfig.isDeferrable());
                    }
                    if (constraintsConfig.isInitiallyDeferred() != null) {
                        foreignKeyConstraint.setInitiallyDeferred(constraintsConfig.isInitiallyDeferred());
                    }
                    constraints.add(foreignKeyConstraint);
                }
            }
            if (addColumnConfig.isAutoIncrement() != null && addColumnConfig.isAutoIncrement().booleanValue()) {
                constraints.add(new AutoIncrementConstraint(addColumnConfig.getName(), addColumnConfig.getStartWith(), addColumnConfig.getIncrementBy(), addColumnConfig.getGenerationType(), addColumnConfig.getDefaultOnNull()));
            }
            AddColumnStatement addColumnStatement = new AddColumnStatement(this.getCatalogName(), this.getSchemaName(), this.getTableName(), addColumnConfig.getName(), addColumnConfig.getType(), addColumnConfig.getDefaultValueObject(), addColumnConfig.getRemarks(), constraints.toArray(new ColumnConstraint[0]));
            addColumnStatement.setDefaultValueConstraintName(addColumnConfig.getDefaultValueConstraintName());
            addColumnStatement.setComputed(addColumnConfig.getComputed());
            addColumnStatement.setAddAfterColumn(addColumnConfig.getAfterColumn());
            addColumnStatement.setAddBeforeColumn(addColumnConfig.getBeforeColumn());
            addColumnStatement.setAddAtPosition(addColumnConfig.getPosition());
            addColumnStatements.add(addColumnStatement);
            if (addColumnConfig.getValueObject() == null) continue;
            UpdateStatement updateStatement = new UpdateStatement(this.getCatalogName(), this.getSchemaName(), this.getTableName());
            updateStatement.addNewColumnValue(addColumnConfig.getName(), addColumnConfig.getValueObject());
            if (database instanceof DB2Database) {
                addColumnUpdateStatements.add(updateStatement);
                continue;
            }
            sql.add(updateStatement);
        }
        if (database instanceof DB2Database) {
            sql.add(new ReorganizeTableStatement(this.getCatalogName(), this.getSchemaName(), this.getTableName()));
            sql.addAll(addColumnUpdateStatements);
        }
        if (addColumnStatements.size() == 1) {
            sql.add(0, (SqlStatement)addColumnStatements.get(0));
        } else {
            sql.add(0, new AddColumnStatement(addColumnStatements));
        }
        sql.addAll(addNotNullConstraintStatements);
        for (ColumnConfig columnConfig : this.getColumns()) {
            String columnRemarks = StringUtil.trimToNull(columnConfig.getRemarks());
            if (columnRemarks == null) continue;
            SetColumnRemarksStatement remarksStatement = new SetColumnRemarksStatement(this.catalogName, this.schemaName, this.tableName, columnConfig.getName(), columnRemarks, columnConfig.getType());
            if (!SqlGeneratorFactory.getInstance().supports(remarksStatement, database) || database instanceof MySQLDatabase) continue;
            sql.add(remarksStatement);
        }
        return sql.toArray(SqlStatement.EMPTY_SQL_STATEMENT);
    }

    @Override
    protected Change[] createInverses() {
        ArrayList<AbstractChange> inverses = new ArrayList<AbstractChange>();
        DropColumnChange inverse = new DropColumnChange();
        inverse.setCatalogName(this.getCatalogName());
        inverse.setSchemaName(this.getSchemaName());
        inverse.setTableName(this.getTableName());
        for (ColumnConfig columnConfig : this.columns) {
            if (columnConfig.hasDefaultValue()) {
                DropDefaultValueChange dropChange = new DropDefaultValueChange();
                dropChange.setTableName(this.getTableName());
                dropChange.setColumnName(columnConfig.getName());
                dropChange.setSchemaName(this.getSchemaName());
                inverses.add(dropChange);
            }
            inverse.addColumn(columnConfig);
        }
        inverses.add(inverse);
        return inverses.toArray(EMPTY_CHANGE);
    }

    @Override
    public ChangeStatus checkStatus(Database database) {
        ChangeStatus result = new ChangeStatus();
        try {
            for (AddColumnConfig column : this.getColumns()) {
                Column snapshot = SnapshotGeneratorFactory.getInstance().createSnapshot(new Column(Table.class, this.getCatalogName(), this.getSchemaName(), this.getTableName(), column.getName()), database);
                result.assertComplete(snapshot != null, "Column " + column.getName() + " does not exist");
                if (snapshot == null) continue;
                PrimaryKey snapshotPK = ((Table)snapshot.getRelation()).getPrimaryKey();
                ConstraintsConfig constraints = column.getConstraints();
                if (constraints == null) continue;
                result.assertComplete(constraints.isPrimaryKey() == (snapshotPK != null && snapshotPK.getColumnNames().contains(column.getName())), "Column " + column.getName() + " not set as primary key");
            }
        }
        catch (Exception e) {
            return result.unknown(e);
        }
        return result;
    }

    @Override
    public String getConfirmationMessage() {
        ArrayList<String> names = new ArrayList<String>(this.columns.size());
        for (ColumnConfig columnConfig : this.columns) {
            names.add(columnConfig.getName() + "(" + columnConfig.getType() + ")");
        }
        return "Columns " + StringUtil.join(names, ",") + " added to " + this.tableName;
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    @Override
    public void modify(ChangeVisitor changeVisitor) throws ParsedNodeException {
        String remove;
        if (!changeVisitor.getName().equals("addColumn")) {
            return;
        }
        switch (remove = ((AddColumnChangeVisitor)changeVisitor).getRemove()) {
            case "afterColumn": {
                this.getColumns().forEach(c -> c.setAfterColumn(null));
                break;
            }
            case "beforeColumn": {
                this.getColumns().forEach(c -> c.setBeforeColumn(null));
                break;
            }
            case "position": {
                this.getColumns().forEach(c -> c.setPosition(null));
                break;
            }
            default: {
                throw new ParsedNodeException("Unexpected value found under removeChangeSetProperty for remove tag: " + remove);
            }
        }
    }

    private NotNullConstraint createNotNullConstraint(ConstraintsConfig constraintsConfig) {
        NotNullConstraint notNullConstraint = new NotNullConstraint();
        if (constraintsConfig.getValidateNullable() != null && !constraintsConfig.getValidateNullable().booleanValue()) {
            notNullConstraint.setValidateNullable(false);
        }
        notNullConstraint.setConstraintName(constraintsConfig.getNotNullConstraintName());
        return notNullConstraint;
    }

    private List<SqlStatement> generateAddNotNullConstraintStatements(AddColumnConfig column, ConstraintsConfig constraints, Database database) {
        AddNotNullConstraintChange addNotNullConstraintChange = this.createAddNotNullConstraintChange(column, constraints);
        ArrayList<SqlStatement> returnList = new ArrayList<SqlStatement>(Arrays.asList(addNotNullConstraintChange.generateStatements(database)));
        if (database instanceof MySQLDatabase && column.getDefaultValueObject() != null) {
            AddDefaultValueChange change = new AddDefaultValueChange();
            change.setCatalogName(this.getCatalogName());
            change.setSchemaName(this.getSchemaName());
            change.setTableName(this.getTableName());
            change.setColumnName(column.getName());
            change.setColumnDataType(column.getName());
            if (column.getDefaultValueDate() != null) {
                change.setDefaultValueDate(new ISODateFormat().format(column.getDefaultValueDate()));
            } else {
                change.setDefaultValue(column.getDefaultValue());
                Number defaultValueNumeric = column.getDefaultValueNumeric();
                if (defaultValueNumeric != null) {
                    change.setDefaultValueNumeric(String.valueOf(defaultValueNumeric));
                }
                change.setDefaultValueBoolean(column.getDefaultValueBoolean());
                change.setDefaultValueComputed(column.getDefaultValueComputed());
                change.setDefaultValueSequenceNext(column.getDefaultValueSequenceNext());
            }
            change.setDefaultValueConstraintName(column.getDefaultValueConstraintName());
            returnList.addAll(Arrays.asList(change.generateStatements(database)));
        }
        return returnList;
    }

    private AddNotNullConstraintChange createAddNotNullConstraintChange(AddColumnConfig column, ConstraintsConfig constraints) {
        AddNotNullConstraintChange addNotNullConstraintChange = new AddNotNullConstraintChange();
        addNotNullConstraintChange.setCatalogName(this.getCatalogName());
        addNotNullConstraintChange.setSchemaName(this.getSchemaName());
        addNotNullConstraintChange.setTableName(this.getTableName());
        addNotNullConstraintChange.setColumnName(column.getName());
        addNotNullConstraintChange.setColumnDataType(column.getType());
        addNotNullConstraintChange.setValidate(constraints.getValidateNullable());
        addNotNullConstraintChange.setConstraintName(constraints.getNotNullConstraintName());
        return addNotNullConstraintChange;
    }
}

