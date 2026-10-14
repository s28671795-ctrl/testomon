/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import java.util.ArrayList;
import liquibase.change.AbstractChange;
import liquibase.change.Change;
import liquibase.change.ChangeStatus;
import liquibase.change.ColumnConfig;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.change.core.DropForeignKeyConstraintChange;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.AddForeignKeyConstraintStatement;
import liquibase.structure.core.Column;
import liquibase.structure.core.ForeignKey;
import liquibase.structure.core.ForeignKeyConstraintType;
import liquibase.structure.core.Table;

@DatabaseChange(name="addForeignKeyConstraint", description="Adds a foreign key constraint to an existing column", priority=1, appliesTo={"column"})
public class AddForeignKeyConstraintChange
extends AbstractChange {
    private String baseTableCatalogName;
    private String baseTableSchemaName;
    private String baseTableName;
    private String baseColumnNames;
    private String referencedTableCatalogName;
    private String referencedTableSchemaName;
    private String referencedTableName;
    private String referencedColumnNames;
    private String constraintName;
    private Boolean deferrable;
    private Boolean initiallyDeferred;
    private Boolean shouldValidate;
    private String onUpdate;
    private String onDelete;

    @Override
    protected String[] createSupportedDatabasesMetaData(String parameterName, DatabaseChangeProperty changePropertyAnnotation) {
        if ("deferrable".equals(parameterName) || "initiallyDeferred".equals(parameterName)) {
            ArrayList<String> supported = new ArrayList<String>();
            for (Database database : DatabaseFactory.getInstance().getImplementedDatabases()) {
                if (!database.supportsInitiallyDeferrableColumns()) continue;
                supported.add(database.getShortName());
            }
            return supported.toArray(new String[0]);
        }
        return super.createSupportedDatabasesMetaData(parameterName, changePropertyAnnotation);
    }

    @DatabaseChangeProperty(since="3.0", mustEqualExisting="column.relation.catalog", description="Name of the database catalog of the base table")
    public String getBaseTableCatalogName() {
        return this.baseTableCatalogName;
    }

    public void setBaseTableCatalogName(String baseTableCatalogName) {
        this.baseTableCatalogName = baseTableCatalogName;
    }

    @DatabaseChangeProperty(mustEqualExisting="column.relation.schema", description="Name of the database schema of the base table")
    public String getBaseTableSchemaName() {
        return this.baseTableSchemaName;
    }

    public void setBaseTableSchemaName(String baseTableSchemaName) {
        this.baseTableSchemaName = baseTableSchemaName;
    }

    @DatabaseChangeProperty(description="Name of the table containing the column to constrain", exampleValue="address", mustEqualExisting="column.relation")
    public String getBaseTableName() {
        return this.baseTableName;
    }

    public void setBaseTableName(String baseTableName) {
        this.baseTableName = baseTableName;
    }

    @DatabaseChangeProperty(description="Name of the column(s) to place the foreign key constraint on. Comma-separate if multiple", exampleValue="person_id", mustEqualExisting="column")
    public String getBaseColumnNames() {
        return this.baseColumnNames;
    }

    public void setBaseColumnNames(String baseColumnNames) {
        this.baseColumnNames = baseColumnNames;
    }

    @DatabaseChangeProperty(since="3.0", mustEqualExisting="column", description="Name of the database catalog of the referenced table")
    public String getReferencedTableCatalogName() {
        return this.referencedTableCatalogName;
    }

    public void setReferencedTableCatalogName(String referencedTableCatalogName) {
        this.referencedTableCatalogName = referencedTableCatalogName;
    }

    @DatabaseChangeProperty(description="Name of the database schema of the referenced table")
    public String getReferencedTableSchemaName() {
        return this.referencedTableSchemaName;
    }

    public void setReferencedTableSchemaName(String referencedTableSchemaName) {
        this.referencedTableSchemaName = referencedTableSchemaName;
    }

    @DatabaseChangeProperty(description="Name of the table the foreign key points to", exampleValue="person")
    public String getReferencedTableName() {
        return this.referencedTableName;
    }

    public void setReferencedTableName(String referencedTableName) {
        this.referencedTableName = referencedTableName;
    }

    @DatabaseChangeProperty(description="Name of the column(s) the foreign key points to. Comma-separate if multiple", exampleValue="id")
    public String getReferencedColumnNames() {
        return this.referencedColumnNames;
    }

    public void setReferencedColumnNames(String referencedColumnNames) {
        this.referencedColumnNames = referencedColumnNames;
    }

    @DatabaseChangeProperty(description="Name of the new foreign key constraint", exampleValue="fk_address_person")
    public String getConstraintName() {
        return this.constraintName;
    }

    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }

    @DatabaseChangeProperty(description="Defines whether the foreign key is deferrable")
    public Boolean getDeferrable() {
        return this.deferrable;
    }

    public void setDeferrable(Boolean deferrable) {
        this.deferrable = deferrable;
    }

    @DatabaseChangeProperty(description="Defines whether the foreign key is initially deferred")
    public Boolean getInitiallyDeferred() {
        return this.initiallyDeferred;
    }

    public void setInitiallyDeferred(Boolean initiallyDeferred) {
        this.initiallyDeferred = initiallyDeferred;
    }

    @DatabaseChangeProperty(description="Defines whether to check if the foreign key constraint refers to a valid row. This is true if the foreign key has 'ENABLE VALIDATE' set, or false if the foreign key has 'ENABLE NOVALIDATE' set.")
    public Boolean getValidate() {
        return this.shouldValidate;
    }

    public void setValidate(Boolean shouldValidate) {
        this.shouldValidate = shouldValidate;
    }

    @DatabaseChangeProperty(description="Deprecated. This is true to set onDelete to Cascade, priority given to onDelete tag if one exists")
    @Deprecated
    public Boolean getDeleteCascade() {
        return null;
    }

    public void setDeleteCascade(Boolean deleteCascade) {
        if (deleteCascade != null && deleteCascade.booleanValue() && this.onDelete == null) {
            this.setOnDelete("CASCADE");
        } else {
            this.setOnDelete((String)null);
        }
    }

    public void setOnUpdate(String rule) {
        this.onUpdate = rule;
    }

    @DatabaseChangeProperty(description="ON UPDATE functionality. Possible values: 'CASCADE', 'SET NULL', 'SET DEFAULT', 'RESTRICT', 'NO ACTION'", exampleValue="RESTRICT")
    public String getOnUpdate() {
        return this.onUpdate;
    }

    @DatabaseChangeProperty(description="ON DELETE functionality. Possible values: 'CASCADE', 'SET NULL', 'SET DEFAULT', 'RESTRICT', 'NO ACTION'", exampleValue="CASCADE")
    public String getOnDelete() {
        return this.onDelete;
    }

    public void setOnDelete(String onDelete) {
        this.onDelete = onDelete;
    }

    public void setOnDelete(ForeignKeyConstraintType rule) {
        if (rule != null) {
            if (rule == ForeignKeyConstraintType.importedKeyCascade) {
                this.setOnDelete("CASCADE");
            } else if (rule == ForeignKeyConstraintType.importedKeySetNull) {
                this.setOnDelete("SET NULL");
            } else if (rule == ForeignKeyConstraintType.importedKeySetDefault) {
                this.setOnDelete("SET DEFAULT");
            } else if (rule == ForeignKeyConstraintType.importedKeyRestrict) {
                this.setOnDelete("RESTRICT");
            } else if (rule == ForeignKeyConstraintType.importedKeyNoAction) {
                this.setOnDelete("NO ACTION");
            } else {
                throw new UnexpectedLiquibaseException("Unknown onDelete action: " + (Object)((Object)rule));
            }
        }
    }

    public void setOnUpdate(ForeignKeyConstraintType rule) {
        if (rule != null) {
            if (rule == ForeignKeyConstraintType.importedKeyCascade) {
                this.setOnUpdate("CASCADE");
            } else if (rule == ForeignKeyConstraintType.importedKeySetNull) {
                this.setOnUpdate("SET NULL");
            } else if (rule == ForeignKeyConstraintType.importedKeySetDefault) {
                this.setOnUpdate("SET DEFAULT");
            } else if (rule == ForeignKeyConstraintType.importedKeyRestrict) {
                this.setOnUpdate("RESTRICT");
            } else if (rule == ForeignKeyConstraintType.importedKeyNoAction) {
                this.setOnUpdate("NO ACTION");
            } else {
                throw new UnexpectedLiquibaseException("Unknown onUpdate action: " + this.onUpdate);
            }
        }
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        boolean deferrable = false;
        if (this.checkDefferable(database) && this.getDeferrable() != null) {
            deferrable = this.getDeferrable();
        }
        boolean initiallyDeferred = false;
        if (this.checkDefferable(database) && this.getInitiallyDeferred() != null) {
            initiallyDeferred = this.getInitiallyDeferred();
        }
        boolean shouldValidate = true;
        if (this.getValidate() != null) {
            shouldValidate = this.getValidate();
        }
        return new SqlStatement[]{new AddForeignKeyConstraintStatement(this.getConstraintName(), this.getBaseTableCatalogName(), this.getBaseTableSchemaName(), this.getBaseTableName(), ColumnConfig.arrayFromNames(this.getBaseColumnNames()), this.getReferencedTableCatalogName(), this.getReferencedTableSchemaName(), this.getReferencedTableName(), ColumnConfig.arrayFromNames(this.getReferencedColumnNames())).setDeferrable(deferrable).setInitiallyDeferred(initiallyDeferred).setOnUpdate(this.getOnUpdate()).setOnDelete(this.getOnDelete()).setShouldValidate(shouldValidate)};
    }

    private boolean checkDefferable(Database database) {
        return database.supportsInitiallyDeferrableColumns() || database.failOnDefferable();
    }

    @Override
    protected Change[] createInverses() {
        DropForeignKeyConstraintChange inverse = new DropForeignKeyConstraintChange();
        inverse.setBaseTableSchemaName(this.getBaseTableSchemaName());
        inverse.setBaseTableName(this.getBaseTableName());
        inverse.setConstraintName(this.getConstraintName());
        return new Change[]{inverse};
    }

    @Override
    public ChangeStatus checkStatus(Database database) {
        ChangeStatus result = new ChangeStatus();
        try {
            ForeignKey example = new ForeignKey(this.getConstraintName(), this.getBaseTableCatalogName(), this.getBaseTableSchemaName(), this.getBaseTableName(), new Column[0]);
            example.setPrimaryKeyTable(new Table(this.getReferencedTableCatalogName(), this.getReferencedTableSchemaName(), this.getReferencedTableName()));
            example.setForeignKeyColumns(Column.listFromNames(this.getBaseColumnNames()));
            example.setPrimaryKeyColumns(Column.listFromNames(this.getReferencedColumnNames()));
            ForeignKey snapshot = SnapshotGeneratorFactory.getInstance().createSnapshot(example, database);
            result.assertComplete(snapshot != null, "Foreign key does not exist");
            if (snapshot != null) {
                if (this.getInitiallyDeferred() != null) {
                    result.assertCorrect(this.getInitiallyDeferred().equals(snapshot.isInitiallyDeferred()), "Initially deferred incorrect");
                }
                if (this.getDeferrable() != null) {
                    result.assertCorrect(this.getDeferrable().equals(snapshot.isDeferrable()), "Initially deferred incorrect");
                }
                if (this.getValidate() != null) {
                    result.assertCorrect(this.getValidate().equals(snapshot.shouldValidate()), "validate incorrect");
                }
            }
            return result;
        }
        catch (Exception e) {
            return result.unknown(e);
        }
    }

    @Override
    public String getConfirmationMessage() {
        return "Foreign key constraint added to " + this.getBaseTableName() + " (" + this.getBaseColumnNames() + ")";
    }

    @Deprecated
    @DatabaseChangeProperty(description="Deprecated")
    public Boolean getReferencesUniqueColumn() {
        return null;
    }

    public void setReferencesUniqueColumn(Boolean referencesUniqueColumn) {
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }
}

