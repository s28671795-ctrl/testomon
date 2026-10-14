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

public class AuditEventBuilder {
    private Instant timestamp;
    private String sessionId;
    private int userId;
    private String userIp;
    private String sourceId;
    private AuditEventAction action = AuditEventImpl.DEFAULT_ACTION;
    private String message = "";
    private int clientId;

    public AuditEventBuilder setUser(@NotNull User user) {
        return this.setUserId(user.getId()).setClientId(user.getClientId());
    }

    public AuditEventBuilder setContext(@NotNull Context ctx) {
        return this.setUserIp(ctx.ip()).setSessionId(this.getSessionId(ctx));
    }

    @NotNull
    private String getSessionId(@NotNull Context ctx) {
        String sid = ctx.req().getSession().getId();
        if (sid != null) {
            return sid.substring(sid.lastIndexOf(45) + 1);
        }
        return "API Token";
    }

    public AuditEvent build() {
        return new AuditEventImpl(this.timestamp, this.sessionId, this.userId, this.userIp, this.sourceId, this.action, this.message, this.clientId);
    }

    public Instant getTimestamp() {
        return this.timestamp;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public int getUserId() {
        return this.userId;
    }

    public String getUserIp() {
        return this.userIp;
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

    public AuditEventBuilder setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public AuditEventBuilder setSessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public AuditEventBuilder setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public AuditEventBuilder setUserIp(String userIp) {
        this.userIp = userIp;
        return this;
    }

    public AuditEventBuilder setSourceId(String sourceId) {
        this.sourceId = sourceId;
        return this;
    }

    public AuditEventBuilder setAction(AuditEventAction action) {
        this.action = action;
        return this;
    }

    public AuditEventBuilder setMessage(String message) {
        this.message = message;
        return this;
    }

    public AuditEventBuilder setClientId(int clientId) {
        this.clientId = clientId;
        return this;
    }
}

