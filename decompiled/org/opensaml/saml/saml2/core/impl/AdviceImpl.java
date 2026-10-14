/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.Advice;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.AssertionIDRef;
import org.opensaml.saml.saml2.core.AssertionURIRef;
import org.opensaml.saml.saml2.core.EncryptedAssertion;

public class AdviceImpl
extends AbstractSAMLObject
implements Advice {
    private final IndexedXMLObjectChildrenList<XMLObject> indexedChildren = new IndexedXMLObjectChildrenList(this);

    protected AdviceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<XMLObject> getChildren() {
        return this.indexedChildren;
    }

    @Override
    public List<XMLObject> getChildren(QName typeOrName) {
        return this.indexedChildren.subList(typeOrName);
    }

    @Override
    public List<AssertionIDRef> getAssertionIDReferences() {
        return this.indexedChildren.subList(AssertionIDRef.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public List<AssertionURIRef> getAssertionURIReferences() {
        return this.indexedChildren.subList(AssertionURIRef.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public List<Assertion> getAssertions() {
        return this.indexedChildren.subList(Assertion.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public List<EncryptedAssertion> getEncryptedAssertions() {
        return this.indexedChildren.subList(EncryptedAssertion.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.addAll(this.indexedChildren);
        return Collections.unmodifiableList(children);
    }
}

