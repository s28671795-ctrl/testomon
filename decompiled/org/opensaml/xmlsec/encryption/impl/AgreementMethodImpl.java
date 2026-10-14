/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xmlsec.encryption.AgreementMethod;
import org.opensaml.xmlsec.encryption.KANonce;
import org.opensaml.xmlsec.encryption.OriginatorKeyInfo;
import org.opensaml.xmlsec.encryption.RecipientKeyInfo;

public class AgreementMethodImpl
extends AbstractXMLObject
implements AgreementMethod {
    private String algorithm;
    private KANonce kaNonce;
    private OriginatorKeyInfo originatorKeyInfo;
    private RecipientKeyInfo recipientKeyInfo;
    private IndexedXMLObjectChildrenList xmlChildren = new IndexedXMLObjectChildrenList(this);

    protected AgreementMethodImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getAlgorithm() {
        return this.algorithm;
    }

    @Override
    public void setAlgorithm(String newAlgorithm) {
        this.algorithm = this.prepareForAssignment(this.algorithm, newAlgorithm);
    }

    @Override
    public KANonce getKANonce() {
        return this.kaNonce;
    }

    @Override
    public void setKANonce(KANonce newKANonce) {
        this.kaNonce = this.prepareForAssignment(this.kaNonce, newKANonce);
    }

    @Override
    public OriginatorKeyInfo getOriginatorKeyInfo() {
        return this.originatorKeyInfo;
    }

    @Override
    public void setOriginatorKeyInfo(OriginatorKeyInfo newOriginatorKeyInfo) {
        this.originatorKeyInfo = this.prepareForAssignment(this.originatorKeyInfo, newOriginatorKeyInfo);
    }

    @Override
    public RecipientKeyInfo getRecipientKeyInfo() {
        return this.recipientKeyInfo;
    }

    @Override
    public void setRecipientKeyInfo(RecipientKeyInfo newRecipientKeyInfo) {
        this.recipientKeyInfo = this.prepareForAssignment(this.recipientKeyInfo, newRecipientKeyInfo);
    }

    @Override
    public List<XMLObject> getUnknownXMLObjects() {
        return this.xmlChildren;
    }

    @Override
    public List<XMLObject> getUnknownXMLObjects(QName typeOrName) {
        return this.xmlChildren.subList(typeOrName);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        if (this.kaNonce != null) {
            children.add(this.kaNonce);
        }
        children.addAll(this.xmlChildren);
        if (this.originatorKeyInfo != null) {
            children.add(this.originatorKeyInfo);
        }
        if (this.recipientKeyInfo != null) {
            children.add(this.recipientKeyInfo);
        }
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(children);
    }
}

