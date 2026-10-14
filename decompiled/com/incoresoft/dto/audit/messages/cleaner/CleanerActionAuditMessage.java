/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.audit.messages.cleaner;

import com.incoresoft.middleware.cleaner.CleaningCategory;
import com.incoresoft.plugin.commons.audit.template.AuditMessage;

public final class CleanerActionAuditMessage
extends AuditMessage {
    private final CleaningCategory category;

    @Override
    protected String getTemplateId() {
        return "CLEANER_ACTION";
    }

    public CleanerActionAuditMessage(CleaningCategory category) {
        this.category = category;
    }

    public CleaningCategory getCategory() {
        return this.category;
    }

    public String toString() {
        return "CleanerActionAuditMessage(category=" + String.valueOf(this.getCategory()) + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CleanerActionAuditMessage)) {
            return false;
        }
        CleanerActionAuditMessage other = (CleanerActionAuditMessage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        CleaningCategory this$category = this.getCategory();
        CleaningCategory other$category = other.getCategory();
        return !(this$category == null ? other$category != null : !this$category.equals(other$category));
    }

    protected boolean canEqual(Object other) {
        return other instanceof CleanerActionAuditMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        CleaningCategory $category = this.getCategory();
        result = result * 59 + ($category == null ? 43 : $category.hashCode());
        return result;
    }
}

