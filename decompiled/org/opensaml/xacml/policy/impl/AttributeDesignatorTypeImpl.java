/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import java.util.List;
import net.shibboleth.utilities.java.support.collection.LazyList;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.AttributeDesignatorType;

public class AttributeDesignatorTypeImpl
extends AbstractXACMLObject
implements AttributeDesignatorType {
    private String attributeId;
    private String dataType;
    private String issuer;
    private XSBooleanValue mustBePresentXS = XSBooleanValue.valueOf("false");

    protected AttributeDesignatorTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getAttributeId() {
        return this.attributeId;
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
    public XSBooleanValue getMustBePresentXSBoolean() {
        return this.mustBePresentXS;
    }

    @Override
    public Boolean getMustBePresent() {
        if (this.mustBePresentXS != null) {
            return this.mustBePresentXS.getValue();
        }
        return Boolean.FALSE;
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
    public void setIssuer(String newIssuer) {
        this.issuer = this.prepareForAssignment(this.issuer, newIssuer);
    }

    @Override
    public void setMustBePresentXSBoolean(XSBooleanValue present) {
        this.mustBePresentXS = this.prepareForAssignment(this.mustBePresentXS, present);
    }

    @Override
    public void setMustBePresent(Boolean present) {
        this.mustBePresentXS = present != null ? this.prepareForAssignment(this.mustBePresentXS, new XSBooleanValue(present, false)) : (XSBooleanValue)this.prepareForAssignment(this.mustBePresentXS, null);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return new LazyList<XMLObject>();
    }
}

