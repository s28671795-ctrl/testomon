/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.DescriptionType;

public class DescriptionTypeImpl
extends AbstractXACMLObject
implements DescriptionType {
    private String description;

    protected DescriptionTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getValue() {
        return this.description;
    }

    @Override
    public void setValue(String arg0) {
        this.description = this.prepareForAssignment(this.description, arg0);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

