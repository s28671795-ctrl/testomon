/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.mq.configs;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;

@JsonDeserialize(builder=MQPlacementDTOBuilder.class)
public final class MQPlacementDTO {
    @JsonProperty(value="cluster")
    @JSONField(name="cluster")
    private final String cluster;
    @JsonProperty(value="tags")
    @JSONField(name="tags")
    private final List<String> tags;

    MQPlacementDTO(String cluster, List<String> tags) {
        this.cluster = cluster;
        this.tags = tags;
    }

    public static MQPlacementDTOBuilder builder() {
        return new MQPlacementDTOBuilder();
    }

    public String getCluster() {
        return this.cluster;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof MQPlacementDTO)) {
            return false;
        }
        MQPlacementDTO other = (MQPlacementDTO)o;
        String this$cluster = this.getCluster();
        String other$cluster = other.getCluster();
        if (this$cluster == null ? other$cluster != null : !this$cluster.equals(other$cluster)) {
            return false;
        }
        List<String> this$tags = this.getTags();
        List<String> other$tags = other.getTags();
        return !(this$tags == null ? other$tags != null : !((Object)this$tags).equals(other$tags));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $cluster = this.getCluster();
        result = result * 59 + ($cluster == null ? 43 : $cluster.hashCode());
        List<String> $tags = this.getTags();
        result = result * 59 + ($tags == null ? 43 : ((Object)$tags).hashCode());
        return result;
    }

    public String toString() {
        return "MQPlacementDTO(cluster=" + this.getCluster() + ", tags=" + String.valueOf(this.getTags()) + ")";
    }

    @JsonPOJOBuilder(withPrefix="", buildMethodName="build")
    public static class MQPlacementDTOBuilder {
        private String cluster;
        private List<String> tags;

        MQPlacementDTOBuilder() {
        }

        @JsonProperty(value="cluster")
        public MQPlacementDTOBuilder cluster(String cluster) {
            this.cluster = cluster;
            return this;
        }

        @JsonProperty(value="tags")
        public MQPlacementDTOBuilder tags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public MQPlacementDTO build() {
            return new MQPlacementDTO(this.cluster, this.tags);
        }

        public String toString() {
            return "MQPlacementDTO.MQPlacementDTOBuilder(cluster=" + this.cluster + ", tags=" + String.valueOf(this.tags) + ")";
        }
    }
}

