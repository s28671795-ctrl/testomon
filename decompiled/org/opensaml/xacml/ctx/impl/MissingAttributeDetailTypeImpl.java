/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.xacml.ctx.AttributeValueType;
import org.opensaml.xacml.ctx.MissingAttributeDetailType;
import org.opensaml.xacml.impl.AbstractXACMLObject;

public class MissingAttributeDetailTypeImpl
extends AbstractXACMLObject
implements MissingAttributeDetailType {
    private XMLObjectChildrenList<AttributeValueType> attributeValues = new XMLObjectChildrenList(this);
    private String attributeId;
    private String dataType;
    private String issuer;

    protected MissingAttributeDetailTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getAttributeId() {
        return this.attributeId;
    }

    @Override
    public List<AttributeValueType> getAttributeValues() {
        return this.attributeValues;
    }

    @Override
    public String getDataType() {
        return this.dataType;
    }

    @Override
    public String getIssuer() {
        return this.issuer;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<AttributeValueType> children = new ArrayList<AttributeValueType>();
        children.addAll(this.attributeValues);
        return Collections.unmodifiableList(children);
    }

    @Override
    public void setAttributeId(String id) {
        this.attributeId = this.prepareForAssignment(this.attributeId, id);
    }

    @Override
    public void setDataType(String type) {
        this.dataType = this.prepareForAssignment(this.dataType, type);
    }

    @Override
    public void setIssuer(String iss) {
        this.issuer = this.prepareForAssignment(this.issuer, iss);
    }
}

