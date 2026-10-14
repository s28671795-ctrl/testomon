/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.output.changelog.core;

import liquibase.change.Change;
import liquibase.change.core.CreateSequenceChange;
import liquibase.database.Database;
import liquibase.database.core.SnowflakeDatabase;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.changelog.ChangeGeneratorChain;
import liquibase.diff.output.changelog.core.MissingSequenceChangeGenerator;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Sequence;

public class MissingSequenceChangeGeneratorSnowflake
extends MissingSequenceChangeGenerator {
    @Override
    public int getPriority(Class<? extends DatabaseObject> objectType, Database database) {
        int priority = super.getPriority(objectType, database);
        if (Sequence.class.isAssignableFrom(objectType) && database instanceof SnowflakeDatabase) {
            priority += 5;
        }
        return priority;
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
        return new Change[]{change};
    }
}

