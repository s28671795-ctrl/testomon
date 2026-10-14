/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.incoresoft.config.models.QDrantConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotEmpty;

@Schema(name="QDrantConfigDTO", description="QDrant config class structure")
@JsonDeserialize(builder=QDrantConfigDTOBuilder.class)
public final class QDrantConfigDTO {
    @JsonProperty(value="server")
    @Schema(name="server", description="QDrant server", example="nats://localhost:6333", implementation=String.class)
    @NotEmpty
    private final String server;

    public QDrantConfigDTO(QDrantConfig settings) {
        this(settings.getServer());
    }

    public static QDrantConfigDTOBuilder builder() {
        return new QDrantConfigDTOBuilder();
    }

    public String getServer() {
        return this.server;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof QDrantConfigDTO)) {
            return false;
        }
        QDrantConfigDTO other = (QDrantConfigDTO)o;
        String this$server = this.getServer();
        String other$server = other.getServer();
        return !(this$server == null ? other$server != null : !this$server.equals(other$server));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $server = this.getServer();
        result = result * 59 + ($server == null ? 43 : $server.hashCode());
        return result;
    }

    public String toString() {
        return "QDrantConfigDTO(server=" + this.getServer() + ")";
    }

    public QDrantConfigDTO(String server) {
        this.server = server;
    }

    @JsonPOJOBuilder(withPrefix="", buildMethodName="build")
    public static class QDrantConfigDTOBuilder {
        private String server;

        QDrantConfigDTOBuilder() {
        }

        @JsonProperty(value="server")
        public QDrantConfigDTOBuilder server(String server) {
            this.server = server;
            return this;
        }

        public QDrantConfigDTO build() {
            return new QDrantConfigDTO(this.server);
        }

        public String toString() {
            return "QDrantConfigDTO.QDrantConfigDTOBuilder(server=" + this.server + ")";
        }
    }
}

