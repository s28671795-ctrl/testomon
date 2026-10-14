/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.configuration.security.ProxyAuthorizationPolicy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transport.http.auth.HttpAuthSupplier;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

public class HttpConduitConfig {
    private TLSClientParameters tlsClientParameters;
    private HTTPClientPolicy clientPolicy;
    private HttpAuthSupplier authSupplier;
    private ProxyAuthorizationPolicy proxyAuthorizationPolicy;
    private AuthorizationPolicy authorizationPolicy;

    public void setTlsClientParameters(TLSClientParameters tlsClientParameters) {
        this.tlsClientParameters = tlsClientParameters;
    }

    public HTTPClientPolicy getClientPolicy() {
        return this.clientPolicy;
    }

    public void setClientPolicy(HTTPClientPolicy clientPolicy) {
        this.clientPolicy = clientPolicy;
    }

    public void setAuthSupplier(HttpAuthSupplier authSupplier) {
        this.authSupplier = authSupplier;
    }

    public void setProxyAuthorizationPolicy(ProxyAuthorizationPolicy proxyAuthorizationPolicy) {
        this.proxyAuthorizationPolicy = proxyAuthorizationPolicy;
    }

    public void setAuthorizationPolicy(AuthorizationPolicy authorizationPolicy) {
        this.authorizationPolicy = authorizationPolicy;
    }

    public void apply(HTTPConduit conduit) {
        if (this.tlsClientParameters != null) {
            conduit.setTlsClientParameters(this.tlsClientParameters);
        }
        if (this.clientPolicy != null) {
            conduit.setClient(this.clientPolicy);
        }
        if (this.authSupplier != null) {
            conduit.setAuthSupplier(this.authSupplier);
        }
        if (this.proxyAuthorizationPolicy != null) {
            conduit.setProxyAuthorization(this.proxyAuthorizationPolicy);
        }
        if (this.authorizationPolicy != null) {
            conduit.setAuthorization(this.authorizationPolicy);
        }
    }
}

