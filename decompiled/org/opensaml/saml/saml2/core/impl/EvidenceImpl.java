/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.AssertionIDRef;
import org.opensaml.saml.saml2.core.AssertionURIRef;
import org.opensaml.saml.saml2.core.EncryptedAssertion;
import org.opensaml.saml.saml2.core.Evidence;
import org.opensaml.saml.saml2.core.Evidentiary;

public class EvidenceImpl
extends AbstractSAMLObject
implements Evidence {
    private final IndexedXMLObjectChildrenList<Evidentiary> evidence = new IndexedXMLObjectChildrenList(this);

    protected EvidenceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<Evidentiary> getEvidence() {
        return this.evidence;
    }

    @Override
    public List<AssertionIDRef> getAssertionIDReferences() {
        return this.evidence.subList(AssertionIDRef.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public List<AssertionURIRef> getAssertionURIReferences() {
        return this.evidence.subList(AssertionURIRef.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public List<Assertion> getAssertions() {
        return this.evidence.subList(Assertion.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public List<EncryptedAssertion> getEncryptedAssertions() {
        return this.evidence.subList(EncryptedAssertion.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<Evidentiary> children = new ArrayList<Evidentiary>();
        if (this.evidence.size() == 0) {
            return null;
        }
        children.addAll(this.evidence);
        return Collections.unmodifiableList(children);
    }
}

