/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.StatusCode;

public class StatusCodeImpl
extends AbstractSAMLObject
implements StatusCode {
    private String value;
    private StatusCode childStatusCode;

    protected StatusCodeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public StatusCode getStatusCode() {
        return this.childStatusCode;
    }

    @Override
    public void setStatusCode(StatusCode newStatusCode) {
        this.childStatusCode = this.prepareForAssignment(this.childStatusCode, newStatusCode);
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public void setValue(String newValue) {
        this.value = this.prepareForAssignment(this.value, newValue);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        if (this.childStatusCode != null) {
            ArrayList<StatusCode> children = new ArrayList<StatusCode>();
            children.add(this.childStatusCode);
            return Collections.unmodifiableList(children);
        }
        return null;
    }
}

