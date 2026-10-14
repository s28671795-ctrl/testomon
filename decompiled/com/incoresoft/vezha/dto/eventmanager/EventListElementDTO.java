/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.eventmanager;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.vezha.eventmanager.EventScheme;
import io.swagger.v3.oas.annotations.media.Schema;
import org.jetbrains.annotations.NotNull;

@Schema(name="EventListElementDTO", description="Event list element class structure for frontend")
public final class EventListElementDTO {
    @JSONField(name="id")
    @Schema(name="id", description="Event uuid", implementation=String.class, example="1cc33440-af50-468b-9914-162658226e59")
    private final String id;
    @JSONField(name="event_id")
    @Schema(name="event_id", description="Event id", implementation=Integer.class, example="1")
    private final Integer eventId;
    @JSONField(name="title")
    @Schema(name="title", description="Event title", implementation=String.class, example="Event title")
    private final String title;
    @JSONField(name="description")
    @Schema(name="description", description="Event description", implementation=String.class, example="Event description")
    private final String description;

    public EventListElementDTO(@NotNull EventScheme scheme2) {
        this.id = scheme2.getUuid();
        this.eventId = scheme2.getId();
        this.title = scheme2.getTitle();
        this.description = scheme2.getDescription();
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

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof EventListElementDTO)) {
            return false;
        }
        EventListElementDTO other = (EventListElementDTO)o;
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
        return !(this$description == null ? other$description != null : !this$description.equals(other$description));
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
        return result;
    }

    public String toString() {
        return "EventListElementDTO(id=" + this.getId() + ", eventId=" + this.getEventId() + ", title=" + this.getTitle() + ", description=" + this.getDescription() + ")";
    }

    public EventListElementDTO(String id, Integer eventId, String title, String description) {
        this.id = id;
        this.eventId = eventId;
        this.title = title;
        this.description = description;
    }
}

