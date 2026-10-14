/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml1.core.AttributeDesignator;

public class AttributeDesignatorImpl
extends AbstractSAMLObject
implements AttributeDesignator {
    private String attributeName;
    private String attributeNamespace;

    protected AttributeDesignatorImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getAttributeName() {
        return this.attributeName;
    }

    @Override
    public void setAttributeName(String name) {
        this.attributeName = this.prepareForAssignment(this.attributeName, name);
    }

    @Override
    public String getAttributeNamespace() {
        return this.attributeNamespace;
    }

    @Override
    public void setAttributeNamespace(String ns) {
        this.attributeNamespace = this.prepareForAssignment(this.attributeNamespace, ns);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

