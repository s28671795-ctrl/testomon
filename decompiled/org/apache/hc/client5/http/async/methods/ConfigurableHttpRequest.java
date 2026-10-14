/*
 * Decompiled with CFR 0.152.
 */
package org.apache.hc.client5.http.async.methods;

import java.net.URI;
import org.apache.hc.client5.http.config.Configurable;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.message.BasicHttpRequest;
import org.apache.hc.core5.net.URIAuthority;

public class ConfigurableHttpRequest
extends BasicHttpRequest
implements Configurable {
    private static final long serialVersionUID = 1L;
    private RequestConfig requestConfig;

    public ConfigurableHttpRequest(String method2, String path) {
        super(method2, path);
    }

    public ConfigurableHttpRequest(String method2, HttpHost host2, String path) {
        super(method2, host2, path);
    }

    public ConfigurableHttpRequest(String method2, String scheme2, URIAuthority authority, String path) {
        super(method2, scheme2, authority, path);
    }

    public ConfigurableHttpRequest(String method2, URI requestUri) {
        super(method2, requestUri);
    }

    @Override
    public RequestConfig getConfig() {
        return this.requestConfig;
    }

    public void setConfig(RequestConfig requestConfig) {
        this.requestConfig = requestConfig;
    }
}

