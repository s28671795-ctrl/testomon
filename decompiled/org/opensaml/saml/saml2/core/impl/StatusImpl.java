/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.Status;
import org.opensaml.saml.saml2.core.StatusCode;
import org.opensaml.saml.saml2.core.StatusDetail;
import org.opensaml.saml.saml2.core.StatusMessage;

public class StatusImpl
extends AbstractSAMLObject
implements Status {
    private StatusCode statusCode;
    private StatusMessage statusMessage;
    private StatusDetail statusDetail;

    protected StatusImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public StatusCode getStatusCode() {
        return this.statusCode;
    }

    @Override
    public void setStatusCode(StatusCode newStatusCode) {
        this.statusCode = this.prepareForAssignment(this.statusCode, newStatusCode);
    }

    @Override
    public StatusMessage getStatusMessage() {
        return this.statusMessage;
    }

    @Override
    public void setStatusMessage(StatusMessage newStatusMessage) {
        this.statusMessage = this.prepareForAssignment(this.getStatusMessage(), newStatusMessage);
    }

    @Override
    public StatusDetail getStatusDetail() {
        return this.statusDetail;
    }

    @Override
    public void setStatusDetail(StatusDetail newStatusDetail) {
        this.statusDetail = this.prepareForAssignment(this.statusDetail, newStatusDetail);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<SAMLObject> children = new ArrayList<SAMLObject>();
        children.add(this.statusCode);
        if (this.statusMessage != null) {
            children.add(this.statusMessage);
        }
        if (this.statusDetail != null) {
            children.add(this.statusDetail);
        }
        return Collections.unmodifiableList(children);
    }
}

