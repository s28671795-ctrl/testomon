/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.config.models;

import com.incoresoft.dto.config.RedisConfigDTO;

public class RedisConfig {
    private String host = "localhost";
    private int port = 6379;

    public RedisConfig(RedisConfigDTO dto) {
        if (dto.getHost() != null) {
            this.host = dto.getHost();
        }
        this.port = dto.getPort();
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public void setHost(String host2) {
        this.host = host2;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RedisConfig)) {
            return false;
        }
        RedisConfig other = (RedisConfig)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getPort() != other.getPort()) {
            return false;
        }
        String this$host = this.getHost();
        String other$host = other.getHost();
        return !(this$host == null ? other$host != null : !this$host.equals(other$host));
    }

    protected boolean canEqual(Object other) {
        return other instanceof RedisConfig;
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
        return "RedisConfig(host=" + this.getHost() + ", port=" + this.getPort() + ")";
    }

    public RedisConfig() {
    }
}

