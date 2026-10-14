/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement;

import liquibase.statement.ColumnConstraint;

public class ForeignKeyConstraint
implements ColumnConstraint {
    private final String foreignKeyName;
    private String column;
    private final String references;
    private String referencedTableCatalogName;
    private String referencedTableSchemaName;
    private String referencedTableName;
    private String referencedColumnNames;
    private boolean deleteCascade;
    private boolean initiallyDeferred;
    private boolean deferrable;
    private boolean validateForeignKey = true;

    public ForeignKeyConstraint(String foreignKeyName, String references) {
        this.foreignKeyName = foreignKeyName;
        this.references = references;
    }

    public ForeignKeyConstraint(String foreignKeyName, String references, boolean validateForeignKey) {
        this.foreignKeyName = foreignKeyName;
        this.references = references;
        this.setValidateForeignKey(validateForeignKey);
    }

    public ForeignKeyConstraint(String foreignKeyName, String references, String referencedTableName, String referencedColumnNames) {
        this.foreignKeyName = foreignKeyName;
        this.references = references;
        this.referencedTableName = referencedTableName;
        this.referencedColumnNames = referencedColumnNames;
    }

    public ForeignKeyConstraint(String foreignKeyName, String references, String referencedTableName, String referencedColumnNames, boolean validateForeignKey) {
        this.foreignKeyName = foreignKeyName;
        this.references = references;
        this.referencedTableName = referencedTableName;
        this.referencedColumnNames = referencedColumnNames;
        this.setValidateForeignKey(validateForeignKey);
    }

    public String getForeignKeyName() {
        return this.foreignKeyName;
    }

    public String getColumn() {
        return this.column;
    }

    public ForeignKeyConstraint setColumn(String column) {
        this.column = column;
        return this;
    }

    public String getReferences() {
        return this.references;
    }

    public boolean isDeleteCascade() {
        return this.deleteCascade;
    }

    public String getReferencedTableName() {
        return this.referencedTableName;
    }

    public String getReferencedTableCatalogName() {
        return this.referencedTableCatalogName;
    }

    public void setReferencedTableCatalogName(String referencedTableCatalogName) {
        this.referencedTableCatalogName = referencedTableCatalogName;
    }

    public String getReferencedTableSchemaName() {
        return this.referencedTableSchemaName;
    }

    public void setReferencedTableSchemaName(String referencedTableSchemaName) {
        this.referencedTableSchemaName = referencedTableSchemaName;
    }

    public String getReferencedColumnNames() {
        return this.referencedColumnNames;
    }

    public ForeignKeyConstraint setDeleteCascade(boolean deleteCascade) {
        this.deleteCascade = deleteCascade;
        return this;
    }

    public boolean isInitiallyDeferred() {
        return this.initiallyDeferred;
    }

    public ForeignKeyConstraint setInitiallyDeferred(boolean initiallyDeferred) {
        this.initiallyDeferred = initiallyDeferred;
        return this;
    }

    public boolean isDeferrable() {
        return this.deferrable;
    }

    public ForeignKeyConstraint setDeferrable(boolean deferrable) {
        this.deferrable = deferrable;
        return this;
    }

    public boolean shouldValidateForeignKey() {
        return this.validateForeignKey;
    }

    public void setValidateForeignKey(boolean validateForeignKey) {
        this.validateForeignKey = validateForeignKey;
    }
}

