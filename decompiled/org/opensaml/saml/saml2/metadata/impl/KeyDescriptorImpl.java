/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.metadata.EncryptionMethod;
import org.opensaml.saml.saml2.metadata.KeyDescriptor;
import org.opensaml.security.credential.UsageType;
import org.opensaml.xmlsec.signature.KeyInfo;

public class KeyDescriptorImpl
extends AbstractSAMLObject
implements KeyDescriptor {
    private UsageType keyUseType;
    private KeyInfo keyInfo;
    private final XMLObjectChildrenList<EncryptionMethod> encryptionMethods = new XMLObjectChildrenList(this);

    protected KeyDescriptorImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        this.keyUseType = UsageType.UNSPECIFIED;
    }

    @Override
    public UsageType getUse() {
        return this.keyUseType;
    }

    @Override
    public void setUse(UsageType newType) {
        this.keyUseType = newType != null ? this.prepareForAssignment(this.keyUseType, newType) : this.prepareForAssignment(this.keyUseType, UsageType.UNSPECIFIED);
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
    public List<EncryptionMethod> getEncryptionMethods() {
        return this.encryptionMethods;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.add(this.keyInfo);
        children.addAll(this.encryptionMethods);
        return Collections.unmodifiableList(children);
    }
}

