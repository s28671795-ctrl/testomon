/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.audit.messages.license;

import com.incoresoft.plugin.commons.audit.template.AuditMessage;

public final class LicenseActivatedWithFileAuditMessage
extends AuditMessage {
    private final String fileName;

    @Override
    protected String getTemplateId() {
        return "LICENSE_ACTIVATED_WITH_FILE";
    }

    public LicenseActivatedWithFileAuditMessage(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String toString() {
        return "LicenseActivatedWithFileAuditMessage(fileName=" + this.getFileName() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LicenseActivatedWithFileAuditMessage)) {
            return false;
        }
        LicenseActivatedWithFileAuditMessage other = (LicenseActivatedWithFileAuditMessage)o;
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
        return other instanceof LicenseActivatedWithFileAuditMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        String $fileName = this.getFileName();
        result = result * 59 + ($fileName == null ? 43 : $fileName.hashCode());
        return result;
    }
}

