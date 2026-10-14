/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.processor.output;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import org.apache.wss4j.common.derivedKey.AlgoFactory;
import org.apache.wss4j.common.derivedKey.DerivationAlgorithm;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.KeyUtils;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.impl.processor.output.OutputProcessorUtils;
import org.apache.wss4j.stax.impl.processor.output.ReferenceListOutputProcessor;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.config.JCEAlgorithmMapper;
import org.apache.xml.security.stax.ext.AbstractOutputProcessor;
import org.apache.xml.security.stax.ext.OutputProcessorChain;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.stax.XMLSecAttribute;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.impl.securityToken.GenericOutboundSecurityToken;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.apache.xml.security.stax.securityToken.OutboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;
import org.apache.xml.security.stax.securityToken.SecurityTokenProvider;
import org.apache.xml.security.utils.XMLUtils;

public class DerivedKeyTokenOutputProcessor
extends AbstractOutputProcessor {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void processEvent(XMLSecEvent xmlSecEvent, OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
        try {
            byte[] secret;
            String tokenId = (String)outputProcessorChain.getSecurityContext().get("PROP_USE_THIS_TOKEN_ID_FOR_DERIVED_KEY");
            if (tokenId == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE);
            }
            SecurityTokenProvider<OutboundSecurityToken> wrappingSecurityTokenProvider = outputProcessorChain.getSecurityContext().getSecurityTokenProvider(tokenId);
            if (wrappingSecurityTokenProvider == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE);
            }
            OutboundSecurityToken wrappingSecurityToken = wrappingSecurityTokenProvider.getSecurityToken();
            if (wrappingSecurityToken == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE);
            }
            final String wsuIdDKT = IDGenerator.generateID(null);
            int offset = 0;
            int length = 0;
            XMLSecurityConstants.Action action = this.getAction();
            if (WSSConstants.SIGNATURE_WITH_DERIVED_KEY.equals(action)) {
                if (((WSSSecurityProperties)this.getSecurityProperties()).getDerivedSignatureKeyLength() > 0) {
                    length = ((WSSSecurityProperties)this.getSecurityProperties()).getDerivedSignatureKeyLength();
                } else {
                    length = JCEAlgorithmMapper.getKeyLengthFromURI(this.getSecurityProperties().getSignatureAlgorithm()) / 8;
                    if (length == 0) {
                        length = KeyUtils.getKeyLength(this.getSecurityProperties().getSignatureAlgorithm()) / 8;
                    }
                }
            } else if (WSSConstants.ENCRYPT_WITH_DERIVED_KEY.equals(action)) {
                if (((WSSSecurityProperties)this.getSecurityProperties()).getDerivedEncryptionKeyLength() > 0) {
                    length = ((WSSSecurityProperties)this.getSecurityProperties()).getDerivedEncryptionKeyLength();
                } else {
                    length = JCEAlgorithmMapper.getKeyLengthFromURI(this.getSecurityProperties().getEncryptionSymAlgorithm()) / 8;
                    if (length == 0) {
                        length = KeyUtils.getKeyLength(this.getSecurityProperties().getEncryptionSymAlgorithm()) / 8;
                    }
                }
            }
            String defaultLabel = "WS-SecureConversationWS-SecureConversation";
            byte[] label = defaultLabel.getBytes(StandardCharsets.UTF_8);
            byte[] nonce = WSSConstants.generateBytes(16);
            byte[] seed = new byte[label.length + nonce.length];
            System.arraycopy(label, 0, seed, 0, label.length);
            System.arraycopy(nonce, 0, seed, label.length, nonce.length);
            DerivationAlgorithm derivationAlgorithm = AlgoFactory.getInstance("http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1");
            if (WSSecurityTokenConstants.SECURITY_CONTEXT_TOKEN.equals(wrappingSecurityToken.getTokenType())) {
                WSPasswordCallback passwordCallback = new WSPasswordCallback(wsuIdDKT, 9);
                WSSUtils.doSecretKeyCallback(((WSSSecurityProperties)this.securityProperties).getCallbackHandler(), passwordCallback, wsuIdDKT);
                if (passwordCallback.getKey() == null) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noKey", new Object[]{wsuIdDKT});
                }
                secret = passwordCallback.getKey();
            } else {
                secret = wrappingSecurityToken.getSecretKey("").getEncoded();
            }
            final byte[] derivedKeyBytes = derivationAlgorithm.createKey(secret, seed, offset, length);
            final GenericOutboundSecurityToken derivedKeySecurityToken = new GenericOutboundSecurityToken(wsuIdDKT, WSSecurityTokenConstants.DerivedKeyToken){

                @Override
                public Key getSecretKey(String algorithmURI) throws WSSecurityException {
                    Key key = null;
                    try {
                        key = super.getSecretKey(algorithmURI);
                    }
                    catch (XMLSecurityException e) {
                        throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, (Exception)e);
                    }
                    if (key != null) {
                        return key;
                    }
                    String algoFamily = JCEAlgorithmMapper.getJCEKeyAlgorithmFromURI(algorithmURI);
                    key = new SecretKeySpec(derivedKeyBytes, algoFamily);
                    this.setSecretKey(algorithmURI, key);
                    return key;
                }
            };
            derivedKeySecurityToken.setKeyWrappingToken(wrappingSecurityToken);
            wrappingSecurityToken.addWrappedToken(derivedKeySecurityToken);
            SecurityTokenProvider<OutboundSecurityToken> derivedKeysecurityTokenProvider = new SecurityTokenProvider<OutboundSecurityToken>(){

                @Override
                public OutboundSecurityToken getSecurityToken() throws WSSecurityException {
                    return derivedKeySecurityToken;
                }

                @Override
                public String getId() {
                    return wsuIdDKT;
                }
            };
            if (WSSConstants.SIGNATURE_WITH_DERIVED_KEY.equals(action)) {
                outputProcessorChain.getSecurityContext().put("PROP_USE_THIS_TOKEN_ID_FOR_SIGNATURE", wsuIdDKT);
            } else if (WSSConstants.ENCRYPT_WITH_DERIVED_KEY.equals(action)) {
                outputProcessorChain.getSecurityContext().put("PROP_USE_THIS_TOKEN_ID_FOR_ENCRYPTION", wsuIdDKT);
            }
            outputProcessorChain.getSecurityContext().registerSecurityTokenProvider(wsuIdDKT, derivedKeysecurityTokenProvider);
            FinalDerivedKeyTokenOutputProcessor finalDerivedKeyTokenOutputProcessor = new FinalDerivedKeyTokenOutputProcessor(derivedKeySecurityToken, offset, length, XMLUtils.encodeToString(nonce), ((WSSSecurityProperties)this.getSecurityProperties()).isUse200512Namespace(), wrappingSecurityToken.getSha1Identifier());
            finalDerivedKeyTokenOutputProcessor.setXMLSecurityProperties(this.getSecurityProperties());
            finalDerivedKeyTokenOutputProcessor.setAction(this.getAction());
            if (wrappingSecurityToken.getProcessor() != null) {
                finalDerivedKeyTokenOutputProcessor.addBeforeProcessor(wrappingSecurityToken.getProcessor());
            } else {
                finalDerivedKeyTokenOutputProcessor.addAfterProcessor(ReferenceListOutputProcessor.class.getName());
            }
            finalDerivedKeyTokenOutputProcessor.init(outputProcessorChain);
            derivedKeySecurityToken.setProcessor(finalDerivedKeyTokenOutputProcessor);
        }
        finally {
            outputProcessorChain.removeProcessor(this);
        }
        outputProcessorChain.processEvent(xmlSecEvent);
    }

    static class FinalDerivedKeyTokenOutputProcessor
    extends AbstractOutputProcessor {
        private final OutboundSecurityToken securityToken;
        private final int offset;
        private final int length;
        private final String nonce;
        private final boolean use200512Namespace;
        private final String sha1Identifier;

        FinalDerivedKeyTokenOutputProcessor(OutboundSecurityToken securityToken, int offset, int length, String nonce, boolean use200512Namespace, String sha1Identifier) throws XMLSecurityException {
            this.securityToken = securityToken;
            this.offset = offset;
            this.length = length;
            this.nonce = nonce;
            this.use200512Namespace = use200512Namespace;
            this.sha1Identifier = sha1Identifier;
        }

        @Override
        public void processEvent(XMLSecEvent xmlSecEvent, OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
            outputProcessorChain.processEvent(xmlSecEvent);
            if (WSSUtils.isSecurityHeaderElement(xmlSecEvent, ((WSSSecurityProperties)this.getSecurityProperties()).getActor())) {
                QName headerElementName = this.getHeaderElementName();
                OutputProcessorUtils.updateSecurityHeaderOrder(outputProcessorChain, headerElementName, this.getAction(), false);
                OutputProcessorChain subOutputProcessorChain = outputProcessorChain.createSubChain(this);
                ArrayList<XMLSecAttribute> attributes = new ArrayList<XMLSecAttribute>(1);
                attributes.add(this.createAttribute(WSSConstants.ATT_WSU_ID, this.securityToken.getId()));
                this.createStartElementAndOutputAsEvent(subOutputProcessorChain, headerElementName, true, attributes);
                this.createSecurityTokenReferenceStructureForDerivedKey(subOutputProcessorChain, this.securityToken, ((WSSSecurityProperties)this.getSecurityProperties()).getDerivedKeyKeyIdentifier(), ((WSSSecurityProperties)this.getSecurityProperties()).getDerivedKeyTokenReference(), this.getSecurityProperties().isUseSingleCert());
                this.createStartElementAndOutputAsEvent(subOutputProcessorChain, this.getOffsetName(), false, null);
                this.createCharactersAndOutputAsEvent(subOutputProcessorChain, "" + this.offset);
                this.createEndElementAndOutputAsEvent(subOutputProcessorChain, this.getOffsetName());
                this.createStartElementAndOutputAsEvent(subOutputProcessorChain, this.getLengthName(), false, null);
                this.createCharactersAndOutputAsEvent(subOutputProcessorChain, "" + this.length);
                this.createEndElementAndOutputAsEvent(subOutputProcessorChain, this.getLengthName());
                this.createStartElementAndOutputAsEvent(subOutputProcessorChain, this.getNonceName(), false, null);
                this.createCharactersAndOutputAsEvent(subOutputProcessorChain, this.nonce);
                this.createEndElementAndOutputAsEvent(subOutputProcessorChain, this.getNonceName());
                this.createEndElementAndOutputAsEvent(subOutputProcessorChain, headerElementName);
                outputProcessorChain.removeProcessor(this);
            }
        }

        protected void createSecurityTokenReferenceStructureForDerivedKey(OutputProcessorChain outputProcessorChain, OutboundSecurityToken securityToken, SecurityTokenConstants.KeyIdentifier keyIdentifier, WSSConstants.DerivedKeyTokenReference derivedKeyTokenReference, boolean useSingleCertificate) throws XMLStreamException, XMLSecurityException {
            SecurityToken wrappingToken = securityToken.getKeyWrappingToken();
            ArrayList<XMLSecAttribute> attributes = new ArrayList<XMLSecAttribute>(2);
            attributes.add(this.createAttribute(WSSConstants.ATT_WSU_ID, IDGenerator.generateID(null)));
            if (WSSecurityTokenConstants.KEYIDENTIFIER_SECURITY_TOKEN_DIRECT_REFERENCE.equals(keyIdentifier) && !useSingleCertificate) {
                attributes.add(this.createAttribute(WSSConstants.ATT_WSSE11_TOKEN_TYPE, "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509PKIPathv1"));
            } else if (derivedKeyTokenReference == WSSConstants.DerivedKeyTokenReference.EncryptedKey || WSSecurityTokenConstants.KEYIDENTIFIER_ENCRYPTED_KEY_SHA1_IDENTIFIER.equals(keyIdentifier)) {
                attributes.add(this.createAttribute(WSSConstants.ATT_WSSE11_TOKEN_TYPE, "http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey"));
            } else if (WSSecurityTokenConstants.KERBEROS_TOKEN.equals(wrappingToken.getTokenType())) {
                attributes.add(this.createAttribute(WSSConstants.ATT_WSSE11_TOKEN_TYPE, "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ"));
            }
            this.createStartElementAndOutputAsEvent(outputProcessorChain, WSSConstants.TAG_WSSE_SECURITY_TOKEN_REFERENCE, false, attributes);
            X509Certificate[] x509Certificates = wrappingToken.getX509Certificates();
            String tokenId = wrappingToken.getId();
            if (derivedKeyTokenReference == WSSConstants.DerivedKeyTokenReference.EncryptedKey) {
                String valueType = "http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey";
                WSSUtils.createBSTReferenceStructure(this, outputProcessorChain, tokenId, valueType, true);
            } else if (WSSecurityTokenConstants.KeyIdentifier_IssuerSerial.equals(keyIdentifier)) {
                WSSUtils.createX509IssuerSerialStructure(this, outputProcessorChain, x509Certificates);
            } else if (WSSecurityTokenConstants.KeyIdentifier_SkiKeyIdentifier.equals(keyIdentifier)) {
                WSSUtils.createX509SubjectKeyIdentifierStructure(this, outputProcessorChain, x509Certificates);
            } else if (WSSecurityTokenConstants.KeyIdentifier_X509KeyIdentifier.equals(keyIdentifier)) {
                WSSUtils.createX509KeyIdentifierStructure(this, outputProcessorChain, x509Certificates);
            } else if (WSSecurityTokenConstants.KEYIDENTIFIER_KERBEROS_SHA1_IDENTIFIER.equals(keyIdentifier)) {
                String identifier = wrappingToken.getSha1Identifier();
                WSSUtils.createKerberosSha1IdentifierStructure(this, outputProcessorChain, identifier);
            } else if (WSSecurityTokenConstants.KEYIDENTIFIER_THUMBPRINT_IDENTIFIER.equals(keyIdentifier)) {
                WSSUtils.createThumbprintKeyIdentifierStructure(this, outputProcessorChain, x509Certificates);
            } else if (WSSecurityTokenConstants.KEYIDENTIFIER_SECURITY_TOKEN_DIRECT_REFERENCE.equals(keyIdentifier)) {
                boolean use200512Namespace;
                String valueType = WSSecurityTokenConstants.KERBEROS_TOKEN.equals(wrappingToken.getTokenType()) ? "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ" : (WSSecurityTokenConstants.SPNEGO_CONTEXT_TOKEN.equals(wrappingToken.getTokenType()) || WSSecurityTokenConstants.SECURITY_CONTEXT_TOKEN.equals(wrappingToken.getTokenType()) || WSSecurityTokenConstants.SECURE_CONVERSATION_TOKEN.equals(wrappingToken.getTokenType()) ? ((use200512Namespace = ((WSSSecurityProperties)this.getSecurityProperties()).isUse200512Namespace()) ? "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512/sct" : "http://schemas.xmlsoap.org/ws/2005/02/sc/sct") : (useSingleCertificate ? "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3" : "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509PKIPathv1"));
                boolean included = ((WSSSecurityProperties)this.getSecurityProperties()).isIncludeSignatureToken();
                WSSUtils.createBSTReferenceStructure(this, outputProcessorChain, tokenId, valueType, included);
            } else if (WSSecurityTokenConstants.KEYIDENTIFIER_ENCRYPTED_KEY_SHA1_IDENTIFIER.equals(keyIdentifier)) {
                WSSUtils.createEncryptedKeySha1IdentifierStructure((AbstractOutputProcessor)this, outputProcessorChain, this.sha1Identifier);
            } else {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "unsupportedSecurityToken");
            }
            this.createEndElementAndOutputAsEvent(outputProcessorChain, WSSConstants.TAG_WSSE_SECURITY_TOKEN_REFERENCE);
        }

        private QName getHeaderElementName() {
            if (this.use200512Namespace) {
                return WSSConstants.TAG_WSC0512_DKT;
            }
            return WSSConstants.TAG_WSC0502_DKT;
        }

        private QName getOffsetName() {
            if (this.use200512Namespace) {
                return WSSConstants.TAG_WSC0512_OFFSET;
            }
            return WSSConstants.TAG_WSC0502_OFFSET;
        }

        private QName getLengthName() {
            if (this.use200512Namespace) {
                return WSSConstants.TAG_WSC0512_LENGTH;
            }
            return WSSConstants.TAG_WSC0502_LENGTH;
        }

        private QName getNonceName() {
            if (this.use200512Namespace) {
                return WSSConstants.TAG_WSC0512_NONCE;
            }
            return WSSConstants.TAG_WSC0502_NONCE;
        }
    }
}

