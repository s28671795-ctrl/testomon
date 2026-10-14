/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.backup.database.data;

import com.incoresoft.backup.database.data.BackupIgnoredTables;
import com.incoresoft.backup.database.listeners.DatabaseTablesPopulationListener;
import java.util.Collection;
import javax.sql.DataSource;

public interface BackupDataPopulationManager {
    public void populateBackupDatabase(DataSource var1, BackupIgnoredTables var2, Collection<? extends DatabaseTablesPopulationListener> var3) throws InterruptedException;

    public void populateMainDatabase(DataSource var1, BackupIgnoredTables var2, Collection<? extends DatabaseTablesPopulationListener> var3) throws InterruptedException;
}

