/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.audit;

import com.incoresoft.middleware.audit.AuditEventCategory;
import java.util.UUID;

public enum VezhaAuditEventCategory implements AuditEventCategory
{
    STREAMS_MANAGEMENT("3f95df05-0af2-402b-a54a-8c4dc0969920"),
    EVENT_MANAGEMENT("088b3bbd-431a-49d2-836b-85447e3b0dfe"),
    ANALYTICS_GROUPS_MANAGEMENT("64f493e9-8ac8-49f0-9fa6-aa7f17dccc4a");

    private static final String LOCALIZATION_SUFFIX = "_AUDIT_CATEGORY";
    private final UUID id;

    private VezhaAuditEventCategory(UUID id) {
        this.id = id;
    }

    private VezhaAuditEventCategory(String id) {
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

    @Override
    public String getLocalizationKey() {
        return this.name() + LOCALIZATION_SUFFIX;
    }

    public String toString() {
        return this.name().toLowerCase();
    }
}

