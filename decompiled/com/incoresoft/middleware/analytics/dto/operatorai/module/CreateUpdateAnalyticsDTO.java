/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.operatorai.module;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.dto.events.EventsHolderDTO;
import com.incoresoft.middleware.analytics.dto.restrictions.ItemRestrictionsDTO;
import com.incoresoft.middleware.analytics.module.operatorai.OperatorAiAnalyticsModule;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Schema(description="Operator AI analytics creation object structure")
public class CreateUpdateAnalyticsDTO {
    @JSONField(name="module", deserialize=false)
    @NotNull
    @Valid
    @Schema(name="module", description="Analytics module")
    private OperatorAiAnalyticsModule module;
    @NotNull
    @Valid
    @JSONField(name="events_holder")
    @Schema(name="events_holder", description="Events holder", implementation=EventsHolderDTO.class)
    private EventsHolderDTO eventsHolder;
    @Schema(name="access_restrictions", description="Item restrictions", implementation=ItemRestrictionsDTO.class)
    private ItemRestrictionsDTO restrictionsDTO;
    @JSONField(name="allowed_server_ids")
    @Schema(name="allowed_server_ids", description="List of allowed server IDs")
    private Set<String> allowedServerIds;
    @NotNull
    @JSONField(name="stream_id")
    @Schema(name="stream_id", description="Stream ID", example="1", implementation=Integer.class, required=true)
    private Integer streamId;

    public OperatorAiAnalyticsModule getModule() {
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

