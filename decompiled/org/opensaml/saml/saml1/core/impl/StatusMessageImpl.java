/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml1.core.StatusMessage;

public class StatusMessageImpl
extends AbstractSAMLObject
implements StatusMessage {
    private String message;

    protected StatusMessageImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public void setMessage(String msg) {
        this.message = this.prepareForAssignment(this.message, msg);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

