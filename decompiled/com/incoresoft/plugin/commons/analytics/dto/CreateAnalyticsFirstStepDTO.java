/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.analytics.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.plugin.commons.analytics.dto.validations.StreamIdValidatable;
import com.incoresoft.plugin.commons.analytics.dto.validations.StreamShouldExist;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;

@StreamShouldExist
public class CreateAnalyticsFirstStepDTO
implements StreamIdValidatable {
    @JSONField(name="stream_id")
    @JsonProperty(value="stream_id")
    @Schema(name="stream_id", description="Stream ID Field", example="1", requiredMode=Schema.RequiredMode.REQUIRED, implementation=Integer.class)
    @NotNull
    private Integer streamId;

    @Override
    public Integer getStreamId() {
        return this.streamId;
    }

    @JsonProperty(value="stream_id")
    public void setStreamId(Integer streamId) {
        this.streamId = streamId;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CreateAnalyticsFirstStepDTO)) {
            return false;
        }
        CreateAnalyticsFirstStepDTO other = (CreateAnalyticsFirstStepDTO)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Integer this$streamId = this.getStreamId();
        Integer other$streamId = other.getStreamId();
        return !(this$streamId == null ? other$streamId != null : !((Object)this$streamId).equals(other$streamId));
    }

    protected boolean canEqual(Object other) {
        return other instanceof CreateAnalyticsFirstStepDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Integer $streamId = this.getStreamId();
        result = result * 59 + ($streamId == null ? 43 : ((Object)$streamId).hashCode());
        return result;
    }

    public String toString() {
        return "CreateAnalyticsFirstStepDTO(streamId=" + this.getStreamId() + ")";
    }
}

