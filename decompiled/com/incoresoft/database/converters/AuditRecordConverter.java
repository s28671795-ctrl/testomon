/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.database.converters;

import com.incoresoft.generated.tables.records.AuditTrailRecord;
import com.incoresoft.middleware.audit.AuditEvent;
import com.incoresoft.middleware.audit.AuditEventAction;
import com.incoresoft.middleware.audit.AuditEventCategory;
import com.incoresoft.plugin.commons.audit.AuditEventImpl;
import java.util.Optional;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jooq.Converter;

public abstract class AuditRecordConverter
implements Converter<AuditTrailRecord, AuditEvent> {
    @Override
    public AuditEvent from(AuditTrailRecord record) {
        return new AuditEventImpl(record.getCreatedAt(), record.getSessionId(), Math.toIntExact(record.getUserId().intValue()), record.getUserIp(), record.getSourceId(), this.getActionById(record.getEventActionId()), record.getMessage(), Math.toIntExact(record.getClientId().intValue()));
    }

    protected abstract AuditEventAction getActionById(UUID var1);

    @Override
    public AuditTrailRecord to(AuditEvent event) {
        AuditTrailRecord record = new AuditTrailRecord();
        record.setCreatedAt(event.getTimestamp());
        record.setSessionId(event.getSessionId());
        record.setUserId(event.getUserId());
        record.setUserIp(event.getUserIp());
        record.setSourceId(event.getSourceId());
        Optional.ofNullable(event.getCategory()).map(AuditEventCategory::getId).ifPresent(record::setEventCategoryId);
        Optional.ofNullable(event.getAction()).map(AuditEventAction::getId).ifPresent(record::setEventActionId);
        record.setMessage(event.getMessage());
        record.setClientId(event.getClientId());
        return record;
    }

    @Override
    @NotNull
    public Class<AuditTrailRecord> fromType() {
        return AuditTrailRecord.class;
    }

    @Override
    @NotNull
    public Class<AuditEvent> toType() {
        return AuditEvent.class;
    }
}

