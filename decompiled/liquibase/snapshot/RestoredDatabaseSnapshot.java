/*
 * Decompiled with CFR 0.152.
 */
package liquibase.snapshot;

import liquibase.database.Database;
import liquibase.exception.DatabaseException;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.InvalidExampleException;
import liquibase.structure.DatabaseObject;

public class RestoredDatabaseSnapshot
extends DatabaseSnapshot {
    public RestoredDatabaseSnapshot(Database database) throws DatabaseException, InvalidExampleException {
        super(new DatabaseObject[0], database);
    }
}

