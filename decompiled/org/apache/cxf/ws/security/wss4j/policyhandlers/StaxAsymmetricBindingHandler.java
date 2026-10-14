/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyhandlers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.rt.security.utils.SecurityUtils;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.apache.cxf.ws.security.tokenstore.TokenStoreUtils;
import org.apache.cxf.ws.security.wss4j.TokenStoreCallbackHandler;
import org.apache.cxf.ws.security.wss4j.policyhandlers.AbstractStaxBindingHandler;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.AbstractSymmetricAsymmetricBinding;
import org.apache.wss4j.policy.model.AbstractToken;
import org.apache.wss4j.policy.model.AbstractTokenWrapper;
import org.apache.wss4j.policy.model.AlgorithmSuite;
import org.apache.wss4j.policy.model.AsymmetricBinding;
import org.apache.wss4j.policy.model.IssuedToken;
import org.apache.wss4j.policy.model.SamlToken;
import org.apache.wss4j.policy.model.SecureConversationToken;
import org.apache.wss4j.policy.model.SecurityContextToken;
import org.apache.wss4j.policy.model.SpnegoContextToken;
import org.apache.wss4j.policy.model.X509Token;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.xml.security.stax.ext.OutboundSecurityContext;
import org.apache.xml.security.stax.ext.SecurePart;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;

public class StaxAsymmetricBindingHandler
extends AbstractStaxBindingHandler {
    private static final Logger LOG = LogUtils.getL7dLogger(StaxAsymmetricBindingHandler.class);
    private AsymmetricBinding abinding;
    private SoapMessage message;

    public StaxAsymmetricBindingHandler(WSSSecurityProperties properties, SoapMessage msg, AsymmetricBinding abinding, OutboundSecurityContext outboundSecurityContext) {
        super(properties, msg, abinding, outboundSecurityContext);
        this.message = msg;
        this.abinding = abinding;
    }

    public void handleBinding() {
        String symSignatureAlgorithm;
        AssertionInfoMap aim = this.getMessage().get(AssertionInfoMap.class);
        this.configureTimestamp(aim);
        this.assertPolicy(this.abinding.getName());
        String asymSignatureAlgorithm = (String)this.getMessage().getContextualProperty("ws-security.asymmetric.signature.algorithm");
        if (asymSignatureAlgorithm != null && this.abinding.getAlgorithmSuite() != null) {
            this.abinding.getAlgorithmSuite().setAsymmetricSignature(asymSignatureAlgorithm);
        }
        if ((symSignatureAlgorithm = (String)this.getMessage().getContextualProperty("ws-security.symmetric.signature.algorithm")) != null && this.abinding.getAlgorithmSuite() != null) {
            this.abinding.getAlgorithmSuite().setSymmetricSignature(symSignatureAlgorithm);
        }
        if (this.abinding.getProtectionOrder() == AbstractSymmetricAsymmetricBinding.ProtectionOrder.EncryptBeforeSigning) {
            this.doEncryptBeforeSign();
            this.assertPolicy(new QName(this.abinding.getName().getNamespaceURI(), "EncryptBeforeSigning"));
        } else {
            this.doSignBeforeEncrypt();
            this.assertPolicy(new QName(this.abinding.getName().getNamespaceURI(), "SignBeforeEncrypting"));
        }
        this.configureLayout(aim);
        this.assertAlgorithmSuite(this.abinding.getAlgorithmSuite());
        this.assertWSSProperties(this.abinding.getName().getNamespaceURI());
        this.assertTrustProperties(this.abinding.getName().getNamespaceURI());
        this.assertPolicy(new QName(this.abinding.getName().getNamespaceURI(), "OnlySignEntireHeadersAndBody"));
        if (this.abinding.isProtectTokens()) {
            this.assertPolicy(new QName(this.abinding.getName().getNamespaceURI(), "ProtectTokens"));
        }
    }

    private void doSignBeforeEncrypt() {
        try {
            AbstractTokenWrapper encToken;
            AbstractTokenWrapper initiatorWrapper = this.abinding.getInitiatorSignatureToken();
            if (initiatorWrapper == null) {
                initiatorWrapper = this.abinding.getInitiatorToken();
            }
            if (initiatorWrapper != null) {
                this.assertTokenWrapper(initiatorWrapper);
                AbstractToken initiatorToken = initiatorWrapper.getToken();
                if (initiatorToken instanceof IssuedToken) {
                    SecurityToken sigTok = this.getSecurityToken();
                    this.addIssuedToken(initiatorToken, sigTok, false, true);
                    if (sigTok != null) {
                        this.storeSecurityToken(initiatorToken, sigTok);
                        this.outboundSecurityContext.remove("PROP_USE_THIS_TOKEN_ID_FOR_ENCRYPTION");
                    }
                    WSSSecurityProperties properties = this.getProperties();
                    TokenStoreCallbackHandler callbackHandler = new TokenStoreCallbackHandler(properties.getCallbackHandler(), TokenStoreUtils.getTokenStore(this.message));
                    properties.setCallbackHandler(callbackHandler);
                } else if (initiatorToken instanceof SamlToken) {
                    this.addSamlToken((SamlToken)initiatorToken, false, true);
                }
                this.assertToken(initiatorToken);
            }
            ArrayList<SecurePart> sigs = new ArrayList<SecurePart>();
            if (this.timestampAdded) {
                SecurePart part = new SecurePart(new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Timestamp"), SecurePart.Modifier.Element);
                sigs.add(part);
            }
            sigs.addAll(this.getSignedParts());
            if (this.isRequestor() && initiatorWrapper != null) {
                this.doSignature(initiatorWrapper, sigs);
            } else if (!this.isRequestor()) {
                this.addSignatureConfirmation(sigs);
                AbstractTokenWrapper recipientSignatureToken = this.abinding.getRecipientSignatureToken();
                if (recipientSignatureToken == null) {
                    recipientSignatureToken = this.abinding.getRecipientToken();
                }
                if (recipientSignatureToken != null) {
                    this.assertTokenWrapper(recipientSignatureToken);
                    this.assertToken(recipientSignatureToken.getToken());
                }
                if (recipientSignatureToken != null && !sigs.isEmpty()) {
                    this.doSignature(recipientSignatureToken, sigs);
                }
            }
            this.addSupportingTokens();
            this.removeSignatureIfSignedSAML();
            this.prependSignatureToSC();
            List<SecurePart> enc = this.getEncryptedParts();
            if (this.abinding.isEncryptSignature()) {
                SecurePart part = new SecurePart(new QName("http://www.w3.org/2000/09/xmldsig#", "Signature"), SecurePart.Modifier.Element);
                enc.add(part);
                if (this.signatureConfirmationAdded) {
                    SecurePart securePart = new SecurePart(WSSConstants.TAG_WSSE11_SIG_CONF, SecurePart.Modifier.Element);
                    enc.add(securePart);
                }
                this.assertPolicy(new QName(this.abinding.getName().getNamespaceURI(), "EncryptSignature"));
            }
            if (this.isRequestor()) {
                enc.addAll(this.encryptedTokensList);
                encToken = this.abinding.getRecipientEncryptionToken();
                if (encToken == null) {
                    encToken = this.abinding.getRecipientToken();
                }
            } else {
                encToken = this.abinding.getInitiatorEncryptionToken();
                if (encToken == null) {
                    encToken = this.abinding.getInitiatorToken();
                }
            }
            if (encToken != null) {
                this.assertTokenWrapper(encToken);
                this.assertToken(encToken.getToken());
            }
            this.doEncryption(encToken, enc);
            this.putCustomTokenAfterSignature();
        }
        catch (Exception e) {
            String reason = e.getMessage();
            LOG.log(Level.WARNING, "Sign before encryption failed due to : " + reason);
            throw new Fault(e);
        }
    }

    private void doEncryptBeforeSign() {
        try {
            SecurePart part;
            AbstractTokenWrapper initiatorWrapper;
            AbstractTokenWrapper wrapper;
            AbstractToken encryptionToken = null;
            if (this.isRequestor()) {
                wrapper = this.abinding.getRecipientEncryptionToken();
                if (wrapper == null) {
                    wrapper = this.abinding.getRecipientToken();
                }
            } else {
                wrapper = this.abinding.getInitiatorEncryptionToken();
                if (wrapper == null) {
                    wrapper = this.abinding.getInitiatorToken();
                }
            }
            this.assertTokenWrapper(wrapper);
            if (wrapper != null) {
                encryptionToken = wrapper.getToken();
                this.assertToken(encryptionToken);
            }
            if ((initiatorWrapper = this.abinding.getInitiatorSignatureToken()) == null) {
                initiatorWrapper = this.abinding.getInitiatorToken();
            }
            if (initiatorWrapper != null) {
                this.assertTokenWrapper(initiatorWrapper);
                AbstractToken initiatorToken = initiatorWrapper.getToken();
                if (initiatorToken instanceof IssuedToken) {
                    SecurityToken sigTok = this.getSecurityToken();
                    this.addIssuedToken(initiatorToken, sigTok, false, true);
                    if (sigTok != null) {
                        this.storeSecurityToken(initiatorToken, sigTok);
                        this.outboundSecurityContext.remove("PROP_USE_THIS_TOKEN_ID_FOR_ENCRYPTION");
                    }
                    WSSSecurityProperties properties = this.getProperties();
                    TokenStoreCallbackHandler callbackHandler = new TokenStoreCallbackHandler(properties.getCallbackHandler(), TokenStoreUtils.getTokenStore(this.message));
                    properties.setCallbackHandler(callbackHandler);
                } else if (initiatorToken instanceof SamlToken) {
                    this.addSamlToken((SamlToken)initiatorToken, false, true);
                }
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
                } else {
                    this.addSignatureConfirmation(sigParts);
                }
                if (this.abinding.isEncryptSignature()) {
                    part = new SecurePart(new QName("http://www.w3.org/2000/09/xmldsig#", "Signature"), SecurePart.Modifier.Element);
                    encrParts.add(part);
                    if (this.signatureConfirmationAdded) {
                        SecurePart securePart = new SecurePart(WSSConstants.TAG_WSSE11_SIG_CONF, SecurePart.Modifier.Element);
                        encrParts.add(securePart);
                    }
                    this.assertPolicy(new QName(this.abinding.getName().getNamespaceURI(), "EncryptSignature"));
                }
                this.doEncryption(wrapper, encrParts);
            }
            if (this.timestampAdded) {
                part = new SecurePart(new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Timestamp"), SecurePart.Modifier.Element);
                sigParts.add(part);
            }
            if (!sigParts.isEmpty()) {
                if (initiatorWrapper != null && this.isRequestor()) {
                    this.doSignature(initiatorWrapper, sigParts);
                } else if (!this.isRequestor()) {
                    AbstractTokenWrapper recipientSignatureToken = this.abinding.getRecipientSignatureToken();
                    if (recipientSignatureToken == null) {
                        recipientSignatureToken = this.abinding.getRecipientToken();
                    }
                    if (recipientSignatureToken != null) {
                        this.assertTokenWrapper(recipientSignatureToken);
                        this.assertToken(recipientSignatureToken.getToken());
                        this.doSignature(recipientSignatureToken, sigParts);
                    }
                }
            }
            this.removeSignatureIfSignedSAML();
            this.enforceEncryptBeforeSigningWithSignedSAML();
            this.prependSignatureToSC();
            this.putCustomTokenAfterSignature();
        }
        catch (Exception e) {
            String reason = e.getMessage();
            LOG.log(Level.WARNING, "Encrypt before signing failed due to : " + reason);
            throw new Fault(e);
        }
    }

    private void doEncryption(AbstractTokenWrapper recToken, List<SecurePart> encrParts) throws SOAPException {
        if (recToken != null && recToken.getToken() != null && !encrParts.isEmpty()) {
            AbstractToken encrToken = recToken.getToken();
            AlgorithmSuite algorithmSuite = this.abinding.getAlgorithmSuite();
            WSSSecurityProperties properties = this.getProperties();
            XMLSecurityConstants.Action actionToPerform = XMLSecurityConstants.ENCRYPT;
            if (recToken.getToken().getDerivedKeys() == AbstractToken.DerivedKeys.RequireDerivedKeys) {
                actionToPerform = WSSConstants.ENCRYPT_WITH_DERIVED_KEY;
            }
            properties.addAction(actionToPerform);
            properties.getEncryptionSecureParts().addAll(encrParts);
            properties.setEncryptionKeyIdentifier(this.getKeyIdentifierType(encrToken));
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
            if (!this.isRequestor() && recToken.getToken() instanceof IssuedToken) {
                properties.setUseReqSigCertForEncryption(true);
            }
        }
    }

    private void doSignature(AbstractTokenWrapper wrapper, List<SecurePart> sigParts) throws WSSecurityException, SOAPException {
        WSSSecurityProperties properties = this.getProperties();
        XMLSecurityConstants.Action actionToPerform = XMLSecurityConstants.SIGNATURE;
        if (wrapper.getToken().getDerivedKeys() == AbstractToken.DerivedKeys.RequireDerivedKeys) {
            actionToPerform = WSSConstants.SIGNATURE_WITH_DERIVED_KEY;
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
        this.configureSignature(sigToken, false);
        if (this.abinding.isProtectTokens() && sigToken instanceof X509Token && sigToken.getIncludeTokenType() != SPConstants.IncludeTokenType.INCLUDE_TOKEN_NEVER) {
            SecurePart securePart = new SecurePart(new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "BinarySecurityToken"), SecurePart.Modifier.Element);
            properties.addSignaturePart(securePart);
        } else if (sigToken instanceof IssuedToken || sigToken instanceof SecurityContextToken || sigToken instanceof SecureConversationToken || sigToken instanceof SpnegoContextToken || sigToken instanceof SamlToken) {
            properties.setIncludeSignatureToken(false);
        }
        if (sigToken.getDerivedKeys() == AbstractToken.DerivedKeys.RequireDerivedKeys) {
            properties.setSignatureAlgorithm(this.abinding.getAlgorithmSuite().getSymmetricSignature());
        }
    }
}

