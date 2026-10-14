/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.config.models;

import com.incoresoft.dto.config.ConnectionConfigDTO;

public class ConnectionConfig {
    private int httpPort = 2001;
    private int httpsPort = 2443;
    private int grpcPort = 2003;

    public ConnectionConfig(ConnectionConfigDTO dto) {
        if (dto.getHttpPort() != null) {
            this.httpPort = dto.getHttpPort();
        }
        if (dto.getHttpsPort() != null) {
            this.httpsPort = dto.getHttpsPort();
        }
        if (dto.getGrpcPort() != null) {
            this.grpcPort = dto.getGrpcPort();
        }
    }

    public ConnectionConfig(int httpPort, int httpsPort) {
        this.httpPort = httpPort;
        this.httpsPort = httpsPort;
    }

    public int getHttpPort() {
        return this.httpPort;
    }

    public int getHttpsPort() {
        return this.httpsPort;
    }

    public int getGrpcPort() {
        return this.grpcPort;
    }

    public void setHttpPort(int httpPort) {
        this.httpPort = httpPort;
    }

    public void setHttpsPort(int httpsPort) {
        this.httpsPort = httpsPort;
    }

    public void setGrpcPort(int grpcPort) {
        this.grpcPort = grpcPort;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ConnectionConfig)) {
            return false;
        }
        ConnectionConfig other = (ConnectionConfig)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getHttpPort() != other.getHttpPort()) {
            return false;
        }
        if (this.getHttpsPort() != other.getHttpsPort()) {
            return false;
        }
        return this.getGrpcPort() == other.getGrpcPort();
    }

    protected boolean canEqual(Object other) {
        return other instanceof ConnectionConfig;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getHttpPort();
        result = result * 59 + this.getHttpsPort();
        result = result * 59 + this.getGrpcPort();
        return result;
    }

    public String toString() {
        return "ConnectionConfig(httpPort=" + this.getHttpPort() + ", httpsPort=" + this.getHttpsPort() + ", grpcPort=" + this.getGrpcPort() + ")";
    }

    public ConnectionConfig() {
    }

    public ConnectionConfig(int httpPort, int httpsPort, int grpcPort) {
        this.httpPort = httpPort;
        this.httpsPort = httpsPort;
        this.grpcPort = grpcPort;
    }
}

