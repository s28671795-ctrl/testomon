/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.backup.database.data;

import com.incoresoft.backup.database.data.BackupIgnoredTables;

public interface DatabaseDataDeletionManager {
    public void deleteData(BackupIgnoredTables var1);

    default public void deleteData() {
        this.deleteData(new BackupIgnoredTables());
    }
}

