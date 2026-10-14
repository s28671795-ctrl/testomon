/*
 * Decompiled with CFR 0.152.
 */
package org.apache.hc.core5.http.message;

import java.net.URI;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.Method;
import org.apache.hc.core5.http.message.BasicHttpRequest;
import org.apache.hc.core5.net.URIAuthority;

public class BasicClassicHttpRequest
extends BasicHttpRequest
implements ClassicHttpRequest {
    private static final long serialVersionUID = 1L;
    private HttpEntity entity;

    public BasicClassicHttpRequest(String method2, String scheme2, URIAuthority authority, String path) {
        super(method2, scheme2, authority, path);
    }

    public BasicClassicHttpRequest(String method2, String path) {
        super(method2, path);
    }

    public BasicClassicHttpRequest(String method2, HttpHost host2, String path) {
        super(method2, host2, path);
    }

    public BasicClassicHttpRequest(String method2, URI requestUri) {
        super(method2, requestUri);
    }

    public BasicClassicHttpRequest(Method method2, String path) {
        super(method2, path);
    }

    public BasicClassicHttpRequest(Method method2, HttpHost host2, String path) {
        super(method2, host2, path);
    }

    public BasicClassicHttpRequest(Method method2, URI requestUri) {
        super(method2, requestUri);
    }

    @Override
    public HttpEntity getEntity() {
        return this.entity;
    }

    @Override
    public void setEntity(HttpEntity entity) {
        this.entity = entity;
    }
}

