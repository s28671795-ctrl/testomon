/*
 * Decompiled with CFR 0.152.
 */
package org.apache.hc.client5.http.routing;

import java.net.URI;
import java.net.URISyntaxException;
import org.apache.hc.client5.http.SchemePortResolver;
import org.apache.hc.client5.http.impl.DefaultSchemePortResolver;
import org.apache.hc.client5.http.utils.URIUtils;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.ProtocolException;
import org.apache.hc.core5.net.URIAuthority;

public final class RoutingSupport {
    public static HttpHost determineHost(HttpRequest request) throws HttpException {
        if (request == null) {
            return null;
        }
        URIAuthority authority = request.getAuthority();
        if (authority != null) {
            String scheme2 = request.getScheme();
            if (scheme2 == null) {
                throw new ProtocolException("Protocol scheme is not specified");
            }
            return new HttpHost(scheme2, authority);
        }
        try {
            URI requestURI = request.getUri();
            if (requestURI.isAbsolute()) {
                HttpHost httpHost = URIUtils.extractHost(requestURI);
                if (httpHost == null) {
                    throw new ProtocolException("URI does not specify a valid host name: " + requestURI);
                }
                return httpHost;
            }
        }
        catch (URISyntaxException uRISyntaxException) {
            // empty catch block
        }
        return null;
    }

    public static HttpHost normalize(HttpHost host2, SchemePortResolver schemePortResolver) {
        int port;
        if (host2 == null) {
            return null;
        }
        if (host2.getPort() < 0 && (port = (schemePortResolver != null ? schemePortResolver : DefaultSchemePortResolver.INSTANCE).resolve(host2)) > 0) {
            return new HttpHost(host2.getSchemeName(), host2.getAddress(), host2.getHostName(), port);
        }
        return host2;
    }
}

