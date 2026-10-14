/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.audit.deserializers;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.incoresoft.audit.AuditEventActionsManager;
import com.incoresoft.audit.FallbackAuditAction;
import java.lang.reflect.Type;

public class AuditEventActionDeserializer
implements ObjectDeserializer {
    private final AuditEventActionsManager auditEventActionsManager;

    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        String value = parser.parseObject(String.class);
        if (value == null) {
            return null;
        }
        return (T)this.auditEventActionsManager.getAction(value.toUpperCase()).orElseGet(FallbackAuditAction::new);
    }

    @Override
    public int getFastMatchToken() {
        return 4;
    }

    public AuditEventActionDeserializer(AuditEventActionsManager auditEventActionsManager) {
        this.auditEventActionsManager = auditEventActionsManager;
    }
}

