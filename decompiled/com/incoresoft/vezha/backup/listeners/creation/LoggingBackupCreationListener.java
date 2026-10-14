/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup.listeners.creation;

import com.incoresoft.vezha.backup.BackupCreationStage;
import com.incoresoft.vezha.backup.listeners.creation.BackupCreationListener;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingBackupCreationListener
implements BackupCreationListener {
    private static final Logger log = LoggerFactory.getLogger(LoggingBackupCreationListener.class);
    private Instant start;

    @Override
    public void onBackupCreationProgress(BackupCreationStage stage) {
        BackupCreationStage previous = stage.getPrevious();
        if (previous != null) {
            long duration = Instant.now().getEpochSecond() - this.start.getEpochSecond();
            log.info("Backup creation stage {} is completed in {} seconds", (Object)previous, (Object)duration);
        }
        log.info("Backup creation stage {} is completing...", (Object)stage);
        this.start = Instant.now();
    }

    @Override
    public void onBackupCreationFailed(Exception e) {
        log.error("Backup creation failed", e);
    }

    @Override
    public void onBackupFileCreationCompleted() {
        long duration = Instant.now().getEpochSecond() - this.start.getEpochSecond();
        log.info("Backup files archived in {} seconds", (Object)duration);
        log.info("Backup creation finalization...");
        this.start = Instant.now();
    }

    @Override
    public void onBackupCreationCompleted(int backupId) {
        long duration = Instant.now().getEpochSecond() - this.start.getEpochSecond();
        log.info("Backup creation finalized in {} seconds", (Object)duration);
        log.info("Backup creation completed");
    }

    @Override
    public void onTablePopulated(String tableName) {
        log.info("Table {} is populated", (Object)tableName);
    }
}

