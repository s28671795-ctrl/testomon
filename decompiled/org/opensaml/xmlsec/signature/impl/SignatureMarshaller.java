/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.apache.xml.security.Init;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.signature.XMLSignature;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.Marshaller;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xmlsec.algorithm.AlgorithmSupport;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.impl.SignatureImpl;
import org.opensaml.xmlsec.signature.support.ContentReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SignatureMarshaller
implements Marshaller {
    private final Logger log = LoggerFactory.getLogger(SignatureMarshaller.class);

    public SignatureMarshaller() {
        if (!Init.isInitialized()) {
            this.log.debug("Initializing XML security library");
            Init.init();
        }
    }

    @Override
    public Element marshall(XMLObject xmlObject) throws MarshallingException {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            return this.marshall(xmlObject, document);
        }
        catch (ParserConfigurationException e) {
            throw new MarshallingException("Unable to create Document to place marshalled elements in", e);
        }
    }

    @Override
    public Element marshall(XMLObject xmlObject, Element parentElement) throws MarshallingException {
        Element signatureElement = this.createSignatureElement((SignatureImpl)xmlObject, parentElement.getOwnerDocument());
        ElementSupport.appendChildElement(parentElement, signatureElement);
        return signatureElement;
    }

    @Override
    public Element marshall(XMLObject xmlObject, Document document) throws MarshallingException {
        Element signatureElement = this.createSignatureElement((SignatureImpl)xmlObject, document);
        Element documentRoot = document.getDocumentElement();
        if (documentRoot != null) {
            document.replaceChild(signatureElement, documentRoot);
        } else {
            document.appendChild(signatureElement);
        }
        return signatureElement;
    }

    private Element createSignatureElement(Signature signature, Document document) throws MarshallingException {
        this.log.debug("Starting to marshall {}", (Object)signature.getElementQName());
        try {
            this.log.debug("Creating XMLSignature object");
            XMLSignature dsig = null;
            dsig = signature.getHMACOutputLength() != null && AlgorithmSupport.isHMAC(signature.getSignatureAlgorithm()) ? new XMLSignature(document, "", signature.getSignatureAlgorithm(), signature.getHMACOutputLength(), signature.getCanonicalizationAlgorithm()) : new XMLSignature(document, "", signature.getSignatureAlgorithm(), signature.getCanonicalizationAlgorithm());
            this.log.debug("Adding content to XMLSignature.");
            for (ContentReference contentReference : signature.getContentReferences()) {
                contentReference.createReference(dsig);
            }
            this.log.debug("Creating Signature DOM element");
            Element signatureElement = dsig.getElement();
            if (signature.getKeyInfo() != null) {
                Marshaller keyInfoMarshaller = XMLObjectProviderRegistrySupport.getMarshallerFactory().getMarshaller(KeyInfo.DEFAULT_ELEMENT_NAME);
                keyInfoMarshaller.marshall((XMLObject)signature.getKeyInfo(), signatureElement);
            }
            ((SignatureImpl)signature).setXMLSignature(dsig);
            signature.setDOM(signatureElement);
            signature.releaseParentDOM(true);
            return signatureElement;
        }
        catch (XMLSecurityException e) {
            String msg = "Unable to construct signature Element " + signature.getElementQName();
            this.log.error(msg, e);
            throw new MarshallingException(msg, e);
        }
    }
}

