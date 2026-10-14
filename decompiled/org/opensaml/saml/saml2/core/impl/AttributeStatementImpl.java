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
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.core.AttributeStatement;
import org.opensaml.saml.saml2.core.EncryptedAttribute;

public class AttributeStatementImpl
extends AbstractSAMLObject
implements AttributeStatement {
    private final IndexedXMLObjectChildrenList<XMLObject> indexedChildren = new IndexedXMLObjectChildrenList(this);

    protected AttributeStatementImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<Attribute> getAttributes() {
        return this.indexedChildren.subList(Attribute.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public List<EncryptedAttribute> getEncryptedAttributes() {
        return this.indexedChildren.subList(EncryptedAttribute.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.addAll(this.indexedChildren);
        return Collections.unmodifiableList(children);
    }
}

