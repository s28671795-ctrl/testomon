/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.plugins.dto.alpr;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.incoresoft.middleware.analytics.dto.analytics.ShortAnalyticsWithStreamDTO;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="ShortSpeedRuleDTO", description="Speed rule class structure")
@JsonDeserialize(builder=IntegrationShortSpeedRuleDTOBuilder.class)
public final class IntegrationShortSpeedRuleDTO {
    @JsonProperty(value="id")
    @JSONField(name="id")
    @Schema(name="id", description="Speed rule unique ID", example="1")
    private final Integer id;
    @JsonProperty(value="name")
    @JSONField(name="name")
    @Schema(name="name", description="Speed rule name", example="Speed rule name")
    private final String name;
    @JsonProperty(value="analytics1")
    @JSONField(name="analytics1")
    @Schema(name="analytics1", description="Analytics 1", implementation=ShortAnalyticsWithStreamDTO.class)
    private final ShortAnalyticsWithStreamDTO analytics1;
    @JsonProperty(value="analytics2")
    @JSONField(name="analytics2")
    @Schema(name="analytics2", description="Analytics 2", implementation=ShortAnalyticsWithStreamDTO.class)
    private final ShortAnalyticsWithStreamDTO analytics2;

    public static IntegrationShortSpeedRuleDTOBuilder builder() {
        return new IntegrationShortSpeedRuleDTOBuilder();
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public ShortAnalyticsWithStreamDTO getAnalytics1() {
        return this.analytics1;
    }

    public ShortAnalyticsWithStreamDTO getAnalytics2() {
        return this.analytics2;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof IntegrationShortSpeedRuleDTO)) {
            return false;
        }
        IntegrationShortSpeedRuleDTO other = (IntegrationShortSpeedRuleDTO)o;
        Integer this$id = this.getId();
        Integer other$id = other.getId();
        if (this$id == null ? other$id != null : !((Object)this$id).equals(other$id)) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        ShortAnalyticsWithStreamDTO this$analytics1 = this.getAnalytics1();
        ShortAnalyticsWithStreamDTO other$analytics1 = other.getAnalytics1();
        if (this$analytics1 == null ? other$analytics1 != null : !((Object)this$analytics1).equals(other$analytics1)) {
            return false;
        }
        ShortAnalyticsWithStreamDTO this$analytics2 = this.getAnalytics2();
        ShortAnalyticsWithStreamDTO other$analytics2 = other.getAnalytics2();
        return !(this$analytics2 == null ? other$analytics2 != null : !((Object)this$analytics2).equals(other$analytics2));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Integer $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        ShortAnalyticsWithStreamDTO $analytics1 = this.getAnalytics1();
        result = result * 59 + ($analytics1 == null ? 43 : ((Object)$analytics1).hashCode());
        ShortAnalyticsWithStreamDTO $analytics2 = this.getAnalytics2();
        result = result * 59 + ($analytics2 == null ? 43 : ((Object)$analytics2).hashCode());
        return result;
    }

    public String toString() {
        return "IntegrationShortSpeedRuleDTO(id=" + this.getId() + ", name=" + this.getName() + ", analytics1=" + String.valueOf(this.getAnalytics1()) + ", analytics2=" + String.valueOf(this.getAnalytics2()) + ")";
    }

    public IntegrationShortSpeedRuleDTO(Integer id, String name, ShortAnalyticsWithStreamDTO analytics1, ShortAnalyticsWithStreamDTO analytics2) {
        this.id = id;
        this.name = name;
        this.analytics1 = analytics1;
        this.analytics2 = analytics2;
    }

    @JsonPOJOBuilder(withPrefix="", buildMethodName="build")
    public static class IntegrationShortSpeedRuleDTOBuilder {
        private Integer id;
        private String name;
        private ShortAnalyticsWithStreamDTO analytics1;
        private ShortAnalyticsWithStreamDTO analytics2;

        IntegrationShortSpeedRuleDTOBuilder() {
        }

        @JsonProperty(value="id")
        public IntegrationShortSpeedRuleDTOBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        @JsonProperty(value="name")
        public IntegrationShortSpeedRuleDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        @JsonProperty(value="analytics1")
        public IntegrationShortSpeedRuleDTOBuilder analytics1(ShortAnalyticsWithStreamDTO analytics1) {
            this.analytics1 = analytics1;
            return this;
        }

        @JsonProperty(value="analytics2")
        public IntegrationShortSpeedRuleDTOBuilder analytics2(ShortAnalyticsWithStreamDTO analytics2) {
            this.analytics2 = analytics2;
            return this;
        }

        public IntegrationShortSpeedRuleDTO build() {
            return new IntegrationShortSpeedRuleDTO(this.id, this.name, this.analytics1, this.analytics2);
        }

        public String toString() {
            return "IntegrationShortSpeedRuleDTO.IntegrationShortSpeedRuleDTOBuilder(id=" + this.id + ", name=" + this.name + ", analytics1=" + String.valueOf(this.analytics1) + ", analytics2=" + String.valueOf(this.analytics2) + ")";
        }
    }
}

