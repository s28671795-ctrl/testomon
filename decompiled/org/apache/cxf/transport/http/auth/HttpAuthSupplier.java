/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http.auth;

import java.net.URI;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.message.Message;

public interface HttpAuthSupplier {
    public boolean requiresRequestCaching();

    public String getAuthorization(AuthorizationPolicy var1, URI var2, Message var3, String var4);
}

