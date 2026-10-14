/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.saml;

import java.math.BigInteger;
import java.security.Key;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;
import org.apache.wss4j.common.SignatureActionToken;
import org.apache.wss4j.common.WSEncryptionPart;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.CryptoType;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.saml.OpenSAMLUtil;
import org.apache.wss4j.common.saml.SAMLKeyInfo;
import org.apache.wss4j.common.saml.SAMLKeyInfoProcessor;
import org.apache.wss4j.common.saml.SAMLUtil;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.common.token.DOMX509Data;
import org.apache.wss4j.common.token.DOMX509IssuerSerial;
import org.apache.wss4j.common.token.SecurityTokenReference;
import org.apache.wss4j.common.token.X509Security;
import org.apache.wss4j.common.util.KeyUtils;
import org.apache.wss4j.dom.WSDocInfo;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.message.WSSecHeader;
import org.apache.wss4j.dom.message.WSSecSignature;
import org.apache.wss4j.dom.saml.WSSSAMLKeyInfoProcessor;
import org.apache.wss4j.dom.util.WSSecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class WSSecSignatureSAML
extends WSSecSignature {
    private static final Logger LOG = LoggerFactory.getLogger(WSSecSignatureSAML.class);
    private boolean senderVouches;
    private SecurityTokenReference secRefSaml;
    private String secRefID;
    private Element samlToken;
    private Crypto userCrypto;
    private Crypto issuerCrypto;
    private String issuerKeyName;
    private String issuerKeyPW;
    private boolean useDirectReferenceToAssertion;

    public WSSecSignatureSAML(WSSecHeader securityHeader) {
        super(securityHeader);
    }

    public WSSecSignatureSAML(Document doc) {
        super(doc);
    }

    public Document build(Crypto uCrypto, SamlAssertionWrapper samlAssertion, Crypto iCrypto, String iKeyName, String iKeyPW) throws WSSecurityException {
        this.prepare(uCrypto, samlAssertion, iCrypto, iKeyName, iKeyPW);
        if (this.getParts().isEmpty()) {
            this.getParts().add(WSSecurityUtil.getDefaultEncryptionPart(this.getDocument()));
        } else {
            for (WSEncryptionPart part : this.getParts()) {
                if (!"STRTransform".equals(part.getName()) || part.getId() != null) continue;
                part.setId(this.strUri);
            }
        }
        if (this.secRefID != null) {
            String soapNamespace = WSSecurityUtil.getSOAPNamespace(this.getDocument().getDocumentElement());
            WSEncryptionPart encP = new WSEncryptionPart("STRTransform", soapNamespace, "Content");
            encP.setId(this.secRefID);
            this.getParts().add(encP);
        }
        List<Reference> referenceList = this.addReferencesToSign(this.getParts());
        this.prependSAMLElementsToHeader();
        if (this.senderVouches) {
            this.computeSignature(referenceList, this.secRefSaml.getElement());
        } else {
            this.computeSignature(referenceList, this.samlToken);
        }
        if (this.bstToken != null) {
            this.prependBSTElementToHeader();
        }
        return this.getDocument();
    }

    public void prepare(Crypto uCrypto, SamlAssertionWrapper samlAssertion, Crypto iCrypto, String iKeyName, String iKeyPW) throws WSSecurityException {
        LOG.debug("Beginning ST signing...");
        this.userCrypto = uCrypto;
        this.issuerCrypto = iCrypto;
        this.issuerKeyName = iKeyName;
        this.issuerKeyPW = iKeyPW;
        this.samlToken = samlAssertion.toDOM(this.getDocument());
        String confirmMethod = null;
        List<String> methods = samlAssertion.getConfirmationMethods();
        if (methods != null && !methods.isEmpty()) {
            confirmMethod = methods.get(0);
        }
        if (OpenSAMLUtil.isMethodSenderVouches(confirmMethod)) {
            this.senderVouches = true;
        }
        if (super.getWsDocInfo() == null) {
            WSDocInfo wsDocInfo = new WSDocInfo(this.getDocument());
            super.setWsDocInfo(wsDocInfo);
        }
        X509Certificate[] certs = null;
        Key publicKey = null;
        if (this.senderVouches) {
            CryptoType cryptoType = new CryptoType(CryptoType.TYPE.ALIAS);
            cryptoType.setAlias(this.issuerKeyName);
            certs = this.issuerCrypto.getX509Certificates(cryptoType);
            this.getWsDocInfo().setCrypto(this.issuerCrypto);
        } else {
            if (this.userCrypto == null || !samlAssertion.isSigned()) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "invalidSAMLsecurity", new Object[]{"for SAML Signature (Key Holder)"});
            }
            if (this.secretKey == null) {
                RequestData data = new RequestData();
                data.setWsDocInfo(this.getWsDocInfo());
                SignatureActionToken actionToken = new SignatureActionToken();
                data.setSignatureToken(actionToken);
                actionToken.setCrypto(this.userCrypto);
                SAMLKeyInfo samlKeyInfo = SAMLUtil.getCredentialFromSubject(samlAssertion, (SAMLKeyInfoProcessor)new WSSSAMLKeyInfoProcessor(data), this.userCrypto, data.getCallbackHandler());
                if (samlKeyInfo != null) {
                    publicKey = samlKeyInfo.getPublicKey();
                    certs = samlKeyInfo.getCerts();
                    this.getWsDocInfo().setCrypto(this.userCrypto);
                }
            }
        }
        if ((certs == null || certs.length == 0 || certs[0] == null) && publicKey == null && this.secretKey == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noCertsFound", new Object[]{"SAML signature"});
        }
        if (this.getSignatureAlgorithm() == null) {
            Key key = null;
            if (certs != null && certs[0] != null) {
                key = certs[0].getPublicKey();
            } else if (publicKey != null) {
                key = publicKey;
            } else {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "unknownSignatureAlgorithm");
            }
            String pubKeyAlgo = key.getAlgorithm();
            LOG.debug("automatic sig algo detection: {}", (Object)pubKeyAlgo);
            if (pubKeyAlgo.equalsIgnoreCase("DSA")) {
                this.setSignatureAlgorithm("http://www.w3.org/2000/09/xmldsig#dsa-sha1");
            } else if (pubKeyAlgo.equalsIgnoreCase("RSA")) {
                this.setSignatureAlgorithm("http://www.w3.org/2000/09/xmldsig#rsa-sha1");
            } else {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "unknownSignatureAlgorithm", new Object[]{pubKeyAlgo});
            }
        }
        this.sig = null;
        try {
            ExcC14NParameterSpec c14nSpec = null;
            if (this.isAddInclusivePrefixes() && this.getSigCanonicalization().equals("http://www.w3.org/2001/10/xml-exc-c14n#")) {
                Element securityHeaderElement = this.getSecurityHeader().getSecurityHeaderElement();
                List<String> prefixes = this.getInclusivePrefixes(securityHeaderElement, false);
                c14nSpec = new ExcC14NParameterSpec(prefixes);
            }
            this.c14nMethod = this.signatureFactory.newCanonicalizationMethod(this.getSigCanonicalization(), c14nSpec);
        }
        catch (Exception ex) {
            LOG.error("", ex);
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_SIGNATURE, ex, "noXMLSig");
        }
        this.keyInfoUri = this.getIdAllocator().createSecureId("KeyId-", this.keyInfo);
        SecurityTokenReference secRef = new SecurityTokenReference(this.getDocument());
        this.strUri = this.getIdAllocator().createSecureId("STRId-", secRef);
        secRef.setID(this.strUri);
        this.setSecurityTokenReference(secRef);
        if (certs != null && certs.length != 0) {
            this.certUri = this.getIdAllocator().createSecureId("CertId-", certs[0]);
        }
        try {
            if (this.senderVouches) {
                this.secRefSaml = new SecurityTokenReference(this.getDocument());
                this.secRefID = this.getIdAllocator().createSecureId("STRSAMLId-", this.secRefSaml);
                this.secRefSaml.setID(this.secRefID);
                if (this.useDirectReferenceToAssertion) {
                    org.apache.wss4j.common.token.Reference ref = new org.apache.wss4j.common.token.Reference(this.getDocument());
                    ref.setURI("#" + samlAssertion.getId());
                    if (samlAssertion.getSaml1() != null) {
                        ref.setValueType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID");
                        this.secRefSaml.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1");
                    } else if (samlAssertion.getSaml2() != null) {
                        this.secRefSaml.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
                    }
                    this.secRefSaml.setReference(ref);
                } else {
                    Element keyId = this.getDocument().createElementNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsse:KeyIdentifier");
                    String valueType = null;
                    if (samlAssertion.getSaml1() != null) {
                        valueType = "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID";
                        this.secRefSaml.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1");
                    } else if (samlAssertion.getSaml2() != null) {
                        valueType = "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID";
                        this.secRefSaml.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
                    }
                    keyId.setAttributeNS(null, "ValueType", valueType);
                    keyId.appendChild(this.getDocument().createTextNode(samlAssertion.getId()));
                    Element elem = this.secRefSaml.getElement();
                    elem.appendChild(keyId);
                }
                this.getWsDocInfo().addTokenElement(this.secRefSaml.getElement(), false);
            }
        }
        catch (Exception ex) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_SIGNATURE, ex, "noXMLSig");
        }
        X509Certificate cert = certs != null ? certs[0] : null;
        this.configureKeyInfo(secRef, cert, iCrypto != null ? iCrypto : uCrypto, samlAssertion);
        this.getWsDocInfo().addTokenElement(this.samlToken, false);
    }

    private void configureKeyInfo(SecurityTokenReference secRef, X509Certificate cert, Crypto crypto, SamlAssertionWrapper samlAssertion) throws WSSecurityException {
        block15: {
            block16: {
                if (this.getCustomKeyInfoElement() != null) break block15;
                if (!this.senderVouches) break block16;
                switch (this.keyIdentifierType) {
                    case 1: {
                        org.apache.wss4j.common.token.Reference ref = new org.apache.wss4j.common.token.Reference(this.getDocument());
                        ref.setURI("#" + this.certUri);
                        X509Security binarySecurity = new X509Security(this.getDocument());
                        binarySecurity.setX509Certificate(cert);
                        binarySecurity.setID(this.certUri);
                        this.bstToken = binarySecurity.getElement();
                        this.getWsDocInfo().addTokenElement(this.bstToken, false);
                        ref.setValueType(binarySecurity.getValueType());
                        secRef.setReference(ref);
                        break block15;
                    }
                    case 3: {
                        secRef.setKeyIdentifier(cert);
                        break block15;
                    }
                    case 4: {
                        secRef.setKeyIdentifierSKI(cert, crypto);
                        break block15;
                    }
                    case 8: {
                        secRef.setKeyIdentifierThumb(cert);
                        break block15;
                    }
                    case 2: {
                        String issuer = cert.getIssuerDN().getName();
                        BigInteger serialNumber = cert.getSerialNumber();
                        DOMX509IssuerSerial domIssuerSerial = new DOMX509IssuerSerial(this.getDocument(), issuer, serialNumber);
                        DOMX509Data domX509Data = new DOMX509Data(this.getDocument(), domIssuerSerial);
                        secRef.setUnknownElement(domX509Data.getElement());
                        break block15;
                    }
                    default: {
                        throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "unsupportedKeyId");
                    }
                }
            }
            if (this.useDirectReferenceToAssertion) {
                org.apache.wss4j.common.token.Reference ref = new org.apache.wss4j.common.token.Reference(this.getDocument());
                ref.setURI("#" + samlAssertion.getId());
                if (samlAssertion.getSaml1() != null) {
                    ref.setValueType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID");
                    secRef.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1");
                } else if (samlAssertion.getSaml2() != null) {
                    secRef.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
                }
                secRef.setReference(ref);
            } else {
                Element keyId = this.getDocument().createElementNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsse:KeyIdentifier");
                String valueType = null;
                if (samlAssertion.getSaml1() != null) {
                    valueType = "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID";
                    secRef.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1");
                } else if (samlAssertion.getSaml2() != null) {
                    valueType = "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID";
                    secRef.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
                }
                keyId.setAttributeNS(null, "ValueType", valueType);
                keyId.appendChild(this.getDocument().createTextNode(samlAssertion.getId()));
                Element elem = secRef.getElement();
                elem.appendChild(keyId);
            }
        }
        this.marshalKeyInfo(this.getWsDocInfo());
    }

    public void prependSAMLElementsToHeader() {
        Element securityHeaderElement = this.getSecurityHeader().getSecurityHeaderElement();
        if (this.senderVouches) {
            WSSecurityUtil.prependChildElement(securityHeaderElement, this.secRefSaml.getElement());
        }
        WSSecurityUtil.prependChildElement(securityHeaderElement, this.samlToken);
    }

    public void computeSignature(List<Reference> referenceList, Element siblingElement) throws WSSecurityException {
        try {
            Key key = this.senderVouches ? this.issuerCrypto.getPrivateKey(this.issuerKeyName, this.issuerKeyPW) : (this.secretKey != null ? KeyUtils.prepareSecretKey(this.getSignatureAlgorithm(), this.secretKey) : this.userCrypto.getPrivateKey(this.user, this.password));
            SignatureMethod signatureMethod = this.signatureFactory.newSignatureMethod(this.getSignatureAlgorithm(), null);
            SignedInfo signedInfo = this.signatureFactory.newSignedInfo(this.c14nMethod, signatureMethod, referenceList);
            this.sig = this.signatureFactory.newXMLSignature(signedInfo, this.keyInfo, null, this.getIdAllocator().createId("SIG-", null), null);
            Element securityHeaderElement = this.getSecurityHeader().getSecurityHeaderElement();
            DOMSignContext signContext = null;
            signContext = siblingElement != null && siblingElement.getNextSibling() != null ? new DOMSignContext(key, (Node)securityHeaderElement, siblingElement.getNextSibling()) : new DOMSignContext(key, (Node)securityHeaderElement);
            if (this.getSignatureProvider() != null) {
                signContext.setProperty("org.jcp.xml.dsig.internal.dom.SignatureProvider", this.getSignatureProvider());
            }
            signContext.putNamespacePrefix("http://www.w3.org/2000/09/xmldsig#", "ds");
            if ("http://www.w3.org/2001/10/xml-exc-c14n#".equals(this.getSigCanonicalization())) {
                signContext.putNamespacePrefix("http://www.w3.org/2001/10/xml-exc-c14n#", "ec");
            }
            signContext.setProperty("transform_ws_doc_info", this.getWsDocInfo());
            this.getWsDocInfo().setCallbackLookup(this.callbackLookup);
            this.getWsDocInfo().setTokensOnContext(signContext);
            this.sig.sign(signContext);
            this.signatureValue = this.sig.getSignatureValue().getValue();
        }
        catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_SIGNATURE, ex);
        }
    }

    public boolean isUseDirectReferenceToAssertion() {
        return this.useDirectReferenceToAssertion;
    }

    public void setUseDirectReferenceToAssertion(boolean useDirectReferenceToAssertion) {
        this.useDirectReferenceToAssertion = useDirectReferenceToAssertion;
    }
}

