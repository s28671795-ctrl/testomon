/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.audit.messages.service;

import com.incoresoft.plugin.commons.audit.template.AuditMessage;

public final class ServiceUpdatedAuditMessage
extends AuditMessage {
    private final String id;
    private final String newVersion;
    private final String oldVersion;

    @Override
    protected String getTemplateId() {
        return "SERVICE_UPDATED";
    }

    public ServiceUpdatedAuditMessage(String id, String newVersion, String oldVersion) {
        this.id = id;
        this.newVersion = newVersion;
        this.oldVersion = oldVersion;
    }

    public String getId() {
        return this.id;
    }

    public String getNewVersion() {
        return this.newVersion;
    }

    public String getOldVersion() {
        return this.oldVersion;
    }

    public String toString() {
        return "ServiceUpdatedAuditMessage(id=" + this.getId() + ", newVersion=" + this.getNewVersion() + ", oldVersion=" + this.getOldVersion() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ServiceUpdatedAuditMessage)) {
            return false;
        }
        ServiceUpdatedAuditMessage other = (ServiceUpdatedAuditMessage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        String this$id = this.getId();
        String other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) {
            return false;
        }
        String this$newVersion = this.getNewVersion();
        String other$newVersion = other.getNewVersion();
        if (this$newVersion == null ? other$newVersion != null : !this$newVersion.equals(other$newVersion)) {
            return false;
        }
        String this$oldVersion = this.getOldVersion();
        String other$oldVersion = other.getOldVersion();
        return !(this$oldVersion == null ? other$oldVersion != null : !this$oldVersion.equals(other$oldVersion));
    }

    protected boolean canEqual(Object other) {
        return other instanceof ServiceUpdatedAuditMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        String $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        String $newVersion = this.getNewVersion();
        result = result * 59 + ($newVersion == null ? 43 : $newVersion.hashCode());
        String $oldVersion = this.getOldVersion();
        result = result * 59 + ($oldVersion == null ? 43 : $oldVersion.hashCode());
        return result;
    }
}

