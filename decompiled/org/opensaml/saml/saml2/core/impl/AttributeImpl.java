/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.Attribute;

public class AttributeImpl
extends AbstractSAMLObject
implements Attribute {
    private String name;
    private String nameFormat;
    private String friendlyName;
    private AttributeMap unknownAttributes = new AttributeMap(this);
    private final XMLObjectChildrenList<XMLObject> attributeValues = new XMLObjectChildrenList(this);

    protected AttributeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String n) {
        this.name = this.prepareForAssignment(this.name, n);
    }

    @Override
    public String getNameFormat() {
        return this.nameFormat;
    }

    @Override
    public void setNameFormat(String format) {
        this.nameFormat = this.prepareForAssignment(this.nameFormat, format);
    }

    @Override
    public String getFriendlyName() {
        return this.friendlyName;
    }

    @Override
    public void setFriendlyName(String fname) {
        this.friendlyName = this.prepareForAssignment(this.friendlyName, fname);
    }

    @Override
    public AttributeMap getUnknownAttributes() {
        return this.unknownAttributes;
    }

    @Override
    public List<XMLObject> getAttributeValues() {
        return this.attributeValues;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.addAll(this.attributeValues);
        return Collections.unmodifiableList(children);
    }
}

