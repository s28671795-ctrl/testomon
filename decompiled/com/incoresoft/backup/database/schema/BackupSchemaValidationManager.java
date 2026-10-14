/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.backup.database.schema;

import javax.sql.DataSource;

public interface BackupSchemaValidationManager {
    public void validateMainDatabaseStructure();

    public void validateBackupDatabaseStructure(DataSource var1);
}

