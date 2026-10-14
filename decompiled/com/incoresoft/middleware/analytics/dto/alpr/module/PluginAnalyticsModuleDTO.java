/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.alpr.module;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.dto.events.EventsHolderDTO;
import com.incoresoft.middleware.analytics.dto.restrictions.ItemRestrictionsDTO;
import com.incoresoft.middleware.analytics.module.alpr.AlprAnalyticsModule;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Schema(name="AlprAnalyticsModuleDTO", description="ALPR class structure")
public class PluginAnalyticsModuleDTO {
    @Schema(name="id", description="ID field", implementation=Integer.class, example="1", required=true)
    private Integer id;
    @JSONField(name="module", deserialize=false)
    @JsonProperty(value="module", access=JsonProperty.Access.READ_ONLY)
    @NotNull
    @Valid
    @Schema(name="module", description="Analytics module", implementation=AlprAnalyticsModule.class)
    private AlprAnalyticsModule module;
    @NotNull
    @Valid
    @JSONField(name="events_holder")
    @JsonProperty(value="events_holder")
    @Schema(name="events_holder", description="Events holder", implementation=EventsHolderDTO.class)
    private EventsHolderDTO eventsHolder;
    @JsonProperty(value="access_restrictions")
    @Schema(name="access_restrictions", description="Item restrictions", implementation=ItemRestrictionsDTO.class)
    private ItemRestrictionsDTO restrictionsDTO;
    @JSONField(name="allowed_server_ids")
    @JsonProperty(value="allowed_server_ids")
    @ArraySchema(arraySchema=@Schema(name="allowed_server_ids", description="Server ID", implementation=String.class, example="[1,2,3]", nullable=true), schema=@Schema(description="Server ID", implementation=String.class, example="1", nullable=true))
    private Set<String> allowedServerIds;
    @NotNull
    @JSONField(name="stream_id")
    @JsonProperty(value="stream_id")
    @Schema(name="stream_id", description="Stream ID", example="1", implementation=Integer.class, required=true)
    private Integer streamId;

    public Integer getId() {
        return this.id;
    }

    public AlprAnalyticsModule getModule() {
        return this.module;
    }

    public EventsHolderDTO getEventsHolder() {
        return this.eventsHolder;
    }

    public ItemRestrictionsDTO getRestrictionsDTO() {
        return this.restrictionsDTO;
    }

    public Set<String> getAllowedServerIds() {
        return this.allowedServerIds;
    }

    public Integer getStreamId() {
        return this.streamId;
    }
}

