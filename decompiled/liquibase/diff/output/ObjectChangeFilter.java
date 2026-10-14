/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.output;

import liquibase.database.Database;
import liquibase.diff.ObjectDifferences;
import liquibase.structure.DatabaseObject;

public interface ObjectChangeFilter {
    public boolean includeMissing(DatabaseObject var1, Database var2, Database var3);

    public boolean includeUnexpected(DatabaseObject var1, Database var2, Database var3);

    public boolean includeChanged(DatabaseObject var1, ObjectDifferences var2, Database var3, Database var4);

    public boolean include(DatabaseObject var1);
}

