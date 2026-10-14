/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http.auth;

import java.net.URI;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.http.auth.AbstractSpnegoAuthSupplier;
import org.apache.cxf.transport.http.auth.HttpAuthSupplier;

public class SpnegoAuthSupplier
extends AbstractSpnegoAuthSupplier
implements HttpAuthSupplier {
    @Override
    public boolean requiresRequestCaching() {
        return false;
    }

    @Override
    public String getAuthorization(AuthorizationPolicy authPolicy, URI currentURI, Message message, String fullHeader) {
        return super.getAuthorization(authPolicy, currentURI, message);
    }
}

