/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.saml1.core.AttributeDesignator;
import org.opensaml.saml.saml1.core.AttributeQuery;
import org.opensaml.saml.saml1.core.impl.SubjectQueryImpl;

public class AttributeQueryImpl
extends SubjectQueryImpl
implements AttributeQuery {
    private String resource;
    private final XMLObjectChildrenList<AttributeDesignator> attributeDesignators = new XMLObjectChildrenList(this);

    protected AttributeQueryImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getResource() {
        return this.resource;
    }

    @Override
    public void setResource(String res) {
        this.resource = this.prepareForAssignment(this.resource, res);
    }

    @Override
    public List<AttributeDesignator> getAttributeDesignators() {
        return this.attributeDesignators;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> list = new ArrayList<XMLObject>(this.attributeDesignators.size() + 1);
        if (super.getOrderedChildren() != null) {
            list.addAll(super.getOrderedChildren());
        }
        list.addAll(this.attributeDesignators);
        return Collections.unmodifiableList(list);
    }
}

