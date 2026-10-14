/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup.ws.message.create.client;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.vezha.backup.ws.message.create.client.ClientBackupCreationWSMessage;
import com.incoresoft.vezha.dto.backup.validations.BackupNameContainsIllegalSymbols;

@BackupNameContainsIllegalSymbols
public class CreateBackupMessage
implements ClientBackupCreationWSMessage {
    @JSONField(name="include_audit_logs")
    private boolean includeAuditLogs;
    @JSONField(name="name")
    private String name;
    @JSONField(name="description")
    private String description;

    public boolean isIncludeAuditLogs() {
        return this.includeAuditLogs;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setIncludeAuditLogs(boolean includeAuditLogs) {
        this.includeAuditLogs = includeAuditLogs;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CreateBackupMessage)) {
            return false;
        }
        CreateBackupMessage other = (CreateBackupMessage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.isIncludeAuditLogs() != other.isIncludeAuditLogs()) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        String this$description = this.getDescription();
        String other$description = other.getDescription();
        return !(this$description == null ? other$description != null : !this$description.equals(other$description));
    }

    protected boolean canEqual(Object other) {
        return other instanceof CreateBackupMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isIncludeAuditLogs() ? 79 : 97);
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        String $description = this.getDescription();
        result = result * 59 + ($description == null ? 43 : $description.hashCode());
        return result;
    }

    public String toString() {
        return "CreateBackupMessage(includeAuditLogs=" + this.isIncludeAuditLogs() + ", name=" + this.getName() + ", description=" + this.getDescription() + ")";
    }

    public static final class Fields {
        public static final String includeAuditLogs = "includeAuditLogs";
        public static final String name = "name";
        public static final String description = "description";
    }
}

