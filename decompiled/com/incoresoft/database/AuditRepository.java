/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.database;

import com.google.inject.Inject;
import com.incoresoft.Utils.CollectionUtils;
import com.incoresoft.Utils.Pair;
import com.incoresoft.database.converters.AuditRecordConverter;
import com.incoresoft.generated.tables.AuditTrail;
import com.incoresoft.generated.tables.records.AuditTrailRecord;
import com.incoresoft.http.params.AuditTrailParams;
import com.incoresoft.middleware.audit.AuditEvent;
import com.incoresoft.middleware.audit.AuditEventAction;
import com.incoresoft.middleware.audit.AuditEventCategory;
import com.incoresoft.plugin.commons.params.PaginationParams;
import com.incoresoft.plugin.commons.params.StartEndDateParams;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Condition;
import org.jooq.DSLContext;

public class AuditRepository {
    private final DSLContext create;
    private final AuditRecordConverter converter;

    public void addAuditEvent(@NotNull AuditEvent event) {
        AuditTrailRecord record = this.create.newRecord(AuditTrail.AUDIT_TRAIL);
        record.from((Object)this.converter.to(event));
        record.insert();
    }

    public void saveAll(List<AuditEvent> events) {
        List<AuditTrailRecord> records = events.stream().map(this.converter::to).toList();
        this.create.batchInsert(records).execute();
    }

    public Pair<Integer, List<AuditEvent>> getAuditEvents(@NotNull StartEndDateParams dateParams, @NotNull AuditTrailParams auditTrailParams, @NotNull PaginationParams paginationParams, @Nullable Set<Integer> userIds, @Nullable Integer clientId) {
        int count;
        LinkedList<Condition> conditions = new LinkedList<Condition>();
        Optional.ofNullable(clientId).map(AuditTrail.AUDIT_TRAIL.CLIENT_ID::eq).ifPresent(conditions::add);
        Optional.ofNullable(dateParams.getStartDate()).map(AuditTrail.AUDIT_TRAIL.CREATED_AT::ge).ifPresent(conditions::add);
        Optional.ofNullable(dateParams.getEndDate()).map(AuditTrail.AUDIT_TRAIL.CREATED_AT::le).ifPresent(conditions::add);
        if (CollectionUtils.isNotEmpty(auditTrailParams.getActions())) {
            conditions.add(AuditTrail.AUDIT_TRAIL.EVENT_ACTION_ID.in((Collection<?>)auditTrailParams.getActions().stream().map(AuditEventAction::getId).toList()));
        }
        if (CollectionUtils.isNotEmpty(auditTrailParams.getCategories())) {
            conditions.add(AuditTrail.AUDIT_TRAIL.EVENT_CATEGORY_ID.in((Collection<?>)auditTrailParams.getCategories().stream().map(AuditEventCategory::getId).toList()));
        }
        if (userIds != null) {
            conditions.add(AuditTrail.AUDIT_TRAIL.USER_ID.in((Collection<?>)userIds.stream().map(Integer::longValue).toList()));
        }
        if ((count = this.create.fetchCount(AuditTrail.AUDIT_TRAIL, conditions)) <= 0) {
            return new Pair<Integer, List<AuditEvent>>(0, Collections.emptyList());
        }
        return new Pair<Integer, List<AuditEvent>>(count, this.create.selectFrom(AuditTrail.AUDIT_TRAIL).where(conditions).orderBy(AuditTrail.AUDIT_TRAIL.CREATED_AT.desc()).offset(paginationParams.getOffset()).limit(paginationParams.getLimit().orElse(100)).fetch().map(this.converter::from).stream().toList());
    }

    @Inject
    public AuditRepository(DSLContext create, AuditRecordConverter converter) {
        this.create = create;
        this.converter = converter;
    }
}

