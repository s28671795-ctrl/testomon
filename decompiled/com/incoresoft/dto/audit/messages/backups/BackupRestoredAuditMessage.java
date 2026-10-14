/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.audit.messages.backups;

import com.incoresoft.plugin.commons.audit.template.AuditMessage;

public final class BackupRestoredAuditMessage
extends AuditMessage {
    private final String fileName;

    @Override
    protected String getTemplateId() {
        return "BACKUP_RESTORED";
    }

    public BackupRestoredAuditMessage(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String toString() {
        return "BackupRestoredAuditMessage(fileName=" + this.getFileName() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BackupRestoredAuditMessage)) {
            return false;
        }
        BackupRestoredAuditMessage other = (BackupRestoredAuditMessage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        String this$fileName = this.getFileName();
        String other$fileName = other.getFileName();
        return !(this$fileName == null ? other$fileName != null : !this$fileName.equals(other$fileName));
    }

    protected boolean canEqual(Object other) {
        return other instanceof BackupRestoredAuditMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        String $fileName = this.getFileName();
        result = result * 59 + ($fileName == null ? 43 : $fileName.hashCode());
        return result;
    }
}

