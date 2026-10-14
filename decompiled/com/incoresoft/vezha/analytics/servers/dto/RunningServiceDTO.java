/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.servers.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.vezha.analytics.servers.models.RunningService;
import io.swagger.v3.oas.annotations.media.Schema;
import org.jetbrains.annotations.NotNull;

@Schema(name="RunningServiceDTO", description="Running service class structure")
public final class RunningServiceDTO {
    @JSONField(name="type")
    @Schema(name="type", description="Analytics type", implementation=String.class, example="alpr")
    private final String type;
    @JSONField(name="ready")
    @Schema(name="ready", description="Determines whether service is able to host analytics", implementation=Boolean.class, example="true")
    private final boolean isReady;

    public RunningServiceDTO(@NotNull RunningService runningService) {
        this.type = runningService.getType();
        this.isReady = runningService.isReady();
    }

    public String getType() {
        return this.type;
    }

    public boolean isReady() {
        return this.isReady;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RunningServiceDTO)) {
            return false;
        }
        RunningServiceDTO other = (RunningServiceDTO)o;
        if (this.isReady() != other.isReady()) {
            return false;
        }
        String this$type = this.getType();
        String other$type = other.getType();
        return !(this$type == null ? other$type != null : !this$type.equals(other$type));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isReady() ? 79 : 97);
        String $type = this.getType();
        result = result * 59 + ($type == null ? 43 : $type.hashCode());
        return result;
    }

    public String toString() {
        return "RunningServiceDTO(type=" + this.getType() + ", isReady=" + this.isReady() + ")";
    }

    public RunningServiceDTO(String type, boolean isReady) {
        this.type = type;
        this.isReady = isReady;
    }
}

