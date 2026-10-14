/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.compare.core;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import liquibase.database.Database;
import liquibase.diff.ObjectDifferences;
import liquibase.diff.compare.CompareControl;
import liquibase.diff.compare.DatabaseObjectComparator;
import liquibase.diff.compare.DatabaseObjectComparatorChain;
import liquibase.diff.compare.DatabaseObjectComparatorFactory;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Column;
import liquibase.structure.core.DataType;

public final class DefaultDatabaseObjectComparator
implements DatabaseObjectComparator {
    @Override
    public int getPriority(Class<? extends DatabaseObject> objectType, Database database) {
        return 1;
    }

    @Override
    public String[] hash(DatabaseObject databaseObject, Database accordingTo, DatabaseObjectComparatorChain chain) {
        String name = databaseObject.getName();
        if (name == null) {
            name = "null";
        }
        return new String[]{name.toLowerCase()};
    }

    @Override
    public boolean isSameObject(DatabaseObject databaseObject1, DatabaseObject databaseObject2, Database accordingTo, DatabaseObjectComparatorChain chain) {
        if (databaseObject1.getSchema() != null && databaseObject2.getSchema() != null && !DatabaseObjectComparatorFactory.getInstance().isSameObject(databaseObject1.getSchema(), databaseObject2.getSchema(), chain.getSchemaComparisons(), accordingTo)) {
            return false;
        }
        if (databaseObject1.getClass().isAssignableFrom(databaseObject2.getClass()) || databaseObject2.getClass().isAssignableFrom(databaseObject1.getClass())) {
            return DefaultDatabaseObjectComparator.nameMatches(databaseObject1, databaseObject2, accordingTo);
        }
        return false;
    }

    @Override
    public ObjectDifferences findDifferences(DatabaseObject databaseObject1, DatabaseObject databaseObject2, Database accordingTo, CompareControl compareControl, DatabaseObjectComparatorChain chain, Set<String> exclude) {
        HashSet<String> attributes = new HashSet<String>();
        attributes.addAll(databaseObject1.getAttributes());
        attributes.addAll(databaseObject2.getAttributes());
        ObjectDifferences differences = new ObjectDifferences(compareControl);
        exclude.add("schema");
        exclude.add("catalog");
        for (String attribute : attributes) {
            ObjectDifferences.CompareFunction compareFunction;
            if (exclude.contains(attribute)) continue;
            Object attribute1 = databaseObject1.getAttribute(attribute, Object.class);
            Object attribute2 = databaseObject2.getAttribute(attribute, Object.class);
            attribute1 = this.undoCollection(attribute1, attribute2);
            attribute2 = this.undoCollection(attribute2, attribute1);
            if (attribute1 instanceof DatabaseObject || attribute2 instanceof DatabaseObject) {
                Class<?> type = attribute1 != null ? attribute1.getClass() : attribute2.getClass();
                compareFunction = new ObjectDifferences.DatabaseObjectNameCompareFunction(type, accordingTo);
            } else {
                compareFunction = attribute1 instanceof DataType || attribute2 instanceof DataType ? new ObjectDifferences.ToStringCompareFunction(false) : (attribute1 instanceof Column.AutoIncrementInformation || attribute2 instanceof Column.AutoIncrementInformation ? new ObjectDifferences.ToStringCompareFunction(false) : (attribute1 instanceof Collection || attribute2 instanceof Collection ? new ObjectDifferences.OrderedCollectionCompareFunction(new ObjectDifferences.StandardCompareFunction(chain.getSchemaComparisons(), accordingTo)) : new ObjectDifferences.StandardCompareFunction(chain.getSchemaComparisons(), accordingTo)));
            }
            differences.compare(attribute, databaseObject1, databaseObject2, compareFunction);
        }
        return differences;
    }

    protected Object undoCollection(Object potentialCollection, Object otherObject) {
        if (otherObject != null && potentialCollection instanceof Collection && !(otherObject instanceof Collection) && ((Collection)potentialCollection).size() == 1 && ((Collection)potentialCollection).iterator().next().getClass().equals(otherObject.getClass())) {
            potentialCollection = ((Collection)potentialCollection).iterator().next();
        }
        return potentialCollection;
    }

    public static boolean nameMatches(DatabaseObject databaseObject1, DatabaseObject databaseObject2, Database accordingTo) {
        String object1Name = accordingTo.correctObjectName(databaseObject1.getName(), databaseObject1.getClass());
        String object2Name = accordingTo.correctObjectName(databaseObject2.getName(), databaseObject2.getClass());
        if (object1Name == null && object2Name == null) {
            return true;
        }
        if (object1Name == null || object2Name == null) {
            return false;
        }
        if (accordingTo.isCaseSensitive()) {
            return object1Name.equals(object2Name);
        }
        return object1Name.equalsIgnoreCase(object2Name);
    }
}

