/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup.ws.message.restore.client;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.vezha.backup.ws.message.restore.BackupRestoreWSMessage;

public interface ClientBackupRestoreWSMessage
extends BackupRestoreWSMessage {
    @JSONField(name="backup_hash")
    public String getBackupHash();
}

