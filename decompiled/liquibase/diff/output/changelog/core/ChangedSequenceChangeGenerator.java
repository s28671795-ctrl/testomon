/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.output.changelog.core;

import java.util.ArrayList;
import liquibase.change.Change;
import liquibase.change.core.AlterSequenceChange;
import liquibase.database.Database;
import liquibase.database.core.PostgresDatabase;
import liquibase.diff.ObjectDifferences;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.changelog.AbstractChangeGenerator;
import liquibase.diff.output.changelog.ChangeGeneratorChain;
import liquibase.diff.output.changelog.ChangedObjectChangeGenerator;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Sequence;

public class ChangedSequenceChangeGenerator
extends AbstractChangeGenerator
implements ChangedObjectChangeGenerator {
    @Override
    public int getPriority(Class<? extends DatabaseObject> objectType, Database database) {
        if (Sequence.class.isAssignableFrom(objectType)) {
            return 1;
        }
        return -1;
    }

    @Override
    public Class<? extends DatabaseObject>[] runBeforeTypes() {
        return null;
    }

    @Override
    public Class<? extends DatabaseObject>[] runAfterTypes() {
        return null;
    }

    @Override
    public Change[] fixChanged(DatabaseObject changedObject, ObjectDifferences differences, DiffOutputControl control, Database referenceDatabase, Database comparisonDatabase, ChangeGeneratorChain chain) {
        AlterSequenceChange change;
        Sequence sequence2 = (Sequence)changedObject;
        ArrayList<AlterSequenceChange> changes = new ArrayList<AlterSequenceChange>();
        AlterSequenceChange accumulatedChange = this.createAlterSequenceChange(sequence2, control);
        if (differences.isDifferent("incrementBy")) {
            change = this.createAlterSequenceChange(sequence2, control);
            change.setIncrementBy(sequence2.getIncrementBy());
            accumulatedChange.setIncrementBy(sequence2.getIncrementBy());
            changes.add(change);
        }
        if (differences.isDifferent("maxValue")) {
            change = this.createAlterSequenceChange(sequence2, control);
            change.setMaxValue(sequence2.getMaxValue());
            accumulatedChange.setMaxValue(sequence2.getMaxValue());
            changes.add(change);
        }
        if (differences.isDifferent("minValue")) {
            change = this.createAlterSequenceChange(sequence2, control);
            change.setMinValue(sequence2.getMinValue());
            accumulatedChange.setMinValue(sequence2.getMinValue());
            changes.add(change);
            changes.add(change);
        }
        if (differences.isDifferent("ordered")) {
            change = this.createAlterSequenceChange(sequence2, control);
            change.setOrdered(sequence2.getOrdered());
            accumulatedChange.setOrdered(sequence2.getOrdered());
            changes.add(change);
        }
        if (differences.isDifferent("cacheSize")) {
            change = this.createAlterSequenceChange(sequence2, control);
            change.setCacheSize(sequence2.getCacheSize());
            accumulatedChange.setCacheSize(sequence2.getCacheSize());
            changes.add(change);
        }
        if (differences.isDifferent("willCycle")) {
            change = this.createAlterSequenceChange(sequence2, control);
            change.setCycle(sequence2.getWillCycle());
            accumulatedChange.setCycle(sequence2.getWillCycle());
            changes.add(change);
        }
        if (differences.isDifferent("dataType")) {
            change = this.createAlterSequenceChange(sequence2, control);
            change.setDataType(sequence2.getDataType());
            accumulatedChange.setDataType(sequence2.getDataType());
            changes.add(change);
        }
        if (changes.isEmpty()) {
            return null;
        }
        if (comparisonDatabase instanceof PostgresDatabase) {
            return new Change[]{accumulatedChange};
        }
        return changes.toArray(EMPTY_CHANGE);
    }

    protected AlterSequenceChange createAlterSequenceChange(Sequence sequence2, DiffOutputControl control) {
        AlterSequenceChange change = new AlterSequenceChange();
        if (control.getIncludeCatalog()) {
            change.setCatalogName(sequence2.getSchema().getCatalogName());
        }
        if (control.getIncludeSchema()) {
            change.setSchemaName(sequence2.getSchema().getName());
        }
        change.setSequenceName(sequence2.getName());
        return change;
    }
}

