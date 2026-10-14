/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml1.core.ConfirmationMethod;
import org.opensaml.saml.saml1.core.SubjectConfirmation;
import org.opensaml.xmlsec.signature.KeyInfo;

public class SubjectConfirmationImpl
extends AbstractSAMLObject
implements SubjectConfirmation {
    private final XMLObjectChildrenList<ConfirmationMethod> confirmationMethods = new XMLObjectChildrenList(this);
    private XMLObject subjectConfirmationData;
    private KeyInfo keyInfo;

    protected SubjectConfirmationImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<ConfirmationMethod> getConfirmationMethods() {
        return this.confirmationMethods;
    }

    @Override
    public void setSubjectConfirmationData(XMLObject data) {
        this.subjectConfirmationData = this.prepareForAssignment(this.subjectConfirmationData, data);
    }

    @Override
    public XMLObject getSubjectConfirmationData() {
        return this.subjectConfirmationData;
    }

    @Override
    public KeyInfo getKeyInfo() {
        return this.keyInfo;
    }

    @Override
    public void setKeyInfo(KeyInfo info) {
        this.keyInfo = this.prepareForAssignment(this.keyInfo, info);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> list = new ArrayList<XMLObject>(this.confirmationMethods.size() + 1);
        list.addAll(this.confirmationMethods);
        if (this.subjectConfirmationData != null) {
            list.add(this.subjectConfirmationData);
        }
        if (this.keyInfo != null) {
            list.add(this.keyInfo);
        }
        if (list.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(list);
    }
}

