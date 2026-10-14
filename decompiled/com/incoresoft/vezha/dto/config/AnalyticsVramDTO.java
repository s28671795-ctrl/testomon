/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.incoresoft.vezha.config.models.AnalyticsVramConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;
import java.util.Objects;

@Schema(name="AnalyticsVramDTO", description="How much GPU memory one analytics is expected to take once its stream is online")
@JsonDeserialize(builder=AnalyticsVramDTOBuilder.class)
public final class AnalyticsVramDTO {
    @JsonProperty(value="per_type")
    @Schema(name="per_type", description="GPU memory in GB per analytics type, keyed by the plugin name reported as \"type\" in the servers API. Overrides the resolution-based estimate.", example="{\"alpr\": 0.5, \"face\": 0.8}")
    private final Map<String, Double> perType;
    @JsonProperty(value="default")
    @Schema(name="default", description="GPU memory in GB assumed when neither a per_type override nor the stream resolution is available", example="0.3")
    private final Double defaultGb;
    @JsonProperty(value="gb_per_megapixel")
    @Schema(name="gb_per_megapixel", description="GPU memory in GB per megapixel of stream resolution (decoder + frame buffering scale linearly with pixel count). Measured: 1080p ~171 MB, 4K ~656 MB, 4096x4096 ~1285 MB per analytics.", example="0.085")
    private final Double gbPerMegapixel;
    @JsonProperty(value="min_gb")
    @Schema(name="min_gb", description="Floor for the resolution-based estimate, protecting very small streams from a near-zero reserve", example="0.1")
    private final Double minGb;

    public AnalyticsVramDTO(AnalyticsVramConfig config) {
        Objects.requireNonNull(config, "config must not be null");
        this.perType = config.getPerType() == null ? Map.of() : Map.copyOf(config.getPerType());
        this.defaultGb = config.getDefaultGb();
        this.gbPerMegapixel = config.getGbPerMegapixel();
        this.minGb = config.getMinGb();
    }

    public static AnalyticsVramDTOBuilder builder() {
        return new AnalyticsVramDTOBuilder();
    }

    public Map<String, Double> getPerType() {
        return this.perType;
    }

    public Double getDefaultGb() {
        return this.defaultGb;
    }

    public Double getGbPerMegapixel() {
        return this.gbPerMegapixel;
    }

    public Double getMinGb() {
        return this.minGb;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AnalyticsVramDTO)) {
            return false;
        }
        AnalyticsVramDTO other = (AnalyticsVramDTO)o;
        Double this$defaultGb = this.getDefaultGb();
        Double other$defaultGb = other.getDefaultGb();
        if (this$defaultGb == null ? other$defaultGb != null : !((Object)this$defaultGb).equals(other$defaultGb)) {
            return false;
        }
        Double this$gbPerMegapixel = this.getGbPerMegapixel();
        Double other$gbPerMegapixel = other.getGbPerMegapixel();
        if (this$gbPerMegapixel == null ? other$gbPerMegapixel != null : !((Object)this$gbPerMegapixel).equals(other$gbPerMegapixel)) {
            return false;
        }
        Double this$minGb = this.getMinGb();
        Double other$minGb = other.getMinGb();
        if (this$minGb == null ? other$minGb != null : !((Object)this$minGb).equals(other$minGb)) {
            return false;
        }
        Map<String, Double> this$perType = this.getPerType();
        Map<String, Double> other$perType = other.getPerType();
        return !(this$perType == null ? other$perType != null : !((Object)this$perType).equals(other$perType));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Double $defaultGb = this.getDefaultGb();
        result = result * 59 + ($defaultGb == null ? 43 : ((Object)$defaultGb).hashCode());
        Double $gbPerMegapixel = this.getGbPerMegapixel();
        result = result * 59 + ($gbPerMegapixel == null ? 43 : ((Object)$gbPerMegapixel).hashCode());
        Double $minGb = this.getMinGb();
        result = result * 59 + ($minGb == null ? 43 : ((Object)$minGb).hashCode());
        Map<String, Double> $perType = this.getPerType();
        result = result * 59 + ($perType == null ? 43 : ((Object)$perType).hashCode());
        return result;
    }

    public String toString() {
        return "AnalyticsVramDTO(perType=" + String.valueOf(this.getPerType()) + ", defaultGb=" + this.getDefaultGb() + ", gbPerMegapixel=" + this.getGbPerMegapixel() + ", minGb=" + this.getMinGb() + ")";
    }

    public AnalyticsVramDTO(Map<String, Double> perType, Double defaultGb, Double gbPerMegapixel, Double minGb) {
        this.perType = perType;
        this.defaultGb = defaultGb;
        this.gbPerMegapixel = gbPerMegapixel;
        this.minGb = minGb;
    }

    @JsonPOJOBuilder(withPrefix="", buildMethodName="build")
    public static class AnalyticsVramDTOBuilder {
        private Map<String, Double> perType;
        private Double defaultGb;
        private Double gbPerMegapixel;
        private Double minGb;

        AnalyticsVramDTOBuilder() {
        }

        @JsonProperty(value="per_type")
        public AnalyticsVramDTOBuilder perType(Map<String, Double> perType) {
            this.perType = perType;
            return this;
        }

        @JsonProperty(value="default")
        public AnalyticsVramDTOBuilder defaultGb(Double defaultGb) {
            this.defaultGb = defaultGb;
            return this;
        }

        @JsonProperty(value="gb_per_megapixel")
        public AnalyticsVramDTOBuilder gbPerMegapixel(Double gbPerMegapixel) {
            this.gbPerMegapixel = gbPerMegapixel;
            return this;
        }

        @JsonProperty(value="min_gb")
        public AnalyticsVramDTOBuilder minGb(Double minGb) {
            this.minGb = minGb;
            return this;
        }

        public AnalyticsVramDTO build() {
            return new AnalyticsVramDTO(this.perType, this.defaultGb, this.gbPerMegapixel, this.minGb);
        }

        public String toString() {
            return "AnalyticsVramDTO.AnalyticsVramDTOBuilder(perType=" + String.valueOf(this.perType) + ", defaultGb=" + this.defaultGb + ", gbPerMegapixel=" + this.gbPerMegapixel + ", minGb=" + this.minGb + ")";
        }
    }
}

