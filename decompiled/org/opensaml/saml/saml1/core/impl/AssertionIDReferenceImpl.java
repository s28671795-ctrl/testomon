/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml1.core.AssertionIDReference;

public class AssertionIDReferenceImpl
extends AbstractSAMLObject
implements AssertionIDReference {
    private String ncName;

    protected AssertionIDReferenceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getReference() {
        return this.ncName;
    }

    @Override
    public void setReference(String name) {
        this.ncName = this.prepareForAssignment(this.ncName, name);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

