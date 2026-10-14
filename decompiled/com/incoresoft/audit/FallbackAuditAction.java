/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.audit;

import com.incoresoft.audit.FallbackAuditEventCategory;
import com.incoresoft.middleware.audit.AuditEventAction;
import com.incoresoft.middleware.audit.AuditEventCategory;
import java.util.UUID;

public class FallbackAuditAction
implements AuditEventAction {
    private static final UUID DEFAULT_ID = new UUID(0L, 0L);

    @Override
    public UUID getId() {
        return DEFAULT_ID;
    }

    @Override
    public String getName() {
        return "FALLBACK_AUDIT_ACTION";
    }

    @Override
    public AuditEventCategory getCategory() {
        return new FallbackAuditEventCategory();
    }

    public String toString() {
        return this.getName().toLowerCase();
    }
}

