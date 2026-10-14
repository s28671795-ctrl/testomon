/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.ldap;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.dto.ldap.LDAPConnectionParams;
import com.incoresoft.dto.ldap.LDAPImportUserParams;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Schema(name="LdapImportParams", description="Ldap import class structure")
public record LDAPImportParams(@Schema(name="import_params", description="Import params", implementation=LDAPImportUserParams.class) @JSONField(name="import_params") @JsonProperty(value="import_params") @NotNull @Valid LDAPImportUserParams importParams, @Schema(name="connection_params", description="Connection params", implementation=LDAPConnectionParams.class) @JSONField(name="connection_params") @JsonProperty(value="connection_params") @NotNull @Valid LDAPConnectionParams connectionParams) {
}

