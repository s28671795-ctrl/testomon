/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.audit.messages.event_manager;

import com.incoresoft.plugin.commons.audit.template.update.ObjectUpdateAuditMessage;
import com.incoresoft.vezha.dto.eventmanager.EventSchemeDTO;
import com.incoresoft.vezha.eventmanager.EventScheme;

public final class EventManagerEventUpdatedAuditMessage
extends ObjectUpdateAuditMessage<EventSchemeDTO> {
    private final Integer id;
    private final String uuid;

    @Override
    protected String getTemplateId() {
        return "EVENT_MANAGER_EVENT_UPDATED";
    }

    public EventManagerEventUpdatedAuditMessage(EventScheme old, EventScheme updated) {
        super(new EventSchemeDTO(old), new EventSchemeDTO(updated));
        this.id = updated.getId();
        this.uuid = updated.getUuid();
    }

    public Integer getId() {
        return this.id;
    }

    public String getUuid() {
        return this.uuid;
    }

    public String toString() {
        return "EventManagerEventUpdatedAuditMessage(id=" + this.getId() + ", uuid=" + this.getUuid() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof EventManagerEventUpdatedAuditMessage)) {
            return false;
        }
        EventManagerEventUpdatedAuditMessage other = (EventManagerEventUpdatedAuditMessage)o;
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
        return !(this$uuid == null ? other$uuid != null : !this$uuid.equals(other$uuid));
    }

    protected boolean canEqual(Object other) {
        return other instanceof EventManagerEventUpdatedAuditMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        Integer $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        String $uuid = this.getUuid();
        result = result * 59 + ($uuid == null ? 43 : $uuid.hashCode());
        return result;
    }
}

