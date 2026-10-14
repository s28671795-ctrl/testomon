/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.output.changelog.core;

import liquibase.change.AddColumnConfig;
import liquibase.change.Change;
import liquibase.change.core.CreateIndexChange;
import liquibase.database.Database;
import liquibase.database.core.MSSQLDatabase;
import liquibase.diff.compare.DatabaseObjectComparatorFactory;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.changelog.AbstractChangeGenerator;
import liquibase.diff.output.changelog.ChangeGeneratorChain;
import liquibase.diff.output.changelog.MissingObjectChangeGenerator;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Column;
import liquibase.structure.core.Index;
import liquibase.structure.core.PrimaryKey;
import liquibase.structure.core.Table;

public class MissingIndexChangeGenerator
extends AbstractChangeGenerator
implements MissingObjectChangeGenerator {
    @Override
    public int getPriority(Class<? extends DatabaseObject> objectType, Database database) {
        if (Index.class.isAssignableFrom(objectType)) {
            return 1;
        }
        return -1;
    }

    @Override
    public Class<? extends DatabaseObject>[] runAfterTypes() {
        return new Class[]{Table.class, Column.class};
    }

    @Override
    public Class<? extends DatabaseObject>[] runBeforeTypes() {
        return null;
    }

    @Override
    public Change[] fixMissing(DatabaseObject missingObject, DiffOutputControl control, Database referenceDatabase, Database comparisonDatabase, ChangeGeneratorChain chain) {
        PrimaryKey primaryKey;
        Index index = (Index)missingObject;
        if (comparisonDatabase instanceof MSSQLDatabase && index.getRelation() instanceof Table && (primaryKey = ((Table)index.getRelation()).getPrimaryKey()) != null && DatabaseObjectComparatorFactory.getInstance().isSameObject(missingObject, primaryKey.getBackingIndex(), control.getSchemaComparisons(), referenceDatabase)) {
            return EMPTY_CHANGE;
        }
        CreateIndexChange change = this.createCreateIndexChange();
        change.setTableName(index.getRelation().getName());
        if (control.getIncludeTablespace()) {
            change.setTablespace(index.getTablespace());
        }
        if (control.getIncludeCatalog()) {
            change.setCatalogName(index.getRelation().getSchema().getCatalogName());
        }
        if (control.getIncludeSchema()) {
            change.setSchemaName(index.getRelation().getSchema().getName());
        }
        change.setIndexName(index.getName());
        change.setUnique(index.isUnique() != null && index.isUnique() != false ? Boolean.TRUE : null);
        change.setClustered(index.getClustered() != null && index.getClustered() != false ? Boolean.TRUE : null);
        if (referenceDatabase.createsIndexesForForeignKeys()) {
            change.setAssociatedWith(index.getAssociatedWithAsString());
        }
        for (Column column : index.getColumns()) {
            change.addColumn(new AddColumnConfig(column));
        }
        return new Change[]{change};
    }

    protected CreateIndexChange createCreateIndexChange() {
        return new CreateIndexChange();
    }
}

