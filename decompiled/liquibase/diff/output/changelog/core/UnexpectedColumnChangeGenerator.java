/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.output.changelog.core;

import liquibase.change.Change;
import liquibase.change.core.DropColumnChange;
import liquibase.database.Database;
import liquibase.database.core.Db2zDatabase;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.changelog.AbstractChangeGenerator;
import liquibase.diff.output.changelog.ChangeGeneratorChain;
import liquibase.diff.output.changelog.UnexpectedObjectChangeGenerator;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Column;
import liquibase.structure.core.ForeignKey;
import liquibase.structure.core.PrimaryKey;
import liquibase.structure.core.Table;
import liquibase.structure.core.View;
import liquibase.util.BooleanUtil;

public class UnexpectedColumnChangeGenerator
extends AbstractChangeGenerator
implements UnexpectedObjectChangeGenerator {
    @Override
    public int getPriority(Class<? extends DatabaseObject> objectType, Database database) {
        if (Column.class.isAssignableFrom(objectType)) {
            return 1;
        }
        return -1;
    }

    @Override
    public Class<? extends DatabaseObject>[] runAfterTypes() {
        return new Class[]{PrimaryKey.class, ForeignKey.class, Table.class};
    }

    @Override
    public Class<? extends DatabaseObject>[] runBeforeTypes() {
        return null;
    }

    @Override
    public Change[] fixUnexpected(DatabaseObject unexpectedObject, DiffOutputControl control, Database referenceDatabase, Database comparisonDatabase, ChangeGeneratorChain chain) {
        Column column = (Column)unexpectedObject;
        if (BooleanUtil.isTrue(column.getComputed()) || BooleanUtil.isTrue(column.getDescending())) {
            return null;
        }
        if (column.getRelation() instanceof View) {
            return null;
        }
        if (column.getRelation().getSnapshotId() == null) {
            return null;
        }
        if (comparisonDatabase instanceof Db2zDatabase) {
            return null;
        }
        DropColumnChange change = new DropColumnChange();
        change.setTableName(column.getRelation().getName());
        if (control.getIncludeCatalog()) {
            change.setCatalogName(column.getRelation().getSchema().getCatalogName());
        }
        if (control.getIncludeSchema()) {
            change.setSchemaName(column.getRelation().getSchema().getName());
        }
        change.setColumnName(column.getName());
        return new Change[]{change};
    }
}

