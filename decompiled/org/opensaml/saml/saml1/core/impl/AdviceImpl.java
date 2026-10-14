/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml1.core.Advice;
import org.opensaml.saml.saml1.core.Assertion;
import org.opensaml.saml.saml1.core.AssertionIDReference;

public class AdviceImpl
extends AbstractSAMLObject
implements Advice {
    private final IndexedXMLObjectChildrenList<XMLObject> assertionChildren = new IndexedXMLObjectChildrenList(this);
    private final IndexedXMLObjectChildrenList<XMLObject> unknownChildren = new IndexedXMLObjectChildrenList(this);

    protected AdviceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<AssertionIDReference> getAssertionIDReferences() {
        QName assertionIDRefQName = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AssertionIDReference");
        return this.assertionChildren.subList(assertionIDRefQName);
    }

    @Override
    public List<Assertion> getAssertions() {
        QName assertionQname = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Assertion");
        return this.assertionChildren.subList(assertionQname);
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
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.addAll(this.assertionChildren);
        children.addAll(this.unknownChildren);
        return Collections.unmodifiableList(children);
    }
}

