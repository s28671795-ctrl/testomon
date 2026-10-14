/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.audit.messages.streams;

import com.incoresoft.plugin.commons.audit.template.update.ObjectUpdateAuditMessage;
import com.incoresoft.vezha.dto.streams.StreamGroupDTO;
import com.incoresoft.vezha.models.streams.groups.StreamGroup;

public final class StreamGroupUpdatedAuditMessage
extends ObjectUpdateAuditMessage<StreamGroupDTO> {
    private final int id;

    @Override
    protected String getTemplateId() {
        return "VIDEO_STREAM_GROUP_UPDATED";
    }

    public StreamGroupUpdatedAuditMessage(StreamGroup old, StreamGroup updated) {
        super(new StreamGroupDTO(old), new StreamGroupDTO(updated));
        this.id = updated.getId();
    }

    public int getId() {
        return this.id;
    }

    public String toString() {
        return "StreamGroupUpdatedAuditMessage(id=" + this.getId() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof StreamGroupUpdatedAuditMessage)) {
            return false;
        }
        StreamGroupUpdatedAuditMessage other = (StreamGroupUpdatedAuditMessage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return this.getId() == other.getId();
    }

    protected boolean canEqual(Object other) {
        return other instanceof StreamGroupUpdatedAuditMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        result = result * 59 + this.getId();
        return result;
    }
}

