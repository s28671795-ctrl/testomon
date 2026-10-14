/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement;

import liquibase.statement.ColumnConstraint;

public class NotNullConstraint
implements ColumnConstraint {
    private String columnName;
    private String constraintName;
    private boolean validateNullable = true;

    public NotNullConstraint() {
    }

    public NotNullConstraint(String columnName) {
        this.setColumnName(columnName);
    }

    public NotNullConstraint(String columnName, boolean validateNullable) {
        this.setColumnName(columnName);
        this.setValidateNullable(validateNullable);
    }

    public String getColumnName() {
        return this.columnName;
    }

    public NotNullConstraint setColumnName(String columnName) {
        this.columnName = columnName;
        return this;
    }

    public String getConstraintName() {
        return this.constraintName;
    }

    public NotNullConstraint setConstraintName(String name) {
        this.constraintName = name;
        return this;
    }

    public boolean shouldValidateNullable() {
        return this.validateNullable;
    }

    public NotNullConstraint setValidateNullable(boolean validateNullable) {
        this.validateNullable = validateNullable;
        return this;
    }
}

