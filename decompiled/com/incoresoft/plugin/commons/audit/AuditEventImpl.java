/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.audit;

import com.incoresoft.middleware.audit.AuditEvent;
import com.incoresoft.middleware.audit.AuditEventAction;
import com.incoresoft.middleware.audit.AuditEventCategory;
import com.incoresoft.plugin.commons.audit.AuditEventActions;
import java.time.Instant;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AuditEventImpl
implements AuditEvent {
    public static final String DEFAULT_MESSAGE = "";
    public static final AuditEventAction DEFAULT_ACTION = AuditEventActions.OTHER;
    private final Instant timestamp;
    private final String sessionId;
    private final int userId;
    private final String userIp;
    private final String sourceId;
    private final AuditEventAction action;
    private final String message;
    private final int clientId;

    public AuditEventImpl(@NotNull Instant timestamp, @Nullable String sessionId, int userId, @Nullable String userIp, @NotNull String sourceId, @Nullable AuditEventAction action, @Nullable String message, int clientId) {
        this.timestamp = Objects.requireNonNullElseGet(timestamp, Instant::now);
        this.sessionId = sessionId;
        this.userId = userId;
        this.userIp = userIp;
        this.sourceId = Objects.requireNonNull(sourceId);
        this.action = action;
        this.message = message;
        this.clientId = clientId;
    }

    @Override
    public AuditEventCategory getCategory() {
        return this.action.getCategory();
    }

    @Override
    public Instant getTimestamp() {
        return this.timestamp;
    }

    @Override
    public String getSessionId() {
        return this.sessionId;
    }

    @Override
    public int getUserId() {
        return this.userId;
    }

    @Override
    public String getUserIp() {
        return this.userIp;
    }

    @Override
    public String getSourceId() {
        return this.sourceId;
    }

    @Override
    public AuditEventAction getAction() {
        return this.action;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public int getClientId() {
        return this.clientId;
    }
}

