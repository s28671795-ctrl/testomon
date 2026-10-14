/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.compare;

import java.util.Set;
import liquibase.database.Database;
import liquibase.diff.ObjectDifferences;
import liquibase.diff.compare.CompareControl;
import liquibase.diff.compare.DatabaseObjectComparatorChain;
import liquibase.structure.DatabaseObject;

public interface DatabaseObjectComparator {
    public static final int PRIORITY_NONE = -1;
    public static final int PRIORITY_DEFAULT = 1;
    public static final int PRIORITY_TYPE = 5;
    public static final int PRIORITY_DATABASE = 10;

    public int getPriority(Class<? extends DatabaseObject> var1, Database var2);

    public boolean isSameObject(DatabaseObject var1, DatabaseObject var2, Database var3, DatabaseObjectComparatorChain var4);

    public String[] hash(DatabaseObject var1, Database var2, DatabaseObjectComparatorChain var3);

    public ObjectDifferences findDifferences(DatabaseObject var1, DatabaseObject var2, Database var3, CompareControl var4, DatabaseObjectComparatorChain var5, Set<String> var6);
}

