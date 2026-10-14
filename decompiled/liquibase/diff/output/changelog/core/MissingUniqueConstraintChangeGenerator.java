/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.output.changelog.core;

import java.util.ArrayList;
import java.util.Arrays;
import liquibase.Scope;
import liquibase.change.Change;
import liquibase.change.core.AddUniqueConstraintChange;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.Database;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.diff.DiffResult;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.changelog.AbstractChangeGenerator;
import liquibase.diff.output.changelog.ChangeGeneratorChain;
import liquibase.diff.output.changelog.ChangeGeneratorFactory;
import liquibase.diff.output.changelog.MissingObjectChangeGenerator;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Column;
import liquibase.structure.core.Index;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Table;
import liquibase.structure.core.UniqueConstraint;

public class MissingUniqueConstraintChangeGenerator
extends AbstractChangeGenerator
implements MissingObjectChangeGenerator {
    @Override
    public int getPriority(Class<? extends DatabaseObject> objectType, Database database) {
        if (UniqueConstraint.class.isAssignableFrom(objectType)) {
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
        return new Class[]{Index.class};
    }

    @Override
    public Change[] fixMissing(DatabaseObject missingObject, DiffOutputControl control, Database referenceDatabase, Database comparisonDatabase, ChangeGeneratorChain chain) {
        Change[] changes;
        Index backingIndex;
        ArrayList<Change> returnList = new ArrayList<Change>();
        UniqueConstraint uc = (UniqueConstraint)missingObject;
        if (uc.getRelation() == null) {
            return null;
        }
        AddUniqueConstraintChange change = this.createAddUniqueConstraintChange();
        change.setTableName(uc.getRelation().getName());
        if (uc.getBackingIndex() != null && control.getIncludeTablespace()) {
            change.setTablespace(uc.getBackingIndex().getTablespace());
        }
        if (control.getIncludeCatalog()) {
            change.setCatalogName(uc.getRelation().getSchema().getCatalogName());
        }
        if (control.getIncludeSchema()) {
            change.setSchemaName(uc.getRelation().getSchema().getName());
        }
        change.setConstraintName(uc.getName());
        change.setColumnNames(uc.getColumnNames());
        change.setDeferrable(uc.isDeferrable() ? Boolean.TRUE : null);
        change.setValidate(!uc.shouldValidate() ? Boolean.FALSE : null);
        change.setInitiallyDeferred(uc.isInitiallyDeferred() ? Boolean.TRUE : null);
        change.setDisabled(uc.isDisabled() ? Boolean.TRUE : null);
        if (referenceDatabase instanceof MSSQLDatabase) {
            change.setClustered(uc.isClustered() ? Boolean.TRUE : null);
        }
        if (comparisonDatabase instanceof OracleDatabase && (backingIndex = uc.getBackingIndex()) != null && backingIndex.getName() != null && (referenceDatabase.equals(comparisonDatabase) || !this.alreadyExists(backingIndex, comparisonDatabase, control)) && (changes = ChangeGeneratorFactory.getInstance().fixMissing(backingIndex, control, referenceDatabase, comparisonDatabase)) != null) {
            returnList.addAll(Arrays.asList(changes));
            change.setForIndexName(backingIndex.getName());
            Schema schema = backingIndex.getSchema();
            if (schema != null) {
                if (control.getIncludeCatalog()) {
                    change.setForIndexCatalogName(schema.getCatalogName());
                }
                if (control.getIncludeSchema()) {
                    change.setForIndexSchemaName(schema.getName());
                }
            }
        }
        backingIndex = uc.getBackingIndex();
        control.setAlreadyHandledMissing(backingIndex);
        returnList.add(change);
        return returnList.toArray(EMPTY_CHANGE);
    }

    private boolean alreadyExists(Index backingIndex, Database comparisonDatabase, DiffOutputControl control) {
        boolean found = false;
        try {
            Index foundIndex;
            String catalogName = null;
            String schemaName = null;
            if (control.getIncludeCatalog()) {
                catalogName = backingIndex.getTable().getSchema().getCatalogName();
            }
            if (control.getIncludeSchema()) {
                schemaName = backingIndex.getTable().getSchema().getName();
            }
            Index backingIndexCopy = new Index(backingIndex.getName(), catalogName, schemaName, backingIndex.getTable().getName(), new Column[0]);
            for (Column column : backingIndex.getColumns()) {
                backingIndexCopy.addColumn(column);
            }
            DiffResult diffResult = null;
            if (comparisonDatabase instanceof AbstractJdbcDatabase) {
                diffResult = (DiffResult)((AbstractJdbcDatabase)comparisonDatabase).get("diffResult");
            }
            found = diffResult != null ? (foundIndex = diffResult.getComparisonSnapshot().get(backingIndexCopy)) != null : SnapshotGeneratorFactory.getInstance().has(backingIndexCopy, comparisonDatabase);
        }
        catch (Exception e) {
            Scope.getCurrentScope().getLog(this.getClass()).warning("Error checking for backing index " + backingIndex.toString() + ": " + e.getMessage(), e);
        }
        return found;
    }

    protected AddUniqueConstraintChange createAddUniqueConstraintChange() {
        return new AddUniqueConstraintChange();
    }
}

