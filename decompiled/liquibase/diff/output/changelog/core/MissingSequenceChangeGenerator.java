/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.output.changelog.core;

import liquibase.change.Change;
import liquibase.change.core.CreateSequenceChange;
import liquibase.database.Database;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.changelog.AbstractChangeGenerator;
import liquibase.diff.output.changelog.ChangeGeneratorChain;
import liquibase.diff.output.changelog.MissingObjectChangeGenerator;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Sequence;

public class MissingSequenceChangeGenerator
extends AbstractChangeGenerator
implements MissingObjectChangeGenerator {
    @Override
    public int getPriority(Class<? extends DatabaseObject> objectType, Database database) {
        if (Sequence.class.isAssignableFrom(objectType)) {
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
        return null;
    }

    @Override
    public Change[] fixMissing(DatabaseObject missingObject, DiffOutputControl control, Database referenceDatabase, Database comparisonDatabase, ChangeGeneratorChain chain) {
        Sequence sequence2 = (Sequence)missingObject;
        CreateSequenceChange change = new CreateSequenceChange();
        change.setSequenceName(sequence2.getName());
        if (control.getIncludeCatalog()) {
            change.setCatalogName(sequence2.getSchema().getCatalogName());
        }
        if (control.getIncludeSchema()) {
            change.setSchemaName(sequence2.getSchema().getName());
        }
        change.setStartValue(sequence2.getStartValue());
        change.setIncrementBy(sequence2.getIncrementBy());
        change.setMinValue(sequence2.getMinValue());
        change.setMaxValue(sequence2.getMaxValue());
        change.setCacheSize(sequence2.getCacheSize());
        change.setCycle(sequence2.getWillCycle());
        change.setOrdered(sequence2.getOrdered());
        change.setDataType(sequence2.getDataType());
        return new Change[]{change};
    }
}

