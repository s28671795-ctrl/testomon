/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.loading;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.loading.LoadingStage;
import com.incoresoft.loading.LoadingStatus;

public final class LoadingStageDTO {
    @JsonProperty(value="stage")
    @JSONField(name="stage")
    private final LoadingStage stage;
    @JsonProperty(value="status")
    @JSONField(name="status")
    private final String status;
    @JsonProperty(value="error")
    @JSONField(name="error")
    private final String error;

    public LoadingStageDTO(LoadingStage stage, LoadingStatus status, String error) {
        this.stage = stage;
        this.status = status.name().toLowerCase();
        this.error = error;
    }

    public LoadingStageDTO(LoadingStage stage, LoadingStatus status) {
        this(stage, status, null);
    }

    public LoadingStage getStage() {
        return this.stage;
    }

    public String getStatus() {
        return this.status;
    }

    public String getError() {
        return this.error;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LoadingStageDTO)) {
            return false;
        }
        LoadingStageDTO other = (LoadingStageDTO)o;
        LoadingStage this$stage = this.getStage();
        LoadingStage other$stage = other.getStage();
        if (this$stage == null ? other$stage != null : !((Object)((Object)this$stage)).equals((Object)other$stage)) {
            return false;
        }
        String this$status = this.getStatus();
        String other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) {
            return false;
        }
        String this$error = this.getError();
        String other$error = other.getError();
        return !(this$error == null ? other$error != null : !this$error.equals(other$error));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        LoadingStage $stage = this.getStage();
        result = result * 59 + ($stage == null ? 43 : ((Object)((Object)$stage)).hashCode());
        String $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        String $error = this.getError();
        result = result * 59 + ($error == null ? 43 : $error.hashCode());
        return result;
    }

    public String toString() {
        return "LoadingStageDTO(stage=" + String.valueOf((Object)this.getStage()) + ", status=" + this.getStatus() + ", error=" + this.getError() + ")";
    }
}

