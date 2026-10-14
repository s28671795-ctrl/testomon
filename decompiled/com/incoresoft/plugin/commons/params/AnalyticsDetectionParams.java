/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.params;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.incoresoft.Utils.CollectionUtils;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsManager;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.plugin.commons.params.validations.LatitudeMustBeSpecified;
import com.incoresoft.plugin.commons.params.validations.LongitudeMustBeSpecified;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@LatitudeMustBeSpecified
@LongitudeMustBeSpecified
@JsonDeserialize(builder=AnalyticsDetectionParamsBuilder.class)
public class AnalyticsDetectionParams {
    @JsonProperty(value="analytics_ids")
    @JSONField(name="analytics_ids")
    private final Set<Integer> analyticsIds;
    @JsonProperty(value="camera_ids")
    @JSONField(name="camera_ids")
    private final Set<Integer> cameraIds;
    @JsonProperty(value="radius", defaultValue="0")
    @JSONField(name="radius", defaultValue="0")
    @Min(value=0L)
    private final @Min(value=0L) Double radius;
    @JsonProperty(value="lat", defaultValue="0")
    @JSONField(name="lat", defaultValue="0")
    @Min(value=-90L)
    @Max(value=90L)
    private final @Min(value=-90L) @Max(value=90L) Double latitude;
    @JsonProperty(value="lng", defaultValue="0")
    @JSONField(name="lng", defaultValue="0")
    @Min(value=-180L)
    @Max(value=180L)
    private final @Min(value=-180L) @Max(value=180L) Double longitude;

    public boolean useAnalyticsIds() {
        return CollectionUtils.isNotEmpty(this.analyticsIds);
    }

    public boolean areLocationParamsSpecified() {
        return this.radius != null && this.radius != 0.0;
    }

    public Set<Integer> getAnalyticsIds(Function<Set<Integer>, List<Stream>> streamIdsProvider, AnalyticsManager analyticsManager) {
        if (CollectionUtils.isEmpty(this.cameraIds)) {
            return this.analyticsIds;
        }
        Set streamIds = streamIdsProvider.apply(this.cameraIds).stream().map(Stream::getId).collect(Collectors.toSet());
        Set<Integer> analyticsIds = analyticsManager.getAll().stream().filter(it -> streamIds.contains(it.getStreamId())).map(Analytics::getId).collect(Collectors.toSet());
        if (analyticsIds.isEmpty()) {
            analyticsIds = Set.of(Integer.valueOf(-1));
        }
        return analyticsIds;
    }

    public static AnalyticsDetectionParamsBuilder builder() {
        return new AnalyticsDetectionParamsBuilder();
    }

    public AnalyticsDetectionParams(Set<Integer> analyticsIds, Set<Integer> cameraIds, Double radius, Double latitude, Double longitude) {
        this.analyticsIds = analyticsIds;
        this.cameraIds = cameraIds;
        this.radius = radius;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Set<Integer> getAnalyticsIds() {
        return this.analyticsIds;
    }

    public Double getRadius() {
        return this.radius;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    @JsonPOJOBuilder(withPrefix="", buildMethodName="build")
    public static class AnalyticsDetectionParamsBuilder {
        private Set<Integer> analyticsIds;
        private Set<Integer> cameraIds;
        private Double radius;
        private Double latitude;
        private Double longitude;

        AnalyticsDetectionParamsBuilder() {
        }

        @JsonProperty(value="analytics_ids")
        public AnalyticsDetectionParamsBuilder analyticsIds(Set<Integer> analyticsIds) {
            this.analyticsIds = analyticsIds;
            return this;
        }

        @JsonProperty(value="camera_ids")
        public AnalyticsDetectionParamsBuilder cameraIds(Set<Integer> cameraIds) {
            this.cameraIds = cameraIds;
            return this;
        }

        @JsonProperty(value="radius", defaultValue="0")
        public AnalyticsDetectionParamsBuilder radius(Double radius) {
            this.radius = radius;
            return this;
        }

        @JsonProperty(value="lat", defaultValue="0")
        public AnalyticsDetectionParamsBuilder latitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        @JsonProperty(value="lng", defaultValue="0")
        public AnalyticsDetectionParamsBuilder longitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }

        public AnalyticsDetectionParams build() {
            return new AnalyticsDetectionParams(this.analyticsIds, this.cameraIds, this.radius, this.latitude, this.longitude);
        }

        public String toString() {
            return "AnalyticsDetectionParams.AnalyticsDetectionParamsBuilder(analyticsIds=" + String.valueOf(this.analyticsIds) + ", cameraIds=" + String.valueOf(this.cameraIds) + ", radius=" + this.radius + ", latitude=" + this.latitude + ", longitude=" + this.longitude + ")";
        }
    }

    public static final class Fields {
        public static final String analyticsIds = "analyticsIds";
        public static final String cameraIds = "cameraIds";
        public static final String radius = "radius";
        public static final String latitude = "latitude";
        public static final String longitude = "longitude";
    }
}

