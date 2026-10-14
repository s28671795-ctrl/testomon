/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdrpi.impl;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.ext.saml2mdrpi.RegistrationInfo;
import org.opensaml.saml.ext.saml2mdrpi.RegistrationPolicy;

public class RegistrationInfoImpl
extends AbstractSAMLObject
implements RegistrationInfo {
    private XMLObjectChildrenList<RegistrationPolicy> registrationPolicies = new IndexedXMLObjectChildrenList<RegistrationPolicy>(this);
    private String registrationAuthority;
    private DateTime registrationInstant;

    protected RegistrationInfoImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getRegistrationAuthority() {
        return this.registrationAuthority;
    }

    @Override
    public void setRegistrationAuthority(String authority) {
        this.registrationAuthority = this.prepareForAssignment(this.registrationAuthority, authority);
    }

    @Override
    public DateTime getRegistrationInstant() {
        return this.registrationInstant;
    }

    @Override
    public void setRegistrationInstant(DateTime dateTime) {
        this.registrationInstant = this.prepareForAssignment(this.registrationInstant, dateTime);
    }

    @Override
    public List<RegistrationPolicy> getRegistrationPolicies() {
        return this.registrationPolicies;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.addAll(this.registrationPolicies);
        return children;
    }
}

