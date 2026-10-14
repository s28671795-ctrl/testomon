/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.audit.messages.streams;

import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.plugin.commons.audit.template.AuditMessage;

public final class StreamFrameUpdatedAuditMessage
extends AuditMessage {
    private final int id;
    private final String name;

    public StreamFrameUpdatedAuditMessage(Stream stream) {
        this.id = stream.getId();
        this.name = stream.getName();
    }

    @Override
    protected String getTemplateId() {
        return "VIDEO_STREAM_FRAME_UPDATED";
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return "StreamFrameUpdatedAuditMessage(id=" + this.getId() + ", name=" + this.getName() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof StreamFrameUpdatedAuditMessage)) {
            return false;
        }
        StreamFrameUpdatedAuditMessage other = (StreamFrameUpdatedAuditMessage)o;
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
        return other instanceof StreamFrameUpdatedAuditMessage;
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

