/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.sts.provider.operation;

import java.security.Principal;
import java.util.Map;
import org.apache.cxf.ws.security.sts.provider.model.RequestSecurityTokenCollectionType;
import org.apache.cxf.ws.security.sts.provider.model.RequestSecurityTokenResponseCollectionType;

public interface RequestCollectionOperation {
    public RequestSecurityTokenResponseCollectionType requestCollection(RequestSecurityTokenCollectionType var1, Principal var2, Map<String, Object> var3);
}

