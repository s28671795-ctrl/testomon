/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import java.util.ArrayList;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.metadata.Company;
import org.opensaml.saml.saml2.metadata.ContactPerson;
import org.opensaml.saml.saml2.metadata.ContactPersonTypeEnumeration;
import org.opensaml.saml.saml2.metadata.EmailAddress;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.opensaml.saml.saml2.metadata.GivenName;
import org.opensaml.saml.saml2.metadata.SurName;
import org.opensaml.saml.saml2.metadata.TelephoneNumber;

public class ContactPersonImpl
extends AbstractSAMLObject
implements ContactPerson {
    private ContactPersonTypeEnumeration type;
    private Extensions extensions;
    private Company company;
    private GivenName givenName;
    private SurName surName;
    private final AttributeMap unknownAttributes = new AttributeMap(this);
    private final XMLObjectChildrenList<EmailAddress> emailAddresses = new XMLObjectChildrenList(this);
    private final XMLObjectChildrenList<TelephoneNumber> telephoneNumbers = new XMLObjectChildrenList(this);

    protected ContactPersonImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public ContactPersonTypeEnumeration getType() {
        return this.type;
    }

    @Override
    public void setType(ContactPersonTypeEnumeration theType) {
        this.type = this.prepareForAssignment(this.type, theType);
    }

    @Override
    public Extensions getExtensions() {
        return this.extensions;
    }

    @Override
    public void setExtensions(Extensions theExtensions) {
        this.extensions = this.prepareForAssignment(this.extensions, theExtensions);
    }

    @Override
    public Company getCompany() {
        return this.company;
    }

    @Override
    public void setCompany(Company theCompany) {
        this.company = this.prepareForAssignment(this.company, theCompany);
    }

    @Override
    public GivenName getGivenName() {
        return this.givenName;
    }

    @Override
    public void setGivenName(GivenName name) {
        this.givenName = this.prepareForAssignment(this.givenName, name);
    }

    @Override
    public SurName getSurName() {
        return this.surName;
    }

    @Override
    public void setSurName(SurName name) {
        this.surName = this.prepareForAssignment(this.surName, name);
    }

    @Override
    public List<EmailAddress> getEmailAddresses() {
        return this.emailAddresses;
    }

    @Override
    public List<TelephoneNumber> getTelephoneNumbers() {
        return this.telephoneNumbers;
    }

    @Override
    public AttributeMap getUnknownAttributes() {
        return this.unknownAttributes;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.add(this.extensions);
        children.add(this.company);
        children.add(this.givenName);
        children.add(this.surName);
        children.addAll(this.emailAddresses);
        children.addAll(this.telephoneNumbers);
        return children;
    }
}

