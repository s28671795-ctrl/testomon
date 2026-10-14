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
import org.opensaml.saml.saml1.core.Assertion;
import org.opensaml.saml.saml1.core.AssertionIDReference;
import org.opensaml.saml.saml1.core.Evidence;
import org.opensaml.saml.saml1.core.Evidentiary;

public class EvidenceImpl
extends AbstractSAMLObject
implements Evidence {
    private final IndexedXMLObjectChildrenList<Evidentiary> evidence = new IndexedXMLObjectChildrenList(this);

    protected EvidenceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<AssertionIDReference> getAssertionIDReferences() {
        QName qname = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AssertionIDReference");
        return this.evidence.subList(qname);
    }

    @Override
    public List<Assertion> getAssertions() {
        QName qname = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Assertion");
        return this.evidence.subList(qname);
    }

    @Override
    public List<Evidentiary> getEvidence() {
        return this.evidence;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        if (this.evidence.size() == 0) {
            return null;
        }
        ArrayList<Evidentiary> list = new ArrayList<Evidentiary>();
        list.addAll(this.evidence);
        return Collections.unmodifiableList(list);
    }
}

