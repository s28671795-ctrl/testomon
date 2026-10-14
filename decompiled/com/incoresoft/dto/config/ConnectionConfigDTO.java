/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.config;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.incoresoft.config.models.ConnectionConfig;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="ConnectionConfigDTO", description="Connection config class structure")
@JsonDeserialize(builder=ConnectionConfigDTOBuilder.class)
public final class ConnectionConfigDTO {
    @JsonProperty(value="http_port")
    @JsonAlias(value={"port"})
    @Schema(name="http_port", description="HTTP port", example="2001", implementation=Integer.class)
    private final Integer httpPort;
    @JsonProperty(value="https_port")
    @Schema(name="https_port", description="HTTPS port", example="2443", implementation=Integer.class)
    private final Integer httpsPort;
    @JsonProperty(value="grpc_port")
    @Schema(name="grpc_port", description="GRPC port", example="2003", implementation=Integer.class)
    private final Integer grpcPort;

    public ConnectionConfigDTO(ConnectionConfig connection) {
        this.httpPort = connection.getHttpPort();
        this.httpsPort = connection.getHttpsPort();
        this.grpcPort = connection.getGrpcPort();
    }

    public static ConnectionConfigDTOBuilder builder() {
        return new ConnectionConfigDTOBuilder();
    }

    public Integer getHttpPort() {
        return this.httpPort;
    }

    public Integer getHttpsPort() {
        return this.httpsPort;
    }

    public Integer getGrpcPort() {
        return this.grpcPort;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ConnectionConfigDTO)) {
            return false;
        }
        ConnectionConfigDTO other = (ConnectionConfigDTO)o;
        Integer this$httpPort = this.getHttpPort();
        Integer other$httpPort = other.getHttpPort();
        if (this$httpPort == null ? other$httpPort != null : !((Object)this$httpPort).equals(other$httpPort)) {
            return false;
        }
        Integer this$httpsPort = this.getHttpsPort();
        Integer other$httpsPort = other.getHttpsPort();
        if (this$httpsPort == null ? other$httpsPort != null : !((Object)this$httpsPort).equals(other$httpsPort)) {
            return false;
        }
        Integer this$grpcPort = this.getGrpcPort();
        Integer other$grpcPort = other.getGrpcPort();
        return !(this$grpcPort == null ? other$grpcPort != null : !((Object)this$grpcPort).equals(other$grpcPort));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Integer $httpPort = this.getHttpPort();
        result = result * 59 + ($httpPort == null ? 43 : ((Object)$httpPort).hashCode());
        Integer $httpsPort = this.getHttpsPort();
        result = result * 59 + ($httpsPort == null ? 43 : ((Object)$httpsPort).hashCode());
        Integer $grpcPort = this.getGrpcPort();
        result = result * 59 + ($grpcPort == null ? 43 : ((Object)$grpcPort).hashCode());
        return result;
    }

    public String toString() {
        return "ConnectionConfigDTO(httpPort=" + this.getHttpPort() + ", httpsPort=" + this.getHttpsPort() + ", grpcPort=" + this.getGrpcPort() + ")";
    }

    public ConnectionConfigDTO(Integer httpPort, Integer httpsPort, Integer grpcPort) {
        this.httpPort = httpPort;
        this.httpsPort = httpsPort;
        this.grpcPort = grpcPort;
    }

    @JsonPOJOBuilder(withPrefix="", buildMethodName="build")
    public static class ConnectionConfigDTOBuilder {
        private Integer httpPort;
        private Integer httpsPort;
        private Integer grpcPort;

        ConnectionConfigDTOBuilder() {
        }

        @JsonProperty(value="http_port")
        @JsonAlias(value={"port"})
        public ConnectionConfigDTOBuilder httpPort(Integer httpPort) {
            this.httpPort = httpPort;
            return this;
        }

        @JsonProperty(value="https_port")
        public ConnectionConfigDTOBuilder httpsPort(Integer httpsPort) {
            this.httpsPort = httpsPort;
            return this;
        }

        @JsonProperty(value="grpc_port")
        public ConnectionConfigDTOBuilder grpcPort(Integer grpcPort) {
            this.grpcPort = grpcPort;
            return this;
        }

        public ConnectionConfigDTO build() {
            return new ConnectionConfigDTO(this.httpPort, this.httpsPort, this.grpcPort);
        }

        public String toString() {
            return "ConnectionConfigDTO.ConnectionConfigDTOBuilder(httpPort=" + this.httpPort + ", httpsPort=" + this.httpsPort + ", grpcPort=" + this.grpcPort + ")";
        }
    }
}

