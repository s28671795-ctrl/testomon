/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.backup.database.listeners;

import com.incoresoft.backup.database.listeners.DatabaseTablesPopulationListener;

public interface BackupDatabaseRestoreListener
extends DatabaseTablesPopulationListener {
    public void onDatabaseDataDeletingStage();

    public void onDatabasePopulationStage();
}

