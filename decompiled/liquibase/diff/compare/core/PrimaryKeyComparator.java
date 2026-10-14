/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.compare.core;

import java.util.List;
import java.util.Set;
import liquibase.database.Database;
import liquibase.diff.ObjectDifferences;
import liquibase.diff.compare.CompareControl;
import liquibase.diff.compare.DatabaseObjectComparator;
import liquibase.diff.compare.DatabaseObjectComparatorChain;
import liquibase.diff.compare.DatabaseObjectComparatorFactory;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Column;
import liquibase.structure.core.PrimaryKey;
import liquibase.util.StringUtil;

public class PrimaryKeyComparator
implements DatabaseObjectComparator {
    @Override
    public int getPriority(Class<? extends DatabaseObject> objectType, Database database) {
        if (PrimaryKey.class.isAssignableFrom(objectType)) {
            return 5;
        }
        return -1;
    }

    @Override
    public String[] hash(DatabaseObject databaseObject, Database accordingTo, DatabaseObjectComparatorChain chain) {
        PrimaryKey pk = (PrimaryKey)databaseObject;
        if (databaseObject.getName() == null) {
            return DatabaseObjectComparatorFactory.getInstance().hash(pk.getTable(), chain.getSchemaComparisons(), accordingTo);
        }
        if (pk.getTable() == null || pk.getTable().getName() == null) {
            return new String[]{pk.getName().toLowerCase()};
        }
        return new String[]{pk.getName().toLowerCase(), pk.getTable().getName().toLowerCase()};
    }

    @Override
    public boolean isSameObject(DatabaseObject databaseObject1, DatabaseObject databaseObject2, Database accordingTo, DatabaseObjectComparatorChain chain) {
        if (!(databaseObject1 instanceof PrimaryKey) || !(databaseObject2 instanceof PrimaryKey)) {
            return false;
        }
        PrimaryKey thisPrimaryKey = (PrimaryKey)databaseObject1;
        PrimaryKey otherPrimaryKey = (PrimaryKey)databaseObject2;
        if (thisPrimaryKey.getTable() != null && thisPrimaryKey.getTable().getName() != null && otherPrimaryKey.getTable() != null && otherPrimaryKey.getTable().getName() != null) {
            return DatabaseObjectComparatorFactory.getInstance().isSameObject(thisPrimaryKey.getTable(), otherPrimaryKey.getTable(), chain.getSchemaComparisons(), accordingTo);
        }
        return StringUtil.trimToEmpty(thisPrimaryKey.getName()).equalsIgnoreCase(otherPrimaryKey.getName());
    }

    @Override
    public ObjectDifferences findDifferences(DatabaseObject databaseObject1, DatabaseObject databaseObject2, Database accordingTo, CompareControl compareControl, DatabaseObjectComparatorChain chain, Set<String> exclude) {
        exclude.add("name");
        exclude.add("backingIndex");
        exclude.add("columns");
        ObjectDifferences differences = chain.findDifferences(databaseObject1, databaseObject2, accordingTo, compareControl, exclude);
        differences.compare("columns", databaseObject1, databaseObject2, (referenceValue, compareToValue) -> {
            List referenceList = (List)referenceValue;
            List compareList = (List)compareToValue;
            if (referenceList.size() != compareList.size()) {
                return false;
            }
            for (int i = 0; i < referenceList.size(); ++i) {
                if (StringUtil.trimToEmpty(((Column)referenceList.get(i)).getName()).equalsIgnoreCase(StringUtil.trimToEmpty(((Column)compareList.get(i)).getName()))) continue;
                return false;
            }
            return true;
        });
        return differences;
    }
}

