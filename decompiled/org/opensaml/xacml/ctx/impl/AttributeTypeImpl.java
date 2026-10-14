/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.xacml.ctx.AttributeType;
import org.opensaml.xacml.ctx.AttributeValueType;
import org.opensaml.xacml.impl.AbstractXACMLObject;

public class AttributeTypeImpl
extends AbstractXACMLObject
implements AttributeType {
    private String issuer;
    private String attributeID;
    private String datatype;
    private final XMLObjectChildrenList<AttributeValueType> attributeValues = new XMLObjectChildrenList(this);

    protected AttributeTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getAttributeId() {
        return this.attributeID;
    }

    @Override
    public String getDataType() {
        return this.datatype;
    }

    @Override
    public String getIssuer() {
        return this.issuer;
    }

    @Override
    public void setAttributeID(String attributeId) {
        this.attributeID = this.prepareForAssignment(this.attributeID, attributeId);
    }

    @Override
    public void setDataType(String type) {
        this.datatype = this.prepareForAssignment(this.datatype, type);
    }

    @Override
    public void setIssuer(String iss) {
        this.issuer = this.prepareForAssignment(this.issuer, iss);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<AttributeValueType> children = new ArrayList<AttributeValueType>();
        children.addAll(this.attributeValues);
        return Collections.unmodifiableList(children);
    }

    @Override
    public List<AttributeValueType> getAttributeValues() {
        return this.attributeValues;
    }
}

