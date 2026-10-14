/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup.listeners.creation;

import com.incoresoft.vezha.backup.BackupCreationStage;
import com.incoresoft.vezha.backup.listeners.creation.BackupCreationListener;
import com.incoresoft.vezha.backup.ws.message.create.server.BackupCreationCompletedMessage;
import com.incoresoft.vezha.backup.ws.message.create.server.BackupCreationFailedMessage;
import com.incoresoft.vezha.backup.ws.message.create.server.BackupCreationProgressMessage;
import io.javalin.websocket.WsMessageContext;

public class WSBackupCreationListener
implements BackupCreationListener {
    private final WsMessageContext context;

    @Override
    public void onBackupCreationProgress(BackupCreationStage stage) {
        this.context.send(new BackupCreationProgressMessage(stage));
    }

    @Override
    public void onBackupCreationFailed(Exception e) {
        this.context.send(new BackupCreationFailedMessage());
    }

    @Override
    public void onBackupFileCreationCompleted() {
    }

    @Override
    public void onBackupCreationCompleted(int backupId) {
        this.context.send(new BackupCreationCompletedMessage(backupId));
    }

    @Override
    public void onTablePopulated(String tableName) {
    }

    public WSBackupCreationListener(WsMessageContext context) {
        this.context = context;
    }
}

