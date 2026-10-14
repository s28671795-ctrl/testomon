/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.streams.authorization;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.model.stream.AuthType;
import com.incoresoft.middleware.model.stream.StreamAuth;
import javax.validation.constraints.NotBlank;

public final class DigestAuth
implements StreamAuth {
    @JSONField(name="login")
    @JsonProperty(value="login")
    @NotBlank
    private final String login;
    @JSONField(name="password")
    @JsonProperty(value="password")
    @NotBlank
    private final String password;

    @Override
    @JSONField(name="type")
    @JsonProperty(value="type")
    public AuthType getAuthType() {
        return AuthType.digest;
    }

    public DigestAuth(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DigestAuth)) {
            return false;
        }
        DigestAuth other = (DigestAuth)o;
        String this$login = this.getLogin();
        String other$login = other.getLogin();
        if (this$login == null ? other$login != null : !this$login.equals(other$login)) {
            return false;
        }
        String this$password = this.getPassword();
        String other$password = other.getPassword();
        return !(this$password == null ? other$password != null : !this$password.equals(other$password));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $login = this.getLogin();
        result = result * 59 + ($login == null ? 43 : $login.hashCode());
        String $password = this.getPassword();
        result = result * 59 + ($password == null ? 43 : $password.hashCode());
        return result;
    }

    public String toString() {
        return "DigestAuth(login=" + this.getLogin() + ", password=" + this.getPassword() + ")";
    }
}

