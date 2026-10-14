/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.ctx.StatusCodeType;
import org.opensaml.xacml.ctx.StatusDetailType;
import org.opensaml.xacml.ctx.StatusMessageType;
import org.opensaml.xacml.ctx.StatusType;
import org.opensaml.xacml.impl.AbstractXACMLObject;

public class StatusTypeImpl
extends AbstractXACMLObject
implements StatusType {
    private StatusCodeType statusCode;
    private StatusMessageType statusMessage;
    private StatusDetailType statusDetail;

    protected StatusTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public StatusCodeType getStatusCode() {
        return this.statusCode;
    }

    @Override
    public void setStatusCode(StatusCodeType newStatusCode) {
        this.statusCode = this.prepareForAssignment(this.statusCode, newStatusCode);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XACMLObject> children = new ArrayList<XACMLObject>();
        if (this.statusCode != null) {
            children.add(this.statusCode);
        }
        if (this.statusMessage != null) {
            children.add(this.statusMessage);
        }
        if (this.statusDetail != null) {
            children.add(this.statusDetail);
        }
        return Collections.unmodifiableList(children);
    }

    @Override
    public StatusMessageType getStatusMessage() {
        return this.statusMessage;
    }

    @Override
    public void setStatusMessage(StatusMessageType newStatusMessage) {
        this.statusMessage = this.prepareForAssignment(this.statusMessage, newStatusMessage);
    }

    @Override
    public StatusDetailType getStatusDetail() {
        return this.statusDetail;
    }

    @Override
    public void setStatusDetail(StatusDetailType newStatusDetail) {
        this.statusDetail = this.prepareForAssignment(this.statusDetail, newStatusDetail);
    }
}

