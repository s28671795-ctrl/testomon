/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import java.util.List;
import net.shibboleth.utilities.java.support.collection.LazyList;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.AttributeSelectorType;

public class AttributeSelectorTypeImpl
extends AbstractXACMLObject
implements AttributeSelectorType {
    private String dataType;
    private String requestContextPath;
    private XSBooleanValue mustBePresentXS = XSBooleanValue.valueOf("false");

    protected AttributeSelectorTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getDataType() {
        return this.dataType;
    }

    @Override
    public Boolean getMustBePresent() {
        if (this.mustBePresentXS != null) {
            return this.mustBePresentXS.getValue();
        }
        return Boolean.FALSE;
    }

    @Override
    public XSBooleanValue getMustBePresentXSBoolean() {
        return this.mustBePresentXS;
    }

    @Override
    public String getRequestContextPath() {
        return this.requestContextPath;
    }

    @Override
    public void setDataType(String type) {
        this.dataType = this.prepareForAssignment(this.dataType, type);
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
    public void setRequestContextPath(String path) {
        this.requestContextPath = this.prepareForAssignment(this.requestContextPath, path);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return new LazyList<XMLObject>();
    }
}

