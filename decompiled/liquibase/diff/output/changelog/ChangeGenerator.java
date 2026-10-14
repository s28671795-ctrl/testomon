/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.output.changelog;

import liquibase.change.Change;
import liquibase.database.Database;
import liquibase.diff.compare.CompareControl;
import liquibase.structure.DatabaseObject;

public interface ChangeGenerator {
    public static final Change[] EMPTY_CHANGE = new Change[0];
    public static final int PRIORITY_NONE = -1;
    public static final int PRIORITY_DEFAULT = 1;
    public static final int PRIORITY_DATABASE = 5;
    public static final int PRIORITY_ADDITIONAL = 50;

    public int getPriority(Class<? extends DatabaseObject> var1, Database var2);

    public Class<? extends DatabaseObject>[] runAfterTypes();

    public Class<? extends DatabaseObject>[] runBeforeTypes();

    public Change[] fixSchema(Change[] var1, CompareControl.SchemaComparison[] var2);

    public Change[] fixOutputAsSchema(Change[] var1, CompareControl.SchemaComparison[] var2);
}

