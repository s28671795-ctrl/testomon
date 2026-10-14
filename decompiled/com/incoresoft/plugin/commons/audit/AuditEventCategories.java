/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.audit;

import com.incoresoft.middleware.audit.AuditEventCategory;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public enum AuditEventCategories implements AuditEventCategory
{
    ANALYTICS_MANAGEMENT("ccde8195-6cd2-40e9-be41-c6b0d5a8b9e5"),
    PLUGIN_MANAGEMENT("35a9ea9f-164b-4124-ae35-2637a3ab1d97"),
    FILE_AND_REPORT_MANAGEMENT("f7f4404a-c12c-4cbe-89c1-9d632542ee7d"),
    NOTIFICATION_MANAGEMENT("3b7f0976-5b24-43eb-bbd2-e9dcf1d2c252"),
    OTHER("62170477-6eac-4c89-9b8c-4507453115b9");

    private static final Map<UUID, AuditEventCategories> categoriesMap;
    private final UUID id;

    public static AuditEventCategories getById(UUID id) {
        return categoriesMap.get(id);
    }

    private AuditEventCategories(UUID id) {
        this.id = id;
    }

    private AuditEventCategories(String id) {
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
        return this.name().toLowerCase();
    }

    static {
        HashMap<UUID, AuditEventCategories> tempMap = new HashMap<UUID, AuditEventCategories>();
        for (AuditEventCategories category : AuditEventCategories.values()) {
            if (tempMap.containsKey(category.getId())) {
                throw new RuntimeException("duplicate category id");
            }
            tempMap.put(category.getId(), category);
        }
        categoriesMap = Collections.unmodifiableMap(tempMap);
    }
}

