/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.audit.deserializers;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.google.inject.Inject;
import com.incoresoft.audit.AuditEventActionsManager;
import com.incoresoft.audit.FallbackAuditEventCategory;
import java.lang.reflect.Type;

public class AuditEventCategoryDeserializer
implements ObjectDeserializer {
    private final AuditEventActionsManager auditEventActionsManager;

    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        String value = parser.parseObject(String.class);
        if (value == null) {
            return null;
        }
        return (T)this.auditEventActionsManager.getCategory(value.toUpperCase()).orElseGet(FallbackAuditEventCategory::new);
    }

    @Override
    public int getFastMatchToken() {
        return 4;
    }

    @Inject
    public AuditEventCategoryDeserializer(AuditEventActionsManager auditEventActionsManager) {
        this.auditEventActionsManager = auditEventActionsManager;
    }
}

