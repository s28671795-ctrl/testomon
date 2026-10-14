/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.params;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.AnalyticsStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public class AnalyticsParams {
    @Schema(name="matching_text", description="Text to match id or name of analytics", example="Camera", implementation=String.class)
    @JsonProperty(value="matching_text")
    @JSONField(name="matching_text")
    private String matchingText;
    @Schema(name="status", description="Analytics status", example="deleted", implementation=AnalyticsStatus.class)
    @JsonProperty(value="status")
    @JSONField(name="status")
    private AnalyticsStatus status;
    @Schema(name="show_deleted", description="Show deleted", example="true", implementation=Boolean.class)
    @JsonProperty(value="show_deleted")
    @JSONField(name="show_deleted")
    private boolean showDeleted;

    public String getMatchingText() {
        return this.matchingText;
    }

    public AnalyticsStatus getStatus() {
        return this.status;
    }

    public boolean isShowDeleted() {
        return this.showDeleted;
    }

    @JsonProperty(value="matching_text")
    public void setMatchingText(String matchingText) {
        this.matchingText = matchingText;
    }

    @JsonProperty(value="status")
    public void setStatus(AnalyticsStatus status) {
        this.status = status;
    }

    @JsonProperty(value="show_deleted")
    public void setShowDeleted(boolean showDeleted) {
        this.showDeleted = showDeleted;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AnalyticsParams)) {
            return false;
        }
        AnalyticsParams other = (AnalyticsParams)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.isShowDeleted() != other.isShowDeleted()) {
            return false;
        }
        String this$matchingText = this.getMatchingText();
        String other$matchingText = other.getMatchingText();
        if (this$matchingText == null ? other$matchingText != null : !this$matchingText.equals(other$matchingText)) {
            return false;
        }
        AnalyticsStatus this$status = this.getStatus();
        AnalyticsStatus other$status = other.getStatus();
        return !(this$status == null ? other$status != null : !((Object)((Object)this$status)).equals((Object)other$status));
    }

    protected boolean canEqual(Object other) {
        return other instanceof AnalyticsParams;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isShowDeleted() ? 79 : 97);
        String $matchingText = this.getMatchingText();
        result = result * 59 + ($matchingText == null ? 43 : $matchingText.hashCode());
        AnalyticsStatus $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : ((Object)((Object)$status)).hashCode());
        return result;
    }

    public String toString() {
        return "AnalyticsParams(matchingText=" + this.getMatchingText() + ", status=" + String.valueOf((Object)this.getStatus()) + ", showDeleted=" + this.isShowDeleted() + ")";
    }
}

