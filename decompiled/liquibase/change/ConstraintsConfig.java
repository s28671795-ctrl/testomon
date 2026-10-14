/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change;

import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.ParsedNodeException;
import liquibase.resource.ResourceAccessor;
import liquibase.serializer.AbstractLiquibaseSerializable;
import liquibase.util.StringUtil;

public class ConstraintsConfig
extends AbstractLiquibaseSerializable {
    private Boolean nullable;
    private String notNullConstraintName;
    private Boolean primaryKey;
    private String primaryKeyName;
    private String primaryKeyTablespace;
    private String references;
    private String referencedTableCatalogName;
    private String referencedTableSchemaName;
    private String referencedTableName;
    private String referencedColumnNames;
    private Boolean unique;
    private String uniqueConstraintName;
    private String checkConstraint;
    private Boolean deleteCascade;
    private String foreignKeyName;
    private Boolean initiallyDeferred;
    private Boolean deferrable;
    private Boolean validateNullable;
    private Boolean validateUnique;
    private Boolean validatePrimaryKey;
    private Boolean validateForeignKey;

    public Boolean isNullable() {
        return this.nullable;
    }

    public ConstraintsConfig setNullable(Boolean nullable) {
        this.nullable = nullable;
        return this;
    }

    public ConstraintsConfig setNullable(String nullable) {
        this.nullable = this.parseBoolean(nullable);
        return this;
    }

    public String getNotNullConstraintName() {
        return this.notNullConstraintName;
    }

    public ConstraintsConfig setNotNullConstraintName(String notNullConstraintName) {
        this.notNullConstraintName = notNullConstraintName;
        return this;
    }

    public Boolean isPrimaryKey() {
        return this.primaryKey;
    }

    public ConstraintsConfig setPrimaryKey(Boolean primaryKey) {
        this.primaryKey = primaryKey;
        return this;
    }

    public ConstraintsConfig setPrimaryKey(String primaryKey) {
        this.primaryKey = this.parseBoolean(primaryKey);
        return this;
    }

    public String getPrimaryKeyName() {
        return this.primaryKeyName;
    }

    public ConstraintsConfig setPrimaryKeyName(String primaryKeyName) {
        this.primaryKeyName = primaryKeyName;
        return this;
    }

    public String getReferences() {
        return this.references;
    }

    public ConstraintsConfig setReferences(String references) {
        this.references = references;
        return this;
    }

    public Boolean isUnique() {
        return this.unique;
    }

    public ConstraintsConfig setUnique(Boolean unique) {
        this.unique = unique;
        return this;
    }

    public ConstraintsConfig setUnique(String unique) {
        this.unique = this.parseBoolean(unique);
        return this;
    }

    public String getUniqueConstraintName() {
        return this.uniqueConstraintName;
    }

    public ConstraintsConfig setUniqueConstraintName(String uniqueConstraintName) {
        this.uniqueConstraintName = uniqueConstraintName;
        return this;
    }

    public String getCheckConstraint() {
        return this.checkConstraint;
    }

    public ConstraintsConfig setCheckConstraint(String checkConstraint) {
        this.checkConstraint = checkConstraint;
        return this;
    }

    public Boolean isDeleteCascade() {
        return this.deleteCascade;
    }

    public ConstraintsConfig setDeleteCascade(Boolean deleteCascade) {
        this.deleteCascade = deleteCascade;
        return this;
    }

    public ConstraintsConfig setDeleteCascade(String deleteCascade) {
        this.deleteCascade = this.parseBoolean(deleteCascade);
        return this;
    }

    public String getForeignKeyName() {
        return this.foreignKeyName;
    }

    public ConstraintsConfig setForeignKeyName(String foreignKeyName) {
        this.foreignKeyName = foreignKeyName;
        return this;
    }

    public Boolean isInitiallyDeferred() {
        return this.initiallyDeferred;
    }

    public ConstraintsConfig setInitiallyDeferred(Boolean initiallyDeferred) {
        this.initiallyDeferred = initiallyDeferred;
        return this;
    }

    public ConstraintsConfig setInitiallyDeferred(String initiallyDeferred) {
        this.initiallyDeferred = this.parseBoolean(initiallyDeferred);
        return this;
    }

    public Boolean isDeferrable() {
        return this.deferrable;
    }

    public ConstraintsConfig setDeferrable(Boolean deferrable) {
        this.deferrable = deferrable;
        return this;
    }

    public ConstraintsConfig setValidateNullable(String validateNullable) {
        this.validateNullable = this.parseBoolean(validateNullable);
        return this;
    }

    public Boolean getValidateNullable() {
        return this.validateNullable;
    }

    public ConstraintsConfig setValidateNullable(Boolean validateNullable) {
        this.validateNullable = validateNullable;
        return this;
    }

    public ConstraintsConfig setValidateUnique(String validateUnique) {
        this.validateUnique = this.parseBoolean(validateUnique);
        return this;
    }

    public Boolean getValidateUnique() {
        return this.validateUnique;
    }

    public ConstraintsConfig setValidateUnique(Boolean validateUnique) {
        this.validateUnique = validateUnique;
        return this;
    }

    public ConstraintsConfig setValidatePrimaryKey(String validatePrimaryKey) {
        this.validatePrimaryKey = this.parseBoolean(validatePrimaryKey);
        return this;
    }

    public Boolean getValidatePrimaryKey() {
        return this.validatePrimaryKey;
    }

    public ConstraintsConfig setValidatePrimaryKey(Boolean validatePrimaryKey) {
        this.validatePrimaryKey = validatePrimaryKey;
        return this;
    }

    public ConstraintsConfig setValidateForeignKey(String validateForeignKey) {
        this.validateForeignKey = this.parseBoolean(validateForeignKey);
        return this;
    }

    public Boolean getValidateForeignKey() {
        return this.validateForeignKey;
    }

    public ConstraintsConfig setValidateForeignKey(Boolean validateForeignKey) {
        this.validateForeignKey = validateForeignKey;
        return this;
    }

    public ConstraintsConfig setDeferrable(String deferrable) {
        this.deferrable = this.parseBoolean(deferrable);
        return this;
    }

    public String getPrimaryKeyTablespace() {
        return this.primaryKeyTablespace;
    }

    public ConstraintsConfig setPrimaryKeyTablespace(String primaryKeyTablespace) {
        this.primaryKeyTablespace = primaryKeyTablespace;
        return this;
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

    public String getReferencedTableName() {
        return this.referencedTableName;
    }

    public void setReferencedTableName(String referencedTableName) {
        this.referencedTableName = referencedTableName;
    }

    public String getReferencedColumnNames() {
        return this.referencedColumnNames;
    }

    public void setReferencedColumnNames(String referencedColumnNames) {
        this.referencedColumnNames = referencedColumnNames;
    }

    private Boolean parseBoolean(String value) {
        if ((value = StringUtil.trimToNull(value)) == null || "null".equalsIgnoreCase(value)) {
            return null;
        }
        if ("true".equalsIgnoreCase(value) || "1".equals(value)) {
            return true;
        }
        if ("false".equalsIgnoreCase(value) || "0".equals(value)) {
            return false;
        }
        throw new UnexpectedLiquibaseException("Unparsable boolean value: " + value);
    }

    @Override
    public String getSerializedObjectName() {
        return "constraints";
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    @Override
    public void load(ParsedNode parsedNode, ResourceAccessor resourceAccessor) throws ParsedNodeException {
        throw new RuntimeException("TODO");
    }
}

