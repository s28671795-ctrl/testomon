/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.audit;

import com.incoresoft.middleware.audit.AuditEventCategory;
import java.util.UUID;

public interface AuditEventAction {
    public UUID getId();

    public String getName();

    public AuditEventCategory getCategory();

    default public String getLocalizationKey() {
        return this.getName() + "_AUDIT_ACTION";
    }
}

