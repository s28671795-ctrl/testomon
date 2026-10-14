/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import liquibase.sql.Sql;
import liquibase.structure.DatabaseObject;
import liquibase.util.StringUtil;

public class UnparsedSql
implements Sql {
    private final String sql;
    private final String endDelimiter;
    private final Set<DatabaseObject> affectedDatabaseObjects = new HashSet<DatabaseObject>();

    public UnparsedSql(String sql, DatabaseObject ... affectedDatabaseObjects) {
        this(sql, ";", affectedDatabaseObjects);
    }

    public UnparsedSql(String sql, String endDelimiter, DatabaseObject ... affectedDatabaseObjects) {
        this.sql = StringUtil.trimToEmpty(sql);
        this.endDelimiter = endDelimiter;
        this.affectedDatabaseObjects.addAll(Arrays.asList(affectedDatabaseObjects));
        ArrayList<DatabaseObject> moreAffectedDatabaseObjects = new ArrayList<DatabaseObject>();
        boolean foundMore = true;
        while (foundMore) {
            for (DatabaseObject object : this.affectedDatabaseObjects) {
                DatabaseObject[] containingObjects = object.getContainingObjects();
                if (containingObjects == null) continue;
                for (DatabaseObject containingObject : containingObjects) {
                    if (containingObject == null || this.affectedDatabaseObjects.contains(containingObject) || moreAffectedDatabaseObjects.contains(containingObject)) continue;
                    moreAffectedDatabaseObjects.add(containingObject);
                }
            }
            foundMore = !moreAffectedDatabaseObjects.isEmpty();
            this.affectedDatabaseObjects.addAll(moreAffectedDatabaseObjects);
            moreAffectedDatabaseObjects.clear();
        }
        this.affectedDatabaseObjects.addAll(moreAffectedDatabaseObjects);
    }

    @Override
    public String toSql() {
        return this.sql;
    }

    public String toString() {
        return this.toSql() + this.getEndDelimiter();
    }

    @Override
    public String getEndDelimiter() {
        return this.endDelimiter;
    }

    public Set<? extends DatabaseObject> getAffectedDatabaseObjects() {
        return this.affectedDatabaseObjects;
    }
}

