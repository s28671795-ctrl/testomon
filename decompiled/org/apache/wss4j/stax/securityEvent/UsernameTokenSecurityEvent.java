/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.securityEvent;

import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.wss4j.stax.securityToken.UsernameSecurityToken;
import org.apache.xml.security.stax.securityEvent.TokenSecurityEvent;

public class UsernameTokenSecurityEvent
extends TokenSecurityEvent<UsernameSecurityToken> {
    private String usernameTokenProfile;

    public UsernameTokenSecurityEvent() {
        super(WSSecurityEventConstants.USERNAME_TOKEN);
    }

    public WSSConstants.UsernameTokenPasswordType getUsernameTokenPasswordType() {
        return ((UsernameSecurityToken)this.getSecurityToken()).getUsernameTokenPasswordType();
    }

    public String getUsernameTokenProfile() {
        return this.usernameTokenProfile;
    }

    public void setUsernameTokenProfile(String usernameTokenProfile) {
        this.usernameTokenProfile = usernameTokenProfile;
    }
}

