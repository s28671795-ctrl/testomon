/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.config.models;

import com.incoresoft.dto.config.NatsConfigDTO;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NatsConfig {
    private static final Logger log = LoggerFactory.getLogger(NatsConfig.class);
    private List<String> server = List.of("localhost:4222");
    private String user = "";
    private String password = "";

    public NatsConfig(NatsConfigDTO dto) {
        if (dto.getServer() != null) {
            this.server = dto.getServer();
        }
        if (dto.getUser() != null) {
            this.user = dto.getUser();
        }
        if (dto.getPassword() != null) {
            this.password = dto.getPassword();
        }
    }

    public NatsConfig(String server) {
        this.server = Collections.singletonList(server);
    }

    public String getServer() {
        return String.join((CharSequence)",", this.server);
    }

    public void setServer(String value) {
        this.server = List.of(value.split(","));
    }

    public String getUser() {
        return this.user;
    }

    public String getPassword() {
        return this.password;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof NatsConfig)) {
            return false;
        }
        NatsConfig other = (NatsConfig)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$server = this.getServer();
        String other$server = other.getServer();
        if (this$server == null ? other$server != null : !this$server.equals(other$server)) {
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

    protected boolean canEqual(Object other) {
        return other instanceof NatsConfig;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $server = this.getServer();
        result = result * 59 + ($server == null ? 43 : $server.hashCode());
        String $user = this.getUser();
        result = result * 59 + ($user == null ? 43 : $user.hashCode());
        String $password = this.getPassword();
        result = result * 59 + ($password == null ? 43 : $password.hashCode());
        return result;
    }

    public String toString() {
        return "NatsConfig(server=" + this.getServer() + ", user=" + this.getUser() + ", password=" + this.getPassword() + ")";
    }

    public NatsConfig() {
    }
}

