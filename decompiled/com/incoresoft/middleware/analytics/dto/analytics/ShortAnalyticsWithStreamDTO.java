/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.analytics;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.dto.analytics.ShortAnalyticsDTO;
import com.incoresoft.middleware.analytics.dto.streams.ShortStreamDTO;
import com.incoresoft.middleware.model.stream.Stream;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Optional;

public class ShortAnalyticsWithStreamDTO
extends ShortAnalyticsDTO {
    @Schema(name="stream", description="Short stream", implementation=ShortStreamDTO.class)
    @JsonProperty(value="stream")
    @JSONField(name="stream")
    private ShortStreamDTO stream;

    public ShortAnalyticsWithStreamDTO(Analytics analytics, Stream stream) {
        super(analytics);
        this.stream = Optional.ofNullable(stream).map(ShortStreamDTO::new).orElse(null);
    }

    public ShortAnalyticsWithStreamDTO(Integer analyticsId) {
        super(analyticsId);
    }

    public ShortStreamDTO getStream() {
        return this.stream;
    }

    public ShortAnalyticsWithStreamDTO(ShortStreamDTO stream) {
        this.stream = stream;
    }

    public ShortAnalyticsWithStreamDTO() {
    }
}

