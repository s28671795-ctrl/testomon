/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup;

import com.incoresoft.middleware.model.user.User;
import com.incoresoft.vezha.backup.BackupsManager;
import com.incoresoft.vezha.backup.listeners.creation.BackupCreationListener;
import com.incoresoft.vezha.backup.ws.message.create.client.CreateBackupMessage;
import java.util.List;

public class BackupCreationProcess {
    private final Thread thread = new Thread(() -> backupsManager.create(user, createBackupMessage, listeners));

    public BackupCreationProcess(User user, BackupsManager backupsManager, CreateBackupMessage createBackupMessage, List<BackupCreationListener> listeners) {
    }

    public void start() {
        this.thread.start();
    }

    public void cancel() {
        this.thread.interrupt();
    }

    public boolean isRunning() {
        return !this.thread.isInterrupted() && this.thread.isAlive();
    }
}

