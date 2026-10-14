/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup.ws.message.restore.client;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.vezha.backup.ws.message.restore.client.ClientBackupRestoreWSMessage;

public class ConfirmRestoreMessage
implements ClientBackupRestoreWSMessage {
    @JSONField(name="backup_hash")
    private String backupHash;

    @Override
    public String getBackupHash() {
        return this.backupHash;
    }

    public void setBackupHash(String backupHash) {
        this.backupHash = backupHash;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ConfirmRestoreMessage)) {
            return false;
        }
        ConfirmRestoreMessage other = (ConfirmRestoreMessage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$backupHash = this.getBackupHash();
        String other$backupHash = other.getBackupHash();
        return !(this$backupHash == null ? other$backupHash != null : !this$backupHash.equals(other$backupHash));
    }

    protected boolean canEqual(Object other) {
        return other instanceof ConfirmRestoreMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $backupHash = this.getBackupHash();
        result = result * 59 + ($backupHash == null ? 43 : $backupHash.hashCode());
        return result;
    }

    public String toString() {
        return "ConfirmRestoreMessage(backupHash=" + this.getBackupHash() + ")";
    }
}

