/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup.ws.message.create.server;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.vezha.backup.ws.message.create.server.ServerBackupCreationWSMessage;

public class BackupCreationCompletedMessage
implements ServerBackupCreationWSMessage {
    @JSONField(name="id")
    private int id;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BackupCreationCompletedMessage)) {
            return false;
        }
        BackupCreationCompletedMessage other = (BackupCreationCompletedMessage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        return this.getId() == other.getId();
    }

    protected boolean canEqual(Object other) {
        return other instanceof BackupCreationCompletedMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getId();
        return result;
    }

    public String toString() {
        return "BackupCreationCompletedMessage(id=" + this.getId() + ")";
    }

    public BackupCreationCompletedMessage() {
    }

    public BackupCreationCompletedMessage(int id) {
        this.id = id;
    }
}

