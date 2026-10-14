/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.securityEvent;

import org.apache.wss4j.stax.securityEvent.IssuedTokenSecurityEvent;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.xml.security.stax.securityToken.SecurityToken;

public class SecurityContextTokenSecurityEvent
extends IssuedTokenSecurityEvent<SecurityToken> {
    private boolean externalUriRef;
    private String issuerName;

    public SecurityContextTokenSecurityEvent() {
        super(WSSecurityEventConstants.SECURITY_CONTEXT_TOKEN);
    }

    public boolean isExternalUriRef() {
        return this.externalUriRef;
    }

    public void setExternalUriRef(boolean externalUriRef) {
        this.externalUriRef = externalUriRef;
    }

    @Override
    public String getIssuerName() {
        return this.issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }
}

