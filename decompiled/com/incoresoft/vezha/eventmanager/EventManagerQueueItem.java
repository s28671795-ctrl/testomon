/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.eventmanager;

import com.alibaba.fastjson.JSONObject;
import com.incoresoft.middleware.eventmanager.Event;
import java.time.Instant;

public final class EventManagerQueueItem {
    private final Instant timestamp;
    private final Event event;
    private final JSONObject message;

    public String getEventId() {
        return this.event.getId();
    }

    public EventManagerQueueItem(Instant timestamp, Event event, JSONObject message) {
        this.timestamp = timestamp;
        this.event = event;
        this.message = message;
    }

    public Event getEvent() {
        return this.event;
    }

    public JSONObject getMessage() {
        return this.message;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof EventManagerQueueItem)) {
            return false;
        }
        EventManagerQueueItem other = (EventManagerQueueItem)o;
        Instant this$timestamp = this.getTimestamp();
        Instant other$timestamp = other.getTimestamp();
        if (this$timestamp == null ? other$timestamp != null : !((Object)this$timestamp).equals(other$timestamp)) {
            return false;
        }
        Event this$event = this.getEvent();
        Event other$event = other.getEvent();
        if (this$event == null ? other$event != null : !this$event.equals(other$event)) {
            return false;
        }
        JSONObject this$message = this.getMessage();
        JSONObject other$message = other.getMessage();
        return !(this$message == null ? other$message != null : !((Object)this$message).equals(other$message));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Instant $timestamp = this.getTimestamp();
        result = result * 59 + ($timestamp == null ? 43 : ((Object)$timestamp).hashCode());
        Event $event = this.getEvent();
        result = result * 59 + ($event == null ? 43 : $event.hashCode());
        JSONObject $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : ((Object)$message).hashCode());
        return result;
    }

    public String toString() {
        return "EventManagerQueueItem(timestamp=" + String.valueOf(this.getTimestamp()) + ", event=" + String.valueOf(this.getEvent()) + ", message=" + String.valueOf(this.getMessage()) + ")";
    }

    public Instant getTimestamp() {
        return this.timestamp;
    }
}

