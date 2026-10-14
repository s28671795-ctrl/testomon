/*
 * Decompiled with CFR 0.152.
 */
package liquibase.snapshot;

import liquibase.database.Database;
import liquibase.structure.DatabaseObject;

public interface SnapshotListener {
    public void willSnapshot(DatabaseObject var1, Database var2);

    public void finishedSnapshot(DatabaseObject var1, DatabaseObject var2, Database var3);
}

