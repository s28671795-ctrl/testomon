/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.SubjectConfirmationData;

public class SubjectConfirmationDataImpl
extends AbstractSAMLObject
implements SubjectConfirmationData {
    private DateTime notBefore;
    private DateTime notOnOrAfter;
    private String recipient;
    private String inResponseTo;
    private String address;
    private final AttributeMap unknownAttributes = new AttributeMap(this);
    private final IndexedXMLObjectChildrenList<XMLObject> unknownChildren = new IndexedXMLObjectChildrenList(this);

    protected SubjectConfirmationDataImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public DateTime getNotBefore() {
        return this.notBefore;
    }

    @Override
    public void setNotBefore(DateTime newNotBefore) {
        this.notBefore = this.prepareForAssignment(this.notBefore, newNotBefore);
    }

    @Override
    public DateTime getNotOnOrAfter() {
        return this.notOnOrAfter;
    }

    @Override
    public void setNotOnOrAfter(DateTime newNotOnOrAfter) {
        this.notOnOrAfter = this.prepareForAssignment(this.notOnOrAfter, newNotOnOrAfter);
    }

    @Override
    public String getRecipient() {
        return this.recipient;
    }

    @Override
    public void setRecipient(String newRecipient) {
        this.recipient = this.prepareForAssignment(this.recipient, newRecipient);
    }

    @Override
    public String getInResponseTo() {
        return this.inResponseTo;
    }

    @Override
    public void setInResponseTo(String newInResponseTo) {
        this.inResponseTo = this.prepareForAssignment(this.inResponseTo, newInResponseTo);
    }

    @Override
    public String getAddress() {
        return this.address;
    }

    @Override
    public void setAddress(String newAddress) {
        this.address = this.prepareForAssignment(this.address, newAddress);
    }

    @Override
    public AttributeMap getUnknownAttributes() {
        return this.unknownAttributes;
    }

    @Override
    public List<XMLObject> getUnknownXMLObjects() {
        return this.unknownChildren;
    }

    @Override
    public List<XMLObject> getUnknownXMLObjects(QName typeOrName) {
        return this.unknownChildren.subList(typeOrName);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return Collections.unmodifiableList(this.unknownChildren);
    }
}

