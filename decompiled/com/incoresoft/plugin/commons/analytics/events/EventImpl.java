/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.analytics.events;

import com.incoresoft.middleware.analytics.dto.events.EventDTO;
import com.incoresoft.middleware.eventmanager.Event;
import java.util.Map;

public class EventImpl
implements Event {
    private String id;
    private Map<String, String> params;

    public EventImpl(EventDTO dto) {
        this.id = dto.getId();
        this.params = dto.getParams();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Map<String, String> getParams() {
        return this.params;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof EventImpl)) {
            return false;
        }
        EventImpl other = (EventImpl)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$id = this.getId();
        String other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) {
            return false;
        }
        Map<String, String> this$params = this.getParams();
        Map<String, String> other$params = other.getParams();
        return !(this$params == null ? other$params != null : !((Object)this$params).equals(other$params));
    }

    protected boolean canEqual(Object other) {
        return other instanceof EventImpl;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        Map<String, String> $params = this.getParams();
        result = result * 59 + ($params == null ? 43 : ((Object)$params).hashCode());
        return result;
    }

    public String toString() {
        return "EventImpl(id=" + this.getId() + ", params=" + String.valueOf(this.getParams()) + ")";
    }

    public EventImpl(String id, Map<String, String> params) {
        this.id = id;
        this.params = params;
    }
}

