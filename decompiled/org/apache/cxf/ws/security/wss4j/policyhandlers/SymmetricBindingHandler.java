/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyhandlers;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import javax.xml.crypto.dsig.Reference;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.apache.cxf.ws.security.tokenstore.TokenStore;
import org.apache.cxf.ws.security.wss4j.AttachmentCallbackHandler;
import org.apache.cxf.ws.security.wss4j.StaxSerializer;
import org.apache.cxf.ws.security.wss4j.WSS4JUtils;
import org.apache.cxf.ws.security.wss4j.policyhandlers.AbstractBindingBuilder;
import org.apache.neethi.Assertion;
import org.apache.wss4j.common.WSEncryptionPart;
import org.apache.wss4j.common.bsp.BSPEnforcer;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.token.SecurityTokenReference;
import org.apache.wss4j.common.util.KeyUtils;
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
import org.apache.wss4j.dom.message.WSSecUsernameToken;
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
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Element;

public class SymmetricBindingHandler
extends AbstractBindingBuilder {
    private final SymmetricBinding sbinding;
    private final TokenStore tokenStore;

    public SymmetricBindingHandler(WSSConfig config, SymmetricBinding binding, SOAPMessage saaj, WSSecHeader secHeader, AssertionInfoMap aim, SoapMessage message) throws SOAPException {
        super(config, binding, saaj, secHeader, aim, message);
        this.sbinding = binding;
        this.tokenStore = this.getTokenStore();
        this.protectionOrder = binding.getProtectionOrder();
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
        WSSecTimestamp timestamp = this.createTimestamp();
        this.handleLayout(timestamp);
        this.assertPolicy(this.sbinding.getName());
        if (this.sbinding.getProtectionOrder() == AbstractSymmetricAsymmetricBinding.ProtectionOrder.EncryptBeforeSigning) {
            this.doEncryptBeforeSign();
            this.assertPolicy(new QName(this.sbinding.getName().getNamespaceURI(), "EncryptBeforeSigning"));
        } else {
            this.doSignBeforeEncrypt();
            this.assertPolicy(new QName(this.sbinding.getName().getNamespaceURI(), "SignBeforeEncrypting"));
        }
        this.reshuffleTimestamp();
        this.assertAlgorithmSuite(this.sbinding.getAlgorithmSuite());
        this.assertWSSProperties(this.sbinding.getName().getNamespaceURI());
        this.assertTrustProperties(this.sbinding.getName().getNamespaceURI());
        this.assertPolicy(new QName(this.sbinding.getName().getNamespaceURI(), "OnlySignEntireHeadersAndBody"));
    }

    private void doEncryptBeforeSign() {
        try {
            AbstractTokenWrapper encryptionWrapper = this.getEncryptionToken();
            this.assertTokenWrapper(encryptionWrapper);
            AbstractToken encryptionToken = encryptionWrapper.getToken();
            if (encryptionToken != null) {
                Element el;
                String tokenId = null;
                SecurityToken tok = null;
                if (encryptionToken instanceof IssuedToken || encryptionToken instanceof KerberosToken || encryptionToken instanceof SecureConversationToken || encryptionToken instanceof SecurityContextToken || encryptionToken instanceof SpnegoContextToken) {
                    tok = this.getSecurityToken();
                } else if (encryptionToken instanceof X509Token) {
                    if (this.isRequestor()) {
                        tokenId = this.setupEncryptedKey(encryptionWrapper, encryptionToken);
                    } else {
                        tok = this.getEncryptedKey();
                    }
                } else if (encryptionToken instanceof UsernameToken) {
                    if (this.isRequestor()) {
                        tokenId = this.setupUTDerivedKey((UsernameToken)encryptionToken);
                    } else {
                        tok = this.getUTDerivedKey();
                    }
                }
                if (tok == null) {
                    if (tokenId != null && tokenId.startsWith("#")) {
                        tokenId = tokenId.substring(1);
                    }
                    tok = this.tokenStore.getToken(tokenId);
                }
                boolean attached = false;
                if (this.isTokenRequired(encryptionToken.getIncludeTokenType())) {
                    el = tok.getToken();
                    this.addEncryptedKeyElement(this.cloneElement(el));
                    attached = true;
                } else if (encryptionToken instanceof X509Token && this.isRequestor()) {
                    el = tok.getToken();
                    this.addEncryptedKeyElement(this.cloneElement(el));
                    attached = true;
                }
                ArrayList<WSEncryptionPart> sigParts = new ArrayList<WSEncryptionPart>();
                if (this.timestampEl != null) {
                    WSEncryptionPart timestampPart = this.convertToEncryptionPart(this.timestampEl.getElement());
                    sigParts.add(timestampPart);
                }
                this.addSupportingTokens(sigParts);
                sigParts.addAll(this.getSignedParts(null));
                List<WSEncryptionPart> encrParts = this.getEncryptedParts();
                WSSecBase encr = this.doEncryption(encryptionWrapper, tok, attached, encrParts, true);
                this.handleEncryptedSignedHeaders(encrParts, sigParts);
                if (!this.isRequestor()) {
                    this.addSignatureConfirmation(sigParts);
                }
                if (!sigParts.isEmpty()) {
                    this.addSig(this.doSignature(sigParts, encryptionWrapper, encryptionToken, tok, attached));
                }
                if (this.isRequestor()) {
                    this.doEndorse();
                }
                if (this.sbinding.isEncryptSignature() || !this.encryptedTokensList.isEmpty() && this.isRequestor()) {
                    ArrayList<WSEncryptionPart> secondEncrParts = new ArrayList<WSEncryptionPart>();
                    if (this.sbinding.isEncryptSignature()) {
                        if (this.mainSigId != null) {
                            WSEncryptionPart sigPart = new WSEncryptionPart(this.mainSigId, "Element");
                            sigPart.setElement(this.bottomUpElement);
                            secondEncrParts.add(sigPart);
                        }
                        if (this.sigConfList != null && !this.sigConfList.isEmpty()) {
                            secondEncrParts.addAll(this.sigConfList);
                        }
                        this.assertPolicy(new QName(this.sbinding.getName().getNamespaceURI(), "EncryptSignature"));
                    }
                    if (this.isRequestor()) {
                        secondEncrParts.addAll(this.encryptedTokensList);
                    }
                    Element secondRefList = null;
                    if (encryptionToken.getDerivedKeys() == AbstractToken.DerivedKeys.RequireDerivedKeys && !secondEncrParts.isEmpty()) {
                        secondRefList = ((WSSecDKEncrypt)encr).encryptForExternalRef(null, secondEncrParts);
                    } else if (!secondEncrParts.isEmpty()) {
                        secondRefList = ((WSSecEncrypt)encr).encryptForRef(null, secondEncrParts);
                    }
                    if (secondRefList != null) {
                        this.addDerivedKeyElement(secondRefList);
                    }
                }
            }
        }
        catch (RuntimeException ex) {
            LOG.log(Level.FINE, ex.getMessage(), ex);
            throw ex;
        }
        catch (Exception ex) {
            LOG.log(Level.FINE, ex.getMessage(), ex);
            throw new Fault(ex);
        }
    }

    private void doSignBeforeEncrypt() {
        AbstractTokenWrapper sigAbstractTokenWrapper = this.getSignatureToken();
        this.assertTokenWrapper(sigAbstractTokenWrapper);
        AbstractToken sigToken = sigAbstractTokenWrapper.getToken();
        String sigTokId = null;
        Element sigTokElem = null;
        try {
            Element el;
            SecurityToken sigTok = null;
            if (sigToken != null) {
                if (sigToken instanceof SecureConversationToken || sigToken instanceof SecurityContextToken || sigToken instanceof IssuedToken || sigToken instanceof KerberosToken || sigToken instanceof SpnegoContextToken) {
                    sigTok = this.getSecurityToken();
                } else if (sigToken instanceof X509Token) {
                    if (this.isRequestor()) {
                        sigTokId = this.setupEncryptedKey(sigAbstractTokenWrapper, sigToken);
                    } else {
                        sigTok = this.getEncryptedKey();
                    }
                } else if (sigToken instanceof UsernameToken) {
                    if (this.isRequestor()) {
                        sigTokId = this.setupUTDerivedKey((UsernameToken)sigToken);
                    } else {
                        sigTok = this.getUTDerivedKey();
                    }
                }
            } else {
                this.unassertPolicy((Assertion)this.sbinding, "No signature token");
                return;
            }
            if (sigTok == null && StringUtils.isEmpty(sigTokId)) {
                this.unassertPolicy((Assertion)sigAbstractTokenWrapper, "No signature token id");
                return;
            }
            this.assertPolicy(sigAbstractTokenWrapper);
            if (sigTok == null) {
                sigTok = this.tokenStore.getToken(sigTokId);
            }
            boolean tokIncluded = true;
            if (this.isTokenRequired(sigToken.getIncludeTokenType())) {
                el = sigTok.getToken();
                sigTokElem = this.cloneElement(el);
                this.addEncryptedKeyElement(sigTokElem);
            } else if (this.isRequestor() && sigToken instanceof X509Token) {
                el = sigTok.getToken();
                sigTokElem = this.cloneElement(el);
                this.addEncryptedKeyElement(sigTokElem);
            } else {
                tokIncluded = false;
            }
            ArrayList<WSEncryptionPart> sigs = new ArrayList<WSEncryptionPart>();
            if (this.timestampEl != null) {
                WSEncryptionPart timestampPart = this.convertToEncryptionPart(this.timestampEl.getElement());
                sigs.add(timestampPart);
            }
            this.addSupportingTokens(sigs);
            sigs.addAll(this.getSignedParts(null));
            if (this.isRequestor()) {
                if (!sigs.isEmpty()) {
                    this.addSig(this.doSignature(sigs, sigAbstractTokenWrapper, sigToken, sigTok, tokIncluded));
                }
                this.doEndorse();
            } else {
                this.addSignatureConfirmation(sigs);
                if (!sigs.isEmpty()) {
                    this.doSignature(sigs, sigAbstractTokenWrapper, sigToken, sigTok, tokIncluded);
                }
            }
            AbstractTokenWrapper encrAbstractTokenWrapper = this.getEncryptionToken();
            AbstractToken encrToken = encrAbstractTokenWrapper.getToken();
            SecurityToken encrTok = null;
            if (!sigToken.equals(encrToken)) {
                this.unassertPolicy((Assertion)this.sbinding, "Encryption token does not equal signature token");
                return;
            }
            encrTok = sigTok;
            List<WSEncryptionPart> enc = this.getEncryptedParts();
            if (this.sbinding.isEncryptSignature()) {
                if (this.mainSigId != null) {
                    WSEncryptionPart sigPart = new WSEncryptionPart(this.mainSigId, "Element");
                    sigPart.setElement(this.bottomUpElement);
                    enc.add(sigPart);
                }
                if (this.sigConfList != null && !this.sigConfList.isEmpty()) {
                    enc.addAll(this.sigConfList);
                }
                this.assertPolicy(new QName(this.sbinding.getName().getNamespaceURI(), "EncryptSignature"));
            }
            if (this.isRequestor()) {
                enc.addAll(this.encryptedTokensList);
            }
            this.doEncryption(encrAbstractTokenWrapper, encrTok, tokIncluded, enc, false);
        }
        catch (Exception e) {
            LOG.log(Level.FINE, e.getMessage(), e);
            throw new Fault(e);
        }
    }

    private WSSecBase doEncryptionDerived(AbstractTokenWrapper recToken, SecurityToken encrTok, AbstractToken encrToken, boolean attached, List<WSEncryptionPart> encrParts, boolean atEnd) {
        try {
            String tokenType;
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
            if (attached && encrTok.getAttachedReference() != null) {
                dkEncr.setExternalKey(encrTok.getSecret(), this.cloneElement(encrTok.getAttachedReference()));
            } else if (encrTok.getUnattachedReference() != null) {
                dkEncr.setExternalKey(encrTok.getSecret(), this.cloneElement(encrTok.getUnattachedReference()));
            } else if (!this.isRequestor() && encrTok.getSHA1() != null) {
                SecurityTokenReference tokenRef = new SecurityTokenReference(this.saaj.getSOAPPart());
                String tokenType2 = encrTok.getTokenType();
                if (encrToken instanceof KerberosToken) {
                    tokenRef.setKeyIdentifier("http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#Kerberosv5APREQSHA1", encrTok.getSHA1(), true);
                    if (tokenType2 == null) {
                        tokenType2 = "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ";
                    }
                } else {
                    tokenRef.setKeyIdentifierEncKeySHA1(encrTok.getSHA1());
                    if (tokenType2 == null) {
                        tokenType2 = "http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey";
                    }
                }
                tokenRef.addTokenType(tokenType2);
                dkEncr.setExternalKey(encrTok.getSecret(), tokenRef.getElement());
            } else if (attached) {
                String id = encrTok.getWsuId();
                if (id == null && (encrToken instanceof SecureConversationToken || encrToken instanceof SecurityContextToken)) {
                    dkEncr.setTokenIdDirectId(true);
                    id = encrTok.getId();
                } else if (id == null) {
                    id = encrTok.getId();
                }
                if (id.startsWith("#")) {
                    id = id.substring(1);
                }
                dkEncr.setExternalKey(encrTok.getSecret(), id);
            } else {
                dkEncr.setTokenIdDirectId(true);
                dkEncr.setExternalKey(encrTok.getSecret(), encrTok.getId());
            }
            if (encrTok.getSHA1() != null) {
                tokenType = encrTok.getTokenType();
                if (tokenType == null) {
                    tokenType = "http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey";
                }
                dkEncr.setCustomValueType(tokenType);
            } else {
                tokenType = encrTok.getTokenType();
                if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1".equals(tokenType) || "urn:oasis:names:tc:SAML:1.0:assertion".equals(tokenType)) {
                    dkEncr.setKeyIdentifierType(12);
                    dkEncr.setCustomValueType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID");
                } else if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0".equals(tokenType) || "urn:oasis:names:tc:SAML:2.0:assertion".equals(tokenType)) {
                    dkEncr.setKeyIdentifierType(12);
                    dkEncr.setCustomValueType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID");
                } else if (encrToken instanceof UsernameToken) {
                    dkEncr.setCustomValueType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#UsernameToken");
                } else {
                    dkEncr.setCustomValueType(tokenType);
                }
            }
            AlgorithmSuite.AlgorithmSuiteType algType = this.sbinding.getAlgorithmSuite().getAlgorithmSuiteType();
            dkEncr.setSymmetricEncAlgorithm(algType.getEncryption());
            dkEncr.setDerivedKeyLength(algType.getEncryptionDerivedKeyLength() / 8);
            dkEncr.prepare();
            Element encrDKTokenElem = null;
            encrDKTokenElem = dkEncr.getdktElement();
            this.addDerivedKeyElement(encrDKTokenElem);
            Element refList = dkEncr.encryptForExternalRef(null, encrParts);
            List<Element> attachments = dkEncr.getAttachmentEncryptedDataElements();
            this.addAttachmentsForEncryption(atEnd, refList, attachments);
            return dkEncr;
        }
        catch (Exception e) {
            LOG.log(Level.FINE, e.getMessage(), e);
            this.unassertPolicy((Assertion)recToken, e);
            return null;
        }
    }

    private WSSecBase doEncryption(AbstractTokenWrapper recToken, SecurityToken encrTok, boolean attached, List<WSEncryptionPart> encrParts, boolean atEnd) {
        if (recToken != null && recToken.getToken() != null && !encrParts.isEmpty()) {
            AbstractToken encrToken = recToken.getToken();
            this.assertPolicy(recToken);
            this.assertPolicy(encrToken);
            AlgorithmSuite algorithmSuite = this.sbinding.getAlgorithmSuite();
            if (encrToken.getDerivedKeys() == AbstractToken.DerivedKeys.RequireDerivedKeys) {
                return this.doEncryptionDerived(recToken, encrTok, encrToken, attached, encrParts, atEnd);
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
                String encrTokId = encrTok.getId();
                if (attached) {
                    encrTokId = encrTok.getWsuId();
                    if (encrTokId == null && (encrToken instanceof SecureConversationToken || encrToken instanceof SecurityContextToken)) {
                        encr.setEncKeyIdDirectId(true);
                        encrTokId = encrTok.getId();
                    } else if (encrTokId == null) {
                        encrTokId = encrTok.getId();
                    }
                    if (encrTokId.startsWith("#")) {
                        encrTokId = encrTokId.substring(1);
                    }
                } else {
                    encr.setEncKeyIdDirectId(true);
                }
                if (encrTok.getTokenType() != null) {
                    encr.setCustomReferenceValue(encrTok.getTokenType());
                }
                encr.setEncKeyId(encrTokId);
                encr.setEphemeralKey(encrTok.getSecret());
                Crypto crypto = this.getEncryptionCrypto();
                if (crypto != null) {
                    this.setEncryptionUser(encr, encrToken, false, crypto);
                }
                encr.setEncryptSymmKey(false);
                encr.setSymmetricEncAlgorithm(algorithmSuite.getAlgorithmSuiteType().getEncryption());
                encr.setMGFAlgorithm(algorithmSuite.getAlgorithmSuiteType().getMGFAlgo());
                encr.setDigestAlgorithm(algorithmSuite.getAlgorithmSuiteType().getEncryptionDigest());
                if (encrToken instanceof IssuedToken || encrToken instanceof SpnegoContextToken || encrToken instanceof SecureConversationToken) {
                    Element ref = attached ? encrTok.getAttachedReference() : encrTok.getUnattachedReference();
                    String tokenType = encrTok.getTokenType();
                    if (ref != null) {
                        SecurityTokenReference secRef = new SecurityTokenReference(this.cloneElement(ref), new BSPEnforcer());
                        encr.setSecurityTokenReference(secRef);
                    } else if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1".equals(tokenType) || "urn:oasis:names:tc:SAML:1.0:assertion".equals(tokenType)) {
                        encr.setCustomReferenceValue("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID");
                        encr.setKeyIdentifierType(12);
                    } else if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0".equals(tokenType) || "urn:oasis:names:tc:SAML:2.0:assertion".equals(tokenType)) {
                        encr.setCustomReferenceValue("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID");
                        encr.setKeyIdentifierType(12);
                    } else {
                        encr.setCustomReferenceValue(tokenType);
                        encr.setKeyIdentifierType(12);
                    }
                } else if (encrToken instanceof UsernameToken) {
                    encr.setCustomReferenceValue("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#UsernameToken");
                } else if (encrToken instanceof KerberosToken && !this.isRequestor()) {
                    encr.setCustomReferenceValue("http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#Kerberosv5APREQSHA1");
                    encr.setEncKeyId(encrTok.getSHA1());
                } else if (!this.isRequestor() && encrTok.getSHA1() != null) {
                    encr.setCustomReferenceValue(encrTok.getSHA1());
                    encr.setKeyIdentifierType(10);
                }
                encr.prepare(crypto);
                if (encr.getBSTTokenId() != null) {
                    encr.prependBSTElementToHeader();
                }
                Element refList = encr.encryptForRef(null, encrParts);
                List<Element> attachments = encr.getAttachmentEncryptedDataElements();
                this.addAttachmentsForEncryption(atEnd, refList, attachments);
                return encr;
            }
            catch (WSSecurityException e) {
                LOG.log(Level.FINE, e.getMessage(), e);
                this.unassertPolicy((Assertion)recToken, e);
            }
        }
        return null;
    }

    private void addAttachmentsForEncryption(boolean atEnd, Element refList, List<Element> attachments) {
        block6: {
            block5: {
                if (!atEnd) break block5;
                if (refList != null) {
                    this.insertBeforeBottomUp(refList);
                }
                if (attachments == null) break block6;
                for (Element attachment : attachments) {
                    this.insertBeforeBottomUp(attachment);
                }
                break block6;
            }
            if (refList != null) {
                this.addDerivedKeyElement(refList);
            }
            if (attachments != null) {
                for (Element attachment : attachments) {
                    this.addDerivedKeyElement(attachment);
                }
            }
        }
    }

    private byte[] doSignatureDK(List<WSEncryptionPart> sigs, AbstractTokenWrapper policyAbstractTokenWrapper, AbstractToken policyToken, SecurityToken tok, boolean included) throws WSSecurityException {
        String tokenType;
        Element ref;
        SOAPPart doc = this.saaj.getSOAPPart();
        WSSecDKSign dkSign = new WSSecDKSign(this.secHeader);
        dkSign.setIdAllocator(this.wssConfig.getIdAllocator());
        dkSign.setCallbackLookup(this.callbackLookup);
        dkSign.setAttachmentCallbackHandler(new AttachmentCallbackHandler(this.message));
        dkSign.setStoreBytesInAttachment(this.storeBytesInAttachment);
        dkSign.setExpandXopInclude(this.isExpandXopInclude());
        dkSign.setWsDocInfo(this.wsDocInfo);
        if (policyAbstractTokenWrapper.getToken().getVersion() == SPConstants.SPVersion.SP11) {
            dkSign.setWscVersion(1);
        }
        boolean attached = false;
        if (this.isTokenRequired(policyToken.getIncludeTokenType())) {
            attached = true;
        }
        if ((ref = attached ? tok.getAttachedReference() : tok.getUnattachedReference()) != null) {
            dkSign.setExternalKey(tok.getSecret(), this.cloneElement(ref));
        } else if (!this.isRequestor() && policyToken.getDerivedKeys() == AbstractToken.DerivedKeys.RequireDerivedKeys && tok.getSHA1() != null) {
            SecurityTokenReference tokenRef = new SecurityTokenReference(doc);
            if (tok.getSHA1() != null) {
                String tokenType2 = tok.getTokenType();
                if (policyToken instanceof KerberosToken) {
                    tokenRef.setKeyIdentifier("http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#Kerberosv5APREQSHA1", tok.getSHA1(), true);
                    if (tokenType2 == null) {
                        tokenType2 = "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ";
                    }
                } else {
                    tokenRef.setKeyIdentifierEncKeySHA1(tok.getSHA1());
                    if (tokenType2 == null) {
                        tokenType2 = "http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey";
                    }
                }
                tokenRef.addTokenType(tokenType2);
            }
            dkSign.setExternalKey(tok.getSecret(), tokenRef.getElement());
        } else {
            if (!attached && !this.isRequestor() || policyToken instanceof SecureConversationToken || policyToken instanceof SecurityContextToken) {
                dkSign.setTokenIdDirectId(true);
            }
            dkSign.setExternalKey(tok.getSecret(), tok.getId());
        }
        dkSign.setSignatureAlgorithm(this.sbinding.getAlgorithmSuite().getSymmetricSignature());
        dkSign.setSigCanonicalization(this.sbinding.getAlgorithmSuite().getC14n().getValue());
        AlgorithmSuite.AlgorithmSuiteType algType = this.sbinding.getAlgorithmSuite().getAlgorithmSuiteType();
        dkSign.setDigestAlgorithm(algType.getDigest());
        dkSign.setDerivedKeyLength(algType.getSignatureDerivedKeyLength() / 8);
        boolean includePrefixes = MessageUtils.getContextualBoolean(this.message, "ws-security.add.inclusive.prefixes", true);
        dkSign.setAddInclusivePrefixes(includePrefixes);
        if (tok.getSHA1() != null) {
            tokenType = tok.getTokenType();
            if (tokenType == null) {
                tokenType = "http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey";
            }
            dkSign.setCustomValueType(tokenType);
        } else {
            tokenType = tok.getTokenType();
            if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1".equals(tokenType) || "urn:oasis:names:tc:SAML:1.0:assertion".equals(tokenType)) {
                dkSign.setKeyIdentifierType(12);
                dkSign.setCustomValueType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID");
            } else if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0".equals(tokenType) || "urn:oasis:names:tc:SAML:2.0:assertion".equals(tokenType)) {
                dkSign.setKeyIdentifierType(12);
                dkSign.setCustomValueType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID");
            } else if (policyToken instanceof UsernameToken) {
                dkSign.setCustomValueType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#UsernameToken");
            } else {
                dkSign.setCustomValueType(tokenType);
            }
        }
        dkSign.prepare();
        if (this.sbinding.isProtectTokens()) {
            String sigTokId = tok.getId();
            if (included) {
                sigTokId = tok.getWsuId();
                if (sigTokId == null) {
                    sigTokId = tok.getId();
                }
                if (sigTokId.startsWith("#")) {
                    sigTokId = sigTokId.substring(1);
                }
            }
            sigs.add(new WSEncryptionPart(sigTokId));
            this.assertPolicy(new QName(this.sbinding.getName().getNamespaceURI(), "ProtectTokens"));
        }
        dkSign.getParts().addAll(sigs);
        List<Reference> referenceList = dkSign.addReferencesToSign(sigs);
        if (!referenceList.isEmpty()) {
            Element el = dkSign.getdktElement();
            this.addDerivedKeyElement(el);
            if (this.bottomUpElement == null) {
                dkSign.computeSignature(referenceList, false, null);
            } else {
                dkSign.computeSignature(referenceList, true, this.bottomUpElement);
            }
            this.bottomUpElement = dkSign.getSignatureElement();
            this.mainSigId = dkSign.getSignatureId();
            return dkSign.getSignatureValue();
        }
        return null;
    }

    private byte[] doSignature(List<WSEncryptionPart> sigs, AbstractTokenWrapper policyAbstractTokenWrapper, AbstractToken policyToken, SecurityToken tok, boolean included) throws WSSecurityException {
        if (policyToken.getDerivedKeys() == AbstractToken.DerivedKeys.RequireDerivedKeys) {
            return this.doSignatureDK(sigs, policyAbstractTokenWrapper, policyToken, tok, included);
        }
        WSSecSignature sig = new WSSecSignature(this.secHeader);
        sig.setIdAllocator(this.wssConfig.getIdAllocator());
        sig.setCallbackLookup(this.callbackLookup);
        sig.setAttachmentCallbackHandler(new AttachmentCallbackHandler(this.message));
        sig.setStoreBytesInAttachment(this.storeBytesInAttachment);
        sig.setExpandXopInclude(this.isExpandXopInclude());
        sig.setWsDocInfo(this.wsDocInfo);
        int type = included ? 9 : 11;
        String sigTokId = tok.getId();
        if (policyToken instanceof X509Token) {
            if (this.isRequestor()) {
                sig.setCustomTokenValueType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey");
                sig.setKeyIdentifierType(type);
            } else {
                sig.setEncrKeySha1value(tok.getSHA1());
                sig.setKeyIdentifierType(10);
            }
        } else if (policyToken instanceof UsernameToken) {
            sig.setCustomTokenValueType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#UsernameToken");
            sig.setKeyIdentifierType(type);
        } else if (policyToken instanceof KerberosToken) {
            if (this.isRequestor()) {
                sig.setCustomTokenValueType(tok.getTokenType());
                sig.setKeyIdentifierType(type);
            } else {
                sig.setCustomTokenValueType("http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#Kerberosv5APREQSHA1");
                sig.setKeyIdentifierType(12);
                sigTokId = tok.getSHA1();
            }
        } else {
            Element ref = included ? tok.getAttachedReference() : tok.getUnattachedReference();
            if (ref != null) {
                SecurityTokenReference secRef = new SecurityTokenReference(this.cloneElement(ref), new BSPEnforcer());
                sig.setSecurityTokenReference(secRef);
                sig.setKeyIdentifierType(12);
            } else {
                String tokenType = tok.getTokenType();
                if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1".equals(tokenType) || "urn:oasis:names:tc:SAML:1.0:assertion".equals(tokenType)) {
                    sig.setCustomTokenValueType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID");
                    sig.setKeyIdentifierType(12);
                } else if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0".equals(tokenType) || "urn:oasis:names:tc:SAML:2.0:assertion".equals(tokenType)) {
                    sig.setCustomTokenValueType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID");
                    sig.setKeyIdentifierType(12);
                } else {
                    sig.setCustomTokenValueType(tokenType);
                    sig.setKeyIdentifierType(type);
                }
            }
        }
        if (included) {
            sigTokId = tok.getWsuId();
            if (sigTokId == null) {
                if (policyToken instanceof SecureConversationToken || policyToken instanceof SecurityContextToken) {
                    sig.setKeyIdentifierType(11);
                }
                sigTokId = tok.getId();
            }
            if (sigTokId.startsWith("#")) {
                sigTokId = sigTokId.substring(1);
            }
        }
        if (this.sbinding.isProtectTokens()) {
            this.assertPolicy(new QName(this.sbinding.getName().getNamespaceURI(), "ProtectTokens"));
            if (included) {
                sigs.add(new WSEncryptionPart(sigTokId));
            }
        }
        sig.setCustomTokenId(sigTokId);
        sig.setSecretKey(tok.getSecret());
        sig.setSignatureAlgorithm(this.sbinding.getAlgorithmSuite().getSymmetricSignature());
        boolean includePrefixes = MessageUtils.getContextualBoolean(this.message, "ws-security.add.inclusive.prefixes", true);
        sig.setAddInclusivePrefixes(includePrefixes);
        AlgorithmSuite.AlgorithmSuiteType algType = this.sbinding.getAlgorithmSuite().getAlgorithmSuiteType();
        sig.setDigestAlgo(algType.getDigest());
        sig.setSigCanonicalization(this.sbinding.getAlgorithmSuite().getC14n().getValue());
        Crypto crypto = null;
        crypto = this.sbinding.getProtectionToken() != null ? this.getEncryptionCrypto() : this.getSignatureCrypto();
        this.message.getExchange().put("security.signature.crypto", crypto);
        sig.prepare(crypto);
        sig.getParts().addAll(sigs);
        List<Reference> referenceList = sig.addReferencesToSign(sigs);
        if (!referenceList.isEmpty()) {
            if (this.bottomUpElement == null) {
                sig.computeSignature(referenceList, false, null);
            } else {
                sig.computeSignature(referenceList, true, this.bottomUpElement);
            }
            this.bottomUpElement = sig.getSignatureElement();
            this.mainSigId = sig.getId();
            return sig.getSignatureValue();
        }
        return null;
    }

    private String setupEncryptedKey(AbstractTokenWrapper wrapper, AbstractToken sigToken) throws WSSecurityException {
        WSSecEncryptedKey encrKey = this.getEncryptedKeyBuilder(sigToken);
        this.assertTokenWrapper(wrapper);
        String id = encrKey.getId();
        byte[] secret = encrKey.getEphemeralKey();
        Instant created = Instant.now();
        Instant expires = created.plusSeconds(WSS4JUtils.getSecurityTokenLifetime(this.message) / 1000L);
        SecurityToken tempTok = new SecurityToken(id, encrKey.getEncryptedKeyElement(), created, expires);
        tempTok.setSecret(secret);
        tempTok.setSHA1(SymmetricBindingHandler.getSHA1(encrKey.getEncryptedEphemeralKey()));
        this.tokenStore.add(tempTok);
        this.tokenStore.add(tempTok.getSHA1(), tempTok);
        String bstTokenId = encrKey.getBSTTokenId();
        if (bstTokenId != null && bstTokenId.length() > 0) {
            encrKey.prependBSTElementToHeader();
        }
        return id;
    }

    private static String getSHA1(byte[] input) {
        try {
            byte[] digestBytes = KeyUtils.generateDigest(input);
            return XMLUtils.encodeToString(digestBytes);
        }
        catch (WSSecurityException wSSecurityException) {
            return null;
        }
    }

    private String setupUTDerivedKey(UsernameToken sigToken) throws WSSecurityException {
        boolean useMac = this.hasSignedPartsOrElements();
        WSSecUsernameToken usernameToken = this.addDKUsernameToken(sigToken, useMac);
        String id = usernameToken.getId();
        byte[] secret = usernameToken.getDerivedKey();
        Instant created = Instant.now();
        Instant expires = created.plusSeconds(WSS4JUtils.getSecurityTokenLifetime(this.message) / 1000L);
        SecurityToken tempTok = new SecurityToken(id, usernameToken.getUsernameTokenElement(), created, expires);
        tempTok.setSecret(secret);
        this.tokenStore.add(tempTok);
        return id;
    }

    private SecurityToken getEncryptedKey() {
        WSSecurityEngineResult encryptedKeyResult = this.getEncryptedKeyResult();
        if (encryptedKeyResult != null) {
            Instant created = Instant.now();
            Instant expires = created.plusSeconds(WSS4JUtils.getSecurityTokenLifetime(this.message) / 1000L);
            String encryptedKeyID = (String)encryptedKeyResult.get("id");
            SecurityToken securityToken = new SecurityToken(encryptedKeyID, created, expires);
            securityToken.setSecret((byte[])encryptedKeyResult.get("secret"));
            securityToken.setSHA1(SymmetricBindingHandler.getSHA1((byte[])encryptedKeyResult.get("encrypted-ephemeral-key-bytes")));
            return securityToken;
        }
        return null;
    }

    private SecurityToken getUTDerivedKey() throws WSSecurityException {
        List<WSHandlerResult> results = CastUtils.cast((List)this.message.getExchange().getInMessage().get("RECV_RESULTS"));
        for (WSHandlerResult rResult : results) {
            Iterator<WSSecurityEngineResult> iterator2;
            List<WSSecurityEngineResult> wsSecEngineResults = rResult.getActionResults().get(8192);
            if (wsSecEngineResults == null || !(iterator2 = wsSecEngineResults.iterator()).hasNext()) continue;
            WSSecurityEngineResult wser = iterator2.next();
            String utID = (String)wser.get("id");
            if (utID == null || utID.length() == 0) {
                utID = this.wssConfig.getIdAllocator().createId("UsernameToken-", null);
            }
            Instant created = Instant.now();
            Instant expires = created.plusSeconds(WSS4JUtils.getSecurityTokenLifetime(this.message) / 1000L);
            SecurityToken securityToken = new SecurityToken(utID, created, expires);
            byte[] secret = (byte[])wser.get("secret");
            securityToken.setSecret(secret);
            return securityToken;
        }
        return null;
    }

    private boolean hasSignedPartsOrElements() {
        return PolicyUtils.getFirstAssertionByLocalname(this.aim, "SignedParts") != null || PolicyUtils.getFirstAssertionByLocalname(this.aim, "SignedElements") != null;
    }
}

