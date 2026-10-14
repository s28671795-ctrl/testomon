/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.output.changelog.core;

import java.util.ArrayList;
import java.util.Arrays;
import liquibase.change.Change;
import liquibase.change.core.AddUniqueConstraintChange;
import liquibase.change.core.DropUniqueConstraintChange;
import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.diff.ObjectDifferences;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.changelog.AbstractChangeGenerator;
import liquibase.diff.output.changelog.ChangeGeneratorChain;
import liquibase.diff.output.changelog.ChangeGeneratorFactory;
import liquibase.diff.output.changelog.ChangedObjectChangeGenerator;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Index;
import liquibase.structure.core.Schema;
import liquibase.structure.core.UniqueConstraint;

public class ChangedUniqueConstraintChangeGenerator
extends AbstractChangeGenerator
implements ChangedObjectChangeGenerator {
    @Override
    public int getPriority(Class<? extends DatabaseObject> objectType, Database database) {
        if (UniqueConstraint.class.isAssignableFrom(objectType)) {
            return 1;
        }
        return -1;
    }

    @Override
    public Class<? extends DatabaseObject>[] runBeforeTypes() {
        return new Class[]{Index.class};
    }

    @Override
    public Class<? extends DatabaseObject>[] runAfterTypes() {
        return null;
    }

    @Override
    public Change[] fixChanged(DatabaseObject changedObject, ObjectDifferences differences, DiffOutputControl control, Database referenceDatabase, Database comparisonDatabase, ChangeGeneratorChain chain) {
        ArrayList<Change> returnList = new ArrayList<Change>();
        UniqueConstraint uniqueConstraint = (UniqueConstraint)changedObject;
        DropUniqueConstraintChange dropUniqueConstraintChange = this.createDropUniqueConstraintChange();
        dropUniqueConstraintChange.setTableName(uniqueConstraint.getRelation().getName());
        dropUniqueConstraintChange.setConstraintName(uniqueConstraint.getName());
        AddUniqueConstraintChange addUniqueConstraintChange = this.createAddUniqueConstraintChange();
        addUniqueConstraintChange.setConstraintName(uniqueConstraint.getName());
        addUniqueConstraintChange.setTableName(uniqueConstraint.getRelation().getName());
        addUniqueConstraintChange.setColumnNames(uniqueConstraint.getColumnNames());
        returnList.add(dropUniqueConstraintChange);
        if (control.getIncludeCatalog()) {
            dropUniqueConstraintChange.setCatalogName(uniqueConstraint.getSchema().getCatalogName());
            addUniqueConstraintChange.setCatalogName(uniqueConstraint.getSchema().getCatalogName());
        }
        if (control.getIncludeSchema()) {
            dropUniqueConstraintChange.setSchemaName(uniqueConstraint.getSchema().getName());
            addUniqueConstraintChange.setSchemaName(uniqueConstraint.getSchema().getName());
        }
        Index backingIndex = uniqueConstraint.getBackingIndex();
        if (comparisonDatabase instanceof OracleDatabase && backingIndex != null && backingIndex.getName() != null) {
            Change[] missingIndexChanges = ChangeGeneratorFactory.getInstance().fixMissing(backingIndex, control, referenceDatabase, comparisonDatabase);
            if (missingIndexChanges != null) {
                returnList.addAll(Arrays.asList(missingIndexChanges));
            }
            addUniqueConstraintChange.setForIndexName(backingIndex.getName());
            Schema schema = backingIndex.getSchema();
            if (schema != null) {
                if (control.getIncludeCatalog()) {
                    addUniqueConstraintChange.setForIndexCatalogName(schema.getCatalogName());
                }
                if (control.getIncludeSchema()) {
                    addUniqueConstraintChange.setForIndexSchemaName(schema.getName());
                }
            }
        }
        control.setAlreadyHandledChanged(backingIndex);
        returnList.add(addUniqueConstraintChange);
        return returnList.toArray(EMPTY_CHANGE);
    }

    protected DropUniqueConstraintChange createDropUniqueConstraintChange() {
        return new DropUniqueConstraintChange();
    }

    protected AddUniqueConstraintChange createAddUniqueConstraintChange() {
        return new AddUniqueConstraintChange();
    }
}

