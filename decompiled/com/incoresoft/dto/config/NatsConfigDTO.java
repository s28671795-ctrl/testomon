/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.incoresoft.config.models.NatsConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collections;
import java.util.List;
import javax.validation.constraints.NotEmpty;

@Schema(name="NatsConfigDTO", description="Nats config class structure")
@JsonDeserialize(builder=NatsConfigDTOBuilder.class)
public final class NatsConfigDTO {
    @JsonProperty(value="server")
    @Schema(name="server", description="Nats server", example="[\"nats://localhost:4222\"]")
    @NotEmpty
    private final List<String> server;
    @JsonProperty(value="user")
    @Schema(name="user", description="Nats user", example="admin", implementation=String.class)
    private final String user;
    @JsonProperty(value="password")
    @Schema(name="password", description="Nats password", example="admin", implementation=String.class)
    private final String password;

    public NatsConfigDTO(NatsConfig nats) {
        this.server = Collections.singletonList(nats.getServer());
        this.user = nats.getUser();
        this.password = nats.getPassword();
    }

    public static NatsConfigDTOBuilder builder() {
        return new NatsConfigDTOBuilder();
    }

    public List<String> getServer() {
        return this.server;
    }

    public String getUser() {
        return this.user;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof NatsConfigDTO)) {
            return false;
        }
        NatsConfigDTO other = (NatsConfigDTO)o;
        List<String> this$server = this.getServer();
        List<String> other$server = other.getServer();
        if (this$server == null ? other$server != null : !((Object)this$server).equals(other$server)) {
            return false;
        }
        String this$user = this.getUser();
        String other$user = other.getUser();
        if (this$user == null ? other$user != null : !this$user.equals(other$user)) {
            return false;
        }
        String this$password = this.getPassword();
        String other$password = other.getPassword();
        return !(this$password == null ? other$password != null : !this$password.equals(other$password));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        List<String> $server = this.getServer();
        result = result * 59 + ($server == null ? 43 : ((Object)$server).hashCode());
        String $user = this.getUser();
        result = result * 59 + ($user == null ? 43 : $user.hashCode());
        String $password = this.getPassword();
        result = result * 59 + ($password == null ? 43 : $password.hashCode());
        return result;
    }

    public String toString() {
        return "NatsConfigDTO(server=" + String.valueOf(this.getServer()) + ", user=" + this.getUser() + ", password=" + this.getPassword() + ")";
    }

    public NatsConfigDTO(List<String> server, String user, String password) {
        this.server = server;
        this.user = user;
        this.password = password;
    }

    @JsonPOJOBuilder(withPrefix="", buildMethodName="build")
    public static class NatsConfigDTOBuilder {
        private List<String> server;
        private String user;
        private String password;

        NatsConfigDTOBuilder() {
        }

        @JsonProperty(value="server")
        public NatsConfigDTOBuilder server(List<String> server) {
            this.server = server;
            return this;
        }

        @JsonProperty(value="user")
        public NatsConfigDTOBuilder user(String user) {
            this.user = user;
            return this;
        }

        @JsonProperty(value="password")
        public NatsConfigDTOBuilder password(String password) {
            this.password = password;
            return this;
        }

        public NatsConfigDTO build() {
            return new NatsConfigDTO(this.server, this.user, this.password);
        }

        public String toString() {
            return "NatsConfigDTO.NatsConfigDTOBuilder(server=" + String.valueOf(this.server) + ", user=" + this.user + ", password=" + this.password + ")";
        }
    }
}

