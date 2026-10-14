/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.rt.security.saml.xacml2;

import java.security.Principal;
import java.util.List;
import org.apache.cxf.message.Message;
import org.opensaml.xacml.ctx.RequestType;

public interface XACMLRequestBuilder {
    public RequestType createRequest(Principal var1, List<String> var2, Message var3) throws Exception;
}

