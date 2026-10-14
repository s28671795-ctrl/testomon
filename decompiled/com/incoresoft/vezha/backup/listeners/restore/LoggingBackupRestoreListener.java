/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup.listeners.restore;

import com.incoresoft.vezha.backup.BackupRestoreStage;
import com.incoresoft.vezha.backup.listeners.restore.BackupRestoreListener;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingBackupRestoreListener
implements BackupRestoreListener {
    private static final Logger log = LoggerFactory.getLogger(LoggingBackupRestoreListener.class);
    private Instant start;

    @Override
    public void onBackupRestoreProgress(BackupRestoreStage stage) {
        BackupRestoreStage previous = stage.getPrevious();
        if (previous != null) {
            long duration = Instant.now().getEpochSecond() - this.start.getEpochSecond();
            log.info("Backup creation stage {} is completed in {} seconds", (Object)previous, (Object)duration);
        }
        log.info("Backup creation stage {} is completing...", (Object)stage);
        this.start = Instant.now();
    }

    @Override
    public void onBackupRestoreCompleted() {
        long duration = Instant.now().getEpochSecond() - this.start.getEpochSecond();
        log.info("Configuration files imported in {} seconds", (Object)duration);
        log.info("Backup restore completed");
    }

    @Override
    public void onTablePopulated(String tableName) {
        log.info("Table {} populated", (Object)tableName);
    }
}

