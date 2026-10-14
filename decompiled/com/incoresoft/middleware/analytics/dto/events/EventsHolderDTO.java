/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.events;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.incoresoft.middleware.analytics.dto.events.EventDTO;
import com.incoresoft.middleware.eventmanager.EventsHolder;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

@Schema(name="EventsHolderDTO", description="Events holder class structure")
@JsonDeserialize(builder=EventsHolderDTOBuilder.class)
public final class EventsHolderDTO {
    @JSONField(name="notify_enabled", defaultValue="true")
    @JsonProperty(value="notify_enabled", defaultValue="true")
    @Schema(name="notify_enabled", description="Enable notifications status", implementation=Boolean.class, example="true")
    private final boolean notifyEnabled;
    @JSONField(name="events", defaultValue="[]")
    @JsonProperty(value="events", defaultValue="[]")
    @Valid
    @ArraySchema(arraySchema=@Schema(name="events", description="Events list", type="object", example="[{\"id\":\"98b9e166-ff52-4bde-941d-6dbc03b3faaa\",\"params\":{\"param1\":\"value1\"}}]"), schema=@Schema(name="events", implementation=EventDTO.class))
    private final List<EventDTO> events;

    public EventsHolderDTO(EventsHolder eventsHolder) {
        this.notifyEnabled = eventsHolder.isEnabled();
        this.events = eventsHolder.getEvents().stream().map(EventDTO::new).collect(Collectors.toList());
    }

    public static EventsHolderDTOBuilder builder() {
        return new EventsHolderDTOBuilder();
    }

    public boolean isNotifyEnabled() {
        return this.notifyEnabled;
    }

    public List<EventDTO> getEvents() {
        return this.events;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof EventsHolderDTO)) {
            return false;
        }
        EventsHolderDTO other = (EventsHolderDTO)o;
        if (this.isNotifyEnabled() != other.isNotifyEnabled()) {
            return false;
        }
        List<EventDTO> this$events = this.getEvents();
        List<EventDTO> other$events = other.getEvents();
        return !(this$events == null ? other$events != null : !((Object)this$events).equals(other$events));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isNotifyEnabled() ? 79 : 97);
        List<EventDTO> $events = this.getEvents();
        result = result * 59 + ($events == null ? 43 : ((Object)$events).hashCode());
        return result;
    }

    public String toString() {
        return "EventsHolderDTO(notifyEnabled=" + this.isNotifyEnabled() + ", events=" + String.valueOf(this.getEvents()) + ")";
    }

    public EventsHolderDTO(boolean notifyEnabled, List<EventDTO> events) {
        this.notifyEnabled = notifyEnabled;
        this.events = events;
    }

    @JsonPOJOBuilder(withPrefix="", buildMethodName="build")
    public static class EventsHolderDTOBuilder {
        private boolean notifyEnabled;
        private List<EventDTO> events;

        EventsHolderDTOBuilder() {
        }

        @JsonProperty(value="notify_enabled", defaultValue="true")
        public EventsHolderDTOBuilder notifyEnabled(boolean notifyEnabled) {
            this.notifyEnabled = notifyEnabled;
            return this;
        }

        @JsonProperty(value="events", defaultValue="[]")
        public EventsHolderDTOBuilder events(List<EventDTO> events) {
            this.events = events;
            return this;
        }

        public EventsHolderDTO build() {
            return new EventsHolderDTO(this.notifyEnabled, this.events);
        }

        public String toString() {
            return "EventsHolderDTO.EventsHolderDTOBuilder(notifyEnabled=" + this.notifyEnabled + ", events=" + String.valueOf(this.events) + ")";
        }
    }
}

