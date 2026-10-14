/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.audit.messages.streams;

import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.plugin.commons.audit.template.update.ObjectUpdateAuditMessage;
import com.incoresoft.vezha.dto.streams.FullStreamDTO;

public final class StreamUpdatedAuditMessage
extends ObjectUpdateAuditMessage<FullStreamDTO> {
    private final int id;

    @Override
    protected String getTemplateId() {
        return "VIDEO_STREAM_UPDATED";
    }

    public StreamUpdatedAuditMessage(Stream old, Stream updated) {
        super(new FullStreamDTO(old), new FullStreamDTO(updated));
        this.id = updated.getId();
    }

    public int getId() {
        return this.id;
    }

    public String toString() {
        return "StreamUpdatedAuditMessage(id=" + this.getId() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof StreamUpdatedAuditMessage)) {
            return false;
        }
        StreamUpdatedAuditMessage other = (StreamUpdatedAuditMessage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return this.getId() == other.getId();
    }

    protected boolean canEqual(Object other) {
        return other instanceof StreamUpdatedAuditMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        result = result * 59 + this.getId();
        return result;
    }
}

