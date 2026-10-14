/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.audit.messages.streams;

import com.incoresoft.plugin.commons.audit.template.AuditMessage;
import com.incoresoft.vezha.models.streams.groups.StreamGroup;

public final class StreamGroupDeletedAuditMessage
extends AuditMessage {
    private final int id;
    private final String name;

    public StreamGroupDeletedAuditMessage(StreamGroup streamGroup) {
        this.id = streamGroup.getId();
        this.name = streamGroup.getName();
    }

    @Override
    protected String getTemplateId() {
        return "VIDEO_STREAM_GROUP_DELETED";
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return "StreamGroupDeletedAuditMessage(id=" + this.getId() + ", name=" + this.getName() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof StreamGroupDeletedAuditMessage)) {
            return false;
        }
        StreamGroupDeletedAuditMessage other = (StreamGroupDeletedAuditMessage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        if (this.getId() != other.getId()) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        return !(this$name == null ? other$name != null : !this$name.equals(other$name));
    }

    protected boolean canEqual(Object other) {
        return other instanceof StreamGroupDeletedAuditMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        result = result * 59 + this.getId();
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        return result;
    }
}

