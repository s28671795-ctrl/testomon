/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.audit.messages.license;

import com.incoresoft.plugin.commons.audit.template.AuditMessage;

public final class LicenseActivatedWithKeyAuditMessage
extends AuditMessage {
    private final String key;

    @Override
    protected String getTemplateId() {
        return "LICENSE_ACTIVATED_WITH_KEY";
    }

    public LicenseActivatedWithKeyAuditMessage(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    public String toString() {
        return "LicenseActivatedWithKeyAuditMessage(key=" + this.getKey() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LicenseActivatedWithKeyAuditMessage)) {
            return false;
        }
        LicenseActivatedWithKeyAuditMessage other = (LicenseActivatedWithKeyAuditMessage)o;
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
        return other instanceof LicenseActivatedWithKeyAuditMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        String $key = this.getKey();
        result = result * 59 + ($key == null ? 43 : $key.hashCode());
        return result;
    }
}

