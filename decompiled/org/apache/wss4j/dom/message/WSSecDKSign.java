/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.message;

import java.security.NoSuchProviderException;
import java.security.Provider;
import java.util.Collections;
import java.util.List;
import javax.crypto.SecretKey;
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
import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;
import org.apache.wss4j.common.WSEncryptionPart;
import org.apache.wss4j.common.derivedKey.ConversationConstants;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.token.SecurityTokenReference;
import org.apache.wss4j.common.util.KeyUtils;
import org.apache.wss4j.common.util.XMLUtils;
import org.apache.wss4j.dom.WSDocInfo;
import org.apache.wss4j.dom.message.WSSecDerivedKeyBase;
import org.apache.wss4j.dom.message.WSSecHeader;
import org.apache.wss4j.dom.util.WSSecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class WSSecDKSign
extends WSSecDerivedKeyBase {
    private static final Logger LOG = LoggerFactory.getLogger(WSSecDKSign.class);
    private String sigAlgo = "http://www.w3.org/2000/09/xmldsig#hmac-sha1";
    private String digestAlgo = "http://www.w3.org/2000/09/xmldsig#sha1";
    private String canonAlgo = "http://www.w3.org/2001/10/xml-exc-c14n#";
    private byte[] signatureValue;
    private String keyInfoUri;
    private SecurityTokenReference secRef;
    private String strUri;
    private WSDocInfo wsDocInfo;
    private XMLSignatureFactory signatureFactory;
    private XMLSignature sig;
    private KeyInfo keyInfo;
    private CanonicalizationMethod c14nMethod;
    private int derivedKeyLength = -1;
    private boolean addInclusivePrefixes = true;

    public WSSecDKSign(WSSecHeader securityHeader) {
        super(securityHeader);
        this.init(null);
    }

    public WSSecDKSign(Document doc) {
        this(doc, null);
    }

    public WSSecDKSign(Document doc, Provider provider) {
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

    public Document build() throws WSSecurityException {
        this.prepare();
        if (this.getParts().isEmpty()) {
            this.getParts().add(WSSecurityUtil.getDefaultEncryptionPart(this.getDocument()));
        } else {
            for (WSEncryptionPart part : this.getParts()) {
                if (!"STRTransform".equals(part.getName()) || part.getId() != null) continue;
                part.setId(this.strUri);
            }
        }
        List<Reference> referenceList = this.addReferencesToSign(this.getParts());
        this.computeSignature(referenceList);
        this.prependDKElementToHeader();
        return this.getDocument();
    }

    @Override
    public void prepare() throws WSSecurityException {
        super.prepare();
        this.wsDocInfo = new WSDocInfo(this.getDocument());
        this.sig = null;
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
        this.secRef = new SecurityTokenReference(this.getDocument());
        this.strUri = this.getIdAllocator().createSecureId("STR-", this.secRef);
        this.secRef.setID(this.strUri);
        if (this.addWSUNamespace) {
            this.secRef.addWSUNamespace();
        }
        org.apache.wss4j.common.token.Reference ref = new org.apache.wss4j.common.token.Reference(this.getDocument());
        ref.setURI("#" + this.getId());
        String ns = ConversationConstants.getWSCNs(this.getWscVersion()) + "/dk";
        ref.setValueType(ns);
        this.secRef.setReference(ref);
        DOMStructure structure = new DOMStructure(this.secRef.getElement());
        this.wsDocInfo.addTokenElement(this.secRef.getElement(), false);
        KeyInfoFactory keyInfoFactory = this.signatureFactory.getKeyInfoFactory();
        this.keyInfo = keyInfoFactory.newKeyInfo(Collections.singletonList(structure), this.keyInfoUri);
    }

    public Element getSignatureElement() {
        Element securityHeaderElement = this.getSecurityHeader().getSecurityHeaderElement();
        return XMLUtils.getDirectChildElement(securityHeaderElement, "Signature", "http://www.w3.org/2000/09/xmldsig#");
    }

    public List<Reference> addReferencesToSign(List<WSEncryptionPart> references) throws WSSecurityException {
        return this.addReferencesToSign(this.getDocument(), references, this.wsDocInfo, this.signatureFactory, this.addInclusivePrefixes, this.digestAlgo);
    }

    public void computeSignature(List<Reference> referenceList) throws WSSecurityException {
        this.computeSignature(referenceList, true, null);
    }

    public void computeSignature(List<Reference> referenceList, boolean prepend, Element siblingElement) throws WSSecurityException {
        try {
            SecretKey key = this.getDerivedKey(this.sigAlgo);
            SignatureMethod signatureMethod = this.signatureFactory.newSignatureMethod(this.sigAlgo, null);
            SignedInfo signedInfo = this.signatureFactory.newSignedInfo(this.c14nMethod, signatureMethod, referenceList);
            this.sig = this.signatureFactory.newXMLSignature(signedInfo, this.keyInfo, null, this.getIdAllocator().createId("SIG-", null), null);
            DOMSignContext signContext = null;
            Element securityHeaderElement = this.getSecurityHeader().getSecurityHeaderElement();
            if (prepend) {
                if (siblingElement == null) {
                    siblingElement = (Element)securityHeaderElement.getFirstChild();
                }
                signContext = siblingElement == null ? new DOMSignContext(key, (Node)securityHeaderElement) : new DOMSignContext(key, (Node)securityHeaderElement, (Node)siblingElement);
            } else {
                signContext = new DOMSignContext(key, (Node)securityHeaderElement);
            }
            signContext.putNamespacePrefix("http://www.w3.org/2000/09/xmldsig#", "ds");
            if ("http://www.w3.org/2001/10/xml-exc-c14n#".equals(this.canonAlgo)) {
                signContext.putNamespacePrefix("http://www.w3.org/2001/10/xml-exc-c14n#", "ec");
            }
            signContext.setProperty("transform_ws_doc_info", this.wsDocInfo);
            this.wsDocInfo.setCallbackLookup(this.callbackLookup);
            this.wsDocInfo.setTokensOnContext(signContext);
            this.sig.sign(signContext);
            this.signatureValue = this.sig.getSignatureValue().getValue();
        }
        catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_SIGNATURE, ex);
        }
    }

    @Override
    protected int getDerivedKeyLength() throws WSSecurityException {
        return this.derivedKeyLength > 0 ? this.derivedKeyLength : KeyUtils.getKeyLength(this.sigAlgo);
    }

    public void setDerivedKeyLength(int keyLength) {
        this.derivedKeyLength = keyLength;
    }

    public void setSignatureAlgorithm(String algorithm) {
        this.sigAlgo = algorithm;
    }

    public String getSignatureAlgorithm() {
        return this.sigAlgo;
    }

    public String getSignatureId() {
        if (this.sig == null) {
            return null;
        }
        return this.sig.getId();
    }

    public void setDigestAlgorithm(String algorithm) {
        this.digestAlgo = algorithm;
    }

    public String getDigestAlgorithm() {
        return this.digestAlgo;
    }

    public byte[] getSignatureValue() {
        return this.signatureValue;
    }

    public void setSigCanonicalization(String algo) {
        this.canonAlgo = algo;
    }

    public String getSigCanonicalization() {
        return this.canonAlgo;
    }

    public boolean isAddInclusivePrefixes() {
        return this.addInclusivePrefixes;
    }

    public void setAddInclusivePrefixes(boolean addInclusivePrefixes) {
        this.addInclusivePrefixes = addInclusivePrefixes;
    }
}

