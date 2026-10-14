/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.apache.xml.security.signature.XMLSignature;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.security.credential.Credential;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.support.ContentReference;

public class SignatureImpl
extends AbstractXMLObject
implements Signature {
    private String canonicalizationAlgorithm;
    private String signatureAlgorithm;
    private Integer hmacOutputLength;
    private Credential signingCredential;
    private KeyInfo keyInfo;
    private List<ContentReference> contentReferences = new LinkedList<ContentReference>();
    private XMLSignature xmlSignature;

    protected SignatureImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getCanonicalizationAlgorithm() {
        return this.canonicalizationAlgorithm;
    }

    @Override
    public void setCanonicalizationAlgorithm(String newAlgorithm) {
        this.canonicalizationAlgorithm = this.prepareForAssignment(this.canonicalizationAlgorithm, newAlgorithm);
    }

    @Override
    public String getSignatureAlgorithm() {
        return this.signatureAlgorithm;
    }

    @Override
    public void setSignatureAlgorithm(String newAlgorithm) {
        this.signatureAlgorithm = this.prepareForAssignment(this.signatureAlgorithm, newAlgorithm);
    }

    @Override
    public Integer getHMACOutputLength() {
        return this.hmacOutputLength;
    }

    @Override
    public void setHMACOutputLength(Integer length) {
        this.hmacOutputLength = this.prepareForAssignment(this.hmacOutputLength, length);
    }

    @Override
    public Credential getSigningCredential() {
        return this.signingCredential;
    }

    @Override
    public void setSigningCredential(Credential newCredential) {
        this.signingCredential = this.prepareForAssignment(this.signingCredential, newCredential);
    }

    @Override
    public KeyInfo getKeyInfo() {
        return this.keyInfo;
    }

    @Override
    public void setKeyInfo(KeyInfo newKeyInfo) {
        this.keyInfo = this.prepareForAssignment(this.keyInfo, newKeyInfo);
    }

    @Override
    public List<ContentReference> getContentReferences() {
        return this.contentReferences;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public void releaseDOM() {
        super.releaseDOM();
        this.xmlSignature = null;
        if (this.keyInfo != null) {
            this.keyInfo.releaseChildrenDOM(true);
            this.keyInfo.releaseDOM();
        }
    }

    public XMLSignature getXMLSignature() {
        return this.xmlSignature;
    }

    public void setXMLSignature(XMLSignature signature) {
        this.xmlSignature = this.prepareForAssignment(this.xmlSignature, signature);
    }
}

