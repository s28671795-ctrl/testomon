/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import liquibase.datatype.LiquibaseDataType;
import liquibase.statement.AbstractSqlStatement;
import liquibase.statement.AutoIncrementConstraint;
import liquibase.statement.ColumnConstraint;
import liquibase.statement.CompoundStatement;
import liquibase.statement.ForeignKeyConstraint;
import liquibase.statement.NotNullConstraint;
import liquibase.statement.PrimaryKeyConstraint;
import liquibase.statement.UniqueConstraint;

public class CreateTableStatement
extends AbstractSqlStatement
implements CompoundStatement {
    private String tableType;
    private final String catalogName;
    private String schemaName;
    private final String tableName;
    private String tablespace;
    private String remarks;
    private final List<String> columns = new ArrayList<String>();
    private final Set<AutoIncrementConstraint> autoIncrementConstraints = new HashSet<AutoIncrementConstraint>();
    private final Map<String, LiquibaseDataType> columnTypes = new HashMap<String, LiquibaseDataType>();
    private final Map<String, Object> defaultValues = new HashMap<String, Object>();
    private final Map<String, String> defaultValueConstraintNames = new HashMap<String, String>();
    private final Map<String, String> columnRemarks = new HashMap<String, String>();
    private PrimaryKeyConstraint primaryKeyConstraint;
    private final Map<String, NotNullConstraint> notNullConstraints = new HashMap<String, NotNullConstraint>();
    private final Set<ForeignKeyConstraint> foreignKeyConstraints = new HashSet<ForeignKeyConstraint>();
    private final HashMap<String, NotNullConstraint> notNullColumns = new HashMap();
    private final Set<UniqueConstraint> uniqueConstraints = new LinkedHashSet<UniqueConstraint>();
    private final Set<String> computedColumns = new HashSet<String>();
    private boolean ifNotExists;

    public CreateTableStatement(String catalogName, String schemaName, String tableName) {
        this(catalogName, schemaName, tableName, null, null);
    }

    public CreateTableStatement(String catalogName, String schemaName, String tableName, String remarks) {
        this(catalogName, schemaName, tableName, remarks, null);
    }

    public CreateTableStatement(String catalogName, String schemaName, String tableName, String remarks, String tableType) {
        this.catalogName = catalogName;
        this.schemaName = schemaName;
        this.tableName = tableName;
        this.remarks = remarks;
        this.tableType = tableType;
    }

    public CreateTableStatement(String catalogName, String schemaName, String tableName, boolean ifNotExists) {
        this(catalogName, schemaName, tableName);
        this.ifNotExists = ifNotExists;
    }

    public CreateTableStatement(String catalogName, String schemaName, String tableName, String remarks, String tableType, boolean ifNotExists) {
        this(catalogName, schemaName, tableName, remarks, tableType);
        this.ifNotExists = ifNotExists;
    }

    public String getCatalogName() {
        return this.catalogName;
    }

    public String getSchemaName() {
        return this.schemaName;
    }

    public String getTableName() {
        return this.tableName;
    }

    public List<String> getColumns() {
        return this.columns;
    }

    public String getTablespace() {
        return this.tablespace;
    }

    public CreateTableStatement setTablespace(String tablespace) {
        this.tablespace = tablespace;
        return this;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public PrimaryKeyConstraint getPrimaryKeyConstraint() {
        return this.primaryKeyConstraint;
    }

    public Set<ForeignKeyConstraint> getForeignKeyConstraints() {
        return this.foreignKeyConstraints;
    }

    public Set<UniqueConstraint> getUniqueConstraints() {
        return this.uniqueConstraints;
    }

    public Map<String, NotNullConstraint> getNotNullColumns() {
        return this.notNullConstraints;
    }

    public CreateTableStatement addPrimaryKeyColumn(String columnName, LiquibaseDataType columnType, Object defaultValue, String keyName, String tablespace, ColumnConstraint ... constraints) {
        PrimaryKeyConstraint pkConstraint = new PrimaryKeyConstraint(keyName);
        pkConstraint.addColumns(columnName);
        pkConstraint.setTablespace(tablespace);
        ArrayList<ColumnConstraint> allConstraints = new ArrayList<ColumnConstraint>(Arrays.asList(constraints));
        allConstraints.add(new NotNullConstraint(columnName));
        allConstraints.add(pkConstraint);
        this.addColumn(columnName, columnType, defaultValue, allConstraints.toArray(new ColumnConstraint[0]));
        return this;
    }

    public CreateTableStatement addPrimaryKeyColumn(String columnName, LiquibaseDataType columnType, Object defaultValue, Boolean validate, String keyName, String tablespace, ColumnConstraint ... constraints) {
        return this.addPrimaryKeyColumn(columnName, columnType, defaultValue, validate, false, false, keyName, tablespace, constraints);
    }

    public CreateTableStatement addPrimaryKeyColumn(String columnName, LiquibaseDataType columnType, Object defaultValue, Boolean validate, boolean deferrable, boolean initiallyDeferred, String keyName, String tablespace, ColumnConstraint ... constraints) {
        return this.addPrimaryKeyColumn(columnName, columnType, defaultValue, validate, deferrable, initiallyDeferred, keyName, tablespace, (String)null, constraints);
    }

    public CreateTableStatement addPrimaryKeyColumn(String columnName, LiquibaseDataType columnType, Object defaultValue, Boolean validate, boolean deferrable, boolean initiallyDeferred, String keyName, String tablespace, String remarks, ColumnConstraint ... constraints) {
        PrimaryKeyConstraint pkConstraint = new PrimaryKeyConstraint(keyName);
        if (validate != null) {
            pkConstraint.setValidatePrimaryKey(validate);
        }
        pkConstraint.addColumns(columnName);
        pkConstraint.setTablespace(tablespace);
        pkConstraint.setDeferrable(deferrable);
        pkConstraint.setInitiallyDeferred(initiallyDeferred);
        ArrayList<ColumnConstraint> allConstraints = new ArrayList<ColumnConstraint>(Arrays.asList(constraints));
        allConstraints.add(new NotNullConstraint(columnName));
        allConstraints.add(pkConstraint);
        this.addColumn(columnName, columnType, defaultValue, remarks, allConstraints.toArray(new ColumnConstraint[0]));
        return this;
    }

    public CreateTableStatement addColumn(String columnName, LiquibaseDataType columnType) {
        return this.addColumn(columnName, columnType, null, new ColumnConstraint[0]);
    }

    public CreateTableStatement addColumn(String columnName, LiquibaseDataType columnType, Object defaultValue) {
        if (defaultValue instanceof ColumnConstraint) {
            return this.addColumn(columnName, columnType, null, new ColumnConstraint[]{(ColumnConstraint)defaultValue});
        }
        return this.addColumn(columnName, columnType, defaultValue, new ColumnConstraint[0]);
    }

    public CreateTableStatement addColumn(String columnName, LiquibaseDataType columnType, ColumnConstraint[] constraints) {
        return this.addColumn(columnName, columnType, null, constraints);
    }

    public CreateTableStatement addColumn(String columnName, LiquibaseDataType columnType, Object defaultValue, ColumnConstraint[] constraints) {
        return this.addColumn(columnName, columnType, defaultValue, null, constraints);
    }

    public CreateTableStatement addColumn(String columnName, LiquibaseDataType columnType, Object defaultValue, String remarks, ColumnConstraint ... constraints) {
        return this.addColumn(columnName, columnType, null, defaultValue, remarks, constraints);
    }

    public CreateTableStatement addColumn(String columnName, LiquibaseDataType columnType, String defaultValueConstraintName, Object defaultValue, String remarks, ColumnConstraint ... constraints) {
        this.getColumns().add(columnName);
        this.columnTypes.put(columnName, columnType);
        if (defaultValue != null) {
            this.defaultValues.put(columnName, defaultValue);
        }
        if (defaultValueConstraintName != null) {
            this.defaultValueConstraintNames.put(columnName, defaultValueConstraintName);
        }
        if (remarks != null) {
            this.columnRemarks.put(columnName, remarks);
        }
        if (constraints != null) {
            for (ColumnConstraint constraint : constraints) {
                if (constraint == null) continue;
                if (constraint instanceof PrimaryKeyConstraint) {
                    if (this.getPrimaryKeyConstraint() == null) {
                        this.primaryKeyConstraint = (PrimaryKeyConstraint)constraint;
                        continue;
                    }
                    for (String column : ((PrimaryKeyConstraint)constraint).getColumns()) {
                        this.getPrimaryKeyConstraint().addColumns(column);
                    }
                    continue;
                }
                if (constraint instanceof NotNullConstraint) {
                    ((NotNullConstraint)constraint).setColumnName(columnName);
                    this.getNotNullColumns().put(columnName, (NotNullConstraint)constraint);
                    continue;
                }
                if (constraint instanceof ForeignKeyConstraint) {
                    ((ForeignKeyConstraint)constraint).setColumn(columnName);
                    this.getForeignKeyConstraints().add((ForeignKeyConstraint)constraint);
                    continue;
                }
                if (constraint instanceof UniqueConstraint) {
                    ((UniqueConstraint)constraint).addColumns(columnName);
                    this.getUniqueConstraints().add((UniqueConstraint)constraint);
                    continue;
                }
                if (constraint instanceof AutoIncrementConstraint) {
                    this.autoIncrementConstraints.add((AutoIncrementConstraint)constraint);
                    continue;
                }
                throw new RuntimeException("Unknown constraint type: " + constraint.getClass().getName());
            }
        }
        return this;
    }

    public Object getDefaultValue(String column) {
        return this.defaultValues.get(column);
    }

    public String getDefaultValueConstraintName(String column) {
        return this.defaultValueConstraintNames.get(column);
    }

    public String getColumnRemarks(String column) {
        return this.columnRemarks.get(column);
    }

    public CreateTableStatement addColumnConstraint(NotNullConstraint notNullConstraint) {
        this.getNotNullColumns().put(notNullConstraint.getColumnName(), notNullConstraint);
        return this;
    }

    public CreateTableStatement addColumnConstraint(ForeignKeyConstraint fkConstraint) {
        this.getForeignKeyConstraints().add(fkConstraint);
        return this;
    }

    public CreateTableStatement addColumnConstraint(UniqueConstraint uniqueConstraint) {
        this.getUniqueConstraints().add(uniqueConstraint);
        return this;
    }

    public CreateTableStatement addColumnConstraint(AutoIncrementConstraint autoIncrementConstraint) {
        this.getAutoIncrementConstraints().add(autoIncrementConstraint);
        return this;
    }

    public Set<AutoIncrementConstraint> getAutoIncrementConstraints() {
        return this.autoIncrementConstraints;
    }

    public Map<String, LiquibaseDataType> getColumnTypes() {
        return this.columnTypes;
    }

    public Map<String, Object> getDefaultValues() {
        return this.defaultValues;
    }

    public Map<String, String> getDefaultValueConstraintNames() {
        return this.defaultValueConstraintNames;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public void setComputed(String columnName) {
        this.computedColumns.add(columnName);
    }

    public boolean isComputed(String columnName) {
        return this.computedColumns.contains(columnName);
    }

    public String getTableType() {
        return this.tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public boolean isIfNotExists() {
        return this.ifNotExists;
    }

    public void setIfNotExists(boolean ifNotExists) {
        this.ifNotExists = ifNotExists;
    }
}

