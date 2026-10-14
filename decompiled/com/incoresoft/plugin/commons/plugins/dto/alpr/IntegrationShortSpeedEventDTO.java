/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.plugins.dto.alpr;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.incoresoft.middleware.analytics.dto.streams.CoordinatesDTO;
import com.incoresoft.plugin.commons.plugins.dto.alpr.IntegrationShortSpeedRuleDTO;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="ShortSpeedEventDTO", description="Speed event class structure")
@JsonDeserialize(builder=IntegrationShortSpeedEventDTOBuilder.class)
public final class IntegrationShortSpeedEventDTO {
    @Schema(name="id", description="Speed event id", implementation=Integer.class)
    @JsonProperty(value="id")
    @JSONField(name="id")
    private final int id;
    @Schema(name="speed", description="Detected speed value", implementation=Integer.class)
    @JsonProperty(value="speed")
    @JSONField(name="speed")
    private final int speed;
    @Schema(name="rule", description="Speed rule assigned to this event", implementation=IntegrationShortSpeedRuleDTO.class)
    @JsonProperty(value="rule")
    @JSONField(name="rule")
    private final IntegrationShortSpeedRuleDTO rule;
    @Schema(name="location1", description="First detected location", implementation=CoordinatesDTO.class)
    @JsonProperty(value="location1")
    @JSONField(name="location1")
    private final CoordinatesDTO location1;
    @Schema(name="location2", description="Second detected location", implementation=CoordinatesDTO.class)
    @JsonProperty(value="location2")
    @JSONField(name="location2")
    private final CoordinatesDTO location2;

    public static IntegrationShortSpeedEventDTOBuilder builder() {
        return new IntegrationShortSpeedEventDTOBuilder();
    }

    public int getId() {
        return this.id;
    }

    public int getSpeed() {
        return this.speed;
    }

    public IntegrationShortSpeedRuleDTO getRule() {
        return this.rule;
    }

    public CoordinatesDTO getLocation1() {
        return this.location1;
    }

    public CoordinatesDTO getLocation2() {
        return this.location2;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof IntegrationShortSpeedEventDTO)) {
            return false;
        }
        IntegrationShortSpeedEventDTO other = (IntegrationShortSpeedEventDTO)o;
        if (this.getId() != other.getId()) {
            return false;
        }
        if (this.getSpeed() != other.getSpeed()) {
            return false;
        }
        IntegrationShortSpeedRuleDTO this$rule = this.getRule();
        IntegrationShortSpeedRuleDTO other$rule = other.getRule();
        if (this$rule == null ? other$rule != null : !((Object)this$rule).equals(other$rule)) {
            return false;
        }
        CoordinatesDTO this$location1 = this.getLocation1();
        CoordinatesDTO other$location1 = other.getLocation1();
        if (this$location1 == null ? other$location1 != null : !((Object)this$location1).equals(other$location1)) {
            return false;
        }
        CoordinatesDTO this$location2 = this.getLocation2();
        CoordinatesDTO other$location2 = other.getLocation2();
        return !(this$location2 == null ? other$location2 != null : !((Object)this$location2).equals(other$location2));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getId();
        result = result * 59 + this.getSpeed();
        IntegrationShortSpeedRuleDTO $rule = this.getRule();
        result = result * 59 + ($rule == null ? 43 : ((Object)$rule).hashCode());
        CoordinatesDTO $location1 = this.getLocation1();
        result = result * 59 + ($location1 == null ? 43 : ((Object)$location1).hashCode());
        CoordinatesDTO $location2 = this.getLocation2();
        result = result * 59 + ($location2 == null ? 43 : ((Object)$location2).hashCode());
        return result;
    }

    public String toString() {
        return "IntegrationShortSpeedEventDTO(id=" + this.getId() + ", speed=" + this.getSpeed() + ", rule=" + String.valueOf(this.getRule()) + ", location1=" + String.valueOf(this.getLocation1()) + ", location2=" + String.valueOf(this.getLocation2()) + ")";
    }

    public IntegrationShortSpeedEventDTO(int id, int speed, IntegrationShortSpeedRuleDTO rule, CoordinatesDTO location1, CoordinatesDTO location2) {
        this.id = id;
        this.speed = speed;
        this.rule = rule;
        this.location1 = location1;
        this.location2 = location2;
    }

    @JsonPOJOBuilder(withPrefix="", buildMethodName="build")
    public static class IntegrationShortSpeedEventDTOBuilder {
        private int id;
        private int speed;
        private IntegrationShortSpeedRuleDTO rule;
        private CoordinatesDTO location1;
        private CoordinatesDTO location2;

        IntegrationShortSpeedEventDTOBuilder() {
        }

        @JsonProperty(value="id")
        public IntegrationShortSpeedEventDTOBuilder id(int id) {
            this.id = id;
            return this;
        }

        @JsonProperty(value="speed")
        public IntegrationShortSpeedEventDTOBuilder speed(int speed) {
            this.speed = speed;
            return this;
        }

        @JsonProperty(value="rule")
        public IntegrationShortSpeedEventDTOBuilder rule(IntegrationShortSpeedRuleDTO rule) {
            this.rule = rule;
            return this;
        }

        @JsonProperty(value="location1")
        public IntegrationShortSpeedEventDTOBuilder location1(CoordinatesDTO location1) {
            this.location1 = location1;
            return this;
        }

        @JsonProperty(value="location2")
        public IntegrationShortSpeedEventDTOBuilder location2(CoordinatesDTO location2) {
            this.location2 = location2;
            return this;
        }

        public IntegrationShortSpeedEventDTO build() {
            return new IntegrationShortSpeedEventDTO(this.id, this.speed, this.rule, this.location1, this.location2);
        }

        public String toString() {
            return "IntegrationShortSpeedEventDTO.IntegrationShortSpeedEventDTOBuilder(id=" + this.id + ", speed=" + this.speed + ", rule=" + String.valueOf(this.rule) + ", location1=" + String.valueOf(this.location1) + ", location2=" + String.valueOf(this.location2) + ")";
        }
    }
}

