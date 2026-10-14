/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyhandlers;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.rt.security.utils.SecurityUtils;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.apache.cxf.ws.security.tokenstore.TokenStoreUtils;
import org.apache.cxf.ws.security.wss4j.TokenStoreCallbackHandler;
import org.apache.cxf.ws.security.wss4j.WSS4JUtils;
import org.apache.cxf.ws.security.wss4j.policyhandlers.AbstractStaxBindingHandler;
import org.apache.neethi.Assertion;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.KeyUtils;
import org.apache.wss4j.common.util.XMLUtils;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.AbstractSymmetricAsymmetricBinding;
import org.apache.wss4j.policy.model.AbstractToken;
import org.apache.wss4j.policy.model.AbstractTokenWrapper;
import org.apache.wss4j.policy.model.AlgorithmSuite;
import org.apache.wss4j.policy.model.IssuedToken;
import org.apache.wss4j.policy.model.KerberosToken;
import org.apache.wss4j.policy.model.SecureConversationToken;
import org.apache.wss4j.policy.model.SecurityContextToken;
import org.apache.wss4j.policy.model.SpnegoContextToken;
import org.apache.wss4j.policy.model.SymmetricBinding;
import org.apache.wss4j.policy.model.UsernameToken;
import org.apache.wss4j.policy.model.X509Token;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.OutboundSecurityContext;
import org.apache.xml.security.stax.ext.SecurePart;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.apache.xml.security.stax.securityEvent.AbstractSecuredElementSecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;

public class StaxSymmetricBindingHandler
extends AbstractStaxBindingHandler {
    private SymmetricBinding sbinding;
    private SoapMessage message;

    public StaxSymmetricBindingHandler(WSSSecurityProperties properties, SoapMessage msg, SymmetricBinding sbinding, OutboundSecurityContext outboundSecurityContext) {
        super(properties, msg, sbinding, outboundSecurityContext);
        this.message = msg;
        this.sbinding = sbinding;
    }

    private AbstractTokenWrapper getSignatureToken() {
        if (this.sbinding.getProtectionToken() != null) {
            return this.sbinding.getProtectionToken();
        }
        return this.sbinding.getSignatureToken();
    }

    private AbstractTokenWrapper getEncryptionToken() {
        if (this.sbinding.getProtectionToken() != null) {
            return this.sbinding.getProtectionToken();
        }
        return this.sbinding.getEncryptionToken();
    }

    public void handleBinding() {
        String symSignatureAlgorithm;
        AssertionInfoMap aim = this.getMessage().get(AssertionInfoMap.class);
        this.configureTimestamp(aim);
        this.assertPolicy(this.sbinding.getName());
        String asymSignatureAlgorithm = (String)this.getMessage().getContextualProperty("ws-security.asymmetric.signature.algorithm");
        if (asymSignatureAlgorithm != null && this.sbinding.getAlgorithmSuite() != null) {
            this.sbinding.getAlgorithmSuite().setAsymmetricSignature(asymSignatureAlgorithm);
        }
        if ((symSignatureAlgorithm = (String)this.getMessage().getContextualProperty("ws-security.symmetric.signature.algorithm")) != null && this.sbinding.getAlgorithmSuite() != null) {
            this.sbinding.getAlgorithmSuite().setSymmetricSignature(symSignatureAlgorithm);
        }
        WSSSecurityProperties properties = this.getProperties();
        TokenStoreCallbackHandler callbackHandler = new TokenStoreCallbackHandler(properties.getCallbackHandler(), TokenStoreUtils.getTokenStore(this.message));
        properties.setCallbackHandler(callbackHandler);
        if (this.sbinding.getProtectionOrder() == AbstractSymmetricAsymmetricBinding.ProtectionOrder.EncryptBeforeSigning) {
            this.doEncryptBeforeSign();
            this.assertPolicy(new QName(this.sbinding.getName().getNamespaceURI(), "EncryptBeforeSigning"));
        } else {
            this.doSignBeforeEncrypt();
            this.assertPolicy(new QName(this.sbinding.getName().getNamespaceURI(), "SignBeforeEncrypting"));
        }
        if (!this.isRequestor()) {
            properties.setEncryptSymmetricEncryptionKey(false);
        }
        this.configureLayout(aim);
        this.assertAlgorithmSuite(this.sbinding.getAlgorithmSuite());
        this.assertWSSProperties(this.sbinding.getName().getNamespaceURI());
        this.assertTrustProperties(this.sbinding.getName().getNamespaceURI());
        this.assertPolicy(new QName(this.sbinding.getName().getNamespaceURI(), "OnlySignEntireHeadersAndBody"));
        if (this.sbinding.isProtectTokens()) {
            this.assertPolicy(new QName(this.sbinding.getName().getNamespaceURI(), "ProtectTokens"));
        }
    }

    private void doEncryptBeforeSign() {
        try {
            AbstractTokenWrapper sigAbstractTokenWrapper;
            SecurePart part;
            org.apache.xml.security.stax.securityToken.SecurityToken securityToken;
            AbstractTokenWrapper encryptionWrapper = this.getEncryptionToken();
            this.assertTokenWrapper(encryptionWrapper);
            AbstractToken encryptionToken = encryptionWrapper.getToken();
            String tokenId = null;
            SecurityToken tok = null;
            if (encryptionToken instanceof KerberosToken) {
                tok = this.getSecurityToken();
                if (MessageUtils.isRequestor(this.message)) {
                    this.addKerberosToken((KerberosToken)encryptionToken, false, true, true);
                }
            } else if (encryptionToken instanceof IssuedToken) {
                tok = this.getSecurityToken();
                this.addIssuedToken(encryptionToken, tok, false, true);
                if (tok == null && !this.isRequestor()) {
                    securityToken = this.findInboundSecurityToken(WSSecurityEventConstants.SAML_TOKEN);
                    tokenId = WSS4JUtils.parseAndStoreStreamingSecurityToken(securityToken, this.message);
                }
            } else if (encryptionToken instanceof SecureConversationToken || encryptionToken instanceof SecurityContextToken || encryptionToken instanceof SpnegoContextToken) {
                tok = this.getSecurityToken();
                if (tok != null && this.isRequestor()) {
                    WSSSecurityProperties properties = this.getProperties();
                    XMLSecurityConstants.Action actionToPerform = WSSConstants.CUSTOM_TOKEN;
                    properties.addAction(actionToPerform);
                } else if (tok == null && !this.isRequestor()) {
                    securityToken = this.findInboundSecurityToken(WSSecurityEventConstants.SECURITY_CONTEXT_TOKEN);
                    tokenId = WSS4JUtils.parseAndStoreStreamingSecurityToken(securityToken, this.message);
                }
            } else if (encryptionToken instanceof X509Token) {
                if (this.isRequestor()) {
                    tokenId = this.setupEncryptedKey();
                } else {
                    securityToken = this.findEncryptedKeyToken();
                    tokenId = WSS4JUtils.parseAndStoreStreamingSecurityToken(securityToken, this.message);
                }
            } else if (encryptionToken instanceof UsernameToken) {
                this.unassertPolicy((Assertion)this.sbinding, "UsernameTokens not supported with Symmetric binding");
                return;
            }
            this.assertToken(encryptionToken);
            if (tok == null) {
                tokenId = XMLUtils.getIDFromReference(tokenId);
                tok = TokenStoreUtils.getTokenStore(this.message).getToken(tokenId);
            }
            if (!MessageUtils.isRequestor(this.message) || !(encryptionToken instanceof KerberosToken)) {
                this.storeSecurityToken(encryptionToken, tok);
            }
            List<SecurePart> encrParts = null;
            List<SecurePart> sigParts = null;
            try {
                encrParts = this.getEncryptedParts();
                sigParts = this.getSignedParts();
            }
            catch (SOAPException ex) {
                throw new Fault(ex);
            }
            this.addSupportingTokens();
            if (encryptionToken != null && !encrParts.isEmpty()) {
                if (this.isRequestor()) {
                    encrParts.addAll(this.encryptedTokensList);
                }
                if (this.sbinding.isEncryptSignature()) {
                    part = new SecurePart(new QName("http://www.w3.org/2000/09/xmldsig#", "Signature"), SecurePart.Modifier.Element);
                    encrParts.add(part);
                    if (this.signatureConfirmationAdded) {
                        part = new SecurePart(WSSConstants.TAG_WSSE11_SIG_CONF, SecurePart.Modifier.Element);
                        encrParts.add(part);
                    }
                    this.assertPolicy(new QName(this.sbinding.getName().getNamespaceURI(), "EncryptSignature"));
                }
                this.doEncryption(encryptionWrapper, encrParts);
            }
            if (this.timestampAdded) {
                part = new SecurePart(new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Timestamp"), SecurePart.Modifier.Element);
                sigParts.add(part);
            }
            sigParts.addAll(this.getSignedParts());
            if (!sigParts.isEmpty() && (sigAbstractTokenWrapper = this.getSignatureToken()) != null) {
                AbstractToken sigToken = sigAbstractTokenWrapper.getToken();
                if (this.isRequestor()) {
                    this.doSignature(sigAbstractTokenWrapper, sigToken, sigParts);
                } else {
                    this.addSignatureConfirmation(sigParts);
                    this.doSignature(sigAbstractTokenWrapper, sigToken, sigParts);
                }
            }
            this.removeSignatureIfSignedSAML();
            this.enforceEncryptBeforeSigningWithSignedSAML();
            this.prependSignatureToSC();
            this.putCustomTokenAfterSignature();
        }
        catch (RuntimeException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new Fault(ex);
        }
    }

    private void doSignBeforeEncrypt() {
        AbstractTokenWrapper sigAbstractTokenWrapper = this.getSignatureToken();
        this.assertTokenWrapper(sigAbstractTokenWrapper);
        AbstractToken sigToken = sigAbstractTokenWrapper.getToken();
        String sigTokId = null;
        try {
            SecurityToken sigTok = null;
            if (sigToken != null) {
                org.apache.xml.security.stax.securityToken.SecurityToken securityToken;
                if (sigToken instanceof KerberosToken) {
                    sigTok = this.getSecurityToken();
                    if (this.isRequestor()) {
                        this.addKerberosToken((KerberosToken)sigToken, false, true, true);
                    }
                } else if (sigToken instanceof IssuedToken) {
                    sigTok = this.getSecurityToken();
                    this.addIssuedToken(sigToken, sigTok, false, true);
                    if (sigTok == null && !this.isRequestor()) {
                        securityToken = this.findInboundSecurityToken(WSSecurityEventConstants.SAML_TOKEN);
                        sigTokId = WSS4JUtils.parseAndStoreStreamingSecurityToken(securityToken, this.message);
                    }
                } else if (sigToken instanceof SecureConversationToken || sigToken instanceof SecurityContextToken || sigToken instanceof SpnegoContextToken) {
                    sigTok = this.getSecurityToken();
                    if (sigTok != null && this.isRequestor()) {
                        WSSSecurityProperties properties = this.getProperties();
                        XMLSecurityConstants.Action actionToPerform = WSSConstants.CUSTOM_TOKEN;
                        properties.addAction(actionToPerform);
                    } else if (sigTok == null && !this.isRequestor()) {
                        securityToken = this.findInboundSecurityToken(WSSecurityEventConstants.SECURITY_CONTEXT_TOKEN);
                        sigTokId = WSS4JUtils.parseAndStoreStreamingSecurityToken(securityToken, this.message);
                    }
                } else if (sigToken instanceof X509Token) {
                    if (this.isRequestor()) {
                        sigTokId = this.setupEncryptedKey();
                    } else {
                        securityToken = this.findEncryptedKeyToken();
                        sigTokId = WSS4JUtils.parseAndStoreStreamingSecurityToken(securityToken, this.message);
                    }
                } else if (sigToken instanceof UsernameToken) {
                    this.unassertPolicy((Assertion)this.sbinding, "UsernameTokens not supported with Symmetric binding");
                    return;
                }
            } else {
                this.unassertPolicy((Assertion)this.sbinding, "No signature token");
                return;
            }
            this.assertToken(sigToken);
            if (sigTok == null && StringUtils.isEmpty(sigTokId)) {
                this.unassertPolicy((Assertion)sigAbstractTokenWrapper, "No signature token id");
                return;
            }
            if (sigTok == null) {
                sigTok = TokenStoreUtils.getTokenStore(this.message).getToken(sigTokId);
            }
            if (!MessageUtils.isRequestor(this.message) || !(sigToken instanceof KerberosToken)) {
                this.storeSecurityToken(sigToken, sigTok);
            }
            ArrayList<SecurePart> sigs = new ArrayList<SecurePart>();
            if (this.timestampAdded) {
                SecurePart part = new SecurePart(new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Timestamp"), SecurePart.Modifier.Element);
                sigs.add(part);
            }
            sigs.addAll(this.getSignedParts());
            if (!this.isRequestor()) {
                this.addSignatureConfirmation(sigs);
            }
            if (!sigs.isEmpty()) {
                this.doSignature(sigAbstractTokenWrapper, sigToken, sigs);
            }
            this.addSupportingTokens();
            this.removeSignatureIfSignedSAML();
            this.prependSignatureToSC();
            List<SecurePart> enc = this.getEncryptedParts();
            if (this.sbinding.isEncryptSignature()) {
                SecurePart part = new SecurePart(new QName("http://www.w3.org/2000/09/xmldsig#", "Signature"), SecurePart.Modifier.Element);
                enc.add(part);
                if (this.signatureConfirmationAdded) {
                    part = new SecurePart(WSSConstants.TAG_WSSE11_SIG_CONF, SecurePart.Modifier.Element);
                    enc.add(part);
                }
                this.assertPolicy(new QName(this.sbinding.getName().getNamespaceURI(), "EncryptSignature"));
            }
            if (this.isRequestor()) {
                enc.addAll(this.encryptedTokensList);
            }
            AbstractTokenWrapper encrAbstractTokenWrapper = this.getEncryptionToken();
            this.doEncryption(encrAbstractTokenWrapper, enc);
            this.putCustomTokenAfterSignature();
        }
        catch (Exception e) {
            throw new Fault(e);
        }
    }

    private void doEncryption(AbstractTokenWrapper recToken, List<SecurePart> encrParts) throws SOAPException {
        if (recToken != null && recToken.getToken() != null) {
            AbstractToken encrToken = recToken.getToken();
            AlgorithmSuite algorithmSuite = this.sbinding.getAlgorithmSuite();
            WSSSecurityProperties properties = this.getProperties();
            XMLSecurityConstants.Action actionToPerform = XMLSecurityConstants.ENCRYPT;
            if (recToken.getToken().getDerivedKeys() == AbstractToken.DerivedKeys.RequireDerivedKeys) {
                actionToPerform = WSSConstants.ENCRYPT_WITH_DERIVED_KEY;
                if (MessageUtils.isRequestor(this.message) && recToken.getToken() instanceof X509Token) {
                    properties.setDerivedKeyTokenReference(WSSConstants.DerivedKeyTokenReference.EncryptedKey);
                } else {
                    properties.setDerivedKeyTokenReference(WSSConstants.DerivedKeyTokenReference.DirectReference);
                }
                AlgorithmSuite.AlgorithmSuiteType algSuiteType = this.sbinding.getAlgorithmSuite().getAlgorithmSuiteType();
                properties.setDerivedEncryptionKeyLength(algSuiteType.getEncryptionDerivedKeyLength() / 8);
            }
            if (recToken.getVersion() == SPConstants.SPVersion.SP12) {
                properties.setUse200512Namespace(true);
            }
            properties.getEncryptionSecureParts().addAll(encrParts);
            properties.addAction(actionToPerform);
            if (this.isRequestor()) {
                properties.setEncryptionKeyIdentifier(this.getKeyIdentifierType(encrToken));
                properties.setDerivedKeyKeyIdentifier(WSSecurityTokenConstants.KEYIDENTIFIER_SECURITY_TOKEN_DIRECT_REFERENCE);
            } else if (recToken.getToken() instanceof KerberosToken && !this.isRequestor()) {
                properties.setEncryptionKeyIdentifier(WSSecurityTokenConstants.KEYIDENTIFIER_KERBEROS_SHA1_IDENTIFIER);
                properties.setDerivedKeyKeyIdentifier(WSSecurityTokenConstants.KEYIDENTIFIER_KERBEROS_SHA1_IDENTIFIER);
                if (recToken.getToken().getDerivedKeys() == AbstractToken.DerivedKeys.RequireDerivedKeys) {
                    properties.setEncryptionKeyIdentifier(WSSecurityTokenConstants.KEYIDENTIFIER_SECURITY_TOKEN_DIRECT_REFERENCE);
                }
            } else if ((recToken.getToken() instanceof IssuedToken || recToken.getToken() instanceof SecureConversationToken || recToken.getToken() instanceof SpnegoContextToken) && !this.isRequestor()) {
                properties.setEncryptionKeyIdentifier(WSSecurityTokenConstants.KEYIDENTIFIER_SECURITY_TOKEN_DIRECT_REFERENCE);
            } else {
                properties.setEncryptionKeyIdentifier(WSSecurityTokenConstants.KEYIDENTIFIER_ENCRYPTED_KEY_SHA1_IDENTIFIER);
                if (recToken.getToken().getDerivedKeys() == AbstractToken.DerivedKeys.RequireDerivedKeys) {
                    properties.setDerivedKeyKeyIdentifier(WSSecurityTokenConstants.KEYIDENTIFIER_ENCRYPTED_KEY_SHA1_IDENTIFIER);
                    properties.setEncryptionKeyIdentifier(WSSecurityTokenConstants.KEYIDENTIFIER_SECURITY_TOKEN_DIRECT_REFERENCE);
                    properties.setEncryptSymmetricEncryptionKey(false);
                }
            }
            SecurityTokenConstants.KeyIdentifier keyIdentifier = properties.getEncryptionKeyIdentifier();
            if (encrToken instanceof X509Token && this.isTokenRequired(encrToken.getIncludeTokenType()) && (WSSecurityTokenConstants.KeyIdentifier_IssuerSerial.equals(keyIdentifier) || WSSecurityTokenConstants.KEYIDENTIFIER_THUMBPRINT_IDENTIFIER.equals(keyIdentifier) || WSSecurityTokenConstants.KEYIDENTIFIER_SECURITY_TOKEN_DIRECT_REFERENCE.equals(keyIdentifier))) {
                properties.setIncludeEncryptionToken(true);
            } else {
                properties.setIncludeEncryptionToken(false);
            }
            properties.setEncryptionKeyTransportAlgorithm(algorithmSuite.getAlgorithmSuiteType().getAsymmetricKeyWrap());
            properties.setEncryptionSymAlgorithm(algorithmSuite.getAlgorithmSuiteType().getEncryption());
            properties.setEncryptionKeyTransportDigestAlgorithm(algorithmSuite.getAlgorithmSuiteType().getEncryptionDigest());
            properties.setEncryptionKeyTransportMGFAlgorithm(algorithmSuite.getAlgorithmSuiteType().getMGFAlgo());
            String encUser = (String)SecurityUtils.getSecurityPropertyValue("security.encryption.username", this.message);
            if (encUser == null) {
                encUser = (String)SecurityUtils.getSecurityPropertyValue("security.username", this.message);
            }
            if (encUser != null && properties.getEncryptionUser() == null) {
                properties.setEncryptionUser(encUser);
            }
            if ("useReqSigCert".equals(encUser)) {
                properties.setUseReqSigCertForEncryption(true);
            }
            if (encrToken instanceof KerberosToken || encrToken instanceof IssuedToken || encrToken instanceof SpnegoContextToken || encrToken instanceof SecurityContextToken || encrToken instanceof SecureConversationToken) {
                properties.setEncryptSymmetricEncryptionKey(false);
            }
        }
    }

    private void doSignature(AbstractTokenWrapper wrapper, AbstractToken policyToken, List<SecurePart> sigParts) throws WSSecurityException, SOAPException {
        WSSSecurityProperties properties = this.getProperties();
        XMLSecurityConstants.Action actionToPerform = XMLSecurityConstants.SIGNATURE;
        if (wrapper.getToken().getDerivedKeys() == AbstractToken.DerivedKeys.RequireDerivedKeys) {
            actionToPerform = WSSConstants.SIGNATURE_WITH_DERIVED_KEY;
            if (MessageUtils.isRequestor(this.message) && policyToken instanceof X509Token) {
                properties.setDerivedKeyTokenReference(WSSConstants.DerivedKeyTokenReference.EncryptedKey);
            } else {
                properties.setDerivedKeyTokenReference(WSSConstants.DerivedKeyTokenReference.DirectReference);
            }
            AlgorithmSuite.AlgorithmSuiteType algSuiteType = this.sbinding.getAlgorithmSuite().getAlgorithmSuiteType();
            properties.setDerivedSignatureKeyLength(algSuiteType.getSignatureDerivedKeyLength() / 8);
        }
        if (policyToken.getVersion() == SPConstants.SPVersion.SP12) {
            properties.setUse200512Namespace(true);
        }
        List<XMLSecurityConstants.Action> actionList = properties.getActions();
        boolean actionAdded = false;
        for (int i = 0; i < actionList.size(); ++i) {
            XMLSecurityConstants.Action action = actionList.get(i);
            if (!action.equals(WSSConstants.KERBEROS_TOKEN)) continue;
            actionList.add(i, actionToPerform);
            actionAdded = true;
            break;
        }
        if (!actionAdded) {
            actionList.add(actionToPerform);
        }
        properties.getSignatureSecureParts().addAll(sigParts);
        AbstractToken sigToken = wrapper.getToken();
        if (this.sbinding.isProtectTokens() && sigToken instanceof X509Token && this.isRequestor()) {
            SecurePart securePart = new SecurePart(new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptedKey"), SecurePart.Modifier.Element);
            properties.addSignaturePart(securePart);
        }
        this.configureSignature(sigToken, false);
        if (policyToken instanceof X509Token) {
            properties.setIncludeSignatureToken(false);
            if (this.isRequestor()) {
                properties.setSignatureKeyIdentifier(WSSecurityTokenConstants.KeyIdentifier_EncryptedKey);
            } else {
                properties.setSignatureKeyIdentifier(WSSecurityTokenConstants.KEYIDENTIFIER_ENCRYPTED_KEY_SHA1_IDENTIFIER);
                if (wrapper.getToken().getDerivedKeys() == AbstractToken.DerivedKeys.RequireDerivedKeys) {
                    properties.setDerivedKeyKeyIdentifier(WSSecurityTokenConstants.KEYIDENTIFIER_ENCRYPTED_KEY_SHA1_IDENTIFIER);
                    properties.setSignatureKeyIdentifier(WSSecurityTokenConstants.KEYIDENTIFIER_SECURITY_TOKEN_DIRECT_REFERENCE);
                }
            }
        } else if (policyToken instanceof KerberosToken) {
            if (this.isRequestor()) {
                properties.setDerivedKeyKeyIdentifier(WSSecurityTokenConstants.KEYIDENTIFIER_SECURITY_TOKEN_DIRECT_REFERENCE);
            } else {
                if (wrapper.getToken().getDerivedKeys() == AbstractToken.DerivedKeys.RequireDerivedKeys) {
                    properties.setSignatureKeyIdentifier(WSSecurityTokenConstants.KEYIDENTIFIER_SECURITY_TOKEN_DIRECT_REFERENCE);
                } else {
                    properties.setSignatureKeyIdentifier(WSSecurityTokenConstants.KEYIDENTIFIER_KERBEROS_SHA1_IDENTIFIER);
                }
                properties.setDerivedKeyKeyIdentifier(WSSecurityTokenConstants.KEYIDENTIFIER_KERBEROS_SHA1_IDENTIFIER);
            }
        } else if (policyToken instanceof IssuedToken || policyToken instanceof SecurityContextToken || policyToken instanceof SecureConversationToken || policyToken instanceof SpnegoContextToken) {
            if (!this.isRequestor()) {
                properties.setIncludeSignatureToken(false);
            } else {
                properties.setIncludeSignatureToken(true);
            }
            properties.setDerivedKeyKeyIdentifier(WSSecurityTokenConstants.KEYIDENTIFIER_SECURITY_TOKEN_DIRECT_REFERENCE);
        }
        if (sigToken.getDerivedKeys() == AbstractToken.DerivedKeys.RequireDerivedKeys) {
            properties.setSignatureAlgorithm(this.sbinding.getAlgorithmSuite().getSymmetricSignature());
        }
    }

    private String setupEncryptedKey() throws WSSecurityException {
        Instant created = Instant.now();
        Instant expires = created.plusSeconds(WSS4JUtils.getSecurityTokenLifetime(this.message) / 1000L);
        SecurityToken tempTok = new SecurityToken(IDGenerator.generateID(null), created, expires);
        KeyGenerator keyGenerator = KeyUtils.getKeyGenerator(this.sbinding.getAlgorithmSuite().getAlgorithmSuiteType().getEncryption());
        SecretKey symmetricKey = keyGenerator.generateKey();
        tempTok.setKey(symmetricKey);
        tempTok.setSecret(symmetricKey.getEncoded());
        TokenStoreUtils.getTokenStore(this.message).add(tempTok);
        return tempTok.getId();
    }

    private org.apache.xml.security.stax.securityToken.SecurityToken findEncryptedKeyToken() throws XMLSecurityException {
        List incomingEventList = (List)this.message.getExchange().get(SecurityEvent.class.getName() + ".in");
        if (incomingEventList != null) {
            org.apache.xml.security.stax.securityToken.SecurityToken token;
            for (SecurityEvent incomingEvent : incomingEventList) {
                if (WSSecurityEventConstants.ENCRYPTED_PART != incomingEvent.getSecurityEventType() && WSSecurityEventConstants.EncryptedElement != incomingEvent.getSecurityEventType()) continue;
                token = ((AbstractSecuredElementSecurityEvent)incomingEvent).getSecurityToken();
                if (token != null && token.getKeyWrappingToken() != null && token.getKeyWrappingToken().getSecretKey() != null && token.getKeyWrappingToken().getSha1Identifier() != null) {
                    return token.getKeyWrappingToken();
                }
                if (token == null || token.getSecretKey() == null || token.getSha1Identifier() == null) continue;
                return token;
            }
            for (SecurityEvent incomingEvent : incomingEventList) {
                if (WSSecurityEventConstants.SIGNED_PART != incomingEvent.getSecurityEventType() && WSSecurityEventConstants.SignedElement != incomingEvent.getSecurityEventType()) continue;
                token = ((AbstractSecuredElementSecurityEvent)incomingEvent).getSecurityToken();
                if (token != null && token.getKeyWrappingToken() != null && token.getKeyWrappingToken().getSecretKey() != null && token.getKeyWrappingToken().getSha1Identifier() != null) {
                    return token.getKeyWrappingToken();
                }
                if (token == null || token.getSecretKey() == null || token.getSha1Identifier() == null) continue;
                return token;
            }
        }
        return null;
    }
}

