/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.output.changelog.core;

import liquibase.change.Change;
import liquibase.change.core.AddForeignKeyConstraintChange;
import liquibase.database.Database;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.changelog.AbstractChangeGenerator;
import liquibase.diff.output.changelog.ChangeGeneratorChain;
import liquibase.diff.output.changelog.MissingObjectChangeGenerator;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Catalog;
import liquibase.structure.core.Column;
import liquibase.structure.core.ForeignKey;
import liquibase.structure.core.Index;
import liquibase.structure.core.PrimaryKey;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Table;
import liquibase.structure.core.UniqueConstraint;
import liquibase.util.StringUtil;

public class MissingForeignKeyChangeGenerator
extends AbstractChangeGenerator
implements MissingObjectChangeGenerator {
    @Override
    public int getPriority(Class<? extends DatabaseObject> objectType, Database database) {
        if (ForeignKey.class.isAssignableFrom(objectType)) {
            return 1;
        }
        return -1;
    }

    @Override
    public Class<? extends DatabaseObject>[] runAfterTypes() {
        return new Class[]{Table.class, Column.class, PrimaryKey.class, UniqueConstraint.class, Index.class};
    }

    @Override
    public Class<? extends DatabaseObject>[] runBeforeTypes() {
        return null;
    }

    @Override
    public Change[] fixMissing(DatabaseObject missingObject, DiffOutputControl control, Database referenceDatabase, Database comparisonDatabase, ChangeGeneratorChain chain) {
        ForeignKey fk = (ForeignKey)missingObject;
        AddForeignKeyConstraintChange change = new AddForeignKeyConstraintChange();
        change.setConstraintName(fk.getName());
        String defaultSchemaName = StringUtil.trimToEmpty(referenceDatabase.getDefaultSchemaName());
        String defaultCatalogName = StringUtil.trimToEmpty(referenceDatabase.getDefaultCatalogName());
        String compDefaultSchemaName = StringUtil.trimToEmpty(comparisonDatabase.getDefaultSchemaName());
        String compDefaultCatalogName = StringUtil.trimToEmpty(comparisonDatabase.getDefaultCatalogName());
        boolean includedCatalog = false;
        change.setReferencedTableName(fk.getPrimaryKeyTable().getName());
        String missingPrimaryKeyCatalogName = StringUtil.trimToEmpty(fk.getPrimaryKeyTable().getSchema().getCatalogName());
        if (referenceDatabase.supports(Catalog.class)) {
            if (control.getIncludeCatalog() || control.considerCatalogsAsSchemas()) {
                change.setReferencedTableCatalogName(fk.getPrimaryKeyTable().getSchema().getCatalogName());
                includedCatalog = true;
            } else if (!defaultCatalogName.equalsIgnoreCase(missingPrimaryKeyCatalogName) && !compDefaultCatalogName.equalsIgnoreCase(missingPrimaryKeyCatalogName)) {
                change.setReferencedTableCatalogName(fk.getPrimaryKeyTable().getSchema().getCatalogName());
                includedCatalog = true;
            }
        }
        String missingPrimaryKeySchemaName = StringUtil.trimToEmpty(fk.getPrimaryKeyTable().getSchema().getName());
        if (referenceDatabase.supports(Schema.class)) {
            if (includedCatalog || control.getIncludeSchema()) {
                change.setReferencedTableSchemaName(fk.getPrimaryKeyTable().getSchema().getName());
            } else if (!defaultSchemaName.equalsIgnoreCase(missingPrimaryKeySchemaName) && !compDefaultSchemaName.equalsIgnoreCase(missingPrimaryKeySchemaName)) {
                change.setReferencedTableSchemaName(fk.getPrimaryKeyTable().getSchema().getName());
            }
        }
        change.setReferencedColumnNames(StringUtil.join(fk.getPrimaryKeyColumns(), ",", Column::getName));
        change.setBaseTableName(fk.getForeignKeyTable().getName());
        if (control.getIncludeCatalog()) {
            change.setBaseTableCatalogName(fk.getForeignKeyTable().getSchema().getCatalogName());
        }
        if (control.getIncludeSchema()) {
            change.setBaseTableSchemaName(fk.getForeignKeyTable().getSchema().getName());
        }
        change.setBaseColumnNames(StringUtil.join(fk.getForeignKeyColumns(), ",", Column::getName));
        change.setDeferrable(fk.isDeferrable());
        change.setInitiallyDeferred(fk.isInitiallyDeferred());
        change.setValidate(fk.shouldValidate());
        change.setOnUpdate(fk.getUpdateRule());
        change.setOnDelete(fk.getDeleteRule());
        Index backingIndex = fk.getBackingIndex();
        if (referenceDatabase.createsIndexesForForeignKeys()) {
            control.setAlreadyHandledMissing(backingIndex);
        }
        return new Change[]{change};
    }
}

