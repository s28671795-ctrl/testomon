/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.ldap;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Schema(name="LDAPImportUserParams", description="Ldap user import")
public record LDAPImportUserParams(@ArraySchema(arraySchema=@Schema(name="user_sids", description="LDAP sids of users to add", requiredMode=Schema.RequiredMode.REQUIRED), schema=@Schema(name="user_sids", implementation=String.class, example="S-1-5-21-2127521184-1604012920-1887927527-72713")) @JSONField(name="user_sids") @JsonProperty(value="user_sids") @NotEmpty Set<String> sids, @ArraySchema(arraySchema=@Schema(name="role_ids", description="Role ids to assign to users", requiredMode=Schema.RequiredMode.REQUIRED), schema=@Schema(name="role_ids", implementation=Integer.class, example="1")) @JSONField(name="role_ids") @JsonProperty(value="role_ids") @NotEmpty(message="AT_LEAST_ONE_ROLE_MUST_BE_SELECTED") @NotEmpty(message="AT_LEAST_ONE_ROLE_MUST_BE_SELECTED") Set<@Min(value=1L, message="ROLE_MUST_BE_SELECTED") Integer> roleIds) {
}

