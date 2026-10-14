/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.audit;

import com.incoresoft.middleware.audit.AuditEventCategory;
import java.util.UUID;

public enum CoreAuditEventCategory implements AuditEventCategory
{
    MARKETPLACE_MANAGEMENT("286a0550-b2e6-43b7-b7a8-059278020832"),
    USER_MANAGEMENT("2bc7a9f8-3e0d-4671-9524-f513e8e51ad3"),
    LICENSE_MANAGEMENT("faa5dbb2-af7e-4d4b-8128-001d9e43d275"),
    SYSTEM_SETTINGS_MANAGEMENT("bbdcfc00-ad75-40b5-be71-4e0f135b78cc");

    private final UUID id;

    private CoreAuditEventCategory(UUID id) {
        this.id = id;
    }

    private CoreAuditEventCategory(String id) {
        this.id = UUID.fromString(id);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name();
    }

    public String toString() {
        return this.getName().toLowerCase();
    }
}

