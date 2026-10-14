/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.AssertionIDRef;

public class AssertionIDRefImpl
extends AbstractSAMLObject
implements AssertionIDRef {
    private String assertionID;

    protected AssertionIDRefImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getAssertionID() {
        return this.assertionID;
    }

    @Override
    public void setAssertionID(String newIDRef) {
        this.assertionID = this.prepareForAssignment(this.assertionID, newIDRef);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

