/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.vms.systemlogs;

import com.incoresoft.middleware.vms.systemlogs.SystemLogEventAction;
import com.incoresoft.middleware.vms.systemlogs.SystemLogEventCategory;
import com.incoresoft.middleware.vms.systemlogs.SystemLogLevel;
import java.time.Instant;

public interface SystemLogEvent {
    public Instant getTimestamp();

    public SystemLogLevel getLevel();

    public String getSourceId();

    public SystemLogEventCategory getCategory();

    public SystemLogEventAction getAction();

    public Integer getStreamId();

    public Integer getAnalyticsId();

    public String getMessage();
}

