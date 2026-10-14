/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.securityToken;

import java.io.IOException;
import java.security.Key;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.principal.SAMLTokenPrincipal;
import org.apache.wss4j.common.saml.OpenSAMLUtil;
import org.apache.wss4j.common.saml.SAMLKeyInfo;
import org.apache.wss4j.common.saml.SAMLUtil;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.stax.ext.WSInboundSecurityContext;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.securityToken.SamlSecurityToken;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.xml.security.algorithms.JCEMapper;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.impl.securityToken.AbstractInboundSecurityToken;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;
import org.opensaml.saml.common.SAMLVersion;
import org.w3c.dom.Element;

public class SamlSecurityTokenImpl
extends AbstractInboundSecurityToken
implements SamlSecurityToken {
    private final SamlAssertionWrapper samlAssertionWrapper;
    private InboundSecurityToken subjectSecurityToken;
    private Crypto crypto;
    private WSSSecurityProperties securityProperties;
    private Principal principal;
    private SAMLKeyInfo subjectKeyInfo;
    private byte[] secret;
    private Key key;

    public SamlSecurityTokenImpl(WSInboundSecurityContext wsInboundSecurityContext, String id, SecurityTokenConstants.KeyIdentifier keyIdentifier, WSSSecurityProperties securityProperties) throws WSSecurityException {
        super(wsInboundSecurityContext, id, keyIdentifier, false);
        this.securityProperties = securityProperties;
        if (securityProperties.getCallbackHandler() != null) {
            Element assertionElem;
            WSPasswordCallback pwcb = new WSPasswordCallback(id, 7);
            try {
                securityProperties.getCallbackHandler().handle(new Callback[]{pwcb});
            }
            catch (IOException | UnsupportedCallbackException e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "noPassword");
            }
            this.secret = pwcb.getKey();
            this.key = pwcb.getKeyObject();
            if (this.key instanceof PrivateKey) {
                super.setAsymmetric(true);
            }
            if ((assertionElem = pwcb.getCustomToken()) != null && "Assertion".equals(assertionElem.getLocalName()) && ("urn:oasis:names:tc:SAML:1.0:assertion".equals(assertionElem.getNamespaceURI()) || "urn:oasis:names:tc:SAML:2.0:assertion".equals(assertionElem.getNamespaceURI()))) {
                this.samlAssertionWrapper = new SamlAssertionWrapper(assertionElem);
                this.subjectKeyInfo = SAMLUtil.getCredentialFromSubject(this.samlAssertionWrapper, null, securityProperties.getSignatureVerificationCrypto(), securityProperties.getCallbackHandler());
            } else {
                this.samlAssertionWrapper = null;
            }
            if (this.samlAssertionWrapper == null && this.secret == null && this.key == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.SECURITY_TOKEN_UNAVAILABLE, "noToken", new Object[]{id});
            }
        } else {
            throw new WSSecurityException(WSSecurityException.ErrorCode.SECURITY_TOKEN_UNAVAILABLE, "noToken", new Object[]{id});
        }
    }

    public SamlSecurityTokenImpl(SamlAssertionWrapper samlAssertionWrapper, InboundSecurityToken subjectSecurityToken, WSInboundSecurityContext wsInboundSecurityContext, Crypto crypto, SecurityTokenConstants.KeyIdentifier keyIdentifier, WSSSecurityProperties securityProperties) {
        super(wsInboundSecurityContext, samlAssertionWrapper.getId(), keyIdentifier, true);
        this.samlAssertionWrapper = samlAssertionWrapper;
        this.crypto = crypto;
        this.subjectSecurityToken = subjectSecurityToken;
        this.securityProperties = securityProperties;
    }

    @Override
    public boolean isAsymmetric() throws XMLSecurityException {
        if (this.subjectSecurityToken != null && this.subjectSecurityToken.isAsymmetric()) {
            return true;
        }
        return super.isAsymmetric();
    }

    @Override
    protected Key getKey(String algorithmURI, XMLSecurityConstants.AlgorithmUsage algorithmUsage, String correlationID) throws XMLSecurityException {
        Key key = null;
        if (this.key != null) {
            key = this.key;
        } else if (this.secret != null) {
            String keyAlgorithm = JCEMapper.getJCEKeyAlgorithmFromURI(algorithmURI);
            key = new SecretKeySpec(this.secret, keyAlgorithm);
        } else if (this.subjectSecurityToken != null) {
            key = this.subjectSecurityToken.getSecretKey(algorithmURI, algorithmUsage, correlationID);
        } else if (this.subjectKeyInfo != null && this.subjectKeyInfo.getSecret() != null) {
            String keyAlgorithm = JCEMapper.getJCEKeyAlgorithmFromURI(algorithmURI);
            key = new SecretKeySpec(this.subjectKeyInfo.getSecret(), keyAlgorithm);
        }
        if (key != null) {
            super.setSecretKey(algorithmURI, key);
            return key;
        }
        return super.getKey(algorithmURI, algorithmUsage, correlationID);
    }

    @Override
    protected PublicKey getPubKey(String algorithmURI, XMLSecurityConstants.AlgorithmUsage algorithmUsage, String correlationID) throws XMLSecurityException {
        if (this.subjectSecurityToken != null) {
            return this.subjectSecurityToken.getPublicKey(algorithmURI, algorithmUsage, correlationID);
        }
        if (this.subjectKeyInfo != null && this.subjectKeyInfo.getPublicKey() != null) {
            return this.subjectKeyInfo.getPublicKey();
        }
        return super.getPubKey(algorithmURI, algorithmUsage, correlationID);
    }

    @Override
    public PublicKey getPublicKey() throws XMLSecurityException {
        if (this.subjectSecurityToken != null) {
            return this.subjectSecurityToken.getPublicKey();
        }
        if (this.subjectKeyInfo != null && this.subjectKeyInfo.getPublicKey() != null) {
            return this.subjectKeyInfo.getPublicKey();
        }
        return super.getPublicKey();
    }

    @Override
    public X509Certificate[] getX509Certificates() throws XMLSecurityException {
        if (this.subjectSecurityToken != null) {
            return this.subjectSecurityToken.getX509Certificates();
        }
        if (this.subjectKeyInfo != null && this.subjectKeyInfo.getCerts() != null) {
            return this.subjectKeyInfo.getCerts();
        }
        return super.getX509Certificates();
    }

    @Override
    public void verify() throws XMLSecurityException {
        if (this.samlAssertionWrapper == null) {
            return;
        }
        String confirmMethod = null;
        List<String> methods = this.samlAssertionWrapper.getConfirmationMethods();
        if (methods != null && !methods.isEmpty()) {
            confirmMethod = methods.get(0);
        }
        if (!OpenSAMLUtil.isMethodHolderOfKey(confirmMethod) && !this.samlAssertionWrapper.isSigned()) {
            PublicKey publicKey;
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
                this.crypto.verifyTrust(x509Certificates, enableRevocation, subjectCertConstraints, issuerCertConstraints);
            }
            if ((publicKey = this.getPublicKey()) != null) {
                this.crypto.verifyTrust(publicKey);
            }
        }
    }

    @Override
    public SecurityTokenConstants.TokenType getTokenType() {
        if (this.samlAssertionWrapper != null && this.samlAssertionWrapper.getSamlVersion() == SAMLVersion.VERSION_10) {
            return WSSecurityTokenConstants.SAML_10_TOKEN;
        }
        if (this.samlAssertionWrapper != null && this.samlAssertionWrapper.getSamlVersion() == SAMLVersion.VERSION_11) {
            return WSSecurityTokenConstants.SAML_11_TOKEN;
        }
        return WSSecurityTokenConstants.SAML_20_TOKEN;
    }

    @Override
    public Subject getSubject() throws WSSecurityException {
        return null;
    }

    @Override
    public Principal getPrincipal() throws WSSecurityException {
        if (this.principal == null) {
            this.principal = new SAMLTokenPrincipal(){

                @Override
                public SamlAssertionWrapper getToken() {
                    return SamlSecurityTokenImpl.this.samlAssertionWrapper;
                }

                @Override
                public String getName() {
                    return SamlSecurityTokenImpl.this.samlAssertionWrapper.getSubjectName();
                }

                @Override
                public String getId() {
                    return SamlSecurityTokenImpl.this.samlAssertionWrapper.getId();
                }
            };
        }
        return this.principal;
    }

    @Override
    public SamlAssertionWrapper getSamlAssertionWrapper() {
        return this.samlAssertionWrapper;
    }
}

