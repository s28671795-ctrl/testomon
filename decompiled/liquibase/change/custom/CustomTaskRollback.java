/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.custom;

import liquibase.database.Database;
import liquibase.exception.CustomChangeException;
import liquibase.exception.RollbackImpossibleException;

public interface CustomTaskRollback {
    public void rollback(Database var1) throws CustomChangeException, RollbackImpossibleException;
}

