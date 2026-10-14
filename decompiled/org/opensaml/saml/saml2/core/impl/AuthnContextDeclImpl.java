/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.AuthnContextDecl;

public class AuthnContextDeclImpl
extends AbstractSAMLObject
implements AuthnContextDecl {
    private IndexedXMLObjectChildrenList<XMLObject> unknownXMLObjects = new IndexedXMLObjectChildrenList(this);
    private AttributeMap unknownAttributes = new AttributeMap(this);
    private String textContent;

    protected AuthnContextDeclImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getTextContent() {
        return this.textContent;
    }

    @Override
    public void setTextContent(String newContent) {
        this.textContent = this.prepareForAssignment(this.textContent, newContent);
    }

    @Override
    public List<XMLObject> getUnknownXMLObjects() {
        return this.unknownXMLObjects;
    }

    @Override
    public List<XMLObject> getUnknownXMLObjects(QName typeOrName) {
        return this.unknownXMLObjects.subList(typeOrName);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return Collections.unmodifiableList(this.unknownXMLObjects);
    }

    @Override
    public AttributeMap getUnknownAttributes() {
        return this.unknownAttributes;
    }
}

