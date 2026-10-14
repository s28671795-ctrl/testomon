/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.rt.security.saml.xacml2;

import org.apache.cxf.message.Message;
import org.apache.cxf.rt.security.saml.xacml2.AbstractXACMLAuthorizingInterceptor;
import org.apache.cxf.rt.security.saml.xacml2.PolicyDecisionPoint;
import org.opensaml.xacml.ctx.RequestType;
import org.opensaml.xacml.ctx.ResponseType;

public class XACMLAuthorizingInterceptor
extends AbstractXACMLAuthorizingInterceptor {
    private PolicyDecisionPoint pdp;

    public XACMLAuthorizingInterceptor(PolicyDecisionPoint pdp) {
        this.pdp = pdp;
    }

    @Override
    protected ResponseType performRequest(RequestType request, Message message) throws Exception {
        return this.pdp.evaluate(request);
    }
}

