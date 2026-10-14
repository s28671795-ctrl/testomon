/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyhandlers;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import javax.xml.crypto.dsig.Reference;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.rt.security.utils.SecurityUtils;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.apache.cxf.ws.security.wss4j.AttachmentCallbackHandler;
import org.apache.cxf.ws.security.wss4j.WSS4JUtils;
import org.apache.cxf.ws.security.wss4j.policyhandlers.AbstractBindingBuilder;
import org.apache.neethi.Assertion;
import org.apache.wss4j.common.WSEncryptionPart;
import org.apache.wss4j.common.bsp.BSPEnforcer;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.common.token.SecurityTokenReference;
import org.apache.wss4j.dom.engine.WSSConfig;
import org.apache.wss4j.dom.message.WSSecDKSign;
import org.apache.wss4j.dom.message.WSSecEncryptedKey;
import org.apache.wss4j.dom.message.WSSecHeader;
import org.apache.wss4j.dom.message.WSSecSignature;
import org.apache.wss4j.dom.message.WSSecTimestamp;
import org.apache.wss4j.dom.message.WSSecUsernameToken;
import org.apache.wss4j.policy.SP11Constants;
import org.apache.wss4j.policy.SP12Constants;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.AbstractToken;
import org.apache.wss4j.policy.model.AlgorithmSuite;
import org.apache.wss4j.policy.model.Header;
import org.apache.wss4j.policy.model.IssuedToken;
import org.apache.wss4j.policy.model.KerberosToken;
import org.apache.wss4j.policy.model.KeyValueToken;
import org.apache.wss4j.policy.model.SamlToken;
import org.apache.wss4j.policy.model.SecureConversationToken;
import org.apache.wss4j.policy.model.SecurityContextToken;
import org.apache.wss4j.policy.model.SignedElements;
import org.apache.wss4j.policy.model.SignedParts;
import org.apache.wss4j.policy.model.SpnegoContextToken;
import org.apache.wss4j.policy.model.SupportingTokens;
import org.apache.wss4j.policy.model.TransportBinding;
import org.apache.wss4j.policy.model.TransportToken;
import org.apache.wss4j.policy.model.UsernameToken;
import org.apache.wss4j.policy.model.X509Token;
import org.w3c.dom.Element;

public class TransportBindingHandler
extends AbstractBindingBuilder {
    TransportBinding tbinding;

    public TransportBindingHandler(WSSConfig config, TransportBinding binding, SOAPMessage saaj, WSSecHeader secHeader, AssertionInfoMap aim, SoapMessage message) throws SOAPException {
        super(config, binding, saaj, secHeader, aim, message);
        this.tbinding = binding;
    }

    private void addSignedSupportingTokens(SupportingTokens sgndSuppTokens) throws Exception {
        for (AbstractToken token : sgndSuppTokens.getTokens()) {
            SamlAssertionWrapper assertionWrapper;
            this.assertToken(token);
            if (token != null && !this.isTokenRequired(token.getIncludeTokenType())) continue;
            if (token instanceof UsernameToken) {
                WSSecUsernameToken utBuilder = this.addUsernameToken((UsernameToken)token);
                if (utBuilder == null) continue;
                utBuilder.prepare();
                utBuilder.appendToHeader();
                continue;
            }
            if (token instanceof IssuedToken || token instanceof KerberosToken || token instanceof SpnegoContextToken) {
                SecurityToken secTok = this.getSecurityToken();
                if (!this.isTokenRequired(token.getIncludeTokenType())) continue;
                this.addEncryptedKeyElement(this.cloneElement(secTok.getToken()));
                continue;
            }
            if (!(token instanceof SamlToken) || (assertionWrapper = this.addSamlToken((SamlToken)token)) == null) continue;
            Element envelope = this.saaj.getSOAPPart().getEnvelope();
            envelope = (Element)DOMUtils.getDomElement(envelope);
            this.addSupportingElement(assertionWrapper.toDOM(envelope.getOwnerDocument()));
        }
    }

    public void handleBinding() {
        WSSecTimestamp timestamp = this.createTimestamp();
        this.handleLayout(timestamp);
        try {
            if (this.isRequestor()) {
                TransportToken transportTokenWrapper = this.tbinding.getTransportToken();
                if (transportTokenWrapper != null) {
                    AbstractToken transportToken = transportTokenWrapper.getToken();
                    if (transportToken instanceof IssuedToken) {
                        SecurityToken secToken = this.getSecurityToken();
                        if (secToken == null) {
                            this.unassertPolicy((Assertion)transportToken, "No transport token id");
                            return;
                        }
                        this.assertPolicy(transportToken);
                        if (this.isTokenRequired(transportToken.getIncludeTokenType())) {
                            Element el = secToken.getToken();
                            this.addEncryptedKeyElement(this.cloneElement(el));
                        }
                    }
                    this.assertToken(transportToken);
                    this.assertTokenWrapper(transportTokenWrapper);
                }
                this.handleNonEndorsingSupportingTokens();
                if (transportTokenWrapper != null) {
                    this.handleEndorsingSupportingTokens();
                }
            } else {
                this.handleNonEndorsingSupportingTokens();
                if (this.tbinding != null && this.tbinding.getTransportToken() != null) {
                    this.assertTokenWrapper(this.tbinding.getTransportToken());
                    this.assertToken(this.tbinding.getTransportToken().getToken());
                    this.handleEndorsingSupportingTokens();
                }
                this.addSignatureConfirmation(null);
            }
        }
        catch (Exception e) {
            LOG.log(Level.FINE, e.getMessage(), e);
            throw new Fault(e);
        }
        if (this.tbinding != null) {
            this.assertPolicy(this.tbinding.getName());
            this.assertAlgorithmSuite(this.tbinding.getAlgorithmSuite());
            this.assertWSSProperties(this.tbinding.getName().getNamespaceURI());
            this.assertTrustProperties(this.tbinding.getName().getNamespaceURI());
        }
        this.assertPolicy(SP12Constants.SIGNED_PARTS);
        this.assertPolicy(SP11Constants.SIGNED_PARTS);
        this.assertPolicy(SP12Constants.ENCRYPTED_PARTS);
        this.assertPolicy(SP11Constants.ENCRYPTED_PARTS);
    }

    private void handleNonEndorsingSupportingTokens() throws Exception {
        SupportingTokens sgndSuppTokens;
        Collection<AssertionInfo> ais = this.getAllAssertionsByLocalname("SignedSupportingTokens");
        if (!ais.isEmpty()) {
            for (AssertionInfo ai : ais) {
                sgndSuppTokens = (SupportingTokens)ai.getAssertion();
                if (sgndSuppTokens != null) {
                    this.addSignedSupportingTokens(sgndSuppTokens);
                }
                ai.setAsserted(true);
            }
        }
        if (!(ais = this.getAllAssertionsByLocalname("SignedEncryptedSupportingTokens")).isEmpty()) {
            for (AssertionInfo ai : ais) {
                sgndSuppTokens = (SupportingTokens)ai.getAssertion();
                if (sgndSuppTokens != null) {
                    this.addSignedSupportingTokens(sgndSuppTokens);
                }
                ai.setAsserted(true);
            }
        }
        if (!(ais = this.getAllAssertionsByLocalname("EncryptedSupportingTokens")).isEmpty()) {
            for (AssertionInfo ai : ais) {
                SupportingTokens encrSuppTokens = (SupportingTokens)ai.getAssertion();
                if (encrSuppTokens != null) {
                    this.addSignedSupportingTokens(encrSuppTokens);
                }
                ai.setAsserted(true);
            }
        }
        if (!(ais = this.getAllAssertionsByLocalname("SupportingTokens")).isEmpty()) {
            for (AssertionInfo ai : ais) {
                SupportingTokens suppTokens = (SupportingTokens)ai.getAssertion();
                if (suppTokens != null && suppTokens.getTokens() != null && suppTokens.getTokens().size() > 0) {
                    this.handleSupportingTokens(suppTokens, false, new ArrayList<AbstractBindingBuilder.SupportingToken>());
                }
                ai.setAsserted(true);
            }
        }
    }

    private void handleEndorsingSupportingTokens() throws Exception {
        SupportingTokens endSuppTokens;
        Collection<AssertionInfo> ais = this.getAllAssertionsByLocalname("SignedEndorsingSupportingTokens");
        if (!ais.isEmpty()) {
            SupportingTokens sgndSuppTokens = null;
            for (AssertionInfo ai : ais) {
                sgndSuppTokens = (SupportingTokens)ai.getAssertion();
                ai.setAsserted(true);
            }
            if (sgndSuppTokens != null) {
                for (AbstractToken token : sgndSuppTokens.getTokens()) {
                    this.handleEndorsingToken(token, sgndSuppTokens);
                }
            }
        }
        if (!(ais = this.getAllAssertionsByLocalname("EndorsingSupportingTokens")).isEmpty()) {
            endSuppTokens = null;
            for (AssertionInfo ai : ais) {
                endSuppTokens = (SupportingTokens)ai.getAssertion();
                ai.setAsserted(true);
            }
            if (endSuppTokens != null) {
                for (AbstractToken token : endSuppTokens.getTokens()) {
                    this.handleEndorsingToken(token, endSuppTokens);
                }
            }
        }
        if (!(ais = this.getAllAssertionsByLocalname("EndorsingEncryptedSupportingTokens")).isEmpty()) {
            endSuppTokens = null;
            for (AssertionInfo ai : ais) {
                endSuppTokens = (SupportingTokens)ai.getAssertion();
                ai.setAsserted(true);
            }
            if (endSuppTokens != null) {
                for (AbstractToken token : endSuppTokens.getTokens()) {
                    this.handleEndorsingToken(token, endSuppTokens);
                }
            }
        }
        if (!(ais = this.getAllAssertionsByLocalname("SignedEndorsingEncryptedSupportingTokens")).isEmpty()) {
            endSuppTokens = null;
            for (AssertionInfo ai : ais) {
                endSuppTokens = (SupportingTokens)ai.getAssertion();
                ai.setAsserted(true);
            }
            if (endSuppTokens != null) {
                for (AbstractToken token : endSuppTokens.getTokens()) {
                    this.handleEndorsingToken(token, endSuppTokens);
                }
            }
        }
    }

    private void handleEndorsingToken(AbstractToken token, SupportingTokens wrapper) throws Exception {
        this.assertToken(token);
        if (token != null && !this.isTokenRequired(token.getIncludeTokenType())) {
            return;
        }
        if (token instanceof IssuedToken || token instanceof SecureConversationToken || token instanceof SecurityContextToken || token instanceof KerberosToken || token instanceof SpnegoContextToken) {
            this.addSig(this.doIssuedTokenSignature(token, wrapper));
        } else if (token instanceof X509Token || token instanceof KeyValueToken) {
            this.addSig(this.doX509TokenSignature(token, wrapper));
        } else if (token instanceof SamlToken) {
            SamlAssertionWrapper assertionWrapper = this.addSamlToken((SamlToken)token);
            Element envelope = this.saaj.getSOAPPart().getEnvelope();
            envelope = (Element)DOMUtils.getDomElement(envelope);
            assertionWrapper.toDOM(envelope.getOwnerDocument());
            this.storeAssertionAsSecurityToken(assertionWrapper);
            this.addSig(this.doIssuedTokenSignature(token, wrapper));
        } else if (token instanceof UsernameToken) {
            WSSecUsernameToken usernameToken = this.addDKUsernameToken((UsernameToken)token, true);
            String id = usernameToken.getId();
            byte[] secret = usernameToken.getDerivedKey();
            Instant created = Instant.now();
            Instant expires = created.plusSeconds(WSS4JUtils.getSecurityTokenLifetime(this.message) / 1000L);
            SecurityToken tempTok = new SecurityToken(id, usernameToken.getUsernameTokenElement(), created, expires);
            tempTok.setSecret(secret);
            this.getTokenStore().add(tempTok);
            this.message.put("ws-security.token.id", (Object)tempTok.getId());
            this.addSig(this.doIssuedTokenSignature(token, wrapper));
        }
    }

    private byte[] doX509TokenSignature(AbstractToken token, SupportingTokens wrapper) throws Exception {
        List<WSEncryptionPart> sigParts = this.signPartsAndElements(wrapper.getSignedParts(), wrapper.getSignedElements());
        if (token.getDerivedKeys() == AbstractToken.DerivedKeys.RequireDerivedKeys) {
            WSSecEncryptedKey encrKey = this.getEncryptedKeyBuilder(token);
            this.assertPolicy(wrapper);
            Element bstElem = encrKey.getBinarySecurityTokenElement();
            if (bstElem != null) {
                this.addTopDownElement(bstElem);
            }
            encrKey.appendToHeader();
            WSSecDKSign dkSig = new WSSecDKSign(this.secHeader);
            dkSig.setIdAllocator(this.wssConfig.getIdAllocator());
            dkSig.setCallbackLookup(this.callbackLookup);
            if (token.getVersion() == SPConstants.SPVersion.SP11) {
                dkSig.setWscVersion(1);
            }
            dkSig.setSigCanonicalization(this.binding.getAlgorithmSuite().getC14n().getValue());
            dkSig.setSignatureAlgorithm(this.binding.getAlgorithmSuite().getSymmetricSignature());
            dkSig.setAttachmentCallbackHandler(new AttachmentCallbackHandler(this.message));
            dkSig.setStoreBytesInAttachment(this.storeBytesInAttachment);
            dkSig.setExpandXopInclude(this.isExpandXopInclude());
            dkSig.setWsDocInfo(this.wsDocInfo);
            AlgorithmSuite.AlgorithmSuiteType algType = this.binding.getAlgorithmSuite().getAlgorithmSuiteType();
            dkSig.setDerivedKeyLength(algType.getSignatureDerivedKeyLength() / 8);
            dkSig.setExternalKey(encrKey.getEphemeralKey(), encrKey.getId());
            dkSig.prepare();
            dkSig.getParts().addAll(sigParts);
            List<Reference> referenceList = dkSig.addReferencesToSign(sigParts);
            dkSig.appendDKElementToHeader();
            dkSig.computeSignature(referenceList, false, null);
            return dkSig.getSignatureValue();
        }
        WSSecSignature sig = this.getSignatureBuilder(token, false, false);
        this.assertPolicy(wrapper);
        if (sig != null) {
            sig.prependBSTElementToHeader();
            List<Reference> referenceList = sig.addReferencesToSign(sigParts);
            if (this.bottomUpElement == null) {
                sig.computeSignature(referenceList, false, null);
            } else {
                sig.computeSignature(referenceList, true, this.bottomUpElement);
            }
            this.bottomUpElement = sig.getSignatureElement();
            this.mainSigId = sig.getId();
            return sig.getSignatureValue();
        }
        return new byte[0];
    }

    private byte[] doIssuedTokenSignature(AbstractToken token, SupportingTokens wrapper) throws Exception {
        boolean tokenIncluded = false;
        SecurityToken secTok = this.getSecurityToken();
        if (secTok == null) {
            LOG.fine("The retrieved SecurityToken was null");
            Exception ex = new Exception("The retrieved SecurityToken was null");
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex);
        }
        if (this.isTokenRequired(token.getIncludeTokenType())) {
            Element el = this.cloneElement(secTok.getToken());
            this.addEncryptedKeyElement(el);
            tokenIncluded = true;
        }
        List<WSEncryptionPart> sigParts = this.signPartsAndElements(wrapper.getSignedParts(), wrapper.getSignedElements());
        if (token.getDerivedKeys() == AbstractToken.DerivedKeys.RequireDerivedKeys) {
            return this.doDerivedKeySignature(tokenIncluded, secTok, token, sigParts);
        }
        return this.doSignature(tokenIncluded, secTok, token, sigParts);
    }

    private byte[] doDerivedKeySignature(boolean tokenIncluded, SecurityToken secTok, AbstractToken token, List<WSEncryptionPart> sigParts) throws Exception {
        WSSecDKSign dkSign = new WSSecDKSign(this.secHeader);
        dkSign.setIdAllocator(this.wssConfig.getIdAllocator());
        dkSign.setCallbackLookup(this.callbackLookup);
        dkSign.setAttachmentCallbackHandler(new AttachmentCallbackHandler(this.message));
        dkSign.setStoreBytesInAttachment(this.storeBytesInAttachment);
        dkSign.setExpandXopInclude(this.isExpandXopInclude());
        dkSign.setWsDocInfo(this.wsDocInfo);
        AlgorithmSuite algorithmSuite = this.tbinding.getAlgorithmSuite();
        Element ref = tokenIncluded ? secTok.getAttachedReference() : secTok.getUnattachedReference();
        if (ref != null) {
            dkSign.setExternalKey(secTok.getSecret(), this.cloneElement(ref));
        } else {
            dkSign.setExternalKey(secTok.getSecret(), secTok.getId());
        }
        if (token instanceof UsernameToken) {
            dkSign.setCustomValueType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#UsernameToken");
        }
        dkSign.setSignatureAlgorithm(algorithmSuite.getSymmetricSignature());
        AlgorithmSuite.AlgorithmSuiteType algType = this.binding.getAlgorithmSuite().getAlgorithmSuiteType();
        dkSign.setDerivedKeyLength(algType.getSignatureDerivedKeyLength() / 8);
        if (token.getVersion() == SPConstants.SPVersion.SP11) {
            dkSign.setWscVersion(1);
        }
        dkSign.prepare();
        this.addDerivedKeyElement(dkSign.getdktElement());
        dkSign.getParts().addAll(sigParts);
        List<Reference> referenceList = dkSign.addReferencesToSign(sigParts);
        dkSign.computeSignature(referenceList, false, null);
        return dkSign.getSignatureValue();
    }

    private byte[] doSignature(boolean tokenIncluded, SecurityToken secTok, AbstractToken token, List<WSEncryptionPart> sigParts) throws Exception {
        WSSecSignature sig = new WSSecSignature(this.secHeader);
        sig.setIdAllocator(this.wssConfig.getIdAllocator());
        sig.setCallbackLookup(this.callbackLookup);
        sig.setAttachmentCallbackHandler(new AttachmentCallbackHandler(this.message));
        sig.setStoreBytesInAttachment(this.storeBytesInAttachment);
        sig.setExpandXopInclude(this.isExpandXopInclude());
        sig.setWsDocInfo(this.wsDocInfo);
        Element ref = tokenIncluded ? secTok.getAttachedReference() : secTok.getUnattachedReference();
        if (ref != null) {
            SecurityTokenReference secRef = new SecurityTokenReference(this.cloneElement(ref), new BSPEnforcer());
            sig.setSecurityTokenReference(secRef);
            sig.setKeyIdentifierType(12);
        } else if (token instanceof UsernameToken) {
            sig.setCustomTokenId(secTok.getId());
            sig.setCustomTokenValueType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#UsernameToken");
            int type = tokenIncluded ? 9 : 11;
            sig.setKeyIdentifierType(type);
        } else if (secTok.getTokenType() == null) {
            sig.setCustomTokenValueType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID");
            sig.setKeyIdentifierType(12);
        } else {
            String id = secTok.getWsuId();
            if (id == null) {
                sig.setCustomTokenId(secTok.getId());
                sig.setKeyIdentifierType(11);
            } else {
                sig.setCustomTokenId(secTok.getWsuId());
                sig.setKeyIdentifierType(9);
            }
            String tokenType = secTok.getTokenType();
            if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1".equals(tokenType) || "urn:oasis:names:tc:SAML:1.0:assertion".equals(tokenType)) {
                sig.setCustomTokenValueType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID");
                sig.setKeyIdentifierType(12);
            } else if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0".equals(tokenType) || "urn:oasis:names:tc:SAML:2.0:assertion".equals(tokenType)) {
                sig.setCustomTokenValueType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID");
                sig.setKeyIdentifierType(12);
            } else {
                sig.setCustomTokenValueType(tokenType);
            }
        }
        Crypto crypto = null;
        if (secTok.getSecret() == null) {
            String password;
            sig.setX509Certificate(secTok.getX509Certificate());
            crypto = secTok.getCrypto();
            if (crypto == null) {
                crypto = this.getSignatureCrypto();
            }
            if (crypto == null) {
                LOG.fine("No signature Crypto properties are available");
                Exception ex = new Exception("No signature Crypto properties are available");
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex);
            }
            String uname = crypto.getX509Identifier(secTok.getX509Certificate());
            if (uname == null) {
                String userNameKey = "security.signature.username";
                uname = (String)SecurityUtils.getSecurityPropertyValue(userNameKey, this.message);
            }
            if (StringUtils.isEmpty(password = (String)SecurityUtils.getSecurityPropertyValue("security.signature.password", this.message))) {
                password = this.getPassword(uname, token, 3);
            }
            sig.setUserInfo(uname, password);
            sig.setSignatureAlgorithm(this.binding.getAlgorithmSuite().getAsymmetricSignature());
        } else {
            crypto = this.getSignatureCrypto();
            sig.setSecretKey(secTok.getSecret());
            sig.setSignatureAlgorithm(this.binding.getAlgorithmSuite().getSymmetricSignature());
        }
        sig.setSigCanonicalization(this.binding.getAlgorithmSuite().getC14n().getValue());
        AlgorithmSuite.AlgorithmSuiteType algType = this.binding.getAlgorithmSuite().getAlgorithmSuiteType();
        sig.setDigestAlgo(algType.getDigest());
        sig.prepare(crypto);
        sig.getParts().addAll(sigParts);
        List<Reference> referenceList = sig.addReferencesToSign(sigParts);
        if (this.bottomUpElement == null) {
            sig.computeSignature(referenceList, false, null);
        } else {
            sig.computeSignature(referenceList, true, this.bottomUpElement);
        }
        this.bottomUpElement = sig.getSignatureElement();
        this.mainSigId = sig.getId();
        return sig.getSignatureValue();
    }

    private List<WSEncryptionPart> signPartsAndElements(SignedParts signedParts, SignedElements signedElements) throws SOAPException {
        ArrayList<WSEncryptionPart> result = new ArrayList<WSEncryptionPart>();
        ArrayList<Element> found = new ArrayList<Element>();
        if (this.timestampEl != null) {
            WSEncryptionPart timestampPart = new WSEncryptionPart("Timestamp", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Element");
            String id = this.addWsuIdToElement(this.timestampEl.getElement());
            timestampPart.setId(id);
            timestampPart.setElement(this.timestampEl.getElement());
            found.add(timestampPart.getElement());
            result.add(timestampPart);
        }
        if (signedParts != null) {
            ArrayList<WSEncryptionPart> parts = new ArrayList<WSEncryptionPart>();
            boolean isSignBody = signedParts.isBody();
            for (Header head : signedParts.getHeaders()) {
                WSEncryptionPart wep = new WSEncryptionPart(head.getName(), head.getNamespace(), "Element");
                parts.add(wep);
            }
            result.addAll(this.getParts(true, isSignBody, parts, found));
        }
        if (signedElements != null) {
            result.addAll(this.getElements("Element", signedElements.getXPaths(), found, true));
        }
        return result;
    }
}

