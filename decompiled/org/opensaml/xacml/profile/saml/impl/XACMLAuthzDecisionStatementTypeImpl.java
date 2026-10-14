/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.profile.saml.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.ctx.RequestType;
import org.opensaml.xacml.ctx.ResponseType;
import org.opensaml.xacml.profile.saml.XACMLAuthzDecisionStatementType;

public class XACMLAuthzDecisionStatementTypeImpl
extends AbstractSAMLObject
implements XACMLAuthzDecisionStatementType {
    private RequestType request;
    private ResponseType response;

    protected XACMLAuthzDecisionStatementTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public RequestType getRequest() {
        return this.request;
    }

    @Override
    public ResponseType getResponse() {
        return this.response;
    }

    @Override
    public void setRequest(RequestType req) {
        this.request = this.prepareForAssignment(this.request, req);
    }

    @Override
    public void setResponse(ResponseType resp) {
        this.response = this.prepareForAssignment(this.response, resp);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XACMLObject> children = new ArrayList<XACMLObject>();
        if (this.request != null) {
            children.add(this.request);
        }
        if (this.response != null) {
            children.add(this.response);
        }
        return Collections.unmodifiableList(children);
    }
}

