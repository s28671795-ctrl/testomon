/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.streams.status;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StreamStatusDTO {
    @JSONField(name="method")
    @JsonProperty(value="method")
    private String method;
    @JSONField(name="stream_id")
    @JsonProperty(value="stream_id")
    private int streamId;
    @JSONField(name="online")
    @JsonProperty(value="online")
    private boolean online;

    public StreamStatusDTO(int streamId, boolean online) {
        this("stream_status_changed", streamId, online);
    }

    public String getMethod() {
        return this.method;
    }

    public int getStreamId() {
        return this.streamId;
    }

    public boolean isOnline() {
        return this.online;
    }

    @JsonProperty(value="method")
    public void setMethod(String method2) {
        this.method = method2;
    }

    @JsonProperty(value="stream_id")
    public void setStreamId(int streamId) {
        this.streamId = streamId;
    }

    @JsonProperty(value="online")
    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof StreamStatusDTO)) {
            return false;
        }
        StreamStatusDTO other = (StreamStatusDTO)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getStreamId() != other.getStreamId()) {
            return false;
        }
        if (this.isOnline() != other.isOnline()) {
            return false;
        }
        String this$method = this.getMethod();
        String other$method = other.getMethod();
        return !(this$method == null ? other$method != null : !this$method.equals(other$method));
    }

    protected boolean canEqual(Object other) {
        return other instanceof StreamStatusDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getStreamId();
        result = result * 59 + (this.isOnline() ? 79 : 97);
        String $method = this.getMethod();
        result = result * 59 + ($method == null ? 43 : $method.hashCode());
        return result;
    }

    public String toString() {
        return "StreamStatusDTO(method=" + this.getMethod() + ", streamId=" + this.getStreamId() + ", online=" + this.isOnline() + ")";
    }

    public StreamStatusDTO() {
    }

    public StreamStatusDTO(String method2, int streamId, boolean online) {
        this.method = method2;
        this.streamId = streamId;
        this.online = online;
    }
}

