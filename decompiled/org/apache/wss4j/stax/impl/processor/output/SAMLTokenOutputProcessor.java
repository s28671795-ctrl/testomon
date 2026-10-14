/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.processor.output;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import org.apache.wss4j.common.crypto.CryptoType;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.saml.OpenSAMLUtil;
import org.apache.wss4j.common.saml.SAMLCallback;
import org.apache.wss4j.common.saml.SAMLKeyInfo;
import org.apache.wss4j.common.saml.SAMLUtil;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.common.saml.bean.KeyInfoBean;
import org.apache.wss4j.common.saml.bean.SubjectBean;
import org.apache.wss4j.stax.ext.WSSConfigurationException;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.impl.processor.output.OutputProcessorUtils;
import org.apache.wss4j.stax.impl.processor.output.UsernameTokenOutputProcessor;
import org.apache.wss4j.stax.impl.processor.output.WSSSignatureOutputProcessor;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.config.JCEAlgorithmMapper;
import org.apache.xml.security.stax.ext.AbstractOutputProcessor;
import org.apache.xml.security.stax.ext.OutputProcessorChain;
import org.apache.xml.security.stax.ext.SecurePart;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.stax.XMLSecAttribute;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.impl.securityToken.GenericOutboundSecurityToken;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.apache.xml.security.stax.securityEvent.TokenSecurityEvent;
import org.apache.xml.security.stax.securityToken.OutboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;
import org.apache.xml.security.stax.securityToken.SecurityTokenProvider;
import org.apache.xml.security.utils.XMLUtils;
import org.opensaml.saml.common.SAMLVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class SAMLTokenOutputProcessor
extends AbstractOutputProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(SAMLTokenOutputProcessor.class);

    public SAMLTokenOutputProcessor() throws XMLSecurityException {
        this.addBeforeProcessor(WSSSignatureOutputProcessor.class.getName());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void processEvent(XMLSecEvent xmlSecEvent, OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
        try {
            FinalSAMLTokenOutputProcessor finalSAMLTokenOutputProcessor;
            SAMLCallback samlCallback = new SAMLCallback();
            SAMLUtil.doSAMLCallback(((WSSSecurityProperties)this.getSecurityProperties()).getSamlCallbackHandler(), samlCallback);
            SamlAssertionWrapper samlAssertionWrapper = new SamlAssertionWrapper(samlCallback);
            if (samlCallback.isSignAssertion()) {
                samlAssertionWrapper.signAssertion(samlCallback.getIssuerKeyName(), samlCallback.getIssuerKeyPassword(), samlCallback.getIssuerCrypto(), samlCallback.isSendKeyValue(), samlCallback.getCanonicalizationAlgorithm(), samlCallback.getSignatureAlgorithm(), samlCallback.getSignatureDigestAlgorithm());
            }
            boolean senderVouches = false;
            boolean hok = false;
            List<String> methods = samlAssertionWrapper.getConfirmationMethods();
            if (methods != null && !methods.isEmpty()) {
                String confirmMethod = methods.get(0);
                if (OpenSAMLUtil.isMethodSenderVouches(confirmMethod)) {
                    senderVouches = true;
                } else if (OpenSAMLUtil.isMethodHolderOfKey(confirmMethod)) {
                    hok = true;
                }
            }
            String securityTokenReferenceId = IDGenerator.generateID(null);
            String tokenId = samlAssertionWrapper.getId();
            XMLSecurityConstants.Action action = this.getAction();
            boolean includeSTR = false;
            GenericOutboundSecurityToken securityToken = null;
            String sigTokenId = (String)outputProcessorChain.getSecurityContext().get("PROP_USE_THIS_TOKEN_ID_FOR_SIGNATURE");
            SecurityTokenProvider<OutboundSecurityToken> signatureTokenProvider = null;
            if (sigTokenId != null && (signatureTokenProvider = outputProcessorChain.getSecurityContext().getSecurityTokenProvider(sigTokenId)) != null) {
                securityToken = (GenericOutboundSecurityToken)signatureTokenProvider.getSecurityToken();
            }
            if (WSSConstants.SAML_TOKEN_SIGNED.equals(action) && senderVouches) {
                includeSTR = true;
                if (securityToken == null) {
                    securityToken = this.getSecurityToken(samlCallback, outputProcessorChain);
                }
                finalSAMLTokenOutputProcessor = new FinalSAMLTokenOutputProcessor(securityToken, samlAssertionWrapper, securityTokenReferenceId, senderVouches, includeSTR);
                securityToken.setProcessor(finalSAMLTokenOutputProcessor);
            } else if (WSSConstants.SAML_TOKEN_SIGNED.equals(action) && hok) {
                SAMLKeyInfo samlKeyInfo = this.getSamlKeyInfo(samlCallback);
                Element ref = securityToken != null ? securityToken.getCustomTokenReference() : null;
                finalSAMLTokenOutputProcessor = new FinalSAMLTokenOutputProcessor(null, samlAssertionWrapper, securityTokenReferenceId, senderVouches, includeSTR);
                final SAMLSecurityTokenProvider securityTokenProvider = new SAMLSecurityTokenProvider(samlKeyInfo, samlCallback, tokenId, ref, finalSAMLTokenOutputProcessor);
                TokenSecurityEvent<OutboundSecurityToken> tokenSecurityEvent = new TokenSecurityEvent<OutboundSecurityToken>(WSSecurityEventConstants.SAML_TOKEN){

                    @Override
                    public OutboundSecurityToken getSecurityToken() {
                        try {
                            return securityTokenProvider.getSecurityToken();
                        }
                        catch (XMLSecurityException e) {
                            LOG.debug(e.getMessage(), e);
                            return null;
                        }
                    }
                };
                outputProcessorChain.getSecurityContext().registerSecurityEvent(tokenSecurityEvent);
                outputProcessorChain.getSecurityContext().registerSecurityTokenProvider(tokenId, securityTokenProvider);
                outputProcessorChain.getSecurityContext().put("PROP_USE_THIS_TOKEN_ID_FOR_SIGNATURE", tokenId);
            } else if (WSSConstants.SAML_TOKEN_UNSIGNED.equals(this.getAction())) {
                QName assertionName = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Assertion");
                if (samlAssertionWrapper.getSamlVersion() == SAMLVersion.VERSION_11) {
                    assertionName = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Assertion");
                }
                Iterator<SecurePart> signaturePartsIterator = this.securityProperties.getSignatureSecureParts().iterator();
                while (signaturePartsIterator.hasNext()) {
                    SecurePart securePart = signaturePartsIterator.next();
                    if (!samlAssertionWrapper.getId().equals(securePart.getIdToSign()) && !assertionName.equals(securePart.getName())) continue;
                    includeSTR = true;
                    signaturePartsIterator.remove();
                    break;
                }
                finalSAMLTokenOutputProcessor = new FinalSAMLTokenOutputProcessor(null, samlAssertionWrapper, securityTokenReferenceId, senderVouches, includeSTR);
                if (includeSTR) {
                    finalSAMLTokenOutputProcessor.addBeforeProcessor(WSSSignatureOutputProcessor.class.getName());
                }
            } else {
                finalSAMLTokenOutputProcessor = new FinalSAMLTokenOutputProcessor(null, samlAssertionWrapper, securityTokenReferenceId, senderVouches, includeSTR);
            }
            finalSAMLTokenOutputProcessor.setXMLSecurityProperties(this.getSecurityProperties());
            finalSAMLTokenOutputProcessor.setAction(action);
            finalSAMLTokenOutputProcessor.init(outputProcessorChain);
            if (includeSTR) {
                SecurePart securePart = new SecurePart(new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#STR-Transform"), tokenId, securityTokenReferenceId, SecurePart.Modifier.Element);
                outputProcessorChain.getSecurityContext().putAsMap("signatureParts", tokenId, securePart);
            }
        }
        finally {
            outputProcessorChain.removeProcessor(this);
        }
        outputProcessorChain.processEvent(xmlSecEvent);
    }

    private GenericOutboundSecurityToken getSecurityToken(SAMLCallback samlCallback, OutputProcessorChain outputProcessorChain) throws WSSecurityException {
        PrivateKey privateKey;
        CryptoType cryptoType = new CryptoType(CryptoType.TYPE.ALIAS);
        cryptoType.setAlias(samlCallback.getIssuerKeyName());
        X509Certificate[] certificates = null;
        if (samlCallback.getIssuerCrypto() != null) {
            certificates = samlCallback.getIssuerCrypto().getX509Certificates(cryptoType);
        }
        if (certificates == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"No issuer certs were found to sign the SAML Assertion using issuer name: " + samlCallback.getIssuerKeyName()});
        }
        try {
            privateKey = samlCallback.getIssuerCrypto().getPrivateKey(samlCallback.getIssuerKeyName(), samlCallback.getIssuerKeyPassword());
        }
        catch (Exception ex) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex);
        }
        final String binarySecurityTokenId = IDGenerator.generateID(null);
        final GenericOutboundSecurityToken bstSecurityToken = new GenericOutboundSecurityToken(binarySecurityTokenId, WSSecurityTokenConstants.X509V3Token, privateKey, certificates);
        SecurityTokenProvider<OutboundSecurityToken> securityTokenProvider = new SecurityTokenProvider<OutboundSecurityToken>(){

            @Override
            public OutboundSecurityToken getSecurityToken() throws WSSecurityException {
                return bstSecurityToken;
            }

            @Override
            public String getId() {
                return binarySecurityTokenId;
            }
        };
        outputProcessorChain.getSecurityContext().registerSecurityTokenProvider(binarySecurityTokenId, securityTokenProvider);
        outputProcessorChain.getSecurityContext().put("PROP_USE_THIS_TOKEN_ID_FOR_SIGNATURE", binarySecurityTokenId);
        return bstSecurityToken;
    }

    private SAMLKeyInfo getSamlKeyInfo(SAMLCallback samlCallback) throws WSSConfigurationException, WSSecurityException {
        KeyInfoBean keyInfoBean;
        SAMLKeyInfo samlKeyInfo = new SAMLKeyInfo();
        SubjectBean subjectBean = samlCallback.getSubject();
        if (subjectBean != null && (keyInfoBean = subjectBean.getKeyInfo()) != null) {
            X509Certificate x509Certificate = keyInfoBean.getCertificate();
            if (x509Certificate != null) {
                String alias = ((WSSSecurityProperties)this.getSecurityProperties()).getSignatureCrypto().getX509Identifier(x509Certificate);
                if (alias == null) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "aliasIsNull");
                }
                WSPasswordCallback wsPasswordCallback = new WSPasswordCallback(alias, 3);
                WSSUtils.doPasswordCallback(((WSSSecurityProperties)this.getSecurityProperties()).getCallbackHandler(), wsPasswordCallback);
                CryptoType cryptoType = new CryptoType(CryptoType.TYPE.ALIAS);
                cryptoType.setAlias(alias);
                samlKeyInfo.setCerts(((WSSSecurityProperties)this.getSecurityProperties()).getSignatureCrypto().getX509Certificates(cryptoType));
                samlKeyInfo.setPrivateKey(((WSSSecurityProperties)this.getSecurityProperties()).getSignatureCrypto().getPrivateKey(alias, wsPasswordCallback.getPassword()));
            } else if (keyInfoBean.getPublicKey() != null) {
                PublicKey publicKey = keyInfoBean.getPublicKey();
                samlKeyInfo.setPublicKey(publicKey);
                samlKeyInfo.setPrivateKey(((WSSSecurityProperties)this.getSecurityProperties()).getSignatureCrypto().getPrivateKey(samlCallback.getIssuerKeyName(), samlCallback.getIssuerKeyPassword()));
            } else {
                samlKeyInfo.setSecret(keyInfoBean.getEphemeralKey());
            }
        }
        return samlKeyInfo;
    }

    private void outputSecurityTokenReference(OutputProcessorChain outputProcessorChain, SamlAssertionWrapper samlAssertionWrapper, String referenceId, String tokenId) throws XMLStreamException, XMLSecurityException {
        ArrayList<XMLSecAttribute> attributes = new ArrayList<XMLSecAttribute>(2);
        SecurityTokenConstants.TokenType tokenType = WSSecurityTokenConstants.SAML_11_TOKEN;
        if (samlAssertionWrapper.getSamlVersion() == SAMLVersion.VERSION_11) {
            attributes.add(this.createAttribute(WSSConstants.ATT_WSSE11_TOKEN_TYPE, "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1"));
        } else {
            tokenType = WSSecurityTokenConstants.SAML_20_TOKEN;
            attributes.add(this.createAttribute(WSSConstants.ATT_WSSE11_TOKEN_TYPE, "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0"));
        }
        attributes.add(this.createAttribute(WSSConstants.ATT_WSU_ID, referenceId));
        this.createStartElementAndOutputAsEvent(outputProcessorChain, WSSConstants.TAG_WSSE_SECURITY_TOKEN_REFERENCE, false, attributes);
        WSSUtils.createSAMLKeyIdentifierStructure(this, outputProcessorChain, tokenType, tokenId);
        this.createEndElementAndOutputAsEvent(outputProcessorChain, WSSConstants.TAG_WSSE_SECURITY_TOKEN_REFERENCE);
    }

    class FinalSAMLTokenOutputProcessor
    extends AbstractOutputProcessor {
        private final OutboundSecurityToken securityToken;
        private final SamlAssertionWrapper samlAssertionWrapper;
        private final String securityTokenReferenceId;
        private boolean senderVouches = false;
        private boolean includeSTR = false;

        FinalSAMLTokenOutputProcessor(OutboundSecurityToken securityToken, SamlAssertionWrapper samlAssertionWrapper, String securityTokenReferenceId, boolean senderVouches, boolean includeSTR) throws XMLSecurityException {
            this.addAfterProcessor(UsernameTokenOutputProcessor.class.getName());
            this.addAfterProcessor(SAMLTokenOutputProcessor.class.getName());
            this.samlAssertionWrapper = samlAssertionWrapper;
            this.securityTokenReferenceId = securityTokenReferenceId;
            this.senderVouches = senderVouches;
            this.securityToken = securityToken;
            this.includeSTR = includeSTR;
        }

        @Override
        public void processEvent(XMLSecEvent xmlSecEvent, OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
            outputProcessorChain.processEvent(xmlSecEvent);
            if (WSSUtils.isSecurityHeaderElement(xmlSecEvent, ((WSSSecurityProperties)this.getSecurityProperties()).getActor())) {
                OutputProcessorChain subOutputProcessorChain = outputProcessorChain.createSubChain(this);
                if (this.includeBST()) {
                    OutputProcessorUtils.updateSecurityHeaderOrder(outputProcessorChain, WSSConstants.TAG_WSSE_BINARY_SECURITY_TOKEN, this.getAction(), false);
                    WSSUtils.createBinarySecurityTokenStructure(this, outputProcessorChain, this.securityToken.getId(), this.securityToken.getX509Certificates(), this.getSecurityProperties().isUseSingleCert());
                }
                QName headerElementName = this.samlAssertionWrapper.getSamlVersion() == SAMLVersion.VERSION_11 ? WSSConstants.TAG_SAML_ASSERTION : WSSConstants.TAG_SAML2_ASSERTION;
                OutputProcessorUtils.updateSecurityHeaderOrder(outputProcessorChain, headerElementName, this.getAction(), false);
                try {
                    this.outputDOMElement(this.samlAssertionWrapper.toDOM(XMLUtils.newDocument()), subOutputProcessorChain);
                }
                catch (ParserConfigurationException ex) {
                    LOG.debug("Error writing out SAML Assertion", ex);
                    throw new XMLSecurityException(ex);
                }
                if (this.includeSTR) {
                    OutputProcessorUtils.updateSecurityHeaderOrder(outputProcessorChain, WSSConstants.TAG_WSSE_SECURITY_TOKEN_REFERENCE, this.getAction(), false);
                    SAMLTokenOutputProcessor.this.outputSecurityTokenReference(subOutputProcessorChain, this.samlAssertionWrapper, this.securityTokenReferenceId, this.samlAssertionWrapper.getId());
                }
                outputProcessorChain.removeProcessor(this);
            }
        }

        private boolean includeBST() {
            return this.senderVouches && this.getSecurityProperties().getSignatureKeyIdentifiers().contains(WSSecurityTokenConstants.KEYIDENTIFIER_SECURITY_TOKEN_DIRECT_REFERENCE) && this.securityToken != null && (!WSSConstants.SAML_TOKEN_SIGNED.equals(this.action) || !((WSSSecurityProperties)this.getSecurityProperties()).isIncludeSignatureToken());
        }
    }

    private static class SAMLSecurityTokenProvider
    implements SecurityTokenProvider<OutboundSecurityToken> {
        private GenericOutboundSecurityToken samlSecurityToken;
        private SAMLKeyInfo samlKeyInfo;
        private SAMLCallback samlCallback;
        private String tokenId;
        private Element ref;
        private FinalSAMLTokenOutputProcessor finalSAMLTokenOutputProcessor;

        SAMLSecurityTokenProvider(SAMLKeyInfo samlKeyInfo, SAMLCallback samlCallback, String tokenId, Element ref, FinalSAMLTokenOutputProcessor finalSAMLTokenOutputProcessor) {
            this.samlKeyInfo = samlKeyInfo;
            this.samlCallback = samlCallback;
            this.tokenId = tokenId;
            this.ref = ref;
            this.finalSAMLTokenOutputProcessor = finalSAMLTokenOutputProcessor;
        }

        @Override
        public OutboundSecurityToken getSecurityToken() throws XMLSecurityException {
            if (this.samlSecurityToken != null) {
                return this.samlSecurityToken;
            }
            SecurityTokenConstants.TokenType tokenType = this.samlCallback.getSamlVersion() == SAMLVersion.VERSION_10 ? WSSecurityTokenConstants.SAML_10_TOKEN : (this.samlCallback.getSamlVersion() == SAMLVersion.VERSION_11 ? WSSecurityTokenConstants.SAML_11_TOKEN : WSSecurityTokenConstants.SAML_20_TOKEN);
            this.samlSecurityToken = this.samlKeyInfo.getPrivateKey() != null ? new GenericOutboundSecurityToken(this.tokenId, tokenType, this.samlKeyInfo.getPrivateKey(), this.samlKeyInfo.getCerts()) : new GenericOutboundSecurityToken(this.tokenId, tokenType){

                @Override
                public Key getSecretKey(String algorithmURI) throws WSSecurityException {
                    Key key;
                    try {
                        key = super.getSecretKey(algorithmURI);
                    }
                    catch (XMLSecurityException e) {
                        throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, (Exception)e);
                    }
                    if (key != null) {
                        return key;
                    }
                    if (samlKeyInfo.getSecret() != null) {
                        String algoFamily = JCEAlgorithmMapper.getJCEKeyAlgorithmFromURI(algorithmURI);
                        key = new SecretKeySpec(samlKeyInfo.getSecret(), algoFamily);
                        this.setSecretKey(algorithmURI, key);
                    }
                    return key;
                }
            };
            this.samlSecurityToken.setProcessor(this.finalSAMLTokenOutputProcessor);
            this.samlSecurityToken.setCustomTokenReference(this.ref);
            return this.samlSecurityToken;
        }

        @Override
        public String getId() {
            return this.tokenId;
        }
    }
}

