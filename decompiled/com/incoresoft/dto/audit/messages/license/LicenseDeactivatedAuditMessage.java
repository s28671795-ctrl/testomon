/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.audit.messages.license;

import com.incoresoft.plugin.commons.audit.template.AuditMessage;

public final class LicenseDeactivatedAuditMessage
extends AuditMessage {
    private final String key;

    @Override
    protected String getTemplateId() {
        return "LICENSE_DEACTIVATED_WITH_KEY";
    }

    public LicenseDeactivatedAuditMessage(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    public String toString() {
        return "LicenseDeactivatedAuditMessage(key=" + this.getKey() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LicenseDeactivatedAuditMessage)) {
            return false;
        }
        LicenseDeactivatedAuditMessage other = (LicenseDeactivatedAuditMessage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        String this$key = this.getKey();
        String other$key = other.getKey();
        return !(this$key == null ? other$key != null : !this$key.equals(other$key));
    }

    protected boolean canEqual(Object other) {
        return other instanceof LicenseDeactivatedAuditMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        String $key = this.getKey();
        result = result * 59 + ($key == null ? 43 : $key.hashCode());
        return result;
    }
}

