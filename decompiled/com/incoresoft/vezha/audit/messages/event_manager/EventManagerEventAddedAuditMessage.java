/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.audit.messages.event_manager;

import com.incoresoft.plugin.commons.audit.template.AuditMessage;
import com.incoresoft.vezha.eventmanager.EventScheme;

public final class EventManagerEventAddedAuditMessage
extends AuditMessage {
    private final Integer id;
    private final String uuid;
    private final String title;

    public EventManagerEventAddedAuditMessage(EventScheme eventScheme) {
        this.id = eventScheme.getId();
        this.uuid = eventScheme.getUuid();
        this.title = eventScheme.getTitle();
    }

    @Override
    protected String getTemplateId() {
        return "EVENT_MANAGER_EVENT_ADDED";
    }

    public Integer getId() {
        return this.id;
    }

    public String getUuid() {
        return this.uuid;
    }

    public String getTitle() {
        return this.title;
    }

    public String toString() {
        return "EventManagerEventAddedAuditMessage(id=" + this.getId() + ", uuid=" + this.getUuid() + ", title=" + this.getTitle() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof EventManagerEventAddedAuditMessage)) {
            return false;
        }
        EventManagerEventAddedAuditMessage other = (EventManagerEventAddedAuditMessage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Integer this$id = this.getId();
        Integer other$id = other.getId();
        if (this$id == null ? other$id != null : !((Object)this$id).equals(other$id)) {
            return false;
        }
        String this$uuid = this.getUuid();
        String other$uuid = other.getUuid();
        if (this$uuid == null ? other$uuid != null : !this$uuid.equals(other$uuid)) {
            return false;
        }
        String this$title = this.getTitle();
        String other$title = other.getTitle();
        return !(this$title == null ? other$title != null : !this$title.equals(other$title));
    }

    protected boolean canEqual(Object other) {
        return other instanceof EventManagerEventAddedAuditMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        Integer $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        String $uuid = this.getUuid();
        result = result * 59 + ($uuid == null ? 43 : $uuid.hashCode());
        String $title = this.getTitle();
        result = result * 59 + ($title == null ? 43 : $title.hashCode());
        return result;
    }
}

