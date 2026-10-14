/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.AssertionURIRef;

public class AssertionURIRefImpl
extends AbstractSAMLObject
implements AssertionURIRef {
    private String assertionURI;

    protected AssertionURIRefImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getAssertionURI() {
        return this.assertionURI;
    }

    @Override
    public void setAssertionURI(String newAssertionURI) {
        this.assertionURI = this.prepareForAssignment(this.assertionURI, newAssertionURI);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

