/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.events;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.incoresoft.middleware.analytics.dto.events.validations.EventMustExist;
import com.incoresoft.middleware.eventmanager.Event;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;

@EventMustExist
@Schema(name="EventDTO", description="Event class structure")
@JsonDeserialize(builder=EventDTOBuilder.class)
public final class EventDTO {
    @JSONField(name="id")
    @JsonProperty(value="id")
    @Schema(name="id", description="Event ID", example="1")
    @NotBlank
    private final String id;
    @JSONField(name="params")
    @JsonProperty(value="params")
    @Schema(name="params", description="Event Params", type="object", example="{\"param1\":\"value1\"}}")
    private final Map<@NotBlank String, String> params;

    public EventDTO(Event event) {
        this.id = event.getId();
        this.params = event.getParams().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static EventDTOBuilder builder() {
        return new EventDTOBuilder();
    }

    public String getId() {
        return this.id;
    }

    public Map<@NotBlank String, String> getParams() {
        return this.params;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof EventDTO)) {
            return false;
        }
        EventDTO other = (EventDTO)o;
        String this$id = this.getId();
        String other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) {
            return false;
        }
        Map<String, String> this$params = this.getParams();
        Map<String, String> other$params = other.getParams();
        return !(this$params == null ? other$params != null : !((Object)this$params).equals(other$params));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        Map<String, String> $params = this.getParams();
        result = result * 59 + ($params == null ? 43 : ((Object)$params).hashCode());
        return result;
    }

    public String toString() {
        return "EventDTO(id=" + this.getId() + ", params=" + String.valueOf(this.getParams()) + ")";
    }

    public EventDTO(String id, Map<@NotBlank String, String> params) {
        this.id = id;
        this.params = params;
    }

    @JsonPOJOBuilder(withPrefix="", buildMethodName="build")
    public static class EventDTOBuilder {
        private String id;
        private Map<@NotBlank String, String> params;

        EventDTOBuilder() {
        }

        @JsonProperty(value="id")
        public EventDTOBuilder id(String id) {
            this.id = id;
            return this;
        }

        @JsonProperty(value="params")
        public EventDTOBuilder params(Map<@NotBlank String, String> params) {
            this.params = params;
            return this;
        }

        public EventDTO build() {
            return new EventDTO(this.id, this.params);
        }

        public String toString() {
            return "EventDTO.EventDTOBuilder(id=" + this.id + ", params=" + String.valueOf(this.params) + ")";
        }
    }

    public static final class Fields {
        public static final String id = "id";
        public static final String params = "params";
    }
}

