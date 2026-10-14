/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import liquibase.statement.AbstractSqlStatement;
import liquibase.statement.AutoIncrementConstraint;
import liquibase.statement.ColumnConstraint;
import liquibase.statement.NotNullConstraint;
import liquibase.statement.PrimaryKeyConstraint;
import liquibase.statement.UniqueConstraint;

public class AddColumnStatement
extends AbstractSqlStatement {
    private String catalogName;
    private String schemaName;
    private String tableName;
    private String columnName;
    private String columnType;
    private Object defaultValue;
    private String defaultValueConstraintName;
    private String remarks;
    private String addAfterColumn;
    private String addBeforeColumn;
    private Integer addAtPosition;
    private Boolean computed;
    private final Set<ColumnConstraint> constraints = new HashSet<ColumnConstraint>();
    private final List<AddColumnStatement> columns = new ArrayList<AddColumnStatement>();

    public AddColumnStatement(String catalogName, String schemaName, String tableName, String columnName, String columnType, Object defaultValue, ColumnConstraint ... constraints) {
        this.catalogName = catalogName;
        this.schemaName = schemaName;
        this.tableName = tableName;
        this.columnName = columnName;
        this.columnType = columnType;
        this.defaultValue = defaultValue;
        if (constraints != null) {
            this.constraints.addAll(Arrays.asList(constraints));
        }
    }

    public AddColumnStatement(String catalogName, String schemaName, String tableName, String columnName, String columnType, Object defaultValue, String remarks, ColumnConstraint ... constraints) {
        this(catalogName, schemaName, tableName, columnName, columnType, defaultValue, constraints);
        this.remarks = remarks;
    }

    public AddColumnStatement(List<AddColumnStatement> columns) {
        this.columns.addAll(columns);
    }

    public AddColumnStatement(AddColumnStatement ... columns) {
        this(Arrays.asList(columns));
    }

    public boolean isMultiple() {
        return !this.columns.isEmpty();
    }

    public boolean isAutoIncrement() {
        for (ColumnConstraint constraint : this.getConstraints()) {
            if (!(constraint instanceof AutoIncrementConstraint)) continue;
            return true;
        }
        return false;
    }

    public AutoIncrementConstraint getAutoIncrementConstraint() {
        AutoIncrementConstraint autoIncrementConstraint = null;
        for (ColumnConstraint constraint : this.getConstraints()) {
            if (!(constraint instanceof AutoIncrementConstraint)) continue;
            autoIncrementConstraint = (AutoIncrementConstraint)constraint;
            break;
        }
        return autoIncrementConstraint;
    }

    public boolean isPrimaryKey() {
        for (ColumnConstraint constraint : this.getConstraints()) {
            if (!(constraint instanceof PrimaryKeyConstraint)) continue;
            return true;
        }
        return false;
    }

    public boolean isNullable() {
        if (this.isPrimaryKey()) {
            return false;
        }
        for (ColumnConstraint constraint : this.getConstraints()) {
            if (!(constraint instanceof NotNullConstraint)) continue;
            return false;
        }
        return true;
    }

    public boolean shouldValidateNullable() {
        if (this.isPrimaryKey()) {
            return false;
        }
        for (ColumnConstraint constraint : this.getConstraints()) {
            if (!(constraint instanceof NotNullConstraint) || ((NotNullConstraint)constraint).shouldValidateNullable()) continue;
            return false;
        }
        return true;
    }

    public boolean shouldValidateUnique() {
        for (ColumnConstraint constraint : this.getConstraints()) {
            if (!(constraint instanceof UniqueConstraint) || ((UniqueConstraint)constraint).shouldValidateUnique()) continue;
            return false;
        }
        return true;
    }

    public boolean shouldValidatePrimaryKey() {
        for (ColumnConstraint constraint : this.getConstraints()) {
            if (!(constraint instanceof PrimaryKeyConstraint) || ((PrimaryKeyConstraint)constraint).shouldValidatePrimaryKey()) continue;
            return false;
        }
        return true;
    }

    public boolean isUnique() {
        for (ColumnConstraint constraint : this.getConstraints()) {
            if (!(constraint instanceof UniqueConstraint)) continue;
            return true;
        }
        return false;
    }

    public String getUniqueStatementName() {
        for (ColumnConstraint constraint : this.getConstraints()) {
            if (!(constraint instanceof UniqueConstraint)) continue;
            return ((UniqueConstraint)constraint).getConstraintName();
        }
        return null;
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

    public String getColumnName() {
        return this.columnName;
    }

    public String getColumnType() {
        return this.columnType;
    }

    public Object getDefaultValue() {
        return this.defaultValue;
    }

    public String getDefaultValueConstraintName() {
        return this.defaultValueConstraintName;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public String getAddAfterColumn() {
        return this.addAfterColumn;
    }

    public String getAddBeforeColumn() {
        return this.addBeforeColumn;
    }

    public Integer getAddAtPosition() {
        return this.addAtPosition;
    }

    public Boolean getComputed() {
        return this.computed;
    }

    public Set<ColumnConstraint> getConstraints() {
        return this.constraints;
    }

    public List<AddColumnStatement> getColumns() {
        return this.columns;
    }

    public void setDefaultValueConstraintName(String defaultValueConstraintName) {
        this.defaultValueConstraintName = defaultValueConstraintName;
    }

    public void setAddAfterColumn(String addAfterColumn) {
        this.addAfterColumn = addAfterColumn;
    }

    public void setAddBeforeColumn(String addBeforeColumn) {
        this.addBeforeColumn = addBeforeColumn;
    }

    public void setAddAtPosition(Integer addAtPosition) {
        this.addAtPosition = addAtPosition;
    }

    public void setComputed(Boolean computed) {
        this.computed = computed;
    }
}

