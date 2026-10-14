/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.audit;

import com.incoresoft.middleware.audit.AuditEvent;
import com.incoresoft.middleware.audit.AuditEventAction;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.plugin.commons.audit.AuditEventImpl;
import io.javalin.http.Context;
import java.time.Instant;
import org.jetbrains.annotations.NotNull;

public class AsyncAuditEventBuilder {
    private final String sessionId;
    private final String userIp;
    private Instant timestamp;
    private int userId;
    private String sourceId;
    private AuditEventAction action = AuditEventImpl.DEFAULT_ACTION;
    private String message = "";
    private int clientId;

    public AsyncAuditEventBuilder setUser(@NotNull User user) {
        return this.setUserId(user.getId()).setClientId(user.getClientId());
    }

    public AsyncAuditEventBuilder(@NotNull Context ctx) {
        this.userIp = ctx.ip();
        this.sessionId = this.getSessionId(ctx);
    }

    @NotNull
    private String getSessionId(@NotNull Context ctx) {
        String sid = ctx.req().getSession().getId();
        return sid != null ? sid.substring(sid.lastIndexOf(45) + 1) : "API Token";
    }

    public AuditEvent build() {
        return new AuditEventImpl(this.timestamp, this.sessionId, this.userId, this.userIp, this.sourceId, this.action, this.message, this.clientId);
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public String getUserIp() {
        return this.userIp;
    }

    public Instant getTimestamp() {
        return this.timestamp;
    }

    public int getUserId() {
        return this.userId;
    }

    public String getSourceId() {
        return this.sourceId;
    }

    public AuditEventAction getAction() {
        return this.action;
    }

    public String getMessage() {
        return this.message;
    }

    public int getClientId() {
        return this.clientId;
    }

    public AsyncAuditEventBuilder setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public AsyncAuditEventBuilder setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public AsyncAuditEventBuilder setSourceId(String sourceId) {
        this.sourceId = sourceId;
        return this;
    }

    public AsyncAuditEventBuilder setAction(AuditEventAction action) {
        this.action = action;
        return this;
    }

    public AsyncAuditEventBuilder setMessage(String message) {
        this.message = message;
        return this;
    }

    public AsyncAuditEventBuilder setClientId(int clientId) {
        this.clientId = clientId;
        return this;
    }
}

