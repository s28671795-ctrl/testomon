/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.audit;

import com.incoresoft.middleware.audit.AuditEventAction;
import com.incoresoft.middleware.audit.AuditEventCategory;
import com.incoresoft.plugin.commons.audit.AuditEventCategories;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public enum AuditEventActions implements AuditEventAction
{
    ANALYTICS_ADDED((AuditEventCategory)AuditEventCategories.ANALYTICS_MANAGEMENT, "78824fdd-3499-40a8-83b5-e1d11c08d64b"),
    ANALYTICS_UPDATED((AuditEventCategory)AuditEventCategories.ANALYTICS_MANAGEMENT, "7307b146-3220-4ac4-9f32-08eb22178ed1"),
    ANALYTICS_DELETED((AuditEventCategory)AuditEventCategories.ANALYTICS_MANAGEMENT, "ea63fa89-e0cf-475e-84cd-ca63d3b9133f"),
    ANALYTICS_VIEWED((AuditEventCategory)AuditEventCategories.ANALYTICS_MANAGEMENT, "3d043ba1-753e-4f5b-bb96-6c7b04a9163e"),
    ANALYTICS_STARTED((AuditEventCategory)AuditEventCategories.ANALYTICS_MANAGEMENT, "d4f086a8-dc2f-4055-9336-fd7a01eae78a"),
    ANALYTICS_STOPPED((AuditEventCategory)AuditEventCategories.ANALYTICS_MANAGEMENT, "b958e4f6-fdbc-4a56-acd3-85584986cc81"),
    FILE_EXPORTED((AuditEventCategory)AuditEventCategories.FILE_AND_REPORT_MANAGEMENT, "dfcdc40d-5cac-4258-aaf9-25eff2e07225"),
    FILE_IMPORTED((AuditEventCategory)AuditEventCategories.FILE_AND_REPORT_MANAGEMENT, "91e2e2ec-d096-4210-8187-9fe4fe0b1c9f"),
    OTHER((AuditEventCategory)AuditEventCategories.OTHER, "d04fb050-ec17-49d6-b524-50044f24ceab");

    private static final Map<UUID, AuditEventActions> actionsMap;
    private final UUID id;
    private final AuditEventCategory category;

    public static AuditEventActions getById(UUID id) {
        return actionsMap.get(id);
    }

    private AuditEventActions(AuditEventCategory category, UUID id) {
        this.id = id;
        this.category = Objects.requireNonNull(category);
    }

    private AuditEventActions(AuditEventCategory category, String id) {
        this.id = UUID.fromString(id);
        this.category = Objects.requireNonNull(category);
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
    public AuditEventCategory getCategory() {
        return this.category;
    }

    public String toString() {
        return this.name().toLowerCase();
    }

    static {
        HashMap<UUID, AuditEventActions> tempMap = new HashMap<UUID, AuditEventActions>();
        for (AuditEventActions category : AuditEventActions.values()) {
            if (tempMap.containsKey(category.getId())) {
                throw new RuntimeException("duplicate action id");
            }
            tempMap.put(category.getId(), category);
        }
        actionsMap = Collections.unmodifiableMap(tempMap);
    }
}

