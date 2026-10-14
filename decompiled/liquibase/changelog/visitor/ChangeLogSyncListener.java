/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog.visitor;

import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.database.Database;

public interface ChangeLogSyncListener {
    public void markedRan(ChangeSet var1, DatabaseChangeLog var2, Database var3);

    default public void markedRanFailed(ChangeSet changeSet, DatabaseChangeLog databaseChangeLog, Database database, Exception e) {
    }
}

