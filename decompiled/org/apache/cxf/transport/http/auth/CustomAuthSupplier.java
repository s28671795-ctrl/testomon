/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http.auth;

import java.net.URI;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.http.auth.HttpAuthSupplier;

public final class CustomAuthSupplier
implements HttpAuthSupplier {
    @Override
    public boolean requiresRequestCaching() {
        return false;
    }

    @Override
    public String getAuthorization(AuthorizationPolicy authPolicy, URI currentURI, Message message, String fullHeader) {
        if (authPolicy.getAuthorizationType() != null && authPolicy.getAuthorization() != null) {
            return authPolicy.getAuthorizationType() + " " + authPolicy.getAuthorization();
        }
        return null;
    }
}

