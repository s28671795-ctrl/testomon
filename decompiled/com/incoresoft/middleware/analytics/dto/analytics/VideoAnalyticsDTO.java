/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.analytics;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.dto.analytics.validation.FirstStep;
import com.incoresoft.middleware.analytics.dto.events.EventsHolderDTO;
import com.incoresoft.middleware.analytics.dto.restrictions.ItemRestrictionsDTO;
import com.incoresoft.middleware.model.analytics.module.AnalyticsModule;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.stream.StreamsManager;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@GroupSequence(value={FirstStep.class, VideoAnalyticsDTO.class})
@Schema(name="VideoAnalyticsDTO", description="Video Analytics DTO")
public class VideoAnalyticsDTO {
    @JSONField(name="id")
    @JsonProperty(value="id")
    @Schema(name="id", description="ID field", example="1", required=true)
    private Integer id;
    @JSONField(name="module", deserialize=false)
    @JsonProperty(value="module", access=JsonProperty.Access.READ_ONLY)
    @NotNull
    @Valid
    @Schema(name="module", description="Analytics module", implementation=AnalyticsModule.class)
    private AnalyticsModule module;
    @JSONField(name="events_holder")
    @JsonProperty(value="events_holder")
    @NotNull
    @Valid
    @Schema(name="events_holder", description="Events holder", implementation=EventsHolderDTO.class)
    private EventsHolderDTO eventsHolder;
    @JSONField(name="access_restrictions")
    @JsonProperty(value="access_restrictions")
    @Schema(name="access_restrictions", description="Item restrictions", implementation=ItemRestrictionsDTO.class)
    private ItemRestrictionsDTO restrictionsDTO;
    @JSONField(name="allowed_server_ids")
    @JsonProperty(value="allowed_server_ids")
    @Schema(name="allowed_server_ids", description="List of server ids", example="[1,2,3]", nullable=true)
    private Set<String> allowedServerIds;
    @JSONField(name="stream_id")
    @JsonProperty(value="stream_id")
    @Schema(name="stream_id", description="Stream ID Field", example="1", required=true)
    private Integer streamId;
    @JSONField(serialize=false, deserialize=false)
    @Schema(hidden=true)
    private Stream stream;
    @JSONField(name="parent_id")
    @JsonProperty(value="parent_id")
    @Schema(name="parent_id", description="Group id of analytics", example="1")
    private int parentId;

    public void update(AnalyticsModule module, StreamsManager streamsManager) {
        this.module = module;
        if (this.streamId != null) {
            this.stream = (Stream)streamsManager.get(this.streamId);
        }
    }

    public Integer getId() {
        return this.id;
    }

    public AnalyticsModule getModule() {
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

    public Stream getStream() {
        return this.stream;
    }

    public int getParentId() {
        return this.parentId;
    }

    @JsonProperty(value="id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty(value="module", access=JsonProperty.Access.READ_ONLY)
    public void setModule(AnalyticsModule module) {
        this.module = module;
    }

    @JsonProperty(value="events_holder")
    public void setEventsHolder(EventsHolderDTO eventsHolder) {
        this.eventsHolder = eventsHolder;
    }

    @JsonProperty(value="access_restrictions")
    public void setRestrictionsDTO(ItemRestrictionsDTO restrictionsDTO) {
        this.restrictionsDTO = restrictionsDTO;
    }

    @JsonProperty(value="allowed_server_ids")
    public void setAllowedServerIds(Set<String> allowedServerIds) {
        this.allowedServerIds = allowedServerIds;
    }

    @JsonProperty(value="stream_id")
    public void setStreamId(Integer streamId) {
        this.streamId = streamId;
    }

    public void setStream(Stream stream) {
        this.stream = stream;
    }

    @JsonProperty(value="parent_id")
    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public VideoAnalyticsDTO(Integer id, AnalyticsModule module, EventsHolderDTO eventsHolder, ItemRestrictionsDTO restrictionsDTO, Set<String> allowedServerIds, Integer streamId, Stream stream, int parentId) {
        this.id = id;
        this.module = module;
        this.eventsHolder = eventsHolder;
        this.restrictionsDTO = restrictionsDTO;
        this.allowedServerIds = allowedServerIds;
        this.streamId = streamId;
        this.stream = stream;
        this.parentId = parentId;
    }

    public VideoAnalyticsDTO() {
    }
}

