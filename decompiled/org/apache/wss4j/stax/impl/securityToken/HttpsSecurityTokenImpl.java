/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.securityToken;

import java.security.Principal;
import java.security.cert.X509Certificate;
import javax.security.auth.Subject;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.securityToken.HttpsSecurityToken;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.impl.securityToken.AbstractInboundSecurityToken;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;

public class HttpsSecurityTokenImpl
extends AbstractInboundSecurityToken
implements HttpsSecurityToken {
    private String username;
    private final AuthenticationType authenticationType;
    private Principal principal;

    public HttpsSecurityTokenImpl() {
        super(null, IDGenerator.generateID(null), WSSecurityTokenConstants.KeyIdentifier_NoKeyInfo, true);
        this.authenticationType = AuthenticationType.noAuthentication;
    }

    public HttpsSecurityTokenImpl(X509Certificate x509Certificate) {
        super(null, IDGenerator.generateID(null), WSSecurityTokenConstants.KeyIdentifier_NoKeyInfo, true);
        this.setX509Certificates(new X509Certificate[]{x509Certificate});
        this.authenticationType = AuthenticationType.httpsClientAuthentication;
    }

    public HttpsSecurityTokenImpl(boolean basicAuthentication, String username) {
        super(null, IDGenerator.generateID(null), WSSecurityTokenConstants.KeyIdentifier_NoKeyInfo, true);
        this.authenticationType = basicAuthentication ? AuthenticationType.httpBasicAuthentication : AuthenticationType.httpDigestAuthentication;
        this.username = username;
    }

    @Override
    public SecurityTokenConstants.TokenType getTokenType() {
        return WSSecurityTokenConstants.HTTPS_TOKEN;
    }

    public String getUsername() {
        return this.username;
    }

    public AuthenticationType getAuthenticationType() {
        return this.authenticationType;
    }

    @Override
    public Subject getSubject() throws WSSecurityException {
        return null;
    }

    @Override
    public Principal getPrincipal() throws WSSecurityException {
        if (this.principal == null) {
            try {
                X509Certificate[] certs = this.getX509Certificates();
                if (certs != null && certs.length > 0) {
                    this.principal = certs[0].getSubjectX500Principal();
                    return this.principal;
                }
            }
            catch (XMLSecurityException e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, (Exception)e);
            }
        }
        return this.principal;
    }

    private static enum AuthenticationType {
        httpsClientAuthentication,
        httpBasicAuthentication,
        httpDigestAuthentication,
        noAuthentication;

    }
}

