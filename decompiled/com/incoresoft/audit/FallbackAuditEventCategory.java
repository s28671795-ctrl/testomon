/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.audit;

import com.incoresoft.middleware.audit.AuditEventCategory;
import java.util.UUID;

public class FallbackAuditEventCategory
implements AuditEventCategory {
    private static final UUID DEFAULT_ID = new UUID(0L, 0L);

    @Override
    public UUID getId() {
        return DEFAULT_ID;
    }

    @Override
    public String getName() {
        return "FALLBACK_AUDIT_EVENT_CATEGORY";
    }

    public String toString() {
        return this.getName().toLowerCase();
    }
}

