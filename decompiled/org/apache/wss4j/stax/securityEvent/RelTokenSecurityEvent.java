/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.securityEvent;

import org.apache.wss4j.stax.securityEvent.IssuedTokenSecurityEvent;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.xml.security.stax.securityToken.SecurityToken;

public class RelTokenSecurityEvent
extends IssuedTokenSecurityEvent<SecurityToken> {
    private String issuerName;

    public RelTokenSecurityEvent() {
        super(WSSecurityEventConstants.REL_TOKEN);
    }

    @Override
    public String getIssuerName() {
        return this.issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }
}

