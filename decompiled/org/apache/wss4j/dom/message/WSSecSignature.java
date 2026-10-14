/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.message;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;
import org.apache.wss4j.common.WSEncryptionPart;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.CryptoType;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.token.BinarySecurity;
import org.apache.wss4j.common.token.DOMX509Data;
import org.apache.wss4j.common.token.DOMX509IssuerSerial;
import org.apache.wss4j.common.token.PKIPathSecurity;
import org.apache.wss4j.common.token.SecurityTokenReference;
import org.apache.wss4j.common.token.X509Security;
import org.apache.wss4j.common.util.KeyUtils;
import org.apache.wss4j.dom.WSDocInfo;
import org.apache.wss4j.dom.message.WSSecHeader;
import org.apache.wss4j.dom.message.WSSecSignatureBase;
import org.apache.wss4j.dom.message.token.KerberosSecurity;
import org.apache.wss4j.dom.util.WSSecurityUtil;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class WSSecSignature
extends WSSecSignatureBase {
    private static final Logger LOG = LoggerFactory.getLogger(WSSecSignature.class);
    protected XMLSignatureFactory signatureFactory;
    protected KeyInfo keyInfo;
    protected CanonicalizationMethod c14nMethod;
    protected XMLSignature sig;
    protected byte[] secretKey;
    protected String strUri;
    protected Element bstToken;
    protected String keyInfoUri;
    protected String certUri;
    protected byte[] signatureValue;
    private boolean useSingleCert = true;
    private String sigAlgo;
    private String canonAlgo = "http://www.w3.org/2001/10/xml-exc-c14n#";
    private SecurityTokenReference secRef;
    private String customTokenValueType;
    private String customTokenId;
    private String encrKeySha1value;
    private Crypto crypto;
    private String digestAlgo = "http://www.w3.org/2000/09/xmldsig#sha1";
    private X509Certificate useThisCert;
    private boolean useCustomSecRef;
    private boolean bstAddedToSecurityHeader;
    private boolean includeSignatureToken;
    private boolean addInclusivePrefixes = true;
    private Element customKeyInfoElement;
    private Provider signatureProvider;

    public WSSecSignature(WSSecHeader securityHeader) {
        super(securityHeader);
        this.init(null);
    }

    public WSSecSignature(Document doc) {
        this(doc, null);
    }

    public WSSecSignature(Document doc, Provider provider) {
        super(doc);
        this.init(provider);
    }

    private void init(Provider provider) {
        if (provider == null) {
            try {
                this.signatureFactory = XMLSignatureFactory.getInstance("DOM", "ApacheXMLDSig");
            }
            catch (NoSuchProviderException ex) {
                this.signatureFactory = XMLSignatureFactory.getInstance("DOM");
            }
        } else {
            this.signatureFactory = XMLSignatureFactory.getInstance("DOM", provider);
        }
    }

    public void prepare(Crypto cr) throws WSSecurityException {
        this.crypto = cr;
        WSDocInfo wsDocInfo = this.getWsDocInfo();
        if (wsDocInfo == null) {
            wsDocInfo = new WSDocInfo(this.getDocument());
            super.setWsDocInfo(wsDocInfo);
        }
        wsDocInfo.setCrypto(cr);
        X509Certificate[] certs = this.getSigningCerts();
        try {
            ExcC14NParameterSpec c14nSpec = null;
            if (this.addInclusivePrefixes && this.canonAlgo.equals("http://www.w3.org/2001/10/xml-exc-c14n#")) {
                Element securityHeaderElement = this.getSecurityHeader().getSecurityHeaderElement();
                List<String> prefixes = this.getInclusivePrefixes(securityHeaderElement, false);
                c14nSpec = new ExcC14NParameterSpec(prefixes);
            }
            this.c14nMethod = this.signatureFactory.newCanonicalizationMethod(this.canonAlgo, c14nSpec);
        }
        catch (Exception ex) {
            LOG.error("", ex);
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_SIGNATURE, ex, "noXMLSig");
        }
        this.keyInfoUri = this.getIdAllocator().createSecureId("KI-", this.keyInfo);
        if (!this.useCustomSecRef && this.customKeyInfoElement == null) {
            this.secRef = new SecurityTokenReference(this.getDocument());
            this.strUri = this.getIdAllocator().createSecureId("STR-", this.secRef);
            this.secRef.addWSSENamespace();
            this.secRef.addWSUNamespace();
            this.secRef.setID(this.strUri);
            switch (this.keyIdentifierType) {
                case 1: {
                    org.apache.wss4j.common.token.Reference ref = new org.apache.wss4j.common.token.Reference(this.getDocument());
                    ref.setURI("#" + this.certUri);
                    this.addBST(certs);
                    if (!this.useSingleCert) {
                        this.secRef.addTokenType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509PKIPathv1");
                        ref.setValueType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509PKIPathv1");
                    } else {
                        ref.setValueType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3");
                    }
                    this.secRef.setReference(ref);
                    break;
                }
                case 2: {
                    String issuer = certs[0].getIssuerX500Principal().getName();
                    BigInteger serialNumber = certs[0].getSerialNumber();
                    DOMX509IssuerSerial domIssuerSerial = new DOMX509IssuerSerial(this.getDocument(), issuer, serialNumber);
                    DOMX509Data domX509Data = new DOMX509Data(this.getDocument(), domIssuerSerial);
                    this.secRef.setUnknownElement(domX509Data.getElement());
                    if (!this.includeSignatureToken) break;
                    this.addBST(certs);
                    break;
                }
                case 3: {
                    this.secRef.setKeyIdentifier(certs[0]);
                    break;
                }
                case 4: {
                    this.secRef.setKeyIdentifierSKI(certs[0], this.crypto);
                    if (!this.includeSignatureToken) break;
                    this.addBST(certs);
                    break;
                }
                case 8: {
                    this.secRef.setKeyIdentifierThumb(certs[0]);
                    if (!this.includeSignatureToken) break;
                    this.addBST(certs);
                    break;
                }
                case 10: {
                    if (this.encrKeySha1value != null) {
                        this.secRef.setKeyIdentifierEncKeySHA1(this.encrKeySha1value);
                    } else {
                        byte[] digestBytes = KeyUtils.generateDigest(this.secretKey);
                        this.secRef.setKeyIdentifierEncKeySHA1(XMLUtils.encodeToString(digestBytes));
                    }
                    this.secRef.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey");
                    break;
                }
                case 9: {
                    org.apache.wss4j.common.token.Reference refCust = new org.apache.wss4j.common.token.Reference(this.getDocument());
                    if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(this.customTokenValueType)) {
                        this.secRef.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1");
                        refCust.setValueType(this.customTokenValueType);
                    } else if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(this.customTokenValueType)) {
                        this.secRef.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
                    } else if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey".equals(this.customTokenValueType)) {
                        this.secRef.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey");
                        refCust.setValueType(this.customTokenValueType);
                    } else if (KerberosSecurity.isKerberosToken(this.customTokenValueType)) {
                        this.secRef.addTokenType(this.customTokenValueType);
                        refCust.setValueType(this.customTokenValueType);
                    } else {
                        refCust.setValueType(this.customTokenValueType);
                    }
                    refCust.setURI("#" + this.customTokenId);
                    this.secRef.setReference(refCust);
                    break;
                }
                case 11: {
                    org.apache.wss4j.common.token.Reference refCustd = new org.apache.wss4j.common.token.Reference(this.getDocument());
                    if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(this.customTokenValueType)) {
                        this.secRef.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1");
                        refCustd.setValueType(this.customTokenValueType);
                    } else if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(this.customTokenValueType)) {
                        this.secRef.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
                    } else if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey".equals(this.customTokenValueType)) {
                        this.secRef.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey");
                        refCustd.setValueType(this.customTokenValueType);
                    } else if (KerberosSecurity.isKerberosToken(this.customTokenValueType)) {
                        this.secRef.addTokenType(this.customTokenValueType);
                        refCustd.setValueType(this.customTokenValueType);
                    } else {
                        refCustd.setValueType(this.customTokenValueType);
                    }
                    refCustd.setURI(this.customTokenId);
                    this.secRef.setReference(refCustd);
                    break;
                }
                case 12: {
                    if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(this.customTokenValueType)) {
                        this.secRef.setKeyIdentifier(this.customTokenValueType, this.customTokenId);
                        this.secRef.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1");
                        break;
                    }
                    if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(this.customTokenValueType)) {
                        this.secRef.setKeyIdentifier(this.customTokenValueType, this.customTokenId);
                        this.secRef.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
                        break;
                    }
                    if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey".equals(this.customTokenValueType)) {
                        this.secRef.setKeyIdentifier(this.customTokenValueType, this.customTokenId, true);
                        this.secRef.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey");
                        break;
                    }
                    if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKeySHA1".equals(this.customTokenValueType)) {
                        this.secRef.setKeyIdentifier(this.customTokenValueType, this.customTokenId, true);
                        this.secRef.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey");
                        break;
                    }
                    if (!"http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#Kerberosv5APREQSHA1".equals(this.customTokenValueType)) break;
                    this.secRef.setKeyIdentifier(this.customTokenValueType, this.customTokenId, true);
                    this.secRef.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ");
                    break;
                }
                case 13: {
                    PublicKey publicKey = certs[0].getPublicKey();
                    try {
                        KeyInfoFactory keyInfoFactory = this.signatureFactory.getKeyInfoFactory();
                        KeyValue keyValue = keyInfoFactory.newKeyValue(publicKey);
                        this.keyInfo = keyInfoFactory.newKeyInfo(Collections.singletonList(keyValue), this.keyInfoUri);
                        break;
                    }
                    catch (KeyException ex) {
                        LOG.error("", ex);
                        throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_SIGNATURE, ex, "noXMLSig");
                    }
                }
                default: {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "unsupportedKeyId");
                }
            }
        }
        if (this.keyIdentifierType != 13) {
            this.marshalKeyInfo(wsDocInfo);
        }
    }

    protected void marshalKeyInfo(WSDocInfo wsDocInfo) throws WSSecurityException {
        List<DOMStructure> kiChildren = null;
        if (this.customKeyInfoElement == null) {
            DOMStructure structure = new DOMStructure(this.secRef.getElement());
            wsDocInfo.addTokenElement(this.secRef.getElement(), false);
            kiChildren = Collections.singletonList(structure);
        } else {
            kiChildren = new ArrayList<DOMStructure>();
            for (Node kiChild = this.customKeyInfoElement.getFirstChild(); kiChild != null; kiChild = kiChild.getNextSibling()) {
                kiChildren.add(new DOMStructure(kiChild));
            }
        }
        KeyInfoFactory keyInfoFactory = this.signatureFactory.getKeyInfoFactory();
        this.keyInfo = keyInfoFactory.newKeyInfo(kiChildren, this.keyInfoUri);
    }

    public Document build(Crypto cr) throws WSSecurityException {
        LOG.debug("Beginning signing...");
        this.prepare(cr);
        if (this.getParts().isEmpty()) {
            this.getParts().add(WSSecurityUtil.getDefaultEncryptionPart(this.getDocument()));
        } else {
            for (WSEncryptionPart part : this.getParts()) {
                if (part.getId() == null && "STRTransform".equals(part.getName())) {
                    part.setId(this.strUri);
                    continue;
                }
                if (!"KeyInfo".equals(part.getName()) || !"http://www.w3.org/2000/09/xmldsig#".equals(part.getNamespace()) || part.getElement() != null) continue;
                Element keyInfoElement = this.getKeyInfoElement();
                part.setElement(keyInfoElement);
            }
        }
        List<Reference> referenceList = this.addReferencesToSign(this.getParts());
        this.computeSignature(referenceList);
        if (this.bstToken != null) {
            this.prependBSTElementToHeader();
        }
        return this.getDocument();
    }

    public List<Reference> addReferencesToSign(List<WSEncryptionPart> references) throws WSSecurityException {
        return this.addReferencesToSign(this.getDocument(), references, this.getWsDocInfo(), this.signatureFactory, this.addInclusivePrefixes, this.digestAlgo);
    }

    public Element getSignatureElement() {
        Element securityHeaderElement = this.getSecurityHeader().getSecurityHeaderElement();
        return org.apache.wss4j.common.util.XMLUtils.getDirectChildElement(securityHeaderElement, "Signature", "http://www.w3.org/2000/09/xmldsig#");
    }

    private void addBST(X509Certificate[] certs) throws WSSecurityException {
        if (this.storeBytesInAttachment) {
            this.bstToken = this.getDocument().createElementNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsse:BinarySecurityToken");
            this.bstToken.setAttributeNS(null, "EncodingType", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary");
            this.bstToken.setAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsu:Id", this.certUri);
            if (this.addWSUNamespace) {
                this.bstToken.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
            }
            byte[] certBytes = null;
            if (!this.useSingleCert) {
                this.bstToken.setAttributeNS(null, "ValueType", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509PKIPathv1");
                certBytes = this.crypto.getBytesFromCertificates(certs);
            } else {
                this.bstToken.setAttributeNS(null, "ValueType", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3");
                try {
                    certBytes = certs[0].getEncoded();
                }
                catch (CertificateEncodingException e) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.SECURITY_TOKEN_UNAVAILABLE, e, "encodeError");
                }
            }
            String attachmentId = this.getIdAllocator().createId("", this.getDocument());
            WSSecurityUtil.storeBytesInAttachment(this.bstToken, this.getDocument(), attachmentId, certBytes, this.attachmentCallbackHandler);
            this.getWsDocInfo().addTokenElement(this.bstToken, false);
        } else {
            BinarySecurity binarySecurity = null;
            if (!this.useSingleCert) {
                binarySecurity = new PKIPathSecurity(this.getDocument());
                ((PKIPathSecurity)binarySecurity).setX509Certificates(certs, this.crypto);
            } else {
                binarySecurity = new X509Security(this.getDocument());
                ((X509Security)binarySecurity).setX509Certificate(certs[0]);
            }
            binarySecurity.setID(this.certUri);
            if (this.addWSUNamespace) {
                binarySecurity.addWSUNamespace();
            }
            this.bstToken = binarySecurity.getElement();
            this.getWsDocInfo().addTokenElement(this.bstToken, false);
        }
        this.bstAddedToSecurityHeader = false;
    }

    public void prependBSTElementToHeader() {
        if (this.bstToken != null && !this.bstAddedToSecurityHeader) {
            Element securityHeaderElement = this.getSecurityHeader().getSecurityHeaderElement();
            WSSecurityUtil.prependChildElement(securityHeaderElement, this.bstToken);
            this.bstAddedToSecurityHeader = true;
        }
    }

    public void appendBSTElementToHeader() {
        if (this.bstToken != null && !this.bstAddedToSecurityHeader) {
            Element securityHeaderElement = this.getSecurityHeader().getSecurityHeaderElement();
            securityHeaderElement.appendChild(this.bstToken);
            this.bstAddedToSecurityHeader = true;
        }
    }

    public void computeSignature(List<Reference> referenceList) throws WSSecurityException {
        this.computeSignature(referenceList, true, null);
    }

    public void computeSignature(List<Reference> referenceList, boolean prepend, Element siblingElement) throws WSSecurityException {
        try {
            Key key = this.secretKey == null ? this.crypto.getPrivateKey(this.user, this.password) : KeyUtils.prepareSecretKey(this.sigAlgo, this.secretKey);
            SignatureMethod signatureMethod = this.signatureFactory.newSignatureMethod(this.sigAlgo, null);
            SignedInfo signedInfo = this.signatureFactory.newSignedInfo(this.c14nMethod, signatureMethod, referenceList);
            this.sig = this.signatureFactory.newXMLSignature(signedInfo, this.keyInfo, null, this.getIdAllocator().createId("SIG-", null), null);
            DOMSignContext signContext = null;
            Element securityHeaderElement = this.getSecurityHeader().getSecurityHeaderElement();
            if (prepend) {
                if (siblingElement == null) {
                    Node child;
                    for (child = securityHeaderElement.getFirstChild(); child != null && child.getNodeType() != 1; child = child.getNextSibling()) {
                    }
                    siblingElement = (Element)child;
                }
                signContext = siblingElement == null ? new DOMSignContext(key, (Node)securityHeaderElement) : new DOMSignContext(key, (Node)securityHeaderElement, (Node)siblingElement);
            } else {
                signContext = new DOMSignContext(key, (Node)securityHeaderElement);
            }
            if (this.signatureProvider != null) {
                signContext.setProperty("org.jcp.xml.dsig.internal.dom.SignatureProvider", this.signatureProvider);
            }
            signContext.putNamespacePrefix("http://www.w3.org/2000/09/xmldsig#", "ds");
            if ("http://www.w3.org/2001/10/xml-exc-c14n#".equals(this.canonAlgo)) {
                signContext.putNamespacePrefix("http://www.w3.org/2001/10/xml-exc-c14n#", "ec");
            }
            signContext.setProperty("transform_ws_doc_info", this.getWsDocInfo());
            this.getWsDocInfo().setCallbackLookup(this.callbackLookup);
            this.getWsDocInfo().setTokensOnContext(signContext);
            this.sig.sign(signContext);
            this.signatureValue = this.sig.getSignatureValue().getValue();
            this.cleanup();
        }
        catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_SIGNATURE, ex);
        }
    }

    public void setUseSingleCertificate(boolean useSingleCert) {
        this.useSingleCert = useSingleCert;
    }

    public boolean isUseSingleCertificate() {
        return this.useSingleCert;
    }

    public void setSignatureAlgorithm(String algo) {
        this.sigAlgo = algo;
    }

    public String getSignatureAlgorithm() {
        return this.sigAlgo;
    }

    public void setSigCanonicalization(String algo) {
        this.canonAlgo = algo;
    }

    public String getSigCanonicalization() {
        return this.canonAlgo;
    }

    public String getDigestAlgo() {
        return this.digestAlgo;
    }

    public void setDigestAlgo(String digestAlgo) {
        this.digestAlgo = digestAlgo;
    }

    public byte[] getSignatureValue() {
        return this.signatureValue;
    }

    public Element getKeyInfoElement() throws WSSecurityException {
        Element parent = this.getDocument().createElement("temp");
        DOMCryptoContext cryptoContext = new DOMCryptoContext(){};
        cryptoContext.putNamespacePrefix("http://www.w3.org/2000/09/xmldsig#", "ds");
        try {
            this.keyInfo.marshal(new DOMStructure(parent), cryptoContext);
        }
        catch (MarshalException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_SIGNATURE, (Exception)ex);
        }
        return (Element)parent.getFirstChild();
    }

    public String getId() {
        if (this.sig == null) {
            return null;
        }
        return this.sig.getId();
    }

    public String getBSTTokenId() {
        if (this.bstToken == null) {
            return null;
        }
        return this.bstToken.getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
    }

    public void setSecretKey(byte[] secretKey) {
        this.secretKey = secretKey;
    }

    public void setCustomTokenValueType(String customTokenValueType) {
        this.customTokenValueType = customTokenValueType;
    }

    public void setCustomTokenId(String customTokenId) {
        this.customTokenId = customTokenId;
    }

    public String getCustomTokenId() {
        return this.customTokenId;
    }

    public void setEncrKeySha1value(String encrKeySha1value) {
        this.encrKeySha1value = encrKeySha1value;
    }

    public void setX509Certificate(X509Certificate cer) {
        this.useThisCert = cer;
    }

    public Element getBinarySecurityTokenElement() {
        return this.bstToken;
    }

    public String getSecurityTokenReferenceURI() {
        return this.strUri;
    }

    public SecurityTokenReference getSecurityTokenReference() {
        return this.secRef;
    }

    public void setSecurityTokenReference(SecurityTokenReference secRef) {
        this.useCustomSecRef = true;
        this.secRef = secRef;
    }

    private X509Certificate[] getSigningCerts() throws WSSecurityException {
        X509Certificate[] certs = null;
        if (this.keyIdentifierType != 9 && this.keyIdentifierType != 11 && this.keyIdentifierType != 10 && this.keyIdentifierType != 12) {
            if (this.useThisCert == null) {
                CryptoType cryptoType = new CryptoType(CryptoType.TYPE.ALIAS);
                cryptoType.setAlias(this.user);
                if (this.crypto == null) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noSigCryptoFile");
                }
                certs = this.crypto.getX509Certificates(cryptoType);
            } else {
                certs = new X509Certificate[]{this.useThisCert};
            }
            if (certs == null || certs.length <= 0) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noUserCertsFound", new Object[]{this.user, "signature"});
            }
            this.certUri = this.getIdAllocator().createSecureId("X509-", certs[0]);
            if (this.sigAlgo == null) {
                String pubKeyAlgo = certs[0].getPublicKey().getAlgorithm();
                LOG.debug("Automatic signature algorithm detection: {}", (Object)pubKeyAlgo);
                if (pubKeyAlgo.equalsIgnoreCase("DSA")) {
                    this.sigAlgo = "http://www.w3.org/2000/09/xmldsig#dsa-sha1";
                } else if (pubKeyAlgo.equalsIgnoreCase("RSA")) {
                    this.sigAlgo = "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
                } else if (pubKeyAlgo.equalsIgnoreCase("EC")) {
                    this.sigAlgo = "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha256";
                } else {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "unknownSignatureAlgorithm", new Object[]{pubKeyAlgo});
                }
            }
        }
        return certs;
    }

    public boolean isIncludeSignatureToken() {
        return this.includeSignatureToken;
    }

    public void setIncludeSignatureToken(boolean includeSignatureToken) {
        this.includeSignatureToken = includeSignatureToken;
    }

    public boolean isAddInclusivePrefixes() {
        return this.addInclusivePrefixes;
    }

    public void setAddInclusivePrefixes(boolean addInclusivePrefixes) {
        this.addInclusivePrefixes = addInclusivePrefixes;
    }

    public void setCustomKeyInfoElement(Element keyInfoElement) {
        this.customKeyInfoElement = keyInfoElement;
    }

    public Element getCustomKeyInfoElement() {
        return this.customKeyInfoElement;
    }

    public Provider getSignatureProvider() {
        return this.signatureProvider;
    }

    public void setSignatureProvider(Provider signatureProvider) {
        this.signatureProvider = signatureProvider;
    }
}

