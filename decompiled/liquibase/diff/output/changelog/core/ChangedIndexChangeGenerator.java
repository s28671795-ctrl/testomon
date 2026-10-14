/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.output.changelog.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import liquibase.change.AddColumnConfig;
import liquibase.change.Change;
import liquibase.change.core.CreateIndexChange;
import liquibase.change.core.DropIndexChange;
import liquibase.database.Database;
import liquibase.diff.Difference;
import liquibase.diff.ObjectDifferences;
import liquibase.diff.compare.DatabaseObjectComparatorFactory;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.changelog.AbstractChangeGenerator;
import liquibase.diff.output.changelog.ChangeGeneratorChain;
import liquibase.diff.output.changelog.ChangeGeneratorFactory;
import liquibase.diff.output.changelog.ChangedObjectChangeGenerator;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Column;
import liquibase.structure.core.Index;
import liquibase.structure.core.Table;
import liquibase.structure.core.UniqueConstraint;
import liquibase.util.StringUtil;

public class ChangedIndexChangeGenerator
extends AbstractChangeGenerator
implements ChangedObjectChangeGenerator {
    @Override
    public int getPriority(Class<? extends DatabaseObject> objectType, Database database) {
        if (Index.class.isAssignableFrom(objectType)) {
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
        Difference columnsDifference;
        Difference clusteredDiff = differences.getDifference("clustered");
        if (clusteredDiff != null && (clusteredDiff.getReferenceValue() == null || clusteredDiff.getComparedValue() == null)) {
            differences.removeDifference("clustered");
        }
        for (String field2 : this.getIgnoredFields()) {
            differences.removeDifference(field2);
        }
        if (!differences.hasDifferences()) {
            return EMPTY_CHANGE;
        }
        Index index = (Index)changedObject;
        if (index.getRelation() != null && index.getRelation() instanceof Table) {
            if (((Table)index.getRelation()).getPrimaryKey() != null && DatabaseObjectComparatorFactory.getInstance().isSameObject(((Table)index.getRelation()).getPrimaryKey().getBackingIndex(), changedObject, differences.getSchemaComparisons(), comparisonDatabase)) {
                return ChangeGeneratorFactory.getInstance().fixChanged(((Table)index.getRelation()).getPrimaryKey(), differences, control, referenceDatabase, comparisonDatabase);
            }
            List<UniqueConstraint> uniqueConstraints = index.getRelation().getUniqueConstraints();
            if (uniqueConstraints != null) {
                for (UniqueConstraint constraint : uniqueConstraints) {
                    if (constraint.getBackingIndex() == null || !DatabaseObjectComparatorFactory.getInstance().isSameObject(constraint.getBackingIndex(), changedObject, differences.getSchemaComparisons(), comparisonDatabase)) continue;
                    return ChangeGeneratorFactory.getInstance().fixChanged(constraint, differences, control, referenceDatabase, comparisonDatabase);
                }
            }
        }
        DropIndexChange dropIndexChange = this.createDropIndexChange();
        dropIndexChange.setTableName(index.getRelation().getName());
        dropIndexChange.setIndexName(index.getName());
        CreateIndexChange addIndexChange = this.createCreateIndexChange();
        addIndexChange.setTableName(index.getRelation().getName());
        ArrayList<AddColumnConfig> columns = new ArrayList<AddColumnConfig>();
        for (Column col : index.getColumns()) {
            columns.add(new AddColumnConfig(col));
        }
        addIndexChange.setColumns((List<AddColumnConfig>)columns);
        addIndexChange.setIndexName(index.getName());
        addIndexChange.setUnique(index.isUnique());
        if (control.getIncludeCatalog()) {
            dropIndexChange.setCatalogName(index.getSchema().getCatalogName());
            addIndexChange.setCatalogName(index.getSchema().getCatalogName());
        }
        if (control.getIncludeSchema()) {
            dropIndexChange.setSchemaName(index.getSchema().getName());
            addIndexChange.setSchemaName(index.getSchema().getName());
        }
        if ((columnsDifference = differences.getDifference("columns")) != null) {
            List referenceColumns = (List)columnsDifference.getReferenceValue();
            List comparedColumns = (List)columnsDifference.getComparedValue();
            StringUtil.StringUtilFormatter<Column> formatter = obj -> obj.toString(false);
            control.setAlreadyHandledChanged(new Index().setRelation(index.getRelation()).setColumns(referenceColumns));
            if (!StringUtil.join((Collection)referenceColumns, ",", formatter).equalsIgnoreCase(StringUtil.join((Collection)comparedColumns, ",", formatter))) {
                control.setAlreadyHandledChanged(new Index().setRelation(index.getRelation()).setColumns(comparedColumns));
            }
            if (index.isUnique() != null && index.isUnique().booleanValue()) {
                control.setAlreadyHandledChanged(new UniqueConstraint().setRelation(index.getRelation()).setColumns(referenceColumns));
                if (!StringUtil.join((Collection)referenceColumns, ",", formatter).equalsIgnoreCase(StringUtil.join((Collection)comparedColumns, ",", formatter))) {
                    control.setAlreadyHandledChanged(new UniqueConstraint().setRelation(index.getRelation()).setColumns(comparedColumns));
                }
            }
        }
        return new Change[]{dropIndexChange, addIndexChange};
    }

    protected String[] getIgnoredFields() {
        return new String[]{"padIndex", "fillFactor", "ignoreDuplicateKeys", "recomputeStatistics", "incrementalStatistics", "allowRowLocks", "allowPageLocks", "dataCompression", "includedColumns"};
    }

    protected DropIndexChange createDropIndexChange() {
        return new DropIndexChange();
    }

    protected CreateIndexChange createCreateIndexChange() {
        return new CreateIndexChange();
    }
}

