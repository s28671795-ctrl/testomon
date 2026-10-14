/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.output.changelog.core;

import liquibase.change.Change;
import liquibase.change.core.DropForeignKeyConstraintChange;
import liquibase.database.Database;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.changelog.AbstractChangeGenerator;
import liquibase.diff.output.changelog.ChangeGeneratorChain;
import liquibase.diff.output.changelog.UnexpectedObjectChangeGenerator;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.ForeignKey;
import liquibase.structure.core.Index;
import liquibase.structure.core.PrimaryKey;
import liquibase.structure.core.Table;
import liquibase.structure.core.UniqueConstraint;

public class UnexpectedForeignKeyChangeGenerator
extends AbstractChangeGenerator
implements UnexpectedObjectChangeGenerator {
    @Override
    public int getPriority(Class<? extends DatabaseObject> objectType, Database database) {
        if (ForeignKey.class.isAssignableFrom(objectType)) {
            return 1;
        }
        return -1;
    }

    @Override
    public Class<? extends DatabaseObject>[] runAfterTypes() {
        return null;
    }

    @Override
    public Class<? extends DatabaseObject>[] runBeforeTypes() {
        return new Class[]{Table.class, Index.class, UniqueConstraint.class, PrimaryKey.class};
    }

    @Override
    public Change[] fixUnexpected(DatabaseObject unexpectedObject, DiffOutputControl control, Database referenceDatabase, Database comparisonDatabase, ChangeGeneratorChain chain) {
        ForeignKey fk = (ForeignKey)unexpectedObject;
        DropForeignKeyConstraintChange change = new DropForeignKeyConstraintChange();
        change.setConstraintName(fk.getName());
        change.setBaseTableName(fk.getForeignKeyTable().getName());
        if (control.getIncludeCatalog()) {
            change.setBaseTableCatalogName(fk.getForeignKeyTable().getSchema().getCatalogName());
        }
        if (control.getIncludeSchema()) {
            change.setBaseTableSchemaName(fk.getForeignKeyTable().getSchema().getName());
        }
        Index backingIndex = fk.getBackingIndex();
        control.setAlreadyHandledUnexpected(backingIndex);
        return new Change[]{change};
    }
}

