/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.compare.core;

import java.util.Set;
import liquibase.database.Database;
import liquibase.diff.ObjectDifferences;
import liquibase.diff.compare.CompareControl;
import liquibase.diff.compare.DatabaseObjectComparator;
import liquibase.diff.compare.DatabaseObjectComparatorChain;
import liquibase.diff.compare.DatabaseObjectComparatorFactory;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Column;
import liquibase.structure.core.ForeignKey;
import liquibase.structure.core.Table;
import liquibase.util.StringUtil;

public class ForeignKeyComparator
implements DatabaseObjectComparator {
    @Override
    public int getPriority(Class<? extends DatabaseObject> objectType, Database database) {
        if (ForeignKey.class.isAssignableFrom(objectType)) {
            return 5;
        }
        return -1;
    }

    @Override
    public String[] hash(DatabaseObject databaseObject, Database accordingTo, DatabaseObjectComparatorChain chain) {
        ForeignKey fk = (ForeignKey)databaseObject;
        if (databaseObject.getName() == null) {
            return DatabaseObjectComparatorFactory.getInstance().hash(fk.getForeignKeyTable(), chain.getSchemaComparisons(), accordingTo);
        }
        if (fk.getForeignKeyTable() == null || fk.getForeignKeyTable().getName() == null) {
            return new String[]{fk.getName().toLowerCase()};
        }
        return new String[]{fk.getName().toLowerCase(), fk.getForeignKeyTable().getName().toLowerCase()};
    }

    @Override
    public boolean isSameObject(DatabaseObject databaseObject1, DatabaseObject databaseObject2, Database accordingTo, DatabaseObjectComparatorChain chain) {
        if (!(databaseObject1 instanceof ForeignKey) || !(databaseObject2 instanceof ForeignKey)) {
            return false;
        }
        ForeignKey thisForeignKey = (ForeignKey)databaseObject1;
        ForeignKey otherForeignKey = (ForeignKey)databaseObject2;
        if (thisForeignKey.getPrimaryKeyTable() == null || thisForeignKey.getForeignKeyTable() == null || otherForeignKey.getPrimaryKeyTable() == null || otherForeignKey.getForeignKeyTable() == null) {
            if (thisForeignKey.getForeignKeyTable() != null && thisForeignKey.getForeignKeyTable().getName() != null && otherForeignKey.getForeignKeyTable() != null && otherForeignKey.getForeignKeyTable().getName() != null && !chain.isSameObject(thisForeignKey.getForeignKeyTable(), otherForeignKey.getForeignKeyTable(), accordingTo)) {
                return false;
            }
            if (thisForeignKey.getName() != null && otherForeignKey.getName() != null) {
                if (accordingTo.isCaseSensitive()) {
                    return thisForeignKey.getName().equals(otherForeignKey.getName());
                }
                return thisForeignKey.getName().equalsIgnoreCase(otherForeignKey.getName());
            }
            return false;
        }
        if (thisForeignKey.getForeignKeyColumns() != null && thisForeignKey.getPrimaryKeyColumns() != null && otherForeignKey.getForeignKeyColumns() != null && otherForeignKey.getPrimaryKeyColumns() != null) {
            StringUtil.StringUtilFormatter<Column> formatter = obj -> obj.toString(false);
            boolean columnsTheSame = accordingTo.isCaseSensitive() ? StringUtil.join(thisForeignKey.getForeignKeyColumns(), ",", formatter).equals(StringUtil.join(otherForeignKey.getForeignKeyColumns(), ",", formatter)) && StringUtil.join(thisForeignKey.getPrimaryKeyColumns(), ",", formatter).equals(StringUtil.join(otherForeignKey.getPrimaryKeyColumns(), ",", formatter)) : StringUtil.join(thisForeignKey.getForeignKeyColumns(), ",", formatter).equalsIgnoreCase(StringUtil.join(otherForeignKey.getForeignKeyColumns(), ",", formatter)) && StringUtil.join(thisForeignKey.getPrimaryKeyColumns(), ",", formatter).equalsIgnoreCase(StringUtil.join(otherForeignKey.getPrimaryKeyColumns(), ",", formatter));
            return columnsTheSame && DatabaseObjectComparatorFactory.getInstance().isSameObject(thisForeignKey.getForeignKeyTable(), otherForeignKey.getForeignKeyTable(), chain.getSchemaComparisons(), accordingTo) && DatabaseObjectComparatorFactory.getInstance().isSameObject(thisForeignKey.getPrimaryKeyTable(), otherForeignKey.getPrimaryKeyTable(), chain.getSchemaComparisons(), accordingTo);
        }
        return false;
    }

    @Override
    public ObjectDifferences findDifferences(DatabaseObject databaseObject1, DatabaseObject databaseObject2, Database accordingTo, CompareControl compareControl, DatabaseObjectComparatorChain chain, Set<String> exclue) {
        exclue.add("name");
        exclue.add("backingIndex");
        exclue.add("foreignKeyColumns");
        exclue.add("primaryKeyColumns");
        exclue.add("foreignKeyTable");
        exclue.add("primaryKeyTable");
        ObjectDifferences differences = chain.findDifferences(databaseObject1, databaseObject2, accordingTo, compareControl, exclue);
        differences.compare("foreignKeyColumns", databaseObject1, databaseObject2, new ObjectDifferences.DatabaseObjectNameCompareFunction(Column.class, accordingTo));
        differences.compare("primaryKeyColumns", databaseObject1, databaseObject2, new ObjectDifferences.DatabaseObjectNameCompareFunction(Column.class, accordingTo));
        differences.compare("foreignKeyTable", databaseObject1, databaseObject2, new ObjectDifferences.DatabaseObjectNameCompareFunction(Table.class, accordingTo));
        differences.compare("primaryKeyTable", databaseObject1, databaseObject2, new ObjectDifferences.DatabaseObjectNameCompareFunction(Table.class, accordingTo));
        return differences;
    }
}

