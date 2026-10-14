/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml1.core.StatusCode;

public class StatusCodeImpl
extends AbstractSAMLObject
implements StatusCode {
    private QName value;
    private StatusCode childStatusCode;

    protected StatusCodeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public QName getValue() {
        return this.value;
    }

    @Override
    public void setValue(QName newValue) {
        this.value = this.prepareAttributeValueForAssignment("Value", this.value, newValue);
    }

    @Override
    public StatusCode getStatusCode() {
        return this.childStatusCode;
    }

    @Override
    public void setStatusCode(StatusCode statusCode) {
        this.childStatusCode = this.prepareForAssignment(this.childStatusCode, statusCode);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        if (this.childStatusCode != null) {
            ArrayList<StatusCode> contents = new ArrayList<StatusCode>(1);
            contents.add(this.childStatusCode);
            return Collections.unmodifiableList(contents);
        }
        return null;
    }
}

