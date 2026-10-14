/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.audit;

import java.util.UUID;

public interface AuditEventCategory {
    public UUID getId();

    public String getName();

    default public String getLocalizationKey() {
        return this.getName() + "_AUDIT_CATEGORY";
    }
}

