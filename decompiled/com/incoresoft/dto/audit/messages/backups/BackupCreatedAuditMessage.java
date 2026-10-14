/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.audit.messages.backups;

import com.incoresoft.plugin.commons.audit.template.AuditMessage;

public final class BackupCreatedAuditMessage
extends AuditMessage {
    private final int id;
    private final String fileName;

    @Override
    protected String getTemplateId() {
        return "BACKUP_CREATED";
    }

    public BackupCreatedAuditMessage(int id, String fileName) {
        this.id = id;
        this.fileName = fileName;
    }

    public int getId() {
        return this.id;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String toString() {
        return "BackupCreatedAuditMessage(id=" + this.getId() + ", fileName=" + this.getFileName() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BackupCreatedAuditMessage)) {
            return false;
        }
        BackupCreatedAuditMessage other = (BackupCreatedAuditMessage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        if (this.getId() != other.getId()) {
            return false;
        }
        String this$fileName = this.getFileName();
        String other$fileName = other.getFileName();
        return !(this$fileName == null ? other$fileName != null : !this$fileName.equals(other$fileName));
    }

    protected boolean canEqual(Object other) {
        return other instanceof BackupCreatedAuditMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        result = result * 59 + this.getId();
        String $fileName = this.getFileName();
        result = result * 59 + ($fileName == null ? 43 : $fileName.hashCode());
        return result;
    }
}

