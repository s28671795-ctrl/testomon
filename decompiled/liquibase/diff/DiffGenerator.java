/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff;

import liquibase.database.Database;
import liquibase.diff.DiffResult;
import liquibase.diff.compare.CompareControl;
import liquibase.exception.DatabaseException;
import liquibase.servicelocator.PrioritizedService;
import liquibase.snapshot.DatabaseSnapshot;

public interface DiffGenerator
extends PrioritizedService {
    public DiffResult compare(DatabaseSnapshot var1, DatabaseSnapshot var2, CompareControl var3) throws DatabaseException;

    public boolean supports(Database var1, Database var2);
}

