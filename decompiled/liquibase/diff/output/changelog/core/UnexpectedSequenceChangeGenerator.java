/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.output.changelog.core;

import liquibase.change.Change;
import liquibase.change.core.DropSequenceChange;
import liquibase.database.Database;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.changelog.AbstractChangeGenerator;
import liquibase.diff.output.changelog.ChangeGeneratorChain;
import liquibase.diff.output.changelog.UnexpectedObjectChangeGenerator;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Sequence;
import liquibase.structure.core.Table;

public class UnexpectedSequenceChangeGenerator
extends AbstractChangeGenerator
implements UnexpectedObjectChangeGenerator {
    @Override
    public int getPriority(Class<? extends DatabaseObject> objectType, Database database) {
        if (Sequence.class.isAssignableFrom(objectType)) {
            return 1;
        }
        return -1;
    }

    @Override
    public Class<? extends DatabaseObject>[] runAfterTypes() {
        return new Class[]{Table.class};
    }

    @Override
    public Class<? extends DatabaseObject>[] runBeforeTypes() {
        return null;
    }

    @Override
    public Change[] fixUnexpected(DatabaseObject unexpectedObject, DiffOutputControl control, Database referenceDatabase, Database comparisonDatabase, ChangeGeneratorChain chain) {
        Sequence sequence2 = (Sequence)unexpectedObject;
        DropSequenceChange change = new DropSequenceChange();
        change.setSequenceName(sequence2.getName());
        if (control.getIncludeCatalog()) {
            change.setCatalogName(sequence2.getSchema().getCatalogName());
        }
        if (control.getIncludeSchema()) {
            change.setSchemaName(sequence2.getSchema().getName());
        }
        return new Change[]{change};
    }
}

