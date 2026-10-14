/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import liquibase.database.Database;
import liquibase.diff.ObjectDifferences;
import liquibase.diff.StringDiff;
import liquibase.diff.compare.CompareControl;
import liquibase.diff.compare.DatabaseObjectComparatorFactory;
import liquibase.exception.DatabaseException;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Catalog;
import liquibase.structure.core.Column;
import liquibase.structure.core.Schema;
import liquibase.util.BooleanUtil;

public class DiffResult {
    private final DatabaseSnapshot referenceSnapshot;
    private final DatabaseSnapshot comparisonSnapshot;
    private final CompareControl compareControl;
    private StringDiff productNameDiff;
    private StringDiff productVersionDiff;
    private final Set<DatabaseObject> missingObjects = new HashSet<DatabaseObject>();
    private final Set<DatabaseObject> unexpectedObjects = new HashSet<DatabaseObject>();
    private final Map<DatabaseObject, ObjectDifferences> changedObjects = new HashMap<DatabaseObject, ObjectDifferences>();

    public DiffResult(DatabaseSnapshot referenceDatabaseSnapshot, DatabaseSnapshot comparisonDatabaseSnapshot, CompareControl compareControl) {
        this.referenceSnapshot = referenceDatabaseSnapshot;
        this.comparisonSnapshot = comparisonDatabaseSnapshot;
        this.compareControl = compareControl;
    }

    public DatabaseSnapshot getReferenceSnapshot() {
        return this.referenceSnapshot;
    }

    public DatabaseSnapshot getComparisonSnapshot() {
        return this.comparisonSnapshot;
    }

    public StringDiff getProductNameDiff() {
        return this.productNameDiff;
    }

    public void setProductNameDiff(StringDiff productNameDiff) {
        this.productNameDiff = productNameDiff;
    }

    public StringDiff getProductVersionDiff() {
        return this.productVersionDiff;
    }

    public void setProductVersionDiff(StringDiff productVersionDiff) {
        this.productVersionDiff = productVersionDiff;
    }

    public CompareControl getCompareControl() {
        return this.compareControl;
    }

    public Set<? extends DatabaseObject> getMissingObjects() {
        return this.missingObjects;
    }

    public <T extends DatabaseObject> Set<T> getMissingObjects(Class<T> type) {
        HashSet<DatabaseObject> returnSet = new HashSet<DatabaseObject>();
        for (DatabaseObject obj : this.missingObjects) {
            if (!type.isAssignableFrom(obj.getClass())) continue;
            returnSet.add(obj);
        }
        return returnSet;
    }

    public <T extends DatabaseObject> SortedSet<T> getMissingObjects(Class<T> type, Comparator<DatabaseObject> comparator) {
        TreeSet<DatabaseObject> set = new TreeSet<DatabaseObject>(comparator);
        set.addAll(this.getMissingObjects(type));
        return set;
    }

    public <T extends DatabaseObject> T getMissingObject(T example, CompareControl.SchemaComparison[] schemaComparisons) {
        Database accordingTo = this.getComparisonSnapshot().getDatabase();
        DatabaseObjectComparatorFactory comparator = DatabaseObjectComparatorFactory.getInstance();
        for (DatabaseObject obj : this.getMissingObjects(example.getClass())) {
            if (!comparator.isSameObject(obj, example, schemaComparisons, accordingTo)) continue;
            return (T)obj;
        }
        return null;
    }

    public void addMissingObject(DatabaseObject obj) {
        if (obj instanceof Column && (BooleanUtil.isTrue(((Column)obj).getComputed()) || BooleanUtil.isTrue(((Column)obj).getDescending()))) {
            return;
        }
        this.missingObjects.add(obj);
    }

    public Set<? extends DatabaseObject> getUnexpectedObjects() {
        return this.unexpectedObjects;
    }

    public <T extends DatabaseObject> Set<T> getUnexpectedObjects(Class<T> type) {
        HashSet<DatabaseObject> returnSet = new HashSet<DatabaseObject>();
        for (DatabaseObject obj : this.unexpectedObjects) {
            if (!type.isAssignableFrom(obj.getClass())) continue;
            returnSet.add(obj);
        }
        return returnSet;
    }

    public <T extends DatabaseObject> SortedSet<T> getUnexpectedObjects(Class<T> type, Comparator<DatabaseObject> comparator) {
        TreeSet<DatabaseObject> set = new TreeSet<DatabaseObject>(comparator);
        set.addAll(this.getUnexpectedObjects(type));
        return set;
    }

    public <T extends DatabaseObject> T getUnexpectedObject(T example, CompareControl.SchemaComparison[] schemaComparisons) {
        Database accordingTo = this.getComparisonSnapshot().getDatabase();
        DatabaseObjectComparatorFactory comparator = DatabaseObjectComparatorFactory.getInstance();
        for (DatabaseObject obj : this.getUnexpectedObjects(example.getClass())) {
            if (!comparator.isSameObject(obj, example, schemaComparisons, accordingTo)) continue;
            return (T)obj;
        }
        return null;
    }

    public void addUnexpectedObject(DatabaseObject obj) {
        this.unexpectedObjects.add(obj);
    }

    public Map<DatabaseObject, ObjectDifferences> getChangedObjects() {
        return this.changedObjects;
    }

    public <T extends DatabaseObject> Map<T, ObjectDifferences> getChangedObjects(Class<T> type) {
        HashMap<DatabaseObject, ObjectDifferences> returnSet = new HashMap<DatabaseObject, ObjectDifferences>();
        for (Map.Entry<DatabaseObject, ObjectDifferences> obj : this.changedObjects.entrySet()) {
            if (!type.isAssignableFrom(obj.getKey().getClass())) continue;
            returnSet.put(obj.getKey(), obj.getValue());
        }
        return returnSet;
    }

    public <T extends DatabaseObject> SortedMap<T, ObjectDifferences> getChangedObjects(Class<T> type, Comparator<DatabaseObject> comparator) {
        TreeMap<DatabaseObject, ObjectDifferences> map = new TreeMap<DatabaseObject, ObjectDifferences>(comparator);
        map.putAll(this.getChangedObjects(type));
        return map;
    }

    public ObjectDifferences getChangedObject(DatabaseObject example, CompareControl.SchemaComparison[] schemaComparisons) {
        Database accordingTo = this.getComparisonSnapshot().getDatabase();
        DatabaseObjectComparatorFactory comparator = DatabaseObjectComparatorFactory.getInstance();
        for (Map.Entry<?, ObjectDifferences> entry : this.getChangedObjects(example.getClass()).entrySet()) {
            if (!comparator.isSameObject((DatabaseObject)entry.getKey(), example, schemaComparisons, accordingTo)) continue;
            return entry.getValue();
        }
        return null;
    }

    public void addChangedObject(DatabaseObject obj, ObjectDifferences differences) {
        if ((obj instanceof Catalog || obj instanceof Schema) && differences.getSchemaComparisons() != null && differences.getDifferences().size() == 1 && differences.getDifference("name") != null) {
            if (obj instanceof Catalog && this.getReferenceSnapshot().getDatabase().supports(Schema.class)) {
                this.changedObjects.put(obj, differences);
                return;
            }
            return;
        }
        this.changedObjects.put(obj, differences);
    }

    public boolean areEqual() throws DatabaseException, IOException {
        return this.missingObjects.isEmpty() && this.unexpectedObjects.isEmpty() && this.changedObjects.isEmpty();
    }

    public Set<Class<? extends DatabaseObject>> getComparedTypes() {
        return this.compareControl.getComparedTypes();
    }
}

