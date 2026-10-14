/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.incoresoft.config.models.RedisConfig;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="RedisConfigDTO", description="Redis config class structure")
@JsonDeserialize(builder=RedisConfigDTOBuilder.class)
public final class RedisConfigDTO {
    @JsonProperty(value="host")
    @Schema(name="host", description="Redis host", example="localhost", requiredMode=Schema.RequiredMode.REQUIRED, implementation=String.class)
    private final String host;
    @JsonProperty(value="port")
    @Schema(name="port", description="Redis port", example="6379", requiredMode=Schema.RequiredMode.REQUIRED, implementation=Integer.class)
    private final int port;

    public RedisConfigDTO(String redis) {
        String localHost = "localhost";
        int localPort = 6379;
        String[] split = redis.split(":");
        if (split.length == 2) {
            localHost = split[0];
            localPort = Integer.parseInt(split[1]);
        }
        this.host = localHost;
        this.port = localPort;
    }

    public RedisConfigDTO(RedisConfig redis) {
        this.host = redis.getHost();
        this.port = redis.getPort();
    }

    public static RedisConfigDTOBuilder builder() {
        return new RedisConfigDTOBuilder();
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RedisConfigDTO)) {
            return false;
        }
        RedisConfigDTO other = (RedisConfigDTO)o;
        if (this.getPort() != other.getPort()) {
            return false;
        }
        String this$host = this.getHost();
        String other$host = other.getHost();
        return !(this$host == null ? other$host != null : !this$host.equals(other$host));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getPort();
        String $host = this.getHost();
        result = result * 59 + ($host == null ? 43 : $host.hashCode());
        return result;
    }

    public String toString() {
        return "RedisConfigDTO(host=" + this.getHost() + ", port=" + this.getPort() + ")";
    }

    public RedisConfigDTO(String host2, int port) {
        this.host = host2;
        this.port = port;
    }

    @JsonPOJOBuilder(withPrefix="", buildMethodName="build")
    public static class RedisConfigDTOBuilder {
        private String host;
        private int port;

        RedisConfigDTOBuilder() {
        }

        @JsonProperty(value="host")
        public RedisConfigDTOBuilder host(String host2) {
            this.host = host2;
            return this;
        }

        @JsonProperty(value="port")
        public RedisConfigDTOBuilder port(int port) {
            this.port = port;
            return this;
        }

        public RedisConfigDTO build() {
            return new RedisConfigDTO(this.host, this.port);
        }

        public String toString() {
            return "RedisConfigDTO.RedisConfigDTOBuilder(host=" + this.host + ", port=" + this.port + ")";
        }
    }
}

