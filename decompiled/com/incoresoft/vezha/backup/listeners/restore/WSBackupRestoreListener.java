/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup.listeners.restore;

import com.incoresoft.vezha.backup.BackupRestoreStage;
import com.incoresoft.vezha.backup.listeners.restore.BackupRestoreListener;
import com.incoresoft.vezha.backup.ws.message.restore.server.BackupRestoreCompletedMessage;
import com.incoresoft.vezha.backup.ws.message.restore.server.BackupRestoreProgressMessage;
import io.javalin.websocket.WsMessageContext;

public class WSBackupRestoreListener
implements BackupRestoreListener {
    private final WsMessageContext context;

    @Override
    public void onBackupRestoreProgress(BackupRestoreStage stage) {
        this.context.send(new BackupRestoreProgressMessage(stage));
    }

    @Override
    public void onBackupRestoreCompleted() {
        this.context.send(new BackupRestoreCompletedMessage());
    }

    @Override
    public void onTablePopulated(String s2) {
    }

    public WSBackupRestoreListener(WsMessageContext context) {
        this.context = context;
    }
}

