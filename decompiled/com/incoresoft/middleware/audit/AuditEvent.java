/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.audit;

import com.incoresoft.middleware.audit.AuditEventAction;
import com.incoresoft.middleware.audit.AuditEventCategory;
import java.time.Instant;

public interface AuditEvent {
    public Instant getTimestamp();

    public String getSessionId();

    public int getUserId();

    public String getUserIp();

    public String getSourceId();

    public AuditEventCategory getCategory();

    public AuditEventAction getAction();

    public String getMessage();

    public int getClientId();
}

