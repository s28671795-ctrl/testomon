/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.FunctionType;

public class FunctionTypeImpl
extends AbstractXACMLObject
implements FunctionType {
    private String functionId;

    protected FunctionTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getFunctionId() {
        return this.functionId;
    }

    @Override
    public void setFunctionId(String id) {
        this.functionId = this.prepareForAssignment(this.functionId, id);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

