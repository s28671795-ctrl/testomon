/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.users;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;

@Schema(name="LoginDTO", description="Login class structure")
public final class LoginDTO {
    @JSONField(name="login")
    @Schema(name="login", description="Login", implementation=String.class, example="admin")
    @NotBlank
    private final String login;
    @JSONField(name="password")
    @Schema(name="password", description="Password", implementation=String.class, example="admin")
    @NotBlank
    private final String password;

    public LoginDTO(String login, String password) {
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
        if (!(o instanceof LoginDTO)) {
            return false;
        }
        LoginDTO other = (LoginDTO)o;
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
        return "LoginDTO(login=" + this.getLogin() + ", password=" + this.getPassword() + ")";
    }
}

