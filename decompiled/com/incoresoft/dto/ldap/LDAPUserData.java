/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.ldap;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.ldap.SidDecoder;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Optional;
import org.ldaptive.LdapAttribute;
import org.ldaptive.LdapEntry;

@Schema(name="LDAPUserDTO", description="LDAP user class structure")
public record LDAPUserData(@Schema(name="login", description="User login", example="admin", requiredMode=Schema.RequiredMode.REQUIRED, implementation=String.class) @JSONField(name="login") @JsonProperty(value="login") String login, @Schema(name="name", description="User name", example="admin", requiredMode=Schema.RequiredMode.REQUIRED, implementation=String.class) @JSONField(name="name") @JsonProperty(value="name") String name, @Schema(name="email", description="User email", example="test@test.com", requiredMode=Schema.RequiredMode.REQUIRED, implementation=String.class) @JSONField(name="email") @JsonProperty(value="email") String email, @Schema(name="sid", description="User sid", example="S-1-5-21-1234567890-1234567890-1234567890-500", requiredMode=Schema.RequiredMode.REQUIRED, implementation=String.class) @JSONField(name="sid") @JsonProperty(value="sid") String sid) {
    public LDAPUserData(LdapEntry entry) {
        this(Optional.ofNullable(entry.getAttribute("sAMAccountName")).map(LdapAttribute::getStringValue).map(String::trim).orElse(null), Optional.ofNullable(entry.getAttribute("name")).map(LdapAttribute::getStringValue).map(String::trim).orElse(null), LDAPUserData.getEmailAttribute(entry), Optional.ofNullable(entry.getAttribute("objectSid")).map(LdapAttribute::getBinaryValue).map(SidDecoder::decode).orElse(null));
    }

    private static String getEmailAttribute(LdapEntry entry) {
        LdapAttribute userPrincipalNameAttribute;
        String email = null;
        LdapAttribute mailAttribute = entry.getAttribute("mail");
        if (mailAttribute != null) {
            email = mailAttribute.getStringValue();
        }
        if ((userPrincipalNameAttribute = entry.getAttribute("userPrincipalName")) != null) {
            email = userPrincipalNameAttribute.getStringValue();
        }
        if (email != null) {
            email = email.trim();
        }
        return email;
    }
}

