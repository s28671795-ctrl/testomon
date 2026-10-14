/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import liquibase.statement.ColumnConstraint;

public class PrimaryKeyConstraint
implements ColumnConstraint {
    private String constraintName;
    private boolean initiallyDeferred;
    private boolean deferrable;
    private boolean validatePrimaryKey = true;
    private String tablespace;
    private final List<String> columns = new ArrayList<String>();

    public PrimaryKeyConstraint() {
    }

    public PrimaryKeyConstraint(String constraintName) {
        this.constraintName = constraintName;
    }

    public PrimaryKeyConstraint(String constraintName, boolean validatePrimaryKey) {
        this.constraintName = constraintName;
        this.setValidatePrimaryKey(validatePrimaryKey);
    }

    public String getConstraintName() {
        return this.constraintName;
    }

    public String getTablespace() {
        return this.tablespace;
    }

    public PrimaryKeyConstraint setTablespace(String tablespace) {
        this.tablespace = tablespace;
        return this;
    }

    public List<String> getColumns() {
        return Collections.unmodifiableList(this.columns);
    }

    public PrimaryKeyConstraint addColumns(String ... columns) {
        this.columns.addAll(Arrays.asList(columns));
        return this;
    }

    public boolean shouldValidatePrimaryKey() {
        return this.validatePrimaryKey;
    }

    public void setValidatePrimaryKey(boolean validatePrimaryKey) {
        this.validatePrimaryKey = validatePrimaryKey;
    }

    public boolean isInitiallyDeferred() {
        return this.initiallyDeferred;
    }

    public PrimaryKeyConstraint setInitiallyDeferred(boolean initiallyDeferred) {
        this.initiallyDeferred = initiallyDeferred;
        return this;
    }

    public boolean isDeferrable() {
        return this.deferrable;
    }

    public PrimaryKeyConstraint setDeferrable(boolean deferrable) {
        this.deferrable = deferrable;
        return this;
    }
}

