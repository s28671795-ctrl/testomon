/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.securityToken;

import java.security.Key;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.CryptoType;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.principal.PublicKeyPrincipalImpl;
import org.apache.wss4j.stax.ext.WSInboundSecurityContext;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.wss4j.stax.securityToken.X509SecurityToken;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class X509SecurityTokenImpl
extends org.apache.xml.security.stax.impl.securityToken.X509SecurityToken
implements X509SecurityToken {
    private static final transient Logger LOG = LoggerFactory.getLogger(X509SecurityTokenImpl.class);
    private CallbackHandler callbackHandler;
    private Crypto crypto;
    private WSSSecurityProperties securityProperties;
    private Principal principal;

    protected X509SecurityTokenImpl(SecurityTokenConstants.TokenType tokenType, WSInboundSecurityContext wsInboundSecurityContext, Crypto crypto, CallbackHandler callbackHandler, String id, SecurityTokenConstants.KeyIdentifier keyIdentifier, WSSSecurityProperties securityProperties, boolean includedInMessage) {
        super(tokenType, wsInboundSecurityContext, id, keyIdentifier, includedInMessage);
        this.crypto = crypto;
        this.callbackHandler = callbackHandler;
        this.securityProperties = securityProperties;
    }

    protected Crypto getCrypto() {
        return this.crypto;
    }

    protected void setCrypto(Crypto crypto) {
        this.crypto = crypto;
    }

    public CallbackHandler getCallbackHandler() {
        return this.callbackHandler;
    }

    @Override
    public Key getKey(String algorithmURI, XMLSecurityConstants.AlgorithmUsage algorithmUsage, String correlationID) throws XMLSecurityException {
        WSPasswordCallback pwCb = new WSPasswordCallback(this.getAlias(), 1);
        WSSUtils.doPasswordCallback(this.getCallbackHandler(), pwCb);
        try {
            return this.getCrypto().getPrivateKey(this.getAlias(), pwCb.getPassword());
        }
        catch (WSSecurityException ex) {
            Crypto decCrypto = this.securityProperties.getDecryptionCrypto();
            if (decCrypto != null && decCrypto != this.getCrypto()) {
                return decCrypto.getPrivateKey(this.getAlias(), pwCb.getPassword());
            }
            throw ex;
        }
    }

    @Override
    public X509Certificate[] getX509Certificates() throws XMLSecurityException {
        String alias;
        if (super.getX509Certificates() == null && (alias = this.getAlias()) != null) {
            CryptoType cryptoType = new CryptoType(CryptoType.TYPE.ALIAS);
            cryptoType.setAlias(alias);
            this.setX509Certificates(this.getCrypto().getX509Certificates(cryptoType));
        }
        return super.getX509Certificates();
    }

    @Override
    public void verify() throws XMLSecurityException {
        X509Certificate[] x509Certificates = this.getX509Certificates();
        if (x509Certificates != null && x509Certificates.length > 0) {
            boolean enableRevocation = false;
            Collection<Pattern> subjectCertConstraints = null;
            Collection<Pattern> issuerCertConstraints = null;
            if (this.securityProperties != null) {
                enableRevocation = this.securityProperties.isEnableRevocation();
                subjectCertConstraints = this.securityProperties.getSubjectCertConstraints();
                issuerCertConstraints = this.securityProperties.getIssuerDNConstraints();
            }
            this.getCrypto().verifyTrust(x509Certificates, enableRevocation, subjectCertConstraints, issuerCertConstraints);
        }
    }

    protected boolean matches(X509Certificate cert, Collection<Pattern> subjectDNPatterns) {
        if (subjectDNPatterns.isEmpty()) {
            LOG.warn("No Subject DN Certificate Constraints were defined. This could be a security issue");
        }
        if (!subjectDNPatterns.isEmpty()) {
            if (cert == null) {
                LOG.debug("The certificate is null so no constraints matching was possible");
                return false;
            }
            String subjectName = cert.getSubjectX500Principal().getName();
            boolean subjectMatch = false;
            for (Pattern subjectDNPattern : subjectDNPatterns) {
                Matcher matcher = subjectDNPattern.matcher(subjectName);
                if (!matcher.matches()) continue;
                LOG.debug("Subject DN {} matches with pattern {}", (Object)subjectName, (Object)subjectDNPattern);
                subjectMatch = true;
                break;
            }
            if (!subjectMatch) {
                return false;
            }
        }
        return true;
    }

    protected abstract String getAlias() throws XMLSecurityException;

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
                this.principal = new PublicKeyPrincipalImpl(this.getPublicKey());
                return this.principal;
            }
            catch (XMLSecurityException e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, (Exception)e);
            }
        }
        return this.principal;
    }

    @Override
    public SecurityTokenConstants.TokenType getTokenType() {
        SecurityTokenConstants.TokenType storedTokenType = super.getTokenType();
        if (WSSecurityTokenConstants.X509V3Token.equals(storedTokenType)) {
            try {
                X509Certificate[] certs = super.getX509Certificates();
                if (certs != null && certs.length > 0 && certs[0].getVersion() == 1) {
                    return WSSecurityTokenConstants.X509V1Token;
                }
            }
            catch (XMLSecurityException e) {
                return storedTokenType;
            }
        }
        return storedTokenType;
    }
}

