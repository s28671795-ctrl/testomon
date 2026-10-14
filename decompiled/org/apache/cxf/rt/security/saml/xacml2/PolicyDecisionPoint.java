/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.rt.security.saml.xacml2;

import org.opensaml.xacml.ctx.RequestType;
import org.opensaml.xacml.ctx.ResponseType;

public interface PolicyDecisionPoint {
    public ResponseType evaluate(RequestType var1) throws Exception;
}

