/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.audit;

import com.google.inject.Inject;
import com.incoresoft.middleware.audit.AuditEvent;
import com.incoresoft.middleware.audit.AuditEventAction;
import com.incoresoft.middleware.http.serialization.Serialization;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.plugin.commons.audit.AuditEventBuilder;
import com.incoresoft.plugin.commons.audit.template.AuditMessage;
import io.javalin.http.Context;
import org.jetbrains.annotations.Nullable;

public class AuditEventsFactory {
    private final UsersManager usersManager;

    public AuditEvent create(@Nullable Context ctx, User user, AuditEventAction action, String sourceId, Object dto) {
        Object object;
        AuditEventBuilder builder = new AuditEventBuilder();
        if (ctx != null) {
            builder.setContext(ctx);
        }
        if (dto instanceof AuditMessage) {
            AuditMessage auditMessage = (AuditMessage)dto;
            object = auditMessage.build();
        } else {
            object = dto;
        }
        Object message = object;
        return builder.setUserId(user.getId()).setClientId(user.getClientId()).setAction(action).setSourceId(sourceId).setMessage(Serialization.writeValueAsString(message)).build();
    }

    public AuditEvent create(Context context, AuditEventAction auditEventAction, String sourceId, Object dto) {
        return this.create(context, this.usersManager.getUser(context), auditEventAction, sourceId, dto);
    }

    public AuditEvent create(Context ctx, User user, AuditEventAction action, String sourceId) {
        return this.create(ctx, user, action, sourceId, "");
    }

    public AuditEvent create(Context context, AuditEventAction auditEventAction, String sourceId) {
        return this.create(context, this.usersManager.getUser(context), auditEventAction, sourceId);
    }

    @Inject
    public AuditEventsFactory(UsersManager usersManager) {
        this.usersManager = usersManager;
    }
}

