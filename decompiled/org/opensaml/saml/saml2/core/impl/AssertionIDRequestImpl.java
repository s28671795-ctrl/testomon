/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.saml2.core.AssertionIDRef;
import org.opensaml.saml.saml2.core.AssertionIDRequest;
import org.opensaml.saml.saml2.core.impl.RequestAbstractTypeImpl;

public class AssertionIDRequestImpl
extends RequestAbstractTypeImpl
implements AssertionIDRequest {
    private final XMLObjectChildrenList<AssertionIDRef> assertionIDRefs = new XMLObjectChildrenList(this);

    protected AssertionIDRequestImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<AssertionIDRef> getAssertionIDRefs() {
        return this.assertionIDRefs;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        if (super.getOrderedChildren() != null) {
            children.addAll(super.getOrderedChildren());
        }
        children.addAll(this.assertionIDRefs);
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(children);
    }
}

