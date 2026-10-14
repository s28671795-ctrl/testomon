/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.output.changelog.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import liquibase.change.Change;
import liquibase.change.core.AddPrimaryKeyChange;
import liquibase.change.core.DropPrimaryKeyChange;
import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.diff.Difference;
import liquibase.diff.ObjectDifferences;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.changelog.AbstractChangeGenerator;
import liquibase.diff.output.changelog.ChangeGeneratorChain;
import liquibase.diff.output.changelog.ChangeGeneratorFactory;
import liquibase.diff.output.changelog.ChangedObjectChangeGenerator;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Index;
import liquibase.structure.core.PrimaryKey;
import liquibase.structure.core.Schema;
import liquibase.structure.core.UniqueConstraint;
import liquibase.util.StringUtil;

public class ChangedPrimaryKeyChangeGenerator
extends AbstractChangeGenerator
implements ChangedObjectChangeGenerator {
    @Override
    public int getPriority(Class<? extends DatabaseObject> objectType, Database database) {
        if (PrimaryKey.class.isAssignableFrom(objectType)) {
            return 1;
        }
        return -1;
    }

    @Override
    public Class<? extends DatabaseObject>[] runBeforeTypes() {
        return new Class[]{Index.class, UniqueConstraint.class};
    }

    @Override
    public Class<? extends DatabaseObject>[] runAfterTypes() {
        return null;
    }

    @Override
    public Change[] fixChanged(DatabaseObject changedObject, ObjectDifferences differences, DiffOutputControl control, Database referenceDatabase, Database comparisonDatabase, ChangeGeneratorChain chain) {
        List comparedColumns;
        List referenceColumns;
        Difference columnDifferences;
        Index backingIndex;
        Difference clusteredDiff = differences.getDifference("clustered");
        if (clusteredDiff != null && (clusteredDiff.getReferenceValue() == null || clusteredDiff.getComparedValue() == null)) {
            differences.removeDifference("clustered");
        }
        if (!differences.hasDifferences()) {
            return EMPTY_CHANGE;
        }
        PrimaryKey pk = (PrimaryKey)changedObject;
        ArrayList<Change> returnList = new ArrayList<Change>();
        DropPrimaryKeyChange dropPkChange = new DropPrimaryKeyChange();
        dropPkChange.setTableName(pk.getTable().getName());
        returnList.add(dropPkChange);
        AddPrimaryKeyChange addPkChange = new AddPrimaryKeyChange();
        addPkChange.setTableName(pk.getTable().getName());
        addPkChange.setColumnNames(pk.getColumnNames());
        addPkChange.setConstraintName(pk.getName());
        if (comparisonDatabase instanceof OracleDatabase && (backingIndex = pk.getBackingIndex()) != null && backingIndex.getName() != null) {
            Change[] indexChanges = ChangeGeneratorFactory.getInstance().fixMissing(backingIndex, control, referenceDatabase, comparisonDatabase);
            if (indexChanges != null) {
                returnList.addAll(Arrays.asList(indexChanges));
            }
            addPkChange.setForIndexName(backingIndex.getName());
            Schema schema = backingIndex.getSchema();
            if (schema != null) {
                if (control.getIncludeCatalog()) {
                    addPkChange.setForIndexCatalogName(schema.getCatalogName());
                }
                if (control.getIncludeSchema()) {
                    addPkChange.setForIndexSchemaName(schema.getName());
                }
            }
        }
        returnList.add(addPkChange);
        if (control.getIncludeCatalog()) {
            dropPkChange.setCatalogName(pk.getSchema().getCatalogName());
            addPkChange.setCatalogName(pk.getSchema().getCatalogName());
        }
        if (control.getIncludeSchema()) {
            dropPkChange.setSchemaName(pk.getSchema().getName());
            addPkChange.setSchemaName(pk.getSchema().getName());
        }
        if ((columnDifferences = differences.getDifference("columns")) == null) {
            referenceColumns = pk.getColumns();
            comparedColumns = pk.getColumns();
        } else {
            referenceColumns = (List)columnDifferences.getReferenceValue();
            comparedColumns = (List)columnDifferences.getComparedValue();
        }
        StringUtil.ToStringFormatter formatter = new StringUtil.ToStringFormatter();
        control.setAlreadyHandledChanged(new Index().setRelation(pk.getTable()).setColumns(referenceColumns));
        if (!StringUtil.join((Collection)referenceColumns, ",", (StringUtil.StringUtilFormatter)formatter).equalsIgnoreCase(StringUtil.join((Collection)comparedColumns, ",", (StringUtil.StringUtilFormatter)formatter))) {
            control.setAlreadyHandledChanged(new Index().setRelation(pk.getTable()).setColumns(comparedColumns));
        }
        control.setAlreadyHandledChanged(new UniqueConstraint().setRelation(pk.getTable()).setColumns(referenceColumns));
        if (!StringUtil.join((Collection)referenceColumns, ",", (StringUtil.StringUtilFormatter)formatter).equalsIgnoreCase(StringUtil.join((Collection)comparedColumns, ",", (StringUtil.StringUtilFormatter)formatter))) {
            control.setAlreadyHandledChanged(new UniqueConstraint().setRelation(pk.getTable()).setColumns(comparedColumns));
        }
        return returnList.toArray(EMPTY_CHANGE);
    }
}

