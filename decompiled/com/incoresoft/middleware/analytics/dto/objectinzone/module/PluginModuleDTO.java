/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.objectinzone.module;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.dto.events.EventsHolderDTO;
import com.incoresoft.middleware.analytics.dto.restrictions.ItemRestrictionsDTO;
import com.incoresoft.middleware.analytics.module.objectinzone.ObjectInZoneModule;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Schema(name="PluginModuleDTO", description="Object Detection class structure")
public class PluginModuleDTO {
    @Schema(name="id", description="ID field", implementation=Integer.class, example="1", required=true)
    private Integer id;
    @JSONField(name="module", deserialize=false)
    @NotNull
    @Valid
    @Schema(name="module", description="Analytics module", implementation=ObjectInZoneModule.class)
    private ObjectInZoneModule module;
    @NotNull
    @Valid
    @JSONField(name="events_holder")
    @Schema(name="events_holder", description="Events holder", implementation=EventsHolderDTO.class)
    private EventsHolderDTO eventsHolder;
    @Schema(name="access_restrictions", description="Item restrictions", implementation=ItemRestrictionsDTO.class)
    private ItemRestrictionsDTO restrictionsDTO;
    @JSONField(name="allowed_server_ids")
    @ArraySchema(schema=@Schema(description="Allowed server IDs on which analytics can be run", implementation=String.class, example="1", nullable=true))
    private Set<String> allowedServerIds;
    @NotNull
    @JSONField(name="stream_id")
    @Schema(name="stream_id", description="Stream ID", example="1", implementation=Integer.class, required=true)
    private Integer streamId;

    public Integer getId() {
        return this.id;
    }

    public ObjectInZoneModule getModule() {
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

