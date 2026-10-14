/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.ldap;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.dto.ldap.validations.InvalidLdapLogin;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;

@Schema(name="LDAPConnectionDTO", description="LDAP connection class structure")
public record LDAPConnectionParams(@Schema(name="url", description="LDAP server URL", example="ldap://localhost:389", implementation=String.class) @JSONField(name="url") @JsonProperty(value="url") @NotBlank String url, @Schema(name="login", description="LDAP server login", example="test@test.com", implementation=String.class) @NotBlank @InvalidLdapLogin @JSONField(name="login") @JsonProperty(value="login") String login, @Schema(name="password", description="LDAP server password", example="password", implementation=String.class) @JSONField(name="password") @JsonProperty(value="password") String password, @Schema(name="base", description="LDAP server base", example="dc=example,dc=com", implementation=String.class) @JSONField(name="base") @JsonProperty(value="base") String base, @Schema(name="filter", description="LDAP server filter", example="(objectClass=*)", implementation=String.class) @JSONField(name="filter") @JsonProperty(value="filter") String filter) {

    public static final class Fields {
        public static final String url = "url";
        public static final String login = "login";
        public static final String password = "password";
        public static final String base = "base";
        public static final String filter = "filter";
    }
}

