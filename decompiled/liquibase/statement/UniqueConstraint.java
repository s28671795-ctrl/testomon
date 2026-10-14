/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import liquibase.statement.ColumnConstraint;

public class UniqueConstraint
implements ColumnConstraint {
    private String constraintName;
    private boolean validateUnique = true;
    private final List<String> columns = new ArrayList<String>();

    public UniqueConstraint() {
    }

    public UniqueConstraint(String constraintName) {
        this.constraintName = constraintName;
    }

    public UniqueConstraint(String constraintName, boolean validateUnique) {
        this.constraintName = constraintName;
        this.setValidateUnique(validateUnique);
    }

    public UniqueConstraint addColumns(String ... columns) {
        this.columns.addAll(Arrays.asList(columns));
        return this;
    }

    public String getConstraintName() {
        return this.constraintName;
    }

    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }

    public List<String> getColumns() {
        return this.columns;
    }

    public boolean shouldValidateUnique() {
        return this.validateUnique;
    }

    public void setValidateUnique(boolean validateUnique) {
        this.validateUnique = validateUnique;
    }
}

