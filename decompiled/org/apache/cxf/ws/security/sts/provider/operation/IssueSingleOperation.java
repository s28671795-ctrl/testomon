/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.sts.provider.operation;

import java.security.Principal;
import java.util.Map;
import org.apache.cxf.ws.security.sts.provider.model.RequestSecurityTokenResponseType;
import org.apache.cxf.ws.security.sts.provider.model.RequestSecurityTokenType;

public interface IssueSingleOperation {
    public RequestSecurityTokenResponseType issueSingle(RequestSecurityTokenType var1, Principal var2, Map<String, Object> var3);
}

