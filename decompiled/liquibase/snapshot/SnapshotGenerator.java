/*
 * Decompiled with CFR 0.152.
 */
package liquibase.snapshot;

import liquibase.database.Database;
import liquibase.exception.DatabaseException;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.InvalidExampleException;
import liquibase.snapshot.SnapshotGeneratorChain;
import liquibase.structure.DatabaseObject;

public interface SnapshotGenerator {
    public static final int PRIORITY_NONE = -1;
    public static final int PRIORITY_DEFAULT = 1;
    public static final int PRIORITY_DATABASE = 5;
    public static final int PRIORITY_ADDITIONAL = 50;

    public int getPriority(Class<? extends DatabaseObject> var1, Database var2);

    public <T extends DatabaseObject> T snapshot(T var1, DatabaseSnapshot var2, SnapshotGeneratorChain var3) throws DatabaseException, InvalidExampleException;

    public Class<? extends DatabaseObject>[] addsTo();

    public Class<? extends SnapshotGenerator>[] replaces();
}

