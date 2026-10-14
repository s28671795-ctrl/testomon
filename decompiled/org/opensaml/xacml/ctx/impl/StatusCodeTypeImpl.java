/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xacml.ctx.StatusCodeType;
import org.opensaml.xacml.impl.AbstractXACMLObject;

public class StatusCodeTypeImpl
extends AbstractXACMLObject
implements StatusCodeType {
    private StatusCodeType statusCode;
    private String value;

    protected StatusCodeTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<StatusCodeType> children = new ArrayList<StatusCodeType>();
        if (this.statusCode != null) {
            children.add(this.statusCode);
        }
        return Collections.unmodifiableList(children);
    }

    @Override
    public StatusCodeType getStatusCode() {
        return this.statusCode;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public void setStatusCode(StatusCodeType code) {
        this.statusCode = this.prepareForAssignment(this.statusCode, code);
    }

    @Override
    public void setValue(String newValue) {
        this.value = this.prepareForAssignment(this.value, newValue);
    }
}

