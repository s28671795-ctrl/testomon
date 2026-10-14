/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.audit;

import com.google.inject.Inject;
import com.incoresoft.Utils.Pair;
import com.incoresoft.audit.AuditEventsFactory;
import com.incoresoft.database.AuditRepository;
import com.incoresoft.http.params.AuditTrailParams;
import com.incoresoft.middleware.audit.AuditEvent;
import com.incoresoft.middleware.audit.AuditEventAction;
import com.incoresoft.middleware.audit.Auditor;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.plugin.commons.params.PaginationParams;
import com.incoresoft.plugin.commons.params.StartEndDateParams;
import io.javalin.http.Context;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BasicAuditor
implements Auditor {
    private final AuditRepository repository;
    private final UsersManager usersManager;
    private final AuditEventsFactory eventsFactory;

    @Override
    public void audit(@NotNull AuditEvent auditEvent) {
        this.repository.addAuditEvent(auditEvent);
    }

    @Override
    public void audit(Context ctx, User user, AuditEventAction action, String sourceId) {
        AuditEvent event = this.eventsFactory.create(ctx, user, action, sourceId);
        this.audit(event);
    }

    @Override
    public void audit(@Nullable Context ctx, User user, AuditEventAction action, String sourceId, Object dto) {
        AuditEvent event = this.eventsFactory.create(ctx, user, action, sourceId, dto);
        this.audit(event);
    }

    @Override
    public void audit(Context context, AuditEventAction auditEventAction, String sourceId) {
        AuditEvent event = this.eventsFactory.create(context, auditEventAction, sourceId);
        this.audit(event);
    }

    @Override
    public void audit(Context context, AuditEventAction auditEventAction, String sourceId, Object dto) {
        AuditEvent event = this.eventsFactory.create(context, auditEventAction, sourceId, dto);
        this.audit(event);
    }

    public void audit(List<AuditEvent> auditEvents) {
        this.repository.saveAll(auditEvents);
    }

    public Pair<Integer, List<AuditEvent>> getAuditEvents(@NotNull StartEndDateParams dateParams, @NotNull AuditTrailParams params, @NotNull PaginationParams paginationParams, @Nullable Integer clientId) {
        Set userIds = null;
        if (StringUtils.isNotBlank(params.getLogin())) {
            String lowerCaseLogin = params.getLogin().toLowerCase();
            userIds = this.usersManager.getAll().stream().filter(it -> it.getEmail().toLowerCase().contains(lowerCaseLogin)).map(User::getId).collect(Collectors.toUnmodifiableSet());
        }
        return this.repository.getAuditEvents(dateParams, params, paginationParams, userIds, clientId);
    }

    @Inject
    public BasicAuditor(AuditRepository repository, UsersManager usersManager, AuditEventsFactory eventsFactory) {
        this.repository = repository;
        this.usersManager = usersManager;
        this.eventsFactory = eventsFactory;
    }
}

