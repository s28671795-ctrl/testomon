/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.streams.streamstatus;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.model.stream.StreamStatus;

public final class MQStreamStatusDTO {
    @JSONField(name="stream_id")
    private final int streamId;
    @JSONField(name="status", serializeUsing=StreamStatus.Serializer.class, deserializeUsing=StreamStatus.Serializer.class)
    private final StreamStatus status;

    public MQStreamStatusDTO(int streamId, StreamStatus status) {
        this.streamId = streamId;
        this.status = status;
    }

    public int getStreamId() {
        return this.streamId;
    }

    public StreamStatus getStatus() {
        return this.status;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof MQStreamStatusDTO)) {
            return false;
        }
        MQStreamStatusDTO other = (MQStreamStatusDTO)o;
        if (this.getStreamId() != other.getStreamId()) {
            return false;
        }
        StreamStatus this$status = this.getStatus();
        StreamStatus other$status = other.getStatus();
        return !(this$status == null ? other$status != null : !((Object)((Object)this$status)).equals((Object)other$status));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getStreamId();
        StreamStatus $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : ((Object)((Object)$status)).hashCode());
        return result;
    }

    public String toString() {
        return "MQStreamStatusDTO(streamId=" + this.getStreamId() + ", status=" + String.valueOf((Object)this.getStatus()) + ")";
    }
}

