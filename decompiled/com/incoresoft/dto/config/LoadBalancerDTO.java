/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.incoresoft.config.models.LoadBalancerConfig;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="LoadBalancerDTO", description="Load balancer config class structure")
@JsonDeserialize(builder=LoadBalancerDTOBuilder.class)
public final class LoadBalancerDTO {
    @JsonProperty(value="check_cpu_usage")
    @Schema(name="check_cpu_usage", description="Check CPU usage", implementation=Boolean.class)
    private final Boolean checkCpuUsage;
    @JsonProperty(value="check_gpu_usage")
    @Schema(name="check_gpu_usage", description="Check GPU usage", implementation=Boolean.class)
    private final Boolean checkGpuUsage;
    @JsonProperty(value="check_nv_dec")
    @Schema(name="check_nv_dec", description="Check NV dec", implementation=Boolean.class)
    private final Boolean checkNvDec;
    @JsonProperty(value="check_server_overloaded")
    @Schema(name="check_server_overloaded", description="Check server overloaded", implementation=Boolean.class)
    private final Boolean checkServerOverloaded;
    @JsonProperty(value="check_memory")
    @Schema(name="check_memory", description="Check RAM", implementation=Boolean.class)
    private final Boolean checkMemory;

    public LoadBalancerDTO(LoadBalancerConfig loadBalancer) {
        this.checkCpuUsage = loadBalancer.isCheckCpuUsage();
        this.checkGpuUsage = loadBalancer.isCheckGpuUsage();
        this.checkNvDec = loadBalancer.isCheckNvDec();
        this.checkServerOverloaded = loadBalancer.isCheckServerOverloaded();
        this.checkMemory = loadBalancer.isCheckMemory();
    }

    public static LoadBalancerDTOBuilder builder() {
        return new LoadBalancerDTOBuilder();
    }

    public Boolean getCheckCpuUsage() {
        return this.checkCpuUsage;
    }

    public Boolean getCheckGpuUsage() {
        return this.checkGpuUsage;
    }

    public Boolean getCheckNvDec() {
        return this.checkNvDec;
    }

    public Boolean getCheckServerOverloaded() {
        return this.checkServerOverloaded;
    }

    public Boolean getCheckMemory() {
        return this.checkMemory;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LoadBalancerDTO)) {
            return false;
        }
        LoadBalancerDTO other = (LoadBalancerDTO)o;
        Boolean this$checkCpuUsage = this.getCheckCpuUsage();
        Boolean other$checkCpuUsage = other.getCheckCpuUsage();
        if (this$checkCpuUsage == null ? other$checkCpuUsage != null : !((Object)this$checkCpuUsage).equals(other$checkCpuUsage)) {
            return false;
        }
        Boolean this$checkGpuUsage = this.getCheckGpuUsage();
        Boolean other$checkGpuUsage = other.getCheckGpuUsage();
        if (this$checkGpuUsage == null ? other$checkGpuUsage != null : !((Object)this$checkGpuUsage).equals(other$checkGpuUsage)) {
            return false;
        }
        Boolean this$checkNvDec = this.getCheckNvDec();
        Boolean other$checkNvDec = other.getCheckNvDec();
        if (this$checkNvDec == null ? other$checkNvDec != null : !((Object)this$checkNvDec).equals(other$checkNvDec)) {
            return false;
        }
        Boolean this$checkServerOverloaded = this.getCheckServerOverloaded();
        Boolean other$checkServerOverloaded = other.getCheckServerOverloaded();
        if (this$checkServerOverloaded == null ? other$checkServerOverloaded != null : !((Object)this$checkServerOverloaded).equals(other$checkServerOverloaded)) {
            return false;
        }
        Boolean this$checkMemory = this.getCheckMemory();
        Boolean other$checkMemory = other.getCheckMemory();
        return !(this$checkMemory == null ? other$checkMemory != null : !((Object)this$checkMemory).equals(other$checkMemory));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Boolean $checkCpuUsage = this.getCheckCpuUsage();
        result = result * 59 + ($checkCpuUsage == null ? 43 : ((Object)$checkCpuUsage).hashCode());
        Boolean $checkGpuUsage = this.getCheckGpuUsage();
        result = result * 59 + ($checkGpuUsage == null ? 43 : ((Object)$checkGpuUsage).hashCode());
        Boolean $checkNvDec = this.getCheckNvDec();
        result = result * 59 + ($checkNvDec == null ? 43 : ((Object)$checkNvDec).hashCode());
        Boolean $checkServerOverloaded = this.getCheckServerOverloaded();
        result = result * 59 + ($checkServerOverloaded == null ? 43 : ((Object)$checkServerOverloaded).hashCode());
        Boolean $checkMemory = this.getCheckMemory();
        result = result * 59 + ($checkMemory == null ? 43 : ((Object)$checkMemory).hashCode());
        return result;
    }

    public String toString() {
        return "LoadBalancerDTO(checkCpuUsage=" + this.getCheckCpuUsage() + ", checkGpuUsage=" + this.getCheckGpuUsage() + ", checkNvDec=" + this.getCheckNvDec() + ", checkServerOverloaded=" + this.getCheckServerOverloaded() + ", checkMemory=" + this.getCheckMemory() + ")";
    }

    public LoadBalancerDTO(Boolean checkCpuUsage, Boolean checkGpuUsage, Boolean checkNvDec, Boolean checkServerOverloaded, Boolean checkMemory) {
        this.checkCpuUsage = checkCpuUsage;
        this.checkGpuUsage = checkGpuUsage;
        this.checkNvDec = checkNvDec;
        this.checkServerOverloaded = checkServerOverloaded;
        this.checkMemory = checkMemory;
    }

    @JsonPOJOBuilder(withPrefix="", buildMethodName="build")
    public static class LoadBalancerDTOBuilder {
        private Boolean checkCpuUsage;
        private Boolean checkGpuUsage;
        private Boolean checkNvDec;
        private Boolean checkServerOverloaded;
        private Boolean checkMemory;

        LoadBalancerDTOBuilder() {
        }

        @JsonProperty(value="check_cpu_usage")
        public LoadBalancerDTOBuilder checkCpuUsage(Boolean checkCpuUsage) {
            this.checkCpuUsage = checkCpuUsage;
            return this;
        }

        @JsonProperty(value="check_gpu_usage")
        public LoadBalancerDTOBuilder checkGpuUsage(Boolean checkGpuUsage) {
            this.checkGpuUsage = checkGpuUsage;
            return this;
        }

        @JsonProperty(value="check_nv_dec")
        public LoadBalancerDTOBuilder checkNvDec(Boolean checkNvDec) {
            this.checkNvDec = checkNvDec;
            return this;
        }

        @JsonProperty(value="check_server_overloaded")
        public LoadBalancerDTOBuilder checkServerOverloaded(Boolean checkServerOverloaded) {
            this.checkServerOverloaded = checkServerOverloaded;
            return this;
        }

        @JsonProperty(value="check_memory")
        public LoadBalancerDTOBuilder checkMemory(Boolean checkMemory) {
            this.checkMemory = checkMemory;
            return this;
        }

        public LoadBalancerDTO build() {
            return new LoadBalancerDTO(this.checkCpuUsage, this.checkGpuUsage, this.checkNvDec, this.checkServerOverloaded, this.checkMemory);
        }

        public String toString() {
            return "LoadBalancerDTO.LoadBalancerDTOBuilder(checkCpuUsage=" + this.checkCpuUsage + ", checkGpuUsage=" + this.checkGpuUsage + ", checkNvDec=" + this.checkNvDec + ", checkServerOverloaded=" + this.checkServerOverloaded + ", checkMemory=" + this.checkMemory + ")";
        }
    }
}

