/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.output.changelog.core;

import java.util.ArrayList;
import liquibase.change.Change;
import liquibase.change.core.AlterSequenceChange;
import liquibase.database.Database;
import liquibase.database.core.SnowflakeDatabase;
import liquibase.diff.ObjectDifferences;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.changelog.ChangeGeneratorChain;
import liquibase.diff.output.changelog.core.ChangedSequenceChangeGenerator;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Sequence;

public class ChangedSequenceChangeGeneratorSnowflake
extends ChangedSequenceChangeGenerator {
    @Override
    public int getPriority(Class<? extends DatabaseObject> objectType, Database database) {
        int priority = super.getPriority(objectType, database);
        if (Sequence.class.isAssignableFrom(objectType) && database instanceof SnowflakeDatabase) {
            priority += 5;
        }
        return priority;
    }

    @Override
    public Change[] fixChanged(DatabaseObject changedObject, ObjectDifferences differences, DiffOutputControl control, Database referenceDatabase, Database comparisonDatabase, ChangeGeneratorChain chain) {
        Sequence sequence2 = (Sequence)changedObject;
        ArrayList<AlterSequenceChange> changes = new ArrayList<AlterSequenceChange>();
        AlterSequenceChange accumulatedChange = this.createAlterSequenceChange(sequence2, control);
        if (differences.isDifferent("incrementBy")) {
            AlterSequenceChange change = this.createAlterSequenceChange(sequence2, control);
            change.setIncrementBy(sequence2.getIncrementBy());
            accumulatedChange.setIncrementBy(sequence2.getIncrementBy());
            changes.add(change);
        }
        if (changes.isEmpty()) {
            return null;
        }
        return changes.toArray(EMPTY_CHANGE);
    }
}

