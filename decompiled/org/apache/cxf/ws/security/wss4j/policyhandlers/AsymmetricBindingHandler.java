/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyhandlers;

import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.crypto.dsig.Reference;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.apache.cxf.ws.security.wss4j.AttachmentCallbackHandler;
import org.apache.cxf.ws.security.wss4j.StaxSerializer;
import org.apache.cxf.ws.security.wss4j.WSS4JUtils;
import org.apache.cxf.ws.security.wss4j.policyhandlers.AbstractBindingBuilder;
import org.apache.neethi.Assertion;
import org.apache.wss4j.common.WSEncryptionPart;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.dom.engine.WSSConfig;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.WSHandlerResult;
import org.apache.wss4j.dom.message.WSSecBase;
import org.apache.wss4j.dom.message.WSSecDKEncrypt;
import org.apache.wss4j.dom.message.WSSecDKSign;
import org.apache.wss4j.dom.message.WSSecEncrypt;
import org.apache.wss4j.dom.message.WSSecEncryptedKey;
import org.apache.wss4j.dom.message.WSSecHeader;
import org.apache.wss4j.dom.message.WSSecSignature;
import org.apache.wss4j.dom.message.WSSecTimestamp;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.AbstractSymmetricAsymmetricBinding;
import org.apache.wss4j.policy.model.AbstractToken;
import org.apache.wss4j.policy.model.AbstractTokenWrapper;
import org.apache.wss4j.policy.model.AlgorithmSuite;
import org.apache.wss4j.policy.model.AsymmetricBinding;
import org.apache.wss4j.policy.model.IssuedToken;
import org.apache.wss4j.policy.model.SamlToken;
import org.opensaml.saml.common.SAMLVersion;
import org.w3c.dom.Element;

public class AsymmetricBindingHandler
extends AbstractBindingBuilder {
    private static final Logger LOG = LogUtils.getL7dLogger(AsymmetricBindingHandler.class);
    AsymmetricBinding abinding;
    private WSSecEncryptedKey encrKey;
    private String encryptedKeyId;
    private byte[] encryptedKeyValue;

    public AsymmetricBindingHandler(WSSConfig config, AsymmetricBinding binding, SOAPMessage saaj, WSSecHeader secHeader, AssertionInfoMap aim, SoapMessage message) throws SOAPException {
        super(config, binding, saaj, secHeader, aim, message);
        this.abinding = binding;
        this.protectionOrder = binding.getProtectionOrder();
    }

    public void handleBinding() {
        WSSecTimestamp timestamp = this.createTimestamp();
        this.handleLayout(timestamp);
        this.assertPolicy(this.abinding.getName());
        if (this.abinding.getProtectionOrder() == AbstractSymmetricAsymmetricBinding.ProtectionOrder.EncryptBeforeSigning) {
            this.doEncryptBeforeSign();
            this.assertPolicy(new QName(this.abinding.getName().getNamespaceURI(), "EncryptBeforeSigning"));
        } else {
            this.doSignBeforeEncrypt();
            this.assertPolicy(new QName(this.abinding.getName().getNamespaceURI(), "SignBeforeEncrypting"));
        }
        this.reshuffleTimestamp();
        this.assertAlgorithmSuite(this.abinding.getAlgorithmSuite());
        this.assertWSSProperties(this.abinding.getName().getNamespaceURI());
        this.assertTrustProperties(this.abinding.getName().getNamespaceURI());
        this.assertPolicy(new QName(this.abinding.getName().getNamespaceURI(), "OnlySignEntireHeadersAndBody"));
    }

    private void doSignBeforeEncrypt() {
        try {
            AbstractTokenWrapper encToken;
            AbstractTokenWrapper initiatorWrapper = this.abinding.getInitiatorSignatureToken();
            if (initiatorWrapper == null) {
                initiatorWrapper = this.abinding.getInitiatorToken();
            }
            this.assertTokenWrapper(initiatorWrapper);
            boolean attached = false;
            if (initiatorWrapper != null) {
                String tokenId;
                AbstractToken initiatorToken = initiatorWrapper.getToken();
                if (initiatorToken instanceof IssuedToken) {
                    SecurityToken secToken = this.getSecurityToken();
                    if (secToken == null) {
                        this.unassertPolicy((Assertion)initiatorToken, "Security token is not found or expired");
                        return;
                    }
                    if (this.isTokenRequired(initiatorToken.getIncludeTokenType())) {
                        Element el = secToken.getToken();
                        this.addEncryptedKeyElement(this.cloneElement(el));
                        attached = true;
                    }
                } else if (initiatorToken instanceof SamlToken && this.isRequestor()) {
                    SamlAssertionWrapper assertionWrapper = this.addSamlToken((SamlToken)initiatorToken);
                    if (assertionWrapper != null && this.isTokenRequired(initiatorToken.getIncludeTokenType())) {
                        Element envelope = this.saaj.getSOAPPart().getEnvelope();
                        envelope = (Element)DOMUtils.getDomElement(envelope);
                        this.addSupportingElement(assertionWrapper.toDOM(envelope.getOwnerDocument()));
                        this.storeAssertionAsSecurityToken(assertionWrapper);
                    }
                } else if (initiatorToken instanceof SamlToken && (tokenId = this.getSAMLToken()) == null) {
                    this.unassertPolicy((Assertion)initiatorToken, "Security token is not found or expired");
                    return;
                }
                this.assertToken(initiatorToken);
            }
            ArrayList<WSEncryptionPart> sigs = new ArrayList<WSEncryptionPart>();
            if (this.timestampEl != null) {
                WSEncryptionPart timestampPart = this.convertToEncryptionPart(this.timestampEl.getElement());
                sigs.add(timestampPart);
            }
            this.addSupportingTokens(sigs);
            sigs.addAll(this.getSignedParts(null));
            if (this.isRequestor() && initiatorWrapper != null) {
                this.doSignature(initiatorWrapper, sigs, attached);
                this.doEndorse();
            } else if (!this.isRequestor()) {
                this.addSignatureConfirmation(sigs);
                AbstractTokenWrapper recipientSignatureToken = this.abinding.getRecipientSignatureToken();
                if (recipientSignatureToken == null) {
                    recipientSignatureToken = this.abinding.getRecipientToken();
                }
                if (recipientSignatureToken != null) {
                    this.assertTokenWrapper(recipientSignatureToken);
                    this.assertToken(recipientSignatureToken.getToken());
                    this.doSignature(recipientSignatureToken, sigs, attached);
                }
            }
            List<WSEncryptionPart> enc = this.getEncryptedParts();
            if (this.abinding.isEncryptSignature()) {
                if (this.mainSigId != null) {
                    WSEncryptionPart sigPart = new WSEncryptionPart(this.mainSigId, "Element");
                    sigPart.setElement(this.bottomUpElement);
                    enc.add(sigPart);
                }
                if (this.sigConfList != null && !this.sigConfList.isEmpty()) {
                    enc.addAll(this.sigConfList);
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
            this.doEncryption(encToken, enc, false);
            if (encToken != null) {
                this.assertTokenWrapper(encToken);
                this.assertToken(encToken.getToken());
            }
        }
        catch (Exception e) {
            String reason = e.getMessage();
            LOG.log(Level.WARNING, "Sign before encryption failed due to : " + reason);
            LOG.log(Level.FINE, e.getMessage(), e);
            throw new Fault(e);
        }
    }

    private AbstractTokenWrapper getEncryptBeforeSignWrapper() {
        AbstractTokenWrapper wrapper;
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
        return wrapper;
    }

    /*
     * Unable to fully structure code
     */
    private void doEncryptBeforeSign() {
        wrapper = this.getEncryptBeforeSignWrapper();
        encryptionToken = null;
        if (wrapper != null) {
            encryptionToken = wrapper.getToken();
            this.assertToken(encryptionToken);
        }
        if ((initiatorWrapper = this.abinding.getInitiatorSignatureToken()) == null) {
            initiatorWrapper = this.abinding.getInitiatorToken();
        }
        this.assertTokenWrapper(initiatorWrapper);
        attached = false;
        if (initiatorWrapper != null) {
            initiatorToken = initiatorWrapper.getToken();
            if (initiatorToken instanceof IssuedToken) {
                secToken = this.getSecurityToken();
                if (secToken == null) {
                    this.unassertPolicy((Assertion)initiatorToken, "Security token is not found or expired");
                    return;
                }
                if (this.isTokenRequired(initiatorToken.getIncludeTokenType())) {
                    el = secToken.getToken();
                    this.addEncryptedKeyElement(this.cloneElement(el));
                    attached = true;
                }
            } else if (initiatorToken instanceof SamlToken && this.isRequestor()) {
                try {
                    assertionWrapper = this.addSamlToken((SamlToken)initiatorToken);
                    if (assertionWrapper == null || !this.isTokenRequired(initiatorToken.getIncludeTokenType())) ** GOTO lbl39
                    envelope = this.saaj.getSOAPPart().getEnvelope();
                    envelope = (Element)DOMUtils.getDomElement(envelope);
                    this.addSupportingElement(assertionWrapper.toDOM(envelope.getOwnerDocument()));
                    this.storeAssertionAsSecurityToken(assertionWrapper);
                }
                catch (Exception e) {
                    reason = e.getMessage();
                    AsymmetricBindingHandler.LOG.log(Level.WARNING, "Encrypt before sign failed due to : " + reason);
                    AsymmetricBindingHandler.LOG.log(Level.FINE, e.getMessage(), e);
                    throw new Fault(e);
                }
            } else if (initiatorToken instanceof SamlToken && (tokenId = this.getSAMLToken()) == null) {
                this.unassertPolicy((Assertion)initiatorToken, "Security token is not found or expired");
                return;
            }
        }
lbl39:
        // 7 sources

        sigParts = new ArrayList<WSEncryptionPart>();
        if (this.timestampEl != null) {
            timestampPart = this.convertToEncryptionPart(this.timestampEl.getElement());
            sigParts.add(timestampPart);
        }
        try {
            this.addSupportingTokens(sigParts);
        }
        catch (WSSecurityException ex) {
            AsymmetricBindingHandler.LOG.log(Level.FINE, ex.getMessage(), ex);
            this.unassertPolicy((Assertion)encryptionToken, ex);
        }
        encrParts = null;
        try {
            encrParts = this.getEncryptedParts();
            sigParts.addAll(this.getSignedParts(null));
        }
        catch (SOAPException ex) {
            AsymmetricBindingHandler.LOG.log(Level.FINE, ex.getMessage(), ex);
            throw new Fault(ex);
        }
        encrBase = null;
        if (encryptionToken != null && !encrParts.isEmpty()) {
            encrBase = this.doEncryption(wrapper, encrParts, true);
            this.handleEncryptedSignedHeaders(encrParts, sigParts);
        }
        if (!this.isRequestor()) {
            this.addSignatureConfirmation(sigParts);
        }
        try {
            if (!sigParts.isEmpty()) {
                if (initiatorWrapper != null && this.isRequestor()) {
                    this.doSignature(initiatorWrapper, sigParts, attached);
                } else if (!this.isRequestor()) {
                    recipientSignatureToken = this.abinding.getRecipientSignatureToken();
                    if (recipientSignatureToken == null) {
                        recipientSignatureToken = this.abinding.getRecipientToken();
                    }
                    if (recipientSignatureToken != null) {
                        this.assertTokenWrapper(recipientSignatureToken);
                        this.assertToken(recipientSignatureToken.getToken());
                        this.doSignature(recipientSignatureToken, sigParts, attached);
                    }
                }
            }
        }
        catch (SOAPException | WSSecurityException ex) {
            AsymmetricBindingHandler.LOG.log(Level.FINE, ex.getMessage(), ex);
            throw new Fault(ex);
        }
        if (this.isRequestor()) {
            this.doEndorse();
        }
        if (encrBase != null) {
            this.encryptTokensInSecurityHeader(encryptionToken, encrBase);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void encryptTokensInSecurityHeader(AbstractToken encryptionToken, WSSecBase encrBase) {
        Element secondRefList;
        ArrayList<WSEncryptionPart> secondEncrParts = new ArrayList<WSEncryptionPart>();
        if (this.abinding.isEncryptSignature()) {
            this.assertPolicy(new QName(this.abinding.getName().getNamespaceURI(), "EncryptSignature"));
            if (this.mainSigId != null) {
                WSEncryptionPart sigPart = new WSEncryptionPart(this.mainSigId, "Element");
                sigPart.setElement(this.bottomUpElement);
                secondEncrParts.add(sigPart);
            }
            if (this.sigConfList != null && !this.sigConfList.isEmpty()) {
                secondEncrParts.addAll(this.sigConfList);
            }
        }
        if (this.isRequestor()) {
            secondEncrParts.addAll(this.encryptedTokensList);
        }
        if (secondEncrParts.isEmpty()) {
            return;
        }
        if (encryptionToken.getDerivedKeys() == AbstractToken.DerivedKeys.RequireDerivedKeys && encrBase instanceof WSSecDKEncrypt) {
            try {
                secondRefList = ((WSSecDKEncrypt)encrBase).encryptForExternalRef(null, secondEncrParts);
                if (secondRefList == null) return;
                ((WSSecDKEncrypt)encrBase).addExternalRefElement(secondRefList);
                return;
            }
            catch (WSSecurityException ex) {
                LOG.log(Level.FINE, ex.getMessage(), ex);
                throw new Fault(ex);
            }
        }
        if (!(encrBase instanceof WSSecEncrypt)) return;
        try {
            secondRefList = this.saaj.getSOAPPart().createElementNS("http://www.w3.org/2001/04/xmlenc#", "xenc:ReferenceList");
            if (this.lastEncryptedKeyElement != null) {
                this.insertAfter(secondRefList, this.lastEncryptedKeyElement);
            } else {
                this.insertBeforeBottomUp(secondRefList);
            }
            ((WSSecEncrypt)encrBase).encryptForRef(secondRefList, secondEncrParts);
            return;
        }
        catch (WSSecurityException ex) {
            LOG.log(Level.FINE, ex.getMessage(), ex);
            throw new Fault(ex);
        }
    }

    private WSSecBase doEncryption(AbstractTokenWrapper recToken, List<WSEncryptionPart> encrParts, boolean externalRef) {
        if (recToken != null && recToken.getToken() != null && !encrParts.isEmpty()) {
            AbstractToken encrToken = recToken.getToken();
            this.assertPolicy(recToken);
            this.assertPolicy(encrToken);
            AlgorithmSuite algorithmSuite = this.abinding.getAlgorithmSuite();
            if (encrToken.getDerivedKeys() == AbstractToken.DerivedKeys.RequireDerivedKeys) {
                return this.doEncryptionDerived(recToken, encrToken, encrParts, algorithmSuite);
            }
            try {
                WSSecEncrypt encr = new WSSecEncrypt(this.secHeader);
                encr.setEncryptionSerializer(new StaxSerializer());
                encr.setIdAllocator(this.wssConfig.getIdAllocator());
                encr.setCallbackLookup(this.callbackLookup);
                encr.setAttachmentCallbackHandler(new AttachmentCallbackHandler(this.message));
                encr.setStoreBytesInAttachment(this.storeBytesInAttachment);
                encr.setExpandXopInclude(this.isExpandXopInclude());
                encr.setWsDocInfo(this.wsDocInfo);
                Crypto crypto = this.getEncryptionCrypto();
                SecurityToken securityToken = this.getSecurityToken();
                if (!this.isRequestor() && securityToken != null && recToken.getToken() instanceof SamlToken) {
                    String tokenType = securityToken.getTokenType();
                    if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1".equals(tokenType) || "urn:oasis:names:tc:SAML:1.0:assertion".equals(tokenType)) {
                        encr.setCustomEKTokenValueType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID");
                        encr.setKeyIdentifierType(12);
                        encr.setCustomEKTokenId(securityToken.getId());
                    } else if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0".equals(tokenType) || "urn:oasis:names:tc:SAML:2.0:assertion".equals(tokenType)) {
                        encr.setCustomEKTokenValueType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID");
                        encr.setKeyIdentifierType(12);
                        encr.setCustomEKTokenId(securityToken.getId());
                    } else {
                        this.setKeyIdentifierType(encr, encrToken);
                    }
                } else {
                    this.setKeyIdentifierType(encr, encrToken);
                }
                if (!this.isRequestor() && securityToken != null && securityToken.getX509Certificate() != null) {
                    encr.setUseThisCert(securityToken.getX509Certificate());
                } else if (!this.isRequestor() && securityToken != null && securityToken.getKey() instanceof PublicKey) {
                    encr.setUseThisPublicKey((PublicKey)securityToken.getKey());
                    encr.setKeyIdentifierType(13);
                } else {
                    this.setEncryptionUser(encr, encrToken, false, crypto);
                }
                if (!encr.isCertSet() && encr.getUseThisPublicKey() == null && crypto == null) {
                    this.unassertPolicy((Assertion)recToken, "Missing security configuration. Make sure jaxws:client element is configured with a security.encryption.properties value.");
                }
                AlgorithmSuite.AlgorithmSuiteType algType = algorithmSuite.getAlgorithmSuiteType();
                encr.setSymmetricEncAlgorithm(algType.getEncryption());
                encr.setKeyEncAlgo(algType.getAsymmetricKeyWrap());
                encr.setMGFAlgorithm(algType.getMGFAlgo());
                encr.setDigestAlgorithm(algType.getEncryptionDigest());
                encr.prepare(crypto);
                Element encryptedKeyElement = encr.getEncryptedKeyElement();
                List<Element> attachments = encr.getAttachmentEncryptedDataElements();
                if (externalRef) {
                    Element refList = encr.encryptForRef(null, encrParts);
                    if (refList != null) {
                        this.insertBeforeBottomUp(refList);
                    }
                    if (attachments != null) {
                        for (Element attachment : attachments) {
                            this.insertBeforeBottomUp(attachment);
                        }
                    }
                    if (refList != null || attachments != null && !attachments.isEmpty()) {
                        this.addEncryptedKeyElement(encryptedKeyElement);
                    }
                } else {
                    Element refList = encr.encryptForRef(null, encrParts);
                    if (refList != null || attachments != null && !attachments.isEmpty()) {
                        this.addEncryptedKeyElement(encryptedKeyElement);
                    }
                    if (refList != null) {
                        encryptedKeyElement.appendChild(refList);
                    }
                    if (attachments != null) {
                        for (Element attachment : attachments) {
                            this.addEncryptedKeyElement(attachment);
                        }
                    }
                }
                if (encr.getBSTTokenId() != null) {
                    encr.prependBSTElementToHeader();
                }
                return encr;
            }
            catch (WSSecurityException e) {
                LOG.log(Level.FINE, e.getMessage(), e);
                this.unassertPolicy((Assertion)recToken, e);
            }
        }
        return null;
    }

    private WSSecBase doEncryptionDerived(AbstractTokenWrapper recToken, AbstractToken encrToken, List<WSEncryptionPart> encrParts, AlgorithmSuite algorithmSuite) {
        try {
            WSSecDKEncrypt dkEncr = new WSSecDKEncrypt(this.secHeader);
            dkEncr.setEncryptionSerializer(new StaxSerializer());
            dkEncr.setIdAllocator(this.wssConfig.getIdAllocator());
            dkEncr.setCallbackLookup(this.callbackLookup);
            dkEncr.setAttachmentCallbackHandler(new AttachmentCallbackHandler(this.message));
            dkEncr.setStoreBytesInAttachment(this.storeBytesInAttachment);
            dkEncr.setExpandXopInclude(this.isExpandXopInclude());
            dkEncr.setWsDocInfo(this.wsDocInfo);
            if (recToken.getToken().getVersion() == SPConstants.SPVersion.SP11) {
                dkEncr.setWscVersion(1);
            }
            if (this.encrKey == null) {
                this.setupEncryptedKey(encrToken);
            }
            dkEncr.setExternalKey(this.encryptedKeyValue, this.encryptedKeyId);
            dkEncr.getParts().addAll(encrParts);
            dkEncr.setCustomValueType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey");
            AlgorithmSuite.AlgorithmSuiteType algType = algorithmSuite.getAlgorithmSuiteType();
            dkEncr.setSymmetricEncAlgorithm(algType.getEncryption());
            dkEncr.setDerivedKeyLength(algType.getEncryptionDerivedKeyLength() / 8);
            dkEncr.prepare();
            this.addDerivedKeyElement(dkEncr.getdktElement());
            Element refList = dkEncr.encryptForExternalRef(null, encrParts);
            if (refList != null) {
                this.insertBeforeBottomUp(refList);
            }
            return dkEncr;
        }
        catch (Exception e) {
            LOG.log(Level.FINE, e.getMessage(), e);
            this.unassertPolicy((Assertion)recToken, e);
            return null;
        }
    }

    private void assertUnusedTokens(AbstractTokenWrapper wrapper) {
        if (wrapper == null) {
            return;
        }
        Collection<AssertionInfo> ais = this.aim.getAssertionInfo(wrapper.getName());
        if (ais != null) {
            for (AssertionInfo ai : ais) {
                if (ai.getAssertion() != wrapper) continue;
                ai.setAsserted(true);
            }
        }
        if ((ais = this.aim.getAssertionInfo(wrapper.getToken().getName())) != null) {
            for (AssertionInfo ai : ais) {
                if (ai.getAssertion() != wrapper.getToken()) continue;
                ai.setAsserted(true);
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void doSignature(AbstractTokenWrapper wrapper, List<WSEncryptionPart> sigParts, boolean attached) throws WSSecurityException, SOAPException {
        if (!this.isRequestor()) {
            this.assertUnusedTokens(this.abinding.getInitiatorToken());
            this.assertUnusedTokens(this.abinding.getInitiatorEncryptionToken());
            this.assertUnusedTokens(this.abinding.getInitiatorSignatureToken());
        } else {
            this.assertUnusedTokens(this.abinding.getRecipientToken());
            this.assertUnusedTokens(this.abinding.getRecipientEncryptionToken());
            this.assertUnusedTokens(this.abinding.getRecipientSignatureToken());
        }
        AbstractToken sigToken = wrapper.getToken();
        if (sigParts.isEmpty()) {
            if (attached || !this.isTokenRequired(sigToken.getIncludeTokenType())) return;
            WSSecSignature sig = this.getSignatureBuilder(sigToken, attached, false);
            sig.appendBSTElementToHeader();
            return;
        }
        if (sigToken.getDerivedKeys() == AbstractToken.DerivedKeys.RequireDerivedKeys) {
            this.setupEncryptedKey(sigToken);
            WSSecDKSign dkSign = new WSSecDKSign(this.secHeader);
            dkSign.setIdAllocator(this.wssConfig.getIdAllocator());
            dkSign.setCallbackLookup(this.callbackLookup);
            dkSign.setAttachmentCallbackHandler(new AttachmentCallbackHandler(this.message));
            dkSign.setStoreBytesInAttachment(this.storeBytesInAttachment);
            dkSign.setExpandXopInclude(this.isExpandXopInclude());
            dkSign.setWsDocInfo(this.wsDocInfo);
            if (wrapper.getToken().getVersion() == SPConstants.SPVersion.SP11) {
                dkSign.setWscVersion(1);
            }
            dkSign.setExternalKey(this.encryptedKeyValue, this.encryptedKeyId);
            dkSign.setSignatureAlgorithm(this.abinding.getAlgorithmSuite().getSymmetricSignature());
            dkSign.setSigCanonicalization(this.abinding.getAlgorithmSuite().getC14n().getValue());
            AlgorithmSuite.AlgorithmSuiteType algType = this.abinding.getAlgorithmSuite().getAlgorithmSuiteType();
            dkSign.setDigestAlgorithm(algType.getDigest());
            dkSign.setDerivedKeyLength(algType.getSignatureDerivedKeyLength() / 8);
            dkSign.setCustomValueType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey");
            boolean includePrefixes = MessageUtils.getContextualBoolean(this.message, "ws-security.add.inclusive.prefixes", true);
            dkSign.setAddInclusivePrefixes(includePrefixes);
            try {
                dkSign.prepare();
                if (this.abinding.isProtectTokens()) {
                    this.assertPolicy(new QName(this.abinding.getName().getNamespaceURI(), "ProtectTokens"));
                    if (this.bstElement != null) {
                        WSEncryptionPart bstPart = new WSEncryptionPart(this.bstElement.getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id"));
                        bstPart.setElement(this.bstElement);
                        sigParts.add(bstPart);
                    } else {
                        WSEncryptionPart ekPart = new WSEncryptionPart(this.encrKey.getId());
                        ekPart.setElement(this.encrKey.getEncryptedKeyElement());
                        sigParts.add(ekPart);
                    }
                }
                dkSign.getParts().addAll(sigParts);
                List<Reference> referenceList = dkSign.addReferencesToSign(sigParts);
                if (referenceList.isEmpty()) return;
                this.addDerivedKeyElement(dkSign.getdktElement());
                if (this.bottomUpElement == null) {
                    dkSign.computeSignature(referenceList, false, null);
                } else {
                    dkSign.computeSignature(referenceList, true, this.bottomUpElement);
                }
                this.bottomUpElement = dkSign.getSignatureElement();
                this.addSig(dkSign.getSignatureValue());
                this.mainSigId = dkSign.getSignatureId();
                return;
            }
            catch (Exception ex) {
                LOG.log(Level.FINE, ex.getMessage(), ex);
                throw new Fault(ex);
            }
        } else {
            Element bstElement;
            List<Reference> referenceList;
            WSSecSignature sig = this.getSignatureBuilder(sigToken, attached, false);
            if (this.abinding.isProtectTokens()) {
                this.assertPolicy(new QName(this.abinding.getName().getNamespaceURI(), "ProtectTokens"));
                if (sig.getCustomTokenId() != null && (sigToken instanceof SamlToken || sigToken instanceof IssuedToken)) {
                    WSEncryptionPart samlPart = new WSEncryptionPart(sig.getCustomTokenId());
                    sigParts.add(samlPart);
                } else if (sig.getBSTTokenId() != null) {
                    WSEncryptionPart bstPart = new WSEncryptionPart(sig.getBSTTokenId());
                    bstPart.setElement(sig.getBinarySecurityTokenElement());
                    sigParts.add(bstPart);
                    sig.prependBSTElementToHeader();
                }
            }
            if ((referenceList = sig.addReferencesToSign(sigParts)).isEmpty()) return;
            if (this.bottomUpElement == null) {
                sig.computeSignature(referenceList, false, null);
            } else {
                sig.computeSignature(referenceList, true, this.bottomUpElement);
            }
            this.bottomUpElement = sig.getSignatureElement();
            if (!this.abinding.isProtectTokens() && (bstElement = sig.getBinarySecurityTokenElement()) != null) {
                this.secHeader.getSecurityHeaderElement().insertBefore(bstElement, this.bottomUpElement);
            }
            this.addSig(sig.getSignatureValue());
            this.mainSigId = sig.getId();
        }
    }

    private void setupEncryptedKey(AbstractToken token) throws WSSecurityException {
        if (!this.isRequestor() && token.getDerivedKeys() == AbstractToken.DerivedKeys.RequireDerivedKeys) {
            if (this.encryptedKeyId != null && this.encryptedKeyValue != null) {
                return;
            }
            List results = CastUtils.cast((List)this.message.getExchange().getInMessage().get("RECV_RESULTS"));
            if (results != null) {
                WSSecurityEngineResult encryptedKeyResult = this.getEncryptedKeyResult();
                if (encryptedKeyResult != null) {
                    this.encryptedKeyId = (String)encryptedKeyResult.get("id");
                    this.encryptedKeyValue = (byte[])encryptedKeyResult.get("secret");
                }
                if (this.encryptedKeyId == null && this.encryptedKeyValue == null) {
                    this.createEncryptedKey(token);
                }
            } else {
                this.unassertPolicy((Assertion)token, "No security results found");
            }
        } else {
            this.createEncryptedKey(token);
        }
    }

    private void createEncryptedKey(AbstractToken token) throws WSSecurityException {
        this.encrKey = this.getEncryptedKeyBuilder(token);
        Element bstElem = this.encrKey.getBinarySecurityTokenElement();
        if (bstElem != null) {
            this.encrKey.prependBSTElementToHeader();
        }
        this.addEncryptedKeyElement(this.encrKey.getEncryptedKeyElement());
        this.encryptedKeyValue = this.encrKey.getEphemeralKey();
        this.encryptedKeyId = this.encrKey.getId();
    }

    private String getSAMLToken() {
        List<WSHandlerResult> results = CastUtils.cast((List)this.message.getExchange().getInMessage().get("RECV_RESULTS"));
        for (WSHandlerResult rResult : results) {
            List<WSSecurityEngineResult> wsSecEngineResults = rResult.getResults();
            for (WSSecurityEngineResult wser : wsSecEngineResults) {
                Integer actInt = (Integer)wser.get("action");
                if (actInt != 16 && actInt != 8) continue;
                Instant created = Instant.now();
                Instant expires = created.plusSeconds(WSS4JUtils.getSecurityTokenLifetime(this.message) / 1000L);
                String id = (String)wser.get("id");
                SecurityToken tempTok = new SecurityToken(id, created, expires);
                tempTok.setSecret((byte[])wser.get("secret"));
                tempTok.setX509Certificate((X509Certificate)wser.get("x509-certificate"), null);
                SamlAssertionWrapper samlAssertion = (SamlAssertionWrapper)wser.get("saml-assertion");
                if (samlAssertion.getSamlVersion() == SAMLVersion.VERSION_20) {
                    tempTok.setTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
                } else {
                    tempTok.setTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1");
                }
                this.message.put("ws-security.token", (Object)tempTok);
                return id;
            }
        }
        return null;
    }
}

