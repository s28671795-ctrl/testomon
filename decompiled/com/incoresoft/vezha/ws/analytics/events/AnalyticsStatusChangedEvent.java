/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.ws.analytics.events;

import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsStatus;
import com.incoresoft.vezha.ws.analytics.events.AnalyticsEventType;

public class AnalyticsStatusChangedEvent {
    private AnalyticsStatus status;
    private int id;
    private int clientId;

    public AnalyticsStatusChangedEvent(Analytics analytics) {
        this.status = analytics.getStatus();
        this.id = analytics.getId();
        this.clientId = analytics.getClientId();
    }

    public String getType() {
        return AnalyticsEventType.STATUS_CHANGED.toString();
    }

    public AnalyticsStatus getStatus() {
        return this.status;
    }

    public int getId() {
        return this.id;
    }

    public int getClientId() {
        return this.clientId;
    }

    public void setStatus(AnalyticsStatus status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AnalyticsStatusChangedEvent)) {
            return false;
        }
        AnalyticsStatusChangedEvent other = (AnalyticsStatusChangedEvent)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getId() != other.getId()) {
            return false;
        }
        if (this.getClientId() != other.getClientId()) {
            return false;
        }
        AnalyticsStatus this$status = this.getStatus();
        AnalyticsStatus other$status = other.getStatus();
        return !(this$status == null ? other$status != null : !((Object)((Object)this$status)).equals((Object)other$status));
    }

    protected boolean canEqual(Object other) {
        return other instanceof AnalyticsStatusChangedEvent;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getId();
        result = result * 59 + this.getClientId();
        AnalyticsStatus $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : ((Object)((Object)$status)).hashCode());
        return result;
    }

    public String toString() {
        return "AnalyticsStatusChangedEvent(status=" + String.valueOf((Object)this.getStatus()) + ", id=" + this.getId() + ", clientId=" + this.getClientId() + ")";
    }

    public AnalyticsStatusChangedEvent() {
    }
}

