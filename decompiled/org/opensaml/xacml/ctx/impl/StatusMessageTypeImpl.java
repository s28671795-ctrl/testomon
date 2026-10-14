/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx.impl;

import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xacml.ctx.StatusMessageType;
import org.opensaml.xacml.impl.AbstractXACMLObject;

public class StatusMessageTypeImpl
extends AbstractXACMLObject
implements StatusMessageType {
    private String message;

    protected StatusMessageTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getValue() {
        return this.message;
    }

    @Override
    public void setValue(String newMessage) {
        this.message = this.prepareForAssignment(this.message, newMessage);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

