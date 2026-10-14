/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.eventmanager;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.vezha.dto.eventmanager.NodeDTO;
import com.incoresoft.vezha.dto.eventmanager.validations.NodesNotEmpty;
import com.incoresoft.vezha.eventmanager.EventScheme;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.List;
import javax.validation.constraints.NotEmpty;

@NodesNotEmpty
@Schema(name="EventSchemeDTO", description="Event scheme class structure")
public final class EventSchemeDTO {
    @JSONField(name="id")
    @Schema(name="id", description="UUID", example="1cc33440-af50-468b-9914-162658226e59", implementation=String.class)
    private String id;
    @JSONField(name="event_id")
    @Schema(name="event_id", description="Event id", example="1", implementation=Integer.class)
    private final Integer eventId;
    @JSONField(name="title")
    @Schema(name="title", description="Title", example="Some title", implementation=String.class)
    @NotEmpty
    private final String title;
    @JSONField(name="description")
    @Schema(name="description", description="Description", example="Some description", implementation=String.class)
    private final String description;
    @JSONField(name="nodes")
    @ArraySchema(schema=@Schema(name="nodes", description="Nodes", implementation=NodeDTO.class))
    private final List<NodeDTO> nodes;
    @JSONField(name="created_at")
    @Schema(name="created_at", description="Created at", example="timestamp", implementation=Long.class)
    private final Instant createdAt;

    public EventSchemeDTO(EventScheme scheme2) {
        this.id = scheme2.getUuid();
        this.eventId = scheme2.getId();
        this.title = scheme2.getTitle();
        this.description = scheme2.getDescription();
        this.nodes = scheme2.getNodes().stream().map(NodeDTO::new).toList();
        this.createdAt = scheme2.getCreatedAt();
    }

    public String getId() {
        return this.id;
    }

    public Integer getEventId() {
        return this.eventId;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public List<NodeDTO> getNodes() {
        return this.nodes;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof EventSchemeDTO)) {
            return false;
        }
        EventSchemeDTO other = (EventSchemeDTO)o;
        Integer this$eventId = this.getEventId();
        Integer other$eventId = other.getEventId();
        if (this$eventId == null ? other$eventId != null : !((Object)this$eventId).equals(other$eventId)) {
            return false;
        }
        String this$id = this.getId();
        String other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) {
            return false;
        }
        String this$title = this.getTitle();
        String other$title = other.getTitle();
        if (this$title == null ? other$title != null : !this$title.equals(other$title)) {
            return false;
        }
        String this$description = this.getDescription();
        String other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description)) {
            return false;
        }
        List<NodeDTO> this$nodes = this.getNodes();
        List<NodeDTO> other$nodes = other.getNodes();
        if (this$nodes == null ? other$nodes != null : !((Object)this$nodes).equals(other$nodes)) {
            return false;
        }
        Instant this$createdAt = this.getCreatedAt();
        Instant other$createdAt = other.getCreatedAt();
        return !(this$createdAt == null ? other$createdAt != null : !((Object)this$createdAt).equals(other$createdAt));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Integer $eventId = this.getEventId();
        result = result * 59 + ($eventId == null ? 43 : ((Object)$eventId).hashCode());
        String $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        String $title = this.getTitle();
        result = result * 59 + ($title == null ? 43 : $title.hashCode());
        String $description = this.getDescription();
        result = result * 59 + ($description == null ? 43 : $description.hashCode());
        List<NodeDTO> $nodes = this.getNodes();
        result = result * 59 + ($nodes == null ? 43 : ((Object)$nodes).hashCode());
        Instant $createdAt = this.getCreatedAt();
        result = result * 59 + ($createdAt == null ? 43 : ((Object)$createdAt).hashCode());
        return result;
    }

    public String toString() {
        return "EventSchemeDTO(id=" + this.getId() + ", eventId=" + this.getEventId() + ", title=" + this.getTitle() + ", description=" + this.getDescription() + ", nodes=" + String.valueOf(this.getNodes()) + ", createdAt=" + String.valueOf(this.getCreatedAt()) + ")";
    }

    public EventSchemeDTO(String id, Integer eventId, String title, String description, List<NodeDTO> nodes, Instant createdAt) {
        this.id = id;
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.nodes = nodes;
        this.createdAt = createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }
}

