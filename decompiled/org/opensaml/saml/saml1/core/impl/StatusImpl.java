/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml1.core.Status;
import org.opensaml.saml.saml1.core.StatusCode;
import org.opensaml.saml.saml1.core.StatusDetail;
import org.opensaml.saml.saml1.core.StatusMessage;

public class StatusImpl
extends AbstractSAMLObject
implements Status {
    private StatusMessage statusMessage;
    private StatusCode statusCode;
    private StatusDetail statusDetail;

    protected StatusImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public StatusMessage getStatusMessage() {
        return this.statusMessage;
    }

    @Override
    public void setStatusMessage(StatusMessage message) {
        this.statusMessage = this.prepareForAssignment(this.statusMessage, message);
    }

    @Override
    public StatusCode getStatusCode() {
        return this.statusCode;
    }

    @Override
    public void setStatusCode(StatusCode code) {
        this.statusCode = this.prepareForAssignment(this.statusCode, code);
    }

    @Override
    public StatusDetail getStatusDetail() {
        return this.statusDetail;
    }

    @Override
    public void setStatusDetail(StatusDetail detail) {
        this.statusDetail = this.prepareForAssignment(this.statusDetail, detail);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<SAMLObject> children = new ArrayList<SAMLObject>(3);
        if (this.statusCode != null) {
            children.add(this.statusCode);
        }
        if (this.statusMessage != null) {
            children.add(this.statusMessage);
        }
        if (this.statusDetail != null) {
            children.add(this.statusDetail);
        }
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(children);
    }
}

