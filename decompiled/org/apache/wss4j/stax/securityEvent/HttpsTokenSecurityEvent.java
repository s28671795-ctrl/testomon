/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.securityEvent;

import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.wss4j.stax.securityToken.HttpsSecurityToken;
import org.apache.xml.security.stax.securityEvent.TokenSecurityEvent;

public class HttpsTokenSecurityEvent
extends TokenSecurityEvent<HttpsSecurityToken> {
    private AuthenticationType authenticationType;
    private String issuerName;

    public HttpsTokenSecurityEvent() {
        super(WSSecurityEventConstants.HTTPS_TOKEN);
    }

    public AuthenticationType getAuthenticationType() {
        return this.authenticationType;
    }

    public void setAuthenticationType(AuthenticationType authenticationType) {
        this.authenticationType = authenticationType;
    }

    public String getIssuerName() {
        return this.issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    public static enum AuthenticationType {
        HttpBasicAuthentication,
        HttpDigestAuthentication,
        HttpsClientCertificateAuthentication,
        HttpsNoAuthentication;

    }
}

