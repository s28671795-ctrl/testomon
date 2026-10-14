/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.compare.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import liquibase.database.Database;
import liquibase.diff.ObjectDifferences;
import liquibase.diff.compare.CompareControl;
import liquibase.diff.compare.DatabaseObjectComparator;
import liquibase.diff.compare.DatabaseObjectComparatorChain;
import liquibase.diff.compare.DatabaseObjectComparatorFactory;
import liquibase.diff.compare.core.DefaultDatabaseObjectComparator;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Column;
import liquibase.structure.core.Index;
import liquibase.structure.core.Relation;
import liquibase.util.StringUtil;

public class IndexComparator
implements DatabaseObjectComparator {
    @Override
    public int getPriority(Class<? extends DatabaseObject> objectType, Database database) {
        if (Index.class.isAssignableFrom(objectType)) {
            return 5;
        }
        return -1;
    }

    @Override
    public String[] hash(DatabaseObject databaseObject, Database accordingTo, DatabaseObjectComparatorChain chain) {
        Relation table;
        ArrayList<String> hashes = new ArrayList<String>();
        if (databaseObject.getName() != null) {
            hashes.add(databaseObject.getName().toLowerCase());
        }
        if ((table = ((Index)databaseObject).getRelation()) != null) {
            hashes.addAll(Arrays.asList(DatabaseObjectComparatorFactory.getInstance().hash(table, chain.getSchemaComparisons(), accordingTo)));
        }
        return hashes.toArray(new String[0]);
    }

    @Override
    public boolean isSameObject(DatabaseObject databaseObject1, DatabaseObject databaseObject2, Database accordingTo, DatabaseObjectComparatorChain chain) {
        if (!(databaseObject1 instanceof Index) || !(databaseObject2 instanceof Index)) {
            return false;
        }
        Index thisIndex = (Index)databaseObject1;
        Index otherIndex = (Index)databaseObject2;
        int thisIndexSize = thisIndex.getColumns().size();
        int otherIndexSize = otherIndex.getColumns().size();
        if (thisIndex.getRelation() != null && otherIndex.getRelation() != null) {
            if (!DatabaseObjectComparatorFactory.getInstance().isSameObject(thisIndex.getRelation(), otherIndex.getRelation(), chain.getSchemaComparisons(), accordingTo)) {
                return false;
            }
            if (databaseObject1.getSchema() != null && databaseObject2.getSchema() != null && !DatabaseObjectComparatorFactory.getInstance().isSameObject(databaseObject1.getSchema(), databaseObject2.getSchema(), chain.getSchemaComparisons(), accordingTo)) {
                return false;
            }
            if (databaseObject1.getName() != null && databaseObject2.getName() != null && DefaultDatabaseObjectComparator.nameMatches(databaseObject1, databaseObject2, accordingTo)) {
                return true;
            }
            if (thisIndexSize == 0 || otherIndexSize == 0) {
                return DefaultDatabaseObjectComparator.nameMatches(databaseObject1, databaseObject2, accordingTo);
            }
            if (thisIndexSize != otherIndexSize) {
                return false;
            }
            for (int i = 0; i < otherIndexSize; ++i) {
                if (DatabaseObjectComparatorFactory.getInstance().isSameObject(thisIndex.getColumns().get(i), otherIndex.getColumns().get(i), chain.getSchemaComparisons(), accordingTo)) continue;
                return false;
            }
            return true;
        }
        if (thisIndexSize > 0 && otherIndexSize > 0 && thisIndexSize != otherIndexSize) {
            return false;
        }
        if (!DefaultDatabaseObjectComparator.nameMatches(databaseObject1, databaseObject2, accordingTo)) {
            return false;
        }
        if (databaseObject1.getSchema() != null && databaseObject2.getSchema() != null) {
            return DatabaseObjectComparatorFactory.getInstance().isSameObject(databaseObject1.getSchema(), databaseObject2.getSchema(), chain.getSchemaComparisons(), accordingTo);
        }
        return true;
    }

    @Override
    public ObjectDifferences findDifferences(DatabaseObject databaseObject1, DatabaseObject databaseObject2, Database accordingTo, CompareControl compareControl, DatabaseObjectComparatorChain chain, Set<String> exclude) {
        exclude.add("name");
        exclude.add("columns");
        ObjectDifferences differences = chain.findDifferences(databaseObject1, databaseObject2, accordingTo, compareControl, exclude);
        differences.compare("columns", databaseObject1, databaseObject2, (referenceValue, compareToValue) -> {
            List referenceList = (List)referenceValue;
            List compareList = (List)compareToValue;
            if (referenceList.size() != compareList.size()) {
                return false;
            }
            for (int i = 0; i < referenceList.size(); ++i) {
                if (referenceList.get(i) == null || compareList.get(i) == null) {
                    return referenceList.get(i) == compareList.get(i);
                }
                if (StringUtil.trimToEmpty(((Column)referenceList.get(i)).getName()).equalsIgnoreCase(StringUtil.trimToEmpty(((Column)compareList.get(i)).getName()))) continue;
                return false;
            }
            return true;
        });
        return differences;
    }
}

