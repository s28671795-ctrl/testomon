/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup.ws.message;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.vezha.backup.ws.message.BackupWsMessageType;

public interface BackupWSMessage {
    @JSONField(name="type")
    default public String getType() {
        return BackupWsMessageType.getByMessageClass(this.getClass()).toString();
    }
}

