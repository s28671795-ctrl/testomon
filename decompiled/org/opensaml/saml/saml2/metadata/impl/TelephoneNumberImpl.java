/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.metadata.TelephoneNumber;

public class TelephoneNumberImpl
extends AbstractSAMLObject
implements TelephoneNumber {
    private String number;

    protected TelephoneNumberImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getNumber() {
        return this.number;
    }

    @Override
    public void setNumber(String newNumber) {
        this.number = this.prepareForAssignment(this.number, newNumber);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

